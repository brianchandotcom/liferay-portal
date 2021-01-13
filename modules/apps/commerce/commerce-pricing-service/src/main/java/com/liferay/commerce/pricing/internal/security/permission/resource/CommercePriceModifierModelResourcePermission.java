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

package com.liferay.commerce.pricing.internal.security.permission.resource;

import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
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
	property = "model.class.name=com.liferay.commerce.pricing.model.CommercePriceModifier",
	service = ModelResourcePermission.class
)
public class CommercePriceModifierModelResourcePermission
	implements ModelResourcePermission<CommercePriceModifier> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceModifier commercePriceModifier, String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceModifierId,
			String actionId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.getCommercePriceModifier(
				commercePriceModifierId);

		commercePriceListPermission.check(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceModifier commercePriceModifier, String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceModifierId,
			String actionId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.getCommercePriceModifier(
				commercePriceModifierId);

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceModifier.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

	@Reference
	protected CommercePriceModifierLocalService
		commercePriceModifierLocalService;

}