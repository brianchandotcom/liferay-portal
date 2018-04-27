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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentService
 * @generated
 */
@ProviderType
public class CommerceShipmentServiceWrapper implements CommerceShipmentService,
	ServiceWrapper<CommerceShipmentService> {
	public CommerceShipmentServiceWrapper(
		CommerceShipmentService commerceShipmentService) {
		_commerceShipmentService = commerceShipmentService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
		long shipmentUserId, long commerceAddressId,
		long commerceShippingMethodId, long commerceWarehouseId,
		String carrier, String trackingNumber, int expectedDuration,
		int status, int shippingDateMonth, int shippingDateDay,
		int shippingDateYear, int shippingDateHour, int shippingDateMinute,
		int expectedDateMonth, int expectedDateDay, int expectedDateYear,
		int expectedDateHour, int expectedDateMinute,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.addCommerceShipment(shipmentUserId,
			commerceAddressId, commerceShippingMethodId, commerceWarehouseId,
			carrier, trackingNumber, expectedDuration, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute, serviceContext);
	}

	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShipmentService.deleteCommerceShipment(commerceShipmentId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment getCommerceShipment(
		long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.getCommerceShipment(commerceShipmentId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipments(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator) {
		return _commerceShipmentService.getCommerceShipments(groupId, start,
			end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentsCount(long groupId) {
		return _commerceShipmentService.getCommerceShipmentsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShipmentService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
		long commerceShipmentId, long shipmentUserId, long commerceAddressId,
		long commerceShippingMethodId, String carrier, String trackingNumber,
		int expectedDuration, int status, int shippingDateMonth,
		int shippingDateDay, int shippingDateYear, int shippingDateHour,
		int shippingDateMinute, int expectedDateMonth, int expectedDateDay,
		int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.updateCommerceShipment(commerceShipmentId,
			shipmentUserId, commerceAddressId, commerceShippingMethodId,
			carrier, trackingNumber, expectedDuration, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	@Override
	public CommerceShipmentService getWrappedService() {
		return _commerceShipmentService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentService commerceShipmentService) {
		_commerceShipmentService = commerceShipmentService;
	}

	private CommerceShipmentService _commerceShipmentService;
}