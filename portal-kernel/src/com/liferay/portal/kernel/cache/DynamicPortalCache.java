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

import com.liferay.portal.kernel.util.ProxyFactory;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class DynamicPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	public DynamicPortalCache(
		PortalCacheManager<K, V> portalCacheManager,
		PortalCache<K, V> portalCache, String portalCacheName, boolean blocking,
		boolean mvcc) {

		_portalCacheManager = portalCacheManager;
		_portalCacheName = portalCacheName;
		_blocking = blocking;
		_mvcc = mvcc;

		if (portalCache == null) {
			portalCache = (PortalCache<K, V>)_DUMMY_PORTAL_CACHE;
		}

		_portalCache = portalCache;
	}

	@Override
	public V get(K key) {
		return _portalCache.get(key);
	}

	@Override
	public List<K> getKeys() {
		List<K> keys = _portalCache.getKeys();

		if (keys == null) {
			return Collections.emptyList();
		}

		return keys;
	}

	@Override
	public PortalCacheManager<K, V> getPortalCacheManager() {
		return _portalCacheManager;
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	@Override
	public boolean isBlocking() {
		return _blocking;
	}

	@Override
	public boolean isMVCC() {
		return _mvcc;
	}

	@Override
	public void put(K key, V value) {
		_portalCache.put(key, value);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		_portalCache.put(key, value, timeToLive);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		_portalCache.registerPortalCacheListener(portalCacheListener);

		_portalCacheListeners.put(
			portalCacheListener, PortalCacheListenerScope.ALL);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		_portalCache.registerPortalCacheListener(
			portalCacheListener, portalCacheListenerScope);

		_portalCacheListeners.put(
			portalCacheListener, portalCacheListenerScope);
	}

	@Override
	public void remove(K key) {
		_portalCache.remove(key);
	}

	@Override
	public void removeAll() {
		_portalCache.removeAll();
	}

	@Override
	public void unregisterPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		_portalCache.unregisterPortalCacheListener(portalCacheListener);

		_portalCacheListeners.remove(portalCacheListener);
	}

	@Override
	public void unregisterPortalCacheListeners() {
		_portalCache.unregisterPortalCacheListeners();

		_portalCacheListeners.clear();
	}

	protected void setPortalCache(PortalCache<K, V> portalCache) {
		if (_portalCache == portalCache) {
			return;
		}

		if (portalCache == null) {
			portalCache = (PortalCache<K, V>)_DUMMY_PORTAL_CACHE;
		}

		_portalCache = portalCache;

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			portalCache.registerPortalCacheListener(
				entry.getKey(), entry.getValue());
		}
	}

	private static final PortalCache<? extends Serializable, ?>
		_DUMMY_PORTAL_CACHE = ProxyFactory.newDummyInstance(PortalCache.class);

	private final boolean _blocking;
	private final boolean _mvcc;
	private volatile PortalCache<K, V> _portalCache;
	private final Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
		_portalCacheListeners = new ConcurrentHashMap<>();
	private final PortalCacheManager<K, V> _portalCacheManager;
	private final String _portalCacheName;

}