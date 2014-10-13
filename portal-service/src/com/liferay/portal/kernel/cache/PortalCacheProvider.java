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

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalCacheProvider {

	public static PortalCacheManager<? extends Serializable, ?>
		getPortalCacheManager(String portalCacheManagerName) {

		return _portalCacheManagers.get(portalCacheManagerName);
	}

	public static Collection<PortalCacheManager<? extends Serializable, ?>>
		getPortalCacheManagers() {

		return Collections.unmodifiableCollection(
			_portalCacheManagers.values());
	}

	public static void registerPortalCacheManager(
		PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

		_portalCacheManagers.put(
			portalCacheManager.getName(), portalCacheManager);
	}

	public static void unregisterPortalCacheManager(
		String portalCacheManagerName) {

		_portalCacheManagers.remove(portalCacheManagerName);
	}

	private static final
		Map<String, PortalCacheManager<? extends Serializable, ?>>
			_portalCacheManagers =
				new ConcurrentHashMap
					<String, PortalCacheManager<? extends Serializable, ?>>();

}