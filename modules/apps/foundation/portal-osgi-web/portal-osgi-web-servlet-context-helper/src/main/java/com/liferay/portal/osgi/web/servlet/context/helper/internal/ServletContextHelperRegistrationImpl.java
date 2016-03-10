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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.JspServlet;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
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

		String servletContextName = getServletContextName(bundle, contextPath);

		boolean wabShapedBundle = false;

		URL url = bundle.getEntry("WEB-INF/");

		if (url != null) {
			wabShapedBundle = true;
		}

		BundleContext bundleContext = bundle.getBundleContext();

		_logger = new Logger(bundleContext);

		_customServletContextHelper = new CustomServletContextHelper(
			bundle, wabShapedBundle);

		_servletContextHelperServiceRegistration = createServletContextHelper(
			bundleContext, servletContextName, contextPath);

		_servletContextListenerServiceRegistration =
			createServletContextListener(bundleContext, servletContextName);

		initServletContainerInitializers(
			bundle, getServletContext(), wabShapedBundle);

		_defaultServletServiceRegistration = createDefaultServlet(
			bundleContext, servletContextName, wabShapedBundle);

		_jspServletServiceRegistration = createJspServlet(
			bundleContext, servletContextName);

		_portletServletServiceRegistration = createPortletServlet(
			bundleContext, servletContextName, wabShapedBundle);

		_portletServletRequestFilterServiceRegistration =
			createRestrictPortletServletRequestFilter(
				bundleContext, servletContextName);
	}

	@Override
	public void close() {
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

	protected void collectAnnotatedClasses(
		String classResource, Bundle bundle, Class<?>[] handledTypesArray,
		Set<Class<?>> annotatedClasses) {

		String className = classResource.replaceAll("\\.class$", "");

		className = className.replaceAll("/", ".");

		Class<?> annotatedClass = null;

		try {
			annotatedClass = bundle.loadClass(className);
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_DEBUG, t.getMessage());

			return;
		}

		// Class extends/implements

		for (Class<?> handledType : handledTypesArray) {
			if (handledType.isAssignableFrom(annotatedClass)) {
				annotatedClasses.add(annotatedClass);

				return;
			}
		}

		// Class annotation

		Annotation[] classAnnotations = new Annotation[0];

		try {
			classAnnotations = annotatedClass.getAnnotations();
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_DEBUG, t.getMessage());
		}

		for (Annotation classAnnotation : classAnnotations) {
			if (ArrayUtil.contains(
					handledTypesArray, classAnnotation.annotationType())) {

				annotatedClasses.add(annotatedClass);

				return;
			}
		}

		// Method annotation

		Method[] classMethods = new Method[0];

		try {
			classMethods = annotatedClass.getDeclaredMethods();
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_DEBUG, t.getMessage());
		}

		for (Method method : classMethods) {
			Annotation[] methodAnnotations = new Annotation[0];

			try {
				methodAnnotations = method.getDeclaredAnnotations();
			}
			catch (Throwable t) {
				_logger.log(Logger.LOG_DEBUG, t.getMessage());
			}

			for (Annotation methodAnnotation : methodAnnotations) {
				if (ArrayUtil.contains(
						handledTypesArray, methodAnnotation.annotationType())) {

					annotatedClasses.add(annotatedClass);

					return;
				}
			}
		}

		// Field annotation

		Field[] declaredFields = new Field[0];

		try {
			declaredFields = annotatedClass.getDeclaredFields();
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_DEBUG, t.getMessage());
		}

		for (Field field : declaredFields) {
			Annotation[] fieldAnnotations = new Annotation[0];

			try {
				fieldAnnotations = field.getDeclaredAnnotations();
			}
			catch (Throwable t) {
				_logger.log(Logger.LOG_DEBUG, t.getMessage());
			}

			for (Annotation fieldAnnotation : fieldAnnotations) {
				if (ArrayUtil.contains(
						handledTypesArray, fieldAnnotation.annotationType())) {

					annotatedClasses.add(annotatedClass);

					return;
				}
			}
		}
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
		BundleContext bundleContext, String servletContextName,
		boolean wabShapedBundle) {

		if (wabShapedBundle) {
			return null;
		}

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

	protected void initServletContainerInitializers(
		Bundle bundle, ServletContext servletContext, boolean wabShapedBundle) {

		if (!wabShapedBundle) {
			return;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Collection<String> initializerResources = bundleWiring.listResources(
			"META-INF/services", "javax.servlet.ServletContainerInitializer",
			BundleWiring.LISTRESOURCES_RECURSE);

		if (initializerResources == null) {
			return;
		}

		for (String initializerResource : initializerResources) {
			URL url = bundle.getResource(initializerResource);

			if (url == null) {
				continue;
			}

			try (InputStream inputStream = url.openStream()) {
				String fqcn = StringUtil.read(inputStream);

				processServletContainerInitializerClass(
					fqcn, bundle, bundleWiring, servletContext);
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}
	}

	protected void processServletContainerInitializerClass(
		String fqcn, Bundle bundle, BundleWiring bundleWiring,
		ServletContext servletContext) {

		Class<? extends ServletContainerInitializer> initializerClass = null;

		try {
			Class<?> clazz = bundle.loadClass(fqcn);

			if (!ServletContainerInitializer.class.isAssignableFrom(clazz)) {
				return;
			}

			initializerClass = clazz.asSubclass(
				ServletContainerInitializer.class);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);

			return;
		}

		HandlesTypes handledTypesAnnotation = initializerClass.getAnnotation(
			HandlesTypes.class);

		if (handledTypesAnnotation == null) {
			handledTypesAnnotation = _NULL_HANDLES_TYPES;
		}

		Class<?>[] handledTypesArray = handledTypesAnnotation.value();

		if (handledTypesArray == null) {
			handledTypesArray = new Class[0];
		}

		Collection<String> classResources = bundleWiring.listResources(
			"/", "*.class", BundleWiring.LISTRESOURCES_RECURSE);

		if (classResources == null) {
			classResources = new ArrayList<>(0);
		}

		Set<Class<?>> annotatedClasses = new HashSet<>();

		for (String classResource : classResources) {
			URL urlClassResource = bundle.getResource(classResource);

			if (urlClassResource == null) {
				continue;
			}

			collectAnnotatedClasses(
				classResource, bundle, handledTypesArray, annotatedClasses);
		}

		if (annotatedClasses.isEmpty()) {
			annotatedClasses = null;
		}

		try {
			ServletContainerInitializer servletContainerInitializer =
				initializerClass.newInstance();

			servletContainerInitializer.onStartup(
				annotatedClasses, servletContext);
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_ERROR, t.getMessage(), t);
		}
	}

	private static final String _JSP_SERVLET_INIT_PARAM_PREFIX =
		"jsp.servlet.init.param.";

	private static final HandlesTypes _NULL_HANDLES_TYPES = new HandlesTypes() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return null;
		}

		@Override
		public Class<?>[] value() {
			return new Class[0];
		}

	};

	private static final String _SERVLET_INIT_PARAM_PREFIX =
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX;

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
	private final ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final ServiceRegistration<ServletContextListener>
		_servletContextListenerServiceRegistration;

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