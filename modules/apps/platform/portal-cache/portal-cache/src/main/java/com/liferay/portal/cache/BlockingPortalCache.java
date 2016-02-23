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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.CompeteLatch;

import java.io.Serializable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class BlockingPortalCache<K extends Serializable, V>
	extends PortalCacheWrapper<K, V> {

	public BlockingPortalCache(PortalCache<K, V> portalCache) {
		super(portalCache);
	}

	@Override
	public V get(K key) {
		V value = portalCache.get(key);

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

			value = portalCache.get(key);
		}

		return value;
	}

	@Override
	public void put(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		portalCache.put(key, value, timeToLive);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	@Override
	public void remove(K key) {
		portalCache.remove(key);

		CompeteLatch competeLatch = _competeLatchMap.remove(key);

		if (competeLatch != null) {
			competeLatch.done();
		}
	}

	@Override
	public void removeAll() {
		portalCache.removeAll();
		_competeLatchMap.clear();
	}

	private static final ThreadLocal<CompeteLatch> _competeLatch =
		new ThreadLocal<>();

	private final ConcurrentMap<K, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<>();

}