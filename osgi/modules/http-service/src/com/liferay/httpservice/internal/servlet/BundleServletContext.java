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

import com.liferay.httpservice.servlet.BundleServletConfig;
import com.liferay.portal.apache.bridges.struts.LiferayServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
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
		_servletContextName = servletContextName;
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

	@Override
	public String getServletContextName() {
		return _servletContextName;
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

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public void unregisterFilter(String filterName) {
		Filter filter = _filtersByFilterNames.remove(filterName);

		if (filter == null) {
			return;
		}

		filter.destroy();

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

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private Map<String, Filter> _filtersByFilterNames =
		new ConcurrentHashMap<String, Filter>();
	private Map<String, Filter> _filtersByURLPatterns =
		new ConcurrentHashMap<String, Filter>();
	private String _servletContextName;
	private Map<String, Servlet> _servletsByServletNames =
		new ConcurrentHashMap<String, Servlet>();
	private Map<String, Servlet> _servletsByURLPatterns =
		new ConcurrentHashMap<String, Servlet>();

}