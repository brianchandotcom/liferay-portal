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

import com.liferay.portal.cache.test.util.TestPortalCacheListener;
import com.liferay.portal.cache.test.util.TestPortalCacheManager;
import com.liferay.portal.cache.test.util.TestPortalCacheReplicator;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.RegisteredEventListeners;

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
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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

		_ehcache = _cacheManager.getCache(_PORTAL_CACHE_NAME);

		PortalCacheManager<String, String> portalCacheManager =
			TestPortalCacheManager.createTestPortalCacheManager(
				_PORTAL_CACHE_NAME);

		_ehcachePortalCache = new EhcachePortalCache<>(
			portalCacheManager, _ehcache);

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener = new TestPortalCacheListener<>();

		_ehcachePortalCache.registerPortalCacheListener(
			_defaultPortalCacheListener);

		_defaultPortalCacheReplicator = new TestPortalCacheReplicator<>();

		_ehcachePortalCache.registerPortalCacheListener(
			_defaultPortalCacheReplicator);
	}

	@After
	public void tearDown() {
		_cacheManager.removeAllCaches();
	}

	@Test
	public void testCacheListener() {

		// Register 1

		TestPortalCacheListener<String, String> localPortalCacheListener =
			new TestPortalCacheListener<>();

		_ehcachePortalCache.registerPortalCacheListener(
			localPortalCacheListener, PortalCacheListenerScope.LOCAL);

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		localPortalCacheListener.assertActionsCount(1);
		localPortalCacheListener.assertPut(_KEY_2, _VALUE_2);

		localPortalCacheListener.reset();

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Register 2

		TestPortalCacheListener<String, String> remotePortalCacheListener =
			new TestPortalCacheListener<>();

		_ehcachePortalCache.registerPortalCacheListener(
			remotePortalCacheListener, PortalCacheListenerScope.REMOTE);

		_ehcachePortalCache.put(_KEY_2, _VALUE_1);

		localPortalCacheListener.assertActionsCount(1);
		localPortalCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		localPortalCacheListener.reset();

		remotePortalCacheListener.assertActionsCount(0);

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_2, _VALUE_1);

		_defaultPortalCacheReplicator.reset();

		// Register 3

		_ehcachePortalCache.registerPortalCacheListener(
			remotePortalCacheListener, PortalCacheListenerScope.ALL);

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		localPortalCacheListener.assertActionsCount(1);
		localPortalCacheListener.assertUpdated(_KEY_2, _VALUE_2);

		localPortalCacheListener.reset();

		remotePortalCacheListener.assertActionsCount(0);

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_2, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_2, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Unregister 1

		_ehcachePortalCache.unregisterPortalCacheListener(
			localPortalCacheListener);

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		localPortalCacheListener.assertActionsCount(0);

		remotePortalCacheListener.assertActionsCount(0);

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Unregister 2

		_ehcachePortalCache.unregisterPortalCacheListener(
			new TestPortalCacheListener<String, String>());

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		localPortalCacheListener.assertActionsCount(0);

		remotePortalCacheListener.assertActionsCount(0);

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultPortalCacheReplicator.reset();

		// Unregister 3

		_ehcachePortalCache.unregisterPortalCacheListeners();

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		localPortalCacheListener.assertActionsCount(0);
		remotePortalCacheListener.assertActionsCount(0);
		_defaultPortalCacheListener.assertActionsCount(0);
		_defaultPortalCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testGetEhcache() {
		Assert.assertSame(_ehcache, _ehcachePortalCache.getEhcache());
	}

	@Test
	public void testGetKeys() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		List<String> keys = _ehcachePortalCache.getKeys();

		Assert.assertEquals(keys.toString(), 1, keys.size());
		Assert.assertTrue(keys.contains(_KEY_1));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(
			_PORTAL_CACHE_NAME, _ehcachePortalCache.getPortalCacheName());
	}

	@Test
	public void testPut() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		// Put 1

		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Put 2

		_ehcachePortalCache.put(_KEY_1, _VALUE_2);

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Put 3

		PortalCacheHelperUtil.putWithoutReplicator(
			_ehcachePortalCache, _KEY_2, _VALUE_1);

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_2, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(0);

		// Put 4

		Assert.assertEquals(
			_VALUE_1, _ehcachePortalCache.putIfAbsent(_KEY_2, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(0);
		_defaultPortalCacheReplicator.assertActionsCount(0);

		// Put 5

		_ehcachePortalCache.remove(_KEY_1);

		Assert.assertNull(_ehcachePortalCache.putIfAbsent(_KEY_1, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(2);
		_defaultPortalCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_defaultPortalCacheListener.assertPut(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(2);
		_defaultPortalCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);
		_defaultPortalCacheReplicator.assertPut(_KEY_1, _VALUE_1);

		_defaultPortalCacheReplicator.reset();
	}

	@Test
	public void testReconfigEhcache() {
		Assert.assertSame(_ehcache, _ehcachePortalCache.ehcache);

		Map<PortalCacheListener<String, String>, PortalCacheListenerScope>
			oldPortalCacheListeners =
				_ehcachePortalCache.getPortalCacheListeners();

		_cacheManager.addCache(_NEW_PORTAL_CACHE_NAME);

		Ehcache ehcache2 = _cacheManager.getCache(_NEW_PORTAL_CACHE_NAME);

		_ehcachePortalCache.reconfigEhcache(ehcache2);

		Assert.assertSame(ehcache2, _ehcachePortalCache.ehcache);

		Assert.assertEquals(
			oldPortalCacheListeners,
			_ehcachePortalCache.getPortalCacheListeners());

		RegisteredEventListeners registeredEventListeners =
			_ehcache.getCacheEventNotificationService();

		Set<CacheEventListener> cacheEventListeners =
			registeredEventListeners.getCacheEventListeners();

		Assert.assertTrue(cacheEventListeners.isEmpty());

		registeredEventListeners = ehcache2.getCacheEventNotificationService();

		cacheEventListeners = registeredEventListeners.getCacheEventListeners();

		Assert.assertFalse(cacheEventListeners.isEmpty());
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		// Remove 1

		Assert.assertTrue(_ehcachePortalCache.remove(_KEY_1, _VALUE_1));

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertRemoved(_KEY_1, _VALUE_1);

		_defaultPortalCacheReplicator.reset();

		// Remove 2

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		_ehcachePortalCache.remove(_KEY_2);

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(3);
		_defaultPortalCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultPortalCacheListener.assertRemoved(_KEY_2, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(3);
		_defaultPortalCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_defaultPortalCacheReplicator.assertRemoved(_KEY_2, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Remove 3

		PortalCacheHelperUtil.removeWithoutReplicator(
			_ehcachePortalCache, _KEY_1);

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(0);

		// Remove 4

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		_ehcachePortalCache.removeAll();

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(3);
		_defaultPortalCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultPortalCacheListener.assertRemoveAll();

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(3);
		_defaultPortalCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_defaultPortalCacheReplicator.assertRemoveAll();

		_defaultPortalCacheReplicator.reset();

		// Remove 5

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);
		_ehcachePortalCache.put(_KEY_2, _VALUE_2);

		PortalCacheHelperUtil.removeAllWithoutReplicator(_ehcachePortalCache);

		Assert.assertNull(_ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(3);
		_defaultPortalCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2);
		_defaultPortalCacheListener.assertRemoveAll();

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(2);
		_defaultPortalCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultPortalCacheReplicator.reset();
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

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultPortalCacheReplicator.reset();

		// Replace 2

		Assert.assertNull(_ehcachePortalCache.replace(_KEY_2, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(0);
		_defaultPortalCacheReplicator.assertActionsCount(0);

		// Replace 3

		Assert.assertTrue(
			_ehcachePortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertActionsCount(1);
		_defaultPortalCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultPortalCacheReplicator.reset();

		// Replace 4

		Assert.assertFalse(
			_ehcachePortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _ehcachePortalCache.get(_KEY_1));
		Assert.assertNull(_ehcachePortalCache.get(_KEY_2));

		_defaultPortalCacheListener.assertActionsCount(0);
		_defaultPortalCacheReplicator.assertActionsCount(0);
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

		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultPortalCacheReplicator.reset();

		// Put if absent

		ehcache.removeElement(element);

		_ehcachePortalCache.putIfAbsent(_KEY_2, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_2);

		Assert.assertEquals(_KEY_2, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultPortalCacheListener.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertPut(_KEY_2, _VALUE_2, timeToLive);

		_defaultPortalCacheReplicator.reset();

		// Replace 1

		ehcache.removeElement(element);

		_ehcachePortalCache.replace(_KEY_1, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_1);

		Assert.assertEquals(_KEY_1, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertUpdated(
			_KEY_1, _VALUE_2, timeToLive);

		_defaultPortalCacheReplicator.reset();

		// Replace 2

		ehcache.removeElement(element);

		_ehcachePortalCache.put(_KEY_1, _VALUE_1);

		_ehcachePortalCache.replace(_KEY_1, _VALUE_1, _VALUE_2, timeToLive);

		element = ehcache.get(_KEY_1);

		Assert.assertEquals(_KEY_1, element.getObjectKey());
		Assert.assertEquals(_VALUE_2, element.getObjectValue());
		Assert.assertEquals(timeToLive, element.getTimeToLive());

		_defaultPortalCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheListener.assertUpdated(_KEY_1, _VALUE_2, timeToLive);

		_defaultPortalCacheListener.reset();

		_defaultPortalCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultPortalCacheReplicator.assertUpdated(
			_KEY_1, _VALUE_2, timeToLive);

		_defaultPortalCacheReplicator.reset();
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _KEY_3 = "KEY_3";

	private static final String _NEW_PORTAL_CACHE_NAME =
		"NEW_PORTAL_CACHE_NAME";

	private static final String _PORTAL_CACHE_MANAGER_NAME =
		"PORTAL_CACHE_MANAGER_NAME";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private static final String _VALUE_3 = "VALUE_3";

	private static CacheManager _cacheManager;

	private TestPortalCacheListener<String, String> _defaultPortalCacheListener;
	private TestPortalCacheReplicator<String, String>
		_defaultPortalCacheReplicator;
	private Ehcache _ehcache;
	private EhcachePortalCache<String, String> _ehcachePortalCache;

}