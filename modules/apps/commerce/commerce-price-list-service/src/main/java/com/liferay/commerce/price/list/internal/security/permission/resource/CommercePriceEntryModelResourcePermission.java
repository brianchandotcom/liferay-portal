/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceEntry",
	service = ModelResourcePermission.class
)
public class CommercePriceEntryModelResourcePermission
	implements ModelResourcePermission<CommercePriceEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceEntry commercePriceEntry, String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker, commercePriceEntry.getCommercePriceListId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceEntryId,
			String actionId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		commercePriceListPermission.check(
			permissionChecker, commercePriceEntry.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceEntry commercePriceEntry, String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceEntry.getCommercePriceListId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceEntryId,
			String actionId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		return commercePriceListPermission.contains(
			permissionChecker, commercePriceEntry.getCommercePriceListId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceEntryLocalService commercePriceEntryLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}