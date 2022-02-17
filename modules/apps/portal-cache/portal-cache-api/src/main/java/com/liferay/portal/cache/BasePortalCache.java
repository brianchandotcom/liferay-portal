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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public abstract class BasePortalCache<K extends Serializable, V>
	implements LowLevelCache<K, V> {

	public BasePortalCache(PortalCacheManager<K, V> portalCacheManager) {
		_portalCacheManager = portalCacheManager;
	}

	@Override
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		return doGet(key);
	}

	@Override
	public PortalCacheManager<K, V> getPortalCacheManager() {
		return _portalCacheManager;
	}

	@Override
	public boolean isMVCC() {
		return false;
	}

	@Override
	public void put(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		doPut(key, value, timeToLive);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return putIfAbsent(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public V putIfAbsent(K key, V value, int timeToLive) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		return doPutIfAbsent(key, value, timeToLive);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		aggregatedPortalCacheListener.addPortalCacheListener(
			portalCacheListener);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		aggregatedPortalCacheListener.addPortalCacheListener(
			portalCacheListener, portalCacheListenerScope);
	}

	@Override
	public void remove(K key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		doRemove(key);
	}

	@Override
	public boolean remove(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		return doRemove(key, value);
	}

	@Override
	public V replace(K key, V value) {
		return replace(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public V replace(K key, V value, int timeToLive) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		return doReplace(key, value, timeToLive);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return replace(key, oldValue, newValue, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue, int timeToLive) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (oldValue == null) {
			throw new NullPointerException("Old value is null");
		}

		if (newValue == null) {
			throw new NullPointerException("New value is null");
		}

		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		return doReplace(key, oldValue, newValue, timeToLive);
	}

	@Override
	public void unregisterPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		aggregatedPortalCacheListener.removePortalCacheListener(
			portalCacheListener);
	}

	@Override
	public void unregisterPortalCacheListeners() {
		aggregatedPortalCacheListener.clearAll();
	}

	protected abstract V doGet(K key);

	protected abstract void doPut(K key, V value, int timeToLive);

	protected abstract V doPutIfAbsent(K key, V value, int timeToLive);

	protected abstract void doRemove(K key);

	protected abstract boolean doRemove(K key, V value);

	protected abstract V doReplace(K key, V value, int timeToLive);

	protected abstract boolean doReplace(
		K key, V oldValue, V newValue, int timeToLive);

	protected final AggregatedPortalCacheListener<K, V>
		aggregatedPortalCacheListener = new AggregatedPortalCacheListener<>();

	private final PortalCacheManager<K, V> _portalCacheManager;

}