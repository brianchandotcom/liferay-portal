/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.keypool;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class MultiVMKeyPoolPortalCache extends BasePortalCache {

	public MultiVMKeyPoolPortalCache(
		PortalCache clusterPortalCache, PortalCache localPortalCache) {

		_clusterPortalCache = clusterPortalCache;
		_localPortalCache = localPortalCache;
	}

	public Collection<Object> get(Collection<Serializable> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (Serializable key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public Object get(Serializable key) {
		if (key == null) {
			return null;
		}

		return _localPortalCache.get(key);
	}

	public String getName() {
		return _clusterPortalCache.getName();
	}

	public void put(Serializable key, Object obj) {
		_clusterPortalCache.put(key, key);

		_localPortalCache.put(key, obj);
	}

	public void put(Serializable key, Object obj, int timeToLive) {
		_clusterPortalCache.put(key, key, timeToLive);

		_localPortalCache.put(key, obj, timeToLive);
	}

	public void put(Serializable key, Serializable obj) {
		_clusterPortalCache.put(key, key);

		_localPortalCache.put(key, obj);
	}

	public void put(Serializable key, Serializable obj, int timeToLive) {
		_clusterPortalCache.put(key, key, timeToLive);

		_localPortalCache.put(key, obj, timeToLive);
	}

	public void registerCacheListener(CacheListener cacheListener) {
		_clusterPortalCache.registerCacheListener(cacheListener);
	}

	public void registerCacheListener(
		CacheListener cacheListener, CacheListenerScope cacheListenerScope) {

		_clusterPortalCache.registerCacheListener(
			cacheListener, cacheListenerScope);
	}

	public void remove(Serializable key) {
		_clusterPortalCache.remove(key);
		_localPortalCache.remove(key);
	}

	public void removeAll() {
		_clusterPortalCache.removeAll();
		_localPortalCache.removeAll();
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		_clusterPortalCache.unregisterCacheListener(cacheListener);
	}

	public void unregisterCacheListeners() {
		_clusterPortalCache.unregisterCacheListeners();
	}

	private PortalCache _clusterPortalCache;
	private PortalCache _localPortalCache;

}