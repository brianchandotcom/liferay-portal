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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.FilterDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ListenerDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.ServletDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.wab.extender.internal.registration.FilterRegistrationImpl;
import com.liferay.portal.osgi.web.wab.extender.internal.registration.ServletRegistrationImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Registration.Dynamic;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 */
public class ModifiableServletContextAdapter
	implements InvocationHandler, ModifiableServletContext {

	public static ServletContext createInstance(
		ServletContext servletContext, BundleContext bundleContext,
		WebXMLDefinition webXMLDefinition, Logger logger) {

		return (ServletContext)Proxy.newProxyInstance(
			ModifiableServletContextAdapter.class.getClassLoader(), _INTERFACES,
			new ModifiableServletContextAdapter(
				servletContext, bundleContext, webXMLDefinition, logger));
	}

	public ModifiableServletContextAdapter(
		ServletContext servletContext, BundleContext bundleContext,
		WebXMLDefinition webXMLDefinition, Logger logger) {

		_servletContext = servletContext;
		_bundleContext = bundleContext;
		_webXMLDefinition = webXMLDefinition;
		_logger = logger;

		_bundle = _bundleContext.getBundle();
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, Class<? extends Filter> filterClass) {

		return addFilter(filterName, filterClass.getName());
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, Filter filter) {

		FilterRegistrationImpl filterRegistrationImpl =
			getFilterRegistrationImpl(filterName);

		if (filterRegistrationImpl == null) {
			filterRegistrationImpl = new FilterRegistrationImpl();
		}

		Class<? extends Filter> filterClass = filter.getClass();

		filterRegistrationImpl.setClassName(filterClass.getName());
		filterRegistrationImpl.setName(filterName);
		filterRegistrationImpl.setInstance(filter);

		_filterRegistrations.put(filterName, filterRegistrationImpl);

		return filterRegistrationImpl;
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, String className) {

		FilterRegistrationImpl filterRegistrationImpl =
			getFilterRegistrationImpl(filterName);

		if (filterRegistrationImpl == null) {
			filterRegistrationImpl = new FilterRegistrationImpl();
		}

		filterRegistrationImpl.setClassName(className);
		filterRegistrationImpl.setName(filterName);

		_filterRegistrations.put(filterName, filterRegistrationImpl);

		return filterRegistrationImpl;
	}

	public void addListener(Class<? extends EventListener> eventListenerClass) {
		_eventListeners.put(eventListenerClass, null);
	}

	public void addListener(String className) {
		try {
			Class<?> clazz = _bundle.loadClass(className);

			if (!EventListener.class.isAssignableFrom(clazz)) {
				throw new IllegalArgumentException();
			}

			Class<? extends EventListener> eventListenerClass =
				clazz.asSubclass(EventListener.class);

			_eventListeners.put(eventListenerClass, null);
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load filter " + className);

			throw new IllegalArgumentException(e);
		}
	}

	public <T extends EventListener> void addListener(T t) {
		_eventListeners.put(t.getClass(), t);
	}

	public Dynamic addServlet(
		String servletName, Class<? extends Servlet> servletClass) {

		return addServlet(servletName, servletClass.getName());
	}

	public Dynamic addServlet(String servletName, Servlet servlet) {
		ServletRegistrationImpl servletRegistrationImpl =
			getServletRegistrationImpl(servletName);

		if (servletRegistrationImpl == null) {
			servletRegistrationImpl = new ServletRegistrationImpl();
		}

		Class<? extends Servlet> servetClass = servlet.getClass();

		servletRegistrationImpl.setClassName(servetClass.getName());
		servletRegistrationImpl.setName(servletName);
		servletRegistrationImpl.setInstance(servlet);

		_servletRegistrations.put(servletName, servletRegistrationImpl);

		return servletRegistrationImpl;
	}

	public Dynamic addServlet(String servletName, String className) {
		ServletRegistrationImpl servletRegistrationImpl =
			getServletRegistrationImpl(servletName);

		if (servletRegistrationImpl == null) {
			servletRegistrationImpl = new ServletRegistrationImpl();
		}

		servletRegistrationImpl.setClassName(className);
		servletRegistrationImpl.setName(servletName);

		_servletRegistrations.put(servletName, servletRegistrationImpl);

		return servletRegistrationImpl;
	}

	public <T extends Filter> T createFilter(Class<T> clazz)
		throws ServletException {

		try {
			return clazz.newInstance();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load filter " + clazz);
			throw new ServletException(t);
		}
	}

	public <T extends EventListener> T createListener(Class<T> clazz)
		throws ServletException {

		try {
			return clazz.newInstance();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load listener " + clazz);

			throw new ServletException(t);
		}
	}

	public <T extends Servlet> T createServlet(Class<T> clazz)
		throws ServletException {

		try {
			return clazz.newInstance();
		}
		catch (Throwable t) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load servlet " + clazz);

			throw new ServletException(t);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ServletContext)) {
			return true;
		}

		ServletContext servletContext = (ServletContext)obj;

		if (obj instanceof ModifiableServletContext) {
			ModifiableServletContext modifiableServletContext =
				(ModifiableServletContext)obj;

			servletContext =
				modifiableServletContext.getWrappedServletContext();
		}

		return servletContext.equals(_servletContext);
	}

	@Override
	public Bundle getBundle() {
		return _bundle;
	}

	public FilterRegistration getFilterRegistration(String filterName) {
		return getFilterRegistrationImpl(filterName);
	}

	public FilterRegistrationImpl getFilterRegistrationImpl(String filterName) {
		return _filterRegistrations.get(filterName);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return getFilterRegistrationsImpl();
	}

	public Map<String, ? extends FilterRegistrationImpl>
		getFilterRegistrationsImpl() {

		return _filterRegistrations;
	}

	@Override
	public List<ListenerDefinition> getListenerDefinitions() {
		List<ListenerDefinition> listenerDefinitions = new ArrayList<>();

		for (Entry<Class<? extends EventListener>, EventListener> entry :
				_eventListeners.entrySet()) {

			if (entry.getValue() != null) {
				continue;
			}

			Class<? extends EventListener> eventListenerClass = entry.getKey();

			try {
				EventListener eventListener = eventListenerClass.newInstance();

				ListenerDefinition listenerDefinition =
					new ListenerDefinition();

				listenerDefinition.setEventListener(eventListener);

				listenerDefinitions.add(listenerDefinition);
			}
			catch (Exception e) {
				_logger.log(
					Logger.LOG_ERROR,
					"Bundle " + _bundle + " is unable to load listener " +
						eventListenerClass);
			}
		}

		return listenerDefinitions;
	}

	public Map<Class<? extends EventListener>, EventListener>
		getListenersImpl() {

		return _eventListeners;
	}

	public ServletRegistration getServletRegistration(String servletName) {
		return getServletRegistrationImpl(servletName);
	}

	public ServletRegistrationImpl getServletRegistrationImpl(
		String servletName) {

		return _servletRegistrations.get(servletName);
	}

	public Map<String, ? extends ServletRegistration>
		getServletRegistrations() {

		return getServletRegistrationsImpl();
	}

	public Map<String, ? extends ServletRegistrationImpl>
		getServletRegistrationsImpl() {

		return _servletRegistrations;
	}

	@Override
	public ServletContext getWrappedServletContext() {
		return _servletContext;
	}

	@Override
	public int hashCode() {
		return _servletContext.hashCode();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Method adapterMethod = _contextAdapterMethods.get(method);

		if (adapterMethod != null) {
			return adapterMethod.invoke(this, args);
		}

		return method.invoke(_servletContext, args);
	}

	@Override
	public void registerFilters() {
		Map<String, FilterDefinition> filterDefinitions =
			_webXMLDefinition.getFilterDefinitions();

		for (FilterRegistrationImpl filterRegistrationImpl :
				_filterRegistrations.values()) {

			if (filterRegistrationImpl.getInstance() != null) {
				continue;
			}

			String filterClassName = filterRegistrationImpl.getClassName();

			try {
				Class<?> clazz = _bundle.loadClass(filterClassName);

				Class<? extends Filter> filterClass = clazz.asSubclass(
					Filter.class);

				Filter filter = filterClass.newInstance();

				filterRegistrationImpl.setInstance(filter);

				FilterDefinition filterDefinition = new FilterDefinition();

				filterDefinition.setAsyncSupported(
					filterRegistrationImpl.isAsyncSupported());

				FilterRegistrationImpl.FilterMapping filterMapping =
					filterRegistrationImpl.getFilterMapping();

				for (DispatcherType dispatcherType :
						filterMapping.getDispatchers()) {

					filterDefinition.addDispatcher(dispatcherType.toString());
				}

				filterDefinition.setFilter(filter);
				filterDefinition.setInitParameters(
					filterRegistrationImpl.getInitParameters());
				filterDefinition.setName(filterRegistrationImpl.getName());
				filterDefinition.setServletNames(
					new ArrayList<>(
						filterRegistrationImpl.getServletNameMappings()));
				filterDefinition.setURLPatterns(
					new ArrayList<>(
						filterRegistrationImpl.getUrlPatternMappings()));

				filterDefinitions.put(
					filterRegistrationImpl.getName(), filterDefinition);
			}
			catch (Exception e) {
				_logger.log(
					Logger.LOG_ERROR,
					"Bundle " + _bundle + " is unable to load filter " +
						filterClassName);
			}
		}
	}

	@Override
	public void registerServlets() {
		Map<String, ServletDefinition> servletDefinitions =
			_webXMLDefinition.getServletDefinitions();

		for (ServletRegistrationImpl servletRegistrationImpl :
				_servletRegistrations.values()) {

			if (servletRegistrationImpl.getInstance() != null) {
				continue;
			}

			String servletClassName = servletRegistrationImpl.getClassName();

			try {
				String jspFile = servletRegistrationImpl.getJspFile();

				Servlet servlet = null;

				if (Validator.isNotNull(jspFile)) {
					servlet = new JspServletWrapper(jspFile);
				}
				else {
					Class<?> clazz = _bundle.loadClass(servletClassName);

					Class<? extends Servlet> servletClass = clazz.asSubclass(
						Servlet.class);

					servlet = servletClass.newInstance();
				}

				servletRegistrationImpl.setInstance(servlet);

				ServletDefinition servletDefinition = new ServletDefinition();

				servletDefinition.setAsyncSupported(
					servletRegistrationImpl.isAsyncSupported());
				servletDefinition.setInitParameters(
					servletRegistrationImpl.getInitParameters());
				servletDefinition.setJSPFile(
					servletRegistrationImpl.getJspFile());
				servletDefinition.setName(servletRegistrationImpl.getName());
				servletDefinition.setServlet(servlet);
				servletDefinition.setURLPatterns(
					new ArrayList<>(servletRegistrationImpl.getMappings()));

				servletDefinitions.put(
					servletRegistrationImpl.getName(), servletDefinition);
			}
			catch (Exception e) {
				_logger.log(
					Logger.LOG_ERROR,
					"Bundle " + _bundle + " is unable to load servlet " +
						servletClassName);
			}
		}
	}

	private static Map<Method, Method> _createContextAdapterMethods() {
		Map<Method, Method> methods = new HashMap<>();

		Method[] adapterMethods =
			ModifiableServletContextAdapter.class.getDeclaredMethods();

		for (Method adapterMethod : adapterMethods) {
			String name = adapterMethod.getName();
			Class<?>[] parameterTypes = adapterMethod.getParameterTypes();

			try {
				Method method = ServletContext.class.getMethod(
					name, parameterTypes);

				methods.put(method, adapterMethod);
			}
			catch (NoSuchMethodException nsme1) {
				try {
					Method method = ModifiableServletContext.class.getMethod(
						name, parameterTypes);

					methods.put(method, adapterMethod);
				}
				catch (NoSuchMethodException nsme2) {
				}
			}
		}

		try {
			Method equalsMethod = Object.class.getMethod(
				"equals", Object.class);

			Method equalsHandlerMethod =
				ModifiableServletContextAdapter.class.getMethod(
					"equals", Object.class);

			methods.put(equalsMethod, equalsHandlerMethod);

			Method hashCodeMethod = Object.class.getMethod(
				"hashCode", (Class<?>[])null);

			Method hashCodeHandlerMethod =
				ModifiableServletContextAdapter.class.getMethod(
					"hashCode", (Class<?>[])null);

			methods.put(hashCodeMethod, hashCodeHandlerMethod);
		}
		catch (NoSuchMethodException nsme) {
		}

		return Collections.unmodifiableMap(methods);
	}

	private static final Class<?>[] _INTERFACES = new Class<?>[] {
		ModifiableServletContext.class, ServletContext.class
	};

	private static final Map<Method, Method> _contextAdapterMethods;

	static {
		_contextAdapterMethods = _createContextAdapterMethods();
	}

	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private final LinkedHashMap<Class<? extends EventListener>, EventListener>
		_eventListeners = new LinkedHashMap<>();
	private final LinkedHashMap<String, FilterRegistrationImpl>
		_filterRegistrations = new LinkedHashMap<>();
	private final Logger _logger;
	private final ServletContext _servletContext;
	private final LinkedHashMap<String, ServletRegistrationImpl>
		_servletRegistrations = new LinkedHashMap<>();
	private final WebXMLDefinition _webXMLDefinition;

}