/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

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
public class PooledMemcachePortalCache implements PortalCache {

	public PooledMemcachePortalCache(
		String name, MemcachedClientFactory memcachedClientFactory, int timeout,
		TimeUnit timeoutTimeUnit) {

		_name = name;
		_memcachedClientFactory = memcachedClientFactory;
		_timeout = timeout;
		_timeoutTimeUnit = timeoutTimeUnit;
	}

	public void destroy() {
		try {
			_memcachedClientFactory.close();
		}
		catch (Exception e) {
		}
	}

	public Collection<Object> get(Collection<Serializable> keys) {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return null;
		}

		List<String> processedKeys = new ArrayList<String>(keys.size());

		for (Serializable key : keys) {
			String processedKey = _name.concat(String.valueOf(key));

			processedKeys.add(processedKey);
		}

		Map<String, Object> values = null;

		try {
			Future<Map<String, Object>> future = null;

			try {
				future = memcachedClient.asyncGetBulk(processedKeys);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isWarnEnabled()) {
					_log.warn("Error retrieving with keys " + keys, iae);
				}

				future.cancel(true);
			}

			try {
				values = future.get(_timeout, _timeoutTimeUnit);
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn("Memcache operation error", t);
				}

				future.cancel(true);
			}
		}
		finally {
			cleanupClient(memcachedClient);
		}

		return values.values();
	}

	public Object get(Serializable key) {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return null;
		}

		String processedKey = _name.concat(String.valueOf(key));

		try {
			Future<Object> future = null;

			try {
				future = memcachedClient.asyncGet(processedKey);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isWarnEnabled()) {
					_log.warn("Error retrieving with key " + key, iae);
				}
			}

			Object value = null;

			try {
				value = future.get(_timeout, _timeoutTimeUnit);
			}
			catch (Exception e) {
				future.cancel(true);
			}

			return value;
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public String getName() {
		return _name;
	}

	public void put(Serializable key, Object value) {
		put(key, value, _timeToLive);
	}

	public void put(Serializable key, Object value, int timeToLive) {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		String processedKey = _name.concat(String.valueOf(key));

		try {
			memcachedClient.set(processedKey, timeToLive, value);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing value with key " + key, iae);
			}
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void put(Serializable key, Serializable value) {
		put(key, value, _timeToLive);
	}

	public void put(Serializable key, Serializable value, int timeToLive) {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		String processedKey = _name.concat(String.valueOf(key));

		try {
			memcachedClient.set(processedKey, timeToLive, value);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Error storing value with key " + key, iae);
			}
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void registerCacheListener(CacheListener cacheListener) {
		registerCacheListener(cacheListener, CacheListenerScope.ALL);
	}

	public void registerCacheListener(
		CacheListener cacheListener, CacheListenerScope cacheListenerScope) {

		throw new UnsupportedOperationException();
	}

	public void remove(Serializable key) {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		String processedKey = _name.concat(String.valueOf(key));

		try {
			memcachedClient.delete(processedKey);
		}
		catch (IllegalArgumentException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete value with key " + key, iae);
			}
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void removeAll() {
		MemcachedClientIF memcachedClient = null;

		try {
			memcachedClient = _memcachedClientFactory.getMemcachedClient();
		}
		catch (Exception e) {
			return;
		}

		try {
			memcachedClient.flush();
		}
		finally {
			cleanupClient(memcachedClient);
		}
	}

	public void setTimeToLive(int timeToLive) {
		_timeToLive = timeToLive;
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
	}

	public void unregisterCacheListeners() {
	}

	protected void cleanupClient(MemcachedClientIF memcachedClient) {
		try {
			_memcachedClientFactory.returnMemcachedObject(memcachedClient);
		}
		catch (Exception e) {
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MemcachePortalCache.class);

	private MemcachedClientFactory _memcachedClientFactory;
	private String _name;
	private int _timeout;
	private TimeUnit _timeoutTimeUnit;
	private int _timeToLive;

}