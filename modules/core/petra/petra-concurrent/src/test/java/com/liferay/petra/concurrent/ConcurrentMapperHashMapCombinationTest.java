/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.test.FinalizeManagerUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.ref.Reference;

import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentMapperHashMapCombinationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIdentityKeyWeakValue() throws InterruptedException {
		String testKey1 = "testKey1";

		String testKey2 = new String(testKey1);

		Object testValue1 = new Object();
		Object testValue2 = new Object();

		ConcurrentMap<String, Object> concurrentMap =
			new ConcurrentIdentityHashMap<>(
				new ConcurrentReferenceValueHashMap
					<IdentityKey<String>, Object>(
						FinalizeManager.WEAK_REFERENCE_FACTORY));

		Assert.assertNull(concurrentMap.put(testKey1, testValue1));
		Assert.assertNull(concurrentMap.put(testKey2, testValue2));
		Assert.assertEquals(concurrentMap.toString(), 2, concurrentMap.size());
		Assert.assertTrue(concurrentMap.containsKey(testKey1));
		Assert.assertTrue(concurrentMap.containsValue(testValue1));
		Assert.assertTrue(concurrentMap.containsKey(testKey2));
		Assert.assertTrue(concurrentMap.containsValue(testValue2));
		Assert.assertSame(testValue1, concurrentMap.get(testKey1));
		Assert.assertSame(testValue2, concurrentMap.get(testKey2));

		testValue1 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertEquals(concurrentMap.toString(), 1, concurrentMap.size());
		Assert.assertTrue(concurrentMap.containsKey(testKey2));

		testValue2 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertTrue(concurrentMap.toString(), concurrentMap.isEmpty());
	}

	@Test
	public void testSoftKeyWeakValue() throws InterruptedException {
		String testKey1 = new String("testKey1");
		String testKey2 = new String("testKey2");
		Object testValue1 = new Object();
		Object testValue2 = new Object();

		ConcurrentMap<String, Object> concurrentReferenceMap =
			new ConcurrentReferenceKeyHashMap<>(
				new ConcurrentReferenceValueHashMap<Reference<String>, Object>(
					FinalizeManager.WEAK_REFERENCE_FACTORY),
				FinalizeManager.SOFT_REFERENCE_FACTORY);

		Assert.assertNull(concurrentReferenceMap.put(testKey1, testValue1));
		Assert.assertNull(concurrentReferenceMap.put(testKey2, testValue2));
		Assert.assertEquals(
			concurrentReferenceMap.toString(), 2,
			concurrentReferenceMap.size());
		Assert.assertTrue(concurrentReferenceMap.containsKey(testKey1));
		Assert.assertTrue(concurrentReferenceMap.containsValue(testValue1));
		Assert.assertSame(testValue1, concurrentReferenceMap.get(testKey1));
		Assert.assertTrue(concurrentReferenceMap.containsKey(testKey2));
		Assert.assertTrue(concurrentReferenceMap.containsValue(testValue2));
		Assert.assertSame(testValue2, concurrentReferenceMap.get(testKey2));

		testKey1 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertEquals(
			concurrentReferenceMap.toString(), 2,
			concurrentReferenceMap.size());
		Assert.assertTrue(concurrentReferenceMap.containsValue(testValue1));
		Assert.assertTrue(concurrentReferenceMap.containsKey(testKey2));
		Assert.assertTrue(concurrentReferenceMap.containsValue(testValue2));
		Assert.assertSame(testValue2, concurrentReferenceMap.get(testKey2));

		GCUtil.fullGC(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertEquals(
			concurrentReferenceMap.toString(), 1,
			concurrentReferenceMap.size());
		Assert.assertTrue(concurrentReferenceMap.containsKey(testKey2));
		Assert.assertTrue(concurrentReferenceMap.containsValue(testValue2));
		Assert.assertSame(testValue2, concurrentReferenceMap.get(testKey2));

		testValue2 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertTrue(
			concurrentReferenceMap.toString(),
			concurrentReferenceMap.isEmpty());
	}

}