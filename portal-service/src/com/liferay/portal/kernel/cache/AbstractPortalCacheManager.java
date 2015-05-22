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

import com.liferay.portal.kernel.cache.configuration.CallbackConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.cache.transactional.TransactionalPortalCache;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

		PortalCacheConfiguration portalCacheConfiguration =
			_portalCacheManagerConfiguration.getPortalCacheConfiguration(name);

		if (portalCacheConfiguration == null) {
			portalCacheConfiguration = createPortalCacheConfiguration(
				name, _defaultPortalCacheConfiguration);

			_portalCacheManagerConfiguration.putPortalCacheConfiguration(
				name, portalCacheConfiguration);
		}

		portalCache = createPortalCache(portalCacheConfiguration);

		_initPortalCacheListeners(portalCache, portalCacheConfiguration);

		if (isTransactionalCacheEnabled() && isTransactionalPortalCache(name)) {
			portalCache = new TransactionalPortalCache<>(portalCache);
		}

		if (isBlockingCacheAllowed() && blocking) {
			portalCache = new BlockingPortalCache<>(portalCache);
		}

		PortalCache<K, V> previousPortalCache = portalCaches.putIfAbsent(
			name, portalCache);

		if (previousPortalCache != null) {
			portalCache = previousPortalCache;
		}
		else if (isBootstrapCacheLoaderEnabled() &&
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

	public String[] getTransactionalCacheNames() {
		return _transactionalCacheNames;
	}

	public boolean isBlockingCacheAllowed() {
		return _blockingCacheAllowed;
	}

	public boolean isBootstrapCacheLoaderEnabled() {
		return _bootstrapCacheLoaderEnabled;
	}

	@Override
	public boolean isClusterAware() {
		return _clusterAware;
	}

	public boolean isTransactionalCacheEnabled() {
		return _transactionalCacheEnabled;
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

	public void setBlockingCacheAllowed(boolean blockingCacheAllowed) {
		_blockingCacheAllowed = blockingCacheAllowed;
	}

	public void setBootstrapCacheLoaderEnabled(
		boolean bootstrapCacheLoaderEnabled) {

		_bootstrapCacheLoaderEnabled = bootstrapCacheLoaderEnabled;
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

	public void setTransactionalCacheEnabled(
		boolean transactionalCacheEnabled) {

		_transactionalCacheEnabled = transactionalCacheEnabled;
	}

	public void setTransactionalCacheNames(String[] transactionalCacheNames) {
		_transactionalCacheNames = transactionalCacheNames;
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

	protected abstract PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration);

	protected abstract PortalCacheConfiguration
		createPortalCacheConfiguration(
			String name,
			PortalCacheConfiguration defaultPortalCacheConfiguration);

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

		_defaultPortalCacheConfiguration =
			_portalCacheManagerConfiguration.
				getDefaultPortalCacheConfiguration();

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
		for (String namePattern : getTransactionalCacheNames()) {
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

	private boolean _blockingCacheAllowed;
	private boolean _bootstrapCacheLoaderEnabled;
	private boolean _clusterAware;
	private PortalCacheConfiguration _defaultPortalCacheConfiguration;
	private boolean _mpiOnly;
	private String _name;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private boolean _transactionalCacheEnabled;
	private String[] _transactionalCacheNames = StringPool.EMPTY_ARRAY;

}