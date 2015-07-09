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

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheWrapper<K extends Serializable, V>
	implements PortalCache<K, V> {

	public PortalCacheWrapper(PortalCache<K, V> portalCache) {
		this.portalCache = portalCache;
	}

	@Override
	public V get(K key) {
		return portalCache.get(key);
	}

	@Override
	public List<K> getKeys() {
		return portalCache.getKeys();
	}

	@Override
	public String getPortalCacheName() {
		return portalCache.getPortalCacheName();
	}

	@Override
	public PortalCacheManager<K, V> getPortalCacheManager() {
		return portalCache.getPortalCacheManager();
	}

	public PortalCache<K, V> getWrappedPortalCache() {
		return portalCache;
	}

	@Override
	public void put(K key, V value) {
		portalCache.put(key, value);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		portalCache.put(key, value, timeToLive);
	}

	@Override
	public void registerPortalCacheListener(PortalCacheListener<K, V> cacheListener) {
		portalCache.registerPortalCacheListener(cacheListener);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> cacheListener,
		PortalCacheListenerScope cacheListenerScope) {

		portalCache.registerPortalCacheListener(cacheListener, cacheListenerScope);
	}

	@Override
	public void remove(K key) {
		portalCache.remove(key);
	}

	@Override
	public void removeAll() {
		portalCache.removeAll();
	}

	public void setPortalCache(PortalCache<K, V> portalCache) {
		this.portalCache = portalCache;
	}

	@Override
	public void unregisterPortalCacheListener(PortalCacheListener<K, V> cacheListener) {
		portalCache.unregisterPortalCacheListener(cacheListener);
	}

	@Override
	public void unregisterPortalCacheListeners() {
		portalCache.unregisterPortalCacheListeners();
	}

	protected PortalCache<K, V> portalCache;

}