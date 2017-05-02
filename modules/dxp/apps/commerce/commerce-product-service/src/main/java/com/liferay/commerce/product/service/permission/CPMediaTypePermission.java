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
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.CPMediaTypeLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPMediaType"},
	service = BaseModelPermissionChecker.class
)
public class CPMediaTypePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, CPMediaType cpMediaType,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpMediaType, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPMediaType.class.getName(),
				cpMediaType.getCPMediaTypeId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long cpMediaTypeId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpMediaTypeId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPMediaType.class.getName(), cpMediaTypeId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, CPMediaType cpMediaType,
			String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, cpMediaType.getGroupId(),
			CPMediaType.class.getName(), cpMediaType.getCPMediaTypeId(),
			CPPortletKeys.COMMERCE_PRODUCT_MEDIA_TYPES, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				cpMediaType.getCompanyId(), CPMediaType.class.getName(),
				cpMediaType.getCPMediaTypeId(), cpMediaType.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			cpMediaType.getGroupId(), CPMediaType.class.getName(),
			cpMediaType.getCPMediaTypeId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpMediaTypeId,
			String actionId)
		throws PortalException {

		CPMediaType cpMediaType = _cpMediaTypeLocalService.getCPMediaType(
			cpMediaTypeId);

		return contains(permissionChecker, cpMediaType, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCPOptionLocalService(
		CPMediaTypeLocalService cpOptionLocalService) {

		_cpMediaTypeLocalService = cpOptionLocalService;
	}

	private static CPMediaTypeLocalService _cpMediaTypeLocalService;

}