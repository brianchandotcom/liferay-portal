/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrder}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrder
 * @generated
 */
public class CommerceOrderWrapper
	extends BaseModelWrapper<CommerceOrder>
	implements CommerceOrder, ModelWrapper<CommerceOrder> {

	public CommerceOrderWrapper(CommerceOrder commerceOrder) {
		super(commerceOrder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("billingAddressId", getBillingAddressId());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceCurrencyId", getCommerceCurrencyId());
		attributes.put("commerceOrderTypeId", getCommerceOrderTypeId());
		attributes.put(
			"commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put(
			"deliveryCommerceTermEntryId", getDeliveryCommerceTermEntryId());
		attributes.put(
			"paymentCommerceTermEntryId", getPaymentCommerceTermEntryId());
		attributes.put("shippingAddressId", getShippingAddressId());
		attributes.put("advanceStatus", getAdvanceStatus());
		attributes.put(
			"commercePaymentMethodKey", getCommercePaymentMethodKey());
		attributes.put("couponCode", getCouponCode());
		attributes.put(
			"deliveryCommerceTermEntryDescription",
			getDeliveryCommerceTermEntryDescription());
		attributes.put(
			"deliveryCommerceTermEntryName",
			getDeliveryCommerceTermEntryName());
		attributes.put("lastPriceUpdateDate", getLastPriceUpdateDate());
		attributes.put("manuallyAdjusted", isManuallyAdjusted());
		attributes.put("orderDate", getOrderDate());
		attributes.put("orderStatus", getOrderStatus());
		attributes.put(
			"paymentCommerceTermEntryDescription",
			getPaymentCommerceTermEntryDescription());
		attributes.put(
			"paymentCommerceTermEntryName", getPaymentCommerceTermEntryName());
		attributes.put("paymentStatus", getPaymentStatus());
		attributes.put("printedNote", getPrintedNote());
		attributes.put("purchaseOrderNumber", getPurchaseOrderNumber());
		attributes.put("requestedDeliveryDate", getRequestedDeliveryDate());
		attributes.put("shippingAmount", getShippingAmount());
		attributes.put("shippingDiscountAmount", getShippingDiscountAmount());
		attributes.put(
			"shippingDiscountPercentageLevel1",
			getShippingDiscountPercentageLevel1());
		attributes.put(
			"shippingDiscountPercentageLevel2",
			getShippingDiscountPercentageLevel2());
		attributes.put(
			"shippingDiscountPercentageLevel3",
			getShippingDiscountPercentageLevel3());
		attributes.put(
			"shippingDiscountPercentageLevel4",
			getShippingDiscountPercentageLevel4());
		attributes.put(
			"shippingDiscountPercentageLevel1WithTaxAmount",
			getShippingDiscountPercentageLevel1WithTaxAmount());
		attributes.put(
			"shippingDiscountPercentageLevel2WithTaxAmount",
			getShippingDiscountPercentageLevel2WithTaxAmount());
		attributes.put(
			"shippingDiscountPercentageLevel3WithTaxAmount",
			getShippingDiscountPercentageLevel3WithTaxAmount());
		attributes.put(
			"shippingDiscountPercentageLevel4WithTaxAmount",
			getShippingDiscountPercentageLevel4WithTaxAmount());
		attributes.put(
			"shippingDiscountWithTaxAmount",
			getShippingDiscountWithTaxAmount());
		attributes.put("shippingOptionName", getShippingOptionName());
		attributes.put("shippingWithTaxAmount", getShippingWithTaxAmount());
		attributes.put("subtotal", getSubtotal());
		attributes.put("subtotalDiscountAmount", getSubtotalDiscountAmount());
		attributes.put(
			"subtotalDiscountPercentageLevel1",
			getSubtotalDiscountPercentageLevel1());
		attributes.put(
			"subtotalDiscountPercentageLevel2",
			getSubtotalDiscountPercentageLevel2());
		attributes.put(
			"subtotalDiscountPercentageLevel3",
			getSubtotalDiscountPercentageLevel3());
		attributes.put(
			"subtotalDiscountPercentageLevel4",
			getSubtotalDiscountPercentageLevel4());
		attributes.put(
			"subtotalDiscountPercentageLevel1WithTaxAmount",
			getSubtotalDiscountPercentageLevel1WithTaxAmount());
		attributes.put(
			"subtotalDiscountPercentageLevel2WithTaxAmount",
			getSubtotalDiscountPercentageLevel2WithTaxAmount());
		attributes.put(
			"subtotalDiscountPercentageLevel3WithTaxAmount",
			getSubtotalDiscountPercentageLevel3WithTaxAmount());
		attributes.put(
			"subtotalDiscountPercentageLevel4WithTaxAmount",
			getSubtotalDiscountPercentageLevel4WithTaxAmount());
		attributes.put(
			"subtotalDiscountWithTaxAmount",
			getSubtotalDiscountWithTaxAmount());
		attributes.put("subtotalWithTaxAmount", getSubtotalWithTaxAmount());
		attributes.put("taxAmount", getTaxAmount());
		attributes.put("total", getTotal());
		attributes.put("totalDiscountAmount", getTotalDiscountAmount());
		attributes.put(
			"totalDiscountPercentageLevel1",
			getTotalDiscountPercentageLevel1());
		attributes.put(
			"totalDiscountPercentageLevel2",
			getTotalDiscountPercentageLevel2());
		attributes.put(
			"totalDiscountPercentageLevel3",
			getTotalDiscountPercentageLevel3());
		attributes.put(
			"totalDiscountPercentageLevel4",
			getTotalDiscountPercentageLevel4());
		attributes.put(
			"totalDiscountPercentageLevel1WithTaxAmount",
			getTotalDiscountPercentageLevel1WithTaxAmount());
		attributes.put(
			"totalDiscountPercentageLevel2WithTaxAmount",
			getTotalDiscountPercentageLevel2WithTaxAmount());
		attributes.put(
			"totalDiscountPercentageLevel3WithTaxAmount",
			getTotalDiscountPercentageLevel3WithTaxAmount());
		attributes.put(
			"totalDiscountPercentageLevel4WithTaxAmount",
			getTotalDiscountPercentageLevel4WithTaxAmount());
		attributes.put(
			"totalDiscountWithTaxAmount", getTotalDiscountWithTaxAmount());
		attributes.put("totalWithTaxAmount", getTotalWithTaxAmount());
		attributes.put("transactionId", getTransactionId());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long billingAddressId = (Long)attributes.get("billingAddressId");

		if (billingAddressId != null) {
			setBillingAddressId(billingAddressId);
		}

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long commerceCurrencyId = (Long)attributes.get("commerceCurrencyId");

		if (commerceCurrencyId != null) {
			setCommerceCurrencyId(commerceCurrencyId);
		}

		Long commerceOrderTypeId = (Long)attributes.get("commerceOrderTypeId");

		if (commerceOrderTypeId != null) {
			setCommerceOrderTypeId(commerceOrderTypeId);
		}

		Long commerceShippingMethodId = (Long)attributes.get(
			"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		Long deliveryCommerceTermEntryId = (Long)attributes.get(
			"deliveryCommerceTermEntryId");

		if (deliveryCommerceTermEntryId != null) {
			setDeliveryCommerceTermEntryId(deliveryCommerceTermEntryId);
		}

		Long paymentCommerceTermEntryId = (Long)attributes.get(
			"paymentCommerceTermEntryId");

		if (paymentCommerceTermEntryId != null) {
			setPaymentCommerceTermEntryId(paymentCommerceTermEntryId);
		}

		Long shippingAddressId = (Long)attributes.get("shippingAddressId");

		if (shippingAddressId != null) {
			setShippingAddressId(shippingAddressId);
		}

		String advanceStatus = (String)attributes.get("advanceStatus");

		if (advanceStatus != null) {
			setAdvanceStatus(advanceStatus);
		}

		String commercePaymentMethodKey = (String)attributes.get(
			"commercePaymentMethodKey");

		if (commercePaymentMethodKey != null) {
			setCommercePaymentMethodKey(commercePaymentMethodKey);
		}

		String couponCode = (String)attributes.get("couponCode");

		if (couponCode != null) {
			setCouponCode(couponCode);
		}

		String deliveryCommerceTermEntryDescription = (String)attributes.get(
			"deliveryCommerceTermEntryDescription");

		if (deliveryCommerceTermEntryDescription != null) {
			setDeliveryCommerceTermEntryDescription(
				deliveryCommerceTermEntryDescription);
		}

		String deliveryCommerceTermEntryName = (String)attributes.get(
			"deliveryCommerceTermEntryName");

		if (deliveryCommerceTermEntryName != null) {
			setDeliveryCommerceTermEntryName(deliveryCommerceTermEntryName);
		}

		Date lastPriceUpdateDate = (Date)attributes.get("lastPriceUpdateDate");

		if (lastPriceUpdateDate != null) {
			setLastPriceUpdateDate(lastPriceUpdateDate);
		}

		Boolean manuallyAdjusted = (Boolean)attributes.get("manuallyAdjusted");

		if (manuallyAdjusted != null) {
			setManuallyAdjusted(manuallyAdjusted);
		}

		Date orderDate = (Date)attributes.get("orderDate");

		if (orderDate != null) {
			setOrderDate(orderDate);
		}

		Integer orderStatus = (Integer)attributes.get("orderStatus");

		if (orderStatus != null) {
			setOrderStatus(orderStatus);
		}

		String paymentCommerceTermEntryDescription = (String)attributes.get(
			"paymentCommerceTermEntryDescription");

		if (paymentCommerceTermEntryDescription != null) {
			setPaymentCommerceTermEntryDescription(
				paymentCommerceTermEntryDescription);
		}

		String paymentCommerceTermEntryName = (String)attributes.get(
			"paymentCommerceTermEntryName");

		if (paymentCommerceTermEntryName != null) {
			setPaymentCommerceTermEntryName(paymentCommerceTermEntryName);
		}

		Integer paymentStatus = (Integer)attributes.get("paymentStatus");

		if (paymentStatus != null) {
			setPaymentStatus(paymentStatus);
		}

		String printedNote = (String)attributes.get("printedNote");

		if (printedNote != null) {
			setPrintedNote(printedNote);
		}

		String purchaseOrderNumber = (String)attributes.get(
			"purchaseOrderNumber");

		if (purchaseOrderNumber != null) {
			setPurchaseOrderNumber(purchaseOrderNumber);
		}

		Date requestedDeliveryDate = (Date)attributes.get(
			"requestedDeliveryDate");

		if (requestedDeliveryDate != null) {
			setRequestedDeliveryDate(requestedDeliveryDate);
		}

		BigDecimal shippingAmount = (BigDecimal)attributes.get(
			"shippingAmount");

		if (shippingAmount != null) {
			setShippingAmount(shippingAmount);
		}

		BigDecimal shippingDiscountAmount = (BigDecimal)attributes.get(
			"shippingDiscountAmount");

		if (shippingDiscountAmount != null) {
			setShippingDiscountAmount(shippingDiscountAmount);
		}

		BigDecimal shippingDiscountPercentageLevel1 =
			(BigDecimal)attributes.get("shippingDiscountPercentageLevel1");

		if (shippingDiscountPercentageLevel1 != null) {
			setShippingDiscountPercentageLevel1(
				shippingDiscountPercentageLevel1);
		}

		BigDecimal shippingDiscountPercentageLevel2 =
			(BigDecimal)attributes.get("shippingDiscountPercentageLevel2");

		if (shippingDiscountPercentageLevel2 != null) {
			setShippingDiscountPercentageLevel2(
				shippingDiscountPercentageLevel2);
		}

		BigDecimal shippingDiscountPercentageLevel3 =
			(BigDecimal)attributes.get("shippingDiscountPercentageLevel3");

		if (shippingDiscountPercentageLevel3 != null) {
			setShippingDiscountPercentageLevel3(
				shippingDiscountPercentageLevel3);
		}

		BigDecimal shippingDiscountPercentageLevel4 =
			(BigDecimal)attributes.get("shippingDiscountPercentageLevel4");

		if (shippingDiscountPercentageLevel4 != null) {
			setShippingDiscountPercentageLevel4(
				shippingDiscountPercentageLevel4);
		}

		BigDecimal shippingDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)attributes.get(
				"shippingDiscountPercentageLevel1WithTaxAmount");

		if (shippingDiscountPercentageLevel1WithTaxAmount != null) {
			setShippingDiscountPercentageLevel1WithTaxAmount(
				shippingDiscountPercentageLevel1WithTaxAmount);
		}

		BigDecimal shippingDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)attributes.get(
				"shippingDiscountPercentageLevel2WithTaxAmount");

		if (shippingDiscountPercentageLevel2WithTaxAmount != null) {
			setShippingDiscountPercentageLevel2WithTaxAmount(
				shippingDiscountPercentageLevel2WithTaxAmount);
		}

		BigDecimal shippingDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)attributes.get(
				"shippingDiscountPercentageLevel3WithTaxAmount");

		if (shippingDiscountPercentageLevel3WithTaxAmount != null) {
			setShippingDiscountPercentageLevel3WithTaxAmount(
				shippingDiscountPercentageLevel3WithTaxAmount);
		}

		BigDecimal shippingDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)attributes.get(
				"shippingDiscountPercentageLevel4WithTaxAmount");

		if (shippingDiscountPercentageLevel4WithTaxAmount != null) {
			setShippingDiscountPercentageLevel4WithTaxAmount(
				shippingDiscountPercentageLevel4WithTaxAmount);
		}

		BigDecimal shippingDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"shippingDiscountWithTaxAmount");

		if (shippingDiscountWithTaxAmount != null) {
			setShippingDiscountWithTaxAmount(shippingDiscountWithTaxAmount);
		}

		String shippingOptionName = (String)attributes.get(
			"shippingOptionName");

		if (shippingOptionName != null) {
			setShippingOptionName(shippingOptionName);
		}

		BigDecimal shippingWithTaxAmount = (BigDecimal)attributes.get(
			"shippingWithTaxAmount");

		if (shippingWithTaxAmount != null) {
			setShippingWithTaxAmount(shippingWithTaxAmount);
		}

		BigDecimal subtotal = (BigDecimal)attributes.get("subtotal");

		if (subtotal != null) {
			setSubtotal(subtotal);
		}

		BigDecimal subtotalDiscountAmount = (BigDecimal)attributes.get(
			"subtotalDiscountAmount");

		if (subtotalDiscountAmount != null) {
			setSubtotalDiscountAmount(subtotalDiscountAmount);
		}

		BigDecimal subtotalDiscountPercentageLevel1 =
			(BigDecimal)attributes.get("subtotalDiscountPercentageLevel1");

		if (subtotalDiscountPercentageLevel1 != null) {
			setSubtotalDiscountPercentageLevel1(
				subtotalDiscountPercentageLevel1);
		}

		BigDecimal subtotalDiscountPercentageLevel2 =
			(BigDecimal)attributes.get("subtotalDiscountPercentageLevel2");

		if (subtotalDiscountPercentageLevel2 != null) {
			setSubtotalDiscountPercentageLevel2(
				subtotalDiscountPercentageLevel2);
		}

		BigDecimal subtotalDiscountPercentageLevel3 =
			(BigDecimal)attributes.get("subtotalDiscountPercentageLevel3");

		if (subtotalDiscountPercentageLevel3 != null) {
			setSubtotalDiscountPercentageLevel3(
				subtotalDiscountPercentageLevel3);
		}

		BigDecimal subtotalDiscountPercentageLevel4 =
			(BigDecimal)attributes.get("subtotalDiscountPercentageLevel4");

		if (subtotalDiscountPercentageLevel4 != null) {
			setSubtotalDiscountPercentageLevel4(
				subtotalDiscountPercentageLevel4);
		}

		BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)attributes.get(
				"subtotalDiscountPercentageLevel1WithTaxAmount");

		if (subtotalDiscountPercentageLevel1WithTaxAmount != null) {
			setSubtotalDiscountPercentageLevel1WithTaxAmount(
				subtotalDiscountPercentageLevel1WithTaxAmount);
		}

		BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)attributes.get(
				"subtotalDiscountPercentageLevel2WithTaxAmount");

		if (subtotalDiscountPercentageLevel2WithTaxAmount != null) {
			setSubtotalDiscountPercentageLevel2WithTaxAmount(
				subtotalDiscountPercentageLevel2WithTaxAmount);
		}

		BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)attributes.get(
				"subtotalDiscountPercentageLevel3WithTaxAmount");

		if (subtotalDiscountPercentageLevel3WithTaxAmount != null) {
			setSubtotalDiscountPercentageLevel3WithTaxAmount(
				subtotalDiscountPercentageLevel3WithTaxAmount);
		}

		BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)attributes.get(
				"subtotalDiscountPercentageLevel4WithTaxAmount");

		if (subtotalDiscountPercentageLevel4WithTaxAmount != null) {
			setSubtotalDiscountPercentageLevel4WithTaxAmount(
				subtotalDiscountPercentageLevel4WithTaxAmount);
		}

		BigDecimal subtotalDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"subtotalDiscountWithTaxAmount");

		if (subtotalDiscountWithTaxAmount != null) {
			setSubtotalDiscountWithTaxAmount(subtotalDiscountWithTaxAmount);
		}

		BigDecimal subtotalWithTaxAmount = (BigDecimal)attributes.get(
			"subtotalWithTaxAmount");

		if (subtotalWithTaxAmount != null) {
			setSubtotalWithTaxAmount(subtotalWithTaxAmount);
		}

		BigDecimal taxAmount = (BigDecimal)attributes.get("taxAmount");

		if (taxAmount != null) {
			setTaxAmount(taxAmount);
		}

		BigDecimal total = (BigDecimal)attributes.get("total");

		if (total != null) {
			setTotal(total);
		}

		BigDecimal totalDiscountAmount = (BigDecimal)attributes.get(
			"totalDiscountAmount");

		if (totalDiscountAmount != null) {
			setTotalDiscountAmount(totalDiscountAmount);
		}

		BigDecimal totalDiscountPercentageLevel1 = (BigDecimal)attributes.get(
			"totalDiscountPercentageLevel1");

		if (totalDiscountPercentageLevel1 != null) {
			setTotalDiscountPercentageLevel1(totalDiscountPercentageLevel1);
		}

		BigDecimal totalDiscountPercentageLevel2 = (BigDecimal)attributes.get(
			"totalDiscountPercentageLevel2");

		if (totalDiscountPercentageLevel2 != null) {
			setTotalDiscountPercentageLevel2(totalDiscountPercentageLevel2);
		}

		BigDecimal totalDiscountPercentageLevel3 = (BigDecimal)attributes.get(
			"totalDiscountPercentageLevel3");

		if (totalDiscountPercentageLevel3 != null) {
			setTotalDiscountPercentageLevel3(totalDiscountPercentageLevel3);
		}

		BigDecimal totalDiscountPercentageLevel4 = (BigDecimal)attributes.get(
			"totalDiscountPercentageLevel4");

		if (totalDiscountPercentageLevel4 != null) {
			setTotalDiscountPercentageLevel4(totalDiscountPercentageLevel4);
		}

		BigDecimal totalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)attributes.get(
				"totalDiscountPercentageLevel1WithTaxAmount");

		if (totalDiscountPercentageLevel1WithTaxAmount != null) {
			setTotalDiscountPercentageLevel1WithTaxAmount(
				totalDiscountPercentageLevel1WithTaxAmount);
		}

		BigDecimal totalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)attributes.get(
				"totalDiscountPercentageLevel2WithTaxAmount");

		if (totalDiscountPercentageLevel2WithTaxAmount != null) {
			setTotalDiscountPercentageLevel2WithTaxAmount(
				totalDiscountPercentageLevel2WithTaxAmount);
		}

		BigDecimal totalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)attributes.get(
				"totalDiscountPercentageLevel3WithTaxAmount");

		if (totalDiscountPercentageLevel3WithTaxAmount != null) {
			setTotalDiscountPercentageLevel3WithTaxAmount(
				totalDiscountPercentageLevel3WithTaxAmount);
		}

		BigDecimal totalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)attributes.get(
				"totalDiscountPercentageLevel4WithTaxAmount");

		if (totalDiscountPercentageLevel4WithTaxAmount != null) {
			setTotalDiscountPercentageLevel4WithTaxAmount(
				totalDiscountPercentageLevel4WithTaxAmount);
		}

		BigDecimal totalDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"totalDiscountWithTaxAmount");

		if (totalDiscountWithTaxAmount != null) {
			setTotalDiscountWithTaxAmount(totalDiscountWithTaxAmount);
		}

		BigDecimal totalWithTaxAmount = (BigDecimal)attributes.get(
			"totalWithTaxAmount");

		if (totalWithTaxAmount != null) {
			setTotalWithTaxAmount(totalWithTaxAmount);
		}

		String transactionId = (String)attributes.get("transactionId");

		if (transactionId != null) {
			setTransactionId(transactionId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public CommerceOrder cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.account.model.AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccountEntry();
	}

	/**
	 * Returns the advance status of this commerce order.
	 *
	 * @return the advance status of this commerce order
	 */
	@Override
	public String getAdvanceStatus() {
		return model.getAdvanceStatus();
	}

	@Override
	public CommerceAddress getBillingAddress()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getBillingAddress();
	}

	/**
	 * Returns the billing address ID of this commerce order.
	 *
	 * @return the billing address ID of this commerce order
	 */
	@Override
	public long getBillingAddressId() {
		return model.getBillingAddressId();
	}

	/**
	 * Returns the commerce account ID of this commerce order.
	 *
	 * @return the commerce account ID of this commerce order
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	@Override
	public String getCommerceAccountName()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccountName();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceCurrency();
	}

	/**
	 * Returns the commerce currency ID of this commerce order.
	 *
	 * @return the commerce currency ID of this commerce order
	 */
	@Override
	public long getCommerceCurrencyId() {
		return model.getCommerceCurrencyId();
	}

	/**
	 * Returns the commerce order ID of this commerce order.
	 *
	 * @return the commerce order ID of this commerce order
	 */
	@Override
	public long getCommerceOrderId() {
		return model.getCommerceOrderId();
	}

	@Override
	public java.util.List<CommerceOrderItem> getCommerceOrderItems() {
		return model.getCommerceOrderItems();
	}

	@Override
	public java.util.List<CommerceOrderItem> getCommerceOrderItems(
		long cpInstanceId) {

		return model.getCommerceOrderItems(cpInstanceId);
	}

	@Override
	public int getCommerceOrderItemsCount(long cpInstanceId) {
		return model.getCommerceOrderItemsCount(cpInstanceId);
	}

	/**
	 * Returns the commerce order type ID of this commerce order.
	 *
	 * @return the commerce order type ID of this commerce order
	 */
	@Override
	public long getCommerceOrderTypeId() {
		return model.getCommerceOrderTypeId();
	}

	/**
	 * Returns the commerce payment method key of this commerce order.
	 *
	 * @return the commerce payment method key of this commerce order
	 */
	@Override
	public String getCommercePaymentMethodKey() {
		return model.getCommercePaymentMethodKey();
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceShippingMethod();
	}

	/**
	 * Returns the commerce shipping method ID of this commerce order.
	 *
	 * @return the commerce shipping method ID of this commerce order
	 */
	@Override
	public long getCommerceShippingMethodId() {
		return model.getCommerceShippingMethodId();
	}

	/**
	 * Returns the company ID of this commerce order.
	 *
	 * @return the company ID of this commerce order
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the coupon code of this commerce order.
	 *
	 * @return the coupon code of this commerce order
	 */
	@Override
	public String getCouponCode() {
		return model.getCouponCode();
	}

	/**
	 * Returns the create date of this commerce order.
	 *
	 * @return the create date of this commerce order
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public java.util.List<Long> getCustomerCommerceOrderIds() {
		return model.getCustomerCommerceOrderIds();
	}

	@Override
	public int getCustomerCommerceOrderIdsCount() {
		return model.getCustomerCommerceOrderIdsCount();
	}

	/**
	 * Returns the delivery commerce term entry description of this commerce order.
	 *
	 * @return the delivery commerce term entry description of this commerce order
	 */
	@Override
	public String getDeliveryCommerceTermEntryDescription() {
		return model.getDeliveryCommerceTermEntryDescription();
	}

	/**
	 * Returns the delivery commerce term entry ID of this commerce order.
	 *
	 * @return the delivery commerce term entry ID of this commerce order
	 */
	@Override
	public long getDeliveryCommerceTermEntryId() {
		return model.getDeliveryCommerceTermEntryId();
	}

	/**
	 * Returns the delivery commerce term entry name of this commerce order.
	 *
	 * @return the delivery commerce term entry name of this commerce order
	 */
	@Override
	public String getDeliveryCommerceTermEntryName() {
		return model.getDeliveryCommerceTermEntryName();
	}

	/**
	 * Returns the external reference code of this commerce order.
	 *
	 * @return the external reference code of this commerce order
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce order.
	 *
	 * @return the group ID of this commerce order
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last price update date of this commerce order.
	 *
	 * @return the last price update date of this commerce order
	 */
	@Override
	public Date getLastPriceUpdateDate() {
		return model.getLastPriceUpdateDate();
	}

	/**
	 * Returns the manually adjusted of this commerce order.
	 *
	 * @return the manually adjusted of this commerce order
	 */
	@Override
	public boolean getManuallyAdjusted() {
		return model.getManuallyAdjusted();
	}

	/**
	 * Returns the modified date of this commerce order.
	 *
	 * @return the modified date of this commerce order
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce order.
	 *
	 * @return the mvcc version of this commerce order
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the order date of this commerce order.
	 *
	 * @return the order date of this commerce order
	 */
	@Override
	public Date getOrderDate() {
		return model.getOrderDate();
	}

	/**
	 * Returns the order status of this commerce order.
	 *
	 * @return the order status of this commerce order
	 */
	@Override
	public int getOrderStatus() {
		return model.getOrderStatus();
	}

	/**
	 * Returns the payment commerce term entry description of this commerce order.
	 *
	 * @return the payment commerce term entry description of this commerce order
	 */
	@Override
	public String getPaymentCommerceTermEntryDescription() {
		return model.getPaymentCommerceTermEntryDescription();
	}

	/**
	 * Returns the payment commerce term entry ID of this commerce order.
	 *
	 * @return the payment commerce term entry ID of this commerce order
	 */
	@Override
	public long getPaymentCommerceTermEntryId() {
		return model.getPaymentCommerceTermEntryId();
	}

	/**
	 * Returns the payment commerce term entry name of this commerce order.
	 *
	 * @return the payment commerce term entry name of this commerce order
	 */
	@Override
	public String getPaymentCommerceTermEntryName() {
		return model.getPaymentCommerceTermEntryName();
	}

	/**
	 * Returns the payment status of this commerce order.
	 *
	 * @return the payment status of this commerce order
	 */
	@Override
	public int getPaymentStatus() {
		return model.getPaymentStatus();
	}

	/**
	 * Returns the primary key of this commerce order.
	 *
	 * @return the primary key of this commerce order
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the printed note of this commerce order.
	 *
	 * @return the printed note of this commerce order
	 */
	@Override
	public String getPrintedNote() {
		return model.getPrintedNote();
	}

	/**
	 * Returns the purchase order number of this commerce order.
	 *
	 * @return the purchase order number of this commerce order
	 */
	@Override
	public String getPurchaseOrderNumber() {
		return model.getPurchaseOrderNumber();
	}

	/**
	 * Returns the requested delivery date of this commerce order.
	 *
	 * @return the requested delivery date of this commerce order
	 */
	@Override
	public Date getRequestedDeliveryDate() {
		return model.getRequestedDeliveryDate();
	}

	@Override
	public long getScopeGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getScopeGroupId();
	}

	@Override
	public CommerceAddress getShippingAddress()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getShippingAddress();
	}

	/**
	 * Returns the shipping address ID of this commerce order.
	 *
	 * @return the shipping address ID of this commerce order
	 */
	@Override
	public long getShippingAddressId() {
		return model.getShippingAddressId();
	}

	/**
	 * Returns the shipping amount of this commerce order.
	 *
	 * @return the shipping amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingAmount() {
		return model.getShippingAmount();
	}

	/**
	 * Returns the shipping discount amount of this commerce order.
	 *
	 * @return the shipping discount amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountAmount() {
		return model.getShippingDiscountAmount();
	}

	/**
	 * Returns the shipping discount percentage level1 of this commerce order.
	 *
	 * @return the shipping discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel1() {
		return model.getShippingDiscountPercentageLevel1();
	}

	/**
	 * Returns the shipping discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel1WithTaxAmount() {
		return model.getShippingDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level2 of this commerce order.
	 *
	 * @return the shipping discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel2() {
		return model.getShippingDiscountPercentageLevel2();
	}

	/**
	 * Returns the shipping discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel2WithTaxAmount() {
		return model.getShippingDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level3 of this commerce order.
	 *
	 * @return the shipping discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel3() {
		return model.getShippingDiscountPercentageLevel3();
	}

	/**
	 * Returns the shipping discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel3WithTaxAmount() {
		return model.getShippingDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level4 of this commerce order.
	 *
	 * @return the shipping discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel4() {
		return model.getShippingDiscountPercentageLevel4();
	}

	/**
	 * Returns the shipping discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel4WithTaxAmount() {
		return model.getShippingDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the shipping discount with tax amount of this commerce order.
	 *
	 * @return the shipping discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountWithTaxAmount() {
		return model.getShippingDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getShippingMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getShippingMoney();
	}

	/**
	 * Returns the shipping option name of this commerce order.
	 *
	 * @return the shipping option name of this commerce order
	 */
	@Override
	public String getShippingOptionName() {
		return model.getShippingOptionName();
	}

	/**
	 * Returns the shipping with tax amount of this commerce order.
	 *
	 * @return the shipping with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingWithTaxAmount() {
		return model.getShippingWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getShippingWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getShippingWithTaxAmountMoney();
	}

	/**
	 * Returns the status of this commerce order.
	 *
	 * @return the status of this commerce order
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce order.
	 *
	 * @return the status by user ID of this commerce order
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce order.
	 *
	 * @return the status by user name of this commerce order
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce order.
	 *
	 * @return the status by user uuid of this commerce order
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce order.
	 *
	 * @return the status date of this commerce order
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the subtotal of this commerce order.
	 *
	 * @return the subtotal of this commerce order
	 */
	@Override
	public BigDecimal getSubtotal() {
		return model.getSubtotal();
	}

	/**
	 * Returns the subtotal discount amount of this commerce order.
	 *
	 * @return the subtotal discount amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountAmount() {
		return model.getSubtotalDiscountAmount();
	}

	/**
	 * Returns the subtotal discount percentage level1 of this commerce order.
	 *
	 * @return the subtotal discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel1() {
		return model.getSubtotalDiscountPercentageLevel1();
	}

	/**
	 * Returns the subtotal discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel1WithTaxAmount() {
		return model.getSubtotalDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level2 of this commerce order.
	 *
	 * @return the subtotal discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel2() {
		return model.getSubtotalDiscountPercentageLevel2();
	}

	/**
	 * Returns the subtotal discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel2WithTaxAmount() {
		return model.getSubtotalDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level3 of this commerce order.
	 *
	 * @return the subtotal discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel3() {
		return model.getSubtotalDiscountPercentageLevel3();
	}

	/**
	 * Returns the subtotal discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel3WithTaxAmount() {
		return model.getSubtotalDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level4 of this commerce order.
	 *
	 * @return the subtotal discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel4() {
		return model.getSubtotalDiscountPercentageLevel4();
	}

	/**
	 * Returns the subtotal discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel4WithTaxAmount() {
		return model.getSubtotalDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount with tax amount of this commerce order.
	 *
	 * @return the subtotal discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountWithTaxAmount() {
		return model.getSubtotalDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getSubtotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSubtotalMoney();
	}

	/**
	 * Returns the subtotal with tax amount of this commerce order.
	 *
	 * @return the subtotal with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalWithTaxAmount() {
		return model.getSubtotalWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getSubtotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSubtotalWithTaxAmountMoney();
	}

	@Override
	public java.util.List<Long> getSupplierCommerceOrderIds() {
		return model.getSupplierCommerceOrderIds();
	}

	@Override
	public int getSupplierCommerceOrderIdsCount() {
		return model.getSupplierCommerceOrderIdsCount();
	}

	/**
	 * Returns the tax amount of this commerce order.
	 *
	 * @return the tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTaxAmount() {
		return model.getTaxAmount();
	}

	/**
	 * Returns the total of this commerce order.
	 *
	 * @return the total of this commerce order
	 */
	@Override
	public BigDecimal getTotal() {
		return model.getTotal();
	}

	/**
	 * Returns the total discount amount of this commerce order.
	 *
	 * @return the total discount amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountAmount() {
		return model.getTotalDiscountAmount();
	}

	/**
	 * Returns the total discount percentage level1 of this commerce order.
	 *
	 * @return the total discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel1() {
		return model.getTotalDiscountPercentageLevel1();
	}

	/**
	 * Returns the total discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel1WithTaxAmount() {
		return model.getTotalDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level2 of this commerce order.
	 *
	 * @return the total discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel2() {
		return model.getTotalDiscountPercentageLevel2();
	}

	/**
	 * Returns the total discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel2WithTaxAmount() {
		return model.getTotalDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level3 of this commerce order.
	 *
	 * @return the total discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel3() {
		return model.getTotalDiscountPercentageLevel3();
	}

	/**
	 * Returns the total discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel3WithTaxAmount() {
		return model.getTotalDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level4 of this commerce order.
	 *
	 * @return the total discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel4() {
		return model.getTotalDiscountPercentageLevel4();
	}

	/**
	 * Returns the total discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel4WithTaxAmount() {
		return model.getTotalDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the total discount with tax amount of this commerce order.
	 *
	 * @return the total discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountWithTaxAmount() {
		return model.getTotalDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getTotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTotalMoney();
	}

	/**
	 * Returns the total with tax amount of this commerce order.
	 *
	 * @return the total with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalWithTaxAmount() {
		return model.getTotalWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getTotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTotalWithTaxAmountMoney();
	}

	/**
	 * Returns the transaction ID of this commerce order.
	 *
	 * @return the transaction ID of this commerce order
	 */
	@Override
	public String getTransactionId() {
		return model.getTransactionId();
	}

	/**
	 * Returns the user ID of this commerce order.
	 *
	 * @return the user ID of this commerce order
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order.
	 *
	 * @return the user name of this commerce order
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order.
	 *
	 * @return the user uuid of this commerce order
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce order.
	 *
	 * @return the uuid of this commerce order
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce order is approved.
	 *
	 * @return <code>true</code> if this commerce order is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	@Override
	public boolean isB2B()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isB2B();
	}

	/**
	 * Returns <code>true</code> if this commerce order is denied.
	 *
	 * @return <code>true</code> if this commerce order is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce order is a draft.
	 *
	 * @return <code>true</code> if this commerce order is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	@Override
	public boolean isEmpty() {
		return model.isEmpty();
	}

	/**
	 * Returns <code>true</code> if this commerce order is expired.
	 *
	 * @return <code>true</code> if this commerce order is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	@Override
	public boolean isGuestOrder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isGuestOrder();
	}

	/**
	 * Returns <code>true</code> if this commerce order is inactive.
	 *
	 * @return <code>true</code> if this commerce order is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce order is incomplete.
	 *
	 * @return <code>true</code> if this commerce order is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce order is manually adjusted.
	 *
	 * @return <code>true</code> if this commerce order is manually adjusted; <code>false</code> otherwise
	 */
	@Override
	public boolean isManuallyAdjusted() {
		return model.isManuallyAdjusted();
	}

	@Override
	public boolean isOpen() {
		return model.isOpen();
	}

	/**
	 * Returns <code>true</code> if this commerce order is pending.
	 *
	 * @return <code>true</code> if this commerce order is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isQuote() {
		return model.isQuote();
	}

	/**
	 * Returns <code>true</code> if this commerce order is scheduled.
	 *
	 * @return <code>true</code> if this commerce order is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public boolean isSubscription() {
		return model.isSubscription();
	}

	@Override
	public boolean isSubscriptionOrder() {
		return model.isSubscriptionOrder();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the advance status of this commerce order.
	 *
	 * @param advanceStatus the advance status of this commerce order
	 */
	@Override
	public void setAdvanceStatus(String advanceStatus) {
		model.setAdvanceStatus(advanceStatus);
	}

	/**
	 * Sets the billing address ID of this commerce order.
	 *
	 * @param billingAddressId the billing address ID of this commerce order
	 */
	@Override
	public void setBillingAddressId(long billingAddressId) {
		model.setBillingAddressId(billingAddressId);
	}

	/**
	 * Sets the commerce account ID of this commerce order.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce order
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce currency ID of this commerce order.
	 *
	 * @param commerceCurrencyId the commerce currency ID of this commerce order
	 */
	@Override
	public void setCommerceCurrencyId(long commerceCurrencyId) {
		model.setCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	 * Sets the commerce order ID of this commerce order.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		model.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the commerce order type ID of this commerce order.
	 *
	 * @param commerceOrderTypeId the commerce order type ID of this commerce order
	 */
	@Override
	public void setCommerceOrderTypeId(long commerceOrderTypeId) {
		model.setCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Sets the commerce payment method key of this commerce order.
	 *
	 * @param commercePaymentMethodKey the commerce payment method key of this commerce order
	 */
	@Override
	public void setCommercePaymentMethodKey(String commercePaymentMethodKey) {
		model.setCommercePaymentMethodKey(commercePaymentMethodKey);
	}

	/**
	 * Sets the commerce shipping method ID of this commerce order.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID of this commerce order
	 */
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		model.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	 * Sets the company ID of this commerce order.
	 *
	 * @param companyId the company ID of this commerce order
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the coupon code of this commerce order.
	 *
	 * @param couponCode the coupon code of this commerce order
	 */
	@Override
	public void setCouponCode(String couponCode) {
		model.setCouponCode(couponCode);
	}

	/**
	 * Sets the create date of this commerce order.
	 *
	 * @param createDate the create date of this commerce order
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the delivery commerce term entry description of this commerce order.
	 *
	 * @param deliveryCommerceTermEntryDescription the delivery commerce term entry description of this commerce order
	 */
	@Override
	public void setDeliveryCommerceTermEntryDescription(
		String deliveryCommerceTermEntryDescription) {

		model.setDeliveryCommerceTermEntryDescription(
			deliveryCommerceTermEntryDescription);
	}

	/**
	 * Sets the delivery commerce term entry ID of this commerce order.
	 *
	 * @param deliveryCommerceTermEntryId the delivery commerce term entry ID of this commerce order
	 */
	@Override
	public void setDeliveryCommerceTermEntryId(
		long deliveryCommerceTermEntryId) {

		model.setDeliveryCommerceTermEntryId(deliveryCommerceTermEntryId);
	}

	/**
	 * Sets the delivery commerce term entry name of this commerce order.
	 *
	 * @param deliveryCommerceTermEntryName the delivery commerce term entry name of this commerce order
	 */
	@Override
	public void setDeliveryCommerceTermEntryName(
		String deliveryCommerceTermEntryName) {

		model.setDeliveryCommerceTermEntryName(deliveryCommerceTermEntryName);
	}

	/**
	 * Sets the external reference code of this commerce order.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce order.
	 *
	 * @param groupId the group ID of this commerce order
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last price update date of this commerce order.
	 *
	 * @param lastPriceUpdateDate the last price update date of this commerce order
	 */
	@Override
	public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
		model.setLastPriceUpdateDate(lastPriceUpdateDate);
	}

	/**
	 * Sets whether this commerce order is manually adjusted.
	 *
	 * @param manuallyAdjusted the manually adjusted of this commerce order
	 */
	@Override
	public void setManuallyAdjusted(boolean manuallyAdjusted) {
		model.setManuallyAdjusted(manuallyAdjusted);
	}

	/**
	 * Sets the modified date of this commerce order.
	 *
	 * @param modifiedDate the modified date of this commerce order
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce order.
	 *
	 * @param mvccVersion the mvcc version of this commerce order
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the order date of this commerce order.
	 *
	 * @param orderDate the order date of this commerce order
	 */
	@Override
	public void setOrderDate(Date orderDate) {
		model.setOrderDate(orderDate);
	}

	/**
	 * Sets the order status of this commerce order.
	 *
	 * @param orderStatus the order status of this commerce order
	 */
	@Override
	public void setOrderStatus(int orderStatus) {
		model.setOrderStatus(orderStatus);
	}

	/**
	 * Sets the payment commerce term entry description of this commerce order.
	 *
	 * @param paymentCommerceTermEntryDescription the payment commerce term entry description of this commerce order
	 */
	@Override
	public void setPaymentCommerceTermEntryDescription(
		String paymentCommerceTermEntryDescription) {

		model.setPaymentCommerceTermEntryDescription(
			paymentCommerceTermEntryDescription);
	}

	/**
	 * Sets the payment commerce term entry ID of this commerce order.
	 *
	 * @param paymentCommerceTermEntryId the payment commerce term entry ID of this commerce order
	 */
	@Override
	public void setPaymentCommerceTermEntryId(long paymentCommerceTermEntryId) {
		model.setPaymentCommerceTermEntryId(paymentCommerceTermEntryId);
	}

	/**
	 * Sets the payment commerce term entry name of this commerce order.
	 *
	 * @param paymentCommerceTermEntryName the payment commerce term entry name of this commerce order
	 */
	@Override
	public void setPaymentCommerceTermEntryName(
		String paymentCommerceTermEntryName) {

		model.setPaymentCommerceTermEntryName(paymentCommerceTermEntryName);
	}

	/**
	 * Sets the payment status of this commerce order.
	 *
	 * @param paymentStatus the payment status of this commerce order
	 */
	@Override
	public void setPaymentStatus(int paymentStatus) {
		model.setPaymentStatus(paymentStatus);
	}

	/**
	 * Sets the primary key of this commerce order.
	 *
	 * @param primaryKey the primary key of this commerce order
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the printed note of this commerce order.
	 *
	 * @param printedNote the printed note of this commerce order
	 */
	@Override
	public void setPrintedNote(String printedNote) {
		model.setPrintedNote(printedNote);
	}

	/**
	 * Sets the purchase order number of this commerce order.
	 *
	 * @param purchaseOrderNumber the purchase order number of this commerce order
	 */
	@Override
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		model.setPurchaseOrderNumber(purchaseOrderNumber);
	}

	/**
	 * Sets the requested delivery date of this commerce order.
	 *
	 * @param requestedDeliveryDate the requested delivery date of this commerce order
	 */
	@Override
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		model.setRequestedDeliveryDate(requestedDeliveryDate);
	}

	/**
	 * Sets the shipping address ID of this commerce order.
	 *
	 * @param shippingAddressId the shipping address ID of this commerce order
	 */
	@Override
	public void setShippingAddressId(long shippingAddressId) {
		model.setShippingAddressId(shippingAddressId);
	}

	/**
	 * Sets the shipping amount of this commerce order.
	 *
	 * @param shippingAmount the shipping amount of this commerce order
	 */
	@Override
	public void setShippingAmount(BigDecimal shippingAmount) {
		model.setShippingAmount(shippingAmount);
	}

	/**
	 * Sets the shipping discount amount of this commerce order.
	 *
	 * @param shippingDiscountAmount the shipping discount amount of this commerce order
	 */
	@Override
	public void setShippingDiscountAmount(BigDecimal shippingDiscountAmount) {
		model.setShippingDiscountAmount(shippingDiscountAmount);
	}

	/**
	 * Sets the shipping discount percentage level1 of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel1 the shipping discount percentage level1 of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel1(
		BigDecimal shippingDiscountPercentageLevel1) {

		model.setShippingDiscountPercentageLevel1(
			shippingDiscountPercentageLevel1);
	}

	/**
	 * Sets the shipping discount percentage level1 with tax amount of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel1WithTaxAmount the shipping discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel1WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel1WithTaxAmount) {

		model.setShippingDiscountPercentageLevel1WithTaxAmount(
			shippingDiscountPercentageLevel1WithTaxAmount);
	}

	/**
	 * Sets the shipping discount percentage level2 of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel2 the shipping discount percentage level2 of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel2(
		BigDecimal shippingDiscountPercentageLevel2) {

		model.setShippingDiscountPercentageLevel2(
			shippingDiscountPercentageLevel2);
	}

	/**
	 * Sets the shipping discount percentage level2 with tax amount of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel2WithTaxAmount the shipping discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel2WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel2WithTaxAmount) {

		model.setShippingDiscountPercentageLevel2WithTaxAmount(
			shippingDiscountPercentageLevel2WithTaxAmount);
	}

	/**
	 * Sets the shipping discount percentage level3 of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel3 the shipping discount percentage level3 of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel3(
		BigDecimal shippingDiscountPercentageLevel3) {

		model.setShippingDiscountPercentageLevel3(
			shippingDiscountPercentageLevel3);
	}

	/**
	 * Sets the shipping discount percentage level3 with tax amount of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel3WithTaxAmount the shipping discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel3WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel3WithTaxAmount) {

		model.setShippingDiscountPercentageLevel3WithTaxAmount(
			shippingDiscountPercentageLevel3WithTaxAmount);
	}

	/**
	 * Sets the shipping discount percentage level4 of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel4 the shipping discount percentage level4 of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel4(
		BigDecimal shippingDiscountPercentageLevel4) {

		model.setShippingDiscountPercentageLevel4(
			shippingDiscountPercentageLevel4);
	}

	/**
	 * Sets the shipping discount percentage level4 with tax amount of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel4WithTaxAmount the shipping discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel4WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel4WithTaxAmount) {

		model.setShippingDiscountPercentageLevel4WithTaxAmount(
			shippingDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setShippingDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		model.setShippingDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the shipping discount with tax amount of this commerce order.
	 *
	 * @param shippingDiscountWithTaxAmount the shipping discount with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountWithTaxAmount(
		BigDecimal shippingDiscountWithTaxAmount) {

		model.setShippingDiscountWithTaxAmount(shippingDiscountWithTaxAmount);
	}

	/**
	 * Sets the shipping option name of this commerce order.
	 *
	 * @param shippingOptionName the shipping option name of this commerce order
	 */
	@Override
	public void setShippingOptionName(String shippingOptionName) {
		model.setShippingOptionName(shippingOptionName);
	}

	/**
	 * Sets the shipping with tax amount of this commerce order.
	 *
	 * @param shippingWithTaxAmount the shipping with tax amount of this commerce order
	 */
	@Override
	public void setShippingWithTaxAmount(BigDecimal shippingWithTaxAmount) {
		model.setShippingWithTaxAmount(shippingWithTaxAmount);
	}

	/**
	 * Sets the status of this commerce order.
	 *
	 * @param status the status of this commerce order
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce order.
	 *
	 * @param statusByUserId the status by user ID of this commerce order
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce order.
	 *
	 * @param statusByUserName the status by user name of this commerce order
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce order.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce order
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce order.
	 *
	 * @param statusDate the status date of this commerce order
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the subtotal of this commerce order.
	 *
	 * @param subtotal the subtotal of this commerce order
	 */
	@Override
	public void setSubtotal(BigDecimal subtotal) {
		model.setSubtotal(subtotal);
	}

	/**
	 * Sets the subtotal discount amount of this commerce order.
	 *
	 * @param subtotalDiscountAmount the subtotal discount amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountAmount(BigDecimal subtotalDiscountAmount) {
		model.setSubtotalDiscountAmount(subtotalDiscountAmount);
	}

	/**
	 * Sets the subtotal discount percentage level1 of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel1 the subtotal discount percentage level1 of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel1(
		BigDecimal subtotalDiscountPercentageLevel1) {

		model.setSubtotalDiscountPercentageLevel1(
			subtotalDiscountPercentageLevel1);
	}

	/**
	 * Sets the subtotal discount percentage level1 with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel1WithTaxAmount the subtotal discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel1WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount) {

		model.setSubtotalDiscountPercentageLevel1WithTaxAmount(
			subtotalDiscountPercentageLevel1WithTaxAmount);
	}

	/**
	 * Sets the subtotal discount percentage level2 of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel2 the subtotal discount percentage level2 of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel2(
		BigDecimal subtotalDiscountPercentageLevel2) {

		model.setSubtotalDiscountPercentageLevel2(
			subtotalDiscountPercentageLevel2);
	}

	/**
	 * Sets the subtotal discount percentage level2 with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel2WithTaxAmount the subtotal discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel2WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount) {

		model.setSubtotalDiscountPercentageLevel2WithTaxAmount(
			subtotalDiscountPercentageLevel2WithTaxAmount);
	}

	/**
	 * Sets the subtotal discount percentage level3 of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel3 the subtotal discount percentage level3 of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel3(
		BigDecimal subtotalDiscountPercentageLevel3) {

		model.setSubtotalDiscountPercentageLevel3(
			subtotalDiscountPercentageLevel3);
	}

	/**
	 * Sets the subtotal discount percentage level3 with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel3WithTaxAmount the subtotal discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel3WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount) {

		model.setSubtotalDiscountPercentageLevel3WithTaxAmount(
			subtotalDiscountPercentageLevel3WithTaxAmount);
	}

	/**
	 * Sets the subtotal discount percentage level4 of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel4 the subtotal discount percentage level4 of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel4(
		BigDecimal subtotalDiscountPercentageLevel4) {

		model.setSubtotalDiscountPercentageLevel4(
			subtotalDiscountPercentageLevel4);
	}

	/**
	 * Sets the subtotal discount percentage level4 with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel4WithTaxAmount the subtotal discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel4WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount) {

		model.setSubtotalDiscountPercentageLevel4WithTaxAmount(
			subtotalDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setSubtotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		model.setSubtotalDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the subtotal discount with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountWithTaxAmount the subtotal discount with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountWithTaxAmount(
		BigDecimal subtotalDiscountWithTaxAmount) {

		model.setSubtotalDiscountWithTaxAmount(subtotalDiscountWithTaxAmount);
	}

	/**
	 * Sets the subtotal with tax amount of this commerce order.
	 *
	 * @param subtotalWithTaxAmount the subtotal with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalWithTaxAmount(BigDecimal subtotalWithTaxAmount) {
		model.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
	}

	/**
	 * Sets the tax amount of this commerce order.
	 *
	 * @param taxAmount the tax amount of this commerce order
	 */
	@Override
	public void setTaxAmount(BigDecimal taxAmount) {
		model.setTaxAmount(taxAmount);
	}

	/**
	 * Sets the total of this commerce order.
	 *
	 * @param total the total of this commerce order
	 */
	@Override
	public void setTotal(BigDecimal total) {
		model.setTotal(total);
	}

	/**
	 * Sets the total discount amount of this commerce order.
	 *
	 * @param totalDiscountAmount the total discount amount of this commerce order
	 */
	@Override
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		model.setTotalDiscountAmount(totalDiscountAmount);
	}

	/**
	 * Sets the total discount percentage level1 of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel1 the total discount percentage level1 of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel1(
		BigDecimal totalDiscountPercentageLevel1) {

		model.setTotalDiscountPercentageLevel1(totalDiscountPercentageLevel1);
	}

	/**
	 * Sets the total discount percentage level1 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel1WithTaxAmount the total discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel1WithTaxAmount) {

		model.setTotalDiscountPercentageLevel1WithTaxAmount(
			totalDiscountPercentageLevel1WithTaxAmount);
	}

	/**
	 * Sets the total discount percentage level2 of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel2 the total discount percentage level2 of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel2(
		BigDecimal totalDiscountPercentageLevel2) {

		model.setTotalDiscountPercentageLevel2(totalDiscountPercentageLevel2);
	}

	/**
	 * Sets the total discount percentage level2 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel2WithTaxAmount the total discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel2WithTaxAmount) {

		model.setTotalDiscountPercentageLevel2WithTaxAmount(
			totalDiscountPercentageLevel2WithTaxAmount);
	}

	/**
	 * Sets the total discount percentage level3 of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel3 the total discount percentage level3 of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel3(
		BigDecimal totalDiscountPercentageLevel3) {

		model.setTotalDiscountPercentageLevel3(totalDiscountPercentageLevel3);
	}

	/**
	 * Sets the total discount percentage level3 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel3WithTaxAmount the total discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel3WithTaxAmount) {

		model.setTotalDiscountPercentageLevel3WithTaxAmount(
			totalDiscountPercentageLevel3WithTaxAmount);
	}

	/**
	 * Sets the total discount percentage level4 of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel4 the total discount percentage level4 of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel4(
		BigDecimal totalDiscountPercentageLevel4) {

		model.setTotalDiscountPercentageLevel4(totalDiscountPercentageLevel4);
	}

	/**
	 * Sets the total discount percentage level4 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel4WithTaxAmount the total discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel4WithTaxAmount) {

		model.setTotalDiscountPercentageLevel4WithTaxAmount(
			totalDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setTotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		model.setTotalDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the total discount with tax amount of this commerce order.
	 *
	 * @param totalDiscountWithTaxAmount the total discount with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountWithTaxAmount(
		BigDecimal totalDiscountWithTaxAmount) {

		model.setTotalDiscountWithTaxAmount(totalDiscountWithTaxAmount);
	}

	/**
	 * Sets the total with tax amount of this commerce order.
	 *
	 * @param totalWithTaxAmount the total with tax amount of this commerce order
	 */
	@Override
	public void setTotalWithTaxAmount(BigDecimal totalWithTaxAmount) {
		model.setTotalWithTaxAmount(totalWithTaxAmount);
	}

	/**
	 * Sets the transaction ID of this commerce order.
	 *
	 * @param transactionId the transaction ID of this commerce order
	 */
	@Override
	public void setTransactionId(String transactionId) {
		model.setTransactionId(transactionId);
	}

	/**
	 * Sets the user ID of this commerce order.
	 *
	 * @param userId the user ID of this commerce order
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order.
	 *
	 * @param userName the user name of this commerce order
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order.
	 *
	 * @param userUuid the user uuid of this commerce order
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce order.
	 *
	 * @param uuid the uuid of this commerce order
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceOrderWrapper wrap(CommerceOrder commerceOrder) {
		return new CommerceOrderWrapper(commerceOrder);
	}

}