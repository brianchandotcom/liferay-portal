package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.liferay.portal.osgi.web.servlet.jsp.compiler.JspServlet;

public class JspServletWrapper extends HttpServlet {

	public JspServletWrapper(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void destroy() {
		_servlet.destroy();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servlet.getServletConfig();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		_servlet.init(servletConfig);
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		String curJspFile = (String)servletRequest.getAttribute(
			JspServlet.JSP_FILE);

		if (jspFile != null) {
			servletRequest.setAttribute(JspServlet.JSP_FILE, jspFile);
		}

		try {
			_servlet.service(servletRequest, servletResponse);
		}
		finally {
			servletRequest.setAttribute(JspServlet.JSP_FILE, curJspFile);
		}
	}

	protected String jspFile;

	private final Servlet _servlet = new JspServlet();

}