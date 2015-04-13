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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.key.CompositePortalCacheKey;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Preston Crary
 */
public class CompositePortalCacheKeyManager
	<K extends CompositePortalCacheKey, V>
	implements CacheListener<K, V> {

	public CompositePortalCacheKeyManager(PortalCache<K, V> portalCache) {
		_portalCacheKeys = new ConcurrentHashMap<>();

		_portalCache = portalCache;

		_portalCache.registerCacheListener(this);
	}

	@Override
	public void dispose() {
	}

	public Set<K> getBySimpleKey(String simpleKey) {
		Set<K> keys = _portalCacheKeys.get(simpleKey);

		if (keys == null) {
			return Collections.emptySet();
		}

		return keys;
	}

	public Set<String> getSimpleKeys() {
		return _portalCacheKeys.keySet();
	}

	@Override
	public void notifyEntryEvicted(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

		remove(key);
	}

	@Override
	public void notifyEntryExpired(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

		remove(key);
	}

	@Override
	public void notifyEntryPut(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

		add(key);
	}

	@Override
	public void notifyEntryRemoved(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

		remove(key);
	}

	@Override
	public void notifyEntryUpdated(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache) {
		_portalCacheKeys.clear();
	}

	public void removeBySimpleKey(String simpleKey) {
		Set<K> cacheKeys = _portalCacheKeys.remove(simpleKey);

		if (cacheKeys != null) {
			for (K cacheKey : cacheKeys) {
				_portalCache.remove(cacheKey);
			}
		}
	}

	protected void add(K key) {
		while (true) {
			Set<K> oldValues = _portalCacheKeys.get(key.getSimpleKey());

			if (oldValues == null) {
				Set<K> values = new HashSet<>();

				values.add(key);

				oldValues = _portalCacheKeys.putIfAbsent(
					key.getSimpleKey(), values);

				if (oldValues == null) {
					return;
				}
			}

			Set<K> values = new HashSet<>(oldValues);

			if (values.add(key)) {
				if (_portalCacheKeys.replace(
						key.getSimpleKey(), oldValues, values)) {

					return;
				}
			}
			else {
				return;
			}
		}
	}

	protected void remove(K key) {
		while (true) {
			Set<K> oldValues = _portalCacheKeys.get(key.getSimpleKey());

			if (oldValues == null) {
				return;
			}

			Set<K> values = new HashSet<>(oldValues);

			if (values.remove(key)) {
				if (_portalCacheKeys.replace(
						key.getSimpleKey(), oldValues, values)) {

					return;
				}
			}
			else {
				return;
			}
		}
	}

	private final PortalCache<K, V> _portalCache;
	private final ConcurrentMap<String, Set<K>> _portalCacheKeys;

}