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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = ResourceBundleLoaderProvider.class)
public class ResourceBundleLoaderProvider {

	public ResourceBundleLoader getResourceBundleLoader(
		String bundleSymbolicName) {

		ResourceBundleLoader resourceBundleLoader =
			_resourceBundleLoaders.getService(bundleSymbolicName);

		if (resourceBundleLoader == null) {
			return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}
		else {
			return new AggregateResourceBundleLoader(
				resourceBundleLoader,
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_resourceBundleLoaders = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ResourceBundleLoader.class, "bundle.symbolic.name");
	}

	@Deactivate
	protected void deactivate() {
		_resourceBundleLoaders.close();
	}

	private ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaders;

}