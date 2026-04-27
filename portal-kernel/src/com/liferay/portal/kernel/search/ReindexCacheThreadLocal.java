/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class ReindexCacheThreadLocal {

	public static <T> T getGlobalReindexCache(
		Supplier<Integer> countSupplier, String ownerName,
		IntFunction<T> reindexCacheFunction) {

		Map<String, Object> reindexCacheMap = _reindexCacheMap.get();

		if (reindexCacheMap == null) {
			return null;
		}

		T t = (T)reindexCacheMap.get(ownerName);

		// Waste one get to avoid potential "recursive update" error

		if (t == null) {
			t = (T)reindexCacheMap.computeIfAbsent(
				ownerName,
				key -> {
					int count = countSupplier.get();

					if (count > _SIZE_LIMIT) {
						return _NULL_HOLDER;
					}

					return reindexCacheFunction.apply(count);
				});
		}

		if (t == _NULL_HOLDER) {
			return null;
		}

		return t;
	}

	public static <T> T getScopeReindexCache(
		String ownerName, String scopeName, Supplier<Integer> countSupplier,
		Supplier<Integer> scopeCountSupplier,
		IntFunction<T> reindexCacheFunction) {

		Map<String, Object> reindexCacheMap = _reindexCacheMap.get();

		if (reindexCacheMap == null) {
			return null;
		}

		String cacheKey = ownerName + "#" + scopeName;

		T t = (T)reindexCacheMap.get(cacheKey);

		// Waste one get to avoid potential "recursive update" error

		if (t == null) {

			// Check global count with cache to avoid per scope repeated
			// checking

			int globalCount = (int)reindexCacheMap.computeIfAbsent(
				ownerName + "#globalCount", key -> countSupplier.get());

			t = (T)reindexCacheMap.computeIfAbsent(
				cacheKey,
				key -> {
					int count = globalCount;

					if (count > _SIZE_LIMIT) {

						// If global count is over size limit, give scope count
						// a second chance. This is assuming that not every
						// scope will be used.

						count = scopeCountSupplier.get();

						if (count > _SIZE_LIMIT) {
							return _NULL_HOLDER;
						}
					}

					return reindexCacheFunction.apply(count);
				});
		}

		if (t == _NULL_HOLDER) {
			return null;
		}

		return t;
	}

	public static boolean isFullMode() {
		return _fullMode.get();
	}

	public static SafeCloseable openReindexMode() {
		return _reindexCacheMap.setWithSafeCloseable(new ConcurrentHashMap<>());
	}

	public static SafeCloseable openReindexMode(
		boolean fullMode, Map<String, Object> sharedReindexCacheMap) {

		SafeCloseable safeCloseable1 = _fullMode.setWithSafeCloseable(fullMode);

		SafeCloseable safeCloseable2 = _reindexCacheMap.setWithSafeCloseable(
			sharedReindexCacheMap);

		return () -> {
			safeCloseable2.close();

			safeCloseable1.close();
		};
	}

	public static <T> Callable<T> wrapCallable(Callable<T> callable) {
		boolean fullMode = _fullMode.get();
		Map<String, Object> reindexCacheMap = _reindexCacheMap.get();

		return () -> {
			try (SafeCloseable safeCloseable = openReindexMode(
					fullMode, reindexCacheMap)) {

				return callable.call();
			}
		};
	}

	private static final Object _NULL_HOLDER = new Object();

	private static final int _SIZE_LIMIT = GetterUtil.getInteger(
		PropsUtil.get("reindex.cache.size.limit"), 1000000);

	private static final CentralizedThreadLocal<Boolean> _fullMode =
		new CentralizedThreadLocal<>(
			ReindexCacheThreadLocal.class + "._fullMode", () -> Boolean.FALSE);
	private static final CentralizedThreadLocal<Map<String, Object>>
		_reindexCacheMap = new CentralizedThreadLocal<>(
			ReindexCacheThreadLocal.class + "._reindexCacheMap");

}