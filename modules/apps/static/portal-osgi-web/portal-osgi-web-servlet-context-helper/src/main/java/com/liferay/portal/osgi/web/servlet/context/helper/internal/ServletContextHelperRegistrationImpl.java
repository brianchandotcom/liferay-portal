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

package com.liferay.portal.osgi.web.servlet.context.helper.internal;

import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.internal.definition.WebXMLDefinitionLoader;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.JspServlet;

import java.net.URL;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Raymond Augé
 */
public class ServletContextHelperRegistrationImpl
	implements ServletContextHelperRegistration {

	public ServletContextHelperRegistrationImpl(
		Bundle bundle, SAXParserFactory saxParserFactory, Logger logger,
		Map<String, Object> properties) {

		_bundle = bundle;
		_logger = logger;
		_properties = properties;

		String contextPath = getContextPath();

		_servletContextName = getServletContextName(contextPath);

		URL url = _bundle.getEntry("WEB-INF/");

		if (url != null) {
			_wabShapedBundle = true;

			WebXMLDefinitionLoader webXMLDefinitionLoader =
				new WebXMLDefinitionLoader(_bundle, saxParserFactory, _logger);

			WebXMLDefinition webXMLDefinition = null;

			try {
				webXMLDefinition = webXMLDefinitionLoader.loadWebXML();
			}
			catch (Exception e) {
				webXMLDefinition = new WebXMLDefinition();

				webXMLDefinition.setException(e);
			}

			_webXMLDefinition = webXMLDefinition;
		}
		else {
			_wabShapedBundle = false;

			_webXMLDefinition = new WebXMLDefinition();
		}

		_bundleContext = _bundle.getBundleContext();

		_customServletContextHelper = new CustomServletContextHelper(
			_bundle, _logger,
			_webXMLDefinition.getWebResourceCollectionDefinitions());

		_servletContextHelperServiceRegistration = createServletContextHelper(
			contextPath);

		_servletContextListenerServiceRegistration =
			createServletContextListener();

		registerServletContext();

		_defaultServletServiceRegistration = createDefaultServlet();

		_jspServletServiceRegistration = createJspServlet();

		_portletServletServiceRegistration = createPortletServlet();
	}

	@Override
	public void close() {
		_servletContextRegistration.unregister();

		_servletContextHelperServiceRegistration.unregister();

		_servletContextListenerServiceRegistration.unregister();

		_defaultServletServiceRegistration.unregister();

		_jspServletServiceRegistration.unregister();

		if (_portletServletServiceRegistration != null) {
			_portletServletServiceRegistration.unregister();
		}
	}

	@Override
	public ServletContext getServletContext() {
		return _customServletContextHelper.getServletContext();
	}

	@Override
	public WebXMLDefinition getWebXMLDefinition() {
		return _webXMLDefinition;
	}

	@Override
	public boolean isWabShapedBundle() {
		return _wabShapedBundle;
	}

	@Override
	public void setProperties(Map<String, String> contextParameters) {
		if (contextParameters.isEmpty()) {
			return;
		}

		ServiceReference<ServletContextHelper> serviceReference =
			_servletContextHelperServiceRegistration.getReference();

		Dictionary<String, Object> properties = new Hashtable<>();

		for (String key : serviceReference.getPropertyKeys()) {
			properties.put(key, serviceReference.getProperty(key));
		}

		for (Entry<String, String> entry : contextParameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			properties.put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX + key,
				value);
		}

		_servletContextHelperServiceRegistration.setProperties(properties);
	}

	protected String createContextSelectFilterString() {
		StringBuilder sb = new StringBuilder();

		sb.append('(');
		sb.append(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);
		sb.append('=');
		sb.append(_servletContextName);
		sb.append(')');

		return sb.toString();
	}

	protected ServiceRegistration<?> createDefaultServlet() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());

		String prefix = "/META-INF/resources";

		if (_wabShapedBundle) {
			prefix = "/";
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX, prefix);

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN, "/*");

		return _bundleContext.registerService(
			Object.class, new Object(), properties);
	}

	protected ServiceRegistration<Servlet> createJspServlet() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		for (Map.Entry<String, Object> entry : _properties.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith(_JSP_SERVLET_INIT_PARAM_PREFIX)) {
				continue;
			}

			String name =
				_SERVLET_INIT_PARAM_PREFIX +
					key.substring(_JSP_SERVLET_INIT_PARAM_PREFIX.length());

			properties.put(name, entry.getValue());
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			JspServletWrapper.class.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			new String[] {"*.jsp", "*.jspx"});

		return _bundleContext.registerService(
			Servlet.class, new JspServlet() {}, properties);
	}

	protected ServiceRegistration<Servlet> createPortletServlet() {
		if (_wabShapedBundle) {
			return null;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			PortletServlet.class.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/portlet-servlet/*");

		return _bundleContext.registerService(
			Servlet.class, new PortletServlet() {}, properties);
	}

	protected ServiceRegistration<ServletContextHelper>
		createServletContextHelper(String contextPath) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			_servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);

		Map<String, String> contextParameters =
			_webXMLDefinition.getContextParameters();

		boolean rtlRequiredValue = isRTLRequired(contextParameters);

		properties.put("rtl.required", Boolean.toString(rtlRequiredValue));

		for (Map.Entry<String, String> entry : contextParameters.entrySet()) {
			String key =
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX + entry.getKey();

			properties.put(key, entry.getValue());
		}

		return _bundleContext.registerService(
			ServletContextHelper.class, _customServletContextHelper,
			properties);
	}

	protected ServiceRegistration<ServletContextListener>
		createServletContextListener() {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
			Boolean.TRUE.toString());

		return _bundleContext.registerService(
			ServletContextListener.class, _customServletContextHelper,
			properties);
	}

	protected String getContextPath() {
		Dictionary<String, String> headers = _bundle.getHeaders();

		String contextPath = headers.get("Web-ContextPath");

		if (Validator.isNotNull(contextPath)) {
			return contextPath;
		}

		return '/' + _bundle.getSymbolicName();
	}

	protected String getServletContextName(String contextPath) {
		Dictionary<String, String> headers = _bundle.getHeaders();

		String header = headers.get("Web-ContextName");

		if (Validator.isNotNull(header)) {
			return header;
		}

		return contextPath.substring(1);
	}

	protected boolean isRTLRequired(Map<String, String> contextParameters) {
		boolean rtlRequiredValue = true;

		if (contextParameters.containsKey("rtl.required")) {
			rtlRequiredValue = GetterUtil.getBoolean(
				contextParameters.get("rtl.required"));
		}
		else {
			Dictionary<String, String> headers = _bundle.getHeaders();

			String rtlSupportRequiredHeader = headers.get(
				"Liferay-RTL-Support-Required");

			if (Validator.isNotNull(rtlSupportRequiredHeader)) {
				rtlRequiredValue = GetterUtil.getBoolean(
					rtlSupportRequiredHeader);
			}
		}

		return rtlRequiredValue;
	}

	protected void registerServletContext() {
		ServletContext servletContext =
			_customServletContextHelper.getServletContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"osgi.web.contextname", servletContext.getServletContextName());
		properties.put("osgi.web.contextpath", servletContext.getContextPath());
		properties.put("osgi.web.symbolicname", _bundle.getSymbolicName());
		properties.put("osgi.web.version", _bundle.getVersion());

		_servletContextRegistration = _bundleContext.registerService(
			ServletContext.class, servletContext, properties);
	}

	private static final String _JSP_SERVLET_INIT_PARAM_PREFIX =
		"jsp.servlet.init.param.";

	private static final String _SERVLET_INIT_PARAM_PREFIX =
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX;

	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private final CustomServletContextHelper _customServletContextHelper;
	private final ServiceRegistration<?> _defaultServletServiceRegistration;
	private final ServiceRegistration<Servlet> _jspServletServiceRegistration;
	private final Logger _logger;
	private final ServiceRegistration<Servlet>
		_portletServletServiceRegistration;
	private final Map<String, Object> _properties;
	private final ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final ServiceRegistration<ServletContextListener>
		_servletContextListenerServiceRegistration;
	private final String _servletContextName;
	private ServiceRegistration<ServletContext> _servletContextRegistration;
	private final boolean _wabShapedBundle;
	private final WebXMLDefinition _webXMLDefinition;

}