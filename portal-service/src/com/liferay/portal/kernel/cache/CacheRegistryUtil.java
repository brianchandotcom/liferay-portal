/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Brian Wing Shun Chan
 */
public class CacheRegistryUtil {

	public static void clear() {
		getCacheRegistry().clear();
	}

	public static void clear(String name) {
		getCacheRegistry().clear(name);
	}

	public static CacheRegistry getCacheRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(CacheRegistryUtil.class);

		return _cacheRegistry;
	}

	public static boolean isActive() {
		return getCacheRegistry().isActive();
	}

	public static void register(CacheRegistryItem cacheRegistryItem) {
		getCacheRegistry().register(cacheRegistryItem);
	}

	public static void setActive(boolean active) {
		getCacheRegistry().setActive(active);
	}

	public static void setCacheRegistry(CacheRegistry cacheRegistry) {
		PortalRuntimePermission.checkSetBeanProperty(CacheRegistryUtil.class);

		_cacheRegistry = cacheRegistry;
	}

	public static void unregister(String name) {
		getCacheRegistry().unregister(name);
	}

	private static CacheRegistry _cacheRegistry;

}