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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.AggregatedPortalCacheManagerListener;
import com.liferay.portal.cache.PortalCacheListenerFactory;
import com.liferay.portal.cache.PortalCacheManagerListenerFactory;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
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
	public void destroy() {
		portalCaches.clear();

		doDestroy();
	}

	@Override
	public PortalCache<K, V> fetchPortalCache(String portalCacheName) {
		return portalCaches.get(portalCacheName);
	}

	@Override
	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, mvcc, false);
	}

	@Override
	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners() {
		return aggregatedPortalCacheManagerListener.
			getPortalCacheManagerListeners();
	}

	@Override
	public String getPortalCacheManagerName() {
		return portalCacheManagerName;
	}

	@Override
	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		return aggregatedPortalCacheManagerListener.addPortalCacheListener(
			portalCacheManagerListener);
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		doRemovePortalCache(portalCaches.remove(portalCacheName));
	}

	public void setPortalCacheManagerName(String portalCacheManagerName) {
		this.portalCacheManagerName = portalCacheManagerName;
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

	protected abstract void doDestroy();

	protected abstract void doRemovePortalCache(PortalCache<K, V> portalCache);

	protected abstract PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration();

	protected void initialize() {
		if (portalCacheManagerConfiguration != null) {
			return;
		}

		if (Validator.isNull(portalCacheManagerName)) {
			throw new IllegalArgumentException(
				"Portal cache manager name is not specified");
		}

		initPortalCacheManager();

		portalCacheManagerConfiguration = getPortalCacheManagerConfiguration();

		for (Properties properties :
				portalCacheManagerConfiguration.
					getPortalCacheManagerListenerPropertiesSet()) {

			PortalCacheManagerListener portalCacheManagerListener =
				portalCacheManagerListenerFactory.create(this, properties);

			if (portalCacheManagerListener != null) {
				registerPortalCacheManagerListener(portalCacheManagerListener);
			}
		}
	}

	protected void initPortalCacheListeners(
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
					PortalCacheConfiguration.
						PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE);

			if (portalCacheListenerScope == null) {
				portalCacheListenerScope = PortalCacheListenerScope.ALL;
			}

			portalCache.registerPortalCacheListener(
				portalCacheListener, portalCacheListenerScope);
		}
	}

	protected abstract void initPortalCacheManager();

	protected final AggregatedPortalCacheManagerListener
		aggregatedPortalCacheManagerListener =
			new AggregatedPortalCacheManagerListener();
	protected PortalCacheListenerFactory portalCacheListenerFactory;
	protected PortalCacheManagerConfiguration portalCacheManagerConfiguration;
	protected PortalCacheManagerListenerFactory<PortalCacheManager<K, V>>
		portalCacheManagerListenerFactory;
	protected String portalCacheManagerName;
	protected final ConcurrentMap<String, PortalCache<K, V>> portalCaches =
		new ConcurrentHashMap<>();

}