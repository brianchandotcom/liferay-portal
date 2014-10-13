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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;

/**
 * @author Tina Tian
 */
public class TestBean {

	public static String TIMESTAMP;

	public static String testMethod1(String timeStamp) {
		if (timeStamp.length() == 0) {
			return null;
		}

		TIMESTAMP = timeStamp;

		return timeStamp;
	}

	public static Object testMethod2() {
		return new Object();
	}

	public static Object testMethod3(String timeStamp) throws Exception {
		throw new Exception(timeStamp);
	}

	public static String testMethod4(String value) {
		ThreadLocalCache<String> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, TestBean.class.getName());

		if (value.length() > 0) {
			threadLocalCache.put(_THREAD_LOCAL_CACHE_KEY, value);

			_testThreadLocal.set(value);

			return value;
		}

		if (_testThreadLocal.get() != null) {
			throw new IllegalStateException(
				"Short live thread local has not been cleared");
		}

		if (threadLocalCache.get(_THREAD_LOCAL_CACHE_KEY) != null) {
			throw new IllegalStateException(
				"Thread local cache was not cleared");
		}

		return null;
	}

	private static final String _THREAD_LOCAL_CACHE_KEY  =
		"thread_local_cache_key";

	private static final ThreadLocal<String> _testThreadLocal =
		new CentralizedThreadLocal<String>(true);

}