/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCacheTest extends TestCase {

	public void testConstruct() {

		// maxSize is 0

		try {
			new ConcurrentLRUCache<Object, Object>(0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLRUCache<Object, Object>(0, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// maxSize is less than 0

		try {
			new ConcurrentLRUCache<Object, Object>(-1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new ConcurrentLRUCache<Object, Object>(-1, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 0

		try {
			new ConcurrentLRUCache<Object, Object>(10, 0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is less than 0

		try {
			new ConcurrentLRUCache<Object, Object>(10, -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is 1

		try {
			new ConcurrentLRUCache<Object, Object>(10, 1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// loadFactor is greater than 1

		try {
			new ConcurrentLRUCache<Object, Object>(10, 1.1F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small loadFactor causes _expectSize to be 0

		try {
			new ConcurrentLRUCache<Object, Object>(1, 0.9F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Small maxSize causes _expectSize to be 0

		try {
			new ConcurrentLRUCache<Object, Object>(10, 0.09F);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(10);

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(10, concurrentLRUCache.maxSize());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());

		assertNull(concurrentLRUCache.get("key"));
	}

	public void testLRU() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(2, 0.5F);

		try{
			concurrentLRUCache.put(null, "value");

			fail();
		}
		catch (NullPointerException npe) {
		}

		concurrentLRUCache.put("key1", "value1");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(1, concurrentLRUCache.putCount());
		assertEquals(1, concurrentLRUCache.size());

		assertEquals("value1", concurrentLRUCache.get("key1"));

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(1, concurrentLRUCache.putCount());
		assertEquals(1, concurrentLRUCache.size());

		concurrentLRUCache.put("key2", "value2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key2", "value2-2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(3, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key3", "value3");

		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(4, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		assertEquals("value1", concurrentLRUCache.get("key1"));
		assertNull(concurrentLRUCache.get("key2"));
		assertEquals("value3", concurrentLRUCache.get("key3"));

		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(3, concurrentLRUCache.hitCount());
		assertEquals(1, concurrentLRUCache.missCount());
		assertEquals(4, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());
	}

}