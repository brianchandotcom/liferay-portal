/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCategoryConstants;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, ShoppingCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		long categoryId = category.getCategoryId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (categoryId !=
						ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				category = ShoppingCategoryLocalServiceUtil.getCategory(
					categoryId);

				categoryId = category.getParentCategoryId();

				if (!permissionChecker.hasOwnerPermission(
						category.getCompanyId(),
						ShoppingCategory.class.getName(),
						category.getCategoryId(), category.getUserId(),
						actionId) &&
					!permissionChecker.hasPermission(
						category.getGroupId(), ShoppingCategory.class.getName(),
						category.getCategoryId(), actionId)) {

					return false;
				}

				if (!PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					break;
				}
			}

			return true;
		}

		while (categoryId !=
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			category = ShoppingCategoryLocalServiceUtil.getCategory(categoryId);

			categoryId = category.getParentCategoryId();

			if (permissionChecker.hasOwnerPermission(
					category.getCompanyId(), ShoppingCategory.class.getName(),
					category.getCategoryId(), category.getUserId(), actionId)) {

				return true;
			}

			if (permissionChecker.hasPermission(
					category.getGroupId(), ShoppingCategory.class.getName(),
					category.getCategoryId(), actionId)) {

				return true;
			}

			if (actionId.equals(ActionKeys.VIEW)) {
				break;
			}
		}

		return false;
	}

}