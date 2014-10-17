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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public class AggregatedCacheListener<K extends Serializable, V>
	implements CacheListener<K, V> {

	public static boolean isRemoteInvoke() {
		return _remoteInvokeThreadLocal.get();
	}

	public static void setRemoteInvoke(boolean remoteInvoke) {
		_remoteInvokeThreadLocal.set(remoteInvoke);
	}

	public void addCacheListener(CacheListener<K, V> cacheListener) {
		addCacheListener(cacheListener, CacheListenerScope.ALL);
	}

	public void addCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		_cacheListeners.putIfAbsent(cacheListener, cacheListenerScope);
	}

	public void clearAll() {
		_cacheListeners.clear();
	}

	public Map<CacheListener<K, V>, CacheListenerScope> getCacheListeners() {
		return Collections.unmodifiableMap(_cacheListeners);
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyEntryEvicted(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyEntryExpired(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyEntryPut(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyEntryRemoved(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyEntryUpdated(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		for (Map.Entry<CacheListener<K, V>, CacheListenerScope> entry :
				_cacheListeners.entrySet()) {

			CacheListener<K, V> cacheListener = entry.getKey();

			if (_shouldDeliver(cacheListener, entry.getValue())) {
				cacheListener.notifyRemoveAll(portalCache);
			}
		}
	}

	public void removeCacheListener(CacheListener<K, V> cacheListener) {
		_cacheListeners.remove(cacheListener);
	}

	private boolean _shouldDeliver(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {

		if (_remoteInvokeThreadLocal.get()) {
			if (cacheListener instanceof CacheReplicator) {
				return false;
			}

			if (cacheListenerScope.equals(CacheListenerScope.ALL) ||
				cacheListenerScope.equals(CacheListenerScope.REMOTE)) {

				return true;
			}

			return false;
		}

		if (cacheListenerScope.equals(CacheListenerScope.ALL) ||
			cacheListenerScope.equals(CacheListenerScope.LOCAL)) {

			return true;
		}

		return false;
	}

	private static final ThreadLocal<Boolean> _remoteInvokeThreadLocal =
		new InitialThreadLocal<Boolean>(
			AggregatedCacheListener.class + "._remoteInvokeThreadLocal", false);

	private final ConcurrentMap<CacheListener<K, V>, CacheListenerScope>
		_cacheListeners =
			new ConcurrentHashMap<CacheListener<K, V>, CacheListenerScope>();

}