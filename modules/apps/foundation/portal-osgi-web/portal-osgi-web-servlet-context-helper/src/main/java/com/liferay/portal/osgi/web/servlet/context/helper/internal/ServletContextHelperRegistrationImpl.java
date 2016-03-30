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

import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.JspServlet;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import javax.portlet.PortletRequest;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Raymond Augé
 */
public class ServletContextHelperRegistrationImpl
	implements ServletContextHelperRegistration {

	public ServletContextHelperRegistrationImpl(
		Bundle bundle, Props props, SAXParserFactory saxParserFactory,
		Logger logger, Map<String, Object> properties) {

		_bundle = bundle;
		_props = props;
		_saxParserFactory = saxParserFactory;
		_logger = logger;
		_properties = properties;

		String contextPath = getContextPath();

		_servletContextName = getServletContextName(contextPath);

		URL url = _bundle.getEntry("WEB-INF/");

		if (url != null) {
			_wabShapedBundle = true;
		}
		else {
			_wabShapedBundle = false;
		}

		_bundleContext = _bundle.getBundleContext();

		_customServletContextHelper = new CustomServletContextHelper(
			_bundle, _wabShapedBundle);

		_servletContextHelperServiceRegistration = createServletContextHelper(
			contextPath);

		_servletContextListenerServiceRegistration =
			createServletContextListener();

		registerServletContext();

		_defaultServletServiceRegistration = createDefaultServlet();

		_jspServletServiceRegistration = createJspServlet();

		_portletServletServiceRegistration = createPortletServlet();

		_portletServletRequestFilterServiceRegistration =
			createRestrictPortletServletRequestFilter();
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

		if (_portletServletRequestFilterServiceRegistration != null) {
			_portletServletRequestFilterServiceRegistration.unregister();
		}
	}

	@Override
	public ServletContext getServletContext() {
		return _customServletContextHelper.getServletContext();
	}

	@Override
	public boolean isWabShapedBundle() {
		return _wabShapedBundle;
	}

	@Override
	public void setProperties(Map<String, String> contextParameters) {
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

		for (String key : _properties.keySet()) {
			if (!key.startsWith(_JSP_SERVLET_INIT_PARAM_PREFIX)) {
				continue;
			}

			String name =
				_SERVLET_INIT_PARAM_PREFIX +
					key.substring(_JSP_SERVLET_INIT_PARAM_PREFIX.length());

			properties.put(name, _properties.get(key));
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			new String[] {"*.jsp", "*.jspx"});

		return _bundleContext.registerService(
			Servlet.class, new JspServletWrapper(), properties);
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
			"Portlet Servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/portlet-servlet/*");

		return _bundleContext.registerService(
			Servlet.class, new PortletServletWrapper(), properties);
	}

	protected ServiceRegistration<?>
		createRestrictPortletServletRequestFilter() {

		if (_wabShapedBundle) {
			return null;
		}

		if (!GetterUtil.getBoolean(
				_props.get(PropsKeys.PORTLET_CONTAINER_RESTRICT))) {

			return null;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED,
			Boolean.TRUE);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER,
			new String[] {
				DispatcherType.ASYNC.toString(),
				DispatcherType.FORWARD.toString(),
				DispatcherType.INCLUDE.toString(),
				DispatcherType.REQUEST.toString()
			});
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, "/*");

		return _bundleContext.registerService(
			Filter.class, new RestrictPortletServletRequestFilter(),
			properties);
	}

	protected ServiceRegistration<ServletContextHelper>
		createServletContextHelper(String contextPath) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			_servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);
		properties.put("rtl.required", Boolean.TRUE.toString());

		_collectContextInitParams(properties);

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

		String symbolicName = _bundle.getSymbolicName();

		return '/' + symbolicName.replaceAll("[^a-zA-Z0-9]", "");
	}

	protected String getServletContextName(String contextPath) {
		Dictionary<String, String> headers = _bundle.getHeaders();

		String header = headers.get("Web-ContextName");

		if (Validator.isNotNull(header)) {
			return header;
		}

		contextPath = contextPath.substring(1);

		return contextPath.replaceAll("[^a-zA-Z0-9\\-]", "");
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

	private void _collectContextInitParams(
		Dictionary<String, Object> properties) {

		if (!_wabShapedBundle) {
			return;
		}

		URL url = _bundle.getEntry("WEB-INF/web.xml");

		if (url == null) {
			return;
		}

		try (InputStream inputStream = url.openStream()) {
			SAXParser saxParser = _saxParserFactory.newSAXParser();

			XMLReader xmlReader = saxParser.getXMLReader();

			xmlReader.setContentHandler(
				new ContextInitParamHandler(properties));

			xmlReader.parse(new InputSource(inputStream));
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_WARNING, "Unable to parse web.xml", e);
		}
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
	private final ServiceRegistration<?>
		_portletServletRequestFilterServiceRegistration;
	private final ServiceRegistration<Servlet>
		_portletServletServiceRegistration;
	private final Map<String, Object> _properties;
	private final Props _props;
	private final SAXParserFactory _saxParserFactory;
	private final ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final ServiceRegistration<ServletContextListener>
		_servletContextListenerServiceRegistration;
	private final String _servletContextName;
	private ServiceRegistration<ServletContext> _servletContextRegistration;
	private final boolean _wabShapedBundle;

	private static class ContextInitParamHandler extends DefaultHandler {

		public ContextInitParamHandler(Dictionary<String, Object> properties) {
			_properties = properties;
		}

		@Override
		public void characters(char[] c, int start, int length) {
			if (_stack.empty()) {
				return;
			}

			StringBuilder stringBuilder = _stack.peek();

			stringBuilder.append(c, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (qName.equals("context-param")) {
				_properties.put(
					HttpWhiteboardConstants.
						HTTP_WHITEBOARD_CONTEXT_INIT_PARAM_PREFIX + _paramName,
					_paramValue);

				_paramName = null;
				_paramValue = null;
			}
			else if (qName.equals("param-name")) {
				_paramName = String.valueOf(_stack.pop());
				_paramName = _paramName.trim();
			}
			else if (qName.equals("param-value")) {
				_paramValue = String.valueOf(_stack.pop());
				_paramValue = _paramValue.trim();
			}
		}

		@Override
		public void startElement(
			String uri, String localName, String qName, Attributes attributes) {

			if (qName.equals("param-name") || qName.equals("param-value")) {
				_stack.push(new StringBuilder());
			}
		}

		private String _paramName;
		private String _paramValue;
		private final Dictionary<String, Object> _properties;
		private final Stack<StringBuilder> _stack = new Stack<>();

	}

	private static class JspServletWrapper extends HttpServlet {

		@Override
		public void destroy() {
			_servlet.destroy();
		}

		@Override
		public ServletConfig getServletConfig() {
			return _servlet.getServletConfig();
		}

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			_servlet.init(servletConfig);
		}

		@Override
		public void service(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			_servlet.service(servletRequest, servletResponse);
		}

		private final Servlet _servlet = new JspServlet();

	}

	private static class PortletServletWrapper extends HttpServlet {

		@Override
		protected void service(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException, ServletException {

			_servlet.service(httpServletRequest, httpServletResponse);
		}

		private final Servlet _servlet = new PortletServlet();

	}

	private static class RestrictPortletServletRequestFilter implements Filter {

		@Override
		public void destroy() {
		}

		@Override
		public void doFilter(
				ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain filterChain)
			throws IOException, ServletException {

			try {
				filterChain.doFilter(servletRequest, servletResponse);
			}
			finally {
				PortletRequest portletRequest =
					(PortletRequest)servletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST);

				if (portletRequest == null) {
					return;
				}

				RestrictPortletServletRequest restrictPortletServletRequest =
					new RestrictPortletServletRequest(
						PortalUtil.getHttpServletRequest(portletRequest));

				Enumeration<String> enumeration =
					servletRequest.getAttributeNames();

				while (enumeration.hasMoreElements()) {
					String name = enumeration.nextElement();

					if (!RestrictPortletServletRequest.isSharedRequestAttribute(
							name)) {

						continue;
					}

					restrictPortletServletRequest.setAttribute(
						name, servletRequest.getAttribute(name));
				}

				restrictPortletServletRequest.mergeSharedAttributes();
			}
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
		}

	}

}