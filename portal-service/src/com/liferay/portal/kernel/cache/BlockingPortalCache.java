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
		doPut(key, value, false, -1);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		doPut(key, value, false, timeToLive);
	}

	@Override
	public void putQuiet(K key, V value) {
		doPut(key, value, true, -1);
	}

	@Override
	public void putQuiet(K key, V value, int timeToLive) {
		if (timeToLive < 0) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		doPut(key, value, true, timeToLive);
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

	protected void doPut(K key, V value, boolean quiet, int timeToLive) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (value == null) {
			throw new IllegalArgumentException("Value is null");
		}

		if (quiet) {
			if (timeToLive >= 0) {
				portalCache.putQuiet(key, value, timeToLive);
			}
			else {
				portalCache.putQuiet(key, value);
			}
		}
		else {
			if (timeToLive >= 0) {
				portalCache.put(key, value, timeToLive);
			}
			else {
				portalCache.put(key, value);
			}
		}

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	private static ThreadLocal<CompeteLatch> _competeLatch =
		new ThreadLocal<CompeteLatch>();

	private final ConcurrentMap<K, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<K, CompeteLatch>();

}