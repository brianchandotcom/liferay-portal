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

import com.liferay.portal.cache.MockPortalCacheManager;
import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.TestCacheReplicator;
import com.liferay.portal.kernel.cache.AbstractPortalCache;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class MemoryPortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(AbstractPortalCache.class);
			}

		};

	@Before
	public void setUp() {
		_memoryPortalCache = new MemoryPortalCache<String, String>(
			new MockPortalCacheManager<String, String>(_CACHE_MANAGER_NAME),
			_CACHE_NAME, 16);

		_memoryPortalCache.put(_KEY_1, _VALUE_1);

		_defaultCacheListener = new TestCacheListener<String, String>();

		_memoryPortalCache.registerCacheListener(_defaultCacheListener);

		_defaultCacheReplicator = new TestCacheReplicator<String, String>();

		_memoryPortalCache.registerCacheListener(_defaultCacheReplicator);
	}

	@Test
	public void testCacheEventListener() {

		// Register

		TestCacheListener<String, String> cacheListener =
			new TestCacheListener<String, String>();

		_memoryPortalCache.registerCacheListener(
			cacheListener, CacheListenerScope.ALL);

		_memoryPortalCache.put(_KEY_1, _VALUE_2);

		cacheListener.assertActionsCount(1);
		cacheListener.assertUpdated(_KEY_1, _VALUE_2);

		cacheListener.reset();

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Unregister

		_memoryPortalCache.unregisterCacheListener(cacheListener);

		_memoryPortalCache.put(_KEY_1, _VALUE_1);

		cacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// unregister all

		_memoryPortalCache.unregisterCacheListeners();

		_memoryPortalCache.put(_KEY_1, _VALUE_2);

		cacheListener.assertActionsCount(0);

		_defaultCacheListener.assertActionsCount(0);

		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testDestroy() {
		_memoryPortalCache.destroy();

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertRemoveAll();

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertRemoveAll();

		_defaultCacheReplicator.reset();
	}

	@Test
	public void testGet() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));

		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		try {
			_memoryPortalCache.get(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}
	}

	@Test
	public void testGetKeys() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		List<String> keys = _memoryPortalCache.getKeys();

		Assert.assertEquals(1, keys.size());
		Assert.assertTrue(keys.contains(_KEY_1));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(_CACHE_NAME, _memoryPortalCache.getName());
	}

	@Test
	public void testGetPortalCacheManager() {
		PortalCacheManager<String, String> portalCacheManager =
			_memoryPortalCache.getPortalCacheManager();

		Assert.assertEquals(_CACHE_MANAGER_NAME, portalCacheManager.getName());
	}

	@Test
	public void testPut() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		// Put 1

		_memoryPortalCache.put(_KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Put 2

		_memoryPortalCache.put(_KEY_2, _VALUE_1, 10);

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_1, 10);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_2, _VALUE_1, 10);

		_defaultCacheReplicator.reset();

		// Put 3

		try {
			_memoryPortalCache.put(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put 4

		try {
			_memoryPortalCache.put(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put 5

		try {
			_memoryPortalCache.put(_KEY_1, _VALUE_1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put 6

		PortalCacheHelperUtil.putWithoutReplicator(
			_memoryPortalCache, _KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(0);

		// Put 7

		PortalCacheHelperUtil.putWithoutReplicator(
			_memoryPortalCache, _KEY_2, _VALUE_1, 10);

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_2, _VALUE_1, 10);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testPutIfAbsent() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		// Put if absent 1

		Assert.assertNull(_memoryPortalCache.putIfAbsent(_KEY_2, _VALUE_2));

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Put if absent 2

		Assert.assertEquals(
			_VALUE_1, _memoryPortalCache.putIfAbsent(_KEY_1, _VALUE_2, 10));

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put if absent 3

		try {
			_memoryPortalCache.putIfAbsent(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put if absent 4

		try {
			_memoryPortalCache.putIfAbsent(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Put if absent 5

		try {
			_memoryPortalCache.putIfAbsent(_KEY_1, _VALUE_1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		// Remove 1

		_memoryPortalCache.remove(_KEY_1);

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Remove 2

		_memoryPortalCache.put(_KEY_1, _VALUE_1);

		PortalCacheHelperUtil.removeWithoutReplicator(
			_memoryPortalCache, _KEY_1);

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(2);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Remove 3

		_memoryPortalCache.remove(_KEY_2);

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Remove 4

		_memoryPortalCache.put(_KEY_1, _VALUE_1);

		Assert.assertTrue(_memoryPortalCache.remove(_KEY_1, _VALUE_1));

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(2);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheListener.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(2);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_defaultCacheReplicator.assertRemoved(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Remove 5

		_memoryPortalCache.put(_KEY_1, _VALUE_1);

		Assert.assertFalse(_memoryPortalCache.remove(_KEY_1, _VALUE_2));

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertPut(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertPut(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Remove 6

		try {
			_memoryPortalCache.remove(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Remove 7

		try {
			_memoryPortalCache.remove(null, _VALUE_1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Remove 8

		try {
			_memoryPortalCache.remove(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testRemoveAll() {
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		// Remove all 1

		_memoryPortalCache.removeAll();

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertRemoveAll();

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertRemoveAll();

		_defaultCacheReplicator.reset();

		// Remove all 2

		_memoryPortalCache.put(_KEY_1, _VALUE_1);
		_memoryPortalCache.put(_KEY_2, _VALUE_2);

		PortalCacheHelperUtil.removeAllWithoutReplicator(_memoryPortalCache);

		Assert.assertNull(_memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

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
		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		// Replace 1

		Assert.assertEquals(
			_VALUE_1, _memoryPortalCache.replace(_KEY_1, _VALUE_2));

		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);

		_defaultCacheReplicator.reset();

		// Replace 2

		Assert.assertNull(_memoryPortalCache.replace(_KEY_2, _VALUE_2, 10));

		Assert.assertEquals(_VALUE_2, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 3

		Assert.assertTrue(
			_memoryPortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1));

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(1);
		_defaultCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheListener.reset();

		_defaultCacheReplicator.assertActionsCount(1);
		_defaultCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);

		_defaultCacheReplicator.reset();

		// Replace 4

		Assert.assertFalse(
			_memoryPortalCache.replace(_KEY_1, _VALUE_2, _VALUE_1, 10));

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 5

		try {
			_memoryPortalCache.replace(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 6

		try {
			_memoryPortalCache.replace(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 7

		try {
			_memoryPortalCache.replace(_KEY_1, _VALUE_1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 8

		try {
			_memoryPortalCache.replace(null, _VALUE_1, _VALUE_2);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 9

		try {
			_memoryPortalCache.replace(_KEY_1, null, _VALUE_2);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Old value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 10

		try {
			_memoryPortalCache.replace(_KEY_1, _VALUE_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("New value is null", npe.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);

		// Replace 11

		try {
			_memoryPortalCache.replace(_KEY_1, _VALUE_1, _VALUE_2, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		Assert.assertEquals(_VALUE_1, _memoryPortalCache.get(_KEY_1));
		Assert.assertNull(_memoryPortalCache.get(_KEY_2));

		_defaultCacheListener.assertActionsCount(0);
		_defaultCacheReplicator.assertActionsCount(0);
	}

	private static final String _CACHE_MANAGER_NAME = "CACHE_MANAGER_NAME";

	private static final String _CACHE_NAME = "CACHE_NAME";

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private TestCacheListener<String, String> _defaultCacheListener;
	private TestCacheReplicator<String, String> _defaultCacheReplicator;
	private MemoryPortalCache<String, String> _memoryPortalCache;

}