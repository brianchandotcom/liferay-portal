/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.security.permission;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Sergio González
 */
public class MBCategoryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MBCategory> modelResourcePermission =
			_categoryModelResourcePermissionSnapshot.get();

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			return portletResourcePermission.contains(
				permissionChecker, groupId, actionId);
		}

		return modelResourcePermission.contains(
			permissionChecker, categoryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MBCategory> modelResourcePermission =
			_categoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, categoryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException {

		ModelResourcePermission<MBCategory> modelResourcePermission =
			_categoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, category, actionId);
	}

	private static final Snapshot<ModelResourcePermission<MBCategory>>
		_categoryModelResourcePermissionSnapshot = new Snapshot<>(
			MBCategoryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.message.boards.model.MBCategory)");

}