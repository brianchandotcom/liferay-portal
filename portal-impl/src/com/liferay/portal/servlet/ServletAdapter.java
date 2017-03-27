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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Pavel Savinov
 */
public class ServletAdapter extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(" + config.getInitParameter("filter") + ")" +
					"(objectClass=" + Servlet.class.getName() + "))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();

		Servlet servlet = getServlet();

		if (servlet != null) {
			servlet.init(config);
		}
	}

	protected Servlet getServlet() {
		return _serviceTracker.getService();
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTracker.close();
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		Servlet servlet = getServlet();

		if (servlet == null) {
			ServletConfig servletConfig = getServletConfig();

			PortalUtil.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				new ServletException(
					"A servlet matching the filter " +
						servletConfig.getInitParameter("filter") +
						" is unavailable"),
				request, response);

			return;
		}

		servlet.service(request, response);
	}

	private ServiceTracker<Servlet, Servlet> _serviceTracker;

}