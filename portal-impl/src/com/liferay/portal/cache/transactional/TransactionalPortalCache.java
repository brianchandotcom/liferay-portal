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
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				TransactionalPortalCacheHelper.put(
					portalCache, key, (V)NULL_HOLDER);
			}
			else {
				TransactionalPortalCacheHelper.put(portalCache, key, value);
			}
		}
		else {
			portalCache.put(key, value);
		}
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				TransactionalPortalCacheHelper.put(
					portalCache, key, (V)NULL_HOLDER, timeToLive);
			}
			else {
				TransactionalPortalCacheHelper.put(
					portalCache, key, value, timeToLive);
			}
		}
		else {
			portalCache.put(key, value, timeToLive);
		}
	}

	@Override
	public void remove(K key) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.put(
				portalCache, key, (V)NULL_HOLDER);
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

	protected static Serializable NULL_HOLDER = "NULL_HOLDER";

}