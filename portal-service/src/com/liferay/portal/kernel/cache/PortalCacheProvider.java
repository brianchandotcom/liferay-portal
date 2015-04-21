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

		return _instance._getPortalCacheManager(portalCacheManagerName);
	}

	public static Collection<PortalCacheManager<? extends Serializable, ?>>
		getPortalCacheManagers() {

		return _instance._getPortalCacheManagers();
	}

	public static void registerPortalCacheManager(
		PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

		_instance._registerPortalCacheManager(portalCacheManager);
	}

	public static void unregisterPortalCacheManager(
		String portalCacheManagerName) {

		_instance._unregisterPortalCacheManager(portalCacheManagerName);
	}

	private PortalCacheManager<? extends Serializable, ?>
		_getPortalCacheManager(String portalCacheManagerName) {

		return _portalCacheManagers.get(portalCacheManagerName);
	}

	private Collection<PortalCacheManager<? extends Serializable, ?>>
		_getPortalCacheManagers() {

		return Collections.unmodifiableCollection(
			_portalCacheManagers.values());
	}

	private void _registerPortalCacheManager(
		PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

		_portalCacheManagers.put(
			portalCacheManager.getName(), portalCacheManager);
	}

	private void _unregisterPortalCacheManager(String portalCacheManagerName) {
		_portalCacheManagers.remove(portalCacheManagerName);
	}

	private static final PortalCacheProvider _instance =
		new PortalCacheProvider();

	private static final
		Map<String, PortalCacheManager<? extends Serializable, ?>>
			_portalCacheManagers = new ConcurrentHashMap<>();

}