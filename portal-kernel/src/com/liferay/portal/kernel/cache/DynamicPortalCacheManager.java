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

import com.liferay.portal.kernel.util.ProxyFactory;

import java.io.Serializable;

import java.net.URL;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Tina Tian
 */
public class DynamicPortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	public DynamicPortalCacheManager(String portalCacheManagerName) {
		_portalCacheManagerName = portalCacheManagerName;

		_portalCacheManager =
			(PortalCacheManager<K, V>)_DUMMY_PORTAL_CACHE_MANAGER;
	}

	@Override
	public void clearAll() throws PortalCacheException {
		_portalCacheManager.clearAll();
	}

	@Override
	public void destroy() {
		_dynamicPortalCaches.clear();
	}

	@Override
	public PortalCache<K, V> fetchPortalCache(String portalCacheName) {
		return _dynamicPortalCaches.computeIfAbsent(
			portalCacheName,
			key -> {
				PortalCache<K, V> portalCache =
					_portalCacheManager.fetchPortalCache(portalCacheName);

				if (portalCache == null) {
					return null;
				}

				return new DynamicPortalCache<>(
					this, portalCache, key, portalCache.isMVCC());
			});
	}

	@Override
	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, false, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean blocking, boolean mvcc)
		throws PortalCacheException {

		return _dynamicPortalCaches.computeIfAbsent(
			portalCacheName,
			key -> new DynamicPortalCache<>(
				this, _portalCacheManager.getPortalCache(key, false, mvcc), key,
				mvcc));
	}

	@Override
	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners() {
		PortalCacheManager<K, V> portalCacheManager = _portalCacheManager;

		if (portalCacheManager == _DUMMY_PORTAL_CACHE_MANAGER) {
			return Collections.unmodifiableSet(_portalCacheManagerListeners);
		}

		return portalCacheManager.getPortalCacheManagerListeners();
	}

	@Override
	public String getPortalCacheManagerName() {
		return _portalCacheManagerName;
	}

	@Override
	public boolean isClusterAware() {
		return _portalCacheManager.isClusterAware();
	}

	@Override
	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader) {

		PortalCacheManager<K, V> portalCacheManager = _portalCacheManager;

		if (portalCacheManager == _DUMMY_PORTAL_CACHE_MANAGER) {
			throw new UnsupportedOperationException(
				"This method is not supported because real portal cache " +
					"manager is missing now, please retry later");
		}

		portalCacheManager.reconfigurePortalCaches(
			configurationURL, classLoader);
	}

	@Override
	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		PortalCacheManager<K, V> portalCacheManager = _portalCacheManager;

		if ((portalCacheManager == _DUMMY_PORTAL_CACHE_MANAGER) ||
			portalCacheManager.registerPortalCacheManagerListener(
				portalCacheManagerListener)) {

			_portalCacheManagerListeners.add(portalCacheManagerListener);

			return true;
		}

		return false;
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		_dynamicPortalCaches.remove(portalCacheName);

		_portalCacheManager.removePortalCache(portalCacheName);
	}

	@Override
	public boolean unregisterPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		PortalCacheManager<K, V> portalCacheManager = _portalCacheManager;

		if ((portalCacheManager == _DUMMY_PORTAL_CACHE_MANAGER) ||
			portalCacheManager.unregisterPortalCacheManagerListener(
				portalCacheManagerListener)) {

			_portalCacheManagerListeners.remove(portalCacheManagerListener);

			return true;
		}

		return false;
	}

	@Override
	public void unregisterPortalCacheManagerListeners() {
		_portalCacheManager.unregisterPortalCacheManagerListeners();

		_portalCacheManagerListeners.clear();
	}

	protected PortalCacheManager<K, V> getPortalCacheManager() {
		return _portalCacheManager;
	}

	protected void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ?> portalCacheManager) {

		if (_portalCacheManager == portalCacheManager) {
			return;
		}

		if (portalCacheManager == null) {
			portalCacheManager = _DUMMY_PORTAL_CACHE_MANAGER;
		}

		_portalCacheManager = (PortalCacheManager<K, V>)portalCacheManager;

		for (PortalCacheManagerListener portalCacheManagerListener :
				_portalCacheManagerListeners) {

			_portalCacheManager.registerPortalCacheManagerListener(
				portalCacheManagerListener);
		}

		for (DynamicPortalCache<K, V> dynamicPortalCache :
				_dynamicPortalCaches.values()) {

			dynamicPortalCache.setPortalCache(
				_portalCacheManager.getPortalCache(
					dynamicPortalCache.getPortalCacheName(), false,
					dynamicPortalCache.isMVCC()));
		}
	}

	private static final PortalCacheManager<? extends Serializable, ?>
		_DUMMY_PORTAL_CACHE_MANAGER = ProxyFactory.newDummyInstance(
			PortalCacheManager.class);

	private final Map<String, DynamicPortalCache<K, V>> _dynamicPortalCaches =
		new ConcurrentHashMap<>();
	private volatile PortalCacheManager<K, V> _portalCacheManager;
	private final Set<PortalCacheManagerListener> _portalCacheManagerListeners =
		new CopyOnWriteArraySet<>();
	private final String _portalCacheManagerName;

}