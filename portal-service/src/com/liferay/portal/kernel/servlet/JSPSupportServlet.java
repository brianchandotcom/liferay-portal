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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 * @author Raymond Augé
 */
public class JSPSupportServlet extends HttpServlet {

	public JSPSupportServlet(ServletContext servletContext) {
		_servletContext = servletContext;

		_servletConfig = new JSPSupportServletConfig();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletConfig _servletConfig;
	private final ServletContext _servletContext;

	private class JSPSupportServletConfig implements ServletConfig {

		@Override
		public String getInitParameter(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(Collections.<String>emptyList());
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		@Override
		public String getServletName() {
			return JSPSupportServlet.class.getName();
		}

	}

}