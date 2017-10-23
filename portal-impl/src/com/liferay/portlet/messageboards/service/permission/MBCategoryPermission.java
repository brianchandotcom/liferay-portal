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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.service.MBCategoryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class MBCategoryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, MBCategory.class.getName(),
				categoryId, actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBCategory.class.getName(), categoryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, category.getGroupId(),
				MBCategory.class.getName(), categoryId, actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBCategory.class.getName(), categoryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, category.getGroupId(),
				MBCategory.class.getName(), category.getCategoryId(), actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBCategory.class.getName(),
				category.getCategoryId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, MBCategory.class.getName(),
				categoryId, actionId);

		if ((containsBaseModelPermission != null) &&
			containsBaseModelPermission) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, category.getGroupId(),
				MBCategory.class.getName(), categoryId, actionId);

		if ((containsBaseModelPermission != null) &&
			containsBaseModelPermission) {

			return true;
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, category.getGroupId(),
				MBCategory.class.getName(), category.getCategoryId(), actionId);

		if ((containsBaseModelPermission != null) &&
			containsBaseModelPermission) {

			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.message.boards.internal.service.permission.MBCategoryPermission#checkBaseModel(PermissionChecker, long, long, String)}
	 */
	@Deprecated
	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		Boolean containsBaseModelPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, MBCategory.class.getName(),
				primaryKey, actionId);

		if ((containsBaseModelPermission == null) ||
			!containsBaseModelPermission) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBCategory.class.getName(), primaryKey,
				actionId);
		}
	}

}