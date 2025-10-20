/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class ReindexCacheThreadLocal {

	public static final int SIZE_LIMIT = GetterUtil.getInteger(
		PropsUtil.get("reindex.cache.size.limit"), 1000000);

	public static <T> T getReindexCache(
		String ownerName, Supplier<T> supplier) {

		Map<String, Object> reindexCacheMap = _reindexCacheMap.get();

		if (reindexCacheMap == null) {
			return null;
		}

		T t = (T)reindexCacheMap.computeIfAbsent(
			ownerName,
			key -> {
				Object result = supplier.get();

				if (result == null) {
					return _NULL_HOLDER;
				}

				return result;
			});

		if (t == _NULL_HOLDER) {
			return null;
		}

		return t;
	}

	public static SafeCloseable openReindexMode() {
		return _reindexCacheMap.setWithSafeCloseable(new HashMap<>());
	}

	public static SafeCloseable openReindexMode(
		Map<String, Object> sharedReindexCacheMap) {

		return _reindexCacheMap.setWithSafeCloseable(sharedReindexCacheMap);
	}

	private static final Object _NULL_HOLDER = new Object();

	private static final CentralizedThreadLocal<Map<String, Object>>
		_reindexCacheMap = new CentralizedThreadLocal<>(
			ReindexCacheThreadLocal.class + "._reindexCacheMap");

}