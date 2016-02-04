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

package com.liferay.portal.kernel.resource;

import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ResourceBundleLoaderUtil {

	public static ResourceBundleLoader
		getResourceBundleLoaderByBundleSymbolicName(String bundleSymbolicName) {

		return _instance._resourceBundleLoaderByBundleSymbolicName.getService(
			bundleSymbolicName);
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByServletContextName(String servletContextName) {

		return _instance._resourceBundleLoaderByServletName.getService(
			servletContextName);
	}

	private ResourceBundleLoaderUtil() {
		_resourceBundleLoaderByBundleSymbolicName =
			ServiceTrackerCollections.openSingleValueMap(
				ResourceBundleLoader.class, "bundle.symbolic.name");
		_resourceBundleLoaderByServletName =
			ServiceTrackerCollections.openSingleValueMap(
				ResourceBundleLoader.class, "servlet.context.name");
	}

	private static final ResourceBundleLoaderUtil _instance =
		new ResourceBundleLoaderUtil();

	private final ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaderByBundleSymbolicName;
	private final ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaderByServletName;

}