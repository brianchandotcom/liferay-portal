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

import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.key.TestCompositePortalCacheKey;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crar
 */
public class CompositePortalCacheKeyManagerTest {

	@Before
	public void setUp() {
		_memoryPortalCache = new MemoryPortalCache<>(
			new MockPortalCacheManager<TestCompositePortalCacheKey, String>(
				"Test Cache Manager"),
			"Test Cache", 16);

		_cacheKeyManager = new CompositePortalCacheKeyManager<>(
			_memoryPortalCache);
	}

	@Test
	public void testGetBySimpleKey() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});

		_memoryPortalCache.put(key1, StringPool.BLANK);

		Set<TestCompositePortalCacheKey> set = _cacheKeyManager.getBySimpleKey(
			key1.getSimpleKey());

		Assert.assertTrue(set.contains(key1));
	}

	@Test
	public void testGetSimpleKeys() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});

		_memoryPortalCache.put(key1, StringPool.BLANK);

		Set<String> set = _cacheKeyManager.getSimpleKeys();

		Assert.assertTrue(set.contains(key1.getSimpleKey()));
	}

	@Test
	public void testNotifyEntryEvicted() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});
		TestCompositePortalCacheKey key2 = new TestCompositePortalCacheKey(
			new String[] {"Two", "Second"});

		_memoryPortalCache.put(key1, StringPool.BLANK);
		_memoryPortalCache.put(key2, StringPool.BLANK);

		_cacheKeyManager.notifyEntryEvicted(
			_memoryPortalCache, key1, StringPool.BLANK, 0);

		Set<TestCompositePortalCacheKey> keys = _cacheKeyManager.getBySimpleKey(
			key1.getSimpleKey());

		Assert.assertTrue("Key not evicted", keys.isEmpty());

		keys = _cacheKeyManager.getBySimpleKey(key2.getSimpleKey());

		Assert.assertTrue("Wrong key evicted", keys.contains(key2));
	}

	@Test
	public void testNotifyEntryExpired() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});
		TestCompositePortalCacheKey key2 = new TestCompositePortalCacheKey(
			new String[] {"Two", "Second"});

		_memoryPortalCache.put(key1, StringPool.BLANK);
		_memoryPortalCache.put(key2, StringPool.BLANK);

		_cacheKeyManager.notifyEntryExpired(
			_memoryPortalCache, key1, StringPool.BLANK, 0);

		Set<TestCompositePortalCacheKey> keys = _cacheKeyManager.getBySimpleKey(
			key1.getSimpleKey());

		Assert.assertTrue("Key not expired", keys.isEmpty());

		keys = _cacheKeyManager.getBySimpleKey(key2.getSimpleKey());

		Assert.assertTrue("Wrong key expired", keys.contains(key2));
	}

	@Test
	public void testNotifyEntryPut() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});

		_memoryPortalCache.put(key1, StringPool.BLANK);

		Set<TestCompositePortalCacheKey> set = _cacheKeyManager.getBySimpleKey(
			"One");

		Assert.assertTrue("Key not added to manager", set.contains(key1));
	}

	@Test
	public void testNotifyEntryRemoved() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});
		TestCompositePortalCacheKey key2 = new TestCompositePortalCacheKey(
			new String[] {"One", "Second"});

		_memoryPortalCache.put(key1, StringPool.BLANK);
		_memoryPortalCache.put(key2, StringPool.BLANK);

		Set<TestCompositePortalCacheKey> set = _cacheKeyManager.getBySimpleKey(
			"One");

		Assert.assertTrue("Key not added", set.contains(key1));

		_memoryPortalCache.remove(key1);

		set = _cacheKeyManager.getBySimpleKey("One");

		Assert.assertFalse("Key not removed", set.contains(key1));
		Assert.assertTrue("Key removed", set.contains(key2));
	}

	@Test
	public void testNotifyRemoveAll() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});

		_memoryPortalCache.put(key1, StringPool.BLANK);

		Set<TestCompositePortalCacheKey> set = _cacheKeyManager.getBySimpleKey(
			"One");

		Assert.assertTrue("Key not added", set.contains(key1));

		_memoryPortalCache.removeAll();

		set = _cacheKeyManager.getBySimpleKey("One");

		Assert.assertTrue("Key not removed", set.isEmpty());
	}

	@Test
	public void testRemoveBySimpleKey() {
		TestCompositePortalCacheKey key1 = new TestCompositePortalCacheKey(
			new String[] {"One", "First"});
		TestCompositePortalCacheKey key2 = new TestCompositePortalCacheKey(
			new String[] {"Two", "Second"});

		_memoryPortalCache.put(key1, StringPool.BLANK);
		_memoryPortalCache.put(key2, StringPool.BLANK);

		_cacheKeyManager.removeBySimpleKey(key1.getSimpleKey());

		List<TestCompositePortalCacheKey> keys = _memoryPortalCache.getKeys();

		Assert.assertFalse("Key not removed from cache", keys.contains(key1));

		Set<TestCompositePortalCacheKey> set = _cacheKeyManager.getBySimpleKey(
			key1.getSimpleKey());

		Assert.assertFalse("Key not removed from manager", set.contains(key1));

		Assert.assertTrue("Key removed from cache", keys.contains(key2));

		set = _cacheKeyManager.getBySimpleKey(key2.getSimpleKey());

		Assert.assertTrue("Key removed from manager", set.contains(key2));
	}

	private CompositePortalCacheKeyManager<TestCompositePortalCacheKey, String>
		_cacheKeyManager;
	private PortalCache<TestCompositePortalCacheKey, String> _memoryPortalCache;

}