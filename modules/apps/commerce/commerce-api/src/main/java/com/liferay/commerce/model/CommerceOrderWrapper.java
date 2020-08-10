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

package com.liferay.commerce.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements CommerceOrder, ModelWrapper<CommerceOrder> {

	public CommerceOrderWrapper(CommerceOrder commerceOrder) {
		_commerceOrder = commerceOrder;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrder.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceCurrencyId", getCommerceCurrencyId());
		attributes.put("billingAddressId", getBillingAddressId());
		attributes.put("shippingAddressId", getShippingAddressId());
		attributes.put(
			"commercePaymentMethodKey", getCommercePaymentMethodKey());
		attributes.put("transactionId", getTransactionId());
		attributes.put(
			"commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("shippingOptionName", getShippingOptionName());
		attributes.put("purchaseOrderNumber", getPurchaseOrderNumber());
		attributes.put("couponCode", getCouponCode());
		attributes.put("lastPriceUpdateDate", getLastPriceUpdateDate());
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
		attributes.put("subtotalWithTaxAmount", getSubtotalWithTaxAmount());
		attributes.put(
			"subtotalDiscountWithTaxAmount",
			getSubtotalDiscountWithTaxAmount());
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
		attributes.put("shippingWithTaxAmount", getShippingWithTaxAmount());
		attributes.put(
			"shippingDiscountWithTaxAmount",
			getShippingDiscountWithTaxAmount());
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
		attributes.put("totalWithTaxAmount", getTotalWithTaxAmount());
		attributes.put(
			"totalDiscountWithTaxAmount", getTotalDiscountWithTaxAmount());
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
		attributes.put("advanceStatus", getAdvanceStatus());
		attributes.put("paymentStatus", getPaymentStatus());
		attributes.put("orderDate", getOrderDate());
		attributes.put("orderStatus", getOrderStatus());
		attributes.put("printedNote", getPrintedNote());
		attributes.put("requestedDeliveryDate", getRequestedDeliveryDate());
		attributes.put("manuallyAdjusted", isManuallyAdjusted());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long commerceCurrencyId = (Long)attributes.get("commerceCurrencyId");

		if (commerceCurrencyId != null) {
			setCommerceCurrencyId(commerceCurrencyId);
		}

		Long billingAddressId = (Long)attributes.get("billingAddressId");

		if (billingAddressId != null) {
			setBillingAddressId(billingAddressId);
		}

		Long shippingAddressId = (Long)attributes.get("shippingAddressId");

		if (shippingAddressId != null) {
			setShippingAddressId(shippingAddressId);
		}

		String commercePaymentMethodKey = (String)attributes.get(
			"commercePaymentMethodKey");

		if (commercePaymentMethodKey != null) {
			setCommercePaymentMethodKey(commercePaymentMethodKey);
		}

		String transactionId = (String)attributes.get("transactionId");

		if (transactionId != null) {
			setTransactionId(transactionId);
		}

		Long commerceShippingMethodId = (Long)attributes.get(
			"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		String shippingOptionName = (String)attributes.get(
			"shippingOptionName");

		if (shippingOptionName != null) {
			setShippingOptionName(shippingOptionName);
		}

		String purchaseOrderNumber = (String)attributes.get(
			"purchaseOrderNumber");

		if (purchaseOrderNumber != null) {
			setPurchaseOrderNumber(purchaseOrderNumber);
		}

		String couponCode = (String)attributes.get("couponCode");

		if (couponCode != null) {
			setCouponCode(couponCode);
		}

		Date lastPriceUpdateDate = (Date)attributes.get("lastPriceUpdateDate");

		if (lastPriceUpdateDate != null) {
			setLastPriceUpdateDate(lastPriceUpdateDate);
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

		BigDecimal subtotalWithTaxAmount = (BigDecimal)attributes.get(
			"subtotalWithTaxAmount");

		if (subtotalWithTaxAmount != null) {
			setSubtotalWithTaxAmount(subtotalWithTaxAmount);
		}

		BigDecimal subtotalDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"subtotalDiscountWithTaxAmount");

		if (subtotalDiscountWithTaxAmount != null) {
			setSubtotalDiscountWithTaxAmount(subtotalDiscountWithTaxAmount);
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

		BigDecimal shippingWithTaxAmount = (BigDecimal)attributes.get(
			"shippingWithTaxAmount");

		if (shippingWithTaxAmount != null) {
			setShippingWithTaxAmount(shippingWithTaxAmount);
		}

		BigDecimal shippingDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"shippingDiscountWithTaxAmount");

		if (shippingDiscountWithTaxAmount != null) {
			setShippingDiscountWithTaxAmount(shippingDiscountWithTaxAmount);
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

		BigDecimal totalWithTaxAmount = (BigDecimal)attributes.get(
			"totalWithTaxAmount");

		if (totalWithTaxAmount != null) {
			setTotalWithTaxAmount(totalWithTaxAmount);
		}

		BigDecimal totalDiscountWithTaxAmount = (BigDecimal)attributes.get(
			"totalDiscountWithTaxAmount");

		if (totalDiscountWithTaxAmount != null) {
			setTotalDiscountWithTaxAmount(totalDiscountWithTaxAmount);
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

		String advanceStatus = (String)attributes.get("advanceStatus");

		if (advanceStatus != null) {
			setAdvanceStatus(advanceStatus);
		}

		Integer paymentStatus = (Integer)attributes.get("paymentStatus");

		if (paymentStatus != null) {
			setPaymentStatus(paymentStatus);
		}

		Date orderDate = (Date)attributes.get("orderDate");

		if (orderDate != null) {
			setOrderDate(orderDate);
		}

		Integer orderStatus = (Integer)attributes.get("orderStatus");

		if (orderStatus != null) {
			setOrderStatus(orderStatus);
		}

		String printedNote = (String)attributes.get("printedNote");

		if (printedNote != null) {
			setPrintedNote(printedNote);
		}

		Date requestedDeliveryDate = (Date)attributes.get(
			"requestedDeliveryDate");

		if (requestedDeliveryDate != null) {
			setRequestedDeliveryDate(requestedDeliveryDate);
		}

		Boolean manuallyAdjusted = (Boolean)attributes.get("manuallyAdjusted");

		if (manuallyAdjusted != null) {
			setManuallyAdjusted(manuallyAdjusted);
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
	public Object clone() {
		return new CommerceOrderWrapper((CommerceOrder)_commerceOrder.clone());
	}

	@Override
	public int compareTo(CommerceOrder commerceOrder) {
		return _commerceOrder.compareTo(commerceOrder);
	}

	/**
	 * Returns the advance status of this commerce order.
	 *
	 * @return the advance status of this commerce order
	 */
	@Override
	public String getAdvanceStatus() {
		return _commerceOrder.getAdvanceStatus();
	}

	@Override
	public CommerceAddress getBillingAddress()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getBillingAddress();
	}

	/**
	 * Returns the billing address ID of this commerce order.
	 *
	 * @return the billing address ID of this commerce order
	 */
	@Override
	public long getBillingAddressId() {
		return _commerceOrder.getBillingAddressId();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getCommerceAccount();
	}

	/**
	 * Returns the commerce account ID of this commerce order.
	 *
	 * @return the commerce account ID of this commerce order
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceOrder.getCommerceAccountId();
	}

	@Override
	public String getCommerceAccountName()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getCommerceAccountName();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getCommerceCurrency();
	}

	/**
	 * Returns the commerce currency ID of this commerce order.
	 *
	 * @return the commerce currency ID of this commerce order
	 */
	@Override
	public long getCommerceCurrencyId() {
		return _commerceOrder.getCommerceCurrencyId();
	}

	/**
	 * Returns the commerce order ID of this commerce order.
	 *
	 * @return the commerce order ID of this commerce order
	 */
	@Override
	public long getCommerceOrderId() {
		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	public java.util.List<CommerceOrderItem> getCommerceOrderItems() {
		return _commerceOrder.getCommerceOrderItems();
	}

	@Override
	public java.util.List<CommerceOrderItem> getCommerceOrderItems(
		long cpInstanceId) {

		return _commerceOrder.getCommerceOrderItems(cpInstanceId);
	}

	@Override
	public int getCommerceOrderItemsCount(long cpInstanceId) {
		return _commerceOrder.getCommerceOrderItemsCount(cpInstanceId);
	}

	/**
	 * Returns the commerce payment method key of this commerce order.
	 *
	 * @return the commerce payment method key of this commerce order
	 */
	@Override
	public String getCommercePaymentMethodKey() {
		return _commerceOrder.getCommercePaymentMethodKey();
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getCommerceShippingMethod();
	}

	/**
	 * Returns the commerce shipping method ID of this commerce order.
	 *
	 * @return the commerce shipping method ID of this commerce order
	 */
	@Override
	public long getCommerceShippingMethodId() {
		return _commerceOrder.getCommerceShippingMethodId();
	}

	/**
	 * Returns the company ID of this commerce order.
	 *
	 * @return the company ID of this commerce order
	 */
	@Override
	public long getCompanyId() {
		return _commerceOrder.getCompanyId();
	}

	/**
	 * Returns the coupon code of this commerce order.
	 *
	 * @return the coupon code of this commerce order
	 */
	@Override
	public String getCouponCode() {
		return _commerceOrder.getCouponCode();
	}

	/**
	 * Returns the create date of this commerce order.
	 *
	 * @return the create date of this commerce order
	 */
	@Override
	public Date getCreateDate() {
		return _commerceOrder.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceOrder.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce order.
	 *
	 * @return the external reference code of this commerce order
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceOrder.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce order.
	 *
	 * @return the group ID of this commerce order
	 */
	@Override
	public long getGroupId() {
		return _commerceOrder.getGroupId();
	}

	/**
	 * Returns the last price update date of this commerce order.
	 *
	 * @return the last price update date of this commerce order
	 */
	@Override
	public Date getLastPriceUpdateDate() {
		return _commerceOrder.getLastPriceUpdateDate();
	}

	/**
	 * Returns the manually adjusted of this commerce order.
	 *
	 * @return the manually adjusted of this commerce order
	 */
	@Override
	public boolean getManuallyAdjusted() {
		return _commerceOrder.getManuallyAdjusted();
	}

	/**
	 * Returns the modified date of this commerce order.
	 *
	 * @return the modified date of this commerce order
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceOrder.getModifiedDate();
	}

	/**
	 * Returns the order date of this commerce order.
	 *
	 * @return the order date of this commerce order
	 */
	@Override
	public Date getOrderDate() {
		return _commerceOrder.getOrderDate();
	}

	/**
	 * Returns the order status of this commerce order.
	 *
	 * @return the order status of this commerce order
	 */
	@Override
	public int getOrderStatus() {
		return _commerceOrder.getOrderStatus();
	}

	/**
	 * Returns the payment status of this commerce order.
	 *
	 * @return the payment status of this commerce order
	 */
	@Override
	public int getPaymentStatus() {
		return _commerceOrder.getPaymentStatus();
	}

	/**
	 * Returns the primary key of this commerce order.
	 *
	 * @return the primary key of this commerce order
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceOrder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrder.getPrimaryKeyObj();
	}

	/**
	 * Returns the printed note of this commerce order.
	 *
	 * @return the printed note of this commerce order
	 */
	@Override
	public String getPrintedNote() {
		return _commerceOrder.getPrintedNote();
	}

	/**
	 * Returns the purchase order number of this commerce order.
	 *
	 * @return the purchase order number of this commerce order
	 */
	@Override
	public String getPurchaseOrderNumber() {
		return _commerceOrder.getPurchaseOrderNumber();
	}

	/**
	 * Returns the requested delivery date of this commerce order.
	 *
	 * @return the requested delivery date of this commerce order
	 */
	@Override
	public Date getRequestedDeliveryDate() {
		return _commerceOrder.getRequestedDeliveryDate();
	}

	@Override
	public long getScopeGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getScopeGroupId();
	}

	@Override
	public CommerceAddress getShippingAddress()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getShippingAddress();
	}

	/**
	 * Returns the shipping address ID of this commerce order.
	 *
	 * @return the shipping address ID of this commerce order
	 */
	@Override
	public long getShippingAddressId() {
		return _commerceOrder.getShippingAddressId();
	}

	/**
	 * Returns the shipping amount of this commerce order.
	 *
	 * @return the shipping amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingAmount() {
		return _commerceOrder.getShippingAmount();
	}

	/**
	 * Returns the shipping discount amount of this commerce order.
	 *
	 * @return the shipping discount amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountAmount() {
		return _commerceOrder.getShippingDiscountAmount();
	}

	/**
	 * Returns the shipping discount percentage level1 of this commerce order.
	 *
	 * @return the shipping discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel1() {
		return _commerceOrder.getShippingDiscountPercentageLevel1();
	}

	/**
	 * Returns the shipping discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel1WithTaxAmount() {
		return _commerceOrder.
			getShippingDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level2 of this commerce order.
	 *
	 * @return the shipping discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel2() {
		return _commerceOrder.getShippingDiscountPercentageLevel2();
	}

	/**
	 * Returns the shipping discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel2WithTaxAmount() {
		return _commerceOrder.
			getShippingDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level3 of this commerce order.
	 *
	 * @return the shipping discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel3() {
		return _commerceOrder.getShippingDiscountPercentageLevel3();
	}

	/**
	 * Returns the shipping discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel3WithTaxAmount() {
		return _commerceOrder.
			getShippingDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the shipping discount percentage level4 of this commerce order.
	 *
	 * @return the shipping discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel4() {
		return _commerceOrder.getShippingDiscountPercentageLevel4();
	}

	/**
	 * Returns the shipping discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the shipping discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountPercentageLevel4WithTaxAmount() {
		return _commerceOrder.
			getShippingDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the shipping discount with tax amount of this commerce order.
	 *
	 * @return the shipping discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingDiscountWithTaxAmount() {
		return _commerceOrder.getShippingDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getShippingMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getShippingMoney();
	}

	/**
	 * Returns the shipping option name of this commerce order.
	 *
	 * @return the shipping option name of this commerce order
	 */
	@Override
	public String getShippingOptionName() {
		return _commerceOrder.getShippingOptionName();
	}

	/**
	 * Returns the shipping with tax amount of this commerce order.
	 *
	 * @return the shipping with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getShippingWithTaxAmount() {
		return _commerceOrder.getShippingWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getShippingWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getShippingWithTaxAmountMoney();
	}

	/**
	 * Returns the status of this commerce order.
	 *
	 * @return the status of this commerce order
	 */
	@Override
	public int getStatus() {
		return _commerceOrder.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce order.
	 *
	 * @return the status by user ID of this commerce order
	 */
	@Override
	public long getStatusByUserId() {
		return _commerceOrder.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce order.
	 *
	 * @return the status by user name of this commerce order
	 */
	@Override
	public String getStatusByUserName() {
		return _commerceOrder.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce order.
	 *
	 * @return the status by user uuid of this commerce order
	 */
	@Override
	public String getStatusByUserUuid() {
		return _commerceOrder.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce order.
	 *
	 * @return the status date of this commerce order
	 */
	@Override
	public Date getStatusDate() {
		return _commerceOrder.getStatusDate();
	}

	/**
	 * Returns the subtotal of this commerce order.
	 *
	 * @return the subtotal of this commerce order
	 */
	@Override
	public BigDecimal getSubtotal() {
		return _commerceOrder.getSubtotal();
	}

	/**
	 * Returns the subtotal discount amount of this commerce order.
	 *
	 * @return the subtotal discount amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountAmount() {
		return _commerceOrder.getSubtotalDiscountAmount();
	}

	/**
	 * Returns the subtotal discount percentage level1 of this commerce order.
	 *
	 * @return the subtotal discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel1() {
		return _commerceOrder.getSubtotalDiscountPercentageLevel1();
	}

	/**
	 * Returns the subtotal discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel1WithTaxAmount() {
		return _commerceOrder.
			getSubtotalDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level2 of this commerce order.
	 *
	 * @return the subtotal discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel2() {
		return _commerceOrder.getSubtotalDiscountPercentageLevel2();
	}

	/**
	 * Returns the subtotal discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel2WithTaxAmount() {
		return _commerceOrder.
			getSubtotalDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level3 of this commerce order.
	 *
	 * @return the subtotal discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel3() {
		return _commerceOrder.getSubtotalDiscountPercentageLevel3();
	}

	/**
	 * Returns the subtotal discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel3WithTaxAmount() {
		return _commerceOrder.
			getSubtotalDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount percentage level4 of this commerce order.
	 *
	 * @return the subtotal discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel4() {
		return _commerceOrder.getSubtotalDiscountPercentageLevel4();
	}

	/**
	 * Returns the subtotal discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the subtotal discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountPercentageLevel4WithTaxAmount() {
		return _commerceOrder.
			getSubtotalDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the subtotal discount with tax amount of this commerce order.
	 *
	 * @return the subtotal discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalDiscountWithTaxAmount() {
		return _commerceOrder.getSubtotalDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getSubtotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getSubtotalMoney();
	}

	/**
	 * Returns the subtotal with tax amount of this commerce order.
	 *
	 * @return the subtotal with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getSubtotalWithTaxAmount() {
		return _commerceOrder.getSubtotalWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getSubtotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getSubtotalWithTaxAmountMoney();
	}

	/**
	 * Returns the tax amount of this commerce order.
	 *
	 * @return the tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTaxAmount() {
		return _commerceOrder.getTaxAmount();
	}

	/**
	 * Returns the total of this commerce order.
	 *
	 * @return the total of this commerce order
	 */
	@Override
	public BigDecimal getTotal() {
		return _commerceOrder.getTotal();
	}

	/**
	 * Returns the total discount amount of this commerce order.
	 *
	 * @return the total discount amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountAmount() {
		return _commerceOrder.getTotalDiscountAmount();
	}

	/**
	 * Returns the total discount percentage level1 of this commerce order.
	 *
	 * @return the total discount percentage level1 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel1() {
		return _commerceOrder.getTotalDiscountPercentageLevel1();
	}

	/**
	 * Returns the total discount percentage level1 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel1WithTaxAmount() {
		return _commerceOrder.getTotalDiscountPercentageLevel1WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level2 of this commerce order.
	 *
	 * @return the total discount percentage level2 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel2() {
		return _commerceOrder.getTotalDiscountPercentageLevel2();
	}

	/**
	 * Returns the total discount percentage level2 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel2WithTaxAmount() {
		return _commerceOrder.getTotalDiscountPercentageLevel2WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level3 of this commerce order.
	 *
	 * @return the total discount percentage level3 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel3() {
		return _commerceOrder.getTotalDiscountPercentageLevel3();
	}

	/**
	 * Returns the total discount percentage level3 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel3WithTaxAmount() {
		return _commerceOrder.getTotalDiscountPercentageLevel3WithTaxAmount();
	}

	/**
	 * Returns the total discount percentage level4 of this commerce order.
	 *
	 * @return the total discount percentage level4 of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel4() {
		return _commerceOrder.getTotalDiscountPercentageLevel4();
	}

	/**
	 * Returns the total discount percentage level4 with tax amount of this commerce order.
	 *
	 * @return the total discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountPercentageLevel4WithTaxAmount() {
		return _commerceOrder.getTotalDiscountPercentageLevel4WithTaxAmount();
	}

	/**
	 * Returns the total discount with tax amount of this commerce order.
	 *
	 * @return the total discount with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalDiscountWithTaxAmount() {
		return _commerceOrder.getTotalDiscountWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getTotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getTotalMoney();
	}

	/**
	 * Returns the total with tax amount of this commerce order.
	 *
	 * @return the total with tax amount of this commerce order
	 */
	@Override
	public BigDecimal getTotalWithTaxAmount() {
		return _commerceOrder.getTotalWithTaxAmount();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney
			getTotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.getTotalWithTaxAmountMoney();
	}

	/**
	 * Returns the transaction ID of this commerce order.
	 *
	 * @return the transaction ID of this commerce order
	 */
	@Override
	public String getTransactionId() {
		return _commerceOrder.getTransactionId();
	}

	/**
	 * Returns the user ID of this commerce order.
	 *
	 * @return the user ID of this commerce order
	 */
	@Override
	public long getUserId() {
		return _commerceOrder.getUserId();
	}

	/**
	 * Returns the user name of this commerce order.
	 *
	 * @return the user name of this commerce order
	 */
	@Override
	public String getUserName() {
		return _commerceOrder.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order.
	 *
	 * @return the user uuid of this commerce order
	 */
	@Override
	public String getUserUuid() {
		return _commerceOrder.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce order.
	 *
	 * @return the uuid of this commerce order
	 */
	@Override
	public String getUuid() {
		return _commerceOrder.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrder.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce order is approved.
	 *
	 * @return <code>true</code> if this commerce order is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return _commerceOrder.isApproved();
	}

	@Override
	public boolean isB2B()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.isB2B();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrder.isCachedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce order is denied.
	 *
	 * @return <code>true</code> if this commerce order is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return _commerceOrder.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce order is a draft.
	 *
	 * @return <code>true</code> if this commerce order is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return _commerceOrder.isDraft();
	}

	@Override
	public boolean isEmpty() {
		return _commerceOrder.isEmpty();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrder.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce order is expired.
	 *
	 * @return <code>true</code> if this commerce order is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return _commerceOrder.isExpired();
	}

	@Override
	public boolean isGuestOrder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrder.isGuestOrder();
	}

	/**
	 * Returns <code>true</code> if this commerce order is inactive.
	 *
	 * @return <code>true</code> if this commerce order is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return _commerceOrder.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce order is incomplete.
	 *
	 * @return <code>true</code> if this commerce order is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return _commerceOrder.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce order is manually adjusted.
	 *
	 * @return <code>true</code> if this commerce order is manually adjusted; <code>false</code> otherwise
	 */
	@Override
	public boolean isManuallyAdjusted() {
		return _commerceOrder.isManuallyAdjusted();
	}

	@Override
	public boolean isNew() {
		return _commerceOrder.isNew();
	}

	@Override
	public boolean isOpen() {
		return _commerceOrder.isOpen();
	}

	/**
	 * Returns <code>true</code> if this commerce order is pending.
	 *
	 * @return <code>true</code> if this commerce order is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return _commerceOrder.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce order is scheduled.
	 *
	 * @return <code>true</code> if this commerce order is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return _commerceOrder.isScheduled();
	}

	@Override
	public boolean isSubscription() {
		return _commerceOrder.isSubscription();
	}

	@Override
	public boolean isSubscriptionOrder() {
		return _commerceOrder.isSubscriptionOrder();
	}

	@Override
	public void persist() {
		_commerceOrder.persist();
	}

	/**
	 * Sets the advance status of this commerce order.
	 *
	 * @param advanceStatus the advance status of this commerce order
	 */
	@Override
	public void setAdvanceStatus(String advanceStatus) {
		_commerceOrder.setAdvanceStatus(advanceStatus);
	}

	/**
	 * Sets the billing address ID of this commerce order.
	 *
	 * @param billingAddressId the billing address ID of this commerce order
	 */
	@Override
	public void setBillingAddressId(long billingAddressId) {
		_commerceOrder.setBillingAddressId(billingAddressId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceOrder.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce order.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce order
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceOrder.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce currency ID of this commerce order.
	 *
	 * @param commerceCurrencyId the commerce currency ID of this commerce order
	 */
	@Override
	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commerceOrder.setCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	 * Sets the commerce order ID of this commerce order.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrder.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the commerce payment method key of this commerce order.
	 *
	 * @param commercePaymentMethodKey the commerce payment method key of this commerce order
	 */
	@Override
	public void setCommercePaymentMethodKey(String commercePaymentMethodKey) {
		_commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);
	}

	/**
	 * Sets the commerce shipping method ID of this commerce order.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID of this commerce order
	 */
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	 * Sets the company ID of this commerce order.
	 *
	 * @param companyId the company ID of this commerce order
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceOrder.setCompanyId(companyId);
	}

	/**
	 * Sets the coupon code of this commerce order.
	 *
	 * @param couponCode the coupon code of this commerce order
	 */
	@Override
	public void setCouponCode(String couponCode) {
		_commerceOrder.setCouponCode(couponCode);
	}

	/**
	 * Sets the create date of this commerce order.
	 *
	 * @param createDate the create date of this commerce order
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceOrder.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceOrder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceOrder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceOrder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce order.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceOrder.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce order.
	 *
	 * @param groupId the group ID of this commerce order
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceOrder.setGroupId(groupId);
	}

	/**
	 * Sets the last price update date of this commerce order.
	 *
	 * @param lastPriceUpdateDate the last price update date of this commerce order
	 */
	@Override
	public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
		_commerceOrder.setLastPriceUpdateDate(lastPriceUpdateDate);
	}

	/**
	 * Sets whether this commerce order is manually adjusted.
	 *
	 * @param manuallyAdjusted the manually adjusted of this commerce order
	 */
	@Override
	public void setManuallyAdjusted(boolean manuallyAdjusted) {
		_commerceOrder.setManuallyAdjusted(manuallyAdjusted);
	}

	/**
	 * Sets the modified date of this commerce order.
	 *
	 * @param modifiedDate the modified date of this commerce order
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceOrder.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceOrder.setNew(n);
	}

	/**
	 * Sets the order date of this commerce order.
	 *
	 * @param orderDate the order date of this commerce order
	 */
	@Override
	public void setOrderDate(Date orderDate) {
		_commerceOrder.setOrderDate(orderDate);
	}

	/**
	 * Sets the order status of this commerce order.
	 *
	 * @param orderStatus the order status of this commerce order
	 */
	@Override
	public void setOrderStatus(int orderStatus) {
		_commerceOrder.setOrderStatus(orderStatus);
	}

	/**
	 * Sets the payment status of this commerce order.
	 *
	 * @param paymentStatus the payment status of this commerce order
	 */
	@Override
	public void setPaymentStatus(int paymentStatus) {
		_commerceOrder.setPaymentStatus(paymentStatus);
	}

	/**
	 * Sets the primary key of this commerce order.
	 *
	 * @param primaryKey the primary key of this commerce order
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceOrder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceOrder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the printed note of this commerce order.
	 *
	 * @param printedNote the printed note of this commerce order
	 */
	@Override
	public void setPrintedNote(String printedNote) {
		_commerceOrder.setPrintedNote(printedNote);
	}

	/**
	 * Sets the purchase order number of this commerce order.
	 *
	 * @param purchaseOrderNumber the purchase order number of this commerce order
	 */
	@Override
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		_commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
	}

	/**
	 * Sets the requested delivery date of this commerce order.
	 *
	 * @param requestedDeliveryDate the requested delivery date of this commerce order
	 */
	@Override
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		_commerceOrder.setRequestedDeliveryDate(requestedDeliveryDate);
	}

	/**
	 * Sets the shipping address ID of this commerce order.
	 *
	 * @param shippingAddressId the shipping address ID of this commerce order
	 */
	@Override
	public void setShippingAddressId(long shippingAddressId) {
		_commerceOrder.setShippingAddressId(shippingAddressId);
	}

	/**
	 * Sets the shipping amount of this commerce order.
	 *
	 * @param shippingAmount the shipping amount of this commerce order
	 */
	@Override
	public void setShippingAmount(BigDecimal shippingAmount) {
		_commerceOrder.setShippingAmount(shippingAmount);
	}

	/**
	 * Sets the shipping discount amount of this commerce order.
	 *
	 * @param shippingDiscountAmount the shipping discount amount of this commerce order
	 */
	@Override
	public void setShippingDiscountAmount(BigDecimal shippingDiscountAmount) {
		_commerceOrder.setShippingDiscountAmount(shippingDiscountAmount);
	}

	/**
	 * Sets the shipping discount percentage level1 of this commerce order.
	 *
	 * @param shippingDiscountPercentageLevel1 the shipping discount percentage level1 of this commerce order
	 */
	@Override
	public void setShippingDiscountPercentageLevel1(
		BigDecimal shippingDiscountPercentageLevel1) {

		_commerceOrder.setShippingDiscountPercentageLevel1(
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

		_commerceOrder.setShippingDiscountPercentageLevel1WithTaxAmount(
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

		_commerceOrder.setShippingDiscountPercentageLevel2(
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

		_commerceOrder.setShippingDiscountPercentageLevel2WithTaxAmount(
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

		_commerceOrder.setShippingDiscountPercentageLevel3(
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

		_commerceOrder.setShippingDiscountPercentageLevel3WithTaxAmount(
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

		_commerceOrder.setShippingDiscountPercentageLevel4(
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

		_commerceOrder.setShippingDiscountPercentageLevel4WithTaxAmount(
			shippingDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setShippingDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		_commerceOrder.setShippingDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the shipping discount with tax amount of this commerce order.
	 *
	 * @param shippingDiscountWithTaxAmount the shipping discount with tax amount of this commerce order
	 */
	@Override
	public void setShippingDiscountWithTaxAmount(
		BigDecimal shippingDiscountWithTaxAmount) {

		_commerceOrder.setShippingDiscountWithTaxAmount(
			shippingDiscountWithTaxAmount);
	}

	/**
	 * Sets the shipping option name of this commerce order.
	 *
	 * @param shippingOptionName the shipping option name of this commerce order
	 */
	@Override
	public void setShippingOptionName(String shippingOptionName) {
		_commerceOrder.setShippingOptionName(shippingOptionName);
	}

	/**
	 * Sets the shipping with tax amount of this commerce order.
	 *
	 * @param shippingWithTaxAmount the shipping with tax amount of this commerce order
	 */
	@Override
	public void setShippingWithTaxAmount(BigDecimal shippingWithTaxAmount) {
		_commerceOrder.setShippingWithTaxAmount(shippingWithTaxAmount);
	}

	/**
	 * Sets the status of this commerce order.
	 *
	 * @param status the status of this commerce order
	 */
	@Override
	public void setStatus(int status) {
		_commerceOrder.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce order.
	 *
	 * @param statusByUserId the status by user ID of this commerce order
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commerceOrder.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce order.
	 *
	 * @param statusByUserName the status by user name of this commerce order
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_commerceOrder.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce order.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce order
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_commerceOrder.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce order.
	 *
	 * @param statusDate the status date of this commerce order
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		_commerceOrder.setStatusDate(statusDate);
	}

	/**
	 * Sets the subtotal of this commerce order.
	 *
	 * @param subtotal the subtotal of this commerce order
	 */
	@Override
	public void setSubtotal(BigDecimal subtotal) {
		_commerceOrder.setSubtotal(subtotal);
	}

	/**
	 * Sets the subtotal discount amount of this commerce order.
	 *
	 * @param subtotalDiscountAmount the subtotal discount amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountAmount(BigDecimal subtotalDiscountAmount) {
		_commerceOrder.setSubtotalDiscountAmount(subtotalDiscountAmount);
	}

	/**
	 * Sets the subtotal discount percentage level1 of this commerce order.
	 *
	 * @param subtotalDiscountPercentageLevel1 the subtotal discount percentage level1 of this commerce order
	 */
	@Override
	public void setSubtotalDiscountPercentageLevel1(
		BigDecimal subtotalDiscountPercentageLevel1) {

		_commerceOrder.setSubtotalDiscountPercentageLevel1(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel1WithTaxAmount(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel2(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel2WithTaxAmount(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel3(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel3WithTaxAmount(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel4(
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

		_commerceOrder.setSubtotalDiscountPercentageLevel4WithTaxAmount(
			subtotalDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setSubtotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		_commerceOrder.setSubtotalDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the subtotal discount with tax amount of this commerce order.
	 *
	 * @param subtotalDiscountWithTaxAmount the subtotal discount with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalDiscountWithTaxAmount(
		BigDecimal subtotalDiscountWithTaxAmount) {

		_commerceOrder.setSubtotalDiscountWithTaxAmount(
			subtotalDiscountWithTaxAmount);
	}

	/**
	 * Sets the subtotal with tax amount of this commerce order.
	 *
	 * @param subtotalWithTaxAmount the subtotal with tax amount of this commerce order
	 */
	@Override
	public void setSubtotalWithTaxAmount(BigDecimal subtotalWithTaxAmount) {
		_commerceOrder.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
	}

	/**
	 * Sets the tax amount of this commerce order.
	 *
	 * @param taxAmount the tax amount of this commerce order
	 */
	@Override
	public void setTaxAmount(BigDecimal taxAmount) {
		_commerceOrder.setTaxAmount(taxAmount);
	}

	/**
	 * Sets the total of this commerce order.
	 *
	 * @param total the total of this commerce order
	 */
	@Override
	public void setTotal(BigDecimal total) {
		_commerceOrder.setTotal(total);
	}

	/**
	 * Sets the total discount amount of this commerce order.
	 *
	 * @param totalDiscountAmount the total discount amount of this commerce order
	 */
	@Override
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		_commerceOrder.setTotalDiscountAmount(totalDiscountAmount);
	}

	/**
	 * Sets the total discount percentage level1 of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel1 the total discount percentage level1 of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel1(
		BigDecimal totalDiscountPercentageLevel1) {

		_commerceOrder.setTotalDiscountPercentageLevel1(
			totalDiscountPercentageLevel1);
	}

	/**
	 * Sets the total discount percentage level1 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel1WithTaxAmount the total discount percentage level1 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel1WithTaxAmount) {

		_commerceOrder.setTotalDiscountPercentageLevel1WithTaxAmount(
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

		_commerceOrder.setTotalDiscountPercentageLevel2(
			totalDiscountPercentageLevel2);
	}

	/**
	 * Sets the total discount percentage level2 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel2WithTaxAmount the total discount percentage level2 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel2WithTaxAmount) {

		_commerceOrder.setTotalDiscountPercentageLevel2WithTaxAmount(
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

		_commerceOrder.setTotalDiscountPercentageLevel3(
			totalDiscountPercentageLevel3);
	}

	/**
	 * Sets the total discount percentage level3 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel3WithTaxAmount the total discount percentage level3 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel3WithTaxAmount) {

		_commerceOrder.setTotalDiscountPercentageLevel3WithTaxAmount(
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

		_commerceOrder.setTotalDiscountPercentageLevel4(
			totalDiscountPercentageLevel4);
	}

	/**
	 * Sets the total discount percentage level4 with tax amount of this commerce order.
	 *
	 * @param totalDiscountPercentageLevel4WithTaxAmount the total discount percentage level4 with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel4WithTaxAmount) {

		_commerceOrder.setTotalDiscountPercentageLevel4WithTaxAmount(
			totalDiscountPercentageLevel4WithTaxAmount);
	}

	@Override
	public void setTotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue) {

		_commerceOrder.setTotalDiscounts(commerceDiscountValue);
	}

	/**
	 * Sets the total discount with tax amount of this commerce order.
	 *
	 * @param totalDiscountWithTaxAmount the total discount with tax amount of this commerce order
	 */
	@Override
	public void setTotalDiscountWithTaxAmount(
		BigDecimal totalDiscountWithTaxAmount) {

		_commerceOrder.setTotalDiscountWithTaxAmount(
			totalDiscountWithTaxAmount);
	}

	/**
	 * Sets the total with tax amount of this commerce order.
	 *
	 * @param totalWithTaxAmount the total with tax amount of this commerce order
	 */
	@Override
	public void setTotalWithTaxAmount(BigDecimal totalWithTaxAmount) {
		_commerceOrder.setTotalWithTaxAmount(totalWithTaxAmount);
	}

	/**
	 * Sets the transaction ID of this commerce order.
	 *
	 * @param transactionId the transaction ID of this commerce order
	 */
	@Override
	public void setTransactionId(String transactionId) {
		_commerceOrder.setTransactionId(transactionId);
	}

	/**
	 * Sets the user ID of this commerce order.
	 *
	 * @param userId the user ID of this commerce order
	 */
	@Override
	public void setUserId(long userId) {
		_commerceOrder.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order.
	 *
	 * @param userName the user name of this commerce order
	 */
	@Override
	public void setUserName(String userName) {
		_commerceOrder.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order.
	 *
	 * @param userUuid the user uuid of this commerce order
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceOrder.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce order.
	 *
	 * @param uuid the uuid of this commerce order
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceOrder.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrder>
		toCacheModel() {

		return _commerceOrder.toCacheModel();
	}

	@Override
	public CommerceOrder toEscapedModel() {
		return new CommerceOrderWrapper(_commerceOrder.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceOrder.toString();
	}

	@Override
	public CommerceOrder toUnescapedModel() {
		return new CommerceOrderWrapper(_commerceOrder.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceOrder.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderWrapper)) {
			return false;
		}

		CommerceOrderWrapper commerceOrderWrapper =
			(CommerceOrderWrapper)object;

		if (Objects.equals(
				_commerceOrder, commerceOrderWrapper._commerceOrder)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceOrder.getStagedModelType();
	}

	@Override
	public CommerceOrder getWrappedModel() {
		return _commerceOrder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceOrder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceOrder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceOrder.resetOriginalValues();
	}

	private final CommerceOrder _commerceOrder;

}