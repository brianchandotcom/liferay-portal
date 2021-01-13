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
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.pricing.model.CommercePriceModifierRel",
	service = ModelResourcePermission.class
)
public class CommercePriceModifierRelModelResourcePermission
	implements ModelResourcePermission<CommercePriceModifierRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceModifierRel commercePriceModifierRel, String actionId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		commercePriceListPermission.check(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceModifierRelId, String actionId)
		throws PortalException {

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRelLocalService.getCommercePriceModifierRel(
				commercePriceModifierRelId);

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		commercePriceListPermission.check(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceModifierRel commercePriceModifierRel, String actionId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceModifierRelId, String actionId)
		throws PortalException {

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRelLocalService.getCommercePriceModifierRel(
				commercePriceModifierRelId);

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceModifier.getCommercePriceListId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceModifierRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

	@Reference
	protected CommercePriceModifierRelLocalService
		commercePriceModifierRelLocalService;

}