/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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