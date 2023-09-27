/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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