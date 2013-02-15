/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.httpservice.HttpServicePropsKeys;
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
import com.liferay.portal.kernel.util.StringBundler;
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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
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

		String webContextPath = headers.get(
			HttpServicePropsKeys.WEB_CONTEXTPATH);

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
		WebExtenderServlet webExtenderServlet) {

		super(webExtenderServlet.getServletContext());

		_bundle = bundle;
		_servletContextName = servletContextName;

		_httpContext = new DefaultHttpContext(_bundle);
	}

	public void close() {
		_httpServiceTracker.close();

		_servletContextRegistration.unregister();

		FileUtil.deltree(_tempDir);
	}

	@Override
	public Object getAttribute(String name) {
		if (name.equals("osgi-bundle")) {
			return _bundle;
		}
		else if (name.equals("osgi-bundlecontext")) {

			// This is required to meet OSGi Comp 4.3, WAS 128.6.1.

			return _bundle.getBundleContext();
		}
		else if (name.equals(JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR)) {
			return getTempDir();
		}

		Object value = _contextAttributes.get(name);

		if (value == null) {
			if (name.equals(PluginContextListener.PLUGIN_CLASS_LOADER)) {
				return getClassLoader();
			}
			else if (name.equals("org.apache.catalina.JSP_PROPERTY_GROUPS")) {
				value = new HashMap<String, Object>();

				_contextAttributes.put(name, value);
			}
			else if (name.equals("org.apache.tomcat.InstanceManager")) {
				return super.getAttribute(name);
			}
		}

		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration<String> getAttributeNames() {
		Set<String> attributeNames = new HashSet<String>(
			_contextAttributes.keySet());

		for (Enumeration<String> names = super.getAttributeNames();
				names.hasMoreElements();) {

			attributeNames.add(names.nextElement());
		}

		return Collections.enumeration(attributeNames);
	}

	public Bundle getBundle() {
		return _bundle;
	}

	public ClassLoader getClassLoader() {
		Object value = _contextAttributes.get(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		if (value == null) {
			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			value = bundleWiring.getClassLoader();

			_contextAttributes.put(
				PluginContextListener.PLUGIN_CLASS_LOADER, value);
		}

		return (ClassLoader)value;
	}

	@Override
	public String getContextPath() {
		if (_contextPath == null) {
			StringBundler sb = new StringBundler(4);

			String contextPath = super.getContextPath();

			if (!contextPath.equals(StringPool.SLASH)) {
				sb.append(contextPath);
			}

			sb.append(PortalUtil.getPathContext());
			sb.append(Portal.PATH_MODULE);
			sb.append(getServletContextName());

			_contextPath = sb.toString();
		}

		return _contextPath;
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
	public String getMimeType(String name) {
		return super.getMimeType(name);
	}

	@Override
	public String getRealPath(String path) {
		URL resourceURL = _httpContext.getResource(path);

		if (resourceURL != null) {
			return resourceURL.toExternalForm();
		}

		return path;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		String pathContext = PortalUtil.getPathContext();

		String contextPath = getContextPath();

		if (Validator.isNotNull(pathContext) &&
			contextPath.startsWith(pathContext)) {

			contextPath = contextPath.substring(pathContext.length());
		}

		if (path.startsWith(Portal.PATH_MODULE) &&
				path.startsWith(contextPath)) {

			path = path.substring(contextPath.length());
		}

		if (Validator.isNull(path)) {
			path = StringPool.SLASH;
		}

		String alias = path;

		if (!isValidPath(alias)) {
			return null;
		}

		BundleFilterChain bundleFilterChain = getFilterChain(alias);

		if (_servletsMap.containsKey(alias)) {
			bundleFilterChain.setServlet(_servletsMap.get(alias));

			return new BundleRequestDispatcher(
				alias, false, path, this, bundleFilterChain);
		}

		boolean isExtensionMapping = false;
		String extensionMapping = FileUtil.getExtension(alias).toLowerCase();

		if (Validator.isNotNull(extensionMapping)) {
			extensionMapping = _EXTENSION_PREFIX.concat(extensionMapping);
			isExtensionMapping = true;
		}

		alias = alias.substring(0, alias.lastIndexOf(StringPool.SLASH));

		while (alias.length() != 0) {
			if (_servletsMap.containsKey(alias)) {
				bundleFilterChain.setServlet(_servletsMap.get(alias));

				return new BundleRequestDispatcher(
					alias, false, path, this, bundleFilterChain);
			}
			else if (_servletsMap.containsKey(alias.concat(extensionMapping))) {
				bundleFilterChain.setServlet(
					_servletsMap.get(alias.concat(extensionMapping)));

				return new BundleRequestDispatcher(
					alias.concat(extensionMapping), true, path, this,
					bundleFilterChain);
			}

			alias = alias.substring(0, alias.lastIndexOf(StringPool.SLASH));
		}

		if (_servletsMap.containsKey(
				StringPool.SLASH.concat(extensionMapping))) {

			bundleFilterChain.setServlet(
				_servletsMap.get(StringPool.SLASH.concat(extensionMapping)));

			return new BundleRequestDispatcher(
				StringPool.SLASH.concat(extensionMapping), isExtensionMapping,
				path, this, bundleFilterChain);
		}

		if (_servletsMap.containsKey(StringPool.SLASH)) {
			bundleFilterChain.setServlet(_servletsMap.get(StringPool.SLASH));

			return new BundleRequestDispatcher(
				StringPool.SLASH, false, path, this, bundleFilterChain);
		}

		return null;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return _httpContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		try {
			URL resourceURL = getResource(path);

			if (resourceURL != null) {
				return resourceURL.openStream();
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		Set<String> resourcePaths = new HashSet<String>();

		Enumeration<String> resources = _bundle.getEntryPaths(path);

		if ((resources != null) && resources.hasMoreElements()) {
			while (resources.hasMoreElements()) {
				String resourcePath = resources.nextElement();

				resourcePaths.add(StringPool.SLASH.concat(resourcePath));
			}
		}

		return resourcePaths;
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

		properties.put(
			HttpServicePropsKeys.BUNDLE_SYMBOLICNAME,
			_bundle.getSymbolicName());
		properties.put(HttpServicePropsKeys.BUNDLE_ID, _bundle.getBundleId());
		properties.put("bundle", _bundle);
		properties.put(
			HttpServicePropsKeys.BUNDLE_VERSION, _bundle.getVersion());
		properties.put(
			HttpServicePropsKeys.WEB_CONTEXTPATH,
			StringPool.SLASH.concat(_servletContextName));

		BundleContext bundleContext = _bundle.getBundleContext();

		_servletContextRegistration = bundleContext.registerService(
			BundleServletContext.class, this, properties);

		_httpServiceTracker = new HttpServiceTracker(bundleContext, _bundle);

		_httpServiceTracker.open();
	}

	public void registerFilter(
			String filterMapping, Filter filter, Map<String, String> initParams,
			HttpContext httpContext)
		throws NamespaceException, ServletException {

		validate(filterMapping, filter, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		int serviceRanking = GetterUtil.getInteger(
			initParams.remove(HttpServicePropsKeys.SERVICE_RANKING));

		try {
			currentThread.setContextClassLoader(getClassLoader());

			FilterConfig filterConfig = new BundleFilterConfig(
				this, filterMapping, initParams, httpContext);

			filter.init(filterConfig);

			_filtersMap.put(filterMapping, filter);

			if ((serviceRanking <= 0) && !_filterTreeMap.isEmpty()) {
				serviceRanking = _filterTreeMap.lastKey() + 1;
			}

			_filterTreeMap.put(
				serviceRanking, new Object[] {filterMapping, filter});
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void registerListener(
			Object listener, Map<String, String> initParams,
			HttpContext httpContext)
		throws ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			DependencyManagementThreadLocal.setEnabled(false);

			for (Map.Entry<String, String> entry : initParams.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();

				if (_initParameters.containsKey(key)) {
					continue;
				}

				_initParameters.put(key, value);
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

		validate(name);

		Servlet servlet = new ResourceServlet();

		Hashtable<String, String> initParams = new Hashtable<String, String>();

		initParams.put(HttpServicePropsKeys.ALIAS, alias);
		initParams.put(HttpServicePropsKeys.NAME, name);

		try {
			registerServlet(alias, servlet, initParams, httpContext);

			StringBundler sb = new StringBundler(3);

			sb.append(Portal.PATH_MODULE);
			sb.append(getServletContextName());
			sb.append(alias);

			AuthPublicPathRegistry.register(sb.toString());
		}
		catch (ServletException se) {
			throw new IllegalArgumentException(se);
		}
	}

	public void registerServlet(
			String alias, Servlet servlet,
			Dictionary<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		validate(alias, servlet, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			ServletConfig servletConfig = new BundleServletConfig(
				this, alias, initParameters, httpContext);

			servlet.init(servletConfig);

			_servletsMap.put(alias, servlet);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Registered servlet at " + getContextPath().concat(alias));
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void removeAttribute(String name) {
		Object value = _contextAttributes.remove(name);

		for (ServletContextAttributeListener listener :
				_servletContextAttributeListeners) {

			listener.attributeRemoved(
				new ServletContextAttributeEvent(this, name, value));
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (name.equals(JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR) ||
			name.equals(PluginContextListener.PLUGIN_CLASS_LOADER)) {

			return;
		}

		Object originalValue = _contextAttributes.get(name);

		_contextAttributes.put(name, value);

		for (ServletContextAttributeListener listener :
				_servletContextAttributeListeners) {

			if (originalValue != null) {
				listener.attributeReplaced(
					new ServletContextAttributeEvent(
						this, name, originalValue));
			}
			else {
				listener.attributeAdded(
					new ServletContextAttributeEvent(this, name, value));
			}
		}
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public void unregister(String alias) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			Servlet servlet = _servletsMap.get(alias);

			if (servlet == null) {
				return;
			}

			StringBundler sb = new StringBundler(3);

			sb.append(Portal.PATH_MODULE);
			sb.append(getServletContextName());
			sb.append(alias);

			AuthPublicPathRegistry.unregister(sb.toString());

			servlet.destroy();

			_servletsMap.remove(servlet);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void unregisterFilter(String filterMapping) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			Filter filter = _filtersMap.get(filterMapping);

			if (filter == null) {
				return;
			}

			filter.destroy();

			_filtersMap.remove(filter);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
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

	protected BundleFilterChain getFilterChain(String alias) {
		BundleFilterChain bundleFilterChain = new BundleFilterChain();

		for (Object[] filterDefinition : _filterTreeMap.values()) {
			String filterMapping = (String)filterDefinition[0];
			Filter filter = (Filter)filterDefinition[1];

			if (filterMapping.equals(alias)) {
				bundleFilterChain.addFilter(filter);
			}

			if (filterMapping.contains(StringPool.STAR)) {
				filterMapping = filterMapping.replaceAll(
					"\\".concat(StringPool.STAR), ".*");
			}

			if (alias.matches(filterMapping)) {
				bundleFilterChain.addFilter(filter);
			}
		}

		return bundleFilterChain;
	}

	protected File getTempDir() {
		if (_tempDir == null) {
			File parentTempDir = (File)super.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			String servletContextName = getServletContextName();

			File tempDir = new File(parentTempDir, servletContextName);

			if (!tempDir.exists() && !tempDir.mkdirs()) {
				throw new IllegalStateException(
					"Can't create webapp tempDir for " +
						getServletContextName());
			}

			_tempDir = tempDir;
		}

		return _tempDir;
	}

	protected boolean isValidPath(String path) {
		if (!path.startsWith(StringPool.SLASH)) {
			path = StringPool.SLASH.concat(path);
		}

		for (String illegalPath : _ILLEGAL_PATHS) {
			if (path.startsWith(illegalPath)) {
				return false;
			}
		}

		return true;
	}

	protected void validate(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}

		if (name.endsWith(StringPool.SLASH) && !name.equals(StringPool.SLASH)) {
			throw new IllegalArgumentException("Invalid name " + name);
		}
	}

	protected void validate(
			String filterMapping, Filter filter, HttpContext httpContext)
		throws NamespaceException {

		if (filterMapping == null) {
			throw new IllegalArgumentException("FilterMapping cannot be null");
		}

		if (filterMapping.endsWith(StringPool.SLASH) &&
			!filterMapping.equals(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				"Invalid filterMapping " + filterMapping);
		}

		if (filter == null) {
			throw new IllegalArgumentException("Filter must not be null");
		}

		if (_filtersMap.containsValue(filter)) {
			throw new IllegalArgumentException("Filter is already registered");
		}

		if (httpContext == null) {
			throw new IllegalArgumentException("HttpContext cannot be null");
		}
	}

	protected void validate(
			String alias, Servlet servlet, HttpContext httpContext)
		throws NamespaceException {

		if (Validator.isNull(alias)) {
			throw new IllegalArgumentException("Empty aliases are not allowed");
		}

		if (!alias.startsWith(StringPool.SLASH) ||
			(alias.endsWith(StringPool.SLASH) &&
			 !alias.equals(StringPool.SLASH))) {

			throw new IllegalArgumentException(
				"Alias must start with / but must not end with it");
		}

		if (_servletsMap.containsKey(alias)) {
			throw new NamespaceException("Alias " + alias + " already exists");
		}

		if (servlet == null) {
			throw new IllegalArgumentException("Servlet must not be null");
		}

		if (_servletsMap.containsValue(servlet)) {
			throw new IllegalArgumentException("Servlet is already registered");
		}

		if (httpContext == null) {
			throw new IllegalArgumentException("HttpContext cannot be null");
		}
	}

	private static final String _EXTENSION_PREFIX = "*.";

	private static final String[] _ILLEGAL_PATHS = new String[] {
		"/OSGI-INF/", "/META-INF/", "/OSGI-OPT/", "/WEB-INF/"
	};

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private Map<String, Object> _contextAttributes =
		new ConcurrentHashMap<String, Object>();
	private String _contextPath;
	private Map<String, Filter> _filtersMap =
		new ConcurrentHashMap<String, Filter>();
	private TreeMap<Integer, Object[]> _filterTreeMap =
		new TreeMap<Integer, Object[]>();
	private HttpContext _httpContext;
	private HttpServiceTracker _httpServiceTracker;
	private Map<String, String> _initParameters = new HashMap<String, String>();
	private List<ServletContextAttributeListener>
		_servletContextAttributeListeners =
			new ArrayList<ServletContextAttributeListener>();
	private List<ServletContextListener> _servletContextListeners =
		new ArrayList<ServletContextListener>();
	private ServiceRegistration<BundleServletContext>
		_servletContextRegistration;
	private List<ServletRequestAttributeListener>
		_servletRequestAttributeListeners =
			new ArrayList<ServletRequestAttributeListener>();
	private String _servletContextName;
	private List<ServletRequestListener> _servletRequestListeners =
		new ArrayList<ServletRequestListener>();
	private Map<String, Servlet> _servletsMap =
		new ConcurrentHashMap<String, Servlet>();
	private File _tempDir;

}