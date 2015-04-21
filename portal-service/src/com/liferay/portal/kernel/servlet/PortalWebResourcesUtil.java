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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

/**
 * @author Peter Fellwock
 */
public class PortalWebResourcesUtil {

	public static String getContextPath() {
		return getPortalWebResources().getContextPath();
	}

	public static long getLastModified() {
		return getPortalWebResources().getLastModified();
	}

	public static PortalWebResources getPortalWebResources() {
		return _instance._serviceTracker.getService();
	}

	public static ServletContext getServletContext() {
		return getPortalWebResources().getServletContext();
	}

	public static boolean isResourceAvailable(String path) {
		String contextPath = getContextPath();

		if (path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}

		try {
			ServletContext servletContext = getServletContext();

			URL url = servletContext.getResource(path);

			if (url != null) {
				return true;
			}
		}
		catch (MalformedURLException e) {
		}

		return false;
	}

	private PortalWebResourcesUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(PortalWebResources.class);

		_serviceTracker.open();
	}

	private static final PortalWebResourcesUtil _instance =
		new PortalWebResourcesUtil();

	private final ServiceTracker<PortalWebResources, PortalWebResources>
		_serviceTracker;

}