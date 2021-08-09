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

import com.liferay.commerce.service.CommerceOrderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceOrderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceOrderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceOrder</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceOrderSoap</code>. Methods that SOAP
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
 * @see CommerceOrderServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderServiceSoap {

	public static com.liferay.commerce.model.CommerceOrderSoap addCommerceOrder(
			long groupId, long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.addCommerceOrder(
					groupId, commerceAccountId, commerceCurrencyId,
					commerceOrderTypeId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			addOrUpdateCommerceOrder(
				String externalReferenceCode, long groupId,
				long commerceAccountId, long commerceCurrencyId,
				long commerceOrderTypeId, long billingAddressId,
				long shippingAddressId, String commercePaymentMethodKey,
				long commerceShippingMethodId, String shippingOptionName,
				String purchaseOrderNumber, java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount, int paymentStatus,
				int orderDateMonth, int orderDateDay, int orderDateYear,
				int orderDateHour, int orderDateMinute, int orderStatus,
				String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.addOrUpdateCommerceOrder(
					externalReferenceCode, groupId, commerceAccountId,
					commerceCurrencyId, commerceOrderTypeId, billingAddressId,
					shippingAddressId, commercePaymentMethodKey,
					commerceShippingMethodId, shippingOptionName,
					purchaseOrderNumber, subtotal, shippingAmount, taxAmount,
					total, subtotalWithTaxAmount, shippingWithTaxAmount,
					totalWithTaxAmount, paymentStatus, orderDateMonth,
					orderDateDay, orderDateYear, orderDateHour, orderDateMinute,
					orderStatus, advanceStatus, commerceContext,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			addOrUpdateCommerceOrder(
				String externalReferenceCode, long groupId,
				long commerceAccountId, long commerceCurrencyId,
				long commerceOrderTypeId, long billingAddressId,
				long shippingAddressId, String commercePaymentMethodKey,
				long commerceShippingMethodId, String shippingOptionName,
				String purchaseOrderNumber, java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount, int paymentStatus,
				int orderStatus, String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.addOrUpdateCommerceOrder(
					externalReferenceCode, groupId, commerceAccountId,
					commerceCurrencyId, commerceOrderTypeId, billingAddressId,
					shippingAddressId, commercePaymentMethodKey,
					commerceShippingMethodId, shippingOptionName,
					purchaseOrderNumber, subtotal, shippingAmount, taxAmount,
					total, subtotalWithTaxAmount, shippingWithTaxAmount,
					totalWithTaxAmount, paymentStatus, orderStatus,
					advanceStatus, commerceContext, serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap applyCouponCode(
			long commerceOrderId, String couponCode,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.applyCouponCode(
					commerceOrderId, couponCode, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceOrder(long commerceOrderId)
		throws RemoteException {

		try {
			CommerceOrderServiceUtil.deleteCommerceOrder(commerceOrderId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			executeWorkflowTransition(
				long commerceOrderId, long workflowTaskId,
				String transitionName, String comment)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.executeWorkflowTransition(
					commerceOrderId, workflowTaskId, transitionName, comment);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			fetchCommerceOrder(long commerceOrderId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.fetchCommerceOrder(commerceOrderId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceOrderSoap
			fetchCommerceOrder(
				long commerceAccountId, long groupId, int orderStatus)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.fetchCommerceOrder(
					commerceAccountId, groupId, orderStatus);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			fetchCommerceOrder(
				long commerceAccountId, long groupId, long userId,
				int orderStatus)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.fetchCommerceOrder(
					commerceAccountId, groupId, userId, orderStatus);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			fetchCommerceOrder(String uuid, long groupId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.fetchCommerceOrder(uuid, groupId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap getCommerceOrder(
			long commerceOrderId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.getCommerceOrder(commerceOrderId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			getCommerceOrderByUuidAndGroupId(String uuid, long groupId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.getCommerceOrderByUuidAndGroupId(
					uuid, groupId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getCommerceOrders(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrder>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getCommerceOrders(
					groupId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getCommerceOrders(long groupId, int[] orderStatuses)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getCommerceOrders(
					groupId, orderStatuses);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getCommerceOrders(
				long groupId, int[] orderStatuses, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getCommerceOrders(
					groupId, orderStatuses, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getCommerceOrders(
				long groupId, long commerceAccountId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrder>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getCommerceOrders(
					groupId, commerceAccountId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrdersCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = CommerceOrderServiceUtil.getCommerceOrdersCount(
				groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrdersCount(
			long groupId, long commerceAccountId)
		throws RemoteException {

		try {
			int returnValue = CommerceOrderServiceUtil.getCommerceOrdersCount(
				groupId, commerceAccountId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getPendingCommerceOrders(
				long groupId, long commerceAccountId, String keywords,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getPendingCommerceOrders(
					groupId, commerceAccountId, keywords, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long getPendingCommerceOrdersCount(
			long companyId, long groupId)
		throws RemoteException {

		try {
			long returnValue =
				CommerceOrderServiceUtil.getPendingCommerceOrdersCount(
					companyId, groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getPendingCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderServiceUtil.getPendingCommerceOrdersCount(
					groupId, commerceAccountId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getPlacedCommerceOrders(
				long companyId, long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getPlacedCommerceOrders(
					companyId, groupId, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getPlacedCommerceOrders(
				long groupId, long commerceAccountId, String keywords,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue = CommerceOrderServiceUtil.getPlacedCommerceOrders(
					groupId, commerceAccountId, keywords, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long getPlacedCommerceOrdersCount(
			long companyId, long groupId)
		throws RemoteException {

		try {
			long returnValue =
				CommerceOrderServiceUtil.getPlacedCommerceOrdersCount(
					companyId, groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getPlacedCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderServiceUtil.getPlacedCommerceOrdersCount(
					groupId, commerceAccountId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getUserPendingCommerceOrders(
				long companyId, long groupId, String keywords, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue =
					CommerceOrderServiceUtil.getUserPendingCommerceOrders(
						companyId, groupId, keywords, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long getUserPendingCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws RemoteException {

		try {
			long returnValue =
				CommerceOrderServiceUtil.getUserPendingCommerceOrdersCount(
					companyId, groupId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[]
			getUserPlacedCommerceOrders(
				long companyId, long groupId, String keywords, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder>
				returnValue =
					CommerceOrderServiceUtil.getUserPlacedCommerceOrders(
						companyId, groupId, keywords, start, end);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long getUserPlacedCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws RemoteException {

		try {
			long returnValue =
				CommerceOrderServiceUtil.getUserPlacedCommerceOrdersCount(
					companyId, groupId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CommerceOrderServiceUtil.mergeGuestCommerceOrder(
				guestCommerceOrderId, userCommerceOrderId, commerceContext,
				serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap recalculatePrice(
			long commerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.recalculatePrice(
					commerceOrderId, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			reorderCommerceOrder(
				long commerceOrderId,
				com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.reorderCommerceOrder(
					commerceOrderId, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateBillingAddress(long commerceOrderId, long billingAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateBillingAddress(
					commerceOrderId, billingAddressId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateBillingAddress(
				long commerceOrderId, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateBillingAddress(
					commerceOrderId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrder(
				com.liferay.commerce.model.CommerceOrderSoap commerceOrder)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrder(
					com.liferay.commerce.model.impl.CommerceOrderModelImpl.
						toModel(commerceOrder));

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrder(
				long commerceOrderId, long billingAddressId,
				long shippingAddressId, String commercePaymentMethodKey,
				long commerceShippingMethodId, String shippingOptionName,
				String purchaseOrderNumber, java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount, java.math.BigDecimal total,
				String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrder(
					commerceOrderId, billingAddressId, shippingAddressId,
					commercePaymentMethodKey, commerceShippingMethodId,
					shippingOptionName, purchaseOrderNumber, subtotal,
					shippingAmount, total, advanceStatus, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrder(
				String externalReferenceCode, long commerceOrderId,
				long billingAddressId, long shippingAddressId,
				String commercePaymentMethodKey, long commerceShippingMethodId,
				String shippingOptionName, String purchaseOrderNumber,
				java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount, String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrder(
					externalReferenceCode, commerceOrderId, billingAddressId,
					shippingAddressId, commercePaymentMethodKey,
					commerceShippingMethodId, shippingOptionName,
					purchaseOrderNumber, subtotal, shippingAmount, taxAmount,
					total, subtotalWithTaxAmount, shippingWithTaxAmount,
					totalWithTaxAmount, advanceStatus, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrder(
				String externalReferenceCode, long commerceOrderId,
				long billingAddressId, long shippingAddressId,
				String commercePaymentMethodKey, long commerceShippingMethodId,
				String shippingOptionName, String purchaseOrderNumber,
				java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount, java.math.BigDecimal total,
				String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrder(
					externalReferenceCode, commerceOrderId, billingAddressId,
					shippingAddressId, commercePaymentMethodKey,
					commerceShippingMethodId, shippingOptionName,
					purchaseOrderNumber, subtotal, shippingAmount, total,
					advanceStatus, commerceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrderExternalReferenceCode(
				String externalReferenceCode, long commerceOrderId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.
					updateCommerceOrderExternalReferenceCode(
						externalReferenceCode, commerceOrderId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrderPrices(
				long commerceOrderId, java.math.BigDecimal subtotal,
				java.math.BigDecimal subtotalDiscountAmount,
				java.math.BigDecimal subtotalDiscountPercentageLevel1,
				java.math.BigDecimal subtotalDiscountPercentageLevel2,
				java.math.BigDecimal subtotalDiscountPercentageLevel3,
				java.math.BigDecimal subtotalDiscountPercentageLevel4,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal shippingDiscountAmount,
				java.math.BigDecimal shippingDiscountPercentageLevel1,
				java.math.BigDecimal shippingDiscountPercentageLevel2,
				java.math.BigDecimal shippingDiscountPercentageLevel3,
				java.math.BigDecimal shippingDiscountPercentageLevel4,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal totalDiscountAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1,
				java.math.BigDecimal totalDiscountPercentageLevel2,
				java.math.BigDecimal totalDiscountPercentageLevel3,
				java.math.BigDecimal totalDiscountPercentageLevel4)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrderPrices(
					commerceOrderId, subtotal, subtotalDiscountAmount,
					subtotalDiscountPercentageLevel1,
					subtotalDiscountPercentageLevel2,
					subtotalDiscountPercentageLevel3,
					subtotalDiscountPercentageLevel4, shippingAmount,
					shippingDiscountAmount, shippingDiscountPercentageLevel1,
					shippingDiscountPercentageLevel2,
					shippingDiscountPercentageLevel3,
					shippingDiscountPercentageLevel4, taxAmount, total,
					totalDiscountAmount, totalDiscountPercentageLevel1,
					totalDiscountPercentageLevel2,
					totalDiscountPercentageLevel3,
					totalDiscountPercentageLevel4);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceOrderPrices(
				long commerceOrderId, java.math.BigDecimal subtotal,
				java.math.BigDecimal subtotalDiscountAmount,
				java.math.BigDecimal subtotalDiscountPercentageLevel1,
				java.math.BigDecimal subtotalDiscountPercentageLevel2,
				java.math.BigDecimal subtotalDiscountPercentageLevel3,
				java.math.BigDecimal subtotalDiscountPercentageLevel4,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal shippingDiscountAmount,
				java.math.BigDecimal shippingDiscountPercentageLevel1,
				java.math.BigDecimal shippingDiscountPercentageLevel2,
				java.math.BigDecimal shippingDiscountPercentageLevel3,
				java.math.BigDecimal shippingDiscountPercentageLevel4,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal totalDiscountAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1,
				java.math.BigDecimal totalDiscountPercentageLevel2,
				java.math.BigDecimal totalDiscountPercentageLevel3,
				java.math.BigDecimal totalDiscountPercentageLevel4,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal subtotalDiscountWithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel4WithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal shippingDiscountWithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel4WithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount,
				java.math.BigDecimal totalDiscountWithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel4WithTaxAmount)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceOrderPrices(
					commerceOrderId, subtotal, subtotalDiscountAmount,
					subtotalDiscountPercentageLevel1,
					subtotalDiscountPercentageLevel2,
					subtotalDiscountPercentageLevel3,
					subtotalDiscountPercentageLevel4, shippingAmount,
					shippingDiscountAmount, shippingDiscountPercentageLevel1,
					shippingDiscountPercentageLevel2,
					shippingDiscountPercentageLevel3,
					shippingDiscountPercentageLevel4, taxAmount, total,
					totalDiscountAmount, totalDiscountPercentageLevel1,
					totalDiscountPercentageLevel2,
					totalDiscountPercentageLevel3,
					totalDiscountPercentageLevel4, subtotalWithTaxAmount,
					subtotalDiscountWithTaxAmount,
					subtotalDiscountPercentageLevel1WithTaxAmount,
					subtotalDiscountPercentageLevel2WithTaxAmount,
					subtotalDiscountPercentageLevel3WithTaxAmount,
					subtotalDiscountPercentageLevel4WithTaxAmount,
					shippingWithTaxAmount, shippingDiscountWithTaxAmount,
					shippingDiscountPercentageLevel1WithTaxAmount,
					shippingDiscountPercentageLevel2WithTaxAmount,
					shippingDiscountPercentageLevel3WithTaxAmount,
					shippingDiscountPercentageLevel4WithTaxAmount,
					totalWithTaxAmount, totalDiscountWithTaxAmount,
					totalDiscountPercentageLevel1WithTaxAmount,
					totalDiscountPercentageLevel2WithTaxAmount,
					totalDiscountPercentageLevel3WithTaxAmount,
					totalDiscountPercentageLevel4WithTaxAmount);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommercePaymentMethodKey(
				long commerceOrderId, String commercePaymentMethodKey)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommercePaymentMethodKey(
					commerceOrderId, commercePaymentMethodKey);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCommerceShippingMethod(
				long commerceOrderId, long commerceShippingMethodId,
				String commerceShippingOptionName,
				com.liferay.commerce.context.CommerceContext commerceContext,
				String locale)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCommerceShippingMethod(
					commerceOrderId, commerceShippingMethodId,
					commerceShippingOptionName, commerceContext,
					LocaleUtil.fromLanguageId(locale));

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateCustomFields(
				long commerceOrderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateCustomFields(
					commerceOrderId, serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateInfo(
			long commerceOrderId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateInfo(
					commerceOrderId, printedNote, requestedDeliveryDateMonth,
					requestedDeliveryDateDay, requestedDeliveryDateYear,
					requestedDeliveryDateHour, requestedDeliveryDateMinute,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateOrderDate(
			long commerceOrderId, int orderDateMonth, int orderDateDay,
			int orderDateYear, int orderDateHour, int orderDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateOrderDate(
					commerceOrderId, orderDateMonth, orderDateDay,
					orderDateYear, orderDateHour, orderDateMinute,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceOrderSoap
			updatePaymentStatus(long commerceOrderId, int paymentStatus)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updatePaymentStatus(
					commerceOrderId, paymentStatus);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceOrderSoap
			updatePaymentStatusAndTransactionId(
				long commerceOrderId, int paymentStatus, String transactionId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updatePaymentStatusAndTransactionId(
					commerceOrderId, paymentStatus, transactionId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updatePrintedNote(long commerceOrderId, String printedNote)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updatePrintedNote(
					commerceOrderId, printedNote);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updatePurchaseOrderNumber(
				long commerceOrderId, String purchaseOrderNumber)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updatePurchaseOrderNumber(
					commerceOrderId, purchaseOrderNumber);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateShippingAddress(long commerceOrderId, long shippingAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateShippingAddress(
					commerceOrderId, shippingAddressId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateShippingAddress(
				long commerceOrderId, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateShippingAddress(
					commerceOrderId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap
			updateTransactionId(long commerceOrderId, String transactionId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateTransactionId(
					commerceOrderId, transactionId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateUser(
			long commerceOrderId, long userId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrder returnValue =
				CommerceOrderServiceUtil.updateUser(commerceOrderId, userId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderServiceSoap.class);

}