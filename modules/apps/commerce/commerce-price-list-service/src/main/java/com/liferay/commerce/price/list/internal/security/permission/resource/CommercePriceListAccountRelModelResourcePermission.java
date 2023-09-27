/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceListAccountRel",
	service = ModelResourcePermission.class
)
public class CommercePriceListAccountRelModelResourcePermission
	implements ModelResourcePermission<CommercePriceListAccountRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceListAccountRel commercePriceListAccountRel,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListAccountRel.getCommercePriceListId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceListAccountRelId, String actionId)
		throws PortalException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRel(commercePriceListAccountRelId);

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListAccountRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceListAccountRel commercePriceListAccountRel,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListAccountRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceListAccountRelId, String actionId)
		throws PortalException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRel(commercePriceListAccountRelId);

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListAccountRel.getCommercePriceListId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceListAccountRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListAccountRelLocalService
		commercePriceListAccountRelLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}