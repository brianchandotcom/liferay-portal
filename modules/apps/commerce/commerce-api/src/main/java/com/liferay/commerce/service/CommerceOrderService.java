/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceOrder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceOrderService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce order remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceOrderServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceOrder addCommerceOrder(
			long groupId, long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId)
		throws PortalException;

	public CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long groupId, long billingAddressId,
			long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, int orderDateMonth,
			int orderDateDay, int orderDateYear, int orderDateHour,
			int orderDateMinute, int orderStatus, int paymentStatus,
			String purchaseOrderNumber, BigDecimal shippingAmount,
			String shippingOptionName, BigDecimal shippingWithTaxAmount,
			BigDecimal subtotal, BigDecimal subtotalWithTaxAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal totalWithTaxAmount, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long groupId, long billingAddressId,
			long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, int orderStatus, int paymentStatus,
			String purchaseOrderNumber, BigDecimal shippingAmount,
			String shippingOptionName, BigDecimal shippingWithTaxAmount,
			BigDecimal subtotal, BigDecimal subtotalWithTaxAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal totalWithTaxAmount, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder applyCouponCode(
			long commerceOrderId, String couponCode,
			CommerceContext commerceContext)
		throws PortalException;

	public void deleteCommerceOrder(long commerceOrderId)
		throws PortalException;

	public CommerceOrder executeWorkflowTransition(
			long commerceOrderId, long workflowTaskId, String transitionName,
			String comment)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(
			long commerceAccountId, long groupId, int orderStatus)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(
			long commerceAccountId, long groupId, long userId, int orderStatus)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrderByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(
			long groupId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(
			long groupId, int[] orderStatuses)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(
			long groupId, int[] orderStatuses, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(
			long groupId, long commerceAccountId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrdersCount(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrdersCount(long groupId, long commerceAccountId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getPendingCommerceOrders(
			long groupId, long commerceAccountId, String keywords, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getPendingCommerceOrdersCount(long companyId, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPendingCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getPlacedCommerceOrders(
			long companyId, long groupId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getPlacedCommerceOrders(
			long groupId, long commerceAccountId, String keywords, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getPlacedCommerceOrdersCount(long companyId, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPlacedCommerceOrdersCount(
			long groupId, long commerceAccountId, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getUserCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getUserCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getUserOpenCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getUserPendingCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getUserPendingCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getUserPlacedCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getUserPlacedCommerceOrders(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getUserPlacedCommerceOrdersCount(
			long companyId, long groupId, String keywords)
		throws PortalException;

	public void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder recalculatePrice(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException;

	public CommerceOrder reorderCommerceOrder(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException;

	public CommerceOrder resetTermsAndConditions(
			long commerceOrderId, boolean deliveryCommerceTermEntry,
			boolean paymentCommerceTermEntry)
		throws PortalException;

	public CommerceOrder updateBillingAddress(
			long commerceOrderId, long billingAddressId)
		throws PortalException;

	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder updateCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException;

	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, String purchaseOrderNumber,
			BigDecimal shippingAmount, String shippingOptionName,
			BigDecimal shippingWithTaxAmount, BigDecimal subtotal,
			BigDecimal subtotalWithTaxAmount, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalWithTaxAmount, CommerceContext commerceContext)
		throws PortalException;

	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long commerceShippingMethodId,
			long shippingAddressId, String advanceStatus,
			String commercePaymentMethodKey, String purchaseOrderNumber,
			BigDecimal shippingAmount, String shippingOptionName,
			BigDecimal subtotal, BigDecimal total,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceOrder updateCommerceOrderExternalReferenceCode(
			String externalReferenceCode, long commerceOrderId)
		throws PortalException;

	public CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, BigDecimal shippingAmount,
			BigDecimal shippingDiscountAmount,
			BigDecimal shippingDiscountPercentageLevel1,
			BigDecimal shippingDiscountPercentageLevel2,
			BigDecimal shippingDiscountPercentageLevel3,
			BigDecimal shippingDiscountPercentageLevel4, BigDecimal subtotal,
			BigDecimal subtotalDiscountAmount,
			BigDecimal subtotalDiscountPercentageLevel1,
			BigDecimal subtotalDiscountPercentageLevel2,
			BigDecimal subtotalDiscountPercentageLevel3,
			BigDecimal subtotalDiscountPercentageLevel4, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalDiscountPercentageLevel1,
			BigDecimal totalDiscountPercentageLevel2,
			BigDecimal totalDiscountPercentageLevel3,
			BigDecimal totalDiscountPercentageLevel4)
		throws PortalException;

	public CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, BigDecimal shippingAmount,
			BigDecimal shippingDiscountAmount,
			BigDecimal shippingDiscountPercentageLevel1,
			BigDecimal shippingDiscountPercentageLevel2,
			BigDecimal shippingDiscountPercentageLevel3,
			BigDecimal shippingDiscountPercentageLevel4,
			BigDecimal shippingDiscountPercentageLevel1WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel2WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel3WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel4WithTaxAmount,
			BigDecimal shippingDiscountWithTaxAmount,
			BigDecimal shippingWithTaxAmount, BigDecimal subtotal,
			BigDecimal subtotalDiscountAmount,
			BigDecimal subtotalDiscountPercentageLevel1,
			BigDecimal subtotalDiscountPercentageLevel2,
			BigDecimal subtotalDiscountPercentageLevel3,
			BigDecimal subtotalDiscountPercentageLevel4,
			BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount,
			BigDecimal subtotalDiscountWithTaxAmount,
			BigDecimal subtotalWithTaxAmount, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalDiscountPercentageLevel1,
			BigDecimal totalDiscountPercentageLevel2,
			BigDecimal totalDiscountPercentageLevel3,
			BigDecimal totalDiscountPercentageLevel4,
			BigDecimal totalDiscountPercentageLevel1WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel2WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel3WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel4WithTaxAmount,
			BigDecimal totalDiscountWithTaxAmount,
			BigDecimal totalWithTaxAmount)
		throws PortalException;

	public CommerceOrder updateCommercePaymentMethodKey(
			long commerceOrderId, String commercePaymentMethodKey)
		throws PortalException;

	public CommerceOrder updateCommerceShippingMethod(
			long commerceOrderId, long commerceShippingMethodId,
			String commerceShippingOptionName, CommerceContext commerceContext,
			Locale locale)
		throws PortalException;

	public CommerceOrder updateInfo(
			long commerceOrderId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute, ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder updateOrderDate(
			long commerceOrderId, int orderDateMonth, int orderDateDay,
			int orderDateYear, int orderDateHour, int orderDateMinute,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public CommerceOrder updatePaymentStatus(
			long commerceOrderId, int paymentStatus)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement.
	 See CommercePaymentEngine.updateOrderPaymentStatus.
	 */
	@Deprecated
	public CommerceOrder updatePaymentStatusAndTransactionId(
			long commerceOrderId, int paymentStatus, String transactionId)
		throws PortalException;

	public CommerceOrder updatePrintedNote(
			long commerceOrderId, String printedNote)
		throws PortalException;

	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException;

	public CommerceOrder updateShippingAddress(
			long commerceOrderId, long shippingAddressId)
		throws PortalException;

	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder updateTermsAndConditions(
			long commerceOrderId, long deliveryCommerceTermEntryId,
			long paymentCommerceTermEntryId, String languageId)
		throws PortalException;

}