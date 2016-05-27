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

package com.liferay.shopping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCategoryConstants;
import com.liferay.shopping.service.ShoppingCategoryLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.shopping.model.ShoppingCategory"},
	service = ShoppingCategoryPermission.class
)
public class ShoppingCategoryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ShoppingCategory.class.getName(), categoryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, ShoppingCategory category,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ShoppingCategory.class.getName(),
				category.getCategoryId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (categoryId ==
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return ShoppingPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			ShoppingCategory category =
				ShoppingCategoryLocalServiceUtil.getCategory(categoryId);

			return contains(permissionChecker, category, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, ShoppingCategory category,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long categoryId = category.getCategoryId();

			while (categoryId !=
						ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				category = ShoppingCategoryLocalServiceUtil.getCategory(
					categoryId);

				if (!_hasPermission(permissionChecker, category, actionId)) {
					return false;
				}

				categoryId = category.getParentCategoryId();
			}

			return ShoppingPermission.contains(
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
		PermissionChecker permissionChecker, ShoppingCategory category,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), ShoppingCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				category.getGroupId(), ShoppingCategory.class.getName(),
				category.getCategoryId(), actionId)) {

			return true;
		}

		return false;
	}

}