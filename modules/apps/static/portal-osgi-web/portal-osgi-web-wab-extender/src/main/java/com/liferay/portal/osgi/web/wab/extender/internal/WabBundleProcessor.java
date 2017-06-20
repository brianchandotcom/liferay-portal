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

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.FilterDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ListenerDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ServletDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.JspServlet;
import com.liferay.portal.osgi.web.wab.extender.internal.adapter.FilterExceptionAdapter;
import com.liferay.portal.osgi.web.wab.extender.internal.adapter.ModifiableServletContext;
import com.liferay.portal.osgi.web.wab.extender.internal.adapter.ModifiableServletContextAdapter;
import com.liferay.portal.osgi.web.wab.extender.internal.adapter.ServletContextListenerExceptionAdapter;
import com.liferay.portal.osgi.web.wab.extender.internal.adapter.ServletExceptionAdapter;
import com.liferay.portal.osgi.web.wab.extender.internal.registration.FilterRegistrationImpl;
import com.liferay.portal.osgi.web.wab.extender.internal.registration.ListenerServiceRegistrationComparator;
import com.liferay.portal.osgi.web.wab.extender.internal.registration.ServletRegistrationImpl;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabBundleProcessor {

	public WabBundleProcessor(Bundle bundle, Logger logger) {
		_bundle = bundle;
		_logger = logger;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_bundleClassLoader = bundleWiring.getClassLoader();

		_bundleContext = _bundle.getBundleContext();
	}

	public void destroy() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_bundleClassLoader);

			destroyServlets();

			destroyFilters();

			destroyListeners();

			_bundleContext.ungetService(
				_servletContextHelperRegistrationServiceReference);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void init(Dictionary<String, Object> properties) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_bundleClassLoader);

			ServletContextHelperRegistration servletContextHelperRegistration =
				initContext();

			boolean wabShapedBundle =
				servletContextHelperRegistration.isWabShapedBundle();

			if (!wabShapedBundle) {
				return;
			}

			WebXMLDefinition webXMLDefinition =
				servletContextHelperRegistration.getWebXMLDefinition();

			Exception exception = webXMLDefinition.getException();

			if (exception != null) {
				throw exception;
			}

			ServletContext servletContext =
				ModifiableServletContextAdapter.createInstance(
					servletContextHelperRegistration.getServletContext(),
					_bundle.getBundleContext(), webXMLDefinition, _logger);

			initServletContainerInitializers(_bundle, servletContext);

			ModifiableServletContext modifiableServletContext =
				(ModifiableServletContext)servletContext;

			Map<String, String> unregisteredInitParameters =
				modifiableServletContext.getUnregisteredInitParameters();

			if ((unregisteredInitParameters != null) &&
				!unregisteredInitParameters.isEmpty()) {

				Map<String, Object> attributes = new HashMap<>();

				Enumeration<String> attributeNames =
					servletContext.getAttributeNames();

				while (attributeNames.hasMoreElements()) {
					String attributeName = attributeNames.nextElement();

					attributes.put(
						attributeName,
						servletContext.getAttribute(attributeName));
				}

				List<ListenerDefinition> listenerDefinitions =
					modifiableServletContext.getListenerDefinitions();
				Map<String, FilterRegistrationImpl> filterRegistrationImpls =
					modifiableServletContext.getFilterRegistrationImpls();
				Map<String, ServletRegistrationImpl> servletRegistrationImpls =
					modifiableServletContext.getServletRegistrationImpls();

				servletContextHelperRegistration.setProperties(
					unregisteredInitParameters);

				ServletContext newServletContext =
					servletContextHelperRegistration.getServletContext();

				servletContext = ModifiableServletContextAdapter.createInstance(
					newServletContext, attributes, listenerDefinitions,
					filterRegistrationImpls, servletRegistrationImpls,
					_bundle.getBundleContext(), webXMLDefinition, _logger);

				modifiableServletContext =
					(ModifiableServletContext)servletContext;
			}

			scanTLDsForListeners(webXMLDefinition, servletContext);

			initListeners(
				webXMLDefinition.getListenerDefinitions(), servletContext);

			initListeners(
				modifiableServletContext.getListenerDefinitions(),
				servletContext);

			modifiableServletContext.registerFilters();

			initFilters(webXMLDefinition.getFilterDefinitions());

			modifiableServletContext.registerServlets();

			initServlets(
				webXMLDefinition.getServletDefinitions(),
				modifiableServletContext);
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
			if (handledType.isAssignableFrom(annotatedClass) &&
				!Modifier.isAbstract(annotatedClass.getModifiers())) {

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

	protected void destroyFilters() {
		for (ServiceRegistration<?> serviceRegistration :
				_filterServiceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_filterServiceRegistrations.clear();
	}

	protected void destroyListeners() {
		for (ServiceRegistration<?> serviceRegistration :
				_listenerServiceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_listenerServiceRegistrations.clear();
	}

	protected void destroyServlets() {
		for (ServiceRegistration<?> serviceRegistration :
				_servletServiceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
			}
		}

		_servletServiceRegistrations.clear();
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

		/*if (ServletContextListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletContextListener.class.getName());
		}*/

		if (ServletRequestAttributeListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletRequestAttributeListener.class.getName());
		}

		if (ServletRequestListener.class.isInstance(eventListener)) {
			classNamesList.add(ServletRequestListener.class.getName());
		}

		return classNamesList.toArray(new String[classNamesList.size()]);
	}

	protected ServletContextHelperRegistration initContext() {
		ServiceTracker
			<ServletContextHelperRegistration, ServletContextHelperRegistration>
				serviceTracker = new ServiceTracker<>(
					_bundleContext, ServletContextHelperRegistration.class,
					null);

		serviceTracker.open();

		try {
			ServletContextHelperRegistration servletContextHelperRegistration =
				serviceTracker.waitForService(2000);

			WebXMLDefinition webXMLDefinition =
				servletContextHelperRegistration.getWebXMLDefinition();

			_servletContextHelperRegistrationServiceReference =
				serviceTracker.getServiceReference();

			ServletContext servletContext =
				servletContextHelperRegistration.getServletContext();

			_contextName = servletContext.getServletContextName();

			servletContext.setAttribute(
				"jsp.taglib.mappings", webXMLDefinition.getJspTaglibMappings());
			servletContext.setAttribute("osgi-bundlecontext", _bundleContext);
			servletContext.setAttribute("osgi-runtime-vendor", _VENDOR);

			return servletContextHelperRegistration;
		}
		catch (InterruptedException ie) {
			return ReflectionUtil.throwException(ie);
		}
	}

	protected void initFilters(Map<String, FilterDefinition> filterDefinitions)
		throws Exception {

		for (Map.Entry<String, FilterDefinition> entry :
				filterDefinitions.entrySet()) {

			FilterDefinition filterDefinition = entry.getValue();

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				_contextName);
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED,
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

			_filterServiceRegistrations.add(serviceRegistration);
		}
	}

	protected void initListeners(
			List<ListenerDefinition> listenerDefinitions,
			ServletContext servletContext)
		throws Exception {

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

			if (classNames.length > 0) {
				ServiceRegistration<?> serviceRegistration =
					_bundleContext.registerService(
						classNames, listenerDefinition.getEventListener(),
						properties);

				_listenerServiceRegistrations.add(serviceRegistration);
			}

			if (!ServletContextListener.class.isInstance(
					listenerDefinition.getEventListener())) {

				continue;
			}

			ServletContextListenerExceptionAdapter
				servletContextListenerExceptionAdaptor =
					new ServletContextListenerExceptionAdapter(
						(ServletContextListener)
							listenerDefinition.getEventListener(),
						servletContext);

			ServiceRegistration<?> serviceRegistration =
				_bundleContext.registerService(
					ServletContextListener.class,
					servletContextListenerExceptionAdaptor, properties);

			Exception exception =
				servletContextListenerExceptionAdaptor.getException();

			if (exception != null) {
				serviceRegistration.unregister();

				throw exception;
			}

			_listenerServiceRegistrations.add(serviceRegistration);
		}
	}

	protected void initServletContainerInitializers(
			Bundle bundle, ServletContext servletContext)
		throws IOException {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Enumeration<URL> initializerResources = bundle.getResources(
			"META-INF/services/javax.servlet.ServletContainerInitializer");

		if (initializerResources == null) {
			return;
		}

		while (initializerResources.hasMoreElements()) {
			URL url = initializerResources.nextElement();

			try (InputStream inputStream = url.openStream()) {
				Collection<String> fqcns = new ArrayList<>();

				StringUtil.readLines(inputStream, fqcns);

				for (String fqcn : fqcns) {
					int index = fqcn.indexOf(StringPool.POUND);

					if (index == 0) {
						continue;
					}

					if (index > 0) {
						fqcn = fqcn.substring(0, index);
					}

					fqcn = fqcn.trim();

					if (Validator.isNotNull(fqcn)) {
						processServletContainerInitializerClass(
							fqcn, bundle, bundleWiring, servletContext);
					}
				}
			}
			catch (IOException ioe) {
				_logger.log(Logger.LOG_ERROR, ioe.getMessage(), ioe);
			}
		}
	}

	protected void initServlets(
			Map<String, ServletDefinition> servletDefinitions,
			ModifiableServletContext modifiableServletContext)
		throws Exception {

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

			String jspFile = servletDefinition.getJspFile();
			List<String> urlPatterns = servletDefinition.getURLPatterns();

			if (urlPatterns.isEmpty() && (jspFile != null)) {
				urlPatterns.add(jspFile);
			}

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				urlPatterns);

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
				new ServletExceptionAdapter(
					servletDefinition.getServlet(), modifiableServletContext);

			ServiceRegistration<Servlet> serviceRegistration =
				_bundleContext.registerService(
					Servlet.class, servletExceptionAdaptor, properties);

			Exception exception = servletExceptionAdaptor.getException();

			if (exception != null) {
				serviceRegistration.unregister();

				throw exception;
			}

			_servletServiceRegistrations.add(serviceRegistration);
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

		HandlesTypes handledTypes = initializerClass.getAnnotation(
			HandlesTypes.class);

		if (handledTypes == null) {
			handledTypes = _NULL_HANDLES_TYPES;
		}

		Class<?>[] handledTypesArray = handledTypes.value();

		if (handledTypesArray == null) {
			handledTypesArray = new Class<?>[0];
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

	protected void scanTLDsForListeners(
		WebXMLDefinition webXMLDefinition, ServletContext servletContext) {

		List<String> listenerClassNames = new ArrayList<>();

		JspServlet.scanTLDs(_bundle, servletContext, listenerClassNames);

		for (String listenerClassName : listenerClassNames) {
			try {
				Class<?> clazz = _bundle.loadClass(listenerClassName);

				Class<? extends EventListener> eventListenerClass =
					clazz.asSubclass(EventListener.class);

				EventListener eventListener = eventListenerClass.newInstance();

				ListenerDefinition listenerDefinition =
					new ListenerDefinition();

				listenerDefinition.setEventListener(eventListener);

				webXMLDefinition.addListenerDefinition(listenerDefinition);
			}
			catch (Exception e) {
				_logger.log(
					Logger.LOG_ERROR,
					"Bundle " + _bundle + " is unable to load listener " +
						listenerClassName);
			}
		}
	}

	private static final HandlesTypes _NULL_HANDLES_TYPES = new HandlesTypes() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return null;
		}

		@Override
		public Class<?>[] value() {
			return new Class<?>[0];
		}

	};

	private static final String _VENDOR = "Liferay, Inc.";

	private final Bundle _bundle;
	private final ClassLoader _bundleClassLoader;
	private final BundleContext _bundleContext;
	private String _contextName;
	private final Set<ServiceRegistration<Filter>> _filterServiceRegistrations =
		new ConcurrentSkipListSet<>();
	private final Set<ServiceRegistration<?>> _listenerServiceRegistrations =
		new ConcurrentSkipListSet<>(
			new ListenerServiceRegistrationComparator());
	private final Logger _logger;
	private ServiceReference<ServletContextHelperRegistration>
		_servletContextHelperRegistrationServiceReference;
	private final Set<ServiceRegistration<Servlet>>
		_servletServiceRegistrations = new ConcurrentSkipListSet<>();

}