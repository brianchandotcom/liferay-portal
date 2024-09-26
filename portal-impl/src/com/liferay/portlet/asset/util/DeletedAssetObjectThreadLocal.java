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

	public static boolean isDeletedAssetObject(long classNameId, long classPK) {
		Map.Entry<Long, Long> entry = _assetObjectThreadLocal.get();

		if (entry == null) {
			return false;
		}

		if (Objects.equals(entry.getKey(), classNameId) &&
			(entry.getValue() == classPK)) {

			return true;
		}

		return false;
	}

	public static SafeCloseable setAssetObjectWithSafeCloseable(
		long classNameId, long classPK) {

		return _assetObjectThreadLocal.setWithSafeCloseable(
			new AbstractMap.SimpleImmutableEntry<>(classNameId, classPK));
	}

	private static final CentralizedThreadLocal<Map.Entry<Long, Long>>
		_assetObjectThreadLocal = new CentralizedThreadLocal<>(
			DeletedAssetObjectThreadLocal.class + "._assetObjectThreadLocal");

}