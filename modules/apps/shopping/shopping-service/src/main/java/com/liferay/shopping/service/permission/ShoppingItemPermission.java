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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCategoryConstants;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.service.ShoppingItemLocalService;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {"model.class.name=com.liferay.shopping.model.ShoppingItem"},
	service = ShoppingItemPermission.class
)
public class ShoppingItemPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long itemId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, itemId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ShoppingItem.class.getName(), itemId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, ShoppingItem item,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, item, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ShoppingItem.class.getName(),
				item.getItemId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long itemId, String actionId)
		throws PortalException {

		ShoppingItem item = _shoppingItemLocalService.getItem(itemId);

		return contains(permissionChecker, item, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, ShoppingItem item,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			if (item.getCategoryId() ==
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				if (!ShoppingPermission.contains(
						permissionChecker, item.getGroupId(), actionId)) {

					return false;
				}
			}
			else {
				ShoppingCategory category = item.getCategory();

				if (!_shoppingCategoryPermission.contains(
						permissionChecker, category, actionId)) {

					return false;
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				item.getCompanyId(), ShoppingItem.class.getName(),
				item.getItemId(), item.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			item.getGroupId(), ShoppingItem.class.getName(), item.getItemId(),
			actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@BeanReference(type = ShoppingCategoryPermission.class)
	private static ShoppingCategoryPermission _shoppingCategoryPermission;

	@BeanReference(type = ShoppingItemLocalService.class)
	private static ShoppingItemLocalService _shoppingItemLocalService;

}