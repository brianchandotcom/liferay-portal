/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel",
	service = ModelResourcePermission.class
)
public class CommercePriceListOrderTypeRelModelResourcePermission
	implements ModelResourcePermission<CommercePriceListOrderTypeRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListOrderTypeRel.getCommercePriceListId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceListOrderTypeRelId, String actionId)
		throws PortalException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelLocalService.
				getCommercePriceListOrderTypeRel(
					commercePriceListOrderTypeRelId);

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListOrderTypeRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListOrderTypeRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceListOrderTypeRelId, String actionId)
		throws PortalException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelLocalService.
				getCommercePriceListOrderTypeRel(
					commercePriceListOrderTypeRelId);

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListOrderTypeRel.getCommercePriceListId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceListOrderTypeRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListOrderTypeRelLocalService
		commercePriceListOrderTypeRelLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}