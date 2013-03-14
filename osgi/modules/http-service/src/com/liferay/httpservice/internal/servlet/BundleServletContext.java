/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.internal.http.DefaultHttpContext;
import com.liferay.httpservice.internal.http.HttpServiceTracker;
import com.liferay.httpservice.servlet.BundleServletConfig;
import com.liferay.httpservice.servlet.ResourceServlet;
import com.liferay.portal.apache.bridges.struts.LiferayServletContext;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.struts.AuthPublicPathRegistry;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleServletContext extends LiferayServletContext {

	public static String getServletContextName(Bundle bundle) {
		return getServletContextName(bundle, false);
	}

	public static String getServletContextName(
		Bundle bundle, boolean generate) {

		Dictionary<String, String> headers = bundle.getHeaders();

		String webContextPath = headers.get("Web-ContextPath");

		if (Validator.isNotNull(webContextPath)) {
			return webContextPath.substring(1);
		}

		String deploymentContext = null;

		try {
			String pluginPackageXml = HttpUtil.URLtoString(
				bundle.getResource("/WEB-INF/liferay-plugin-package.xml"));

			if (pluginPackageXml != null) {
				Document document = SAXReaderUtil.read(pluginPackageXml);

				Element rootElement = document.getRootElement();

				deploymentContext = GetterUtil.getString(
					rootElement.elementText("recommended-deployment-context"));
			}
			else {
				String pluginPackageProperties = HttpUtil.URLtoString(
					bundle.getResource(
						"/WEB-INF/liferay-plugin-package.properties"));

				if (pluginPackageProperties != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Reading plugin package from " +
								"liferay-plugin-package.properties");
					}

					Properties properties = PropertiesUtil.load(
						pluginPackageProperties);

					deploymentContext = GetterUtil.getString(
						properties.getProperty(
							"recommended-deployment-context"),
						deploymentContext);
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (Validator.isNull(deploymentContext) && generate) {
			deploymentContext = PortalUtil.getJsSafePortletId(
				bundle.getSymbolicName());
		}

		if (Validator.isNotNull(deploymentContext) &&
			deploymentContext.startsWith(StringPool.SLASH)) {

			deploymentContext = deploymentContext.substring(1);
		}

		return deploymentContext;
	}

	public BundleServletContext(
		Bundle bundle, String servletContextName,
		ServletContext servletContext) {

		super(servletContext);

		_bundle = bundle;
		_servletContextName = servletContextName;

		_httpContext = new DefaultHttpContext(_bundle);
	}

	public void close() {
		_httpServiceTracker.close();

		_serviceRegistration.unregister();

		FileUtil.deltree(_tempDir);
	}

	@Override
	public Object getAttribute(String name) {
		if (name.equals(JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR)) {
			return getTempDir();
		}
		else if (name.equals("osgi-bundle")) {
			return _bundle;
		}
		else if (name.equals("osgi-bundlecontext")) {
			return _bundle.getBundleContext();
		}

		Object value = _contextAttributes.get(name);

		if (value == null) {
			if (name.equals(PluginContextListener.PLUGIN_CLASS_LOADER)) {
				return getClassLoader();
			}
			else if (name.equals("org.apache.catalina.JSP_PROPERTY_GROUPS")) {
				value = new HashMap<Object, Object>();

				_contextAttributes.put(name, value);
			}
			else if (name.equals("org.apache.tomcat.InstanceManager")) {
				return super.getAttribute(name);
			}
		}

		return value;
	}

	public Bundle getBundle() {
		return _bundle;
	}

	public ClassLoader getClassLoader() {
		ClassLoader classLoader = (ClassLoader)_contextAttributes.get(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		if (classLoader == null) {
			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			classLoader = bundleWiring.getClassLoader();

			_contextAttributes.put(
				PluginContextListener.PLUGIN_CLASS_LOADER, classLoader);
		}

		return classLoader;
	}

	public HttpContext getHttpContext() {
		return _httpContext;
	}

	@Override
	public String getInitParameter(String name) {
		return _initParameters.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_initParameters.keySet());
	}

	@Override
	public String getRealPath(String path) {
		URL url = _httpContext.getResource(path);

		if (url != null) {
			return url.toExternalForm();
		}

		return path;
	}

	@Override
	public URL getResource(String path) {
		return _httpContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		try {
			URL url = getResource(path);

			if (url != null) {
				return url.openStream();
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		Set<String> paths = new HashSet<String>();

		Enumeration<String> enumeration = _bundle.getEntryPaths(path);

		if (enumeration == null) {
			return Collections.emptySet();
		}

		while (enumeration.hasMoreElements()) {
			String entryPath = enumeration.nextElement();

			paths.add(StringPool.SLASH.concat(entryPath));
		}

		return paths;
	}

	@Override
	public Servlet getServlet(String name) {
		return _servletsByServletNames.get(name);
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	public List<ServletRequestAttributeListener>
		getServletRequestAttributeListeners() {

		return _servletRequestAttributeListeners;
	}

	public List<ServletRequestListener> getServletRequestListeners() {
		return _servletRequestListeners;
	}

	public void open() throws DocumentException {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put("bundle", _bundle);
		properties.put("bundle.id", _bundle.getBundleId());
		properties.put("bundle.symbolicName", _bundle.getSymbolicName());
		properties.put("bundle.version", _bundle.getVersion());
		properties.put(
			"Web-ContextPath", StringPool.SLASH.concat(_servletContextName));

		BundleContext bundleContext = _bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			BundleServletContext.class, this, properties);

		_httpServiceTracker = new HttpServiceTracker(bundleContext, _bundle);

		_httpServiceTracker.open();
	}

	public void registerFilter(
			String filterName, List<String> urlPatterns, Filter filter,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		validateFilter(filterName, filter, urlPatterns, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			FilterConfig filterConfig = new BundleFilterConfig(
				this, filterName, initParameters, httpContext);

			filter.init(filterConfig);

			_filtersByFilterNames.put(filterName, filter);

			if (_log.isInfoEnabled()) {
				_log.info("Registered filter " + filterName);
			}

			for (String urlPattern : urlPatterns) {
				_filtersByURLPatterns.put(urlPattern, filter);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Mapped filter " + filterName + " to " +
							getContextPath() + urlPattern);
				}
			}

			FilterServiceRanking filterServiceRanking =
				new FilterServiceRanking();

			filterServiceRanking.setFilterName(filterName);

			int serviceRanking = GetterUtil.getInteger(
				initParameters.remove("service.ranking"),
				_FILTER_SERVICE_RANKING_DEFAULT);

			filterServiceRanking.setServiceRanking(serviceRanking);

			_filterServiceRankings.add(filterServiceRanking);

			// Filters are sorted based on their service ranking value where
			// the default service ranking is 10 for those filters that do not
			// specify a specific service ranking.

			// Filters are first sorted based on the service ranking where
			// filters with a smaller service ranking appear earlier in the
			// list, and then based on when the filter was registered where
			// those that are registered earlier appear earlier in the list.

			Collections.sort(_filterServiceRankings);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void registerFilter(
			String filterName, String urlPattern, Filter filter,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		List<String> urlPatterns = Arrays.asList(urlPattern);

		registerFilter(
			filterName, urlPatterns, filter, initParameters, httpContext);
	}

	public void registerListener(
		Object listener, Map<String, String> initParameters,
		HttpContext httpContext) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			DependencyManagementThreadLocal.setEnabled(false);

			if (initParameters != null) {
				Set<Entry<String, String>> set = initParameters.entrySet();

				Iterator<Entry<String, String>> iterator = set.iterator();

				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();

					String value = entry.getValue();

					if (_initParameters.containsKey(value)) {
						continue;
					}

					_initParameters.put(entry.getKey(), value);
				}
			}

			if (listener instanceof HttpSessionActivationListener) {
				PortletSessionListenerManager.addHttpSessionActivationListener(
					(HttpSessionActivationListener)listener);
			}

			if (listener instanceof HttpSessionAttributeListener) {
				PortletSessionListenerManager.addHttpSessionAttributeListener(
					(HttpSessionAttributeListener)listener);
			}

			if (listener instanceof HttpSessionBindingListener) {
				PortletSessionListenerManager.addHttpSessionBindingListener(
					(HttpSessionBindingListener)listener);
			}

			if (listener instanceof HttpSessionListener) {
				PortletSessionListenerManager.addHttpSessionListener(
					(HttpSessionListener)listener);
			}

			if (listener instanceof ServletContextAttributeListener) {
				_servletContextAttributeListeners.add(
					(ServletContextAttributeListener)listener);
			}

			if (listener instanceof ServletContextListener) {
				ServletContextListener servletContextListener =
					(ServletContextListener)listener;

				ServletContextEvent servletContextEvent =
					new ServletContextEvent(this);

				servletContextListener.contextInitialized(servletContextEvent);

				_servletContextListeners.add(servletContextListener);
			}

			if (listener instanceof ServletRequestAttributeListener) {
				_servletRequestAttributeListeners.add(
					(ServletRequestAttributeListener)listener);
			}

			if (listener instanceof ServletRequestListener) {
				_servletRequestListeners.add((ServletRequestListener)listener);
			}
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void registerResources(
			String alias, String name, HttpContext httpContext)
		throws NamespaceException {

		Map<String, String> initParameters = new Hashtable<String, String>();

		initParameters.put("alias", alias);

		if (name == null) {
			throw new IllegalArgumentException("Name is null");
		}

		if (name.endsWith(StringPool.SLASH) && !name.equals(StringPool.SLASH)) {
			throw new IllegalArgumentException("Invalid name " + name);
		}

		initParameters.put("name", name);

		Servlet resourceServlet = new ResourceServlet();

		try {
			registerServlet(
				name, alias, resourceServlet, initParameters, httpContext);

			AuthPublicPathRegistry.register(
				Portal.PATH_MODULE + StringPool.SLASH + _servletContextName +
					alias);
		}
		catch (ServletException se) {
			throw new IllegalArgumentException(se);
		}
	}

	public void registerServlet(
			String servletName, List<String> urlPatterns, Servlet servlet,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		validateServlet(servletName, servlet, urlPatterns, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			ServletConfig servletConfig = new BundleServletConfig(
				this, servletName, initParameters, httpContext);

			servlet.init(servletConfig);

			_servletsByServletNames.put(servletName, servlet);

			if (_log.isInfoEnabled()) {
				_log.info("Registered servlet " + servletName);
			}

			for (String urlPattern : urlPatterns) {
				_servletsByURLPatterns.put(urlPattern, servlet);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Mapped servlet " + servletName + " to " +
							getContextPath() + urlPattern);
				}
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void registerServlet(
			String servletName, String urlPattern, Servlet servlet,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		List<String> urlPatterns = Arrays.asList(urlPattern);

		registerServlet(
			servletName, urlPatterns, servlet, initParameters, httpContext);
	}

	@Override
	public void removeAttribute(String name) {
		Object value = _contextAttributes.remove(name);

		for (ServletContextAttributeListener servletContextAttributeListener :
				_servletContextAttributeListeners) {

			servletContextAttributeListener.attributeRemoved(
				new ServletContextAttributeEvent(this, name, value));
		}
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public void unregisterFilter(String filterName) {
		Filter filter = _filtersByFilterNames.remove(filterName);

		if (filter == null) {
			return;
		}

		filter.destroy();

		unregisterFilterByServiceRanking(filterName);
		unregisterFilterByURLMapping(filterName, filter);
	}

	public void unregisterListener(Object listener) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			DependencyManagementThreadLocal.setEnabled(false);

			if (listener instanceof HttpSessionActivationListener) {
				PortletSessionListenerManager.
					removeHttpSessionActivationListener(
						(HttpSessionActivationListener)listener);
			}

			if (listener instanceof HttpSessionAttributeListener) {
				PortletSessionListenerManager.
					removeHttpSessionAttributeListener(
						(HttpSessionAttributeListener)listener);
			}

			if (listener instanceof HttpSessionBindingListener) {
				PortletSessionListenerManager.removeHttpSessionBindingListener(
					(HttpSessionBindingListener)listener);
			}

			if (listener instanceof HttpSessionListener) {
				PortletSessionListenerManager.removeHttpSessionListener(
					(HttpSessionListener)listener);
			}

			if (listener instanceof ServletContextAttributeListener) {
				_servletContextAttributeListeners.remove(listener);
			}

			if (listener instanceof ServletContextListener) {
				if (_servletContextListeners.contains(listener)) {
					_servletContextListeners.remove(listener);

					ServletContextListener servletContextListener =
						(ServletContextListener)listener;

					ServletContextEvent servletContextEvent =
						new ServletContextEvent(this);

					servletContextListener.contextDestroyed(
						servletContextEvent);
				}
			}

			if (listener instanceof ServletRequestAttributeListener) {
				_servletRequestAttributeListeners.remove(listener);
			}

			if (listener instanceof ServletRequestListener) {
				_servletRequestListeners.remove(listener);
			}
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void unregisterServlet(String servletName) {
		Servlet servlet = _servletsByServletNames.remove(servletName);

		if (servlet == null) {
			return;
		}

		servlet.destroy();

		Set<Map.Entry<String, Servlet>> set = _servletsByURLPatterns.entrySet();

		Iterator<Map.Entry<String, Servlet>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Servlet> entry = iterator.next();

			Servlet curServlet = entry.getValue();

			if (curServlet != servlet) {
				continue;
			}

			AuthPublicPathRegistry.unregister(
				Portal.PATH_MODULE + StringPool.SLASH + _servletContextName +
					entry.getKey());

			iterator.remove();

			if (_log.isInfoEnabled()) {
				String urlPattern = entry.getKey();

				_log.info(
					"Unmapped servlet " + servletName + " from " + urlPattern);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Unregistered servlet " + servletName);
		}
	}

	protected File getTempDir() {
		if (_tempDir != null) {
			return _tempDir;
		}

		File parentTempDir = (File)super.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		File tempDir = new File(parentTempDir, _servletContextName);

		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new IllegalStateException(
				"Unable to make temporary directory " + tempDir);
		}

		_tempDir = tempDir;

		return _tempDir;
	}

	protected void unregisterFilterByServiceRanking(String filterName) {
		Iterator<FilterServiceRanking> iterator =
			_filterServiceRankings.iterator();

		while (iterator.hasNext()) {
			FilterServiceRanking filterServiceRanking = iterator.next();

			if (!filterName.equals(filterServiceRanking.getFilterName())) {
				continue;
			}

			iterator.remove();
		}
	}

	protected void unregisterFilterByURLMapping(
		String filterName, Filter filter) {

		Set<Map.Entry<String, Filter>> set = _filtersByURLPatterns.entrySet();

		Iterator<Map.Entry<String, Filter>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Filter> entry = iterator.next();

			Filter curFilter = entry.getValue();

			if (curFilter != filter) {
				continue;
			}

			iterator.remove();

			if (_log.isInfoEnabled()) {
				String urlPattern = entry.getKey();

				_log.info(
					"Unmapped filter " + filterName + " from " + urlPattern);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Unregistered filter " + filterName);
		}
	}

	protected void validateFilter(
			String filterName, Filter filter, List<String> urlPatterns,
			HttpContext httpContext)
		throws NamespaceException {

		if (filter == null) {
			throw new IllegalArgumentException("Filter must not be null");
		}

		Filter registeredFilter = _filtersByFilterNames.get(filterName);

		if ((registeredFilter != null) && (registeredFilter != filter)) {
			throw new NamespaceException(
				"A filter is already registered with the name " + filterName);
		}

		for (String urlPattern : urlPatterns) {
			validateURLPattern(urlPattern);
		}
	}

	protected void validateServlet(
			String servletName, Servlet servlet, List<String> urlPatterns,
			HttpContext httpContext)
		throws NamespaceException {

		if (servlet == null) {
			throw new IllegalArgumentException("Servlet must not be null");
		}

		Servlet registeredServlet = _servletsByServletNames.get(servletName);

		if ((registeredServlet != null) && (registeredServlet != servlet)) {
			throw new NamespaceException(
				"A servlet is already registered with the name " + servletName);
		}

		for (String urlPattern : urlPatterns) {
			validateURLPattern(urlPattern);

			if (_servletsByURLPatterns.containsKey(urlPattern)) {
				throw new NamespaceException(
					"A servlet is already registered with the URL pattern " +
						urlPattern);
			}
		}
	}

	protected void validateURLPattern(String urlPattern) {
		if (Validator.isNull(urlPattern)) {
			throw new IllegalArgumentException(
				"An empty URL pattern is not allowed");
		}

		if (!urlPattern.startsWith(StringPool.SLASH) ||
			(urlPattern.endsWith(StringPool.SLASH) &&
			 !urlPattern.equals(StringPool.SLASH))) {

			throw new IllegalArgumentException(
				"URL patterns must start with / but cannot end with it");
		}
	}

	private static final int _FILTER_SERVICE_RANKING_DEFAULT = 10;

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private Map<String, Object> _contextAttributes =
		new ConcurrentHashMap<String, Object>();
	private Map<String, Filter> _filtersByFilterNames =
		new ConcurrentHashMap<String, Filter>();
	private Map<String, Filter> _filtersByURLPatterns =
		new ConcurrentHashMap<String, Filter>();
	private List<FilterServiceRanking> _filterServiceRankings =
		new CopyOnWriteArrayList<FilterServiceRanking>();
	private HttpContext _httpContext;
	private HttpServiceTracker _httpServiceTracker;
	private Map<String, String> _initParameters = new HashMap<String, String>();
	private ServiceRegistration<BundleServletContext> _serviceRegistration;
	private List<ServletContextAttributeListener>
		_servletContextAttributeListeners =
			new ArrayList<ServletContextAttributeListener>();
	private List<ServletContextListener> _servletContextListeners =
		new ArrayList<ServletContextListener>();
	private String _servletContextName;
	private List<ServletRequestAttributeListener>
		_servletRequestAttributeListeners =
			new ArrayList<ServletRequestAttributeListener>();
	private List<ServletRequestListener> _servletRequestListeners =
		new ArrayList<ServletRequestListener>();
	private Map<String, Servlet> _servletsByServletNames =
		new ConcurrentHashMap<String, Servlet>();
	private Map<String, Servlet> _servletsByURLPatterns =
		new ConcurrentHashMap<String, Servlet>();
	private File _tempDir;

	private class FilterServiceRanking
		implements Comparable<FilterServiceRanking> {

		public String getFilterName() {
			return _filterName;
		}

		public int getServiceRanking() {
			return _serviceRanking;
		}

		public long getTimestamp() {
			return _timestamp;
		}

		public int compareTo(FilterServiceRanking filterServiceRanking) {
			if (_serviceRanking <
					filterServiceRanking.getServiceRanking()) {

				return -1;
			}
			else if (_serviceRanking >
						filterServiceRanking.getServiceRanking()) {

				return 1;
			}

			if (_timestamp < filterServiceRanking.getTimestamp()) {
				return -1;
			}
			else if (_timestamp > filterServiceRanking.getTimestamp()) {
				return 1;
			}

			return 0;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof FilterServiceRanking)) {
				return false;
			}

			FilterServiceRanking filterServiceRanking =
				(FilterServiceRanking)object;

			if (Validator.equals(
					_filterName, filterServiceRanking._filterName) &&
				Validator.equals(
					_serviceRanking, filterServiceRanking._serviceRanking)) {

				return true;
			}

			return false;
		}

		public void setFilterName(String filterName) {
			_filterName = filterName;
		}

		public void setServiceRanking(int serviceRanking) {
			_serviceRanking = serviceRanking;
		}

		private String _filterName;
		private int _serviceRanking;
		private long _timestamp = System.currentTimeMillis();

	}

}