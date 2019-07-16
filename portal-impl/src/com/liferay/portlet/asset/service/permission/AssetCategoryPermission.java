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

package com.liferay.portlet.asset.service.permission;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;

/**
 * @author Eduardo Lundgren
 */
public class AssetCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, AssetCategory category,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetCategory.class.getName(),
				category.getCategoryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetCategory.class.getName(), categoryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetCategory.class.getName(), categoryId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, AssetCategory category,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.VIEW) &&
			!AssetVocabularyPermission.contains(
				permissionChecker, category.getVocabularyId(),
				ActionKeys.VIEW)) {

			return false;
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			while (true) {
				if (!_hasPermission(permissionChecker, category, actionId)) {
					return false;
				}

				long parentCategoryId = category.getParentCategoryId();

				if (parentCategoryId ==
						AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

					break;
				}

				category = AssetCategoryLocalServiceUtil.getCategory(
					parentCategoryId);
			}

			return AssetVocabularyPermission.contains(
				permissionChecker, category.getVocabularyId(), actionId);
		}

		return _hasPermission(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (categoryId == AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return AssetCategoriesPermission.contains(
				permissionChecker, groupId, actionId);
		}

		return contains(permissionChecker, categoryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			AssetCategoryLocalServiceUtil.getCategory(categoryId), actionId);
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, AssetCategory category,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), AssetCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				category.getGroupId(), AssetCategory.class.getName(),
				category.getCategoryId(), actionId)) {

			return true;
		}

		return false;
	}

}