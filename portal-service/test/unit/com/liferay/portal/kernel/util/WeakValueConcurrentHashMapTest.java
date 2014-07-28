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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.memory.DummyFinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class WeakValueConcurrentHashMapTest {

	@Test
	public void testAutoRemove() throws Exception {
		System.setProperty(
			FinalizeManager.class.getName() + ".thread.enabled",
			StringPool.FALSE);

		Map<String, Object> map =
			new WeakValueConcurrentHashMap<String, Object>();

		String testKey = "testKey";
		Object testValue = new Object();

		map.put(testKey, testValue);

		GCUtil.gc();

		Assert.assertTrue(map.containsKey(testKey));

		testValue = null;

		GCUtil.gc();

		if (!FinalizeManager.THREAD_ENABLED) {
			FinalizeManager.register(new Object(), new DummyFinalizeAction());
		}

		Assert.assertFalse(map.containsKey(testKey));
	}

}