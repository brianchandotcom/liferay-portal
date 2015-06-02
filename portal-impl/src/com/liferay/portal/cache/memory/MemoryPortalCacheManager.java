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

import com.liferay.portal.kernel.cache.AbstractPortalCacheManager;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerTypes;
import com.liferay.portal.kernel.cache.cluster.ClusterLinkCallbackFactory;
import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistrar;

import java.io.Serializable;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Tina Tian
 */
public class MemoryPortalCacheManager<K extends Serializable, V>
	extends AbstractPortalCacheManager<K, V> {

	public static <K extends Serializable, V> MemoryPortalCacheManager<K, V>
		createMemoryPortalCacheManager(String name) {

		MemoryPortalCacheManager<K, V> memoryPortalCacheManager =
			new MemoryPortalCacheManager<>();

		memoryPortalCacheManager.setName(name);

		memoryPortalCacheManager.initialize();

		return memoryPortalCacheManager;
	}

	@Override
	public void destroy() {
		if (_serviceRegistrar != null) {
			_serviceRegistrar.destroy();
		}

		super.destroy();
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		throw new UnsupportedOperationException();
	}

	public void setCacheInitialCapacity(int cacheInitialCapacity) {
		_cacheInitialCapacity = cacheInitialCapacity;
	}

	public void setCacheManagerInitialCapacity(
		int cacheManagerInitialCapacity) {

		_cacheManagerInitialCapacity = cacheManagerInitialCapacity;
	}

	@Override
	protected PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration) {

		String portalCacheName = portalCacheConfiguration.getPortalCacheName();

		MemoryPortalCache<K, V> portalCache = _memoryPortalCaches.get(
			portalCacheName);

		if (portalCache != null) {
			return portalCache;
		}

		portalCache = new MemoryPortalCache<>(
			this, portalCacheName, _cacheInitialCapacity);

		MemoryPortalCache<K, V> previousPortalCache =
			_memoryPortalCaches.putIfAbsent(portalCacheName, portalCache);

		if (previousPortalCache == null) {
			aggregatedCacheManagerListener.notifyCacheAdded(portalCacheName);
		}
		else {
			portalCache = previousPortalCache;
		}

		return portalCache;
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

		aggregatedCacheManagerListener.dispose();
	}

	@Override
	protected void doRemoveCache(String portalCacheName) {
		MemoryPortalCache<K, V> memoryPortalCache = _memoryPortalCaches.remove(
			portalCacheName);

		memoryPortalCache.destroy();

		aggregatedCacheManagerListener.notifyCacheRemoved(portalCacheName);
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		Map<CallbackConfiguration, CacheListenerScope>
			cacheListenerConfigurations = null;
		CallbackConfiguration bootstrapLoaderConfiguration = null;

		if (isClusterAware() && PropsValues.CLUSTER_LINK_ENABLED) {
			CallbackConfiguration cacheListenerConfiguration =
				new CallbackConfiguration(
					ClusterLinkCallbackFactory.INSTANCE, new Properties());

			cacheListenerConfigurations = new HashMap<>();

			cacheListenerConfigurations.put(
				cacheListenerConfiguration, CacheListenerScope.ALL);

			bootstrapLoaderConfiguration = new CallbackConfiguration(
				ClusterLinkCallbackFactory.INSTANCE, new Properties());
		}

		return new PortalCacheManagerConfiguration(
			null,
			new PortalCacheConfiguration(
				PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME,
				cacheListenerConfigurations, bootstrapLoaderConfiguration),
			null);
	}

	@Override
	protected String getType() {
		return PortalCacheManagerTypes.MEMORY;
	}

	@Override
	protected void initialize() {
		super.initialize();

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistrar = registry.getServiceRegistrar(
			(Class<PortalCacheManager<K, V>>)(Class<?>)
				PortalCacheManager.class);

		Map<String, Object> properties = new HashMap<>();

		properties.put("portal.cache.manager.name", getName());
		properties.put("portal.cache.manager.type", getType());

		_serviceRegistrar.registerService(
			(Class<PortalCacheManager<K, V>>) (Class<?>)
				PortalCacheManager.class, this, properties);
	}

	@Override
	protected void initPortalCacheManager() {
		_memoryPortalCaches = new ConcurrentHashMap<>(
			_cacheManagerInitialCapacity);

		aggregatedCacheManagerListener.init();
	}

	private int _cacheInitialCapacity = 10000;
	private int _cacheManagerInitialCapacity = 10000;
	private ConcurrentMap<String, MemoryPortalCache<K, V>> _memoryPortalCaches;
	private ServiceRegistrar<PortalCacheManager<K, V>> _serviceRegistrar;

}