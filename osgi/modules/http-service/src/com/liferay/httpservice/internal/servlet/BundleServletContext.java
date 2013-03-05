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
import com.liferay.httpservice.servlet.BundleServletConfig;
import com.liferay.portal.apache.bridges.struts.LiferayServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.struts.AuthPublicPathRegistry;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;

import org.osgi.framework.Bundle;
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
	}

	public void close() {
	}

	public Bundle getBundle() {
		return _bundle;
	}

	public ClassLoader getClassLoader() {
		return null;
	}

	public HttpContext getHttpContext() {
		return null;
	}

	public List<ServletRequestAttributeListener>
		getServletRequestAttributeListeners() {

		return null;
	}

	public List<ServletRequestListener> getServletRequestListeners() {
		return null;
	}

	public void open() {
	}

	public void registerFilter(
			String filterMapping, Filter filter,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		validate(filterMapping, filter, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		int serviceRanking = GetterUtil.getInteger(
			initParameters.remove(HttpServicePropsKeys.SERVICE_RANKING));

		try {
			currentThread.setContextClassLoader(getClassLoader());

			FilterConfig filterConfig = new BundleFilterConfig(
				this, filterMapping, initParameters, httpContext);

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

	public void registerServlet(
			String alias, Servlet servlet, Map<String, String> initParameters,
			HttpContext httpContext)
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

	public void setServletContextName(String servletContextName) {
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

			_filtersMap.remove(filterMapping);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void unregisterServlet(String alias) {
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

			_servletsMap.remove(alias);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
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

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private Map<String, Filter> _filtersMap =
		new ConcurrentHashMap<String, Filter>();
	private TreeMap<Integer, Object[]> _filterTreeMap =
		new TreeMap<Integer, Object[]>();
	private Map<String, Servlet> _servletsMap =
		new ConcurrentHashMap<String, Servlet>();

}