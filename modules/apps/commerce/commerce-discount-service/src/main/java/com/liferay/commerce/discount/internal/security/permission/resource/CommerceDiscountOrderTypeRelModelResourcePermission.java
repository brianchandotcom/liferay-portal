/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel",
	service = ModelResourcePermission.class
)
public class CommerceDiscountOrderTypeRelModelResourcePermission
	implements ModelResourcePermission<CommerceDiscountOrderTypeRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel,
			String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountOrderTypeRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceDiscountOrderTypeRelId, String actionId)
		throws PortalException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelLocalService.
				getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountOrderTypeRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel,
			String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountOrderTypeRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceDiscountOrderTypeRelId, String actionId)
		throws PortalException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelLocalService.
				getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountOrderTypeRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscountOrderTypeRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceDiscountOrderTypeRelLocalService
		commerceDiscountOrderTypeRelLocalService;

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

}