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

		Date now = new Date();

		if ((commerceVirtualOrderItem.getStartDate() != null) &&
			now.before(commerceVirtualOrderItem.getStartDate())) {

			return false;
		}

		if ((commerceVirtualOrderItem.getEndDate() != null) &&
			now.after(commerceVirtualOrderItem.getEndDate())) {

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