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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.io.Serializable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalCacheManager {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new TransactionLifecycleListener() {

		@Override
		public void committed(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			if (!transactionAttribute.isReadOnly()) {
				enable(Lifecycle.REQUEST);
			}
		}

		@Override
		public void created(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			if (!transactionAttribute.isReadOnly()) {
				disable(Lifecycle.REQUEST);
			}
		}

		@Override
		public void rollbacked(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus, Throwable throwable) {

			if (!transactionAttribute.isReadOnly()) {
				enable(Lifecycle.REQUEST);
			}
		}
	};

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

	public static void disable(Lifecycle lifecycle) {
		Map<Lifecycle, Boolean> threadLocalCacheDisabledFlags =
			_threadLocalCacheDisabledFlags.get();

		threadLocalCacheDisabledFlags.put(lifecycle, Boolean.TRUE);

		clearAll(lifecycle);
	}

	public static void enable(Lifecycle lifecycle) {
		Map<Lifecycle, Boolean> threadLocalCacheDisabledFlags =
			_threadLocalCacheDisabledFlags.get();

		threadLocalCacheDisabledFlags.remove(lifecycle);
	}

	public static <T> ThreadLocalCache<T> getThreadLocalCache(
		Lifecycle lifecycle, Serializable name) {

		Map<Lifecycle, Boolean> threadLocalCacheDisabledFlags =
			_threadLocalCacheDisabledFlags.get();

		if (threadLocalCacheDisabledFlags.get(lifecycle) == Boolean.TRUE) {
			return (ThreadLocalCache<T>)_emptyThreadLocalCache;
		}

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

	private static final EmptyThreadLocalCahce<?> _emptyThreadLocalCache =
		new EmptyThreadLocalCahce<Object>();
	private static final ThreadLocal
		<Map<Lifecycle, Boolean>>
			_threadLocalCacheDisabledFlags = new InitialThreadLocal
				<Map<Lifecycle, Boolean>>(
					ThreadLocalCacheManager.class +
						"._threadLocalCacheDisabledFlags",
					new EnumMap<Lifecycle, Boolean>(Lifecycle.class));
	private static final ThreadLocal
		<Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>>
			_threadLocalCacheMaps = new InitialThreadLocal
				<Map<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>>(
					ThreadLocalCacheManager.class + "._threadLocalCacheMaps",
					new EnumMap
						<Lifecycle, Map<Serializable, ThreadLocalCache<?>>>(
							Lifecycle.class));

	private static class EmptyThreadLocalCahce<T> extends ThreadLocalCache<T> {

		public EmptyThreadLocalCahce() {
			super(null, null);
		}

		@Override
		public T get(String key) {
			return null;
		}

		@Override
		public void put(String key, T obj) {
		}

		@Override
		public void remove(String key) {
		}

		@Override
		public void removeAll() {
		}

		@Override
		public String toString() {
			return EmptyThreadLocalCahce.class.getName();
		}

	}

}