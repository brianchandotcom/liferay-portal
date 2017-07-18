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
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPSpecificationOption",
	service = BaseModelPermissionChecker.class
)
public class CPSpecificationOptionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpSpecificationOption, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPSpecificationOption.class.getName(),
				cpSpecificationOption.getCPSpecificationOptionId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long cpSpecificationOptionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpSpecificationOptionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPSpecificationOption.class.getName(),
				cpSpecificationOptionId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, cpSpecificationOption.getGroupId(),
			CPSpecificationOption.class.getName(),
			cpSpecificationOption.getCPSpecificationOptionId(),
			CPPortletKeys.CP_SPECIFICATION_OPTIONS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				cpSpecificationOption.getCompanyId(),
				CPSpecificationOption.class.getName(),
				cpSpecificationOption.getCPSpecificationOptionId(),
				cpSpecificationOption.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			cpSpecificationOption.getGroupId(),
			CPSpecificationOption.class.getName(),
			cpSpecificationOption.getCPSpecificationOptionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpSpecificationOptionId,
			String actionId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.getCPSpecificationOption(
				cpSpecificationOptionId);

		return contains(permissionChecker, cpSpecificationOption, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCPSpecificationOptionLocalService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	private static CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

}