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

package com.liferay.portal.kernel.concurrent;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLFUCacheTest {

	@Test
	public void testConstruct() {

		// maxSize is 0

		try {
			new ConcurrentLFUCache<Object, Object>(0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLFUCache<Object, Object>(0, 0.9F);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxSize is less than 0

		try {
			new ConcurrentLFUCache<Object, Object>(-1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLFUCache<Object, Object>(-1, 0.9F);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, 0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is less than 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 1

		try {
			new ConcurrentLFUCache<Object, Object>(10, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is greater than 1

		try {
			new ConcurrentLFUCache<Object, Object>(10, 1.1F);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small loadFactor causes _expectSize to be 0

		try {
			new ConcurrentLFUCache<Object, Object>(1, 0.9F);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small maxSize causes _expectSize to be 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, 0.09F);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		ConcurrentLFUCache<String, String> concurrentLFUCache =
			new ConcurrentLFUCache<String, String>(10);

		Assert.assertEquals(0, concurrentLFUCache.evictCount());
		Assert.assertEquals(0, concurrentLFUCache.hitCount());
		Assert.assertEquals(10, concurrentLFUCache.maxSize());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(0, concurrentLFUCache.putCount());
		Assert.assertEquals(0, concurrentLFUCache.size());

		Assert.assertNull(concurrentLFUCache.get("key"));
	}

	@Test
	public void testLFU1() {
		ConcurrentLFUCache<String, String> concurrentLFUCache =
			new ConcurrentLFUCache<String, String>(2, 0.5F);

		try {
			concurrentLFUCache.put(null, "value");

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		concurrentLFUCache.put("key1", "value1");

		Assert.assertEquals(0, concurrentLFUCache.evictCount());
		Assert.assertEquals(0, concurrentLFUCache.hitCount());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(1, concurrentLFUCache.putCount());
		Assert.assertEquals(1, concurrentLFUCache.size());

		Assert.assertEquals("value1", concurrentLFUCache.get("key1"));

		Assert.assertEquals(0, concurrentLFUCache.evictCount());
		Assert.assertEquals(1, concurrentLFUCache.hitCount());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(1, concurrentLFUCache.putCount());
		Assert.assertEquals(1, concurrentLFUCache.size());

		concurrentLFUCache.put("key2", "value2");

		Assert.assertEquals(0, concurrentLFUCache.evictCount());
		Assert.assertEquals(1, concurrentLFUCache.hitCount());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(2, concurrentLFUCache.putCount());
		Assert.assertEquals(2, concurrentLFUCache.size());

		concurrentLFUCache.put("key2", "value2-2");

		Assert.assertEquals(0, concurrentLFUCache.evictCount());
		Assert.assertEquals(1, concurrentLFUCache.hitCount());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(3, concurrentLFUCache.putCount());
		Assert.assertEquals(2, concurrentLFUCache.size());

		concurrentLFUCache.put("key3", "value3");

		Assert.assertEquals(1, concurrentLFUCache.evictCount());
		Assert.assertEquals(1, concurrentLFUCache.hitCount());
		Assert.assertEquals(0, concurrentLFUCache.missCount());
		Assert.assertEquals(4, concurrentLFUCache.putCount());
		Assert.assertEquals(2, concurrentLFUCache.size());

		Assert.assertEquals("value1", concurrentLFUCache.get("key1"));
		Assert.assertNull(concurrentLFUCache.get("key2"));
		Assert.assertEquals("value3", concurrentLFUCache.get("key3"));

		Assert.assertEquals(1, concurrentLFUCache.evictCount());
		Assert.assertEquals(3, concurrentLFUCache.hitCount());
		Assert.assertEquals(1, concurrentLFUCache.missCount());
		Assert.assertEquals(4, concurrentLFUCache.putCount());
		Assert.assertEquals(2, concurrentLFUCache.size());
	}

	@Test
	public void testLFU2() {
		ConcurrentLFUCache<String, String> concurrentLFUCache =
			new ConcurrentLFUCache<String, String>(3);

		concurrentLFUCache.put("1", "1");
		concurrentLFUCache.put("2", "2");
		concurrentLFUCache.put("3", "3");

		concurrentLFUCache.get("1");
		concurrentLFUCache.get("1");
		concurrentLFUCache.get("3");
		concurrentLFUCache.get("2");
		concurrentLFUCache.get("2");

		concurrentLFUCache.put("4", "4");

		Assert.assertNotNull(concurrentLFUCache.get("1"));
		Assert.assertNotNull(concurrentLFUCache.get("2"));

		Assert.assertNull(concurrentLFUCache.get("3"));
	}

}