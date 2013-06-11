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

import com.liferay.portal.kernel.test.TestCase;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLFUCacheTest extends TestCase {

	@Test
	public void testConstruct() {

		// maxSize is 0

		try {
			new ConcurrentLFUCache<Object, Object>(0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLFUCache<Object, Object>(0, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxSize is less than 0

		try {
			new ConcurrentLFUCache<Object, Object>(-1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLFUCache<Object, Object>(-1, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, 0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is less than 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 1

		try {
			new ConcurrentLFUCache<Object, Object>(10, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is greater than 1

		try {
			new ConcurrentLFUCache<Object, Object>(10, 1.1F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small loadFactor causes _expectSize to be 0

		try {
			new ConcurrentLFUCache<Object, Object>(1, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small maxSize causes _expectSize to be 0

		try {
			new ConcurrentLFUCache<Object, Object>(10, 0.09F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		ConcurrentLFUCache<String, String> concurrentLFUCache =
			new ConcurrentLFUCache<String, String>(10);

		assertEquals(0, concurrentLFUCache.evictCount());
		assertEquals(0, concurrentLFUCache.hitCount());
		assertEquals(10, concurrentLFUCache.maxSize());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(0, concurrentLFUCache.putCount());
		assertEquals(0, concurrentLFUCache.size());

		assertNull(concurrentLFUCache.get("key"));
	}

	@Test
	public void testLFU1() {
		ConcurrentLFUCache<String, String> concurrentLFUCache =
			new ConcurrentLFUCache<String, String>(2, 0.5F);

		try {
			concurrentLFUCache.put(null, "value");

			fail();
		}
		catch (NullPointerException npe) {
		}

		concurrentLFUCache.put("key1", "value1");

		assertEquals(0, concurrentLFUCache.evictCount());
		assertEquals(0, concurrentLFUCache.hitCount());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(1, concurrentLFUCache.putCount());
		assertEquals(1, concurrentLFUCache.size());

		assertEquals("value1", concurrentLFUCache.get("key1"));

		assertEquals(0, concurrentLFUCache.evictCount());
		assertEquals(1, concurrentLFUCache.hitCount());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(1, concurrentLFUCache.putCount());
		assertEquals(1, concurrentLFUCache.size());

		concurrentLFUCache.put("key2", "value2");

		assertEquals(0, concurrentLFUCache.evictCount());
		assertEquals(1, concurrentLFUCache.hitCount());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(2, concurrentLFUCache.putCount());
		assertEquals(2, concurrentLFUCache.size());

		concurrentLFUCache.put("key2", "value2-2");

		assertEquals(0, concurrentLFUCache.evictCount());
		assertEquals(1, concurrentLFUCache.hitCount());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(3, concurrentLFUCache.putCount());
		assertEquals(2, concurrentLFUCache.size());

		concurrentLFUCache.put("key3", "value3");

		assertEquals(1, concurrentLFUCache.evictCount());
		assertEquals(1, concurrentLFUCache.hitCount());
		assertEquals(0, concurrentLFUCache.missCount());
		assertEquals(4, concurrentLFUCache.putCount());
		assertEquals(2, concurrentLFUCache.size());

		assertEquals("value1", concurrentLFUCache.get("key1"));
		assertNull(concurrentLFUCache.get("key2"));
		assertEquals("value3", concurrentLFUCache.get("key3"));

		assertEquals(1, concurrentLFUCache.evictCount());
		assertEquals(3, concurrentLFUCache.hitCount());
		assertEquals(1, concurrentLFUCache.missCount());
		assertEquals(4, concurrentLFUCache.putCount());
		assertEquals(2, concurrentLFUCache.size());
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

		assertNotNull(concurrentLFUCache.get("1"));
		assertNotNull(concurrentLFUCache.get("2"));

		assertNull(concurrentLFUCache.get("3"));
	}

}