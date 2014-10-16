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

package com.liferay.portal.cache.memory;

import com.liferay.portal.kernel.cache.AbstractPortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class MemoryPortalCache<K extends Serializable, V>
	extends AbstractPortalCache<K, V> {

	public MemoryPortalCache(
		PortalCacheManager<K, V> portalCacheManager, String name,
		int initialCapacity) {

		super(portalCacheManager);

		_name = name;

		_concurrentMap = new ConcurrentHashMap<K, V>(initialCapacity);
	}

	public void destroy() {
		removeAll();

		_concurrentMap = null;
		_name = null;
	}

	@Override
	public List<K> getKeys() {
		List<K> keys = new ArrayList<K>();

		for (K key : _concurrentMap.keySet()) {
			keys.add(key);
		}

		return keys;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void removeAll() {
		_concurrentMap.clear();

		aggregatedCacheListener.notifyRemoveAll(this);
	}

	@Override
	protected V doGet(K key) {
		return _concurrentMap.get(key);
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.put(key, value);

		if (oldValue != null) {
			aggregatedCacheListener.notifyEntryUpdated(
				this, key, value, timeToLive);
		}
		else {
			aggregatedCacheListener.notifyEntryPut(
				this, key, value, timeToLive);
		}
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.putIfAbsent(key, value);

		if (oldValue == null) {
			aggregatedCacheListener.notifyEntryPut(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected void doRemove(K key) {
		V value = _concurrentMap.remove(key);

		if (value != null) {
			aggregatedCacheListener.notifyEntryRemoved(
				this, key, value, DEFAULT_TIME_TO_LIVE);
		}
	}

	@Override
	protected boolean doRemove(K key, V value) {
		boolean removed = _concurrentMap.remove(key, value);

		if (removed) {
			aggregatedCacheListener.notifyEntryRemoved(
				this, key, value, DEFAULT_TIME_TO_LIVE);
		}

		return removed;
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		V oldValue = _concurrentMap.replace(key, value);

		if (oldValue != null) {
			aggregatedCacheListener.notifyEntryUpdated(
				this, key, value, timeToLive);
		}

		return oldValue;
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		boolean replaced = _concurrentMap.replace(key, oldValue, newValue);

		if (replaced) {
			aggregatedCacheListener.notifyEntryUpdated(
				this, key, newValue, timeToLive);
		}

		return replaced;
	}

	private ConcurrentMap<K, V> _concurrentMap;
	private String _name;

}