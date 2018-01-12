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