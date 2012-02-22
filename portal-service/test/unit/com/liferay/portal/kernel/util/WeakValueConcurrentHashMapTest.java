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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.memory.DummyFinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Shuyang Zhou
 */
public class WeakValueConcurrentHashMapTest extends TestCase {

	public void testAutoRemove() throws Exception{
		Thread thread = Thread.currentThread();

		PortalClassLoaderUtil.setClassLoader(thread.getContextClassLoader());

		WeakValueConcurrentHashMap<String, Object> weakValueConcurrentHashMap =
			new WeakValueConcurrentHashMap<String, Object>();

		String testKey = "testKey";
		Object testValue = new Object();

		weakValueConcurrentHashMap.put(testKey, testValue);

		long startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 100) {
			System.gc();

			Thread.sleep(1);

			assertTrue(weakValueConcurrentHashMap.containsKey(testKey));
		}

		// Release key for gc

		testValue = null;

		startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 100) {
			System.gc();

			Thread.sleep(1);

			if (!FinalizeManager.THREAD_ENABLED) {
				// Manually trigger cleanup when FianlizeManager's thread is
				// diabled

				FinalizeManager.register(
					new Object(), new DummyFinalizeAction());
			}

			if (!weakValueConcurrentHashMap.containsKey(testKey)) {
				break;
			}
		}

		assertFalse(weakValueConcurrentHashMap.containsKey(testKey));

		PortalClassLoaderUtil.setClassLoader(null);
	}

}