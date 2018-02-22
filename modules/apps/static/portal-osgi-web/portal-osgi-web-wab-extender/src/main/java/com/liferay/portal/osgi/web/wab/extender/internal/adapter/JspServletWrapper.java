/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.jasper.Constants;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JspServletWrapper extends HttpServlet {

	public JspServletWrapper(Servlet servlet, String jspFile) {
		_servlet = servlet;
		_jspFile = jspFile;
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
			Constants.JSP_FILE);

		if (_jspFile != null) {
			servletRequest.setAttribute(Constants.JSP_FILE, _jspFile);
		}

		try {
			_servlet.service(servletRequest, servletResponse);
		}
		finally {
			servletRequest.setAttribute(Constants.JSP_FILE, curJspFile);
		}
	}

	private final String _jspFile;
	private final Servlet _servlet;

}