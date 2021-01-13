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

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscountRel",
	service = ModelResourcePermission.class
)
public class CommerceDiscountRelModelResourcePermission
	implements ModelResourcePermission<CommerceDiscountRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscountRel commerceDiscountRel, String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscountRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceDiscountRelId,
			String actionId)
		throws PortalException {

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelLocalService.getCommerceDiscountRel(
				commerceDiscountRelId);

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscountRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscountRel commerceDiscountRel, String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscountRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceDiscountRelId,
			String actionId)
		throws PortalException {

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelLocalService.getCommerceDiscountRel(
				commerceDiscountRelId);

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscountRel.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscountRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

	@Reference
	protected CommerceDiscountRelLocalService commerceDiscountRelLocalService;

}