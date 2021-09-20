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

package com.liferay.headless.commerce.admin.order.client.dto.v1_0;

import com.liferay.headless.commerce.admin.order.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderSerDes;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Order implements Cloneable, Serializable {

	public static Order toDTO(String json) {
		return OrderSerDes.toDTO(json);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAccount(
		UnsafeSupplier<Account, Exception> accountUnsafeSupplier) {

		try {
			account = accountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Account account;

	public String getAccountExternalReferenceCode() {
		return accountExternalReferenceCode;
	}

	public void setAccountExternalReferenceCode(
		String accountExternalReferenceCode) {

		this.accountExternalReferenceCode = accountExternalReferenceCode;
	}

	public void setAccountExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountExternalReferenceCodeUnsafeSupplier) {

		try {
			accountExternalReferenceCode =
				accountExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accountExternalReferenceCode;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setAccountId(
		UnsafeSupplier<Long, Exception> accountIdUnsafeSupplier) {

		try {
			accountId = accountIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long accountId;

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public String getAdvanceStatus() {
		return advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	public void setAdvanceStatus(
		UnsafeSupplier<String, Exception> advanceStatusUnsafeSupplier) {

		try {
			advanceStatus = advanceStatusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String advanceStatus;

	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void setBillingAddress(
		UnsafeSupplier<BillingAddress, Exception>
			billingAddressUnsafeSupplier) {

		try {
			billingAddress = billingAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BillingAddress billingAddress;

	public Long getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	public void setBillingAddressId(
		UnsafeSupplier<Long, Exception> billingAddressIdUnsafeSupplier) {

		try {
			billingAddressId = billingAddressIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long billingAddressId;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setChannel(
		UnsafeSupplier<Channel, Exception> channelUnsafeSupplier) {

		try {
			channel = channelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Channel channel;

	public String getChannelExternalReferenceCode() {
		return channelExternalReferenceCode;
	}

	public void setChannelExternalReferenceCode(
		String channelExternalReferenceCode) {

		this.channelExternalReferenceCode = channelExternalReferenceCode;
	}

	public void setChannelExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			channelExternalReferenceCodeUnsafeSupplier) {

		try {
			channelExternalReferenceCode =
				channelExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String channelExternalReferenceCode;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public void setChannelId(
		UnsafeSupplier<Long, Exception> channelIdUnsafeSupplier) {

		try {
			channelId = channelIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long channelId;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public void setCouponCode(
		UnsafeSupplier<String, Exception> couponCodeUnsafeSupplier) {

		try {
			couponCode = couponCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String couponCode;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date createDate;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCurrencyCode(
		UnsafeSupplier<String, Exception> currencyCodeUnsafeSupplier) {

		try {
			currencyCode = currencyCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String currencyCode;

	public Map<String, ?> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, ?> customFields) {
		this.customFields = customFields;
	}

	public void setCustomFields(
		UnsafeSupplier<Map<String, ?>, Exception> customFieldsUnsafeSupplier) {

		try {
			customFields = customFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> customFields;

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Date getLastPriceUpdateDate() {
		return lastPriceUpdateDate;
	}

	public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
		this.lastPriceUpdateDate = lastPriceUpdateDate;
	}

	public void setLastPriceUpdateDate(
		UnsafeSupplier<Date, Exception> lastPriceUpdateDateUnsafeSupplier) {

		try {
			lastPriceUpdateDate = lastPriceUpdateDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date lastPriceUpdateDate;

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		try {
			modifiedDate = modifiedDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date modifiedDate;

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderDate(
		UnsafeSupplier<Date, Exception> orderDateUnsafeSupplier) {

		try {
			orderDate = orderDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date orderDate;

	public OrderItem[] getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(OrderItem[] orderItems) {
		this.orderItems = orderItems;
	}

	public void setOrderItems(
		UnsafeSupplier<OrderItem[], Exception> orderItemsUnsafeSupplier) {

		try {
			orderItems = orderItemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected OrderItem[] orderItems;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderStatus(
		UnsafeSupplier<Integer, Exception> orderStatusUnsafeSupplier) {

		try {
			orderStatus = orderStatusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer orderStatus;

	public Status getOrderStatusInfo() {
		return orderStatusInfo;
	}

	public void setOrderStatusInfo(Status orderStatusInfo) {
		this.orderStatusInfo = orderStatusInfo;
	}

	public void setOrderStatusInfo(
		UnsafeSupplier<Status, Exception> orderStatusInfoUnsafeSupplier) {

		try {
			orderStatusInfo = orderStatusInfoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status orderStatusInfo;

	public String getOrderTypeExternalReferenceCode() {
		return orderTypeExternalReferenceCode;
	}

	public void setOrderTypeExternalReferenceCode(
		String orderTypeExternalReferenceCode) {

		this.orderTypeExternalReferenceCode = orderTypeExternalReferenceCode;
	}

	public void setOrderTypeExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			orderTypeExternalReferenceCodeUnsafeSupplier) {

		try {
			orderTypeExternalReferenceCode =
				orderTypeExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String orderTypeExternalReferenceCode;

	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public void setOrderTypeId(
		UnsafeSupplier<Long, Exception> orderTypeIdUnsafeSupplier) {

		try {
			orderTypeId = orderTypeIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long orderTypeId;

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setPaymentMethod(
		UnsafeSupplier<String, Exception> paymentMethodUnsafeSupplier) {

		try {
			paymentMethod = paymentMethodUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paymentMethod;

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setPaymentStatus(
		UnsafeSupplier<Integer, Exception> paymentStatusUnsafeSupplier) {

		try {
			paymentStatus = paymentStatusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer paymentStatus;

	public Status getPaymentStatusInfo() {
		return paymentStatusInfo;
	}

	public void setPaymentStatusInfo(Status paymentStatusInfo) {
		this.paymentStatusInfo = paymentStatusInfo;
	}

	public void setPaymentStatusInfo(
		UnsafeSupplier<Status, Exception> paymentStatusInfoUnsafeSupplier) {

		try {
			paymentStatusInfo = paymentStatusInfoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status paymentStatusInfo;

	public String getPrintedNote() {
		return printedNote;
	}

	public void setPrintedNote(String printedNote) {
		this.printedNote = printedNote;
	}

	public void setPrintedNote(
		UnsafeSupplier<String, Exception> printedNoteUnsafeSupplier) {

		try {
			printedNote = printedNoteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String printedNote;

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(
		UnsafeSupplier<String, Exception> purchaseOrderNumberUnsafeSupplier) {

		try {
			purchaseOrderNumber = purchaseOrderNumberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String purchaseOrderNumber;

	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(
		UnsafeSupplier<Date, Exception> requestedDeliveryDateUnsafeSupplier) {

		try {
			requestedDeliveryDate = requestedDeliveryDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date requestedDeliveryDate;

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public void setShippingAddress(
		UnsafeSupplier<ShippingAddress, Exception>
			shippingAddressUnsafeSupplier) {

		try {
			shippingAddress = shippingAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ShippingAddress shippingAddress;

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public void setShippingAddressId(
		UnsafeSupplier<Long, Exception> shippingAddressIdUnsafeSupplier) {

		try {
			shippingAddressId = shippingAddressIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long shippingAddressId;

	public BigDecimal getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(BigDecimal shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public void setShippingAmount(
		UnsafeSupplier<BigDecimal, Exception> shippingAmountUnsafeSupplier) {

		try {
			shippingAmount = shippingAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal shippingAmount;

	public String getShippingAmountFormatted() {
		return shippingAmountFormatted;
	}

	public void setShippingAmountFormatted(String shippingAmountFormatted) {
		this.shippingAmountFormatted = shippingAmountFormatted;
	}

	public void setShippingAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingAmountFormattedUnsafeSupplier) {

		try {
			shippingAmountFormatted =
				shippingAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingAmountFormatted;

	public Double getShippingAmountValue() {
		return shippingAmountValue;
	}

	public void setShippingAmountValue(Double shippingAmountValue) {
		this.shippingAmountValue = shippingAmountValue;
	}

	public void setShippingAmountValue(
		UnsafeSupplier<Double, Exception> shippingAmountValueUnsafeSupplier) {

		try {
			shippingAmountValue = shippingAmountValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingAmountValue;

	public Double getShippingDiscountAmount() {
		return shippingDiscountAmount;
	}

	public void setShippingDiscountAmount(Double shippingDiscountAmount) {
		this.shippingDiscountAmount = shippingDiscountAmount;
	}

	public void setShippingDiscountAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountAmountUnsafeSupplier) {

		try {
			shippingDiscountAmount = shippingDiscountAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountAmount;

	public String getShippingDiscountAmountFormatted() {
		return shippingDiscountAmountFormatted;
	}

	public void setShippingDiscountAmountFormatted(
		String shippingDiscountAmountFormatted) {

		this.shippingDiscountAmountFormatted = shippingDiscountAmountFormatted;
	}

	public void setShippingDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingDiscountAmountFormattedUnsafeSupplier) {

		try {
			shippingDiscountAmountFormatted =
				shippingDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingDiscountAmountFormatted;

	public Double getShippingDiscountPercentageLevel1() {
		return shippingDiscountPercentageLevel1;
	}

	public void setShippingDiscountPercentageLevel1(
		Double shippingDiscountPercentageLevel1) {

		this.shippingDiscountPercentageLevel1 =
			shippingDiscountPercentageLevel1;
	}

	public void setShippingDiscountPercentageLevel1(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel1UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel1 =
				shippingDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel1;

	public Double getShippingDiscountPercentageLevel1WithTaxAmount() {
		return shippingDiscountPercentageLevel1WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel1WithTaxAmount(
		Double shippingDiscountPercentageLevel1WithTaxAmount) {

		this.shippingDiscountPercentageLevel1WithTaxAmount =
			shippingDiscountPercentageLevel1WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel1WithTaxAmount =
				shippingDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel1WithTaxAmount;

	public Double getShippingDiscountPercentageLevel2() {
		return shippingDiscountPercentageLevel2;
	}

	public void setShippingDiscountPercentageLevel2(
		Double shippingDiscountPercentageLevel2) {

		this.shippingDiscountPercentageLevel2 =
			shippingDiscountPercentageLevel2;
	}

	public void setShippingDiscountPercentageLevel2(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel2UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel2 =
				shippingDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel2;

	public Double getShippingDiscountPercentageLevel2WithTaxAmount() {
		return shippingDiscountPercentageLevel2WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel2WithTaxAmount(
		Double shippingDiscountPercentageLevel2WithTaxAmount) {

		this.shippingDiscountPercentageLevel2WithTaxAmount =
			shippingDiscountPercentageLevel2WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel2WithTaxAmount =
				shippingDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel2WithTaxAmount;

	public Double getShippingDiscountPercentageLevel3() {
		return shippingDiscountPercentageLevel3;
	}

	public void setShippingDiscountPercentageLevel3(
		Double shippingDiscountPercentageLevel3) {

		this.shippingDiscountPercentageLevel3 =
			shippingDiscountPercentageLevel3;
	}

	public void setShippingDiscountPercentageLevel3(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel3UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel3 =
				shippingDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel3;

	public Double getShippingDiscountPercentageLevel3WithTaxAmount() {
		return shippingDiscountPercentageLevel3WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel3WithTaxAmount(
		Double shippingDiscountPercentageLevel3WithTaxAmount) {

		this.shippingDiscountPercentageLevel3WithTaxAmount =
			shippingDiscountPercentageLevel3WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel3WithTaxAmount =
				shippingDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel3WithTaxAmount;

	public Double getShippingDiscountPercentageLevel4() {
		return shippingDiscountPercentageLevel4;
	}

	public void setShippingDiscountPercentageLevel4(
		Double shippingDiscountPercentageLevel4) {

		this.shippingDiscountPercentageLevel4 =
			shippingDiscountPercentageLevel4;
	}

	public void setShippingDiscountPercentageLevel4(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel4UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel4 =
				shippingDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel4;

	public Double getShippingDiscountPercentageLevel4WithTaxAmount() {
		return shippingDiscountPercentageLevel4WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel4WithTaxAmount(
		Double shippingDiscountPercentageLevel4WithTaxAmount) {

		this.shippingDiscountPercentageLevel4WithTaxAmount =
			shippingDiscountPercentageLevel4WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel4WithTaxAmount =
				shippingDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountPercentageLevel4WithTaxAmount;

	public Double getShippingDiscountWithTaxAmount() {
		return shippingDiscountWithTaxAmount;
	}

	public void setShippingDiscountWithTaxAmount(
		Double shippingDiscountWithTaxAmount) {

		this.shippingDiscountWithTaxAmount = shippingDiscountWithTaxAmount;
	}

	public void setShippingDiscountWithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingDiscountWithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountWithTaxAmount =
				shippingDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingDiscountWithTaxAmount;

	public String getShippingDiscountWithTaxAmountFormatted() {
		return shippingDiscountWithTaxAmountFormatted;
	}

	public void setShippingDiscountWithTaxAmountFormatted(
		String shippingDiscountWithTaxAmountFormatted) {

		this.shippingDiscountWithTaxAmountFormatted =
			shippingDiscountWithTaxAmountFormatted;
	}

	public void setShippingDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			shippingDiscountWithTaxAmountFormatted =
				shippingDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingDiscountWithTaxAmountFormatted;

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public void setShippingMethod(
		UnsafeSupplier<String, Exception> shippingMethodUnsafeSupplier) {

		try {
			shippingMethod = shippingMethodUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingMethod;

	public String getShippingOption() {
		return shippingOption;
	}

	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}

	public void setShippingOption(
		UnsafeSupplier<String, Exception> shippingOptionUnsafeSupplier) {

		try {
			shippingOption = shippingOptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingOption;

	public BigDecimal getShippingWithTaxAmount() {
		return shippingWithTaxAmount;
	}

	public void setShippingWithTaxAmount(BigDecimal shippingWithTaxAmount) {
		this.shippingWithTaxAmount = shippingWithTaxAmount;
	}

	public void setShippingWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingWithTaxAmountUnsafeSupplier) {

		try {
			shippingWithTaxAmount = shippingWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal shippingWithTaxAmount;

	public String getShippingWithTaxAmountFormatted() {
		return shippingWithTaxAmountFormatted;
	}

	public void setShippingWithTaxAmountFormatted(
		String shippingWithTaxAmountFormatted) {

		this.shippingWithTaxAmountFormatted = shippingWithTaxAmountFormatted;
	}

	public void setShippingWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingWithTaxAmountFormattedUnsafeSupplier) {

		try {
			shippingWithTaxAmountFormatted =
				shippingWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String shippingWithTaxAmountFormatted;

	public Double getShippingWithTaxAmountValue() {
		return shippingWithTaxAmountValue;
	}

	public void setShippingWithTaxAmountValue(
		Double shippingWithTaxAmountValue) {

		this.shippingWithTaxAmountValue = shippingWithTaxAmountValue;
	}

	public void setShippingWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			shippingWithTaxAmountValueUnsafeSupplier) {

		try {
			shippingWithTaxAmountValue =
				shippingWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double shippingWithTaxAmountValue;

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public void setSubtotal(
		UnsafeSupplier<BigDecimal, Exception> subtotalUnsafeSupplier) {

		try {
			subtotal = subtotalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal subtotal;

	public Double getSubtotalAmount() {
		return subtotalAmount;
	}

	public void setSubtotalAmount(Double subtotalAmount) {
		this.subtotalAmount = subtotalAmount;
	}

	public void setSubtotalAmount(
		UnsafeSupplier<Double, Exception> subtotalAmountUnsafeSupplier) {

		try {
			subtotalAmount = subtotalAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalAmount;

	public Double getSubtotalDiscountAmount() {
		return subtotalDiscountAmount;
	}

	public void setSubtotalDiscountAmount(Double subtotalDiscountAmount) {
		this.subtotalDiscountAmount = subtotalDiscountAmount;
	}

	public void setSubtotalDiscountAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountAmountUnsafeSupplier) {

		try {
			subtotalDiscountAmount = subtotalDiscountAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountAmount;

	public String getSubtotalDiscountAmountFormatted() {
		return subtotalDiscountAmountFormatted;
	}

	public void setSubtotalDiscountAmountFormatted(
		String subtotalDiscountAmountFormatted) {

		this.subtotalDiscountAmountFormatted = subtotalDiscountAmountFormatted;
	}

	public void setSubtotalDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalDiscountAmountFormattedUnsafeSupplier) {

		try {
			subtotalDiscountAmountFormatted =
				subtotalDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String subtotalDiscountAmountFormatted;

	public Double getSubtotalDiscountPercentageLevel1() {
		return subtotalDiscountPercentageLevel1;
	}

	public void setSubtotalDiscountPercentageLevel1(
		Double subtotalDiscountPercentageLevel1) {

		this.subtotalDiscountPercentageLevel1 =
			subtotalDiscountPercentageLevel1;
	}

	public void setSubtotalDiscountPercentageLevel1(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel1UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel1 =
				subtotalDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel1;

	public Double getSubtotalDiscountPercentageLevel1WithTaxAmount() {
		return subtotalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel1WithTaxAmount(
		Double subtotalDiscountPercentageLevel1WithTaxAmount) {

		this.subtotalDiscountPercentageLevel1WithTaxAmount =
			subtotalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel1WithTaxAmount =
				subtotalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel1WithTaxAmount;

	public Double getSubtotalDiscountPercentageLevel2() {
		return subtotalDiscountPercentageLevel2;
	}

	public void setSubtotalDiscountPercentageLevel2(
		Double subtotalDiscountPercentageLevel2) {

		this.subtotalDiscountPercentageLevel2 =
			subtotalDiscountPercentageLevel2;
	}

	public void setSubtotalDiscountPercentageLevel2(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel2UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel2 =
				subtotalDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel2;

	public Double getSubtotalDiscountPercentageLevel2WithTaxAmount() {
		return subtotalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel2WithTaxAmount(
		Double subtotalDiscountPercentageLevel2WithTaxAmount) {

		this.subtotalDiscountPercentageLevel2WithTaxAmount =
			subtotalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel2WithTaxAmount =
				subtotalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel2WithTaxAmount;

	public Double getSubtotalDiscountPercentageLevel3() {
		return subtotalDiscountPercentageLevel3;
	}

	public void setSubtotalDiscountPercentageLevel3(
		Double subtotalDiscountPercentageLevel3) {

		this.subtotalDiscountPercentageLevel3 =
			subtotalDiscountPercentageLevel3;
	}

	public void setSubtotalDiscountPercentageLevel3(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel3UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel3 =
				subtotalDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel3;

	public Double getSubtotalDiscountPercentageLevel3WithTaxAmount() {
		return subtotalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel3WithTaxAmount(
		Double subtotalDiscountPercentageLevel3WithTaxAmount) {

		this.subtotalDiscountPercentageLevel3WithTaxAmount =
			subtotalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel3WithTaxAmount =
				subtotalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel3WithTaxAmount;

	public Double getSubtotalDiscountPercentageLevel4() {
		return subtotalDiscountPercentageLevel4;
	}

	public void setSubtotalDiscountPercentageLevel4(
		Double subtotalDiscountPercentageLevel4) {

		this.subtotalDiscountPercentageLevel4 =
			subtotalDiscountPercentageLevel4;
	}

	public void setSubtotalDiscountPercentageLevel4(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel4UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel4 =
				subtotalDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel4;

	public Double getSubtotalDiscountPercentageLevel4WithTaxAmount() {
		return subtotalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel4WithTaxAmount(
		Double subtotalDiscountPercentageLevel4WithTaxAmount) {

		this.subtotalDiscountPercentageLevel4WithTaxAmount =
			subtotalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel4WithTaxAmount =
				subtotalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountPercentageLevel4WithTaxAmount;

	public Double getSubtotalDiscountWithTaxAmount() {
		return subtotalDiscountWithTaxAmount;
	}

	public void setSubtotalDiscountWithTaxAmount(
		Double subtotalDiscountWithTaxAmount) {

		this.subtotalDiscountWithTaxAmount = subtotalDiscountWithTaxAmount;
	}

	public void setSubtotalDiscountWithTaxAmount(
		UnsafeSupplier<Double, Exception>
			subtotalDiscountWithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountWithTaxAmount =
				subtotalDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalDiscountWithTaxAmount;

	public String getSubtotalDiscountWithTaxAmountFormatted() {
		return subtotalDiscountWithTaxAmountFormatted;
	}

	public void setSubtotalDiscountWithTaxAmountFormatted(
		String subtotalDiscountWithTaxAmountFormatted) {

		this.subtotalDiscountWithTaxAmountFormatted =
			subtotalDiscountWithTaxAmountFormatted;
	}

	public void setSubtotalDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			subtotalDiscountWithTaxAmountFormatted =
				subtotalDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String subtotalDiscountWithTaxAmountFormatted;

	public String getSubtotalFormatted() {
		return subtotalFormatted;
	}

	public void setSubtotalFormatted(String subtotalFormatted) {
		this.subtotalFormatted = subtotalFormatted;
	}

	public void setSubtotalFormatted(
		UnsafeSupplier<String, Exception> subtotalFormattedUnsafeSupplier) {

		try {
			subtotalFormatted = subtotalFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String subtotalFormatted;

	public BigDecimal getSubtotalWithTaxAmount() {
		return subtotalWithTaxAmount;
	}

	public void setSubtotalWithTaxAmount(BigDecimal subtotalWithTaxAmount) {
		this.subtotalWithTaxAmount = subtotalWithTaxAmount;
	}

	public void setSubtotalWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalWithTaxAmountUnsafeSupplier) {

		try {
			subtotalWithTaxAmount = subtotalWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal subtotalWithTaxAmount;

	public String getSubtotalWithTaxAmountFormatted() {
		return subtotalWithTaxAmountFormatted;
	}

	public void setSubtotalWithTaxAmountFormatted(
		String subtotalWithTaxAmountFormatted) {

		this.subtotalWithTaxAmountFormatted = subtotalWithTaxAmountFormatted;
	}

	public void setSubtotalWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalWithTaxAmountFormattedUnsafeSupplier) {

		try {
			subtotalWithTaxAmountFormatted =
				subtotalWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String subtotalWithTaxAmountFormatted;

	public Double getSubtotalWithTaxAmountValue() {
		return subtotalWithTaxAmountValue;
	}

	public void setSubtotalWithTaxAmountValue(
		Double subtotalWithTaxAmountValue) {

		this.subtotalWithTaxAmountValue = subtotalWithTaxAmountValue;
	}

	public void setSubtotalWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			subtotalWithTaxAmountValueUnsafeSupplier) {

		try {
			subtotalWithTaxAmountValue =
				subtotalWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double subtotalWithTaxAmountValue;

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void setTaxAmount(
		UnsafeSupplier<BigDecimal, Exception> taxAmountUnsafeSupplier) {

		try {
			taxAmount = taxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal taxAmount;

	public String getTaxAmountFormatted() {
		return taxAmountFormatted;
	}

	public void setTaxAmountFormatted(String taxAmountFormatted) {
		this.taxAmountFormatted = taxAmountFormatted;
	}

	public void setTaxAmountFormatted(
		UnsafeSupplier<String, Exception> taxAmountFormattedUnsafeSupplier) {

		try {
			taxAmountFormatted = taxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String taxAmountFormatted;

	public Double getTaxAmountValue() {
		return taxAmountValue;
	}

	public void setTaxAmountValue(Double taxAmountValue) {
		this.taxAmountValue = taxAmountValue;
	}

	public void setTaxAmountValue(
		UnsafeSupplier<Double, Exception> taxAmountValueUnsafeSupplier) {

		try {
			taxAmountValue = taxAmountValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double taxAmountValue;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setTotal(
		UnsafeSupplier<BigDecimal, Exception> totalUnsafeSupplier) {

		try {
			total = totalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal total;

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setTotalAmount(
		UnsafeSupplier<Double, Exception> totalAmountUnsafeSupplier) {

		try {
			totalAmount = totalAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalAmount;

	public Double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(Double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public void setTotalDiscountAmount(
		UnsafeSupplier<Double, Exception> totalDiscountAmountUnsafeSupplier) {

		try {
			totalDiscountAmount = totalDiscountAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountAmount;

	public String getTotalDiscountAmountFormatted() {
		return totalDiscountAmountFormatted;
	}

	public void setTotalDiscountAmountFormatted(
		String totalDiscountAmountFormatted) {

		this.totalDiscountAmountFormatted = totalDiscountAmountFormatted;
	}

	public void setTotalDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalDiscountAmountFormattedUnsafeSupplier) {

		try {
			totalDiscountAmountFormatted =
				totalDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String totalDiscountAmountFormatted;

	public Double getTotalDiscountPercentageLevel1() {
		return totalDiscountPercentageLevel1;
	}

	public void setTotalDiscountPercentageLevel1(
		Double totalDiscountPercentageLevel1) {

		this.totalDiscountPercentageLevel1 = totalDiscountPercentageLevel1;
	}

	public void setTotalDiscountPercentageLevel1(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel1UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel1 =
				totalDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel1;

	public Double getTotalDiscountPercentageLevel1WithTaxAmount() {
		return totalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		Double totalDiscountPercentageLevel1WithTaxAmount) {

		this.totalDiscountPercentageLevel1WithTaxAmount =
			totalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel1WithTaxAmount =
				totalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel1WithTaxAmount;

	public Double getTotalDiscountPercentageLevel2() {
		return totalDiscountPercentageLevel2;
	}

	public void setTotalDiscountPercentageLevel2(
		Double totalDiscountPercentageLevel2) {

		this.totalDiscountPercentageLevel2 = totalDiscountPercentageLevel2;
	}

	public void setTotalDiscountPercentageLevel2(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel2UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel2 =
				totalDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel2;

	public Double getTotalDiscountPercentageLevel2WithTaxAmount() {
		return totalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		Double totalDiscountPercentageLevel2WithTaxAmount) {

		this.totalDiscountPercentageLevel2WithTaxAmount =
			totalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel2WithTaxAmount =
				totalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel2WithTaxAmount;

	public Double getTotalDiscountPercentageLevel3() {
		return totalDiscountPercentageLevel3;
	}

	public void setTotalDiscountPercentageLevel3(
		Double totalDiscountPercentageLevel3) {

		this.totalDiscountPercentageLevel3 = totalDiscountPercentageLevel3;
	}

	public void setTotalDiscountPercentageLevel3(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel3UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel3 =
				totalDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel3;

	public Double getTotalDiscountPercentageLevel3WithTaxAmount() {
		return totalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		Double totalDiscountPercentageLevel3WithTaxAmount) {

		this.totalDiscountPercentageLevel3WithTaxAmount =
			totalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel3WithTaxAmount =
				totalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel3WithTaxAmount;

	public Double getTotalDiscountPercentageLevel4() {
		return totalDiscountPercentageLevel4;
	}

	public void setTotalDiscountPercentageLevel4(
		Double totalDiscountPercentageLevel4) {

		this.totalDiscountPercentageLevel4 = totalDiscountPercentageLevel4;
	}

	public void setTotalDiscountPercentageLevel4(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel4UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel4 =
				totalDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel4;

	public Double getTotalDiscountPercentageLevel4WithTaxAmount() {
		return totalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		Double totalDiscountPercentageLevel4WithTaxAmount) {

		this.totalDiscountPercentageLevel4WithTaxAmount =
			totalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<Double, Exception>
			totalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel4WithTaxAmount =
				totalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountPercentageLevel4WithTaxAmount;

	public Double getTotalDiscountWithTaxAmount() {
		return totalDiscountWithTaxAmount;
	}

	public void setTotalDiscountWithTaxAmount(
		Double totalDiscountWithTaxAmount) {

		this.totalDiscountWithTaxAmount = totalDiscountWithTaxAmount;
	}

	public void setTotalDiscountWithTaxAmount(
		UnsafeSupplier<Double, Exception>
			totalDiscountWithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountWithTaxAmount =
				totalDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalDiscountWithTaxAmount;

	public String getTotalDiscountWithTaxAmountFormatted() {
		return totalDiscountWithTaxAmountFormatted;
	}

	public void setTotalDiscountWithTaxAmountFormatted(
		String totalDiscountWithTaxAmountFormatted) {

		this.totalDiscountWithTaxAmountFormatted =
			totalDiscountWithTaxAmountFormatted;
	}

	public void setTotalDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			totalDiscountWithTaxAmountFormatted =
				totalDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String totalDiscountWithTaxAmountFormatted;

	public String getTotalFormatted() {
		return totalFormatted;
	}

	public void setTotalFormatted(String totalFormatted) {
		this.totalFormatted = totalFormatted;
	}

	public void setTotalFormatted(
		UnsafeSupplier<String, Exception> totalFormattedUnsafeSupplier) {

		try {
			totalFormatted = totalFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String totalFormatted;

	public BigDecimal getTotalWithTaxAmount() {
		return totalWithTaxAmount;
	}

	public void setTotalWithTaxAmount(BigDecimal totalWithTaxAmount) {
		this.totalWithTaxAmount = totalWithTaxAmount;
	}

	public void setTotalWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalWithTaxAmountUnsafeSupplier) {

		try {
			totalWithTaxAmount = totalWithTaxAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal totalWithTaxAmount;

	public String getTotalWithTaxAmountFormatted() {
		return totalWithTaxAmountFormatted;
	}

	public void setTotalWithTaxAmountFormatted(
		String totalWithTaxAmountFormatted) {

		this.totalWithTaxAmountFormatted = totalWithTaxAmountFormatted;
	}

	public void setTotalWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalWithTaxAmountFormattedUnsafeSupplier) {

		try {
			totalWithTaxAmountFormatted =
				totalWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String totalWithTaxAmountFormatted;

	public Double getTotalWithTaxAmountValue() {
		return totalWithTaxAmountValue;
	}

	public void setTotalWithTaxAmountValue(Double totalWithTaxAmountValue) {
		this.totalWithTaxAmountValue = totalWithTaxAmountValue;
	}

	public void setTotalWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			totalWithTaxAmountValueUnsafeSupplier) {

		try {
			totalWithTaxAmountValue =
				totalWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double totalWithTaxAmountValue;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setTransactionId(
		UnsafeSupplier<String, Exception> transactionIdUnsafeSupplier) {

		try {
			transactionId = transactionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String transactionId;

	public Status getWorkflowStatusInfo() {
		return workflowStatusInfo;
	}

	public void setWorkflowStatusInfo(Status workflowStatusInfo) {
		this.workflowStatusInfo = workflowStatusInfo;
	}

	public void setWorkflowStatusInfo(
		UnsafeSupplier<Status, Exception> workflowStatusInfoUnsafeSupplier) {

		try {
			workflowStatusInfo = workflowStatusInfoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status workflowStatusInfo;

	@Override
	public Order clone() throws CloneNotSupportedException {
		return (Order)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Order)) {
			return false;
		}

		Order order = (Order)object;

		return Objects.equals(toString(), order.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return OrderSerDes.toJSON(this);
	}

}