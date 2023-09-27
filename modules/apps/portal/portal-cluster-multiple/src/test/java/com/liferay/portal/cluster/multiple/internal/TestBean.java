/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;

/**
 * @author Tina Tian
 */
public class TestBean {

	public static String timestamp;

	public static String testMethod1(String timestamp) {
		if (timestamp.length() == 0) {
			return null;
		}

		TestBean.timestamp = timestamp;

		return timestamp;
	}

	public static Object testMethod2() {
		return new Object();
	}

	public static Object testMethod3(String timestamp) throws Exception {
		throw new Exception(timestamp);
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

	public static boolean testMethod5() {
		return ClusterInvokeThreadLocal.isEnabled();
	}

	private static final String _THREAD_LOCAL_CACHE_KEY =
		"thread_local_cache_key";

	private static final ThreadLocal<String> _testThreadLocal =
		new CentralizedThreadLocal<>(true);

}