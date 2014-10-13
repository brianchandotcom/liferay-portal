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

package com.liferay.portal.portlet.tracker.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class BundleServletContextInvocationHandler
	implements InvocationHandler {

	public BundleServletContextInvocationHandler(
		ServletContext servletContext, BundlePortletApp bundlePortletApp,
		ClassLoader classLoader) {

		_servletContext = servletContext;

		_bundleServletContext = new BundleServletContext(
			servletContext, bundlePortletApp, classLoader);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Method bundleServletContextInvocationHandlerMethod =
			_bundleServletContextMethods.get(method);

		if (bundleServletContextInvocationHandlerMethod != null) {
			return bundleServletContextInvocationHandlerMethod.invoke(
				_bundleServletContext, args);
		}

		return method.invoke(_servletContext, args);
	}

	private static final Map<Method, Method> _bundleServletContextMethods =
		new HashMap<Method, Method>();

	static {
		Method[] bundleServletContextMethods =
			BundleServletContext.class.getDeclaredMethods();

		for (Method bundleServletContextMethod : bundleServletContextMethods) {
			String methodName = bundleServletContextMethod.getName();

			Class<?>[] parameterTypes =
				bundleServletContextMethod.getParameterTypes();

			try {
				Method servletContextMethod = ServletContext.class.getMethod(
					methodName, parameterTypes);

				_bundleServletContextMethods.put(
					servletContextMethod, bundleServletContextMethod);
			}
			catch (NoSuchMethodException nsme) {
			}
		}
	}

	private final BundleServletContext _bundleServletContext;
	private final ServletContext _servletContext;

}