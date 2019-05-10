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

import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.equinox.http.servlet.HttpServiceServlet;
import org.eclipse.equinox.http.servlet.HttpSessionTrackerUtil;

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
@Component(immediate = true, service = {})
public class HttpAdapter {

	@Activate
	protected void activate(ComponentContext componentContext) {
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
			public String getInitParameter(String name) {
				if (name.equals(
						HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT)) {

					return _servletContext.getContextPath() +
						_servletContext.getInitParameter(name);
				}

				return _servletContext.getInitParameter(name);
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				return _servletContext.getInitParameterNames();
			}

			@Override
			public ServletContext getServletContext() {
				return _servletContext;
			}

			@Override
			public String getServletName() {
				return "Module Framework Servlet";
			}

		};

		try {
			_httpServiceServlet.init(servletConfig);
		}
		catch (ServletException se) {
			_servletContext.log(se.getMessage(), se);

			return;
		}

		BundleContext bundleContext = componentContext.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("bean.id", HttpServlet.class.getName());
		properties.put("original.bean", Boolean.TRUE.toString());

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				HttpServiceServlet.class.getName(), HttpServlet.class.getName()
			},
			_httpServiceServlet, properties);

		PortletSessionListenerManager.addHttpSessionListener(
			_INVALIDATEHTTPSESSION_LISTENER);
	}

	@Deactivate
	protected void deactivate() {
		PortletSessionListenerManager.removeHttpSessionListener(
			_INVALIDATEHTTPSESSION_LISTENER);

		_serviceRegistration.unregister();

		_serviceRegistration = null;

		_httpServiceServlet.destroy();

		_httpServiceServlet = null;
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
		Class<?> clazz = getClass();

		_servletContext = (ServletContext)Proxy.newProxyInstance(
			clazz.getClassLoader(), _INTERFACES,
			new ServletContextAdaptor(servletContext));
	}

	private static final Class<?>[] _INTERFACES = new Class<?>[] {
		ServletContext.class
	};

	private static final HttpSessionListener _INVALIDATEHTTPSESSION_LISTENER =
		new HttpSessionListener() {

			@Override
			public void sessionCreated(HttpSessionEvent httpSessionEvent) {
			}

			@Override
			public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
				HttpSession httpSession = httpSessionEvent.getSession();

				HttpSessionTrackerUtil.invalidate(httpSession.getId());
			}

		};

	private HttpServiceServlet _httpServiceServlet;
	private ServiceRegistration<?> _serviceRegistration;
	private ServletContext _servletContext;

	private static class ServletContextAdaptor implements InvocationHandler {

		public ServletContextAdaptor(ServletContext servletContext) {
			_servletContext = servletContext;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("getInitParameter") && (args != null) &&
				(args.length == 1)) {

				if (Objects.equals(args[0], "osgi.http.endpoint")) {
					return _servletContext.getInitParameter((String)args[0]);
				}

				return null;
			}
			else if (methodName.equals("getInitParameterNames") &&
					 (args == null)) {

				return Collections.enumeration(
					Collections.singleton("osgi.http.endpoint"));
			}
			else if (methodName.equals("getJspConfigDescriptor") &&
					 JspConfigDescriptor.class.isAssignableFrom(
						 method.getReturnType())) {

				return null;
			}

			try {
				return method.invoke(_servletContext, args);
			}
			catch (InvocationTargetException ite) {
				throw ite.getCause();
			}
		}

		private final ServletContext _servletContext;

	}

}