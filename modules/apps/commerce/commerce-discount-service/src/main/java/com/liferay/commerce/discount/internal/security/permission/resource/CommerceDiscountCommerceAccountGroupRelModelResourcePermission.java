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
	enabled = false, immediate = true,
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