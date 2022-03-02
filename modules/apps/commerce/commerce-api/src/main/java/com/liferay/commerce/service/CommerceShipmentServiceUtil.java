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

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceShipment. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceShipmentServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentService
 * @generated
 */
public class CommerceShipmentServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceShipment addCommerceShipment(
			long commerceOrderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	public static CommerceShipment addCommerceShipment(
			String externalReferenceCode, long groupId, long commerceAccountId,
			long commerceAddressId, long commerceShippingMethodId,
			String commerceShippingOptionName,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShipment(
			externalReferenceCode, groupId, commerceAccountId,
			commerceAddressId, commerceShippingMethodId,
			commerceShippingOptionName, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public static void deleteCommerceShipment(long commerceShipmentId)
		throws PortalException {

		getService().deleteCommerceShipment(commerceShipmentId);
	}

	public static void deleteCommerceShipment(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException {

		getService().deleteCommerceShipment(
			commerceShipmentId, restoreStockQuantity);
	}

	public static CommerceShipment fetchCommerceShipment(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchCommerceShipment(
			companyId, externalReferenceCode);
	}

	public static CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException {

		return getService().getCommerceShipment(commerceShipmentId);
	}

	public static List<CommerceShipment> getCommerceShipments(
			long companyId, int status, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		return getService().getCommerceShipments(
			companyId, status, start, end, orderByComparator);
	}

	public static List<CommerceShipment> getCommerceShipments(
			long companyId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		return getService().getCommerceShipments(
			companyId, start, end, orderByComparator);
	}

	public static List<CommerceShipment> getCommerceShipments(
			long companyId, long commerceAddressId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		return getService().getCommerceShipments(
			companyId, commerceAddressId, start, end, orderByComparator);
	}

	public static List<CommerceShipment> getCommerceShipments(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus, int start, int end)
		throws PortalException {

		return getService().getCommerceShipments(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus, start, end);
	}

	public static List<CommerceShipment> getCommerceShipmentsByOrderId(
		long commerceOrderId, int start, int end) {

		return getService().getCommerceShipmentsByOrderId(
			commerceOrderId, start, end);
	}

	public static int getCommerceShipmentsCount(long companyId)
		throws PortalException {

		return getService().getCommerceShipmentsCount(companyId);
	}

	public static int getCommerceShipmentsCount(long companyId, int status)
		throws PortalException {

		return getService().getCommerceShipmentsCount(companyId, status);
	}

	public static int getCommerceShipmentsCount(
			long companyId, long commerceAddressId)
		throws PortalException {

		return getService().getCommerceShipmentsCount(
			companyId, commerceAddressId);
	}

	public static int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws PortalException {

		return getService().getCommerceShipmentsCount(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus);
	}

	public static int getCommerceShipmentsCountByOrderId(long commerceOrderId) {
		return getService().getCommerceShipmentsCountByOrderId(commerceOrderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShipment reprocessCommerceShipment(
			long commerceShipmentId)
		throws PortalException {

		return getService().reprocessCommerceShipment(commerceShipmentId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateAddress(long, String, String, String, String, String, String,
	 String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	public static CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws PortalException {

		return getService().updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber);
	}

	public static CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, serviceContext);
	}

	public static CommerceShipment updateCarrierDetails(
			long commerceShipmentId, String carrier, String trackingNumber)
		throws PortalException {

		return getService().updateCarrierDetails(
			commerceShipmentId, carrier, trackingNumber);
	}

	public static CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String carrier, String trackingNumber,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		return getService().updateCommerceShipment(
			commerceShipmentId, carrier, trackingNumber, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	public static CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			String carrier, String trackingNumber, int status,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, int expectedDateMonth,
			int expectedDateDay, int expectedDateYear, int expectedDateHour,
			int expectedDateMinute)
		throws PortalException {

		return getService().updateCommerceShipment(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, carrier,
			trackingNumber, status, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute,
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute);
	}

	public static CommerceShipment updateCommerceShipmentExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentId)
		throws PortalException {

		return getService().updateCommerceShipmentExternalReferenceCode(
			externalReferenceCode, commerceShipmentId);
	}

	public static CommerceShipment updateExpectedDate(
			long commerceShipmentId, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		return getService().updateExpectedDate(
			commerceShipmentId, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	public static CommerceShipment updateShippingDate(
			long commerceShipmentId, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute)
		throws PortalException {

		return getService().updateShippingDate(
			commerceShipmentId, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute);
	}

	public static CommerceShipment updateStatus(
			long commerceShipmentId, int status)
		throws PortalException {

		return getService().updateStatus(commerceShipmentId, status);
	}

	public static CommerceShipmentService getService() {
		return _service;
	}

	private static volatile CommerceShipmentService _service;

}