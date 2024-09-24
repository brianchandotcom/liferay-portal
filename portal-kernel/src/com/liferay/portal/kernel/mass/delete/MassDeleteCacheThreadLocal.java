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

	public static <T> T getMassDeleteCache(
		String ownerName, Supplier<T> supplier) {

		Map<String, Object> massDeleteCacheMap =
			_massDeleteCacheThreadLocal.get();

		if (massDeleteCacheMap == null) {
			return null;
		}

		return (T)massDeleteCacheMap.computeIfAbsent(
			ownerName, key -> supplier.get());
	}

	public static boolean isMassDeleteMode() {
		Map<String, Object> massDeleteCacheMap =
			_massDeleteCacheThreadLocal.get();

		if (massDeleteCacheMap == null) {
			return false;
		}

		return true;
	}

	public static SafeCloseable openMassDeleteMode() {
		return _massDeleteCacheThreadLocal.setWithSafeCloseable(
			new HashMap<>());
	}

	private static final CentralizedThreadLocal<Map<String, Object>>
		_massDeleteCacheThreadLocal = new CentralizedThreadLocal<>(
			MassDeleteCacheThreadLocal.class + "._massDeleteCacheThreadLocal");

}