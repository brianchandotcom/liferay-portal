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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.jsp.compiler.internal.JspBundleClassloader;
import com.liferay.taglib.servlet.JspFactorySwapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspFactory;

import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.xmlparser.ParserUtils;
import org.apache.jasper.xmlparser.TreeNode;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 */
public class JspServlet extends HttpServlet {

	@Override
	public void destroy() {
		_jspServlet.destroy();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
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
	public void init() {
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

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			JspFactory.setDefaultFactory(new JspFactoryImpl());

			JspFactorySwapper.swap();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
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
		defaults.put("compilerSourceVM", "1.7");
		defaults.put("compilerTargetVM", "1.7");
		defaults.put("development", "false");
		defaults.put("httpMethods", "GET,POST,HEAD");
		defaults.put("keepgenerated", "false");
		defaults.put("logVerbosityLevel", "NONE");
		defaults.put("saveBytecode", "true");

		StringBundler sb = new StringBundler(4);

		sb.append(_WORK_DIR);
		sb.append(_bundle.getSymbolicName());
		sb.append(StringPool.DASH);
		sb.append(_bundle.getVersion());

		defaults.put("scratchdir", sb.toString());

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

		scanTLDs(servletContext);
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

			if (_DEBUG.equals(
					_jspServlet.getInitParameter("logVerbosityLevel"))) {

				String path = (String)request.getAttribute(
					RequestDispatcher.INCLUDE_SERVLET_PATH);

				if (path != null) {
					String pathInfo = (String)request.getAttribute(
						RequestDispatcher.INCLUDE_PATH_INFO);

					if (pathInfo != null) {
						path += pathInfo;
					}
				}
				else {
					path = request.getServletPath();

					String pathInfo = request.getPathInfo();

					if (pathInfo != null) {
						path += pathInfo;
					}
				}

				_jspServletContext.log(
					"[JSP DEBUG] " + _bundle + " invoking " + path);
			}

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

	protected void addListener(
		String listenerClassName, BundleContext bundleContext,
		ServletContext servletContext) {

		try {
			Class<?> clazz = _bundle.loadClass(listenerClassName);

			String[] classNames = getListenerClassNames(clazz);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				"osgi.http.whiteboard.context.select",
				servletContext.getServletContextName());
			properties.put(
				"osgi.http.whiteboard.listener", Boolean.TRUE.toString());

			ServiceRegistration<?> serviceRegistration =
				bundleContext.registerService(
					classNames, clazz.newInstance(), properties);

			_serviceRegistrations.add(serviceRegistration);
		}
		catch (Exception e) {
			log("Unable to create listener " + listenerClassName, e);
		}
	}

	protected void collectTaglibProviderBundles(List<Bundle> bundles) {
		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire :
				bundleWiring.getRequiredWires("osgi.extender")) {

			BundleCapability bundleCapability = bundleWire.getCapability();

			Map<String, Object> attributes = bundleCapability.getAttributes();

			Object value = attributes.get("osgi.extender");

			if (value.equals("jsp.taglib")) {
				BundleRevision bundleRevision = bundleWire.getProvider();

				Bundle bundle = bundleRevision.getBundle();

				if (!bundles.contains(bundle)) {
					bundles.add(bundle);
				}
			}
		}
	}

	protected String[] getListenerClassNames(Class<?> clazz) {
		List<String> classNames = new ArrayList<>();

		if (ServletContextListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletContextListener.class.getName());
		}

		if (ServletContextAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletContextAttributeListener.class.getName());
		}

		if (ServletRequestListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletRequestListener.class.getName());
		}

		if (ServletRequestAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(ServletRequestAttributeListener.class.getName());
		}

		if (HttpSessionListener.class.isAssignableFrom(clazz)) {
			classNames.add(HttpSessionListener.class.getName());
		}

		if (HttpSessionAttributeListener.class.isAssignableFrom(clazz)) {
			classNames.add(HttpSessionAttributeListener.class.getName());
		}

		if (classNames.isEmpty()) {
			throw new IllegalArgumentException(
				clazz.getName() + " does not implement one of the supported " +
					"servlet listener interfaces");
		}

		return classNames.toArray(new String[classNames.size()]);
	}

	protected ServletContext getServletContextWrapper(
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

	protected void scanTLDs(ServletContext servletContext) {
		Enumeration<URL> urls = _bundle.findEntries("META-INF/", "*.tld", true);

		if (urls == null) {
			return;
		}

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			try (InputStream inputStream = url.openStream()) {
				ParserUtils parserUtils = new ParserUtils(true);

				TreeNode treeNode = parserUtils.parseXMLDocument(
					url.getPath(), inputStream, false);

				Iterator<TreeNode>iterator = treeNode.findChildren("listener");

				while (iterator.hasNext()) {
					TreeNode listenerTreeNode = iterator.next();

					TreeNode listenerClassTreeNode = listenerTreeNode.findChild(
						"listener-class");

					if (listenerClassTreeNode == null) {
						continue;
					}

					String listenerClassName = listenerClassTreeNode.getBody();

					if (listenerClassName == null) {
						continue;
					}

					addListener(
						listenerClassName, _bundle.getBundleContext(),
						servletContext);
				}
			}
			catch (Exception e) {
				log(e.getMessage(), e);
			}
		}
	}

	private static final String _DEBUG = "DEBUG";

	private static final Class<?>[] _INTERFACES = {ServletContext.class};

	private static final String _WORK_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + File.separator + "work" +
			File.separator;

	private static final Bundle _jspBundle = FrameworkUtil.getBundle(
		JspServlet.class);
	private static final Pattern _originalJspPattern = Pattern.compile(
		"^(?<file>.*)(\\.(portal|original))(?<extension>\\.(jsp|jspf))$");

	private Bundle[] _allParticipatingBundles;
	private Bundle _bundle;
	private JspBundleClassloader _jspBundleClassloader;
	private final HttpServlet _jspServlet =
		new org.apache.jasper.servlet.JspServlet();
	private volatile ServletContext _jspServletContext;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

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
			Matcher matcher = _originalJspPattern.matcher(path);

			if (matcher.matches()) {
				path = matcher.group("file") + matcher.group("extension");

				return _bundle.getEntry("META-INF/resources" + path);
			}

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