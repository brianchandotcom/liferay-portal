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
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Peter Fellwock
 */
public class PortalWebResourcesUtil {

	public static String getContextPath(String resourceType) {
		return getModuleContextPath(resourceType);
	}

	public static long getLastModified(String resourceType) {
		PortalWebResources portalWebResources = getPortalWebResources(
			resourceType);

		return portalWebResources.getLastModified();
	}

	public static String getModuleContextPath(String resourceType) {
		PortalWebResources portalWebResources = getPortalWebResources(
			resourceType);

		return portalWebResources.getContextPath();
	}

	public static String getPathResourceType(String path) {
		for (PortalWebResources portalWebResources :
				_instance._getPortalWebResourcesList()) {

			if (path.contains(portalWebResources.getContextPath())) {
				return portalWebResources.getResourceType();
			}
		}

		return null;
	}

	public static ServletContext getPathServletContext(String path) {
		for (PortalWebResources portalWebResources :
				_instance._getPortalWebResourcesList()) {

			ServletContext servletContext =
				portalWebResources.getServletContext();

			URL url = getResource(servletContext, path);

			if (url != null) {
				return servletContext;
			}
		}

		return null;
	}

	public static PortalWebResources getPortalWebResources(
		String resourceType) {

		for (PortalWebResources portalWebResources :
				_instance._getPortalWebResourcesList()) {

			if (resourceType.equals(portalWebResources.getResourceType())) {
				return portalWebResources;
			}
		}

		return null;
	}

	public static URL getResource(ServletContext servletContext, String path) {
		path = stripContextPath(servletContext, path);

		try {
			URL url = servletContext.getResource(path);

			if (url != null) {
				return url;
			}
		}
		catch (MalformedURLException murle) {
		}

		return null;
	}

	public static URL getResource(String path) {
		ServletContext servletContext = getPathServletContext(path);

		if (servletContext != null) {
			return getResource(servletContext, path);
		}

		return null;
	}

	public static ServletContext getServletContext(String resourceType) {
		PortalWebResources portalWebResources = getPortalWebResources(
			resourceType);

		return portalWebResources.getServletContext();
	}

	public static boolean hasContextPath(String requestURI) {
		for (PortalWebResources portalWebResources :
				_instance._getPortalWebResourcesList()) {

			if (requestURI.startsWith(portalWebResources.getContextPath())) {
				return true;
			}
		}

		return false;
	}

	public static boolean isAvailable(String path) {
		URL url = getResource(path);

		if (url != null) {
			return true;
		}

		return false;
	}

	public static String stripContextPath(
		ServletContext servletContext, String path) {

		String contextPath = servletContext.getContextPath();

		if (path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}

		return path;
	}

	private PortalWebResourcesUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortalWebResources.class,
			new PortalWebResourcesServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Collection<PortalWebResources> _getPortalWebResourcesList() {
		return _portalWebResourcesMap.values();
	}

	private static final PortalWebResourcesUtil _instance =
		new PortalWebResourcesUtil();

	private final Map<ServiceReference<PortalWebResources>, PortalWebResources>
		_portalWebResourcesMap = new ConcurrentHashMap<>();
	private final ServiceTracker<PortalWebResources, PortalWebResources>
		_serviceTracker;

	private class PortalWebResourcesServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortalWebResources, PortalWebResources> {

		@Override
		public PortalWebResources addingService(
			ServiceReference<PortalWebResources> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortalWebResources portalWebResources = registry.getService(
				serviceReference);

			_portalWebResourcesMap.put(serviceReference, portalWebResources);

			return portalWebResources;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalWebResources> serviceReference,
			PortalWebResources portalWebResources) {
		}

		@Override
		public void removedService(
			ServiceReference<PortalWebResources> serviceReference,
			PortalWebResources portalWebResources) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_portalWebResourcesMap.remove(serviceReference);
		}

	}

}