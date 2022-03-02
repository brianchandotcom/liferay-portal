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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentService
 * @generated
 */
public class CommerceShipmentServiceWrapper
	implements CommerceShipmentService,
			   ServiceWrapper<CommerceShipmentService> {

	public CommerceShipmentServiceWrapper() {
		this(null);
	}

	public CommerceShipmentServiceWrapper(
		CommerceShipmentService commerceShipmentService) {

		_commerceShipmentService = commerceShipmentService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
			long commerceOrderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
			String externalReferenceCode, long groupId, long commerceAccountId,
			long commerceAddressId, long commerceShippingMethodId,
			String commerceShippingOptionName,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.addCommerceShipment(
			externalReferenceCode, groupId, commerceAccountId,
			commerceAddressId, commerceShippingMethodId,
			commerceShippingOptionName, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentService.deleteCommerceShipment(commerceShipmentId);
	}

	@Override
	public void deleteCommerceShipment(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentService.deleteCommerceShipment(
			commerceShipmentId, restoreStockQuantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment fetchCommerceShipment(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.fetchCommerceShipment(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment getCommerceShipment(
			long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipment(commerceShipmentId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment>
			getCommerceShipments(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipments(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment>
			getCommerceShipments(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipments(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment>
			getCommerceShipments(
				long companyId, long commerceAddressId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipments(
			companyId, commerceAddressId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment>
			getCommerceShipments(
				long companyId, long[] groupIds, long[] commerceAccountIds,
				String keywords, int[] shipmentStatuses,
				boolean excludeShipmentStatus, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipments(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment>
		getCommerceShipmentsByOrderId(
			long commerceOrderId, int start, int end) {

		return _commerceShipmentService.getCommerceShipmentsByOrderId(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceShipmentsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipmentsCount(companyId);
	}

	@Override
	public int getCommerceShipmentsCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipmentsCount(
			companyId, status);
	}

	@Override
	public int getCommerceShipmentsCount(long companyId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipmentsCount(
			companyId, commerceAddressId);
	}

	@Override
	public int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.getCommerceShipmentsCount(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus);
	}

	@Override
	public int getCommerceShipmentsCountByOrderId(long commerceOrderId) {
		return _commerceShipmentService.getCommerceShipmentsCountByOrderId(
			commerceOrderId);
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
	public com.liferay.commerce.model.CommerceShipment
			reprocessCommerceShipment(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.reprocessCommerceShipment(
			commerceShipmentId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateAddress(long, String, String, String, String, String, String,
	 String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCarrierDetails(
			long commerceShipmentId, String carrier, String trackingNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateCarrierDetails(
			commerceShipmentId, carrier, trackingNumber);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String carrier, String trackingNumber,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateCommerceShipment(
			commerceShipmentId, carrier, trackingNumber, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			String carrier, String trackingNumber, int status,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, int expectedDateMonth,
			int expectedDateDay, int expectedDateYear, int expectedDateHour,
			int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateCommerceShipment(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, carrier,
			trackingNumber, status, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute,
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment
			updateCommerceShipmentExternalReferenceCode(
				String externalReferenceCode, long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.
			updateCommerceShipmentExternalReferenceCode(
				externalReferenceCode, commerceShipmentId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateExpectedDate(
			long commerceShipmentId, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateExpectedDate(
			commerceShipmentId, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateShippingDate(
			long commerceShipmentId, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateShippingDate(
			commerceShipmentId, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateStatus(
			long commerceShipmentId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentService.updateStatus(
			commerceShipmentId, status);
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