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

package com.liferay.portal.kernel.servlet;

import javax.servlet.Servlet;

/**
 * @author Shuyang Zhou
 */
public class DirectServletRegistryUtil {

	public static void clearServlets() {
		getDirectServletRegistry().clearServlets();
	}

	public static DirectServletRegistry getDirectServletRegistry() {
		return _directServletRegistry;
	}

	public static Servlet getServlet(String path) {
		return getDirectServletRegistry().getServlet(path);
	}

	public static void putServlet(String path, Servlet servlet) {
		getDirectServletRegistry().putServlet(path, servlet);
	}

	public void setDirectServletRegistry(
		DirectServletRegistry directServletRegistry) {

		_directServletRegistry = directServletRegistry;
	}

	private static DirectServletRegistry _directServletRegistry;

}