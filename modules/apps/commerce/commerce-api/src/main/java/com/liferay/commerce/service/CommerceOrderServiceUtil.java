/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceOrder. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceOrderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderService
 * @generated
 */
public class CommerceOrderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceOrder addCommerceOrder(
			long groupId, long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId)
		throws PortalException {

		return getService().addCommerceOrder(
			groupId, commerceAccountId, commerceCurrencyId,
			commerceOrderTypeId);
	}

	public static CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long groupId, long billingAddressId,
			long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, int orderDateMonth,
			int orderDateDay, int orderDateYear, int orderDateHour,
			int orderDateMinute, int orderStatus, int paymentStatus,
			String purchaseOrderNumber, java.math.BigDecimal shippingAmount,
			String shippingOptionName,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal subtotal,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal totalWithTaxAmount,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceOrder(
			externalReferenceCode, groupId, billingAddressId, commerceAccountId,
			commerceCurrencyId, commerceOrderTypeId, commerceShippingMethodId,
			shippingAddressId, advanceStatus, commercePaymentMethodKey,
			orderDateMonth, orderDateDay, orderDateYear, orderDateHour,
			orderDateMinute, orderStatus, paymentStatus, purchaseOrderNumber,
			shippingAmount, shippingOptionName, shippingWithTaxAmount, subtotal,
			subtotalWithTaxAmount, taxAmount, total, totalWithTaxAmount,
			commerceContext, serviceContext);
	}

	public static CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long groupId, long billingAddressId,
			long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, int orderStatus, int paymentStatus,
			String purchaseOrderNumber, java.math.BigDecimal shippingAmount,
			String shippingOptionName,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal subtotal,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal totalWithTaxAmount,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceOrder(
			externalReferenceCode, groupId, billingAddressId, commerceAccountId,
			commerceCurrencyId, commerceOrderTypeId, commerceShippingMethodId,
			shippingAddressId, advanceStatus, commercePaymentMethodKey,
			orderStatus, paymentStatus, purchaseOrderNumber, shippingAmount,
			shippingOptionName, shippingWithTaxAmount, subtotal,
			subtotalWithTaxAmount, taxAmount, total, totalWithTaxAmount,
			commerceContext, serviceContext);
	}

	public static CommerceOrder applyCouponCode(
			long commerceOrderId, String couponCode,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		return getService().applyCouponCode(
			commerceOrderId, couponCode, commerceContext);
	}

	public static void deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		getService().deleteCommerceOrder(commerceOrderId);
	}

	public static CommerceOrder executeWorkflowTransition(
			long commerceOrderId, long workflowTaskId, String transitionName,
			String comment)
		throws PortalException {

		return getService().executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	public static CommerceOrder fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException {

		return getService().fetchCommerceOrder(commerceOrderId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceOrder fetchCommerceOrder(
			long commerceAccountId, long groupId, int orderStatus)
		throws PortalException {

		return getService().fetchCommerceOrder(
			commerceAccountId, groupId, orderStatus);
	}

	public static CommerceOrder fetchCommerceOrder(
			long commerceAccountId, long groupId, long userId, int orderStatus)
		throws PortalException {

		return getService().fetchCommerceOrder(
			commerceAccountId, groupId, userId, orderStatus);
	}

	public static CommerceOrder fetchCommerceOrder(String uuid, long groupId)
		throws PortalException {

		return getService().fetchCommerceOrder(uuid, groupId);
	}

	public static CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		return getService().getCommerceOrder(commerceOrderId);
	}

	public static CommerceOrder getCommerceOrderByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getCommerceOrderByUuidAndGroupId(uuid, groupId);
	}

	public static List<CommerceOrder> getCommerceOrders(
			long groupId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException {

		return getService().getCommerceOrders(
			groupId, start, end, orderByComparator);
	}

	public static List<CommerceOrder> getCommerceOrders(
			long groupId, int[] orderStatuses)
		throws PortalException {

		return getService().getCommerceOrders(groupId, orderStatuses);
	}

	public static List<CommerceOrder> getCommerceOrders(
			long groupId, int[] orderStatuses, int start, int end)
		throws PortalException {

		return getService().getCommerceOrders(
			groupId, orderStatuses, start, end);
	}

	public static List<CommerceOrder> getCommerceOrders(
			long groupId, long commerceAccountId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException {

		return getService().getCommerceOrders(
			groupId, commerceAccountId, start, end, orderByComparator);
	}

	public static int getCommerceOrdersCount(long groupId)
		throws PortalException {

		return getService().getCommerceOrdersCount(groupId);
	}

	public static int getCommerceOrdersCount(
			long groupId, long commerceAccountId)
		throws PortalException {

		return getService().getCommerceOrdersCount(groupId, commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceOrder> getPendingCommerceOrders(
			long groupId, long commerceAccountId, String keywords, int start,
			int end)
		throws PortalException {

		return getService().getPendingCommerceOrders(
			groupId, commerceAccountId, keywords, start, end);
	}

	public static long getPendingCommerceOrdersCount(
			long companyId, long groupId)
		throws PortalException {

		return getService().getPendingCommerceOrdersCount(companyId, groupId);
	}

	public static int getPendingCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws PortalException {

		return getService().getPendingCommerceOrdersCount(
			groupId, commerceAccountId, keywords);
	}

	public static List<CommerceOrder> getPlacedCommerceOrders(
			long companyId, long groupId, int start, int end)
		throws PortalException {

		return getService().getPlacedCommerceOrders(
			companyId, groupId, start, end);
	}

	public static List<CommerceOrder> getPlacedCommerceOrders(
			long groupId, long commerceAccountId, String keywords, int start,
			int end)
		throws PortalException {

		return getService().getPlacedCommerceOrders(
			groupId, commerceAccountId, keywords, start, end);
	}

	public static long getPlacedCommerceOrdersCount(
			long companyId, long groupId)
		throws PortalException {

		return getService().getPlacedCommerceOrdersCount(companyId, groupId);
	}

	public static int getPlacedCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws PortalException {

		return getService().getPlacedCommerceOrdersCount(
			groupId, commerceAccountId, keywords);
	}

	public static List<CommerceOrder> getUserCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException {

		return getService().getUserCommerceOrders(
			companyId, groupId, keywords, start, end);
	}

	public static long getUserCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException {

		return getService().getUserCommerceOrdersCount(
			companyId, groupId, keywords);
	}

	public static List<CommerceOrder> getUserOpenCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getUserOpenCommerceOrders(
			companyId, groupId, keywords, start, end, sort);
	}

	public static List<CommerceOrder> getUserPendingCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException {

		return getService().getUserPendingCommerceOrders(
			companyId, groupId, keywords, start, end);
	}

	public static long getUserPendingCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException {

		return getService().getUserPendingCommerceOrdersCount(
			companyId, groupId, keywords);
	}

	public static List<CommerceOrder> getUserPlacedCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException {

		return getService().getUserPlacedCommerceOrders(
			companyId, groupId, keywords, start, end);
	}

	public static List<CommerceOrder> getUserPlacedCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getUserPlacedCommerceOrders(
			companyId, groupId, keywords, start, end, sort);
	}

	public static long getUserPlacedCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException {

		return getService().getUserPlacedCommerceOrdersCount(
			companyId, groupId, keywords);
	}

	public static void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().mergeGuestCommerceOrder(
			guestCommerceOrderId, userCommerceOrderId, commerceContext,
			serviceContext);
	}

	public static CommerceOrder recalculatePrice(
			long commerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		return getService().recalculatePrice(commerceOrderId, commerceContext);
	}

	public static CommerceOrder reorderCommerceOrder(
			long commerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		return getService().reorderCommerceOrder(
			commerceOrderId, commerceContext);
	}

	public static CommerceOrder resetTermsAndConditions(
			long commerceOrderId, boolean deliveryCommerceTermEntry,
			boolean paymentCommerceTermEntry)
		throws PortalException {

		return getService().resetTermsAndConditions(
			commerceOrderId, deliveryCommerceTermEntry,
			paymentCommerceTermEntry);
	}

	public static CommerceOrder updateBillingAddress(
			long commerceOrderId, long billingAddressId)
		throws PortalException {

		return getService().updateBillingAddress(
			commerceOrderId, billingAddressId);
	}

	public static CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber, serviceContext);
	}

	public static CommerceOrder updateCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		return getService().updateCommerceOrder(commerceOrder);
	}

	public static CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, String purchaseOrderNumber,
			java.math.BigDecimal shippingAmount, String shippingOptionName,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal subtotal,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal totalDiscountAmount,
			java.math.BigDecimal totalWithTaxAmount,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		return getService().updateCommerceOrder(
			externalReferenceCode, commerceOrderId, billingAddressId,
			commerceShippingMethodId, shippingAddressId, advanceStatus,
			commercePaymentMethodKey, purchaseOrderNumber, shippingAmount,
			shippingOptionName, shippingWithTaxAmount, subtotal,
			subtotalWithTaxAmount, taxAmount, total, totalDiscountAmount,
			totalWithTaxAmount, commerceContext);
	}

	public static CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, String purchaseOrderNumber,
			java.math.BigDecimal shippingAmount, String shippingOptionName,
			java.math.BigDecimal subtotal, java.math.BigDecimal total,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws PortalException {

		return getService().updateCommerceOrder(
			externalReferenceCode, commerceOrderId, billingAddressId,
			commerceShippingMethodId, shippingAddressId, advanceStatus,
			commercePaymentMethodKey, purchaseOrderNumber, shippingAmount,
			shippingOptionName, subtotal, total, commerceContext);
	}

	public static CommerceOrder updateCommerceOrderExternalReferenceCode(
			String externalReferenceCode, long commerceOrderId)
		throws PortalException {

		return getService().updateCommerceOrderExternalReferenceCode(
			externalReferenceCode, commerceOrderId);
	}

	public static CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, java.math.BigDecimal shippingAmount,
			java.math.BigDecimal shippingDiscountAmount,
			java.math.BigDecimal shippingDiscountPercentageLevel1,
			java.math.BigDecimal shippingDiscountPercentageLevel2,
			java.math.BigDecimal shippingDiscountPercentageLevel3,
			java.math.BigDecimal shippingDiscountPercentageLevel4,
			java.math.BigDecimal subtotal,
			java.math.BigDecimal subtotalDiscountAmount,
			java.math.BigDecimal subtotalDiscountPercentageLevel1,
			java.math.BigDecimal subtotalDiscountPercentageLevel2,
			java.math.BigDecimal subtotalDiscountPercentageLevel3,
			java.math.BigDecimal subtotalDiscountPercentageLevel4,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal totalDiscountAmount,
			java.math.BigDecimal totalDiscountPercentageLevel1,
			java.math.BigDecimal totalDiscountPercentageLevel2,
			java.math.BigDecimal totalDiscountPercentageLevel3,
			java.math.BigDecimal totalDiscountPercentageLevel4)
		throws PortalException {

		return getService().updateCommerceOrderPrices(
			commerceOrderId, shippingAmount, shippingDiscountAmount,
			shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel2,
			shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel4,
			subtotal, subtotalDiscountAmount, subtotalDiscountPercentageLevel1,
			subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel3,
			subtotalDiscountPercentageLevel4, taxAmount, total,
			totalDiscountAmount, totalDiscountPercentageLevel1,
			totalDiscountPercentageLevel2, totalDiscountPercentageLevel3,
			totalDiscountPercentageLevel4);
	}

	public static CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, java.math.BigDecimal shippingAmount,
			java.math.BigDecimal shippingDiscountAmount,
			java.math.BigDecimal shippingDiscountPercentageLevel1,
			java.math.BigDecimal shippingDiscountPercentageLevel2,
			java.math.BigDecimal shippingDiscountPercentageLevel3,
			java.math.BigDecimal shippingDiscountPercentageLevel4,
			java.math.BigDecimal shippingDiscountPercentageLevel1WithTaxAmount,
			java.math.BigDecimal shippingDiscountPercentageLevel2WithTaxAmount,
			java.math.BigDecimal shippingDiscountPercentageLevel3WithTaxAmount,
			java.math.BigDecimal shippingDiscountPercentageLevel4WithTaxAmount,
			java.math.BigDecimal shippingDiscountWithTaxAmount,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal subtotal,
			java.math.BigDecimal subtotalDiscountAmount,
			java.math.BigDecimal subtotalDiscountPercentageLevel1,
			java.math.BigDecimal subtotalDiscountPercentageLevel2,
			java.math.BigDecimal subtotalDiscountPercentageLevel3,
			java.math.BigDecimal subtotalDiscountPercentageLevel4,
			java.math.BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount,
			java.math.BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount,
			java.math.BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount,
			java.math.BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount,
			java.math.BigDecimal subtotalDiscountWithTaxAmount,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal totalDiscountAmount,
			java.math.BigDecimal totalDiscountPercentageLevel1,
			java.math.BigDecimal totalDiscountPercentageLevel2,
			java.math.BigDecimal totalDiscountPercentageLevel3,
			java.math.BigDecimal totalDiscountPercentageLevel4,
			java.math.BigDecimal totalDiscountPercentageLevel1WithTaxAmount,
			java.math.BigDecimal totalDiscountPercentageLevel2WithTaxAmount,
			java.math.BigDecimal totalDiscountPercentageLevel3WithTaxAmount,
			java.math.BigDecimal totalDiscountPercentageLevel4WithTaxAmount,
			java.math.BigDecimal totalDiscountWithTaxAmount,
			java.math.BigDecimal totalWithTaxAmount)
		throws PortalException {

		return getService().updateCommerceOrderPrices(
			commerceOrderId, shippingAmount, shippingDiscountAmount,
			shippingDiscountPercentageLevel1, shippingDiscountPercentageLevel2,
			shippingDiscountPercentageLevel3, shippingDiscountPercentageLevel4,
			shippingDiscountPercentageLevel1WithTaxAmount,
			shippingDiscountPercentageLevel2WithTaxAmount,
			shippingDiscountPercentageLevel3WithTaxAmount,
			shippingDiscountPercentageLevel4WithTaxAmount,
			shippingDiscountWithTaxAmount, shippingWithTaxAmount, subtotal,
			subtotalDiscountAmount, subtotalDiscountPercentageLevel1,
			subtotalDiscountPercentageLevel2, subtotalDiscountPercentageLevel3,
			subtotalDiscountPercentageLevel4,
			subtotalDiscountPercentageLevel1WithTaxAmount,
			subtotalDiscountPercentageLevel2WithTaxAmount,
			subtotalDiscountPercentageLevel3WithTaxAmount,
			subtotalDiscountPercentageLevel4WithTaxAmount,
			subtotalDiscountWithTaxAmount, subtotalWithTaxAmount, taxAmount,
			total, totalDiscountAmount, totalDiscountPercentageLevel1,
			totalDiscountPercentageLevel2, totalDiscountPercentageLevel3,
			totalDiscountPercentageLevel4,
			totalDiscountPercentageLevel1WithTaxAmount,
			totalDiscountPercentageLevel2WithTaxAmount,
			totalDiscountPercentageLevel3WithTaxAmount,
			totalDiscountPercentageLevel4WithTaxAmount,
			totalDiscountWithTaxAmount, totalWithTaxAmount);
	}

	public static CommerceOrder updateCommercePaymentMethodKey(
			long commerceOrderId, String commercePaymentMethodKey)
		throws PortalException {

		return getService().updateCommercePaymentMethodKey(
			commerceOrderId, commercePaymentMethodKey);
	}

	public static CommerceOrder updateCommerceShippingMethod(
			long commerceOrderId, long commerceShippingMethodId,
			String commerceShippingOptionName,
			com.liferay.commerce.context.CommerceContext commerceContext,
			java.util.Locale locale)
		throws PortalException {

		return getService().updateCommerceShippingMethod(
			commerceOrderId, commerceShippingMethodId,
			commerceShippingOptionName, commerceContext, locale);
	}

	public static CommerceOrder updateInfo(
			long commerceOrderId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateInfo(
			commerceOrderId, printedNote, requestedDeliveryDateMonth,
			requestedDeliveryDateDay, requestedDeliveryDateYear,
			requestedDeliveryDateHour, requestedDeliveryDateMinute,
			serviceContext);
	}

	public static CommerceOrder updateOrderDate(
			long commerceOrderId, int orderDateMonth, int orderDateDay,
			int orderDateYear, int orderDateHour, int orderDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateOrderDate(
			commerceOrderId, orderDateMonth, orderDateDay, orderDateYear,
			orderDateHour, orderDateMinute, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public static CommerceOrder updatePaymentStatus(
			long commerceOrderId, int paymentStatus)
		throws PortalException {

		return getService().updatePaymentStatus(commerceOrderId, paymentStatus);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public static CommerceOrder updatePaymentStatusAndTransactionId(
			long commerceOrderId, int paymentStatus, String transactionId)
		throws PortalException {

		return getService().updatePaymentStatusAndTransactionId(
			commerceOrderId, paymentStatus, transactionId);
	}

	public static CommerceOrder updatePrintedNote(
			long commerceOrderId, String printedNote)
		throws PortalException {

		return getService().updatePrintedNote(commerceOrderId, printedNote);
	}

	public static CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		return getService().updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	public static CommerceOrder updateShippingAddress(
			long commerceOrderId, long shippingAddressId)
		throws PortalException {

		return getService().updateShippingAddress(
			commerceOrderId, shippingAddressId);
	}

	public static CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber, serviceContext);
	}

	public static CommerceOrder updateTermsAndConditions(
			long commerceOrderId, long deliveryCommerceTermEntryId,
			long paymentCommerceTermEntryId, String languageId)
		throws PortalException {

		return getService().updateTermsAndConditions(
			commerceOrderId, deliveryCommerceTermEntryId,
			paymentCommerceTermEntryId, languageId);
	}

	public static CommerceOrderService getService() {
		return _service;
	}

	public static void setService(CommerceOrderService service) {
		_service = service;
	}

	private static volatile CommerceOrderService _service;

}