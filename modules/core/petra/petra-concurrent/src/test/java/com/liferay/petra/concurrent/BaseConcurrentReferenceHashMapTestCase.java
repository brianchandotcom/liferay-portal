/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import com.liferay.petra.memory.FinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.test.FinalizeManagerUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.lang.ref.Reference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseConcurrentReferenceHashMapTestCase {

	protected Map<String, Object> createDataMap() {
		Map<String, Object> map = new HashMap<>();

		map.put("testKey1", new Object());
		map.put("testKey2", new Object());

		return map;
	}

	protected void testAutoRemove(
			ConcurrentMap<String, Object> concurrentMap, boolean fullGC)
		throws InterruptedException {

		Map<Reference<?>, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		String testKey = new String("testKey");
		Object testValue1 = new Object();
		Object testValue2 = new Object();

		Assert.assertFalse(concurrentMap.containsKey(testKey));
		Assert.assertTrue(
			finalizeActions.toString(), finalizeActions.isEmpty());
		Assert.assertNull(concurrentMap.put(testKey, testValue1));
		Assert.assertTrue(concurrentMap.containsKey(testKey));
		Assert.assertSame(testValue1, concurrentMap.get(testKey));
		Assert.assertTrue(concurrentMap.containsValue(testValue1));
		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());
		Assert.assertSame(testValue1, concurrentMap.put(testKey, testValue2));
		Assert.assertTrue(concurrentMap.containsKey(testKey));
		Assert.assertSame(testValue2, concurrentMap.get(testKey));
		Assert.assertFalse(concurrentMap.containsValue(testValue1));
		Assert.assertTrue(concurrentMap.containsValue(testValue2));
		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());

		Set<String> keySet = concurrentMap.keySet();

		Iterator<String> keyIterator = keySet.iterator();

		Assert.assertSame(testKey, keyIterator.next());

		Collection<Object> values = concurrentMap.values();

		Iterator<Object> valueIterator = values.iterator();

		Assert.assertSame(testValue2, valueIterator.next());

		Assert.assertSame(
			testValue2, concurrentMap.replace(testKey, new Object()));
		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());
		Assert.assertEquals(concurrentMap.toString(), 1, concurrentMap.size());

		testKey = null;

		if (fullGC) {
			GCUtil.fullGC(true);
		}
		else {
			GCUtil.gc(true);
		}

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertTrue(
			finalizeActions.toString(), finalizeActions.isEmpty());
		Assert.assertTrue(concurrentMap.toString(), concurrentMap.isEmpty());
	}

}