/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry",
	service = ModelResourcePermission.class
)
public class CommerceTierPriceEntryModelResourcePermission
	implements ModelResourcePermission<CommerceTierPriceEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceTierPriceEntry commerceTierPriceEntry, String actionId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		commercePriceListPermission.check(
			permissionChecker, commercePriceEntry.getCommercePriceList(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceTierPriceEntryId,
			String actionId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		commercePriceListPermission.check(
			permissionChecker, commercePriceEntry.getCommercePriceList(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceTierPriceEntry commerceTierPriceEntry, String actionId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceEntry.getCommercePriceList(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceTierPriceEntryId,
			String actionId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceEntry.getCommercePriceList(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceTierPriceEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

	@Reference
	protected CommerceTierPriceEntryLocalService
		commerceTierPriceEntryLocalService;

}