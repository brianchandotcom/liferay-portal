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

package com.liferay.shielded.container.internal.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Shuyang Zhou
 */
public class ServletContextDelegate {

	public ServletContextDelegate(
		ProxyFactory proxyFactory, ServletContext servletContext,
		ClassLoader classLoader) {

		_proxyFactory = proxyFactory;
		_servletContext = servletContext;
		_classLoader = classLoader;
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, Class<? extends Filter> filterClass) {

		try {
			return _servletContext.addFilter(
				filterName,
				new FilterWrapper(
					_proxyFactory, new LazyInstanceSupplier<>(filterClass),
					_proxiedServletContext));
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new RuntimeException(noSuchMethodException);
		}
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, Filter filter) {

		return _servletContext.addFilter(
			filterName,
			new FilterWrapper(
				_proxyFactory, () -> filter, _proxiedServletContext));
	}

	public FilterRegistration.Dynamic addFilter(
		String filterName, String filterClassName) {

		try {
			return addFilter(
				filterName,
				(Class<? extends Filter>)_classLoader.loadClass(
					filterClassName));
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
	}

	public void addListener(Class<? extends EventListener> listenerClass) {
		try {
			Constructor<? extends EventListener> constructor =
				listenerClass.getConstructor();

			addListener(constructor.newInstance());
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	public void addListener(String listenerClassName) {
		try {
			addListener(
				(Class<? extends EventListener>)_classLoader.loadClass(
					listenerClassName));
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
	}

	public <T extends EventListener> void addListener(T t) {
		Class<?> clazz = t.getClass();

		Set<Class<?>> interfaceClasses = new LinkedHashSet<>();

		while (clazz != null) {
			for (Class<?> interfaceClass : clazz.getInterfaces()) {
				interfaceClasses.add(interfaceClass);
			}

			clazz = clazz.getSuperclass();
		}

		InvocationHandler invocationHandler =
			new EventListenerInvocationHandler(
				_proxiedServletContext, _classLoader, t);

		if (interfaceClasses.contains(HttpSessionListener.class)) {
			invocationHandler = new HttpSessionListenerInvocationHandlerWrapper(
				invocationHandler, _proxyFactory, _classLoader);
		}

		_servletContext.addListener(
			_proxyFactory.<T>newProxyInstance(
				_classLoader, interfaceClasses.toArray(new Class<?>[0]),
				invocationHandler));
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, Class<? extends Servlet> servletClass) {

		try {
			return _servletContext.addServlet(
				servletName,
				new ServletWrapper(
					_proxyFactory, new LazyInstanceSupplier<>(servletClass),
					_proxiedServletContext));
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new RuntimeException(noSuchMethodException);
		}
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		return _servletContext.addServlet(
			servletName,
			new ServletWrapper(
				_proxyFactory, () -> servlet, _proxiedServletContext));
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, String servletClassName) {

		try {
			return addServlet(
				servletName,
				(Class<? extends Servlet>)_classLoader.loadClass(
					servletClassName));
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
	}

	public Object getAttribute(String name) {
		return _servletContext.getAttribute(_encodeName(name));
	}

	public Enumeration<String> getAttributeNames() {
		List<String> names = new ArrayList<>();

		Enumeration<String> enumeration = _servletContext.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			names.add(_decodeName(enumeration.nextElement()));
		}

		return Collections.enumeration(names);
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public String getContextPath() {
		String contextPath = _servletContext.getContextPath();

		if (contextPath.equals("/")) {
			return "";
		}

		return contextPath;
	}

	public String getInitParameter(String name) {
		return _servletContext.getInitParameter(_encodeName(name));
	}

	public Enumeration<String> getInitParameterNames() {
		List<String> names = new ArrayList<>();

		Enumeration<String> enumeration =
			_servletContext.getInitParameterNames();

		while (enumeration.hasMoreElements()) {
			names.add(_decodeName(enumeration.nextElement()));
		}

		return Collections.enumeration(names);
	}

	public void removeAttribute(String name) {
		_servletContext.removeAttribute(_encodeName(name));
	}

	public void setAttribute(String name, Object object) {
		_servletContext.setAttribute(_encodeName(name), object);
	}

	public boolean setInitParameter(String name, String value) {
		return _servletContext.setInitParameter(_encodeName(name), value);
	}

	public void setProxiedServletContext(ServletContext proxiedServletContext) {
		_proxiedServletContext = proxiedServletContext;
	}

	private String _decodeName(String name) {
		if (name.startsWith(_LIFERAY_NAMESPACE)) {
			return name.substring(_LIFERAY_NAMESPACE.length());
		}

		return name;
	}

	private String _encodeName(String name) {
		if (name.startsWith(_APACHE_NAMESPACE)) {
			return _LIFERAY_NAMESPACE.concat(name);
		}

		return name;
	}

	private static final String _APACHE_NAMESPACE = "org.apache.";

	private static final String _LIFERAY_NAMESPACE = "com.liferay.";

	private final ClassLoader _classLoader;
	private ServletContext _proxiedServletContext;
	private final ProxyFactory _proxyFactory;
	private final ServletContext _servletContext;

}