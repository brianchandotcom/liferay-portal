/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class TransactionalPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	public TransactionalPortalCache(PortalCache<K, V> portalCache) {
		_portalCache = portalCache;
	}

	public void destroy() {
	}

	public Collection<V> get(Collection<K> keys) {
		List<V> values = new ArrayList<V>(keys.size());

		for (K key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public V get(K key) {
		V result = null;

		if (TransactionalPortalCacheHelper.isEnabled()) {
			result = TransactionalPortalCacheHelper.get(_portalCache, key);

			if (result == _nullHolder) {
				return null;
			}
		}

		if (result == null) {
			result = _portalCache.get(key);
		}

		return result;
	}

	public String getName() {
		return _portalCache.getName();
	}

	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				TransactionalPortalCacheHelper.put(
					_portalCache, key, (V)_nullHolder);
			}
			else {
				TransactionalPortalCacheHelper.put(_portalCache, key, value);
			}
		}
		else {
			_portalCache.put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	public void put(K key, V value, int timeToLive) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				TransactionalPortalCacheHelper.put(
					_portalCache, key, (V)_nullHolder);
			}
			else {
				TransactionalPortalCacheHelper.put(_portalCache, key, value);
			}
		}
		else {
			_portalCache.put(key, value, timeToLive);
		}
	}

	public void registerCacheListener(CacheListener<K, V> cacheListener) {
		_portalCache.registerCacheListener(cacheListener);
	}

	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		_portalCache.registerCacheListener(cacheListener, cacheListenerScope);
	}

	public void remove(K key) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.remove(_portalCache, key);
		}

		_portalCache.remove(key);
	}

	public void removeAll() {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.removeAll(_portalCache);
		}

		_portalCache.removeAll();
	}

	public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
		_portalCache.unregisterCacheListener(cacheListener);
	}

	public void unregisterCacheListeners() {
		_portalCache.unregisterCacheListeners();
	}

	private static Serializable _nullHolder = StringPool.BLANK;

	private PortalCache<K, V> _portalCache;

}