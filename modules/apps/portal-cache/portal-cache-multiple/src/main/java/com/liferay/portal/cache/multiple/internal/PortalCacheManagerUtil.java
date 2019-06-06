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

package com.liferay.portal.cache.multiple.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tina Tian
 */
@Component(enabled = false, immediate = true, service = {})
public class PortalCacheManagerUtil {

	public static PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager(String portalCacheManagerName) {

		ServiceTrackerMap<String, PortalCacheManager<? extends Serializable, ?>>
			serviceTrackerMap = _serviceTrackerMap;

		PortalCacheManager<? extends Serializable, ?> portalCacheManager = null;

		if (serviceTrackerMap != null) {
			portalCacheManager = serviceTrackerMap.getService(
				portalCacheManagerName);
		}

		if (portalCacheManager != null) {
			return portalCacheManager;
		}

		throw new IllegalStateException(
			"Unable to get portal cache manager " + portalCacheManagerName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<PortalCacheManager<? extends Serializable, ?>>)
				(Class<?>)PortalCacheManager.class,
			null,
			(serviceReference, emitter) -> {
				PortalCacheManager<? extends Serializable, ?>
					portalCacheManager = bundleContext.getService(
						serviceReference);

				emitter.emit(portalCacheManager.getPortalCacheManagerName());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static volatile ServiceTrackerMap
		<String, PortalCacheManager<? extends Serializable, ?>>
			_serviceTrackerMap;

}