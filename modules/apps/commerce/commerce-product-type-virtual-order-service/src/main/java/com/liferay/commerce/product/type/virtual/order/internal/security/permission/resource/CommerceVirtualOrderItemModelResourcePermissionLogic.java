/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderActionKeys;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<CommerceVirtualOrderItem> {

	public CommerceVirtualOrderItemModelResourcePermissionLogic(
		ModelResourcePermission<CommerceOrder> modelResourcePermission) {

		_commerceOrderModelResourcePermission = modelResourcePermission;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CommerceVirtualOrderItem commerceVirtualOrderItem, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceVirtualOrderItem.getCompanyId()) ||
			permissionChecker.isGroupAdmin(
				commerceVirtualOrderItem.getGroupId())) {

			return true;
		}

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

			return _containsDownloadPermission(commerceVirtualOrderItem);
		}

		return false;
	}

	private boolean _containsDownloadPermission(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

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
			(commerceVirtualOrderItem.getUsages() >=
				commerceVirtualOrderItem.getMaxUsages())) {

			return false;
		}

		return true;
	}

	private final ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

}