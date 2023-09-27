/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel",
	service = ModelResourcePermission.class
)
public class CommercePriceListAccountGroupRelModelResourcePermission
	implements ModelResourcePermission
		<CommercePriceListCommerceAccountGroupRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListCommerceAccountGroupRel.getCommercePriceListId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceListCommerceAccountGroupRelId, String actionId)
		throws PortalException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				commercePriceListCommerceAccountGroupRelLocalService.
					getCommercePriceListCommerceAccountGroupRel(
						commercePriceListCommerceAccountGroupRelId);

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListCommerceAccountGroupRel.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListCommerceAccountGroupRel.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceListCommerceAccountGroupRelId, String actionId)
		throws PortalException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				commercePriceListCommerceAccountGroupRelLocalService.
					getCommercePriceListCommerceAccountGroupRel(
						commercePriceListCommerceAccountGroupRelId);

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListCommerceAccountGroupRel.getCommercePriceListId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceListCommerceAccountGroupRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListCommerceAccountGroupRelLocalService
		commercePriceListCommerceAccountGroupRelLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}