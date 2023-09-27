/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.Inject;

/**
 * @author Samuel Trong Tran
 */
public abstract class BaseHasAssetEntryUADAnonymizerTestCase
	<T extends BaseModel>
		extends BaseUADAnonymizerTestCase<T> {

	protected boolean isAssetEntryAutoAnonymized(
			String className, long classPK, User user)
		throws Exception {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		String userName = assetEntry.getUserName();

		if ((assetEntry.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Inject
	protected AssetEntryLocalService assetEntryLocalService;

}