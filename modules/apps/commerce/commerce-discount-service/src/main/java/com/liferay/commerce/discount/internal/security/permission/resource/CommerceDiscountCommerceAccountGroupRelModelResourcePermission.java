/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel",
	service = ModelResourcePermission.class
)
public class CommerceDiscountCommerceAccountGroupRelModelResourcePermission
	implements ModelResourcePermission
		<CommerceDiscountCommerceAccountGroupRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel,
			String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceDiscountCommerceAccountGroupRelId, String actionId)
		throws PortalException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				commerceDiscountCommerceAccountGroupRelLocalService.
					getCommerceDiscountCommerceAccountGroupRel(
						commerceDiscountCommerceAccountGroupRelId);

		commerceDiscountPermission.check(
			permissionChecker,
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel,
			String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceDiscountCommerceAccountGroupRelId, String actionId)
		throws PortalException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				commerceDiscountCommerceAccountGroupRelLocalService.
					getCommerceDiscountCommerceAccountGroupRel(
						commerceDiscountCommerceAccountGroupRelId);

		return commerceDiscountPermission.contains(
			permissionChecker,
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscountCommerceAccountGroupRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceDiscountCommerceAccountGroupRelLocalService
		commerceDiscountCommerceAccountGroupRelLocalService;

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

}