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
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.put(
				portalCache, key, (V)NULL_HOLDER, false, -1);
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

	protected void doPut(K key, V value, boolean quiet, int timeToLive) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				TransactionalPortalCacheHelper.put(
					portalCache, key, (V)NULL_HOLDER, quiet, timeToLive);
			}
			else {
				TransactionalPortalCacheHelper.put(
					portalCache, key, value, quiet, timeToLive);
			}
		}
		else {
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
		}
	}

	protected static Serializable NULL_HOLDER = "NULL_HOLDER";

}