/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class DeletedAssetEntryThreadLocal {

	public static boolean isDeletedAssetEntry(long classNameId, long classPK) {
		AssetEntry assetEntry = _assetEntryThreadLocal.get();

		if (assetEntry == null) {
			return false;
		}

		if ((assetEntry.getClassNameId() == classNameId) &&
			(assetEntry.getClassPK() == classPK)) {

			return true;
		}

		return false;
	}

	public static SafeCloseable setAssetEntryWithSafeCloseable(
		AssetEntry assetEntry) {

		return _assetEntryThreadLocal.setWithSafeCloseable(assetEntry);
	}

	private static final CentralizedThreadLocal<AssetEntry>
		_assetEntryThreadLocal = new CentralizedThreadLocal<>(
			DeletedAssetEntryThreadLocal.class + "._assetEntryThreadLocal");

}