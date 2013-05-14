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

package com.liferay.portal.cache.memcached;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClientIF;

/**
 * @author Michael C. Han
 */
public class MemcachePortalCache<V> implements PortalCache<String, V> {

	public MemcachePortalCache(
		String name, MemcachedClientIF memcachedClient, int timeout,
		TimeUnit timeoutTimeUnit) {

		_name = name;
		_memcachedClient = memcachedClient;
		_timeout = timeout;
		_timeoutTimeUnit = timeoutTimeUnit;
	}

	@Override
	public void destroy() {
		_memcachedClient.shutdown();
	}

	@Override
	public Collection<V> get(Collection<String> keys) {
		List<String> processedKeys = new ArrayList<String>(keys.size());

		for (String key : keys) {
			String processedKey = _name.concat(key);

			processedKeys.add(processedKey);
		}

		Future<Map<String, Object>> future = null;

		try {
			future = _memcachedClient.asyncGetBulk(processedKeys);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error retrieving with keys " + keys, iae);
			}

			return null;
		}

		Map<String, Object> values = null;

		try {
			values = future.get(_timeout, _timeoutTimeUnit);
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn("Memcache operation error", t);
			}

			future.cancel(true);
		}

		if (values != null) {
			return (Collection<V>)values.values();
		}

		return null;
	}

	public V get(String key) {
		String processedKey = _name.concat(key);

		Future<Object> future = null;

		try {
			future = _memcachedClient.asyncGet(processedKey);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error retrieving with key " + key, iae);
			}

			return null;
		}

		Object value = null;

		try {
			value = future.get(_timeout, _timeoutTimeUnit);
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn("Memcache operation error", t);
			}

			future.cancel(true);
		}

		return (V)value;
	}

	@Override
	public String getName() {
		return _name;
	}

	public void put(String key, V value) {
		put(key, value, _timeToLive);
	}

	public void put(String key, V value, int timeToLive) {
		String processedKey = _name.concat(key);

		try {
			_memcachedClient.set(processedKey, timeToLive, value);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing value with key " + key, iae);
			}
		}
	}

	@Override
	public void registerCacheListener(CacheListener<String, V> cacheListener) {
		registerCacheListener(cacheListener, CacheListenerScope.ALL);
	}

	@Override
	public void registerCacheListener(
		CacheListener<String, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		throw new UnsupportedOperationException();
	}

	public void remove(String key) {
		String processedKey = _name.concat(key);

		try {
			_memcachedClient.delete(processedKey);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error removing value with key " + key, iae);
			}
		}
	}

	@Override
	public void removeAll() {
		_memcachedClient.flush();
	}

	public void setTimeToLive(int timeToLive) {
		_timeToLive = timeToLive;
	}

	@Override
	public void unregisterCacheListener(
		CacheListener<String, V> cacheListener) {
	}

	@Override
	public void unregisterCacheListeners() {
	}

	private static Log _log = LogFactoryUtil.getLog(MemcachePortalCache.class);

	private MemcachedClientIF _memcachedClient;
	private String _name;
	private int _timeout;
	private TimeUnit _timeoutTimeUnit;
	private int _timeToLive;

}