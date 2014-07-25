/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

package com.liferay.osgi.http.adaptor;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.eclipse.equinox.http.servlet.HttpServiceServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true
)
public class HttpAdaptor {

	@Activate
	protected void activate(ComponentContext componentContext) {
		BundleContext bundleContext = componentContext.getBundleContext();

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put("bean.id", HttpServlet.class.getName());
		properties.put("original.bean", Boolean.TRUE.toString());

		String[] classes = new String[] {
			HttpServiceServlet.class.getName(),
			HttpServlet.class.getName()
		};

		_httpServiceServlet = new AdaptorServlet();

		ServletConfig servletConfig = new ServletConfig() {

			@Override
			public String getServletName() {
				return "Module Framework Servlet";
			}

			@Override
			public ServletContext getServletContext() {
				return _servletContext;
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				return _servletContext.getInitParameterNames();
			}

			@Override
			public String getInitParameter(String name) {
				return _servletContext.getInitParameter(name);
			}

		};

		try {
			_httpServiceServlet.init(servletConfig);

			_serviceRegistration = bundleContext.registerService(
				classes, _httpServiceServlet, properties);
		}
		catch (ServletException se) {
			_servletContext.log(se.getMessage(), se);
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
		_serviceRegistration = null;
		_httpServiceServlet.destroy();
		_httpServiceServlet = null;
	}

	@Reference(target = "(original.bean=true)")
	private void getServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;
	private ServiceRegistration<?> _serviceRegistration;
	private HttpServiceServlet _httpServiceServlet;

	private class AdaptorServlet extends HttpServiceServlet {

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			_servletConfig = servletConfig;
		}

		@Override
		public ServletConfig getServletConfig() {
			return _servletConfig;
		}

		private ServletConfig _servletConfig;

	}

}