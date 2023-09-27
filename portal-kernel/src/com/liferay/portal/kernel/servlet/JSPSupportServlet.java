/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletConfig _servletConfig = new JSPSupportServletConfig();
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