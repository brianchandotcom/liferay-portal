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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

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
@ProviderType
public class CommerceOrderWrapper implements CommerceOrder,
	ModelWrapper<CommerceOrder> {
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
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("orderUserId", getOrderUserId());
		attributes.put("billingAddressId", getBillingAddressId());
		attributes.put("shippingAddressId", getShippingAddressId());
		attributes.put("commercePaymentMethodId", getCommercePaymentMethodId());
		attributes.put("commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("shippingOptionName", getShippingOptionName());
		attributes.put("purchaseOrderNumber", getPurchaseOrderNumber());
		attributes.put("subtotal", getSubtotal());
		attributes.put("shippingPrice", getShippingPrice());
		attributes.put("total", getTotal());
		attributes.put("paymentStatus", getPaymentStatus());
		attributes.put("shippingStatus", getShippingStatus());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
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

		Long orderUserId = (Long)attributes.get("orderUserId");

		if (orderUserId != null) {
			setOrderUserId(orderUserId);
		}

		Long billingAddressId = (Long)attributes.get("billingAddressId");

		if (billingAddressId != null) {
			setBillingAddressId(billingAddressId);
		}

		Long shippingAddressId = (Long)attributes.get("shippingAddressId");

		if (shippingAddressId != null) {
			setShippingAddressId(shippingAddressId);
		}

		Long commercePaymentMethodId = (Long)attributes.get(
				"commercePaymentMethodId");

		if (commercePaymentMethodId != null) {
			setCommercePaymentMethodId(commercePaymentMethodId);
		}

		Long commerceShippingMethodId = (Long)attributes.get(
				"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		String shippingOptionName = (String)attributes.get("shippingOptionName");

		if (shippingOptionName != null) {
			setShippingOptionName(shippingOptionName);
		}

		String purchaseOrderNumber = (String)attributes.get(
				"purchaseOrderNumber");

		if (purchaseOrderNumber != null) {
			setPurchaseOrderNumber(purchaseOrderNumber);
		}

		Double subtotal = (Double)attributes.get("subtotal");

		if (subtotal != null) {
			setSubtotal(subtotal);
		}

		Double shippingPrice = (Double)attributes.get("shippingPrice");

		if (shippingPrice != null) {
			setShippingPrice(shippingPrice);
		}

		Double total = (Double)attributes.get("total");

		if (total != null) {
			setTotal(total);
		}

		Integer paymentStatus = (Integer)attributes.get("paymentStatus");

		if (paymentStatus != null) {
			setPaymentStatus(paymentStatus);
		}

		Integer shippingStatus = (Integer)attributes.get("shippingStatus");

		if (shippingStatus != null) {
			setShippingStatus(shippingStatus);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceOrderWrapper((CommerceOrder)_commerceOrder.clone());
	}

	@Override
	public int compareTo(CommerceOrder commerceOrder) {
		return _commerceOrder.compareTo(commerceOrder);
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
	public CommercePaymentMethod getCommercePaymentMethod()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrder.getCommercePaymentMethod();
	}

	/**
	* Returns the commerce payment method ID of this commerce order.
	*
	* @return the commerce payment method ID of this commerce order
	*/
	@Override
	public long getCommercePaymentMethodId() {
		return _commerceOrder.getCommercePaymentMethodId();
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
	* Returns the group ID of this commerce order.
	*
	* @return the group ID of this commerce order
	*/
	@Override
	public long getGroupId() {
		return _commerceOrder.getGroupId();
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

	@Override
	public com.liferay.portal.kernel.model.User getOrderUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrder.getOrderUser();
	}

	/**
	* Returns the order user ID of this commerce order.
	*
	* @return the order user ID of this commerce order
	*/
	@Override
	public long getOrderUserId() {
		return _commerceOrder.getOrderUserId();
	}

	/**
	* Returns the order user uuid of this commerce order.
	*
	* @return the order user uuid of this commerce order
	*/
	@Override
	public java.lang.String getOrderUserUuid() {
		return _commerceOrder.getOrderUserUuid();
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
	* Returns the purchase order number of this commerce order.
	*
	* @return the purchase order number of this commerce order
	*/
	@Override
	public java.lang.String getPurchaseOrderNumber() {
		return _commerceOrder.getPurchaseOrderNumber();
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
	* Returns the shipping option name of this commerce order.
	*
	* @return the shipping option name of this commerce order
	*/
	@Override
	public java.lang.String getShippingOptionName() {
		return _commerceOrder.getShippingOptionName();
	}

	/**
	* Returns the shipping price of this commerce order.
	*
	* @return the shipping price of this commerce order
	*/
	@Override
	public double getShippingPrice() {
		return _commerceOrder.getShippingPrice();
	}

	/**
	* Returns the shipping status of this commerce order.
	*
	* @return the shipping status of this commerce order
	*/
	@Override
	public int getShippingStatus() {
		return _commerceOrder.getShippingStatus();
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
	* Returns the subtotal of this commerce order.
	*
	* @return the subtotal of this commerce order
	*/
	@Override
	public double getSubtotal() {
		return _commerceOrder.getSubtotal();
	}

	/**
	* Returns the total of this commerce order.
	*
	* @return the total of this commerce order
	*/
	@Override
	public double getTotal() {
		return _commerceOrder.getTotal();
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
	public java.lang.String getUserName() {
		return _commerceOrder.getUserName();
	}

	/**
	* Returns the user uuid of this commerce order.
	*
	* @return the user uuid of this commerce order
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceOrder.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce order.
	*
	* @return the uuid of this commerce order
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceOrder.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrder.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrder.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrder.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceOrder.isNew();
	}

	@Override
	public void persist() {
		_commerceOrder.persist();
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
	* Sets the commerce order ID of this commerce order.
	*
	* @param commerceOrderId the commerce order ID of this commerce order
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrder.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the commerce payment method ID of this commerce order.
	*
	* @param commercePaymentMethodId the commerce payment method ID of this commerce order
	*/
	@Override
	public void setCommercePaymentMethodId(long commercePaymentMethodId) {
		_commerceOrder.setCommercePaymentMethodId(commercePaymentMethodId);
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
	* Sets the group ID of this commerce order.
	*
	* @param groupId the group ID of this commerce order
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceOrder.setGroupId(groupId);
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
	* Sets the order user ID of this commerce order.
	*
	* @param orderUserId the order user ID of this commerce order
	*/
	@Override
	public void setOrderUserId(long orderUserId) {
		_commerceOrder.setOrderUserId(orderUserId);
	}

	/**
	* Sets the order user uuid of this commerce order.
	*
	* @param orderUserUuid the order user uuid of this commerce order
	*/
	@Override
	public void setOrderUserUuid(java.lang.String orderUserUuid) {
		_commerceOrder.setOrderUserUuid(orderUserUuid);
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
	* Sets the purchase order number of this commerce order.
	*
	* @param purchaseOrderNumber the purchase order number of this commerce order
	*/
	@Override
	public void setPurchaseOrderNumber(java.lang.String purchaseOrderNumber) {
		_commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
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
	* Sets the shipping option name of this commerce order.
	*
	* @param shippingOptionName the shipping option name of this commerce order
	*/
	@Override
	public void setShippingOptionName(java.lang.String shippingOptionName) {
		_commerceOrder.setShippingOptionName(shippingOptionName);
	}

	/**
	* Sets the shipping price of this commerce order.
	*
	* @param shippingPrice the shipping price of this commerce order
	*/
	@Override
	public void setShippingPrice(double shippingPrice) {
		_commerceOrder.setShippingPrice(shippingPrice);
	}

	/**
	* Sets the shipping status of this commerce order.
	*
	* @param shippingStatus the shipping status of this commerce order
	*/
	@Override
	public void setShippingStatus(int shippingStatus) {
		_commerceOrder.setShippingStatus(shippingStatus);
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
	* Sets the subtotal of this commerce order.
	*
	* @param subtotal the subtotal of this commerce order
	*/
	@Override
	public void setSubtotal(double subtotal) {
		_commerceOrder.setSubtotal(subtotal);
	}

	/**
	* Sets the total of this commerce order.
	*
	* @param total the total of this commerce order
	*/
	@Override
	public void setTotal(double total) {
		_commerceOrder.setTotal(total);
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
	public void setUserName(java.lang.String userName) {
		_commerceOrder.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce order.
	*
	* @param userUuid the user uuid of this commerce order
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceOrder.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce order.
	*
	* @param uuid the uuid of this commerce order
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceOrder.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrder> toCacheModel() {
		return _commerceOrder.toCacheModel();
	}

	@Override
	public CommerceOrder toEscapedModel() {
		return new CommerceOrderWrapper(_commerceOrder.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceOrder.toString();
	}

	@Override
	public CommerceOrder toUnescapedModel() {
		return new CommerceOrderWrapper(_commerceOrder.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceOrder.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderWrapper)) {
			return false;
		}

		CommerceOrderWrapper commerceOrderWrapper = (CommerceOrderWrapper)obj;

		if (Objects.equals(_commerceOrder, commerceOrderWrapper._commerceOrder)) {
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