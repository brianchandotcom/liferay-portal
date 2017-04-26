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
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.CommerceProductOptionLocalService;
import com.liferay.commerce.product.service.CommerceProductOptionValueLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CommerceProductOption"},
	service = BaseModelPermissionChecker.class
)
public class CommerceProductOptionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			CommerceProductOption commerceProductOption, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceProductOption, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceProductOption.class.getName(),
				commerceProductOption.getCommerceProductOptionId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long commerceProductOptionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceProductOptionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceProductOption.class.getName(),
				commerceProductOptionId, actionId);
		}
	}

	public static void checkCommerceProductOptionValue(
			PermissionChecker permissionChecker,
			CommerceProductOptionValue commerceProductOptionValue,
			String actionId)
		throws PortalException {

		long commerceProductOptionId =
			commerceProductOptionValue.getCommerceProductOptionId();

		check(permissionChecker, commerceProductOptionId, actionId);
	}

	public static void checkCommerceProductOptionValue(
			PermissionChecker permissionChecker,
			long commerceProductOptionValueId, String actionId)
		throws PortalException {

		CommerceProductOptionValue commerceProductOptionValue =
			_commerceProductOptionValueLocalService.
				getCommerceProductOptionValue(commerceProductOptionValueId);

		checkCommerceProductOptionValue(
			permissionChecker, commerceProductOptionValue, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			CommerceProductOption commerceProductOption, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, commerceProductOption.getGroupId(),
			CommerceProductOption.class.getName(),
			commerceProductOption.getCommerceProductOptionId(),
			CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				commerceProductOption.getCompanyId(),
				CommerceProductOption.class.getName(),
				commerceProductOption.getCommerceProductOptionId(),
				commerceProductOption.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commerceProductOption.getGroupId(),
			CommerceProductOption.class.getName(),
			commerceProductOption.getCommerceProductOptionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long commerceProductOptionId,
			String actionId)
		throws PortalException {

		CommerceProductOption commerceProductOption =
			_commerceProductOptionLocalService.getCommerceProductOption(
				commerceProductOptionId);

		return contains(permissionChecker, commerceProductOption, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCommerceProductOptionLocalService(
		CommerceProductOptionLocalService commerceProductOptionLocalService) {

		_commerceProductOptionLocalService = commerceProductOptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setCommerceProductOptionValueLocalService(
		CommerceProductOptionValueLocalService
			commerceProductOptionValueLocalService) {

		_commerceProductOptionValueLocalService =
			commerceProductOptionValueLocalService;
	}

	private static CommerceProductOptionLocalService
		_commerceProductOptionLocalService;
	private static CommerceProductOptionValueLocalService
		_commerceProductOptionValueLocalService;

}