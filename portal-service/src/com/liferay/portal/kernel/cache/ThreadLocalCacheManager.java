/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.io.Serializable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalCacheManager {

	public static void clearAll(Lifecycle lifecycle) {
		Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>
			threadLocalCacheMaps = _threadLocalCacheMaps.get();

		Map<Serializable, ThreadLocalCache<?>> threadLocalCacheMap =
			threadLocalCacheMaps.get(lifecycle);

		if (threadLocalCacheMap != null) {
			threadLocalCacheMap.clear();
		}
	}

	public static void destroy() {
		_threadLocalCacheMaps.remove();
	}

	public static <T> ThreadLocalCache<T> getThreadLocalCache(
		Lifecycle lifecycle, Serializable name) {

		Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>
			threadLocalCacheMaps = _threadLocalCacheMaps.get();

		Map<Serializable, ThreadLocalCache<?>> threadLocalCacheMap =
			threadLocalCacheMaps.get(lifecycle);

		if (threadLocalCacheMap == null) {
			threadLocalCacheMap =
				new HashMap<Serializable, ThreadLocalCache<?>>();

			threadLocalCacheMaps.put(lifecycle, threadLocalCacheMap);
		}

		ThreadLocalCache<?> threadLocalCache = threadLocalCacheMap.get(name);

		if (threadLocalCache == null) {
			threadLocalCache = new ThreadLocalCache<T>(name, lifecycle);

			threadLocalCacheMap.put(name, threadLocalCache);
		}

		return (ThreadLocalCache<T>)threadLocalCache;
	}

	private static ThreadLocal
		<Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>>
			_threadLocalCacheMaps = new InitialThreadLocal
				<Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>>(
					ThreadLocalCacheManager.class + "._threadLocalCacheMaps",
					new EnumMap
						<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>(
							Lifecycle.class));

}