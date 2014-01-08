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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class MemoryPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	public MemoryPortalCache(String name, int initialCapacity) {
		_name = name;
		_map = new ConcurrentHashMap<K, V>(initialCapacity);
	}

	@Override
	public void destroy() {
		removeAll();

		_cacheListeners = null;
		_map = null;
		_name = null;
	}

	@Override
	public V get(K key) {
		return _map.get(key);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void put(K key, V value) {
		V oldValue = _map.put(key, value);

		notifyPutEvents(key, value, oldValue != null);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		V oldValue = _map.put(key, value);

		notifyPutEvents(key, value, oldValue != null);
	}

	@Override
	public void registerCacheListener(CacheListener<K, V> cacheListener) {
		_cacheListeners.add(cacheListener);
	}

	@Override
	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		registerCacheListener(cacheListener);
	}

	@Override
	public void remove(K key) {
		V value = _map.remove(key);

		for (CacheListener<K, V> cacheListener : _cacheListeners) {
			cacheListener.notifyEntryRemoved(this, key, value);
		}
	}

	@Override
	public void removeAll() {
		_map.clear();

		for (CacheListener<K, V> cacheListener : _cacheListeners) {
			cacheListener.notifyRemoveAll(this);
		}
	}

	@Override
	public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
		_cacheListeners.remove(cacheListener);
	}

	@Override
	public void unregisterCacheListeners() {
		_cacheListeners.clear();
	}

	protected void notifyPutEvents(K key, V value, boolean updated) {
		if (updated) {
			for (CacheListener<K, V> cacheListener : _cacheListeners) {
				cacheListener.notifyEntryUpdated(this, key, value);
			}
		}
		else {
			for (CacheListener<K, V> cacheListener : _cacheListeners) {
				cacheListener.notifyEntryPut(this, key, value);
			}
		}
	}

	private Set<CacheListener<K, V>> _cacheListeners =
		new ConcurrentHashSet<CacheListener<K, V>>();
	private Map<K, V> _map;
	private String _name;

}