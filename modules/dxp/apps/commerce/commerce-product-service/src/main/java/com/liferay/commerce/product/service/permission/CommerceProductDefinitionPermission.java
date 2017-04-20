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

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {"model.class.name=com.liferay.commerce.product.model.CommerceProductDefinition"},
	service = BaseModelPermissionChecker.class
)
public class CommerceProductDefinitionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			CommerceProductDefinition commerceProductDefinition,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceProductDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceProductDefinition.class.getName(),
				commerceProductDefinition.getCommerceProductDefinitionId(),
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			long commerceProductDefinitionId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceProductDefinitionId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceProductDefinition.class.getName(),
				commerceProductDefinitionId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			CommerceProductDefinition commerceProductDefinition,
			String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, commerceProductDefinition.getGroupId(),
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId(),
			CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				commerceProductDefinition.getCompanyId(),
				CommerceProductDefinition.class.getName(),
				commerceProductDefinition.getCommerceProductDefinitionId(),
				commerceProductDefinition.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commerceProductDefinition.getGroupId(),
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			long commerceProductDefinitionId, String actionId)
		throws PortalException {

		CommerceProductDefinition commerceProductDefinition =
			_commerceProductDefinitionLocalService.getCommerceProductDefinition(
				commerceProductDefinitionId);

		return contains(permissionChecker, commerceProductDefinition, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference
	private static CommerceProductDefinitionLocalService
		_commerceProductDefinitionLocalService;

}