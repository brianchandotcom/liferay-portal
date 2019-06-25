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