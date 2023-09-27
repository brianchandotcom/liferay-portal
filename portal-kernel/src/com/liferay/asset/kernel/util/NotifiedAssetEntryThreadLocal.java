/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Jonathan McCann
 */
public class NotifiedAssetEntryThreadLocal {

	public static boolean isNotifiedAssetEntryIdsModified() {
		return _isNotifiedAssetEntryIdsModified.get();
	}

	public static void setNotifiedAssetEntryIdsModified(
		boolean notifiedAssetEntryIdsModified) {

		_isNotifiedAssetEntryIdsModified.set(notifiedAssetEntryIdsModified);
	}

	private static final ThreadLocal<Boolean> _isNotifiedAssetEntryIdsModified =
		new CentralizedThreadLocal<>(
			NotifiedAssetEntryThreadLocal.class +
				"._isNotifiedAssetEntryIdsModified",
			() -> Boolean.FALSE);

}