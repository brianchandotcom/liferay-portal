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

package com.liferay.portal.cache.test;

import com.liferay.portal.kernel.cache.AbstractPortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManagerTypes;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;

import java.io.Serializable;

import java.net.URL;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tina Tian
 */
public class TestPortalCacheManager<K extends Serializable, V>
	extends AbstractPortalCacheManager<K, V> {

	public static <K extends Serializable, V> TestPortalCacheManager<K, V>
		createTestPortalCacheManager(String portalCacheManagerName) {

		TestPortalCacheManager<K, V> testPortalCacheManager =
			new TestPortalCacheManager<>();

		testPortalCacheManager.setName(portalCacheManagerName);

		testPortalCacheManager.initialize();

		return testPortalCacheManager;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
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
			aggregatedCacheManagerListener.notifyCacheAdded(portalCacheName);
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

		aggregatedCacheManagerListener.dispose();
	}

	@Override
	protected void doRemoveCache(String portalCacheName) {
		TestPortalCache<K, V> testPortalCache = _testPortalCaches.remove(
			portalCacheName);

		testPortalCache.removeAll();

		aggregatedCacheManagerListener.notifyCacheRemoved(portalCacheName);
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		return new PortalCacheManagerConfiguration(
			null,
			new PortalCacheConfiguration(
				PortalCacheConfiguration.DEFAULT_PORTAL_CACHE_NAME, null, null),
			null);
	}

	@Override
	protected String getType() {
		return PortalCacheManagerTypes.TEST;
	}

	@Override
	protected void initPortalCacheManager() {
		_testPortalCaches = new ConcurrentHashMap<>();

		aggregatedCacheManagerListener.init();
	}

	private ConcurrentMap<String, TestPortalCache<K, V>> _testPortalCaches;

}