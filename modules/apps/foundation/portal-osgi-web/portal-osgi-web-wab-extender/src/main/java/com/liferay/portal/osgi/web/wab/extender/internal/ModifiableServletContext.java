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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ModifiableServletContext implements InvocationHandler {

	public static ServletContext createInstance(
		ServletContext servletContext, WabBundleProcessor wabBundleProcessor) {

		return (ServletContext)Proxy.newProxyInstance(
			ModifiableServletContext.class.getClassLoader(), _INTERFACES,
			new ModifiableServletContext(servletContext, wabBundleProcessor));
	}

	public ModifiableServletContext(
		ServletContext servletContext, WabBundleProcessor wabBundleProcessor) {

		_servletContext = servletContext;
		_wabBundleProcessor = wabBundleProcessor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		return method.invoke(_servletContext, args);
	}

	private static final Class<?>[] _INTERFACES = new Class<?>[] {
		ServletContext.class
	};

	private final ServletContext _servletContext;
	private final WabBundleProcessor _wabBundleProcessor;

}