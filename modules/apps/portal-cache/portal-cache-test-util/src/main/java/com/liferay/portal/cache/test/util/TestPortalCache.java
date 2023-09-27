/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		aggregatedPortalCacheListener.notifyEntryRemoved(
			this, key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	protected boolean doRemove(K key, V value) {
		boolean removed = _concurrentMap.remove(key, value);

		aggregatedPortalCacheListener.notifyEntryRemoved(
			this, key, value, DEFAULT_TIME_TO_LIVE);

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