/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class DeletedAssetObjectThreadLocal {

	public static boolean isDeletedAssetObject(String className, long classPK) {
		Map.Entry<String, Long> entry = _assetObjectThreadLocal.get();

		if (entry == null) {
			return false;
		}

		if (Objects.equals(entry.getKey(), className) &&
			(entry.getValue() == classPK)) {

			return true;
		}

		return false;
	}

	public static SafeCloseable setWithSafeCloseable(
		String className, long classPK) {

		return _assetObjectThreadLocal.setWithSafeCloseable(
			new AbstractMap.SimpleImmutableEntry<>(className, classPK));
	}

	private static final CentralizedThreadLocal<Map.Entry<String, Long>>
		_assetObjectThreadLocal = new CentralizedThreadLocal<>(
			DeletedAssetObjectThreadLocal.class + "._assetObjectThreadLocal");

}