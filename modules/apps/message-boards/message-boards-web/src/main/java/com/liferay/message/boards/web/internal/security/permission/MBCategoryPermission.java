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