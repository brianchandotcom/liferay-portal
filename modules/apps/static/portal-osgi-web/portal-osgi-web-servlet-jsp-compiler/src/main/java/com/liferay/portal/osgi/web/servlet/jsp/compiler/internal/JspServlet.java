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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.servlet.JspFactorySwapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspFactory;

import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.runtime.TagHandlerPool;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;

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

		bundles.add(_utilTaglibBundle);

		collectTaglibProviderBundles(bundles);

		_allParticipatingBundles = bundles.toArray(new Bundle[0]);

		_jspBundleClassloader = new JspBundleClassloader(
			_allParticipatingBundles);

		StringBundler sb = new StringBundler(4);

		sb.append(_WORK_DIR);
		sb.append(_bundle.getSymbolicName());
		sb.append(StringPool.DASH);
		sb.append(_bundle.getVersion());

		final Map<String, String> defaults = HashMapBuilder.put(
			_INIT_PARAMETER_NAME_SCRATCH_DIR, sb.toString()
		).put(
			"compilerClassName",
			"com.liferay.portal.osgi.web.servlet.jsp.compiler.internal." +
				"JspCompiler"
		).put(
			"compilerSourceVM", "1.8"
		).put(
			"compilerTargetVM", "1.8"
		).put(
			"development", String.valueOf(PropsValues.WORK_DIR_OVERRIDE_ENABLED)
		).put(
			"httpMethods", "GET,POST,HEAD"
		).put(
			"jspCompilerClassName",
			"com.liferay.portal.osgi.web.servlet.jsp.compiler.internal." +
				"CompilerWrapper"
		).put(
			"keepgenerated", "false"
		).put(
			"logVerbosityLevel", "NONE"
		).put(
			"saveBytecode", "true"
		).build();

		String symbolicName = _bundle.getSymbolicName();

		BundleTracker<Bundle> bundleTracker = new BundleTracker(
			_bundle.getBundleContext(), ~Bundle.UNINSTALLED, null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				Dictionary<String, String> dictionary = bundle.getHeaders(
					StringPool.BLANK);

				String fragmentHost = dictionary.get(Constants.FRAGMENT_HOST);

				if (fragmentHost != null) {
					int index = fragmentHost.indexOf(StringPool.SEMICOLON);

					if (index != -1) {
						fragmentHost = fragmentHost.substring(0, index);
					}

					if (fragmentHost.equals(symbolicName)) {
						Enumeration<URL> enumeration = bundle.findEntries(
							"META-INF/resources", "*.jsp", true);

						if (enumeration != null) {
							defaults.put("hasFragment", "true");

							close();
						}
					}
				}

				return bundle;
			}

		};

		bundleTracker.open();

		bundleTracker.close();

		defaults.put(
			TagHandlerPool.OPTION_TAGPOOL, JspTagHandlerPool.class.getName());

		for (Map.Entry<Object, Object> entry : _initParams.entrySet()) {
			defaults.put(
				String.valueOf(entry.getKey()),
				String.valueOf(entry.getValue()));
		}

		Enumeration<String> names = servletConfig.getInitParameterNames();

		Set<String> nameSet = new HashSet<>(Collections.list(names));

		nameSet.addAll(defaults.keySet());

		final Enumeration<String> initParameterNames = Collections.enumeration(
			nameSet);

		_jspServlet.init(
			new ServletConfig() {

				@Override
				public String getInitParameter(String name) {
					String value = servletConfig.getInitParameter(name);

					if (value == null) {
						value = defaults.get(name);
					}

					return value;
				}

				@Override
				public Enumeration<String> getInitParameterNames() {
					return initParameterNames;
				}

				@Override
				public ServletContext getServletContext() {
					return _jspServletContext;
				}

				@Override
				public String getServletName() {
					return servletConfig.getServletName();
				}

				private final ServletContext _jspServletContext =
					new ServletContextWrapper(servletContext);

			});

		_logVerbosityLevelDebug = Objects.equals(
			_jspServlet.getInitParameter("logVerbosityLevel"), "DEBUG");
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_jspBundleClassloader);

			if (_logVerbosityLevelDebug) {
				String path = (String)httpServletRequest.getAttribute(
					RequestDispatcher.INCLUDE_SERVLET_PATH);

				if (path != null) {
					String pathInfo = (String)httpServletRequest.getAttribute(
						RequestDispatcher.INCLUDE_PATH_INFO);

					if (pathInfo != null) {
						path += pathInfo;
					}
				}
				else {
					path = httpServletRequest.getServletPath();

					String pathInfo = httpServletRequest.getPathInfo();

					if (pathInfo != null) {
						path += pathInfo;
					}
				}

				_jspServlet.log(
					StringBundler.concat(
						"[JSP DEBUG] ", _bundle, " invoking ", path));
			}

			_jspServlet.service(httpServletRequest, httpServletResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		service(
			(HttpServletRequest)servletRequest,
			(HttpServletResponse)servletResponse);
	}

	@Override
	public String toString() {
		return _jspServlet.toString();
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

		return classNames.toArray(new String[0]);
	}

	private static final String _DIR_NAME_RESOURCES = "/META-INF/resources";

	private static final String _INIT_PARAMETER_NAME_SCRATCH_DIR = "scratchdir";

	private static final String _WORK_DIR = StringBundler.concat(
		PropsValues.LIFERAY_HOME, File.separator, "work", File.separator);

	private static final Properties _initParams = PropsUtil.getProperties(
		"jsp.servlet.init.param.", true);
	private static final Bundle _jspBundle = FrameworkUtil.getBundle(
		JspServlet.class);
	private static final Pattern _originalJspPattern = Pattern.compile(
		"^(?<file>.*)(\\.(portal|original))(?<extension>\\.(jsp|jspf))$");
	private static final Bundle _utilTaglibBundle = FrameworkUtil.getBundle(
		JspFactorySwapper.class);

	private Bundle[] _allParticipatingBundles;
	private Bundle _bundle;
	private JspBundleClassloader _jspBundleClassloader;
	private final HttpServlet _jspServlet =
		new org.apache.jasper.servlet.JspServlet();
	private boolean _logVerbosityLevelDebug;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

	private class ServletContextWrapper implements ServletContext {

		@Override
		public FilterRegistration.Dynamic addFilter(
			String filterName, Class<? extends Filter> filterClass) {

			return _servletContext.addFilter(filterName, filterClass);
		}

		@Override
		public FilterRegistration.Dynamic addFilter(
			String filterName, Filter filter) {

			return _servletContext.addFilter(filterName, filter);
		}

		@Override
		public FilterRegistration.Dynamic addFilter(
			String filterName, String className) {

			return _servletContext.addFilter(filterName, className);
		}

		@Override
		public void addListener(Class<? extends EventListener> listenerClass) {
			_servletContext.addListener(listenerClass);
		}

		@Override
		public void addListener(String className) {
			_servletContext.addListener(className);
		}

		@Override
		public <T extends EventListener> void addListener(T listener) {
			_servletContext.addListener(listener);
		}

		@Override
		public ServletRegistration.Dynamic addServlet(
			String servletName, Class<? extends Servlet> servletClass) {

			return _servletContext.addServlet(servletName, servletClass);
		}

		@Override
		public ServletRegistration.Dynamic addServlet(
			String servletName, Servlet servlet) {

			return _servletContext.addServlet(servletName, servlet);
		}

		@Override
		public ServletRegistration.Dynamic addServlet(
			String servletName, String className) {

			return _servletContext.addServlet(servletName, className);
		}

		@Override
		public <T extends Filter> T createFilter(Class<T> clazz)
			throws ServletException {

			return _servletContext.createFilter(clazz);
		}

		@Override
		public <T extends EventListener> T createListener(Class<T> clazz)
			throws ServletException {

			return _servletContext.createListener(clazz);
		}

		@Override
		public <T extends Servlet> T createServlet(Class<T> clazz)
			throws ServletException {

			return _servletContext.createServlet(clazz);
		}

		@Override
		public void declareRoles(String... roleNames) {
			_servletContext.declareRoles(roleNames);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ServletContext)) {
				return false;
			}

			ServletContext servletContext = (ServletContext)obj;

			if (obj instanceof ServletContextWrapper) {
				ServletContextWrapper servletContextWrapper =
					(ServletContextWrapper)obj;

				servletContext = servletContextWrapper._servletContext;
			}

			return servletContext.equals(_servletContext);
		}

		@Override
		public Object getAttribute(String name) {
			return _servletContext.getAttribute(name);
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return _servletContext.getAttributeNames();
		}

		@Override
		public ClassLoader getClassLoader() {
			return _jspBundleClassloader;
		}

		@Override
		public ServletContext getContext(String uripath) {
			return _servletContext.getContext(uripath);
		}

		@Override
		public String getContextPath() {
			return _contextPath;
		}

		@Override
		public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
			return _servletContext.getDefaultSessionTrackingModes();
		}

		@Override
		public int getEffectiveMajorVersion() {
			return _servletContext.getEffectiveMajorVersion();
		}

		@Override
		public int getEffectiveMinorVersion() {
			return _servletContext.getEffectiveMinorVersion();
		}

		@Override
		public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
			return _servletContext.getEffectiveSessionTrackingModes();
		}

		@Override
		public FilterRegistration getFilterRegistration(String filterName) {
			return _servletContext.getFilterRegistration(filterName);
		}

		@Override
		public Map<String, ? extends FilterRegistration>
			getFilterRegistrations() {

			return _servletContext.getFilterRegistrations();
		}

		@Override
		public String getInitParameter(String name) {
			return _servletContext.getInitParameter(name);
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return _servletContext.getInitParameterNames();
		}

		@Override
		public JspConfigDescriptor getJspConfigDescriptor() {
			return _servletContext.getJspConfigDescriptor();
		}

		@Override
		public int getMajorVersion() {
			return _servletContext.getMajorVersion();
		}

		@Override
		public String getMimeType(String file) {
			return _servletContext.getMimeType(file);
		}

		@Override
		public int getMinorVersion() {
			return _servletContext.getMinorVersion();
		}

		@Override
		public RequestDispatcher getNamedDispatcher(String name) {
			return _servletContext.getNamedDispatcher(name);
		}

		@Override
		public String getRealPath(String path) {
			return _servletContext.getRealPath(path);
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return _servletContext.getRequestDispatcher(path);
		}

		@Override
		public URL getResource(String path) {
			try {
				if ((path == null) || path.equals(StringPool.BLANK)) {
					return null;
				}

				if (path.charAt(0) != '/') {
					path = '/' + path;
				}

				URL url = _getExtension(path);

				if (url != null) {
					return url;
				}

				url = _servletContext.getResource(path);

				if (url != null) {
					return url;
				}

				ClassLoader classLoader = _servletContext.getClassLoader();

				url = classLoader.getResource(path);

				if (url != null) {
					return url;
				}

				if (!path.startsWith("/META-INF/")) {
					url = _servletContext.getResource(
						_DIR_NAME_RESOURCES.concat(path));
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

				return _jspBundle.getResource(path);
			}
			catch (MalformedURLException murle) {
			}

			return null;
		}

		@Override
		public InputStream getResourceAsStream(String path) {
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

		@Override
		public Set<String> getResourcePaths(String path) {
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

		@Override
		public String getServerInfo() {
			return _servletContext.getServerInfo();
		}

		/**
		 * @deprecated As of Judson (7.1.x)
		 */
		@Deprecated
		@Override
		public Servlet getServlet(String name) throws ServletException {
			return _servletContext.getServlet(name);
		}

		@Override
		public String getServletContextName() {
			return _servletContextName;
		}

		/**
		 * @deprecated As of Judson (7.1.x)
		 */
		@Deprecated
		@Override
		public Enumeration<String> getServletNames() {
			return _servletContext.getServletNames();
		}

		@Override
		public ServletRegistration getServletRegistration(String servletName) {
			return _servletContext.getServletRegistration(servletName);
		}

		@Override
		public Map<String, ? extends ServletRegistration>
			getServletRegistrations() {

			return _servletContext.getServletRegistrations();
		}

		/**
		 * @deprecated As of Judson (7.1.x)
		 */
		@Deprecated
		@Override
		public Enumeration<Servlet> getServlets() {
			return _servletContext.getServlets();
		}

		@Override
		public SessionCookieConfig getSessionCookieConfig() {
			return _servletContext.getSessionCookieConfig();
		}

		@Override
		public int hashCode() {
			return _servletContext.hashCode();
		}

		/**
		 * @deprecated As of Judson (7.1.x)
		 */
		@Deprecated
		@Override
		public void log(Exception exception, String message) {
			_servletContext.log(exception, message);
		}

		@Override
		public void log(String message) {
			_servletContext.log(message);
		}

		@Override
		public void log(String message, Throwable throwable) {
			_servletContext.log(message, throwable);
		}

		@Override
		public void removeAttribute(String name) {
			_servletContext.removeAttribute(name);
		}

		@Override
		public void setAttribute(String name, Object value) {
			_servletContext.setAttribute(name, value);
		}

		@Override
		public boolean setInitParameter(String name, String value) {
			return _servletContext.setInitParameter(name, value);
		}

		@Override
		public void setSessionTrackingModes(
			Set<SessionTrackingMode> sessionTrackingModes) {

			_servletContext.setSessionTrackingModes(sessionTrackingModes);
		}

		@Override
		public String toString() {
			return _servletContext.toString();
		}

		private ServletContextWrapper(ServletContext servletContext) {
			_servletContext = servletContext;

			_contextPath = servletContext.getContextPath();
			_servletContextName = servletContext.getServletContextName();
		}

		private URL _getExtension(String path) {
			Matcher matcher = _originalJspPattern.matcher(path);

			if (matcher.matches()) {
				path = matcher.group("file") + matcher.group("extension");

				return _bundle.getEntry(_DIR_NAME_RESOURCES + path);
			}

			Enumeration<URL> enumeration = _bundle.findEntries(
				_DIR_NAME_RESOURCES, path.substring(1), false);

			if (enumeration == null) {
				return null;
			}

			List<URL> urls = Collections.list(enumeration);

			return urls.get(urls.size() - 1);
		}

		private final String _contextPath;
		private final ServletContext _servletContext;
		private final String _servletContextName;

	}

}