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

import com.liferay.osgi.util.classloader.PassThroughClassLoader;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.wab.extender.internal.adapter.FilterExceptionAdapter;
import com.liferay.portal.wab.extender.internal.adapter.ServletContextListenerExceptionAdapter;
import com.liferay.portal.wab.extender.internal.adapter.ServletExceptionAdapter;
import com.liferay.portal.wab.extender.internal.definition.FilterDefinition;
import com.liferay.portal.wab.extender.internal.definition.ListenerDefinition;
import com.liferay.portal.wab.extender.internal.definition.ServletDefinition;
import com.liferay.portal.wab.extender.internal.definition.WebXMLDefinition;
import com.liferay.portal.wab.extender.internal.definition.WebXMLDefinitionLoader;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

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
		Dictionary<String, Object> properties,
		SAXParserFactory saxParserFactory, Logger logger) {

		_bundle = bundle;
		_contextPath = contextPath;
		_properties = properties;

		if (_contextPath.indexOf('/') != 0) {
			throw new IllegalArgumentException(
				"Context path must start with /");
		}

		_logger = logger;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_bundleClassLoader = new PassThroughClassLoader(
			bundleWiring.getClassLoader());

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

		Dictionary<String, Object> properties = new HashMapDictionary<>();

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
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Catastrophic initialization failure! Shutting down " +
					_contextName + " WAB due to: " + e.getMessage(),
				e);

			destroy();

			throw e;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected ServiceRegistration<?> createDefaultServlet() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX, "/");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN, "/*");

		return _bundleContext.registerService(
			Object.class, new Object(), properties);
	}

	protected ServiceRegistration<Servlet> createJspServlet() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		for (Enumeration<String> keys = _properties.keys();
				keys.hasMoreElements();) {

			String key = keys.nextElement();

			if (!key.startsWith(_JSP_SERVLET_INIT_PARAM_PREFIX)) {
				continue;
			}

			String paramName =
				_SERVLET_INIT_PARAM_PREFIX +
					key.substring(_JSP_SERVLET_INIT_PARAM_PREFIX.length());

			properties.put(paramName, _properties.get(key));
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");

		return _bundleContext.registerService(
			Servlet.class, new JspServletWrapper(), properties);
	}

	protected void destroyContext() {
		_serviceRegistration.unregister();
	}

	protected void destroyFilters() {
		for (ServiceRegistration<?> serviceRegistration :
				_filterRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_filterRegistrations.clear();
	}

	protected void destroyListeners() {
		for (ServiceRegistration<?> serviceRegistration :
				_listenerRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_listenerRegistrations.clear();
	}

	protected void destroyServlets() {
		for (ServiceRegistration<?> serviceRegistration :
				_servletRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_servletRegistrations.clear();
	}

	protected String[] getClassNames(EventListener eventListener) {
		List<String> classNamesList = new ArrayList<>();

		if (HttpSessionAttributeListener.class.isInstance(eventListener)) {
			classNamesList.add(HttpSessionAttributeListener.class.getName());
		}

		if (HttpSessionListener.class.isInstance(eventListener)) {
			classNamesList.add(HttpSessionListener.class.getName());
		}

		if (ServletContextAttributeListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletContextAttributeListener.class.getName());
		}

		// The following supported listener is omitted on purpose because it is
		// registered individually.

		/*
		if (ServletContextListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletContextListener.class.getName());
		}
		*/

		if (ServletRequestAttributeListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletRequestAttributeListener.class.getName());
		}

		if (ServletRequestListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletRequestListener.class.getName());
		}

		return classNamesList.toArray(new String[classNamesList.size()]);
	}

	protected void initContext() throws Exception {
		Map<String, String> contextParameters =
			_webXMLDefinition.getContextParameters();

		_wabServletContextHelper = new WabServletContextHelper(_bundle);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, _contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, _contextPath);
		properties.put(Constants.SERVICE_RANKING, 1000);
		properties.put("rtl.required", Boolean.TRUE.toString());

		for (Entry<String, String> contextParametersEntry :
				contextParameters.entrySet()) {

			String key = contextParametersEntry.getKey();
			String value = contextParametersEntry.getValue();

			properties.put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX + key,
				value);
		}

		_serviceRegistration = _bundleContext.registerService(
			ServletContextHelper.class, _wabServletContextHelper, properties);
	}

	protected void initFilters() throws Exception {
		Map<String, FilterDefinition> filterDefinitions =
			_webXMLDefinition.getFilterDefinitions();

		for (Map.Entry<String, FilterDefinition> entry :
				filterDefinitions.entrySet()) {

			FilterDefinition filterDefinition = entry.getValue();

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				_contextName);
			properties.put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED,
				filterDefinition.isAsyncSupported());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER,
				filterDefinition.getDispatchers());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
				filterDefinition.getName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN,
				filterDefinition.getURLPatterns());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET,
				filterDefinition.getServletNames());
			properties.put(
				Constants.SERVICE_RANKING, filterDefinition.getPriority());

			Map<String, String> initParameters =
				filterDefinition.getInitParameters();

			for (Entry<String, String> initParametersEntry :
					initParameters.entrySet()) {

				String key = initParametersEntry.getKey();
				String value = initParametersEntry.getValue();

				properties.put(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX + key,
					value);
			}

			FilterExceptionAdapter filterExceptionAdaptor =
				new FilterExceptionAdapter(filterDefinition.getFilter());

			ServiceRegistration<Filter> serviceRegistration =
				_bundleContext.registerService(
					Filter.class, filterExceptionAdaptor, properties);

			Exception exception = filterExceptionAdaptor.getException();

			if (exception != null) {
				serviceRegistration.unregister();

				throw exception;
			}

			_filterRegistrations.add(serviceRegistration);
		}
	}

	protected void initListeners() throws Exception {
		List<ListenerDefinition> listenerDefinitions =
			_webXMLDefinition.getListenerDefinitions();

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				_contextName);
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
				Boolean.TRUE.toString());

			String[] classNames = getClassNames(
				listenerDefinition.getEventListener());

			ServiceRegistration<?> serviceRegistration =
				_bundleContext.registerService(
					classNames, listenerDefinition.getEventListener(),
					properties);

			_listenerRegistrations.add(serviceRegistration);

			if (!ServletContextListener.class.isInstance(
					listenerDefinition.getEventListener())) {

				continue;
			}

			ServletContextListenerExceptionAdapter
				servletContextListenerExceptionAdaptor =
					new ServletContextListenerExceptionAdapter(
						(ServletContextListener)
							listenerDefinition.getEventListener());

			serviceRegistration = _bundleContext.registerService(
				ServletContextListener.class,
				servletContextListenerExceptionAdaptor, properties);

			Exception exception =
				servletContextListenerExceptionAdaptor.getException();

			if (exception != null) {
				serviceRegistration.unregister();

				throw exception;
			}

			_listenerRegistrations.add(serviceRegistration);
		}
	}

	protected void initServlets() throws Exception {
		Map<String, ServletDefinition> servletDefinitions =
			_webXMLDefinition.getServletDefinitions();

		for (Entry<String, ServletDefinition> entry :
				servletDefinitions.entrySet()) {

			ServletDefinition servletDefinition = entry.getValue();

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				_contextName);
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ASYNC_SUPPORTED,
				servletDefinition.isAsyncSupported());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ERROR_PAGE,
				servletDefinition.getErrorPages());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				servletDefinition.getName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				servletDefinition.getURLPatterns());

			Map<String, String> initParameters =
				servletDefinition.getInitParameters();

			for (Entry<String, String> initParametersEntry :
					initParameters.entrySet()) {

				String key = initParametersEntry.getKey();
				String value = initParametersEntry.getValue();

				properties.put(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX + key,
					value);
			}

			ServletExceptionAdapter servletExceptionAdaptor =
				new ServletExceptionAdapter(servletDefinition.getServlet());

			ServiceRegistration<Servlet> serviceRegistration =
				_bundleContext.registerService(
					Servlet.class, servletExceptionAdaptor, properties);

			Exception exception = servletExceptionAdaptor.getException();

			if (exception != null) {
				serviceRegistration.unregister();

				throw exception;
			}

			_servletRegistrations.add(serviceRegistration);
		}
	}

	protected void registerThisAsEventListener() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			_contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
			Boolean.TRUE.toString());

		_thisEventListenerRegistration = _bundleContext.registerService(
			ServletContextListener.class, this, properties);
	}

	private static final String _JSP_SERVLET_INIT_PARAM_PREFIX =
		"jsp.servlet.init.param.";

	private static final String _SERVLET_INIT_PARAM_PREFIX =
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX;

	private static final String _VENDOR = "Liferay, Inc.";

	private final Bundle _bundle;
	private final ClassLoader _bundleClassLoader;
	private final BundleContext _bundleContext;
	private final String _contextName;
	private final String _contextPath;
	private ServiceRegistration<?> _defaultServletServiceRegistration;
	private final Set<ServiceRegistration<Filter>> _filterRegistrations =
		new ConcurrentSkipListSet<>();
	private ServiceRegistration<Servlet> _jspServletServiceRegistration;
	private final Set<ServiceRegistration<?>> _listenerRegistrations =
		new ConcurrentSkipListSet<>();
	private final Logger _logger;
	private final Dictionary<String, Object> _properties;
	private ServiceRegistration<ServletContextHelper> _serviceRegistration;
	private ServiceRegistration<ServletContext> _servletContextRegistration;
	private final Set<ServiceRegistration<Servlet>> _servletRegistrations =
		new ConcurrentSkipListSet<>();
	private ServiceRegistration<ServletContextListener>
		_thisEventListenerRegistration;
	private WabServletContextHelper _wabServletContextHelper;
	private WebXMLDefinition _webXMLDefinition;
	private final WebXMLDefinitionLoader _webXMLDefinitionLoader;

	private class JspServletWrapper extends HttpServlet {

		@Override
		public void destroy() {
			_servlet.destroy();
		}

		@Override
		public ServletConfig getServletConfig() {
			return _servlet.getServletConfig();
		}

		@Override
		public void init(ServletConfig config) throws ServletException {
			_servlet.init(config);
		}

		@Override
		public void service(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {

			_servlet.service(request, response);
		}

		private final Servlet _servlet =
			new com.liferay.portal.servlet.jsp.compiler.JspServlet();

	}

}