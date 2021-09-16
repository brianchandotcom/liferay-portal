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

package com.liferay.portal.kernel.cache;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;

/**
 * @author Brian Wing Shun Chan
 */
public class CacheRegistryUtil {

	public static void clear() {
		for (CacheRegistryItem cacheRegistryItem :
				_cacheRegistryItems.values()) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invalidating " + cacheRegistryItem.getRegistryName());
			}

			cacheRegistryItem.invalidate();
		}
	}

	public static void clear(String name) {
		CacheRegistryItem cacheRegistryItem = _cacheRegistryItems.getService(
			name);

		if (cacheRegistryItem == null) {
			_log.error("No cache registry found with name " + name);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalidating " + name);
			}

			cacheRegistryItem.invalidate();
		}
	}

	public static boolean isActive() {
		return _active;
	}

	public static void setActive(boolean active) {
		_active = active;

		if (!active) {
			clear();
		}
	}

	private CacheRegistryUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CacheRegistryUtil.class);

	private static volatile boolean _active = true;
	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, CacheRegistryItem>
		_cacheRegistryItems = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, CacheRegistryItem.class, null,
			(serviceReference, emitter) -> {
				CacheRegistryItem cacheRegistryItem = _bundleContext.getService(
					serviceReference);

				emitter.emit(cacheRegistryItem.getRegistryName());

				_bundleContext.ungetService(serviceReference);
			});

}