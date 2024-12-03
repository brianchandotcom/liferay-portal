/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.test.util;

import com.liferay.portal.cache.BasePortalCache;

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

	public TestPortalCache(String portalCacheName) {
		super(null);

		_portalCacheName = portalCacheName;
	}

	@Override
	public List<K> getKeys() {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		return new ArrayList<>(concurrentMap.keySet());
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	@Override
	public void removeAll() {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		concurrentMap.clear();

		aggregatedPortalCacheListener.notifyRemoveAll(this);
	}

	@Override
	protected V doGet(K key) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		return concurrentMap.get(key);
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		V oldValue = concurrentMap.put(key, value);

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
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		V oldValue = concurrentMap.putIfAbsent(key, value);

		if (oldValue == null) {
			aggregatedPortalCacheListener.notifyEntryPut(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected void doRemove(K key) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		V value = concurrentMap.remove(key);

		aggregatedPortalCacheListener.notifyEntryRemoved(
			this, key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	protected boolean doRemove(K key, V value) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		boolean removed = concurrentMap.remove(key, value);

		aggregatedPortalCacheListener.notifyEntryRemoved(
			this, key, value, DEFAULT_TIME_TO_LIVE);

		return removed;
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		V oldValue = concurrentMap.replace(key, value);

		if (oldValue != null) {
			aggregatedPortalCacheListener.notifyEntryUpdated(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		ConcurrentMap<K, V> concurrentMap = getConcurrentMap();

		boolean replaced = concurrentMap.replace(key, oldValue, newValue);

		if (replaced) {
			aggregatedPortalCacheListener.notifyEntryUpdated(
				this, key, newValue, timeToLive);
		}

		return replaced;
	}

	protected ConcurrentMap<K, V> getConcurrentMap() {
		return _concurrentMap;
	}

	private final ConcurrentMap<K, V> _concurrentMap =
		new ConcurrentHashMap<>();
	private final String _portalCacheName;

}