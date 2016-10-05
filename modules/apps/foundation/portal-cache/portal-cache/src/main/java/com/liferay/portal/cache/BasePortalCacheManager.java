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

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.internal.mvcc.MVCCPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public abstract class BasePortalCacheManager<K extends Serializable, V>
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
	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean blocking)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, blocking, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean blocking, boolean mvcc)
		throws PortalCacheException {

		PortalCache<K, V> portalCache = portalCaches.get(portalCacheName);

		if (portalCache != null) {
			return portalCache;
		}

		PortalCacheConfiguration portalCacheConfiguration =
			_portalCacheManagerConfiguration.getPortalCacheConfiguration(
				portalCacheName);

		if (portalCacheConfiguration == null) {
			portalCacheConfiguration =
				_defaultPortalCacheConfiguration.newPortalCacheConfiguration(
					portalCacheName);

			_portalCacheManagerConfiguration.putPortalCacheConfiguration(
				portalCacheName, portalCacheConfiguration);
		}

		portalCache = createPortalCache(portalCacheConfiguration);

		_initPortalCacheListeners(portalCache, portalCacheConfiguration);

		if (mvcc) {
			portalCache = (PortalCache<K, V>)new MVCCPortalCache<>(
				(LowLevelCache<K, MVCCModel>)portalCache);
		}

		if (isTransactionalPortalCacheEnabled() &&
			isTransactionalPortalCache(portalCacheName)) {

			portalCache = new TransactionalPortalCache<>(portalCache);
		}

		if (isBlockingPortalCacheAllowed() && blocking) {
			portalCache = new BlockingPortalCache<>(portalCache);
		}

		PortalCache<K, V> previousPortalCache = portalCaches.putIfAbsent(
			portalCacheName, portalCache);

		if (previousPortalCache != null) {
			portalCache = previousPortalCache;
		}
		else if (portalCacheConfiguration != null) {
			Properties portalCacheBootstrapLoaderProperties =
				portalCacheConfiguration.
					getPortalCacheBootstrapLoaderProperties();

			if (portalCacheBootstrapLoaderProperties != null) {
				PortalCacheBootstrapLoader portalCacheBootstrapLoader =
					portalCacheBootstrapLoaderFactory.create(
						portalCacheBootstrapLoaderProperties);

				if (portalCacheBootstrapLoader != null) {
					portalCacheBootstrapLoader.loadPortalCache(
						getPortalCacheManagerName(), portalCacheName);
				}
			}
		}

		return portalCache;
	}

	@Override
	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners() {
		return aggregatedPortalCacheManagerListener.
			getPortalCacheManagerListeners();
	}

	@Override
	public String getPortalCacheManagerName() {
		return _portalCacheManagerName;
	}

	public String[] getTransactionalPortalCacheNames() {
		return _transactionalPortalCacheNames;
	}

	public boolean isBlockingPortalCacheAllowed() {
		return _blockingPortalCacheAllowed;
	}

	@Override
	public boolean isClusterAware() {
		return _clusterAware;
	}

	public boolean isTransactionalPortalCacheEnabled() {
		return _transactionalPortalCacheEnabled;
	}

	@Override
	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		return aggregatedPortalCacheManagerListener.addPortalCacheListener(
			portalCacheManagerListener);
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		portalCaches.remove(portalCacheName);

		doRemovePortalCache(portalCacheName);
	}

	public void setBlockingPortalCacheAllowed(
		boolean blockingPortalCacheAllowed) {

		_blockingPortalCacheAllowed = blockingPortalCacheAllowed;
	}

	public void setClusterAware(boolean clusterAware) {
		this._clusterAware = clusterAware;
	}

	public void setMpiOnly(boolean mpiOnly) {
		_mpiOnly = mpiOnly;
	}

	public void setPortalCacheManagerName(String portalCacheManagerName) {
		_portalCacheManagerName = portalCacheManagerName;
	}

	public void setTransactionalPortalCacheEnabled(
		boolean transactionalPortalCacheEnabled) {

		_transactionalPortalCacheEnabled = transactionalPortalCacheEnabled;
	}

	public void setTransactionalPortalCacheNames(
		String[] transactionalPortalCacheNames) {

		_transactionalPortalCacheNames = transactionalPortalCacheNames;
	}

	@Override
	public boolean unregisterPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		return aggregatedPortalCacheManagerListener.removePortalCacheListener(
			portalCacheManagerListener);
	}

	@Override
	public void unregisterPortalCacheManagerListeners() {
		aggregatedPortalCacheManagerListener.clearAll();
	}

	protected abstract PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration);

	protected abstract void doClearAll();

	protected abstract void doDestroy();

	protected abstract void doRemovePortalCache(String portalCacheName);

	protected abstract PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration();

	protected void initialize() {
		if ((_portalCacheManagerConfiguration != null) ||
			(_mpiOnly && SPIUtil.isSPI())) {

			return;
		}

		if (Validator.isNull(_portalCacheManagerName)) {
			throw new IllegalArgumentException(
				"Portal cache manager name is not specified");
		}

		initPortalCacheManager();

		_portalCacheManagerConfiguration = getPortalCacheManagerConfiguration();

		_defaultPortalCacheConfiguration =
			_portalCacheManagerConfiguration.
				getDefaultPortalCacheConfiguration();

		for (Properties properties :
				_portalCacheManagerConfiguration.
					getPortalCacheManagerListenerPropertiesSet()) {

			PortalCacheManagerListener portalCacheManagerListener =
				portalCacheManagerListenerFactory.create(this, properties);

			if (portalCacheManagerListener != null) {
				registerPortalCacheManagerListener(portalCacheManagerListener);
			}
		}
	}

	protected abstract void initPortalCacheManager();

	protected boolean isTransactionalPortalCache(String portalCacheName) {
		for (String namePattern : getTransactionalPortalCacheNames()) {
			if (StringUtil.wildcardMatches(
					portalCacheName, namePattern, CharPool.QUESTION,
					CharPool.STAR, CharPool.PERCENT, true)) {

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

			removeConfigurableEhcachePortalCacheListeners(portalCache);

			_initPortalCacheListeners(portalCache, portalCacheConfiguration);
		}
	}

	protected abstract void removeConfigurableEhcachePortalCacheListeners(
		PortalCache<K, V> portalCache);

	protected final AggregatedPortalCacheManagerListener
		aggregatedPortalCacheManagerListener =
			new AggregatedPortalCacheManagerListener();
	protected PortalCacheBootstrapLoaderFactory
		portalCacheBootstrapLoaderFactory;
	protected PortalCacheListenerFactory portalCacheListenerFactory;
	protected PortalCacheManagerListenerFactory<PortalCacheManager<K, V>>
		portalCacheManagerListenerFactory;
	protected final ConcurrentMap<String, PortalCache<K, V>> portalCaches =
		new ConcurrentHashMap<>();

	private void _initPortalCacheListeners(
		PortalCache<K, V> portalCache,
		PortalCacheConfiguration portalCacheConfiguration) {

		if (portalCacheConfiguration == null) {
			return;
		}

		for (Properties properties :
				portalCacheConfiguration.
					getPortalCacheListenerPropertiesSet()) {

			PortalCacheListener<K, V> portalCacheListener =
				portalCacheListenerFactory.create(properties);

			if (portalCacheListener == null) {
				continue;
			}

			PortalCacheListenerScope portalCacheListenerScope =
				(PortalCacheListenerScope)properties.remove(
					PortalCacheConfiguration.PORTAL_CACHE_LISTENER_SCOPE);

			if (portalCacheListenerScope == null) {
				portalCacheListenerScope = PortalCacheListenerScope.ALL;
			}

			portalCache.registerPortalCacheListener(
				portalCacheListener, portalCacheListenerScope);
		}
	}

	private boolean _blockingPortalCacheAllowed;
	private boolean _clusterAware;
	private PortalCacheConfiguration _defaultPortalCacheConfiguration;
	private boolean _mpiOnly;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private String _portalCacheManagerName;
	private boolean _transactionalPortalCacheEnabled;
	private String[] _transactionalPortalCacheNames = StringPool.EMPTY_ARRAY;

}