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

import com.liferay.portal.kernel.cache.AggregatedCacheManagerListener;
import com.liferay.portal.kernel.cache.BlockingPortalCache;
import com.liferay.portal.kernel.cache.BootstrapLoader;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.CallbackFactory;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.cache.transactional.TransactionalPortalCache;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public abstract class AbstractPortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	@Override
	public void clearAll() throws PortalCacheException {
		doClearAll();
	}

	@Override
	public void destroy() {
		portalCaches.clear();

		doDestroy();
	}

	@Override
	public PortalCache<K, V> getCache(String name) throws PortalCacheException {
		return getCache(name, false);
	}

	@Override
	public PortalCache<K, V> getCache(String name, boolean blocking)
		throws PortalCacheException {

		PortalCache<K, V> portalCache = portalCaches.get(name);

		if (portalCache != null) {
			return portalCache;
		}

		portalCache = createPortalCache(name);

		PortalCacheConfiguration portalCacheConfiguration =
			_portalCacheManagerConfiguration.getPortalCacheConfiguration(
				portalCache.getName());

		if (portalCacheConfiguration == null) {
			portalCacheConfiguration =
				_portalCacheManagerConfiguration.
					getDefaultPortalCacheConfiguration();
		}

		_initPortalCacheListeners(portalCache, portalCacheConfiguration);

		if (PropsValues.TRANSACTIONAL_CACHE_ENABLED &&
			isTransactionalPortalCache(name)) {

			portalCache = new TransactionalPortalCache<>(portalCache);
		}

		if (PropsValues.EHCACHE_BLOCKING_CACHE_ALLOWED && blocking) {
			portalCache = new BlockingPortalCache<>(portalCache);
		}

		PortalCache<K, V> previousPortalCache = portalCaches.putIfAbsent(
			name, portalCache);

		if (previousPortalCache != null) {
			portalCache = previousPortalCache;
		}
		else if (PropsValues.EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED &&
				 (portalCacheConfiguration != null)) {

			CallbackConfiguration bootstrapLoaderConfiguration =
				portalCacheConfiguration.getBootstrapLoaderConfiguration();

			if (bootstrapLoaderConfiguration != null) {
				CallbackFactory callbackFactory =
					bootstrapLoaderConfiguration.getCallbackFactory();

				BootstrapLoader bootstrapLoader =
					callbackFactory.createBootstrapLoader(
						bootstrapLoaderConfiguration.getProperties());

				if (bootstrapLoader != null) {
					bootstrapLoader.load(getName(), name);
				}
			}
		}

		return portalCache;
	}

	@Override
	public Set<CacheManagerListener> getCacheManagerListeners() {
		return aggregatedCacheManagerListener.getCacheManagerListeners();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isClusterAware() {
		return _clusterAware;
	}

	@Override
	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return aggregatedCacheManagerListener.addCacheListener(
			cacheManagerListener);
	}

	@Override
	public void removeCache(String name) {
		portalCaches.remove(name);

		doRemoveCache(name);
	}

	public void setClusterAware(boolean clusterAware) {
		this._clusterAware = clusterAware;
	}

	public void setMpiOnly(boolean mpiOnly) {
		_mpiOnly = mpiOnly;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		return aggregatedCacheManagerListener.removeCacheListener(
			cacheManagerListener);
	}

	@Override
	public void unregisterCacheManagerListeners() {
		aggregatedCacheManagerListener.clearAll();
	}

	protected abstract PortalCache<K, V> createPortalCache(String cacheName);

	protected abstract void doClearAll();

	protected abstract void doDestroy();

	protected abstract void doRemoveCache(String cacheName);

	protected abstract PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration();

	protected abstract String getType();

	protected void initialize() {
		if ((_portalCacheManagerConfiguration != null) ||
			(_mpiOnly && SPIUtil.isSPI())) {

			return;
		}

		if (Validator.isNull(_name)) {
			throw new IllegalArgumentException(
				"Portal cache manager name is not specified");
		}

		initPortalCacheManager();

		_portalCacheManagerConfiguration = getPortalCacheManagerConfiguration();

		for (CallbackConfiguration callbackConfiguration :
				_portalCacheManagerConfiguration.
					getCacheManagerListenerConfigurations()) {

			CallbackFactory callbackFactory =
				callbackConfiguration.getCallbackFactory();

			CacheManagerListener cacheManagerListener =
				callbackFactory.createCacheManagerListener(
					callbackConfiguration.getProperties());

			if (cacheManagerListener != null) {
				registerCacheManagerListener(cacheManagerListener);
			}
		}
	}

	protected abstract void initPortalCacheManager();

	protected boolean isTransactionalPortalCache(String name) {
		for (String namePattern : PropsValues.TRANSACTIONAL_CACHE_NAMES) {
			if (StringUtil.wildcardMatches(
					name, namePattern, CharPool.QUESTION, CharPool.STAR,
					CharPool.PERCENT, true)) {

				return true;
			}
		}

		return false;
	}

	protected void reconfigPortalCache(
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		for (String portalCacheName :
				portalCacheManagerConfiguration.getPortalCacheNames()) {

			PortalCacheConfiguration portalCacheConfiguration =
				portalCacheManagerConfiguration.getPortalCacheConfiguration(
					portalCacheName);

			_portalCacheManagerConfiguration.putPortalCacheConfiguration(
				portalCacheName, portalCacheConfiguration);

			PortalCache<K, V> portalCache = portalCaches.get(portalCacheName);

			if (portalCache == null) {
				continue;
			}

			portalCache.unregisterCacheListeners();

			_initPortalCacheListeners(portalCache, portalCacheConfiguration);
		}
	}

	protected final AggregatedCacheManagerListener
		aggregatedCacheManagerListener = new AggregatedCacheManagerListener();
	protected final ConcurrentMap<String, PortalCache<K, V>> portalCaches =
		new ConcurrentHashMap<>();

	private void _initPortalCacheListeners(
		PortalCache<K, V> portalCache,
		PortalCacheConfiguration portalCacheConfiguration) {

		if (portalCacheConfiguration == null) {
			return;
		}

		Map<CallbackConfiguration, CacheListenerScope>
			cacheListenerConfigurations =
				portalCacheConfiguration.getCacheListenerConfigurations();

		for (Map.Entry<CallbackConfiguration, CacheListenerScope> entry :
				cacheListenerConfigurations.entrySet()) {

			CallbackConfiguration callbackConfiguration = entry.getKey();

			CallbackFactory callbackFactory =
				callbackConfiguration.getCallbackFactory();

			CacheListener<K, V> cacheListener =
				callbackFactory.createCacheListener(
					callbackConfiguration.getProperties());

			portalCache.registerCacheListener(cacheListener, entry.getValue());
		}
	}

	private boolean _clusterAware;
	private boolean _mpiOnly;
	private String _name;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;

}