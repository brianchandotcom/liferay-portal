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

package com.liferay.portal.wab.extender.internal.adapter;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Enumeration;

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
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
public class HttpAdapter {

	@Activate
	protected void activate(ComponentContext componentContext) {
		BundleContext bundleContext = componentContext.getBundleContext();

		_httpServiceServlet = new HttpServiceServlet() {

			@Override
			public ServletConfig getServletConfig() {
				return _servletConfig;
			}

			@Override
			public void init(ServletConfig servletConfig) {
				_servletConfig = servletConfig;
			}

			private ServletConfig _servletConfig;

		};

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
				if (name.equals(
						HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT)) {

					return _servletContext.getContextPath() +
						_servletContext.getInitParameter(name);
				}

				return _servletContext.getInitParameter(name);
			}

		};

		try {
			_httpServiceServlet.init(servletConfig);
		}
		catch (ServletException se) {
			_servletContext.log(se.getMessage(), se);

			return;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("bean.id", HttpServlet.class.getName());
		properties.put("original.bean", Boolean.TRUE.toString());

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				HttpServiceServlet.class.getName(), HttpServlet.class.getName()
			},
			_httpServiceServlet, properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();

		_serviceRegistration = null;

		_httpServiceServlet.destroy();

		_httpServiceServlet = null;
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private HttpServiceServlet _httpServiceServlet;
	private ServiceRegistration<?> _serviceRegistration;
	private volatile ServletContext _servletContext;

}