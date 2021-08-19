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
	enabled = false, immediate = true,
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