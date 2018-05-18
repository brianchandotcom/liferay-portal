/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemServiceImpl
	extends CommerceShipmentItemServiceBaseImpl {

	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.addCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		CommercePermission.check(
			getPermissionChecker(), commerceShipmentItem.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {

		return commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		CommercePermission.check(
			getPermissionChecker(), commerceShipmentItem.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

}