/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderActionKeys;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemFileEntryModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<CommerceVirtualOrderItemFileEntry> {

	public CommerceVirtualOrderItemFileEntryModelResourcePermissionLogic(
		ModelResourcePermission<CommerceOrder> modelResourcePermission) {

		_commerceOrderModelResourcePermission = modelResourcePermission;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry,
			String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceVirtualOrderItemFileEntry.getCompanyId()) ||
			permissionChecker.isGroupAdmin(
				commerceVirtualOrderItemFileEntry.getGroupId())) {

			return true;
		}

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemFileEntry.getCommerceVirtualOrderItem();

		CommerceOrderItem commerceOrderItem =
			commerceVirtualOrderItem.getCommerceOrderItem();

		if (!_commerceOrderModelResourcePermission.contains(
				permissionChecker, commerceOrderItem.getCommerceOrderId(),
				ActionKeys.VIEW)) {

			return false;
		}

		if (actionId.equals(
				CommerceVirtualOrderActionKeys.
					DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM)) {

			return _containsDownloadPermission(
				commerceVirtualOrderItem, commerceVirtualOrderItemFileEntry);
		}

		return false;
	}

	private boolean _containsDownloadPermission(
		CommerceVirtualOrderItem commerceVirtualOrderItem,
		CommerceVirtualOrderItemFileEntry commerceVirtualOrderItemFileEntry) {

		if (!commerceVirtualOrderItem.isActive()) {
			return false;
		}

		Date date = new Date();

		if ((commerceVirtualOrderItem.getStartDate() != null) &&
			date.before(commerceVirtualOrderItem.getStartDate())) {

			return false;
		}

		if ((commerceVirtualOrderItem.getEndDate() != null) &&
			date.after(commerceVirtualOrderItem.getEndDate())) {

			return false;
		}

		if ((commerceVirtualOrderItem.getMaxUsages() > 0) &&
			(commerceVirtualOrderItemFileEntry.getUsages() >=
				commerceVirtualOrderItem.getMaxUsages())) {

			return false;
		}

		return true;
	}

	private final ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

}