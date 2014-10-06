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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portlet.messageboards.model.MBCategory"
	}
)
public class MBCategoryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (MBBanLocalServiceUtil.hasBan(
				groupId, permissionChecker.getUserId())) {

			return false;
		}

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return MBPermission.contains(permissionChecker, groupId, actionId);
		}

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		return contains(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		return contains(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException {

		if (MBBanLocalServiceUtil.hasBan(
				category.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, category.getGroupId(),
			MBCategory.class.getName(), category.getCategoryId(),
			PortletKeys.MESSAGE_BOARDS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			try {
				long categoryId = category.getCategoryId();

				while (categoryId !=
							MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

					category = MBCategoryLocalServiceUtil.getCategory(
						categoryId);

					if (!_hasPermission(
							permissionChecker, category, actionId)) {

						return false;
					}

					categoryId = category.getParentCategoryId();
				}
			}
			catch (NoSuchCategoryException nsce) {
				if (!category.isInTrash()) {
					throw nsce;
				}
			}

			return MBPermission.contains(
				permissionChecker, category.getGroupId(), actionId);
		}

		return _hasPermission(permissionChecker, category, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, groupId, primaryKey, actionId);
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, MBCategory category,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), MBCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				category.getGroupId(), MBCategory.class.getName(),
				category.getCategoryId(), actionId)) {

			return true;
		}

		return false;
	}

}