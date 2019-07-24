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

package com.liferay.portal.kernel.internal.util;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.Enumeration;
import java.util.function.Function;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class ContextResourcePathsUtil {

	public static <T> T visitResources(
		PortletContext portletContext, String path, String filePattern,
		Function<Enumeration<URL>, T> function) {

		return _visitResources(
			portletContext.getAttribute("osgi-bundlecontext"), path,
			filePattern, function);
	}

	public static <T> T visitResources(
		ServletContext servletContext, String path, String filePattern,
		Function<Enumeration<URL>, T> function) {

		return _visitResources(
			servletContext.getAttribute("osgi-bundlecontext"), path,
			filePattern, function);
	}

	private static <T> T _visitResources(
		Object bundleContext, String path, String filePattern,
		Function<Enumeration<URL>, T> function) {

		if (bundleContext == null) {
			return null;
		}

		Class<?> clazz = bundleContext.getClass();

		try {
			Method method = clazz.getMethod("getBundle");

			Object bundle = method.invoke(bundleContext);

			clazz = bundle.getClass();

			method = clazz.getMethod(
				"findEntries", String.class, String.class, boolean.class);

			return function.apply(
				(Enumeration<URL>)method.invoke(
					bundle, path, filePattern, true));
		}
		catch (ReflectiveOperationException roe) {
			return null;
		}
	}

}