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

package com.liferay.portal.servlet.jsp;

import com.liferay.portal.servlet.jsp.compiler.compiler.internal.JspBundleClassloader;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Raymond Augé
 */
public class JspServlet
	extends org.apache.jasper.servlet.JspServlet {

	public static final String _JSP_CLASSLOADER =
		JspServlet.class.getName().concat(".classLoader");

	public JspServlet() {
		_jspBundle = FrameworkUtil.getBundle(
			com.liferay.portal.servlet.jsp.JspServlet.class);
	}

	@Override
	public void init(final ServletConfig servletConfig)
		throws ServletException {

		final ServletContext servletContext = servletConfig.getServletContext();

		BundleContext bundleContext = (BundleContext)servletContext.getAttribute(
				"osgi-bundlecontext");

		_jspBundleClassloader = new JspBundleClassloader(
			bundleContext.getBundle(), _jspBundle);

		servletContext.setAttribute(_JSP_CLASSLOADER, _jspBundleClassloader);

		final Map<String, String> defaults = new HashMap<String, String>();

		defaults.put(
			"compilerClassName",
			"com.liferay.portal.servlet.jsp.compiler.compiler.JspCompiler");
		defaults.put("development", "false");
		defaults.put("httpMethods", "GET,POST,HEAD");
		defaults.put("keepgenerated", "false");
		defaults.put("logVerbosityLevel", "DEBUG");

		Enumeration<String> names = servletConfig.getInitParameterNames();
		Set<String> nameSet = new HashSet<String>(Collections.list(names));

		nameSet.addAll(defaults.keySet());

		final Enumeration<String> initParameterNames =
				Collections.enumeration(nameSet);

		super.init(
			new ServletConfig() {

				@Override
				public String getServletName() {
					return servletConfig.getServletName();
				}

				@Override
				public ServletContext getServletContext() {
					return getServletContextWrapper(servletContext);
				}

				@Override
				public Enumeration<String> getInitParameterNames() {
					return initParameterNames;
				}

				@Override
				public String getInitParameter(String name) {
					String value = servletConfig.getInitParameter(name);

					if (value == null) {
						value = defaults.get(name);
					}

					return value;
				}
			}
		);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_jspBundleClassloader);

			super.service(request, response);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private ServletContext getServletContextWrapper(
		ServletContext servletContext) {

		if (_jspServletContext == null) {
			synchronized (this) {
				if (_jspServletContext == null) {
					_jspServletContext = (ServletContext)Proxy.newProxyInstance(
						getClass().getClassLoader(), _INTERFACES,
						new JspServletContextInvocationHandler(servletContext));
				}
			}
		}

		return _jspServletContext;
	}

	private static final Class<?>[] _INTERFACES = {
		ServletContext.class
	};

	private final Bundle _jspBundle;
	private URLClassLoader _jspBundleClassloader;
	private volatile ServletContext _jspServletContext;

	private class JspServletContextInvocationHandler
		implements InvocationHandler {

		public JspServletContextInvocationHandler(
			ServletContext servletContext) {

			_servletContext = servletContext;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.getName().equals("getClassLoader")) {
				return _jspBundleClassloader;
			}
			else if (method.getName().equals("getResource")) {
				return getResource((String)args[0]);
			}
			else if (method.getName().equals("getResourceAsStream")) {
				return getResourceAsStream((String)args[0]);
			}
			else if (method.getName().equals("getResourcePaths")) {
				return getResourcePaths((String)args[0]);
			}

			return method.invoke(_servletContext, args);
		}

		private URL getResource(String path) {
			try {
				URL url = _servletContext.getResource(path);

				if (url == null) {
					url = _servletContext.getClassLoader().getResource(path);

					if (url == null) {
						if (path.startsWith("/")) {
							path = path.substring(1);
						}

						if (!path.startsWith("META-INF/")) {
							url = _servletContext.getResource(
								"/META-INF/resources/".concat(path));
						}

						if (url == null) {
							url = _jspBundle.getEntry(path);
						}
					}
				}

				return url;
			}
			catch (MalformedURLException e) {
				// Ignore
			}

			return null;
		}

		private InputStream getResourceAsStream(String path) {
			URL url = getResource(path);

			if (url == null) {
				return null;
			}

			try {
				return url.openStream();
			}
			catch (IOException e) {
				return null;
			}
		}

		private Set<String> getResourcePaths(String path) {
			Set<String> paths = _servletContext.getResourcePaths(path);

			Enumeration<URL> enumeration = _jspBundle.findEntries(
				path, null, false);

			if (enumeration != null) {
				if ((paths == null) && enumeration.hasMoreElements()) {
					paths = new HashSet<>();
				}

				while (enumeration.hasMoreElements()) {
					URL url = enumeration.nextElement();

					paths.add(url.getPath());
				}
			}

			return paths;
		}

		private ServletContext _servletContext;

	}

}