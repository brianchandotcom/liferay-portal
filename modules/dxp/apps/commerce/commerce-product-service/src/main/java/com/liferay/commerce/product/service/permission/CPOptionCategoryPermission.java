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

package com.liferay.commerce.product.service.permission;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPOptionCategory",
	service = BaseModelPermissionChecker.class
)
public class CPOptionCategoryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionCategory, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOptionCategory.class.getName(),
				cpOptionCategory.getCPOptionCategoryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionCategoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOptionCategory.class.getName(),
				cpOptionCategoryId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, cpOptionCategory.getGroupId(),
			CPOptionCategory.class.getName(),
			cpOptionCategory.getCPOptionCategoryId(),
			CPPortletKeys.CP_OPTION_CATEGORIES, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				cpOptionCategory.getCompanyId(),
				CPOptionCategory.class.getName(),
				cpOptionCategory.getCPOptionCategoryId(),
				cpOptionCategory.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			cpOptionCategory.getGroupId(), CPOptionCategory.class.getName(),
			cpOptionCategory.getCPOptionCategoryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.getCPOptionCategory(
				cpOptionCategoryId);

		return contains(permissionChecker, cpOptionCategory, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCPOptionCategoryLocalService(
		CPOptionCategoryLocalService cpOptionCategoryLocalService) {

		_cpOptionCategoryLocalService = cpOptionCategoryLocalService;
	}

	private static CPOptionCategoryLocalService _cpOptionCategoryLocalService;

}