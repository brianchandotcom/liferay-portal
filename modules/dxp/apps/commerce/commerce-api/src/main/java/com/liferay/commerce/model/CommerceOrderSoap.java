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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceOrderServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceOrderServiceSoap
 * @generated
 */
@ProviderType
public class CommerceOrderSoap implements Serializable {
	public static CommerceOrderSoap toSoapModel(CommerceOrder model) {
		CommerceOrderSoap soapModel = new CommerceOrderSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOrderUserId(model.getOrderUserId());
		soapModel.setBillingAddressId(model.getBillingAddressId());
		soapModel.setShippingAddressId(model.getShippingAddressId());
		soapModel.setCommercePaymentMethodId(model.getCommercePaymentMethodId());
		soapModel.setCommerceShippingMethodId(model.getCommerceShippingMethodId());
		soapModel.setShippingOptionName(model.getShippingOptionName());
		soapModel.setPurchaseOrderNumber(model.getPurchaseOrderNumber());
		soapModel.setSubtotal(model.getSubtotal());
		soapModel.setShippingPrice(model.getShippingPrice());
		soapModel.setTotal(model.getTotal());
		soapModel.setPaymentStatus(model.getPaymentStatus());
		soapModel.setShippingStatus(model.getShippingStatus());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static CommerceOrderSoap[] toSoapModels(CommerceOrder[] models) {
		CommerceOrderSoap[] soapModels = new CommerceOrderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderSoap[][] toSoapModels(CommerceOrder[][] models) {
		CommerceOrderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceOrderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceOrderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderSoap[] toSoapModels(List<CommerceOrder> models) {
		List<CommerceOrderSoap> soapModels = new ArrayList<CommerceOrderSoap>(models.size());

		for (CommerceOrder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceOrderSoap[soapModels.size()]);
	}

	public CommerceOrderSoap() {
	}

	public long getPrimaryKey() {
		return _commerceOrderId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceOrderId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getOrderUserId() {
		return _orderUserId;
	}

	public void setOrderUserId(long orderUserId) {
		_orderUserId = orderUserId;
	}

	public long getBillingAddressId() {
		return _billingAddressId;
	}

	public void setBillingAddressId(long billingAddressId) {
		_billingAddressId = billingAddressId;
	}

	public long getShippingAddressId() {
		return _shippingAddressId;
	}

	public void setShippingAddressId(long shippingAddressId) {
		_shippingAddressId = shippingAddressId;
	}

	public long getCommercePaymentMethodId() {
		return _commercePaymentMethodId;
	}

	public void setCommercePaymentMethodId(long commercePaymentMethodId) {
		_commercePaymentMethodId = commercePaymentMethodId;
	}

	public long getCommerceShippingMethodId() {
		return _commerceShippingMethodId;
	}

	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShippingMethodId = commerceShippingMethodId;
	}

	public String getShippingOptionName() {
		return _shippingOptionName;
	}

	public void setShippingOptionName(String shippingOptionName) {
		_shippingOptionName = shippingOptionName;
	}

	public String getPurchaseOrderNumber() {
		return _purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		_purchaseOrderNumber = purchaseOrderNumber;
	}

	public double getSubtotal() {
		return _subtotal;
	}

	public void setSubtotal(double subtotal) {
		_subtotal = subtotal;
	}

	public double getShippingPrice() {
		return _shippingPrice;
	}

	public void setShippingPrice(double shippingPrice) {
		_shippingPrice = shippingPrice;
	}

	public double getTotal() {
		return _total;
	}

	public void setTotal(double total) {
		_total = total;
	}

	public int getPaymentStatus() {
		return _paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		_paymentStatus = paymentStatus;
	}

	public int getShippingStatus() {
		return _shippingStatus;
	}

	public void setShippingStatus(int shippingStatus) {
		_shippingStatus = shippingStatus;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private String _uuid;
	private long _commerceOrderId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _orderUserId;
	private long _billingAddressId;
	private long _shippingAddressId;
	private long _commercePaymentMethodId;
	private long _commerceShippingMethodId;
	private String _shippingOptionName;
	private String _purchaseOrderNumber;
	private double _subtotal;
	private double _shippingPrice;
	private double _total;
	private int _paymentStatus;
	private int _shippingStatus;
	private int _status;
}