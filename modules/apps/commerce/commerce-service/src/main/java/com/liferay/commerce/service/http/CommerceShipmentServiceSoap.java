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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceShipmentServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceShipmentServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceShipmentSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceShipment</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceShipmentSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceShipmentServiceSoap {

	public static com.liferay.commerce.model.CommerceShipmentSoap
			addCommerceShipment(
				long groupId, long commerceAccountId, long commerceAddressId,
				long commerceShippingMethodId,
				String commerceShippingOptionName,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.addCommerceShipment(
					groupId, commerceAccountId, commerceAddressId,
					commerceShippingMethodId, commerceShippingOptionName,
					serviceContext);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			addCommerceShipment(
				long commerceOrderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.addCommerceShipment(
					commerceOrderId, serviceContext);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public static void deleteCommerceShipment(long commerceShipmentId)
		throws RemoteException {

		try {
			CommerceShipmentServiceUtil.deleteCommerceShipment(
				commerceShipmentId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceShipment(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws RemoteException {

		try {
			CommerceShipmentServiceUtil.deleteCommerceShipment(
				commerceShipmentId, restoreStockQuantity);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			getCommerceShipment(long commerceShipmentId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.getCommerceShipment(
					commerceShipmentId);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[]
			getCommerceShipments(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment>
				returnValue = CommerceShipmentServiceUtil.getCommerceShipments(
					companyId, status, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[]
			getCommerceShipments(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment>
				returnValue = CommerceShipmentServiceUtil.getCommerceShipments(
					companyId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[]
			getCommerceShipments(
				long companyId, long commerceAddressId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipment>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment>
				returnValue = CommerceShipmentServiceUtil.getCommerceShipments(
					companyId, commerceAddressId, start, end,
					orderByComparator);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[]
			getCommerceShipments(
				long companyId, long[] groupIds, long[] commerceAccountIds,
				String keywords, int[] shipmentStatuses,
				boolean excludeShipmentStatus, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment>
				returnValue = CommerceShipmentServiceUtil.getCommerceShipments(
					companyId, groupIds, commerceAccountIds, keywords,
					shipmentStatuses, excludeShipmentStatus, start, end);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[]
			getCommerceShipmentsByOrderId(
				long commerceOrderId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment>
				returnValue =
					CommerceShipmentServiceUtil.getCommerceShipmentsByOrderId(
						commerceOrderId, start, end);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShipmentsCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShipmentsCount(long companyId, int status)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsCount(
					companyId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShipmentsCount(
			long companyId, long commerceAddressId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsCount(
					companyId, commerceAddressId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsCount(
					companyId, groupIds, commerceAccountIds, keywords,
					shipmentStatuses, excludeShipmentStatus);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShipmentsCountByOrderId(long commerceOrderId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsCountByOrderId(
					commerceOrderId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			reprocessCommerceShipment(long commerceShipmentId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.reprocessCommerceShipment(
					commerceShipmentId);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateAddress(long, String, String, String, String, String, String,
	 String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceShipmentSoap updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateAddress(
					commerceShipmentId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateAddress(
					commerceShipmentId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					serviceContext);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			updateCarrierDetails(
				long commerceShipmentId, String carrier, String trackingNumber)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateCarrierDetails(
					commerceShipmentId, carrier, trackingNumber);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			updateCommerceShipment(
				long commerceShipmentId, String carrier, String trackingNumber,
				int status, int shippingDateMonth, int shippingDateDay,
				int shippingDateYear, int shippingDateHour,
				int shippingDateMinute, int expectedDateMonth,
				int expectedDateDay, int expectedDateYear, int expectedDateHour,
				int expectedDateMinute)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateCommerceShipment(
					commerceShipmentId, carrier, trackingNumber, status,
					shippingDateMonth, shippingDateDay, shippingDateYear,
					shippingDateHour, shippingDateMinute, expectedDateMonth,
					expectedDateDay, expectedDateYear, expectedDateHour,
					expectedDateMinute);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			updateCommerceShipment(
				long commerceShipmentId, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				String carrier, String trackingNumber, int status,
				int shippingDateMonth, int shippingDateDay,
				int shippingDateYear, int shippingDateHour,
				int shippingDateMinute, int expectedDateMonth,
				int expectedDateDay, int expectedDateYear, int expectedDateHour,
				int expectedDateMinute)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateCommerceShipment(
					commerceShipmentId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					carrier, trackingNumber, status, shippingDateMonth,
					shippingDateDay, shippingDateYear, shippingDateHour,
					shippingDateMinute, expectedDateMonth, expectedDateDay,
					expectedDateYear, expectedDateHour, expectedDateMinute);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			updateExpectedDate(
				long commerceShipmentId, int expectedDateMonth,
				int expectedDateDay, int expectedDateYear, int expectedDateHour,
				int expectedDateMinute)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateExpectedDate(
					commerceShipmentId, expectedDateMonth, expectedDateDay,
					expectedDateYear, expectedDateHour, expectedDateMinute);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap
			updateShippingDate(
				long commerceShipmentId, int shippingDateMonth,
				int shippingDateDay, int shippingDateYear, int shippingDateHour,
				int shippingDateMinute)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateShippingDate(
					commerceShipmentId, shippingDateMonth, shippingDateDay,
					shippingDateYear, shippingDateHour, shippingDateMinute);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap updateStatus(
			long commerceShipmentId, int status)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShipment returnValue =
				CommerceShipmentServiceUtil.updateStatus(
					commerceShipmentId, status);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShipmentServiceSoap.class);

}