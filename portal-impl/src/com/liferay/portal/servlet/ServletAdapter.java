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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.IOException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public class ServletAdapter extends HttpServlet {

	@Override
	public void destroy() {
		super.destroy();

		_serviceTracker.close();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			StringBundler.concat(
				"(&", config.getInitParameter("filter"), "(objectClass=",
				Servlet.class.getName(), "))"));

		_serviceTracker = registry.trackServices(
			filter, new ServletTrackerCustomizer());

		_serviceTracker.open();
	}

	protected Servlet getServlet() {
		return _serviceTracker.getService();
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

	private static final Log _log = LogFactoryUtil.getLog(ServletAdapter.class);

	private ServiceTracker<Servlet, Servlet> _serviceTracker;

	private static class ServletTrackerCustomizer
		implements ServiceTrackerCustomizer<Servlet, Servlet> {

		@Override
		public Servlet addingService(
			ServiceReference<Servlet> serviceReference) {

			Map<String, Object> properties = serviceReference.getProperties();

			ServletConfig servletConfig = new ServletConfig() {

				@Override
				public String getInitParameter(String name) {
					String value = GetterUtil.getString(
						properties.get(name), null);

					return value;
				}

				@Override
				public Enumeration<String> getInitParameterNames() {
					return Collections.enumeration(properties.keySet());
				}

				@Override
				public ServletContext getServletContext() {
					return ServletContextPool.get(
						PortalUtil.getServletContextName());
				}

				@Override
				public String getServletName() {
					return GetterUtil.getString(
						properties.get("osgi.http.whiteboard.servlet.name"));
				}

			};

			Registry registry = RegistryUtil.getRegistry();

			Servlet servlet = registry.getService(serviceReference);

			try {
				servlet.init(servletConfig);
			}
			catch (ServletException se) {
				_log.error("Unable to initialize servlet", se);
			}

			return servlet;
		}

		@Override
		public void modifiedService(
			ServiceReference<Servlet> serviceReference, Servlet service) {
		}

		@Override
		public void removedService(
			ServiceReference<Servlet> serviceReference, Servlet service) {

			service.destroy();
		}

	}

}