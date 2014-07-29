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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheWrapper;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class TransactionalPortalCache<K extends Serializable, V>
	extends PortalCacheWrapper<K, V> {

	public TransactionalPortalCache(PortalCache<K, V> portalCache) {
		super(portalCache);
	}

	@Override
	public V get(K key) {
		V result = null;

		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			result = TransactionalPortalCacheHelper.get(portalCache, key);

			if (result == NULL_HOLDER) {
				return null;
			}
		}

		if (result == null) {
			result = portalCache.get(key);
		}

		return result;
	}

	@Override
	public void put(K key, V value) {
		doPut(key, value, DEFAULT_TIME_TO_LIVE, false);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		doPut(key, value, timeToLive, false);
	}

	@Override
	public void putQuiet(K key, V value) {
		doPut(key, value, DEFAULT_TIME_TO_LIVE, true);
	}

	@Override
	public void putQuiet(K key, V value, int timeToLive) {
		doPut(key, value, timeToLive, true);
	}

	@Override
	public void remove(K key) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			TransactionalPortalCacheHelper.put(
				portalCache, key, (V)NULL_HOLDER, false, DEFAULT_TIME_TO_LIVE);
		}
		else {
			portalCache.remove(key);
		}
	}

	@Override
	public void removeAll() {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.removeAll(portalCache);
		}
		else {
			portalCache.removeAll();
		}
	}

	protected void doPut(K key, V value, int timeToLive, boolean quiet) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (key == null) {
				throw new NullPointerException("Key is null");
			}

			if (value == null) {
				throw new NullPointerException("Value is null");
			}

			if ((timeToLive != DEFAULT_TIME_TO_LIVE) && (timeToLive < 0)) {
				throw new IllegalArgumentException("Time to live is negative");
			}

			TransactionalPortalCacheHelper.put(
				portalCache, key, value, quiet, timeToLive);
		}
		else {
			if (quiet) {
				portalCache.putQuiet(key, value, timeToLive);
			}
			else {
				portalCache.put(key, value, timeToLive);
			}
		}
	}

	protected static Serializable NULL_HOLDER = "NULL_HOLDER";

}