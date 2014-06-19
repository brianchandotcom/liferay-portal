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

		_servletConfig = new InnerServletConfig();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private ServletConfig _servletConfig;
	private ServletContext _servletContext;

	private class InnerServletConfig implements ServletConfig {

		@Override
		public String getServletName() {
			return JSPSupportServlet.this.getClass().getName();
		}

		@Override
		public ServletContext getServletContext() {
			return JSPSupportServlet.this._servletContext;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return Collections.emptyEnumeration();
		}

		@Override
		public String getInitParameter(String arg0) {
			return null;
		}

	}

}