/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentIdentityHashMapTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConcurrentIdentityHashMap() {
		ConcurrentIdentityHashMap<String, Object> concurrentIdentityHashMap =
			new ConcurrentIdentityHashMap<>();

		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_TEST_KEY_1));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue1));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_TEST_KEY_2));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertNull(
			concurrentIdentityHashMap.put(_TEST_KEY_1, _testValue1));
		Assert.assertTrue(concurrentIdentityHashMap.containsKey(_TEST_KEY_1));
		Assert.assertTrue(concurrentIdentityHashMap.containsValue(_testValue1));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_TEST_KEY_2));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertSame(
			_testValue1, concurrentIdentityHashMap.get(_TEST_KEY_1));
		Assert.assertNull(concurrentIdentityHashMap.get(_TEST_KEY_2));
		Assert.assertSame(
			_testValue1,
			concurrentIdentityHashMap.put(_TEST_KEY_1, _testValue2));
		Assert.assertTrue(concurrentIdentityHashMap.containsKey(_TEST_KEY_1));
		Assert.assertFalse(
			concurrentIdentityHashMap.containsValue(_testValue1));
		Assert.assertFalse(concurrentIdentityHashMap.containsKey(_TEST_KEY_2));
		Assert.assertTrue(concurrentIdentityHashMap.containsValue(_testValue2));
		Assert.assertSame(
			_testValue2, concurrentIdentityHashMap.get(_TEST_KEY_1));
		Assert.assertNull(concurrentIdentityHashMap.get(_TEST_KEY_2));

		Set<String> keySet = concurrentIdentityHashMap.keySet();

		Iterator<String> iterator = keySet.iterator();

		Assert.assertSame(_TEST_KEY_1, iterator.next());
		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testConstructor() {
		ConcurrentMap<IdentityKey<String>, Object> innerConcurrentMap =
			new ConcurrentHashMap<>();

		ConcurrentIdentityHashMap<String, Object> concurrentIdentityHashMap =
			new ConcurrentIdentityHashMap<>(innerConcurrentMap);

		Assert.assertSame(
			innerConcurrentMap, concurrentIdentityHashMap.innerConcurrentMap);

		Map<String, Object> dataMap = createDataMap();

		concurrentIdentityHashMap = new ConcurrentIdentityHashMap<>(dataMap);

		Assert.assertEquals(dataMap, concurrentIdentityHashMap);

		new ConcurrentIdentityHashMap<String, Object>(10);
		new ConcurrentIdentityHashMap<String, Object>(10, 0.75F, 4);
	}

	protected Map<String, Object> createDataMap() {
		Map<String, Object> map = new HashMap<>();

		map.put(_TEST_KEY_1, _testValue1);
		map.put("testKey2", _testValue2);

		return map;
	}

	private static final String _TEST_KEY_1 = "testKey";

	private static final String _TEST_KEY_2 = new String(_TEST_KEY_1);

	private final Object _testValue1 = new Object();
	private final Object _testValue2 = new Object();

}