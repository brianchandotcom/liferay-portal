/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.security.permission;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBCategory",
	service = PermissionUpdateHandler.class
)
public class MBCategoryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		MBCategory mbCategory = _mbCategoryLocalService.fetchMBCategory(
			GetterUtil.getLong(primKey));

		if (mbCategory == null) {
			return;
		}

		mbCategory.setModifiedDate(new Date());

		_mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

}