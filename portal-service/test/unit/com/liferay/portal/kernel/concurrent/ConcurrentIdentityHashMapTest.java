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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentIdentityHashMapTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		ConcurrentMap<IdentityKey<String>, Object> innerMap =
			new ConcurrentHashMap<IdentityKey<String>, Object>();

		ConcurrentIdentityHashMap<String, Object> concurrentIdentityHashMap =
			new ConcurrentIdentityHashMap<String, Object>(innerMap);

		Assert.assertSame(innerMap, concurrentIdentityHashMap.innerMap);

		Map<String, Object> dataMap = _createDataMap();

		concurrentIdentityHashMap =
			new ConcurrentIdentityHashMap<String, Object>(dataMap);

		Assert.assertEquals(dataMap, concurrentIdentityHashMap);

		new ConcurrentIdentityHashMap<String, Object>(10);
		new ConcurrentIdentityHashMap<String, Object>(10, 0.75F, 4);
	}

	@Test
	public void testIdentityHashMap() {
		ConcurrentIdentityHashMap<String, Object> concurrentIdentityHashMap =
			new ConcurrentIdentityHashMap<String, Object>();

		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_testKey));
		Assert.assertFalse(concurrentIdentityHashMap.containsValue(_testValue));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_testKey2));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertNull(concurrentIdentityHashMap.put(_testKey, _testValue));
		Assert.assertTrue(concurrentIdentityHashMap.containsKey(_testKey));
		Assert.assertTrue(concurrentIdentityHashMap.containsValue(_testValue));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_testKey2));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertSame(_testValue, concurrentIdentityHashMap.get(_testKey));
		Assert.assertNull(concurrentIdentityHashMap.get(_testKey2));
		Assert.assertSame(
			_testValue, concurrentIdentityHashMap.put(_testKey, _testValue2));
		Assert.assertTrue(concurrentIdentityHashMap.containsKey(_testKey));
		Assert.assertFalse(concurrentIdentityHashMap.containsValue(_testValue));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_testKey2));
		Assert.assertTrue(concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertSame(_testValue2, concurrentIdentityHashMap.get(_testKey));
		Assert.assertNull(concurrentIdentityHashMap.get(_testKey2));

		Set<String> keySet = concurrentIdentityHashMap.keySet();

		Iterator<String> iterator = keySet.iterator();

		Assert.assertSame(_testKey, iterator.next());
		Assert.assertFalse(iterator.hasNext());
	}

	private Map<String, Object> _createDataMap() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(_testKey, _testValue);
		map.put("testKey2", _testValue2);

		return map;
	}

	private final String _testKey = "testKey";
	private final String _testKey2 = new String(_testKey);
	private final Object _testValue = new Object();
	private final Object _testValue2 = new Object();

}