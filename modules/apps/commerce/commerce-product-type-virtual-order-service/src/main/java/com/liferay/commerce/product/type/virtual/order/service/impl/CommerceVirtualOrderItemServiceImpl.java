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

package com.liferay.commerce.product.type.virtual.order.service.impl;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderActionKeys;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.io.File;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceVirtualOrderItem"
	},
	service = AopService.class
)
public class CommerceVirtualOrderItemServiceImpl
	extends CommerceVirtualOrderItemServiceBaseImpl {

	@Override
	public File getFile(long commerceVirtualOrderItemId) throws Exception {
		PermissionChecker permissionChecker = getPermissionChecker();

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItem(
				commerceVirtualOrderItemId);

		_commerceVirtualOrderItemModelResourcePermission.check(
			permissionChecker, commerceVirtualOrderItemId,
			CommerceVirtualOrderActionKeys.
				DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM);

		File file = commerceVirtualOrderItemLocalService.getFile(
			commerceVirtualOrderItemId);

		if (!permissionChecker.isCompanyAdmin() ||
			!permissionChecker.isGroupAdmin(
				commerceVirtualOrderItem.getGroupId())) {

			commerceVirtualOrderItemLocalService.
				incrementCommerceVirtualOrderItemUsages(
					commerceVirtualOrderItemId);
		}

		return file;
	}

	@Override
	public CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			boolean active)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItem(
				commerceVirtualOrderItemId);

		CommerceOrderItem commerceOrderItem =
			commerceVirtualOrderItem.getCommerceOrderItem();

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderItem.getCommerceOrderId(),
			ActionKeys.UPDATE);

		return commerceVirtualOrderItemLocalService.
			updateCommerceVirtualOrderItem(
				commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
				duration, usages, maxUsages, active);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem)"
	)
	private ModelResourcePermission<CommerceVirtualOrderItem>
		_commerceVirtualOrderItemModelResourcePermission;

}