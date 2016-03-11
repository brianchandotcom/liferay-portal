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

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

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
		Bundle bundle, Props props, Map<String, Object> properties) {

		_props = props;
		_properties = properties;

		String contextPath = getContextPath(bundle);

		_servletContextName = getServletContextName(bundle, contextPath);

		URL url = bundle.getEntry("WEB-INF/");

		if (url != null) {
			_wabShapedBundle = true;
		}
		else {
			_wabShapedBundle = false;
		}

		_bundleContext = bundle.getBundleContext();

		_customServletContextHelper = new CustomServletContextHelper(
			bundle, _wabShapedBundle);

		_servletContextHelperServiceRegistration = createServletContextHelper(
			_bundleContext, _servletContextName, contextPath);

		_servletContextListenerServiceRegistration =
			createServletContextListener(_bundleContext, _servletContextName);
	}

	@Override
	public void close() {
		if (_servletContextHelperServiceRegistration != null) {
			_servletContextHelperServiceRegistration.unregister();
		}

		if (_servletContextListenerServiceRegistration != null) {
			_servletContextListenerServiceRegistration.unregister();
		}

		if (_defaultServletServiceRegistration != null) {
			_defaultServletServiceRegistration.unregister();
		}

		if (_jspServletServiceRegistration != null) {
			_jspServletServiceRegistration.unregister();
		}

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
	public ServiceReference<ServletContextHelper>
		getServletContextHelperSeviceReference() {

		return _servletContextHelperServiceRegistration.getReference();
	}

	@Override
	public ServiceReference<ServletContextListener>
		getServletContextListenerSeviceReference() {

		return _servletContextListenerServiceRegistration.getReference();
	}

	@Override
	public void initDefaults() {
		if (_defaultServletServiceRegistration == null) {
			_defaultServletServiceRegistration = createDefaultServlet(
				_bundleContext, _servletContextName, _wabShapedBundle);
		}

		if (_jspServletServiceRegistration == null) {
			_jspServletServiceRegistration = createJspServlet(
				_bundleContext, _servletContextName);
		}

		if (_portletServletServiceRegistration == null) {
			_portletServletServiceRegistration = createPortletServlet(
				_bundleContext, _servletContextName);
		}

		if (_portletServletRequestFilterServiceRegistration == null) {
			_portletServletRequestFilterServiceRegistration =
				createRestrictPortletServletRequestFilter(
					_bundleContext, _servletContextName);
		}
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

	protected String createContextSelectFilterString(
		String servletContextName) {

		StringBuilder sb = new StringBuilder();

		sb.append('(');
		sb.append(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME);
		sb.append('=');
		sb.append(servletContextName);
		sb.append(')');

		return sb.toString();
	}

	protected ServiceRegistration<?> createDefaultServlet(
		BundleContext bundleContext, String servletContextName,
		boolean wabShapedBundle) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString(servletContextName));

		String prefix = "/META-INF/resources";

		if (wabShapedBundle) {
			prefix = "/";
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX, prefix);

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN, "/*");

		return bundleContext.registerService(
			Object.class, new Object(), properties);
	}

	protected ServiceRegistration<Servlet> createJspServlet(
		BundleContext bundleContext, String servletContextName) {

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
			createContextSelectFilterString(servletContextName));
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");

		return bundleContext.registerService(
			Servlet.class, new JspServletWrapper(), properties);
	}

	protected ServiceRegistration<Servlet> createPortletServlet(
		BundleContext bundleContext, String servletContextName) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString(servletContextName));
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"Portlet Servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/portlet-servlet/*");

		return bundleContext.registerService(
			Servlet.class, new PortletServletWrapper(), properties);
	}

	protected ServiceRegistration<?> createRestrictPortletServletRequestFilter(
		BundleContext bundleContext, String servletContextName) {

		if (!GetterUtil.getBoolean(
				_props.get(PropsKeys.PORTLET_CONTAINER_RESTRICT))) {

			return null;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString(servletContextName));
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

		return bundleContext.registerService(
			Filter.class, new RestrictPortletServletRequestFilter(),
			properties);
	}

	protected ServiceRegistration<ServletContextHelper>
		createServletContextHelper(
			BundleContext bundleContext, String servletContextName,
			String contextPath) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);
		properties.put("rtl.required", Boolean.TRUE.toString());

		return bundleContext.registerService(
			ServletContextHelper.class, _customServletContextHelper,
			properties);
	}

	protected ServiceRegistration<ServletContextListener>
		createServletContextListener(
			BundleContext bundleContext, String servletContextName) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			createContextSelectFilterString(servletContextName));
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
			Boolean.TRUE.toString());

		return bundleContext.registerService(
			ServletContextListener.class, _customServletContextHelper,
			properties);
	}

	protected String getContextPath(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String contextPath = headers.get("Web-ContextPath");

		if (Validator.isNotNull(contextPath)) {
			return contextPath;
		}

		String symbolicName = bundle.getSymbolicName();

		return '/' + symbolicName.replaceAll("[^a-zA-Z0-9]", "");
	}

	protected String getServletContextName(Bundle bundle, String contextPath) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String header = headers.get("Web-ContextName");

		if (Validator.isNotNull(header)) {
			return header;
		}

		contextPath = contextPath.substring(1);

		return contextPath.replaceAll("[^a-zA-Z0-9\\-]", "");
	}

	private static final String _JSP_SERVLET_INIT_PARAM_PREFIX =
		"jsp.servlet.init.param.";

	private static final String _SERVLET_INIT_PARAM_PREFIX =
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX;

	private final BundleContext _bundleContext;
	private final CustomServletContextHelper _customServletContextHelper;
	private ServiceRegistration<?> _defaultServletServiceRegistration;
	private ServiceRegistration<Servlet> _jspServletServiceRegistration;
	private ServiceRegistration<?>
		_portletServletRequestFilterServiceRegistration;
	private ServiceRegistration<Servlet> _portletServletServiceRegistration;
	private final Map<String, Object> _properties;
	private final Props _props;
	private final ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final ServiceRegistration<ServletContextListener>
		_servletContextListenerServiceRegistration;
	private final String _servletContextName;
	private final boolean _wabShapedBundle;

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