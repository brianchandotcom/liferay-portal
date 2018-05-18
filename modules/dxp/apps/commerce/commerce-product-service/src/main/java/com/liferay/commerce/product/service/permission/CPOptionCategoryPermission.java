/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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