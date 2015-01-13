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

package com.liferay.portal.servlet.jsp.compiler;

import com.liferay.portal.servlet.jsp.compiler.internal.JspBundleClassloader;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 */
public class JspServlet extends HttpServlet {

	public JspServlet() {
		_jspBundle = FrameworkUtil.getBundle(
			com.liferay.portal.servlet.jsp.compiler.JspServlet.class);
	}

	@Override
	public void destroy() {
		_jspServlet.destroy();
	}

	@Override
	public boolean equals(Object obj) {
		return _jspServlet.equals(obj);
	}

	@Override
	public String getInitParameter(String name) {
		return _jspServlet.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return _jspServlet.getInitParameterNames();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _jspServlet.getServletConfig();
	}

	@Override
	public ServletContext getServletContext() {
		return _jspServlet.getServletContext();
	}

	@Override
	public String getServletInfo() {
		return _jspServlet.getServletInfo();
	}

	@Override
	public String getServletName() {
		return _jspServlet.getServletName();
	}

	@Override
	public int hashCode() {
		return _jspServlet.hashCode();
	}

	@Override
	public void init() throws ServletException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void init(final ServletConfig servletConfig)
		throws ServletException {

		final ServletContext servletContext = servletConfig.getServletContext();

		ClassLoader classLoader = servletContext.getClassLoader();

		if (!(classLoader instanceof BundleReference)) {
			throw new IllegalStateException();
		}

		List<Bundle> bundles = new ArrayList<>();

		BundleReference bundleReference = (BundleReference)classLoader;

		_bundle = bundleReference.getBundle();

		bundles.add(_bundle);
		bundles.add(_jspBundle);

		collectTaglibProviderBundles(bundles);

		_allParticipatingBundles = bundles.toArray(new Bundle[bundles.size()]);

		_jspBundleClassloader = new JspBundleClassloader(
			_allParticipatingBundles);

		final Map<String, String> defaults = new HashMap<>();

		defaults.put(
			"compilerClassName",
			"com.liferay.portal.servlet.jsp.compiler.internal.JspCompiler");
		defaults.put("development", "false");
		defaults.put("httpMethods", "GET,POST,HEAD");
		defaults.put("keepgenerated", "false");
		defaults.put("logVerbosityLevel", "DEBUG");

		Enumeration<String> names = servletConfig.getInitParameterNames();
		Set<String> nameSet = new HashSet<>(Collections.list(names));

		nameSet.addAll(defaults.keySet());

		final Enumeration<String> initParameterNames = Collections.enumeration(
			nameSet);

		_jspServlet.init(
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
	public void log(String msg) {
		_jspServlet.log(msg);
	}

	@Override
	public void log(String message, Throwable t) {
		_jspServlet.log(message, t);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_jspBundleClassloader);

			_jspServlet.service(request, response);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		service((HttpServletRequest)request, (HttpServletResponse)response);
	}

	@Override
	public String toString() {
		return _jspServlet.toString();
	}

	private void collectTaglibProviderBundles(List<Bundle> bundles) {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleWire wire : bundleWiring.getRequiredWires(_OSGI_EXTENDER)) {
			BundleCapability bundleCapability = wire.getCapability();
			Map<String, Object> attributes = bundleCapability.getAttributes();

			if (attributes.get(_OSGI_EXTENDER).equals(_JSP_TAGLIB)) {
				BundleRevision bundleRevision = wire.getProvider();
				Bundle bundle = bundleRevision.getBundle();

				if (!bundles.contains(bundle)) {
					bundles.add(bundle);
				}
			}
		}
	}

	private ServletContext getServletContextWrapper(
		ServletContext servletContext) {

		if (_jspServletContext == null) {
			synchronized (this) {
				if (_jspServletContext == null) {
					_jspServletContext = (ServletContext)Proxy.newProxyInstance(
						_jspBundleClassloader, _INTERFACES,
						new JspServletContextInvocationHandler(servletContext));
				}
			}
		}

		return _jspServletContext;
	}

	private static final Class<?>[] _INTERFACES = {
		ServletContext.class
	};
	private static final String _JSP_TAGLIB = "jsp.taglib";
	private static final String _OSGI_EXTENDER = "osgi.extender";

	private Bundle[] _allParticipatingBundles;
	private Bundle _bundle;
	private final Bundle _jspBundle;
	private JspBundleClassloader _jspBundleClassloader;
	private final HttpServlet _jspServlet =
		new org.apache.jasper.servlet.JspServlet();
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

		private URL getExtension(String path) {
			Enumeration<URL> enumeration = _bundle.findEntries(
				"META-INF/resources", path.substring(1), false);

			if (enumeration == null) {
				return null;
			}

			List<URL> urls = Collections.list(enumeration);

			return urls.get(urls.size() - 1);
		}

		private URL getResource(String path) {
			try {
				if (path.charAt(0) != '/') {
					path = '/' + path;
				}

				URL url = getExtension(path);

				if (url != null) {
					return url;
				}

				url = _servletContext.getResource(path);

				if (url != null) {
					return url;
				}

				url = _servletContext.getClassLoader().getResource(path);

				if (url != null) {
					return url;
				}

				if (!path.startsWith("/META-INF/")) {
					url = _servletContext.getResource(
						"/META-INF/resources".concat(path));
				}

				if (url != null) {
					return url;
				}

				for (int i = 2; i < _allParticipatingBundles.length; i++) {
					url = _allParticipatingBundles[i].getEntry(path);

					if (url != null) {
						return url;
					}
				}

				return _jspBundle.getEntry(path);
			}
			catch (MalformedURLException murle) {
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
			catch (IOException ioe) {
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

		private final ServletContext _servletContext;

	}

}