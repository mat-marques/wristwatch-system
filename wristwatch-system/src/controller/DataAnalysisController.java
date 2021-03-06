package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import dao.BrandDao;
import dao.CollectionDao;
import dao.DAOFactory;
import dao.PurchaseDao;
import dao.SaleDao;
import dao.WristwatchDao;
import model.Brand;
import model.Collection;
import model.Sale;
import model.Salesman;
import model.Wristwatch;

/**
 * Servlet implementation class DataAnalysis
 */
@WebServlet(name = "/DataAnalysisController",
urlPatterns = {
		"/higher-price/Analysis",
		"/lower-price/Analysis",
		"/higher-plots/Analysis",
		"/lower-plots/Analysis",
		"/set-Analysis",
		"/brand-salesman/Analysis",
		"/brand-collection/Analysis",
		"/brand-wristwatch/Analysis",
		"/collection-wristwatch/Analysis",
		"/products-brand/Analysis",
		"/products-collection/Analysis"
	})
public class DataAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataAnalysisController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		
		switch(request.getServletPath()) {
		case "/higher-price/Analysis":
            try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	int qtdElements = Integer.parseInt(request.getParameter("qtdElements"));
            	List<Wristwatch> wristwatchList;
            	String query;
            	
            	query = "SELECT * "
            			+ "FROM wristwatch.wristwatch ";
            	
            	if(request.getParameter("floorValue") != "") {
            		
	            	query = query + "WHERE price > " + request.getParameter("floorValue");
	            	
            	}
            	
            	query = query + " ORDER BY price DESC LIMIT " + qtdElements;
            	
            	query = query + ";";
            	
            	System.out.println(query);
            	
            	wristwatchList = dao.select(query);
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/higher-price.jsp");
			dispatcher.forward(request, response);
			
			break;
			
		case "/lower-price/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	int qtdElements = Integer.parseInt(request.getParameter("qtdElements"));
            	List<Wristwatch> wristwatchList;
            	String query;

            	query = "SELECT * "
            			+ "FROM wristwatch.wristwatch ";
            	
            	if(request.getParameter("floorValue") != "") {
            		
	            	query = query + "WHERE price < " + request.getParameter("floorValue");
	            	
            	}
            	
            	query = query + " ORDER BY price ASC LIMIT " + qtdElements;
            	
            	query = query + ";";
            	
            	wristwatchList = dao.select(query);
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/lower-price.jsp");
			dispatcher.forward(request, response);
			
			break;
			
		case "/higher-plots/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	int qtdElements = Integer.parseInt(request.getParameter("qtdElements"));
            	List<Wristwatch> wristwatchList;
            	String query;

            	query = "SELECT * "
            			+ "FROM wristwatch.wristwatch ";
            	
            	query = query + " ORDER BY price DESC LIMIT " + qtdElements;
            	
            	if(request.getParameter("group") != null) {
	            	query = query + " GROUP BY " + request.getParameter("group");
            	}
            	
            	query = query + ";";
            	
            	wristwatchList = dao.select(query);
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/higher-plots.jsp");
			dispatcher.forward(request, response);
			
			break;
		case "/lower-plots/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	int qtdElements = Integer.parseInt(request.getParameter("qtdElements"));
            	List<Wristwatch> wristwatchList;
            	String query;

            	query = "SELECT * "
            			+ "FROM wristwatch.wristwatch ";
            	
            	if(request.getParameter("floorValue") != "") {
            		
	            	query = query + "WHERE plots_price < " + request.getParameter("floorValue");
	            	
            	}
            	
            	query = query + " ORDER BY plots_price ASC LIMIT " + qtdElements;
            	
            	query = query + ";";
            	
            	wristwatchList = dao.select(query);
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/lower-plots.jsp");
			dispatcher.forward(request, response);
			
			break;
		case "/set-Analysis":
			int myQuery = Integer.parseInt(request.getParameter("query"));
			
			if(myQuery == 1) {
				
				try (DAOFactory daoFactory = new DAOFactory()){
					BrandDao b = daoFactory.getBrandDao();
					List<Brand> brandList;
					
					brandList = b.all();
					
					session.setAttribute("brandList", brandList);
				} catch (ClassNotFoundException | IOException | SQLException ex) {
	                session.setAttribute("error", ex.getMessage());
	            }
				
				response.sendRedirect(request.getContextPath() + "/view/query-brand-salesman.jsp");
			}
			else if(myQuery == 2) {
				try (DAOFactory daoFactory = new DAOFactory()){
					BrandDao b = daoFactory.getBrandDao();
					List<Brand> brandList;
					
					 brandList = b.all();
					
					session.setAttribute("brandList", brandList);
				} catch (ClassNotFoundException | IOException | SQLException ex) {
	                session.setAttribute("error", ex.getMessage());
	            }
				
				response.sendRedirect(request.getContextPath() + "/view/query-brand-collection.jsp");
			}
			else if(myQuery == 3) {
				try (DAOFactory daoFactory = new DAOFactory()){
					BrandDao b = daoFactory.getBrandDao();
					List<Brand> brandList;
					
					 brandList = b.all();
					
					session.setAttribute("brandList", brandList);
				} catch (ClassNotFoundException | IOException | SQLException ex) {
	                session.setAttribute("error", ex.getMessage());
	            }
				
				response.sendRedirect(request.getContextPath() + "/view/query-brand-wristwatch.jsp");
			}
			else if(myQuery == 4) {
				try (DAOFactory daoFactory = new DAOFactory()){
					CollectionDao c = daoFactory.getCollectionDao();
					List<Collection> collectionList;
					
					collectionList = c.all();
					
					session.setAttribute("collectionList", collectionList);
				} catch (ClassNotFoundException | IOException | SQLException ex) {
	                session.setAttribute("error", ex.getMessage());
	            }
				
				response.sendRedirect(request.getContextPath() + "/view/query-collection-wristwatch.jsp");
			}
			else {
				response.sendRedirect(request.getContextPath() + "/view/data-analysis.jsp");
			}
			
			
			break;
		case "/brand-salesman/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	PurchaseDao dao = daoFactory.getPurchaseDao();
            	CachedRowSet result;
            	List<Salesman> salesmanList = new ArrayList<>();
            	String query;

            	query = "SELECT salesman_name FROM wristwatch.purchase WHERE brand_name = '" + request.getParameter("brand") + "';";
            	
            	result = dao.select(query);
            	System.out.println(query);
            	
            	while (result.next()) {
                	Salesman s = new Salesman();
                	
                	s.setName(result.getString("salesman_name"));

                    salesmanList.add(s);
                }
            	
                session.setAttribute("salesmanList", salesmanList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-brand-salesman.jsp");
			dispatcher.forward(request, response);
			break;
		case "/brand-collection/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	CollectionDao dao = daoFactory.getCollectionDao();
            	CachedRowSet result;
            	List<Collection> collectionList = new ArrayList<>();
            	String query;

            	query = "SELECT * FROM wristwatch.collection WHERE brand_name = '" + request.getParameter("brand") + "';";
            	System.out.println(query);
            	result = dao.select(query);
            	
            	while (result.next()) {
            		Collection c = new Collection();
                	
                	c.setName(result.getString("name"));
                	c.setBrand_name(result.getString("brand_name"));
                	
                	collectionList.add(c);
                }
            	
                session.setAttribute("collectionList", collectionList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-brand-collection.jsp");
			dispatcher.forward(request, response);
			break;
		case "/brand-wristwatch/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	CachedRowSet result;
            	List<Wristwatch> wristwatchList = new ArrayList<>();
            	String query;

            	query = "SELECT name, price, collection_name FROM wristwatch.wristwatch WHERE brand_name = '" + request.getParameter("brand") + "';";
            	System.out.println(query);
            	result = dao.select2(query);
            	
            	while (result.next()) {
            		Wristwatch w = new Wristwatch();
                	
                	w.setName(result.getString("name"));
                	w.setCollection_name(result.getString("collection_name"));
                	w.setPrice(result.getDouble("price"));
                	
                	wristwatchList.add(w);
                }
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-brand-wristwatch.jsp");
			dispatcher.forward(request, response);
			break;
		case "/collection-wristwatch/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	CachedRowSet result;
            	List<Wristwatch> wristwatchList = new ArrayList<>();
            	String query;

            	query = "SELECT name, price, brand_name FROM wristwatch.wristwatch WHERE collection_name = '" + request.getParameter("collection") + "';";
            	System.out.println(query);
            	result = dao.select2(query);
            	
            	while (result.next()) {
            		Wristwatch w = new Wristwatch();
                	
                	w.setName(result.getString("name"));
                	w.setBrand_name(result.getString("brand_name"));
                	w.setPrice(result.getDouble("price"));
                	
                	wristwatchList.add(w);
                }
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-collection-wristwatch.jsp");
			dispatcher.forward(request, response);
			break;
		case "/products-brand/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	CachedRowSet result;
            	List<Wristwatch> wristwatchList = new ArrayList<>();
            	String query;

            	query = "SELECT brand_name, AVG(price) AS media " + 
            			"FROM wristwatch.wristwatch GROUP BY brand_name ORDER BY media LIMIT 10;";
            	
            	System.out.println(query);
            	result = dao.select2(query);
            	
            	while (result.next()) {
            		Wristwatch w = new Wristwatch();
                	
                	w.setBrand_name(result.getString("brand_name"));
                	w.setAvg(result.getDouble("media"));
                	
                	wristwatchList.add(w);
                }
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-products-brand.jsp");
			dispatcher.forward(request, response);
			break;
		case "/products-collection/Analysis":
			try (DAOFactory daoFactory = new DAOFactory()) {
            	WristwatchDao dao = daoFactory.getWristwatchDao();
            	CachedRowSet result;
            	List<Wristwatch> wristwatchList = new ArrayList<>();
            	String query;

            	query = "SELECT collection_name, AVG(price) AS media " + 
            			"FROM wristwatch.wristwatch GROUP BY collection_name ORDER BY media LIMIT 10;";
            	
            	System.out.println(query);
            	result = dao.select2(query);
            	
            	while (result.next()) {
            		Wristwatch w = new Wristwatch();
                	
                	w.setCollection_name(result.getString("collection_name"));
                	w.setAvg(result.getDouble("media"));
                	
                	wristwatchList.add(w);
                }
            	
                session.setAttribute("wristwatchList", wristwatchList);
            } catch (ClassNotFoundException | IOException | SQLException ex) {
                session.setAttribute("error", ex.getMessage());
            }
            
            dispatcher = request.getRequestDispatcher("/view/query-products-collection.jsp");
			dispatcher.forward(request, response);
			break;
		}
	}

}
