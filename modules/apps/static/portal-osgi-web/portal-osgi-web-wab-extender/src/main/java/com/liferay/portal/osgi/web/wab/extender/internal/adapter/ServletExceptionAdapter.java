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

import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Raymond Augé
 */
public class ServletExceptionAdapter implements Servlet {

	public ServletExceptionAdapter(
		Servlet servlet, ModifiableServletContext modifiableServletContext) {

		_servlet = servlet;
		_modifiableServletContext = modifiableServletContext;
	}

	@Override
	public void destroy() {
		_servlet.destroy();
	}

	public Exception getException() {
		return _exception;
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servlet.getServletConfig();
	}

	@Override
	public String getServletInfo() {
		return _servlet.getServletInfo();
	}

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			_servlet.init(
				new ServletConfigWrapper(
					servletConfig, _modifiableServletContext));
		}
		catch (Exception exception) {
			_exception = exception;
		}
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		_servlet.service(servletRequest, servletResponse);
	}

	private Exception _exception;
	private ModifiableServletContext _modifiableServletContext;
	private final Servlet _servlet;

	private static class ServletConfigWrapper implements ServletConfig {

		public ServletConfigWrapper(
			ServletConfig wrappedServletConfig,
			ModifiableServletContext modifiableServletContext) {

			_wrappedServletConfig = wrappedServletConfig;
			_modifiableServletContext = modifiableServletContext;
		}

		public String getInitParameter(String name) {
			return _wrappedServletConfig.getInitParameter(name);
		}

		public Enumeration<String> getInitParameterNames() {
			return _wrappedServletConfig.getInitParameterNames();
		}

		public ServletContext getServletContext() {
			return (ServletContext)_modifiableServletContext;
		}

		public String getServletName() {
			return _wrappedServletConfig.getServletName();
		}

		private ModifiableServletContext _modifiableServletContext;
		private final ServletConfig _wrappedServletConfig;

	}

}