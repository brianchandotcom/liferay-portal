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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;

/**
 * @author     Carlos Sierra Andrés
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil}
 */
@Deprecated
public class ResourceBundleLoaderUtil {

	public static ResourceBundleLoader getPortalResourceBundleLoader() {
		return _portalResourceBundleLoader;
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByBundleSymbolicName(String bundleSymbolicName) {

		return _bundleSymbolicNameServiceTrackerMap.getService(
			bundleSymbolicName);
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByServletContextName(String servletContextName) {

		return _servletContextNameServiceTrackerMap.getService(
			servletContextName);
	}

	public static void setPortalResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_portalResourceBundleLoader = resourceBundleLoader;
	}

	private ResourceBundleLoaderUtil() {
	}

	private static final ServiceTrackerMap<String, ResourceBundleLoader>
		_bundleSymbolicNameServiceTrackerMap;
	private static ResourceBundleLoader _portalResourceBundleLoader;
	private static final ServiceTrackerMap<String, ResourceBundleLoader>
		_servletContextNameServiceTrackerMap;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_bundleSymbolicNameServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ResourceBundleLoader.class,
				"bundle.symbolic.name");
		_servletContextNameServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ResourceBundleLoader.class,
				"servlet.context.name");
	}

}