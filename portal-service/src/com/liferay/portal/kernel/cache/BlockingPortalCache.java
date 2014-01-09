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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

import java.io.Serializable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class BlockingPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	public BlockingPortalCache(PortalCache<K, V> portalCache) {
		_portalCache = portalCache;
	}

	@Override
	public V get(K key) {
		V value = _portalCache.get(key);

		if (value != null) {
			return value;
		}

		CompeteLatch lastCompeteLatch = _competeLatch.get();

		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();

			_competeLatch.set(null);
		}

		CompeteLatch currentCompeteLatch = _competeLatchMap.get(key);

		if (currentCompeteLatch == null) {
			CompeteLatch newCompeteLatch = new CompeteLatch();

			currentCompeteLatch = _competeLatchMap.putIfAbsent(
				key, newCompeteLatch);

			if (currentCompeteLatch == null) {
				currentCompeteLatch = newCompeteLatch;
			}
		}

		_competeLatch.set(currentCompeteLatch);

		if (!currentCompeteLatch.compete()) {
			try {
				currentCompeteLatch.await();
			}
			catch (InterruptedException ie) {
			}

			_competeLatch.set(null);

			value = _portalCache.get(key);
		}

		return value;
	}

	@Override
	public String getName() {
		return _portalCache.getName();
	}

	@Override
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (value == null) {
			throw new IllegalArgumentException("Value is null");
		}

		_portalCache.put(key, value);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (value == null) {
			throw new IllegalArgumentException("Value is null");
		}

		_portalCache.put(key, value, timeToLive);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	@Override
	public void registerCacheListener(CacheListener<K, V> cacheListener) {
		_portalCache.registerCacheListener(cacheListener);
	}

	@Override
	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		_portalCache.registerCacheListener(cacheListener, cacheListenerScope);
	}

	@Override
	public void remove(K key) {
		_portalCache.remove(key);

		CompeteLatch competeLatch = _competeLatchMap.remove(key);

		if (competeLatch != null) {
			competeLatch.done();
		}
	}

	@Override
	public void removeAll() {
		_portalCache.removeAll();
		_competeLatchMap.clear();
	}

	@Override
	public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
		_portalCache.unregisterCacheListener(cacheListener);
	}

	@Override
	public void unregisterCacheListeners() {
		_portalCache.unregisterCacheListeners();
	}

	private static ThreadLocal<CompeteLatch> _competeLatch =
		new ThreadLocal<CompeteLatch>();

	private final ConcurrentMap<K, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<K, CompeteLatch>();
	private final PortalCache<K, V> _portalCache;

}