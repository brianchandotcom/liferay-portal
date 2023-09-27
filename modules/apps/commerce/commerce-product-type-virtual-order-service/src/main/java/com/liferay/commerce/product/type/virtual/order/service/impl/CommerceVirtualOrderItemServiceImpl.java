/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceVirtualOrderItem"
	},
	service = AopService.class
)
public class CommerceVirtualOrderItemServiceImpl
	extends CommerceVirtualOrderItemServiceBaseImpl {

	@Override
	public CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemLocalService.fetchCommerceVirtualOrderItem(
				commerceVirtualOrderItemId);

		if (commerceVirtualOrderItem != null) {
			_commerceVirtualOrderItemModelResourcePermission.check(
				getPermissionChecker(), commerceVirtualOrderItem,
				CommerceVirtualOrderActionKeys.
					DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM);
		}

		return commerceVirtualOrderItem;
	}

	@Override
	public CommerceVirtualOrderItem
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					commerceOrderItemId);

		if (commerceVirtualOrderItem != null) {
			_commerceVirtualOrderItemModelResourcePermission.check(
				getPermissionChecker(), commerceVirtualOrderItem,
				CommerceVirtualOrderActionKeys.
					DOWNLOAD_COMMERCE_VIRTUAL_ORDER_ITEM);
		}

		return commerceVirtualOrderItem;
	}

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