/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.memory;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class MemoryPortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	public void afterPropertiesSet() {
		_memoryPortalCaches =
			new ConcurrentHashMap<String, MemoryPortalCache<K, V>>(
				_cacheManagerInitialCapacity);
	}

	@Override
	public void clearAll() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.removeAll();
		}
	}

	@Override
	public void destroy() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.destroy();
		}
	}

	@Override
	public PortalCache<K, V> getCache(String name) {
		return getCache(name, false);
	}

	@Override
	public PortalCache<K, V> getCache(String name, boolean blocking) {
		MemoryPortalCache<K, V> portalCache = _memoryPortalCaches.get(name);

		if (portalCache == null) {
			portalCache = new MemoryPortalCache<K, V>(
				name, _cacheInitialCapacity);

			_memoryPortalCaches.put(name, portalCache);
		}

		return portalCache;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
	}

	@Override
	public void removeCache(String name) {
		MemoryPortalCache<K, V> memoryPortalCache = _memoryPortalCaches.remove(
			name);

		memoryPortalCache.destroy();
	}

	public void setCacheInitialCapacity(int cacheInitialCapacity) {
		_cacheInitialCapacity = cacheInitialCapacity;
	}

	public void setCacheManagerInitialCapacity(
		int cacheManagerInitialCapacity) {

		_cacheManagerInitialCapacity = cacheManagerInitialCapacity;
	}

	private int _cacheInitialCapacity = 10000;
	private int _cacheManagerInitialCapacity = 10000;
	private Map<String, MemoryPortalCache<K, V>> _memoryPortalCaches;

}