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
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.base.CommerceShipmentServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentServiceImpl
	extends CommerceShipmentServiceBaseImpl {

	@Override
	public CommerceShipment addCommerceShipment(
			long shipmentUserId, long commerceAddressId,
			long commerceShippingMethodId, long commerceWarehouseId,
			String carrier, String trackingNumber, int expectedDuration,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.addCommerceShipment(
			shipmentUserId, commerceAddressId, commerceShippingMethodId,
			commerceWarehouseId, carrier, trackingNumber, expectedDuration,
			status, shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute, serviceContext);
	}

	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		CommercePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentLocalService.deleteCommerceShipment(commerceShipment);
	}

	@Override
	public CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		CommercePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipment(
			commerceShipmentId);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
		long groupId, int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {

		return commerceShipmentLocalService.getCommerceShipments(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentsCount(long groupId) {
		return commerceShipmentLocalService.getCommerceShipmentsCount(groupId);
	}

	@Override
	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, long shipmentUserId,
			long commerceAddressId, long commerceShippingMethodId,
			String carrier, String trackingNumber, int expectedDuration,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		CommercePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCommerceShipment(
			commerceShipmentId, shipmentUserId, commerceAddressId,
			commerceShippingMethodId, carrier, trackingNumber, expectedDuration,
			status, shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

}