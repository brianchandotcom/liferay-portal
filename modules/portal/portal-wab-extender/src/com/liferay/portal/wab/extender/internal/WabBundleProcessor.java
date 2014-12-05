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

package com.liferay.portal.wab.extender.internal;

import com.liferay.portal.wab.extender.internal.definition.FilterDefinition;
import com.liferay.portal.wab.extender.internal.definition.ListenerDefinition;
import com.liferay.portal.wab.extender.internal.definition.ServletDefinition;
import com.liferay.portal.wab.extender.internal.definition.WebXMLDefinition;
import com.liferay.portal.wab.extender.internal.definition.WebXMLDefinitionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabBundleProcessor implements ServletContextListener {

	public WabBundleProcessor(
		Bundle bundle, String contextPath,
		ExtendedHttpService extendedHttpService,
		SAXParserFactory saxParserFactory, Logger logger) {

		_bundle = bundle;
		_contextPath = contextPath;

		if (_contextPath.indexOf('/') != 0) {
			throw new IllegalArgumentException(
				"Context path must start with /");
		}

		_extendedHttpService = extendedHttpService;
		_logger = logger;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_bundleClassLoader = bundleWiring.getClassLoader();

		_contextName = _contextPath.substring(1);

		_bundleContext = _bundle.getBundleContext();
		_webXMLDefinitionLoader = new WebXMLDefinitionLoader(
			_bundle, saxParserFactory, _logger);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		_servletContextRegistration.unregister();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		servletContext.setAttribute(
			"jsp.taglib.mappings", _webXMLDefinition.getJspTaglibMappings());
		servletContext.setAttribute("osgi-bundlecontext", _bundleContext);
		servletContext.setAttribute("osgi-runtime-vendor", _VENDOR);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("osgi.web.symbolicname", _bundle.getSymbolicName());
		properties.put("osgi.web.version", _bundle.getVersion());
		properties.put("osgi.web.contextpath", servletContext.getContextPath());

		_servletContextRegistration = _bundleContext.registerService(
			ServletContext.class, servletContext, properties);
	}

	public void destroy() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (_jspServletServiceRegistration != null) {
				_jspServletServiceRegistration.unregister();
			}

			_defaultServletServiceRegistration.unregister();

			currentThread.setContextClassLoader(_bundleClassLoader);

			destroyServlets();

			destroyFilters();

			destroyListeners();

			_thisEventListenerRegistration.unregister();

			destroyContext();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void init() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_bundleClassLoader);

			_webXMLDefinition = _webXMLDefinitionLoader.loadWebXML();

			initContext();

			registerThisAsEventListener();

			initListeners();

			initFilters();

			try {
				currentThread.setContextClassLoader(contextClassLoader);

				_defaultServletServiceRegistration = createDefaultServlet();
				_jspServletServiceRegistration = createJspServlet();
			}
			finally {
				currentThread.setContextClassLoader(_bundleClassLoader);
			}

			initServlets();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected ServiceRegistration<Servlet> createDefaultServlet() {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX, "/");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		return _bundleContext.registerService(
			Servlet.class, new HttpServlet() {}, properties);
	}

	protected ServiceRegistration<Servlet> createJspServlet() {
		Servlet servlet = null;

		try {
			Class<?> clazz = Class.forName(
				"com.liferay.portal.servlet.jsp.JspServlet");

			servlet = (Servlet)clazz.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_WARNING,
				"No JSP Servlet was deployed for WAB " + _contextName +
					" due to: " + e.getMessage());

			return null;
		}

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");

		return _bundleContext.registerService(
			Servlet.class, servlet, properties);
	}

	protected void destroyContext() {
		_serviceRegistration.unregister();
	}

	protected void destroyFilters() {
		Map<String, FilterDefinition> filterDefinitions =
			_webXMLDefinition.getFilterDefinitions();

		List<FilterDefinition> filterDefinitionsList =
			new ArrayList<FilterDefinition>(filterDefinitions.values());

		Collections.reverse(filterDefinitionsList);

		for (FilterDefinition filterDefinition : filterDefinitionsList) {
			try {
				_extendedHttpService.unregisterFilter(
					filterDefinition.getFilter(), _contextName);
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		filterDefinitions.clear();
	}

	protected void destroyListeners() {
		List<ListenerDefinition> listenerDefinitions =
			_webXMLDefinition.getListenerDefinitions();

		Collections.reverse(listenerDefinitions);

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				_extendedHttpService.unregisterListener(
					listenerDefinition.getEventListener(), _contextName);
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		listenerDefinitions.clear();
	}

	protected void destroyServlets() {
		Map<String, ServletDefinition> servletDefinitions =
			_webXMLDefinition.getServletDefinitions();

		List<ServletDefinition> servletDefinitionsList =
			new ArrayList<ServletDefinition>(servletDefinitions.values());

		Collections.reverse(servletDefinitionsList);

		for (ServletDefinition servletDefinition : servletDefinitionsList) {
			Servlet servlet = servletDefinition.getServlet();

			try {
				_extendedHttpService.unregisterServlet(servlet, _contextName);
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}
	}

	protected void initContext() throws Exception {
		Map<String, String> contextParameters =
			_webXMLDefinition.getContextParameters();

		_wabServletContextHelper = new WabServletContextHelper(_bundle);

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, _contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, _contextPath);
		properties.put(Constants.SERVICE_RANKING, 1000);

		for (String key : contextParameters.keySet()) {
			properties.put(key, contextParameters.get(key));
		}

		_serviceRegistration = _bundleContext.registerService(
			ServletContextHelper.class, _wabServletContextHelper, properties);
	}

	protected void initFilters() {
		Map<String, FilterDefinition> filterDefinitions =
			_webXMLDefinition.getFilterDefinitions();

		for (Map.Entry<String, FilterDefinition> entry :
				filterDefinitions.entrySet()) {

			FilterDefinition filterDefinition = entry.getValue();

			try {
				_extendedHttpService.registerFilter(
					filterDefinition.getFilter(), filterDefinition.getName(),
					filterDefinition.getURLPatterns().toArray(new String[0]),
					filterDefinition.getServletNames().toArray(new String[0]),
					filterDefinition.getDispatchers().toArray(new String[0]),
					filterDefinition.isAsyncSupported(),
					filterDefinition.getPriority(),
					filterDefinition.getInitParameters(), _contextName);
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}
	}

	protected void initListeners() throws Exception {
		List<ListenerDefinition> listenerDefinitions =
			_webXMLDefinition.getListenerDefinitions();

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				_extendedHttpService.registerListener(
					listenerDefinition.getEventListener(), _contextName);
			}
			catch (Exception e1) {
				try {
					destroy();
				}
				catch (Exception e2) {
				}

				throw e1;
			}
		}
	}

	protected void initServlets() {
		Map<String, ServletDefinition> servletDefinitions =
			_webXMLDefinition.getServletDefinitions();

		for (Entry<String, ServletDefinition> entry :
				servletDefinitions.entrySet()) {

			ServletDefinition servletDefinition = entry.getValue();

			Servlet servlet = servletDefinition.getServlet();

			try {
				Map<String, String> initParameters =
					servletDefinition.getInitParameters();

				_extendedHttpService.registerServlet(
					servlet, servletDefinition.getName(),
					servletDefinition.getURLPatterns().toArray(new String[0]),
					servletDefinition.getErrorPages().toArray(new String[0]),
					servletDefinition.isAsyncSupported(), initParameters,
					_contextName);
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}
	}

	protected void registerThisAsEventListener() {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);

		_thisEventListenerRegistration = _bundleContext.registerService(
			EventListener.class, this, properties);
	}

	private static final String _VENDOR = "Liferay, Inc.";

	private final Bundle _bundle;
	private final ClassLoader _bundleClassLoader;
	private final BundleContext _bundleContext;
	private final String _contextName;
	private final String _contextPath;
	private ServiceRegistration<Servlet> _defaultServletServiceRegistration;
	private final ExtendedHttpService _extendedHttpService;
	private ServiceRegistration<Servlet> _jspServletServiceRegistration;
	private final Logger _logger;
	private ServiceRegistration<ServletContextHelper> _serviceRegistration;
	private ServiceRegistration<ServletContext> _servletContextRegistration;
	private ServiceRegistration<EventListener> _thisEventListenerRegistration;
	private WabServletContextHelper _wabServletContextHelper;
	private WebXMLDefinition _webXMLDefinition;
	private final WebXMLDefinitionLoader _webXMLDefinitionLoader;

}