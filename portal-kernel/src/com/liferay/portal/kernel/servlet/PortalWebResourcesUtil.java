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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.registry.collections.ServiceReferenceMapperFactory;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

/**
 * @author Peter Fellwock
 */
public class PortalWebResourcesUtil {

	public static String getContextPath(String resourceType) {
		String pathProxy = PortalUtil.getPathProxy();

		return pathProxy.concat(getModuleContextPath(resourceType));
	}

	public static long getLastModified(String resourceType) {
		PortalWebResources portalWebResources = _resourceTypeMap.getService(
			resourceType);

		if (portalWebResources == null) {
			return -1;
		}

		return portalWebResources.getLastModified();
	}

	public static String getModuleContextPath(String resourceType) {
		PortalWebResources portalWebResources = _resourceTypeMap.getService(
			resourceType);

		if (portalWebResources == null) {
			return StringPool.BLANK;
		}

		return portalWebResources.getContextPath();
	}

	public static long getPathLastModified(
		String requestURI, long defaultValue) {

		for (String contextPath : _contextPathMap.keySet()) {
			if (requestURI.equals(Portal.PATH_MODULE) ||
				contextPath.startsWith(requestURI)) {

				PortalWebResources portalWebResources =
					_contextPathMap.getService(contextPath);

				return portalWebResources.getLastModified();
			}
		}

		return defaultValue;
	}

	public static String getPathResourceType(String path) {
		for (String contextPath : _contextPathMap.keySet()) {
			if (path.contains(contextPath)) {
				PortalWebResources portalWebResources =
					_contextPathMap.getService(contextPath);

				return portalWebResources.getResourceType();
			}
		}

		return null;
	}

	public static ServletContext getPathServletContext(String path) {
		for (String contextPath : _contextPathMap.keySet()) {
			PortalWebResources portalWebResources = _contextPathMap.getService(
				contextPath);

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

		return _resourceTypeMap.getService(resourceType);
	}

	public static URL getResource(ServletContext servletContext, String path) {
		if (servletContext == null) {
			return null;
		}

		path = stripContextPath(servletContext, path);

		try {
			URL url = servletContext.getResource(path);

			if (url != null) {
				return url;
			}
		}
		catch (MalformedURLException malformedURLException) {
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
		PortalWebResources portalWebResources = _resourceTypeMap.getService(
			resourceType);

		return portalWebResources.getServletContext();
	}

	public static boolean hasContextPath(String requestURI) {
		for (String contextPath : _contextPathMap.keySet()) {
			if (requestURI.startsWith(contextPath)) {
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

	private static final ServiceTrackerMap<String, PortalWebResources>
		_contextPathMap = ServiceTrackerCollections.openSingleValueMap(
			PortalWebResources.class, null,
			ServiceReferenceMapperFactory.create(
				(portalWebResources, emitter) -> emitter.emit(
					portalWebResources.getContextPath())));
	private static final ServiceTrackerMap<String, PortalWebResources>
		_resourceTypeMap = ServiceTrackerCollections.openSingleValueMap(
			PortalWebResources.class, null,
			ServiceReferenceMapperFactory.create(
				(portalWebResources, emitter) -> emitter.emit(
					portalWebResources.getResourceType())));

}