/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.servlet.PortletServlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletClassLoaderUtil {

	public static ClassLoader getClassLoader() {
		return _classLoadersByThread.get(Thread.currentThread().getId());
	}

	public static ClassLoader getClassLoader(Long threadId) {
		return _classLoadersByThread.get(threadId);
	}

	public static ClassLoader getClassLoader(String portletId) {
		PortletBag portletBag = PortletBagPool.get(portletId);

		if (portletBag == null) {
			return null;
		}

		ServletContext servletContext = portletBag.getServletContext();

		ClassLoader portletClassLoader =
			(ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

		return portletClassLoader;
	}

	public static String getServletContextName() {
		return _servletContextName;
	}

	public static void setClassLoader(Long threadId, ClassLoader classLoader) {
		_classLoadersByThread.put(threadId, classLoader);
	}

	public static void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	private static Map<Long, ClassLoader> _classLoadersByThread =
		new HashMap<Long, ClassLoader>();

	private static String _servletContextName;

}