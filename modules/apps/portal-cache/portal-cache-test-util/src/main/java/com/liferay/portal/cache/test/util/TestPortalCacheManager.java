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

package com.liferay.portal.cache.test.util;

import com.liferay.portal.cache.BasePortalCacheManager;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.net.URL;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public class TestPortalCacheManager<K extends Serializable, V>
	extends BasePortalCacheManager<K, V> {

	public static <K extends Serializable, V> TestPortalCacheManager<K, V>
		createTestPortalCacheManager(String portalCacheManagerName) {

		TestPortalCacheManager<K, V> testPortalCacheManager =
			new TestPortalCacheManager<>();

		testPortalCacheManager.setPortalCacheManagerName(
			portalCacheManagerName);

		testPortalCacheManager.initialize();

		return testPortalCacheManager;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #reconfigurePortalCaches(URL, ClassLoader)}
	 */
	@Deprecated
	@Override
	public void reconfigurePortalCaches(URL configurationURL) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader) {

		throw new UnsupportedOperationException();
	}

	@Override
	protected PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration) {

		String portalCacheName = portalCacheConfiguration.getPortalCacheName();

		TestPortalCache<K, V> portalCache = _testPortalCaches.get(
			portalCacheName);

		if (portalCache != null) {
			return portalCache;
		}

		portalCache = new TestPortalCache<>(this, portalCacheName);

		TestPortalCache<K, V> previousPortalCache =
			_testPortalCaches.putIfAbsent(portalCacheName, portalCache);

		if (previousPortalCache == null) {
			aggregatedPortalCacheManagerListener.notifyPortalCacheAdded(
				portalCacheName);
		}
		else {
			portalCache = previousPortalCache;
		}

		return portalCache;
	}

	@Override
	protected void doClearAll() {
		for (TestPortalCache<K, V> testPortalCache :
				_testPortalCaches.values()) {

			testPortalCache.removeAll();
		}
	}

	@Override
	protected void doDestroy() {
		for (TestPortalCache<K, V> testPortalCache :
				_testPortalCaches.values()) {

			testPortalCache.removeAll();
		}

		aggregatedPortalCacheManagerListener.dispose();
	}

	@Override
	protected void doRemovePortalCache(String portalCacheName) {
		TestPortalCache<K, V> testPortalCache = _testPortalCaches.remove(
			portalCacheName);

		testPortalCache.removeAll();

		aggregatedPortalCacheManagerListener.notifyPortalCacheRemoved(
			portalCacheName);
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		return new PortalCacheManagerConfiguration(
			null,
			new PortalCacheConfiguration(
				PortalCacheConfiguration.PORTAL_CACHE_NAME_DEFAULT, null, null),
			null);
	}

	@Override
	protected void initPortalCacheManager() {
		_testPortalCaches = new ConcurrentHashMap<>();

		aggregatedPortalCacheManagerListener.init();
	}

	@Override
	protected void removeConfigurableEhcachePortalCacheListeners(
		PortalCache<K, V> portalCache) {
	}

	private ConcurrentMap<String, TestPortalCache<K, V>> _testPortalCaches;

}