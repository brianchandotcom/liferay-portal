/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.RegisteredEventListeners;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheTest {

	@Test
	public void testCacheListenerCopying() {
		CacheManager cacheManager = new CacheManager();

		String cacheName1 = "testCache1";

		cacheManager.addCache(cacheName1);

		Cache cache1 = cacheManager.getCache(cacheName1);

		EhcachePortalCache<Serializable, Serializable> ehcachePortalCache =
			new EhcachePortalCache<Serializable, Serializable>(cache1);

		CacheListener<Serializable, Serializable> cacheListener =
			new DummyCacheListener();

		ehcachePortalCache.registerCacheListener(cacheListener);

		RegisteredEventListeners registeredEventListeners =
			cache1.getCacheEventNotificationService();

		Set<CacheEventListener> cacheEventListeners =
			registeredEventListeners.getCacheEventListeners();

		Assert.assertEquals(1, cacheEventListeners.size());

		Iterator<CacheEventListener> iterator = cacheEventListeners.iterator();

		CacheEventListener cacheEventListener = iterator.next();

		Assert.assertTrue(
			cacheEventListener instanceof PortalCacheCacheEventListener);

		PortalCacheCacheEventListener<Serializable, Serializable>
			portalCacheCacheEventListener =
				(PortalCacheCacheEventListener<Serializable, Serializable>)
					cacheEventListener;

		Assert.assertSame(
			cacheListener, portalCacheCacheEventListener.getCacheListener());
		Assert.assertSame(
			ehcachePortalCache, portalCacheCacheEventListener.getPortalCache());

		String cacheName2 = "testCache2";

		cacheManager.addCache(cacheName2);

		Cache cache2 = cacheManager.getCache(cacheName2);

		ehcachePortalCache.setEhcache(cache2);

		registeredEventListeners = cache2.getCacheEventNotificationService();

		Assert.assertEquals(1, cacheEventListeners.size());

		iterator = cacheEventListeners.iterator();

		cacheEventListener = iterator.next();

		Assert.assertTrue(
			cacheEventListener instanceof PortalCacheCacheEventListener);

		portalCacheCacheEventListener =
			(PortalCacheCacheEventListener<Serializable, Serializable>)
				cacheEventListener;

		Assert.assertSame(
			cacheListener, portalCacheCacheEventListener.getCacheListener());
		Assert.assertSame(
			ehcachePortalCache, portalCacheCacheEventListener.getPortalCache());
	}

	private static class DummyCacheListener
		implements CacheListener<Serializable, Serializable> {

		@Override
		public void notifyEntryEvicted(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value) {
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<Serializable, Serializable> portalCache,
			Serializable key, Serializable value) {
		}

		@Override
		public void notifyEntryPut(
			PortalCache<Serializable, Serializable> portalCache,
			Serializable key, Serializable value) {
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<Serializable, Serializable> portalCache,
			Serializable key, Serializable value) {
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<Serializable, Serializable> portalCache,
			Serializable key, Serializable value) {
		}

		@Override
		public void notifyRemoveAll(
			PortalCache<Serializable, Serializable> portalCache) {
		}

	}

}