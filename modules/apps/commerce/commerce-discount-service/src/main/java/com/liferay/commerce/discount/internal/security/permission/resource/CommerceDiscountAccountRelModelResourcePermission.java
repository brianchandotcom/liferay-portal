/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscountAccountRel",
	service = ModelResourcePermission.class
)
public class CommerceDiscountAccountRelModelResourcePermission
	implements ModelResourcePermission<CommerceDiscountAccountRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscountAccountRel commerceDiscountAccountRel,
			String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountAccountRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceDiscountAccountRelId, String actionId)
		throws PortalException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			commerceDiscountAccountRelLocalService.
				getCommerceDiscountAccountRel(commerceDiscountAccountRelId);

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountAccountRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscountAccountRel commerceDiscountAccountRel,
			String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountAccountRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceDiscountAccountRelId, String actionId)
		throws PortalException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			commerceDiscountAccountRelLocalService.
				getCommerceDiscountAccountRel(commerceDiscountAccountRelId);

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountAccountRel.getCommerceDiscountId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscountAccountRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceDiscountAccountRelLocalService
		commerceDiscountAccountRelLocalService;

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

}