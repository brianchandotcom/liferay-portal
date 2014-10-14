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

import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.InstanceFactory;

import java.io.IOException;

import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class SecureServlet
	extends BasePortalLifecycle implements ServletConfig, Servlet {

	@Override
	public void destroy() {
		portalDestroy();
	}

	@Override
	public String getInitParameter(String name) {
		return servletConfig.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return servletConfig.getInitParameterNames();
	}

	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return servletConfig.getServletContext();
	}

	@Override
	public String getServletInfo() {
		return servlet.getServletInfo();
	}

	@Override
	public String getServletName() {
		return servletConfig.getServletName();
	}

	@Override
	public void init(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;

		registerPortalLifecycle();
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		servlet.service(servletRequest, servletResponse);
	}

	@Override
	protected void doPortalDestroy() {
		servlet.destroy();
	}

	@Override
	protected void doPortalInit() throws Exception {
		ServletContext servletContext = servletConfig.getServletContext();

		ClassLoader classLoader = servletContext.getClassLoader();

		String servletClass = servletConfig.getInitParameter("servlet-class");

		servlet = (Servlet)InstanceFactory.newInstance(
			classLoader, servletClass);

		servlet.init(servletConfig);
	}

	protected Servlet servlet;
	protected ServletConfig servletConfig;

}