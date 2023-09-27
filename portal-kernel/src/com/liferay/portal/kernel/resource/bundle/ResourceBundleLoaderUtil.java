/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.resource.bundle;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;

/**
 * @author Carlos Sierra Andrés
 */
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