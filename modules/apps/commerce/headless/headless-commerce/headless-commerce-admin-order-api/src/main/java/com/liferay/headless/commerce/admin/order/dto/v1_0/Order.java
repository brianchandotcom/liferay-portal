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

package com.liferay.headless.commerce.admin.order.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
@GraphQLName("Order")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"channelId", "currencyCode"})
@XmlRootElement(name = "Order")
public class Order implements Serializable {

	public static Order toDTO(String json) {
		return ObjectMapperUtil.readValue(Order.class, json);
	}

	public static Order unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Order.class, json);
	}

	@Schema
	@Valid
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@JsonIgnore
	public void setAccount(
		UnsafeSupplier<Account, Exception> accountUnsafeSupplier) {

		try {
			account = accountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Account account;

	@Schema
	public String getAccountExternalReferenceCode() {
		return accountExternalReferenceCode;
	}

	public void setAccountExternalReferenceCode(
		String accountExternalReferenceCode) {

		this.accountExternalReferenceCode = accountExternalReferenceCode;
	}

	@JsonIgnore
	public void setAccountExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountExternalReferenceCodeUnsafeSupplier) {

		try {
			accountExternalReferenceCode =
				accountExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String accountExternalReferenceCode;

	@DecimalMin("0")
	@Schema
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@JsonIgnore
	public void setAccountId(
		UnsafeSupplier<Long, Exception> accountIdUnsafeSupplier) {

		try {
			accountId = accountIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long accountId;

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, Map<String, String>> actions;

	@Schema
	public String getAdvanceStatus() {
		return advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	@JsonIgnore
	public void setAdvanceStatus(
		UnsafeSupplier<String, Exception> advanceStatusUnsafeSupplier) {

		try {
			advanceStatus = advanceStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String advanceStatus;

	@Schema
	@Valid
	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	@JsonIgnore
	public void setBillingAddress(
		UnsafeSupplier<BillingAddress, Exception>
			billingAddressUnsafeSupplier) {

		try {
			billingAddress = billingAddressUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BillingAddress billingAddress;

	@DecimalMin("0")
	@Schema
	public Long getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	@JsonIgnore
	public void setBillingAddressId(
		UnsafeSupplier<Long, Exception> billingAddressIdUnsafeSupplier) {

		try {
			billingAddressId = billingAddressIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long billingAddressId;

	@Schema
	@Valid
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@JsonIgnore
	public void setChannel(
		UnsafeSupplier<Channel, Exception> channelUnsafeSupplier) {

		try {
			channel = channelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Channel channel;

	@Schema
	public String getChannelExternalReferenceCode() {
		return channelExternalReferenceCode;
	}

	public void setChannelExternalReferenceCode(
		String channelExternalReferenceCode) {

		this.channelExternalReferenceCode = channelExternalReferenceCode;
	}

	@JsonIgnore
	public void setChannelExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			channelExternalReferenceCodeUnsafeSupplier) {

		try {
			channelExternalReferenceCode =
				channelExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String channelExternalReferenceCode;

	@DecimalMin("0")
	@Schema
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@JsonIgnore
	public void setChannelId(
		UnsafeSupplier<Long, Exception> channelIdUnsafeSupplier) {

		try {
			channelId = channelIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Long channelId;

	@Schema
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@JsonIgnore
	public void setCouponCode(
		UnsafeSupplier<String, Exception> couponCodeUnsafeSupplier) {

		try {
			couponCode = couponCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String couponCode;

	@Schema
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date createDate;

	@Schema
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@JsonIgnore
	public void setCurrencyCode(
		UnsafeSupplier<String, Exception> currencyCodeUnsafeSupplier) {

		try {
			currencyCode = currencyCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String currencyCode;

	@Schema
	@Valid
	public Map<String, ?> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, ?> customFields) {
		this.customFields = customFields;
	}

	@JsonIgnore
	public void setCustomFields(
		UnsafeSupplier<Map<String, ?>, Exception> customFieldsUnsafeSupplier) {

		try {
			customFields = customFieldsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, ?> customFields;

	@Schema
	public String getDeliveryTermDescription() {
		return deliveryTermDescription;
	}

	public void setDeliveryTermDescription(String deliveryTermDescription) {
		this.deliveryTermDescription = deliveryTermDescription;
	}

	@JsonIgnore
	public void setDeliveryTermDescription(
		UnsafeSupplier<String, Exception>
			deliveryTermDescriptionUnsafeSupplier) {

		try {
			deliveryTermDescription =
				deliveryTermDescriptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String deliveryTermDescription;

	@DecimalMin("0")
	@Schema
	public Long getDeliveryTermId() {
		return deliveryTermId;
	}

	public void setDeliveryTermId(Long deliveryTermId) {
		this.deliveryTermId = deliveryTermId;
	}

	@JsonIgnore
	public void setDeliveryTermId(
		UnsafeSupplier<Long, Exception> deliveryTermIdUnsafeSupplier) {

		try {
			deliveryTermId = deliveryTermIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long deliveryTermId;

	@Schema
	public String getDeliveryTermName() {
		return deliveryTermName;
	}

	public void setDeliveryTermName(String deliveryTermName) {
		this.deliveryTermName = deliveryTermName;
	}

	@JsonIgnore
	public void setDeliveryTermName(
		UnsafeSupplier<String, Exception> deliveryTermNameUnsafeSupplier) {

		try {
			deliveryTermName = deliveryTermNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String deliveryTermName;

	@Schema
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@DecimalMin("0")
	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@Schema
	public Date getLastPriceUpdateDate() {
		return lastPriceUpdateDate;
	}

	public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
		this.lastPriceUpdateDate = lastPriceUpdateDate;
	}

	@JsonIgnore
	public void setLastPriceUpdateDate(
		UnsafeSupplier<Date, Exception> lastPriceUpdateDateUnsafeSupplier) {

		try {
			lastPriceUpdateDate = lastPriceUpdateDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date lastPriceUpdateDate;

	@Schema
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@JsonIgnore
	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		try {
			modifiedDate = modifiedDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date modifiedDate;

	@Schema
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@JsonIgnore
	public void setOrderDate(
		UnsafeSupplier<Date, Exception> orderDateUnsafeSupplier) {

		try {
			orderDate = orderDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date orderDate;

	@Schema
	@Valid
	public OrderItem[] getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(OrderItem[] orderItems) {
		this.orderItems = orderItems;
	}

	@JsonIgnore
	public void setOrderItems(
		UnsafeSupplier<OrderItem[], Exception> orderItemsUnsafeSupplier) {

		try {
			orderItems = orderItemsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected OrderItem[] orderItems;

	@DecimalMin("0")
	@Schema
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@JsonIgnore
	public void setOrderStatus(
		UnsafeSupplier<Integer, Exception> orderStatusUnsafeSupplier) {

		try {
			orderStatus = orderStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer orderStatus;

	@Schema
	@Valid
	public Status getOrderStatusInfo() {
		return orderStatusInfo;
	}

	public void setOrderStatusInfo(Status orderStatusInfo) {
		this.orderStatusInfo = orderStatusInfo;
	}

	@JsonIgnore
	public void setOrderStatusInfo(
		UnsafeSupplier<Status, Exception> orderStatusInfoUnsafeSupplier) {

		try {
			orderStatusInfo = orderStatusInfoUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Status orderStatusInfo;

	@Schema
	public String getOrderTypeExternalReferenceCode() {
		return orderTypeExternalReferenceCode;
	}

	public void setOrderTypeExternalReferenceCode(
		String orderTypeExternalReferenceCode) {

		this.orderTypeExternalReferenceCode = orderTypeExternalReferenceCode;
	}

	@JsonIgnore
	public void setOrderTypeExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			orderTypeExternalReferenceCodeUnsafeSupplier) {

		try {
			orderTypeExternalReferenceCode =
				orderTypeExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String orderTypeExternalReferenceCode;

	@DecimalMin("0")
	@Schema
	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	@JsonIgnore
	public void setOrderTypeId(
		UnsafeSupplier<Long, Exception> orderTypeIdUnsafeSupplier) {

		try {
			orderTypeId = orderTypeIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long orderTypeId;

	@Schema
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@JsonIgnore
	public void setPaymentMethod(
		UnsafeSupplier<String, Exception> paymentMethodUnsafeSupplier) {

		try {
			paymentMethod = paymentMethodUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paymentMethod;

	@DecimalMin("0")
	@Schema
	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@JsonIgnore
	public void setPaymentStatus(
		UnsafeSupplier<Integer, Exception> paymentStatusUnsafeSupplier) {

		try {
			paymentStatus = paymentStatusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer paymentStatus;

	@Schema
	@Valid
	public Status getPaymentStatusInfo() {
		return paymentStatusInfo;
	}

	public void setPaymentStatusInfo(Status paymentStatusInfo) {
		this.paymentStatusInfo = paymentStatusInfo;
	}

	@JsonIgnore
	public void setPaymentStatusInfo(
		UnsafeSupplier<Status, Exception> paymentStatusInfoUnsafeSupplier) {

		try {
			paymentStatusInfo = paymentStatusInfoUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Status paymentStatusInfo;

	@Schema
	public String getPaymentTermDescription() {
		return paymentTermDescription;
	}

	public void setPaymentTermDescription(String paymentTermDescription) {
		this.paymentTermDescription = paymentTermDescription;
	}

	@JsonIgnore
	public void setPaymentTermDescription(
		UnsafeSupplier<String, Exception>
			paymentTermDescriptionUnsafeSupplier) {

		try {
			paymentTermDescription = paymentTermDescriptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String paymentTermDescription;

	@DecimalMin("0")
	@Schema
	public Long getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(Long paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	@JsonIgnore
	public void setPaymentTermId(
		UnsafeSupplier<Long, Exception> paymentTermIdUnsafeSupplier) {

		try {
			paymentTermId = paymentTermIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long paymentTermId;

	@Schema
	public String getPaymentTermName() {
		return paymentTermName;
	}

	public void setPaymentTermName(String paymentTermName) {
		this.paymentTermName = paymentTermName;
	}

	@JsonIgnore
	public void setPaymentTermName(
		UnsafeSupplier<String, Exception> paymentTermNameUnsafeSupplier) {

		try {
			paymentTermName = paymentTermNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String paymentTermName;

	@Schema
	public String getPrintedNote() {
		return printedNote;
	}

	public void setPrintedNote(String printedNote) {
		this.printedNote = printedNote;
	}

	@JsonIgnore
	public void setPrintedNote(
		UnsafeSupplier<String, Exception> printedNoteUnsafeSupplier) {

		try {
			printedNote = printedNoteUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String printedNote;

	@Schema
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	@JsonIgnore
	public void setPurchaseOrderNumber(
		UnsafeSupplier<String, Exception> purchaseOrderNumberUnsafeSupplier) {

		try {
			purchaseOrderNumber = purchaseOrderNumberUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String purchaseOrderNumber;

	@Schema
	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	@JsonIgnore
	public void setRequestedDeliveryDate(
		UnsafeSupplier<Date, Exception> requestedDeliveryDateUnsafeSupplier) {

		try {
			requestedDeliveryDate = requestedDeliveryDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date requestedDeliveryDate;

	@Schema
	@Valid
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@JsonIgnore
	public void setShippingAddress(
		UnsafeSupplier<ShippingAddress, Exception>
			shippingAddressUnsafeSupplier) {

		try {
			shippingAddress = shippingAddressUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ShippingAddress shippingAddress;

	@DecimalMin("0")
	@Schema
	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	@JsonIgnore
	public void setShippingAddressId(
		UnsafeSupplier<Long, Exception> shippingAddressIdUnsafeSupplier) {

		try {
			shippingAddressId = shippingAddressIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long shippingAddressId;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(BigDecimal shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	@JsonIgnore
	public void setShippingAmount(
		UnsafeSupplier<BigDecimal, Exception> shippingAmountUnsafeSupplier) {

		try {
			shippingAmount = shippingAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingAmount;

	@Schema
	public String getShippingAmountFormatted() {
		return shippingAmountFormatted;
	}

	public void setShippingAmountFormatted(String shippingAmountFormatted) {
		this.shippingAmountFormatted = shippingAmountFormatted;
	}

	@JsonIgnore
	public void setShippingAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingAmountFormattedUnsafeSupplier) {

		try {
			shippingAmountFormatted =
				shippingAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getShippingAmountValue() {
		return shippingAmountValue;
	}

	public void setShippingAmountValue(Double shippingAmountValue) {
		this.shippingAmountValue = shippingAmountValue;
	}

	@JsonIgnore
	public void setShippingAmountValue(
		UnsafeSupplier<Double, Exception> shippingAmountValueUnsafeSupplier) {

		try {
			shippingAmountValue = shippingAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double shippingAmountValue;

	@Schema
	@Valid
	public BigDecimal getShippingDiscountAmount() {
		return shippingDiscountAmount;
	}

	public void setShippingDiscountAmount(BigDecimal shippingDiscountAmount) {
		this.shippingDiscountAmount = shippingDiscountAmount;
	}

	@JsonIgnore
	public void setShippingDiscountAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountAmountUnsafeSupplier) {

		try {
			shippingDiscountAmount = shippingDiscountAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountAmount;

	@Schema
	public String getShippingDiscountAmountFormatted() {
		return shippingDiscountAmountFormatted;
	}

	public void setShippingDiscountAmountFormatted(
		String shippingDiscountAmountFormatted) {

		this.shippingDiscountAmountFormatted = shippingDiscountAmountFormatted;
	}

	@JsonIgnore
	public void setShippingDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingDiscountAmountFormattedUnsafeSupplier) {

		try {
			shippingDiscountAmountFormatted =
				shippingDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingDiscountAmountFormatted;

	@Schema
	public Double getShippingDiscountAmountValue() {
		return shippingDiscountAmountValue;
	}

	public void setShippingDiscountAmountValue(
		Double shippingDiscountAmountValue) {

		this.shippingDiscountAmountValue = shippingDiscountAmountValue;
	}

	@JsonIgnore
	public void setShippingDiscountAmountValue(
		UnsafeSupplier<Double, Exception>
			shippingDiscountAmountValueUnsafeSupplier) {

		try {
			shippingDiscountAmountValue =
				shippingDiscountAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double shippingDiscountAmountValue;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel1() {
		return shippingDiscountPercentageLevel1;
	}

	public void setShippingDiscountPercentageLevel1(
		BigDecimal shippingDiscountPercentageLevel1) {

		this.shippingDiscountPercentageLevel1 =
			shippingDiscountPercentageLevel1;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel1(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel1UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel1 =
				shippingDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel1;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel1WithTaxAmount() {
		return shippingDiscountPercentageLevel1WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel1WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel1WithTaxAmount) {

		this.shippingDiscountPercentageLevel1WithTaxAmount =
			shippingDiscountPercentageLevel1WithTaxAmount;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel1WithTaxAmount =
				shippingDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel1WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel2() {
		return shippingDiscountPercentageLevel2;
	}

	public void setShippingDiscountPercentageLevel2(
		BigDecimal shippingDiscountPercentageLevel2) {

		this.shippingDiscountPercentageLevel2 =
			shippingDiscountPercentageLevel2;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel2(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel2UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel2 =
				shippingDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel2;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel2WithTaxAmount() {
		return shippingDiscountPercentageLevel2WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel2WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel2WithTaxAmount) {

		this.shippingDiscountPercentageLevel2WithTaxAmount =
			shippingDiscountPercentageLevel2WithTaxAmount;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel2WithTaxAmount =
				shippingDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel2WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel3() {
		return shippingDiscountPercentageLevel3;
	}

	public void setShippingDiscountPercentageLevel3(
		BigDecimal shippingDiscountPercentageLevel3) {

		this.shippingDiscountPercentageLevel3 =
			shippingDiscountPercentageLevel3;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel3(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel3UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel3 =
				shippingDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel3;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel3WithTaxAmount() {
		return shippingDiscountPercentageLevel3WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel3WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel3WithTaxAmount) {

		this.shippingDiscountPercentageLevel3WithTaxAmount =
			shippingDiscountPercentageLevel3WithTaxAmount;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel3WithTaxAmount =
				shippingDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel3WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel4() {
		return shippingDiscountPercentageLevel4;
	}

	public void setShippingDiscountPercentageLevel4(
		BigDecimal shippingDiscountPercentageLevel4) {

		this.shippingDiscountPercentageLevel4 =
			shippingDiscountPercentageLevel4;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel4(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel4UnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel4 =
				shippingDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel4;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingDiscountPercentageLevel4WithTaxAmount() {
		return shippingDiscountPercentageLevel4WithTaxAmount;
	}

	public void setShippingDiscountPercentageLevel4WithTaxAmount(
		BigDecimal shippingDiscountPercentageLevel4WithTaxAmount) {

		this.shippingDiscountPercentageLevel4WithTaxAmount =
			shippingDiscountPercentageLevel4WithTaxAmount;
	}

	@JsonIgnore
	public void setShippingDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountPercentageLevel4WithTaxAmount =
				shippingDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountPercentageLevel4WithTaxAmount;

	@Schema
	@Valid
	public BigDecimal getShippingDiscountWithTaxAmount() {
		return shippingDiscountWithTaxAmount;
	}

	public void setShippingDiscountWithTaxAmount(
		BigDecimal shippingDiscountWithTaxAmount) {

		this.shippingDiscountWithTaxAmount = shippingDiscountWithTaxAmount;
	}

	@JsonIgnore
	public void setShippingDiscountWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingDiscountWithTaxAmountUnsafeSupplier) {

		try {
			shippingDiscountWithTaxAmount =
				shippingDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingDiscountWithTaxAmount;

	@Schema
	public String getShippingDiscountWithTaxAmountFormatted() {
		return shippingDiscountWithTaxAmountFormatted;
	}

	public void setShippingDiscountWithTaxAmountFormatted(
		String shippingDiscountWithTaxAmountFormatted) {

		this.shippingDiscountWithTaxAmountFormatted =
			shippingDiscountWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setShippingDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			shippingDiscountWithTaxAmountFormatted =
				shippingDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingDiscountWithTaxAmountFormatted;

	@Schema
	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@JsonIgnore
	public void setShippingMethod(
		UnsafeSupplier<String, Exception> shippingMethodUnsafeSupplier) {

		try {
			shippingMethod = shippingMethodUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingMethod;

	@Schema
	public String getShippingOption() {
		return shippingOption;
	}

	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}

	@JsonIgnore
	public void setShippingOption(
		UnsafeSupplier<String, Exception> shippingOptionUnsafeSupplier) {

		try {
			shippingOption = shippingOptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingOption;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getShippingWithTaxAmount() {
		return shippingWithTaxAmount;
	}

	public void setShippingWithTaxAmount(BigDecimal shippingWithTaxAmount) {
		this.shippingWithTaxAmount = shippingWithTaxAmount;
	}

	@JsonIgnore
	public void setShippingWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			shippingWithTaxAmountUnsafeSupplier) {

		try {
			shippingWithTaxAmount = shippingWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal shippingWithTaxAmount;

	@Schema
	public String getShippingWithTaxAmountFormatted() {
		return shippingWithTaxAmountFormatted;
	}

	public void setShippingWithTaxAmountFormatted(
		String shippingWithTaxAmountFormatted) {

		this.shippingWithTaxAmountFormatted = shippingWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setShippingWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingWithTaxAmountFormattedUnsafeSupplier) {

		try {
			shippingWithTaxAmountFormatted =
				shippingWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shippingWithTaxAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getShippingWithTaxAmountValue() {
		return shippingWithTaxAmountValue;
	}

	public void setShippingWithTaxAmountValue(
		Double shippingWithTaxAmountValue) {

		this.shippingWithTaxAmountValue = shippingWithTaxAmountValue;
	}

	@JsonIgnore
	public void setShippingWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			shippingWithTaxAmountValueUnsafeSupplier) {

		try {
			shippingWithTaxAmountValue =
				shippingWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double shippingWithTaxAmountValue;

	@Schema
	@Valid
	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@JsonIgnore
	public void setSubtotal(
		UnsafeSupplier<BigDecimal, Exception> subtotalUnsafeSupplier) {

		try {
			subtotal = subtotalUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotal;

	@Schema
	public Double getSubtotalAmount() {
		return subtotalAmount;
	}

	public void setSubtotalAmount(Double subtotalAmount) {
		this.subtotalAmount = subtotalAmount;
	}

	@JsonIgnore
	public void setSubtotalAmount(
		UnsafeSupplier<Double, Exception> subtotalAmountUnsafeSupplier) {

		try {
			subtotalAmount = subtotalAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Double subtotalAmount;

	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountAmount() {
		return subtotalDiscountAmount;
	}

	public void setSubtotalDiscountAmount(BigDecimal subtotalDiscountAmount) {
		this.subtotalDiscountAmount = subtotalDiscountAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountAmountUnsafeSupplier) {

		try {
			subtotalDiscountAmount = subtotalDiscountAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountAmount;

	@Schema
	public String getSubtotalDiscountAmountFormatted() {
		return subtotalDiscountAmountFormatted;
	}

	public void setSubtotalDiscountAmountFormatted(
		String subtotalDiscountAmountFormatted) {

		this.subtotalDiscountAmountFormatted = subtotalDiscountAmountFormatted;
	}

	@JsonIgnore
	public void setSubtotalDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalDiscountAmountFormattedUnsafeSupplier) {

		try {
			subtotalDiscountAmountFormatted =
				subtotalDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String subtotalDiscountAmountFormatted;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel1() {
		return subtotalDiscountPercentageLevel1;
	}

	public void setSubtotalDiscountPercentageLevel1(
		BigDecimal subtotalDiscountPercentageLevel1) {

		this.subtotalDiscountPercentageLevel1 =
			subtotalDiscountPercentageLevel1;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel1(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel1UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel1 =
				subtotalDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel1;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel1WithTaxAmount() {
		return subtotalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel1WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount) {

		this.subtotalDiscountPercentageLevel1WithTaxAmount =
			subtotalDiscountPercentageLevel1WithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel1WithTaxAmount =
				subtotalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel2() {
		return subtotalDiscountPercentageLevel2;
	}

	public void setSubtotalDiscountPercentageLevel2(
		BigDecimal subtotalDiscountPercentageLevel2) {

		this.subtotalDiscountPercentageLevel2 =
			subtotalDiscountPercentageLevel2;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel2(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel2UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel2 =
				subtotalDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel2;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel2WithTaxAmount() {
		return subtotalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel2WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount) {

		this.subtotalDiscountPercentageLevel2WithTaxAmount =
			subtotalDiscountPercentageLevel2WithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel2WithTaxAmount =
				subtotalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel3() {
		return subtotalDiscountPercentageLevel3;
	}

	public void setSubtotalDiscountPercentageLevel3(
		BigDecimal subtotalDiscountPercentageLevel3) {

		this.subtotalDiscountPercentageLevel3 =
			subtotalDiscountPercentageLevel3;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel3(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel3UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel3 =
				subtotalDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel3;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel3WithTaxAmount() {
		return subtotalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel3WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount) {

		this.subtotalDiscountPercentageLevel3WithTaxAmount =
			subtotalDiscountPercentageLevel3WithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel3WithTaxAmount =
				subtotalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel4() {
		return subtotalDiscountPercentageLevel4;
	}

	public void setSubtotalDiscountPercentageLevel4(
		BigDecimal subtotalDiscountPercentageLevel4) {

		this.subtotalDiscountPercentageLevel4 =
			subtotalDiscountPercentageLevel4;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel4(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel4UnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel4 =
				subtotalDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel4;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountPercentageLevel4WithTaxAmount() {
		return subtotalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setSubtotalDiscountPercentageLevel4WithTaxAmount(
		BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount) {

		this.subtotalDiscountPercentageLevel4WithTaxAmount =
			subtotalDiscountPercentageLevel4WithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountPercentageLevel4WithTaxAmount =
				subtotalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.
					get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount;

	@Schema
	@Valid
	public BigDecimal getSubtotalDiscountWithTaxAmount() {
		return subtotalDiscountWithTaxAmount;
	}

	public void setSubtotalDiscountWithTaxAmount(
		BigDecimal subtotalDiscountWithTaxAmount) {

		this.subtotalDiscountWithTaxAmount = subtotalDiscountWithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalDiscountWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalDiscountWithTaxAmountUnsafeSupplier) {

		try {
			subtotalDiscountWithTaxAmount =
				subtotalDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalDiscountWithTaxAmount;

	@Schema
	public String getSubtotalDiscountWithTaxAmountFormatted() {
		return subtotalDiscountWithTaxAmountFormatted;
	}

	public void setSubtotalDiscountWithTaxAmountFormatted(
		String subtotalDiscountWithTaxAmountFormatted) {

		this.subtotalDiscountWithTaxAmountFormatted =
			subtotalDiscountWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setSubtotalDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			subtotalDiscountWithTaxAmountFormatted =
				subtotalDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String subtotalDiscountWithTaxAmountFormatted;

	@Schema
	public String getSubtotalFormatted() {
		return subtotalFormatted;
	}

	public void setSubtotalFormatted(String subtotalFormatted) {
		this.subtotalFormatted = subtotalFormatted;
	}

	@JsonIgnore
	public void setSubtotalFormatted(
		UnsafeSupplier<String, Exception> subtotalFormattedUnsafeSupplier) {

		try {
			subtotalFormatted = subtotalFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String subtotalFormatted;

	@Schema
	@Valid
	public BigDecimal getSubtotalWithTaxAmount() {
		return subtotalWithTaxAmount;
	}

	public void setSubtotalWithTaxAmount(BigDecimal subtotalWithTaxAmount) {
		this.subtotalWithTaxAmount = subtotalWithTaxAmount;
	}

	@JsonIgnore
	public void setSubtotalWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			subtotalWithTaxAmountUnsafeSupplier) {

		try {
			subtotalWithTaxAmount = subtotalWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal subtotalWithTaxAmount;

	@Schema
	public String getSubtotalWithTaxAmountFormatted() {
		return subtotalWithTaxAmountFormatted;
	}

	public void setSubtotalWithTaxAmountFormatted(
		String subtotalWithTaxAmountFormatted) {

		this.subtotalWithTaxAmountFormatted = subtotalWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setSubtotalWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			subtotalWithTaxAmountFormattedUnsafeSupplier) {

		try {
			subtotalWithTaxAmountFormatted =
				subtotalWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String subtotalWithTaxAmountFormatted;

	@Schema
	public Double getSubtotalWithTaxAmountValue() {
		return subtotalWithTaxAmountValue;
	}

	public void setSubtotalWithTaxAmountValue(
		Double subtotalWithTaxAmountValue) {

		this.subtotalWithTaxAmountValue = subtotalWithTaxAmountValue;
	}

	@JsonIgnore
	public void setSubtotalWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			subtotalWithTaxAmountValueUnsafeSupplier) {

		try {
			subtotalWithTaxAmountValue =
				subtotalWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Double subtotalWithTaxAmountValue;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@JsonIgnore
	public void setTaxAmount(
		UnsafeSupplier<BigDecimal, Exception> taxAmountUnsafeSupplier) {

		try {
			taxAmount = taxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal taxAmount;

	@Schema
	public String getTaxAmountFormatted() {
		return taxAmountFormatted;
	}

	public void setTaxAmountFormatted(String taxAmountFormatted) {
		this.taxAmountFormatted = taxAmountFormatted;
	}

	@JsonIgnore
	public void setTaxAmountFormatted(
		UnsafeSupplier<String, Exception> taxAmountFormattedUnsafeSupplier) {

		try {
			taxAmountFormatted = taxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String taxAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getTaxAmountValue() {
		return taxAmountValue;
	}

	public void setTaxAmountValue(Double taxAmountValue) {
		this.taxAmountValue = taxAmountValue;
	}

	@JsonIgnore
	public void setTaxAmountValue(
		UnsafeSupplier<Double, Exception> taxAmountValueUnsafeSupplier) {

		try {
			taxAmountValue = taxAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double taxAmountValue;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@JsonIgnore
	public void setTotal(
		UnsafeSupplier<BigDecimal, Exception> totalUnsafeSupplier) {

		try {
			total = totalUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal total;

	@DecimalMin("0")
	@Schema
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@JsonIgnore
	public void setTotalAmount(
		UnsafeSupplier<Double, Exception> totalAmountUnsafeSupplier) {

		try {
			totalAmount = totalAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Double totalAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	@JsonIgnore
	public void setTotalDiscountAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountAmountUnsafeSupplier) {

		try {
			totalDiscountAmount = totalDiscountAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountAmount;

	@Schema
	public String getTotalDiscountAmountFormatted() {
		return totalDiscountAmountFormatted;
	}

	public void setTotalDiscountAmountFormatted(
		String totalDiscountAmountFormatted) {

		this.totalDiscountAmountFormatted = totalDiscountAmountFormatted;
	}

	@JsonIgnore
	public void setTotalDiscountAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalDiscountAmountFormattedUnsafeSupplier) {

		try {
			totalDiscountAmountFormatted =
				totalDiscountAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String totalDiscountAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getTotalDiscountAmountValue() {
		return totalDiscountAmountValue;
	}

	public void setTotalDiscountAmountValue(Double totalDiscountAmountValue) {
		this.totalDiscountAmountValue = totalDiscountAmountValue;
	}

	@JsonIgnore
	public void setTotalDiscountAmountValue(
		UnsafeSupplier<Double, Exception>
			totalDiscountAmountValueUnsafeSupplier) {

		try {
			totalDiscountAmountValue =
				totalDiscountAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double totalDiscountAmountValue;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel1() {
		return totalDiscountPercentageLevel1;
	}

	public void setTotalDiscountPercentageLevel1(
		BigDecimal totalDiscountPercentageLevel1) {

		this.totalDiscountPercentageLevel1 = totalDiscountPercentageLevel1;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel1(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel1UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel1 =
				totalDiscountPercentageLevel1UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel1;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel1WithTaxAmount() {
		return totalDiscountPercentageLevel1WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel1WithTaxAmount) {

		this.totalDiscountPercentageLevel1WithTaxAmount =
			totalDiscountPercentageLevel1WithTaxAmount;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel1WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel1WithTaxAmount =
				totalDiscountPercentageLevel1WithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel1WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel2() {
		return totalDiscountPercentageLevel2;
	}

	public void setTotalDiscountPercentageLevel2(
		BigDecimal totalDiscountPercentageLevel2) {

		this.totalDiscountPercentageLevel2 = totalDiscountPercentageLevel2;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel2(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel2UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel2 =
				totalDiscountPercentageLevel2UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel2;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel2WithTaxAmount() {
		return totalDiscountPercentageLevel2WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel2WithTaxAmount) {

		this.totalDiscountPercentageLevel2WithTaxAmount =
			totalDiscountPercentageLevel2WithTaxAmount;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel2WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel2WithTaxAmount =
				totalDiscountPercentageLevel2WithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel2WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel3() {
		return totalDiscountPercentageLevel3;
	}

	public void setTotalDiscountPercentageLevel3(
		BigDecimal totalDiscountPercentageLevel3) {

		this.totalDiscountPercentageLevel3 = totalDiscountPercentageLevel3;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel3(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel3UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel3 =
				totalDiscountPercentageLevel3UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel3;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel3WithTaxAmount() {
		return totalDiscountPercentageLevel3WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel3WithTaxAmount) {

		this.totalDiscountPercentageLevel3WithTaxAmount =
			totalDiscountPercentageLevel3WithTaxAmount;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel3WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel3WithTaxAmount =
				totalDiscountPercentageLevel3WithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel3WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel4() {
		return totalDiscountPercentageLevel4;
	}

	public void setTotalDiscountPercentageLevel4(
		BigDecimal totalDiscountPercentageLevel4) {

		this.totalDiscountPercentageLevel4 = totalDiscountPercentageLevel4;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel4(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel4UnsafeSupplier) {

		try {
			totalDiscountPercentageLevel4 =
				totalDiscountPercentageLevel4UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel4;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountPercentageLevel4WithTaxAmount() {
		return totalDiscountPercentageLevel4WithTaxAmount;
	}

	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		BigDecimal totalDiscountPercentageLevel4WithTaxAmount) {

		this.totalDiscountPercentageLevel4WithTaxAmount =
			totalDiscountPercentageLevel4WithTaxAmount;
	}

	@JsonIgnore
	public void setTotalDiscountPercentageLevel4WithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountPercentageLevel4WithTaxAmount =
				totalDiscountPercentageLevel4WithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountPercentageLevel4WithTaxAmount;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalDiscountWithTaxAmount() {
		return totalDiscountWithTaxAmount;
	}

	public void setTotalDiscountWithTaxAmount(
		BigDecimal totalDiscountWithTaxAmount) {

		this.totalDiscountWithTaxAmount = totalDiscountWithTaxAmount;
	}

	@JsonIgnore
	public void setTotalDiscountWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalDiscountWithTaxAmountUnsafeSupplier) {

		try {
			totalDiscountWithTaxAmount =
				totalDiscountWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalDiscountWithTaxAmount;

	@Schema
	public String getTotalDiscountWithTaxAmountFormatted() {
		return totalDiscountWithTaxAmountFormatted;
	}

	public void setTotalDiscountWithTaxAmountFormatted(
		String totalDiscountWithTaxAmountFormatted) {

		this.totalDiscountWithTaxAmountFormatted =
			totalDiscountWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setTotalDiscountWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalDiscountWithTaxAmountFormattedUnsafeSupplier) {

		try {
			totalDiscountWithTaxAmountFormatted =
				totalDiscountWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String totalDiscountWithTaxAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getTotalDiscountWithTaxAmountValue() {
		return totalDiscountWithTaxAmountValue;
	}

	public void setTotalDiscountWithTaxAmountValue(
		Double totalDiscountWithTaxAmountValue) {

		this.totalDiscountWithTaxAmountValue = totalDiscountWithTaxAmountValue;
	}

	@JsonIgnore
	public void setTotalDiscountWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			totalDiscountWithTaxAmountValueUnsafeSupplier) {

		try {
			totalDiscountWithTaxAmountValue =
				totalDiscountWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double totalDiscountWithTaxAmountValue;

	@Schema
	public String getTotalFormatted() {
		return totalFormatted;
	}

	public void setTotalFormatted(String totalFormatted) {
		this.totalFormatted = totalFormatted;
	}

	@JsonIgnore
	public void setTotalFormatted(
		UnsafeSupplier<String, Exception> totalFormattedUnsafeSupplier) {

		try {
			totalFormatted = totalFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String totalFormatted;

	@DecimalMin("0")
	@Schema
	@Valid
	public BigDecimal getTotalWithTaxAmount() {
		return totalWithTaxAmount;
	}

	public void setTotalWithTaxAmount(BigDecimal totalWithTaxAmount) {
		this.totalWithTaxAmount = totalWithTaxAmount;
	}

	@JsonIgnore
	public void setTotalWithTaxAmount(
		UnsafeSupplier<BigDecimal, Exception>
			totalWithTaxAmountUnsafeSupplier) {

		try {
			totalWithTaxAmount = totalWithTaxAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal totalWithTaxAmount;

	@Schema
	public String getTotalWithTaxAmountFormatted() {
		return totalWithTaxAmountFormatted;
	}

	public void setTotalWithTaxAmountFormatted(
		String totalWithTaxAmountFormatted) {

		this.totalWithTaxAmountFormatted = totalWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setTotalWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			totalWithTaxAmountFormattedUnsafeSupplier) {

		try {
			totalWithTaxAmountFormatted =
				totalWithTaxAmountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String totalWithTaxAmountFormatted;

	@DecimalMin("0")
	@Schema
	public Double getTotalWithTaxAmountValue() {
		return totalWithTaxAmountValue;
	}

	public void setTotalWithTaxAmountValue(Double totalWithTaxAmountValue) {
		this.totalWithTaxAmountValue = totalWithTaxAmountValue;
	}

	@JsonIgnore
	public void setTotalWithTaxAmountValue(
		UnsafeSupplier<Double, Exception>
			totalWithTaxAmountValueUnsafeSupplier) {

		try {
			totalWithTaxAmountValue =
				totalWithTaxAmountValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Double totalWithTaxAmountValue;

	@Schema
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@JsonIgnore
	public void setTransactionId(
		UnsafeSupplier<String, Exception> transactionIdUnsafeSupplier) {

		try {
			transactionId = transactionIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String transactionId;

	@Schema
	@Valid
	public Status getWorkflowStatusInfo() {
		return workflowStatusInfo;
	}

	public void setWorkflowStatusInfo(Status workflowStatusInfo) {
		this.workflowStatusInfo = workflowStatusInfo;
	}

	@JsonIgnore
	public void setWorkflowStatusInfo(
		UnsafeSupplier<Status, Exception> workflowStatusInfoUnsafeSupplier) {

		try {
			workflowStatusInfo = workflowStatusInfoUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Status workflowStatusInfo;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (account != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(account));
		}

		if (accountExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(accountExternalReferenceCode));

			sb.append("\"");
		}

		if (accountId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(accountId);
		}

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (advanceStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"advanceStatus\": ");

			sb.append("\"");

			sb.append(_escape(advanceStatus));

			sb.append("\"");
		}

		if (billingAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddress\": ");

			sb.append(String.valueOf(billingAddress));
		}

		if (billingAddressId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddressId\": ");

			sb.append(billingAddressId);
		}

		if (channel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channel\": ");

			sb.append(String.valueOf(channel));
		}

		if (channelExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(channelExternalReferenceCode));

			sb.append("\"");
		}

		if (channelId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(channelId);
		}

		if (couponCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"couponCode\": ");

			sb.append("\"");

			sb.append(_escape(couponCode));

			sb.append("\"");
		}

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		if (currencyCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(currencyCode));

			sb.append("\"");
		}

		if (customFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(customFields));
		}

		if (deliveryTermDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deliveryTermDescription\": ");

			sb.append("\"");

			sb.append(_escape(deliveryTermDescription));

			sb.append("\"");
		}

		if (deliveryTermId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deliveryTermId\": ");

			sb.append(deliveryTermId);
		}

		if (deliveryTermName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deliveryTermName\": ");

			sb.append("\"");

			sb.append(_escape(deliveryTermName));

			sb.append("\"");
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (lastPriceUpdateDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPriceUpdateDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(lastPriceUpdateDate));

			sb.append("\"");
		}

		if (modifiedDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(modifiedDate));

			sb.append("\"");
		}

		if (orderDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(orderDate));

			sb.append("\"");
		}

		if (orderItems != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderItems\": ");

			sb.append("[");

			for (int i = 0; i < orderItems.length; i++) {
				sb.append(String.valueOf(orderItems[i]));

				if ((i + 1) < orderItems.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderStatus\": ");

			sb.append(orderStatus);
		}

		if (orderStatusInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderStatusInfo\": ");

			sb.append(String.valueOf(orderStatusInfo));
		}

		if (orderTypeExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(orderTypeExternalReferenceCode));

			sb.append("\"");
		}

		if (orderTypeId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeId\": ");

			sb.append(orderTypeId);
		}

		if (paymentMethod != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethod\": ");

			sb.append("\"");

			sb.append(_escape(paymentMethod));

			sb.append("\"");
		}

		if (paymentStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatus\": ");

			sb.append(paymentStatus);
		}

		if (paymentStatusInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatusInfo\": ");

			sb.append(String.valueOf(paymentStatusInfo));
		}

		if (paymentTermDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentTermDescription\": ");

			sb.append("\"");

			sb.append(_escape(paymentTermDescription));

			sb.append("\"");
		}

		if (paymentTermId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentTermId\": ");

			sb.append(paymentTermId);
		}

		if (paymentTermName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentTermName\": ");

			sb.append("\"");

			sb.append(_escape(paymentTermName));

			sb.append("\"");
		}

		if (printedNote != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"printedNote\": ");

			sb.append("\"");

			sb.append(_escape(printedNote));

			sb.append("\"");
		}

		if (purchaseOrderNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchaseOrderNumber\": ");

			sb.append("\"");

			sb.append(_escape(purchaseOrderNumber));

			sb.append("\"");
		}

		if (requestedDeliveryDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestedDeliveryDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(requestedDeliveryDate));

			sb.append("\"");
		}

		if (shippingAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddress\": ");

			sb.append(String.valueOf(shippingAddress));
		}

		if (shippingAddressId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddressId\": ");

			sb.append(shippingAddressId);
		}

		if (shippingAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmount\": ");

			sb.append(shippingAmount);
		}

		if (shippingAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingAmountFormatted));

			sb.append("\"");
		}

		if (shippingAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmountValue\": ");

			sb.append(shippingAmountValue);
		}

		if (shippingDiscountAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountAmount\": ");

			sb.append(shippingDiscountAmount);
		}

		if (shippingDiscountAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingDiscountAmountFormatted));

			sb.append("\"");
		}

		if (shippingDiscountAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountAmountValue\": ");

			sb.append(shippingDiscountAmountValue);
		}

		if (shippingDiscountPercentageLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel1\": ");

			sb.append(shippingDiscountPercentageLevel1);
		}

		if (shippingDiscountPercentageLevel1WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel1WithTaxAmount\": ");

			sb.append(shippingDiscountPercentageLevel1WithTaxAmount);
		}

		if (shippingDiscountPercentageLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel2\": ");

			sb.append(shippingDiscountPercentageLevel2);
		}

		if (shippingDiscountPercentageLevel2WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel2WithTaxAmount\": ");

			sb.append(shippingDiscountPercentageLevel2WithTaxAmount);
		}

		if (shippingDiscountPercentageLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel3\": ");

			sb.append(shippingDiscountPercentageLevel3);
		}

		if (shippingDiscountPercentageLevel3WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel3WithTaxAmount\": ");

			sb.append(shippingDiscountPercentageLevel3WithTaxAmount);
		}

		if (shippingDiscountPercentageLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel4\": ");

			sb.append(shippingDiscountPercentageLevel4);
		}

		if (shippingDiscountPercentageLevel4WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel4WithTaxAmount\": ");

			sb.append(shippingDiscountPercentageLevel4WithTaxAmount);
		}

		if (shippingDiscountWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountWithTaxAmount\": ");

			sb.append(shippingDiscountWithTaxAmount);
		}

		if (shippingDiscountWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingDiscountWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (shippingMethod != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingMethod\": ");

			sb.append("\"");

			sb.append(_escape(shippingMethod));

			sb.append("\"");
		}

		if (shippingOption != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingOption\": ");

			sb.append("\"");

			sb.append(_escape(shippingOption));

			sb.append("\"");
		}

		if (shippingWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingWithTaxAmount\": ");

			sb.append(shippingWithTaxAmount);
		}

		if (shippingWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (shippingWithTaxAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingWithTaxAmountValue\": ");

			sb.append(shippingWithTaxAmountValue);
		}

		if (subtotal != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotal\": ");

			sb.append(subtotal);
		}

		if (subtotalAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalAmount\": ");

			sb.append(subtotalAmount);
		}

		if (subtotalDiscountAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountAmount\": ");

			sb.append(subtotalDiscountAmount);
		}

		if (subtotalDiscountAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalDiscountAmountFormatted));

			sb.append("\"");
		}

		if (subtotalDiscountPercentageLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel1\": ");

			sb.append(subtotalDiscountPercentageLevel1);
		}

		if (subtotalDiscountPercentageLevel1WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel1WithTaxAmount\": ");

			sb.append(subtotalDiscountPercentageLevel1WithTaxAmount);
		}

		if (subtotalDiscountPercentageLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel2\": ");

			sb.append(subtotalDiscountPercentageLevel2);
		}

		if (subtotalDiscountPercentageLevel2WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel2WithTaxAmount\": ");

			sb.append(subtotalDiscountPercentageLevel2WithTaxAmount);
		}

		if (subtotalDiscountPercentageLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel3\": ");

			sb.append(subtotalDiscountPercentageLevel3);
		}

		if (subtotalDiscountPercentageLevel3WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel3WithTaxAmount\": ");

			sb.append(subtotalDiscountPercentageLevel3WithTaxAmount);
		}

		if (subtotalDiscountPercentageLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel4\": ");

			sb.append(subtotalDiscountPercentageLevel4);
		}

		if (subtotalDiscountPercentageLevel4WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel4WithTaxAmount\": ");

			sb.append(subtotalDiscountPercentageLevel4WithTaxAmount);
		}

		if (subtotalDiscountWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountWithTaxAmount\": ");

			sb.append(subtotalDiscountWithTaxAmount);
		}

		if (subtotalDiscountWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalDiscountWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (subtotalFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalFormatted));

			sb.append("\"");
		}

		if (subtotalWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalWithTaxAmount\": ");

			sb.append(subtotalWithTaxAmount);
		}

		if (subtotalWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (subtotalWithTaxAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalWithTaxAmountValue\": ");

			sb.append(subtotalWithTaxAmountValue);
		}

		if (taxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxAmount\": ");

			sb.append(taxAmount);
		}

		if (taxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(taxAmountFormatted));

			sb.append("\"");
		}

		if (taxAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxAmountValue\": ");

			sb.append(taxAmountValue);
		}

		if (total != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(total);
		}

		if (totalAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalAmount\": ");

			sb.append(totalAmount);
		}

		if (totalDiscountAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountAmount\": ");

			sb.append(totalDiscountAmount);
		}

		if (totalDiscountAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalDiscountAmountFormatted));

			sb.append("\"");
		}

		if (totalDiscountAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountAmountValue\": ");

			sb.append(totalDiscountAmountValue);
		}

		if (totalDiscountPercentageLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel1\": ");

			sb.append(totalDiscountPercentageLevel1);
		}

		if (totalDiscountPercentageLevel1WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel1WithTaxAmount\": ");

			sb.append(totalDiscountPercentageLevel1WithTaxAmount);
		}

		if (totalDiscountPercentageLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel2\": ");

			sb.append(totalDiscountPercentageLevel2);
		}

		if (totalDiscountPercentageLevel2WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel2WithTaxAmount\": ");

			sb.append(totalDiscountPercentageLevel2WithTaxAmount);
		}

		if (totalDiscountPercentageLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel3\": ");

			sb.append(totalDiscountPercentageLevel3);
		}

		if (totalDiscountPercentageLevel3WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel3WithTaxAmount\": ");

			sb.append(totalDiscountPercentageLevel3WithTaxAmount);
		}

		if (totalDiscountPercentageLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel4\": ");

			sb.append(totalDiscountPercentageLevel4);
		}

		if (totalDiscountPercentageLevel4WithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel4WithTaxAmount\": ");

			sb.append(totalDiscountPercentageLevel4WithTaxAmount);
		}

		if (totalDiscountWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountWithTaxAmount\": ");

			sb.append(totalDiscountWithTaxAmount);
		}

		if (totalDiscountWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalDiscountWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (totalDiscountWithTaxAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountWithTaxAmountValue\": ");

			sb.append(totalDiscountWithTaxAmountValue);
		}

		if (totalFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalFormatted));

			sb.append("\"");
		}

		if (totalWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalWithTaxAmount\": ");

			sb.append(totalWithTaxAmount);
		}

		if (totalWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (totalWithTaxAmountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalWithTaxAmountValue\": ");

			sb.append(totalWithTaxAmountValue);
		}

		if (transactionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transactionId\": ");

			sb.append("\"");

			sb.append(_escape(transactionId));

			sb.append("\"");
		}

		if (workflowStatusInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(workflowStatusInfo));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.order.dto.v1_0.Order",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}