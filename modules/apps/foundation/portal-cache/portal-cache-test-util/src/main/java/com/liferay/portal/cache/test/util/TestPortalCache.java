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

package com.liferay.portal.cache.test.util;

import com.liferay.portal.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public class TestPortalCache<K extends Serializable, V>
	extends BasePortalCache<K, V> {

	public TestPortalCache(
		PortalCacheManager<K, V> portalCacheManager, String portalCacheName) {

		super(portalCacheManager);

		_portalCacheName = portalCacheName;

		_concurrentMap = new ConcurrentHashMap<>();
	}

	public TestPortalCache(String portalCacheName) {
		super(null);

		_portalCacheName = portalCacheName;

		_concurrentMap = new ConcurrentHashMap<>();
	}

	@Override
	public List<K> getKeys() {
		List<K> keys = new ArrayList<>();

		for (K key : _concurrentMap.keySet()) {
			keys.add(key);
		}

		return keys;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getPortalCacheName()}
	 */
	@Deprecated
	@Override
	public String getName() {
		return getPortalCacheName();
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	@Override
	public void removeAll() {
		_concurrentMap.clear();

		aggregatedPortalCacheListener.notifyRemoveAll(this);
	}

	@Override
	protected V doGet(K key) {
		return _concurrentMap.get(key);
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.put(key, value);

		if (oldValue != null) {
			aggregatedPortalCacheListener.notifyEntryUpdated(
				this, key, value, timeToLive);
		}
		else {
			aggregatedPortalCacheListener.notifyEntryPut(
				this, key, value, timeToLive);
		}
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.putIfAbsent(key, value);

		if (oldValue == null) {
			aggregatedPortalCacheListener.notifyEntryPut(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected void doRemove(K key) {
		V value = _concurrentMap.remove(key);

		if (value != null) {
			aggregatedPortalCacheListener.notifyEntryRemoved(
				this, key, value, DEFAULT_TIME_TO_LIVE);
		}
	}

	@Override
	protected boolean doRemove(K key, V value) {
		boolean removed = _concurrentMap.remove(key, value);

		if (removed) {
			aggregatedPortalCacheListener.notifyEntryRemoved(
				this, key, value, DEFAULT_TIME_TO_LIVE);
		}

		return removed;
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.replace(key, value);

		if (oldValue != null) {
			aggregatedPortalCacheListener.notifyEntryUpdated(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		boolean replaced = _concurrentMap.replace(key, oldValue, newValue);

		if (replaced) {
			aggregatedPortalCacheListener.notifyEntryUpdated(
				this, key, newValue, timeToLive);
		}

		return replaced;
	}

	private final ConcurrentMap<K, V> _concurrentMap;
	private final String _portalCacheName;

}