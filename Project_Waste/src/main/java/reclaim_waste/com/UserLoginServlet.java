package reclaim_waste.com;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/wastedb?useSSL=false","root","W7301@jqir#");
			PreparedStatement pst=con.prepareStatement("SELECT * from userlogintb WHERE uemail = ? AND upwd = ?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			
			ResultSet rs =pst.executeQuery();
			if(rs.next()) {
				//request.setAttribute("", con);
				session.setAttribute("name",rs.getString("uname"));
				dispatcher =request.getRequestDispatcher("userDashboard.jsp");
				
			}else {
				request.setAttribute("status","failed");
				dispatcher =request.getRequestDispatcher("userlogin.jsp");
				
			}
			dispatcher.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
