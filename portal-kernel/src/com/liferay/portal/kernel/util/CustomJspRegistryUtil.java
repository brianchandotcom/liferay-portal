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

package com.liferay.portal.kernel.util;

import java.util.Set;

/**
 * @author Ryan Park
 * @author Brian Wing Shun Chan
 */
public class CustomJspRegistryUtil {

	public static String getCustomJspFileName(
		String servletContextName, String fileName) {

		return getCustomJspRegistry().getCustomJspFileName(
			servletContextName, fileName);
	}

	public static CustomJspRegistry getCustomJspRegistry() {
		return _customJspRegistry;
	}

	public static String getDisplayName(String servletContextName) {
		return getCustomJspRegistry().getDisplayName(servletContextName);
	}

	public static Set<String> getServletContextNames() {
		return getCustomJspRegistry().getServletContextNames();
	}

	public static void registerServletContextName(
		String servletContextName, String displayName) {

		getCustomJspRegistry().registerServletContextName(
			servletContextName, displayName);
	}

	public static void unregisterServletContextName(String servletContextName) {
		getCustomJspRegistry().unregisterServletContextName(servletContextName);
	}

	public void setCustomJspRegistry(CustomJspRegistry customJspRegistry) {
		_customJspRegistry = customJspRegistry;
	}

	private static CustomJspRegistry _customJspRegistry;

}