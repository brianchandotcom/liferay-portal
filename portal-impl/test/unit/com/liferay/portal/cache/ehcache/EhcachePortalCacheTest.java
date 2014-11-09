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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.MockPortalCacheManager;
import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.TestCacheReplicator;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@BeforeClass
	public static void setUpClass() {
		Configuration configuration = new Configuration();

		CacheConfiguration cacheConfiguration = new CacheConfiguration();

		cacheConfiguration.setMaxEntriesLocalHeap(100);

		configuration.addDefaultCache(cacheConfiguration);

		_cacheManager = CacheManager.newInstance(configuration);
	}

	@AfterClass
	public static void tearDownClass() {
		_cacheManager.shutdown();
	}

	@Before
	public void setUp() {
		_cacheManager.addCache(_PORTAL_CACHE_NAME);

		Cache cache = _cacheManager.getCache(_PORTAL_CACHE_NAME);

		_ehcachePortalCache = new EhcachePortalCache<String, String>(
			new MockPortalCacheManager<String, String>(
				_PORTAL_CACHE_MANAGER_NAME),
			cache);

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		_defaultCacheListener = new TestCacheListener<String, String>();

		_ehcachePortalCache.registerCacheListener(_defaultCacheListener);

		_defaultCacheReplicator = new TestCacheReplicator<String, String>();

		_ehcachePortalCache.registerCacheListener(_defaultCacheReplicator);
	}

	@After
	public void tearDown() {
		_cacheManager.removeAllCaches();
	}

	@Test
	public void testCacheListener() {

		// Register 1

		TestCacheListener<String, String> localCacheListener =
			new TestCacheListener<String, String>();

		_ehcachePortalCache.registerCacheListener(
			localCacheListener, CacheListenerScope.LOCAL);

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		localCacheListener.assertActionsCount(1);
		localCacheListener.assertPut(_KEY_2, _VALUE_2);

		localCacheListener.reset();

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Register 2

		TestCacheListener<String, String> remoteCacheListener =
			new TestCacheListener<String, String>();

		_ehcachePortalCache.registerCacheListener(
			remoteCacheListener, CacheListenerScope.REMOTE);

		_ehcachePortalCache.put(_KEY_2, _VALUE_1);

		localCacheListener.assertActionsCount(1);
		localCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		localCacheListener.reset();

		remoteCacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_2, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Register 3

		_ehcachePortalCache.registerCacheListener(
			remoteCacheListener, CacheListenerScope.ALL);

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		localCacheListener.assertActionsCount(1);
		localCacheListener.assertUpdated(_KEY_2, _VALUE_2);

		localCacheListener.reset();

		remoteCacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Unregister 1

		_ehcachePortalCache.unregisterCacheListener(localCacheListener);

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		localCacheListener.assertActionsCount(0);

		remoteCacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Unregister 2

		_ehcachePortalCache.unregisterCacheListener(
			new TestCacheListener<String, String>());

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		localCacheListener.assertActionsCount(0);

		remoteCacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Unregister 3

		_ehcachePortalCache.unregisterCacheListeners();

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		localCacheListener.assertActionsCount(0);
		remoteCacheListener.assertActionsCount(0);
		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testGetKeys() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		List<String> keys = _ehcachePortalCache.getKeys();

		Assert.assertEquals(1, keys.size());
		Assert.assertTrue(keys.contains(_KEY_1));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(_PORTAL_CACHE_NAME, _ehcachePortalCache.getName());
	}

	@Test
	public void testPut() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		// Put 1

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Put 2

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Put 3

		PortalCacheHelperUtil.putWithoutReplicator(
			_ehcachePortalCache, _KEY_2, _VALUE_1);

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(0);

		// Put 4

		Assert.assertEquals(
			_VALUE_1, _ehcachePortalCache.putIfAbsent(_KEY_2, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put 5

		_ehcachePortalCache.remove(_KEY_1);

		Assert.assertNull(_ehcachePortalCache.putIfAbsent(_KEY_1, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(2);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(2);
		_defaultCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		// Remove 1

		Assert.assertTrue(_ehcachePortalCache.remove(_KEY_1, _VALUE_1));

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Remove 2

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		_ehcachePortalCache.remove(_KEY_2);

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(3);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultCacheListener.assertRemoved(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(3);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_defaultCacheReplicator.assertRemoved(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Remove 3

		PortalCacheHelperUtil.removeWithoutReplicator(
			_ehcachePortalCache, _KEY_1);

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(0);

		// Remove 4

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		_ehcachePortalCache.removeAll();

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(3);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultCacheListener.assertRemoveAll();

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(3);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_defaultCacheReplicator.assertRemoveAll();

		_defaultCacheReplicator.reset();

		// Remove 5

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		PortalCacheHelperUtil.removeAllWithoutReplicator(_ehcachePortalCache);

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(3);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultCacheListener.assertRemoveAll();

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(2);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();
	}

	@Test
	public void testReplace() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		// Replace 1

		Assert.assertEquals(
			_VALUE_1, _ehcachePortalCache.replace(_KEY_1, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Replace 2

		Assert.assertNull(_ehcachePortalCache.replace(_KEY_2, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 3

		Assert.assertTrue(
			_ehcachePortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Replace 4

		Assert.assertFalse(
			_ehcachePortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testTimeToLive() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		int timeToLive = 600;

		Ehcache ehcache = _ehcachePortalCache.ehcache;

		// Put

		_ehcachePortalCache.put(_KEY_2, _VALUE_2, timeToLive);

		Element element = ehcache.get(_KEY_2);

		Assert.assertEquals(_KEY_2, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultCacheReplicator.reset();

		// Put if absent

		ehcache.removeElement(element);

		_ehcachePortalCache.putIfAbsent(_KEY_2, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_2);

		Assert.assertEquals(_KEY_2, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultCacheReplicator.reset();

		// Replace 1

		ehcache.removeElement(element);

		_ehcachePortalCache.replace(_KEY_1, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_1);

		Assert.assertEquals(_KEY_1, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultCacheReplicator.reset();

		// Replace 2

		ehcache.removeElement(element);

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		_ehcachePortalCache.replace(_KEY_1, _VALUE_1, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_1);

		Assert.assertEquals(_KEY_1, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultCacheReplicator.reset();
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _PORTAL_CACHE_MANAGER_NAME =
		"PORTAL_CACHE_MANAGER_NAME";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private static CacheManager _cacheManager;

	private TestCacheListener<String, String> _defaultCacheListener;
	private TestCacheReplicator<String, String> _defaultCacheReplicator;
	private EhcachePortalCache<String, String> _ehcachePortalCache;

}