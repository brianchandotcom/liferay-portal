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

package com.liferay.portal.cache.keypool;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.util.BaseTestCase;

/**
 * @author Shuyang Zhou
 */
public class MultiVMKeyPoolPortalCacheTest extends BaseTestCase {

	public void testPutAndGet() {
		PortalCache clusterPortalCache = SingleVMPoolUtil.getCache(
			"ClusterPortalCache");
		PortalCache localPortalCache = SingleVMPoolUtil.getCache(
			"LocalPortalCache");

		MultiVMKeyPoolPortalCache multiVMKeyPoolPortalCache =
			new MultiVMKeyPoolPortalCache(clusterPortalCache, localPortalCache);

		String testKey = "testKey";
		String testValue = "testValue";

		multiVMKeyPoolPortalCache.put(testKey, testValue);

		assertEquals(testValue, multiVMKeyPoolPortalCache.get(testKey));
	}

}