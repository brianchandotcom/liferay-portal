/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mass.delete;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class MassDeleteCacheThreadLocal {

	public static <T> T getBulkDeleteCache(
		String ownerName, Supplier<T> supplier) {

		Map<String, Object> bulkDeleteCacheMap =
			_bulkDeleteCacheThreadLocal.get();

		if (bulkDeleteCacheMap == null) {
			return null;
		}

		return (T)bulkDeleteCacheMap.computeIfAbsent(
			ownerName, key -> supplier.get());
	}

	public static boolean isBulkDeleteMode() {
		Map<String, Object> bulkDeleteCacheMap =
			_bulkDeleteCacheThreadLocal.get();

		if (bulkDeleteCacheMap == null) {
			return false;
		}

		return true;
	}

	public static SafeCloseable openBulkDeleteMode() {
		return _bulkDeleteCacheThreadLocal.setWithSafeCloseable(
			new HashMap<>());
	}

	private static final CentralizedThreadLocal<Map<String, Object>>
		_bulkDeleteCacheThreadLocal = new CentralizedThreadLocal<>(
			MassDeleteCacheThreadLocal.class + "._bulkDeleteCacheThreadLocal");

}