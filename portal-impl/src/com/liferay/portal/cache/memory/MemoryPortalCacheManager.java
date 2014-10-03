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

package com.liferay.portal.cache.memory;

import com.liferay.portal.cache.AbstractPortalCacheManager;
import com.liferay.portal.cache.cluster.ClusterLinkCallbackFactory;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Brian Wing Shun Chan
 * @author Tina Tian
 */
public class MemoryPortalCacheManager<K extends Serializable, V>
	extends AbstractPortalCacheManager<K, V> {

	@Override
	public Set<CacheManagerListener> getCacheManagerListeners() {
		return new HashSet<CacheManagerListener>(_cacheManagerListeners);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return _cacheManagerListeners.add(cacheManagerListener);
	}

	public void setCacheInitialCapacity(int cacheInitialCapacity) {
		_cacheInitialCapacity = cacheInitialCapacity;
	}

	public void setCacheManagerInitialCapacity(
		int cacheManagerInitialCapacity) {

		_cacheManagerInitialCapacity = cacheManagerInitialCapacity;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return _cacheManagerListeners.remove(cacheManagerListener);
	}

	@Override
	public void unregisterCacheManagerListeners() {
		_cacheManagerListeners.clear();
	}

	@Override
	protected PortalCache<K, V> createPortalCache(String cacheName) {
		MemoryPortalCache<K, V> portalCache = _memoryPortalCaches.get(
			cacheName);

		if (portalCache != null) {
			return portalCache;
		}

		portalCache = new MemoryPortalCache<K, V>(
			this, cacheName, _cacheInitialCapacity);

		if (_memoryPortalCaches.putIfAbsent(cacheName, portalCache) == null) {
			for (CacheManagerListener cacheManagerListener :
					_cacheManagerListeners) {

				cacheManagerListener.notifyCacheAdded(cacheName);
			}
		}

		return _memoryPortalCaches.get(cacheName);
	}

	@Override
	protected void doClearAll() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.removeAll();
		}
	}

	@Override
	protected void doDestroy() {
		for (MemoryPortalCache<K, V> memoryPortalCache :
				_memoryPortalCaches.values()) {

			memoryPortalCache.destroy();
		}

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.dispose();
		}
	}

	@Override
	protected void doRemoveCache(String cacheName) {
		MemoryPortalCache<K, V> memoryPortalCache = _memoryPortalCaches.remove(
			cacheName);

		memoryPortalCache.destroy();

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.notifyCacheRemoved(cacheName);
		}
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		PortalCacheConfiguration defaultPortalCacheConfiguration = null;

		if (clusterAware && PropsValues.CLUSTER_LINK_ENABLED) {
			CallbackConfiguration cacheListenerConfiguration =
				new CallbackConfiguration(
					ClusterLinkCallbackFactory.INSTANCE, new Properties());

			Map<CallbackConfiguration, CacheListenerScope>
				cacheListenerConfigurations =
					new HashMap<CallbackConfiguration, CacheListenerScope>();

			cacheListenerConfigurations.put(
				cacheListenerConfiguration, CacheListenerScope.ALL);

			CallbackConfiguration bootstrapLoaderConfiguration =
				new CallbackConfiguration(
					ClusterLinkCallbackFactory.INSTANCE, new Properties());

			defaultPortalCacheConfiguration =
				new PortalCacheConfiguration(
					PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME,
					cacheListenerConfigurations, bootstrapLoaderConfiguration);
		}

		return new PortalCacheManagerConfiguration(
			null, defaultPortalCacheConfiguration, null);
	}

	@Override
	protected void initPortalCacheManager() {
		if (_name == null) {
			throw new NullPointerException("Name is null");
		}

		_memoryPortalCaches =
			new ConcurrentHashMap<String, MemoryPortalCache<K, V>>(
				_cacheManagerInitialCapacity);

		for (CacheManagerListener cacheManagerListener :
				_cacheManagerListeners) {

			cacheManagerListener.init();
		}
	}

	private int _cacheInitialCapacity = 10000;
	private int _cacheManagerInitialCapacity = 10000;
	private Set<CacheManagerListener> _cacheManagerListeners =
		new CopyOnWriteArraySet<CacheManagerListener>();
	private ConcurrentMap<String, MemoryPortalCache<K, V>> _memoryPortalCaches;
	private String _name;

}