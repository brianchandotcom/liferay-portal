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
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.SkipReplicationThreadLocal;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public class AggregatedPortalCacheListener<K extends Serializable, V>
	implements PortalCacheListener<K, V> {

	public void addPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		addPortalCacheListener(
			portalCacheListener, PortalCacheListenerScope.ALL);
	}

	public void addPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		_portalCacheListeners.putIfAbsent(
			portalCacheListener, portalCacheListenerScope);
	}

	public void clearAll() {
		dispose();

		_portalCacheListeners.clear();
	}

	@Override
	public void dispose() {
		for (PortalCacheListener<K, V> portalCacheListener :
				_portalCacheListeners.keySet()) {

			portalCacheListener.dispose();
		}
	}

	public Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
		getPortalCacheListeners() {

		return Collections.unmodifiableMap(_portalCacheListeners);
	}

	public boolean isEmpty() {
		return _portalCacheListeners.isEmpty();
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyEntryEvicted(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyEntryExpired(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyEntryPut(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyEntryRemoved(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyEntryUpdated(
					portalCache, key, value, timeToLive);
			}
		}
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		for (Map.Entry<PortalCacheListener<K, V>, PortalCacheListenerScope>
				entry : _portalCacheListeners.entrySet()) {

			PortalCacheListener<K, V> portalCacheListener = entry.getKey();

			if (_shouldDeliver(portalCacheListener, entry.getValue())) {
				portalCacheListener.notifyRemoveAll(portalCache);
			}
		}
	}

	public void removePortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		portalCacheListener.dispose();

		_portalCacheListeners.remove(portalCacheListener);
	}

	private boolean _shouldDeliver(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		if (SkipReplicationThreadLocal.isEnabled()) {
			if (portalCacheListener instanceof PortalCacheReplicator) {
				return false;
			}

			if (portalCacheListenerScope.equals(PortalCacheListenerScope.ALL) ||
				portalCacheListenerScope.equals(
					PortalCacheListenerScope.REMOTE)) {

				return true;
			}

			return false;
		}

		if (portalCacheListenerScope.equals(PortalCacheListenerScope.ALL) ||
			portalCacheListenerScope.equals(PortalCacheListenerScope.LOCAL)) {

			return true;
		}

		return false;
	}

	private final ConcurrentMap
		<PortalCacheListener<K, V>, PortalCacheListenerScope>
			_portalCacheListeners = new ConcurrentHashMap<>();

}