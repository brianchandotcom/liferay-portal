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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceOrderItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceOrderItemServiceSoap
 * @generated
 */
@ProviderType
public class CommerceOrderItemSoap implements Serializable {
	public static CommerceOrderItemSoap toSoapModel(CommerceOrderItem model) {
		CommerceOrderItemSoap soapModel = new CommerceOrderItemSoap();

		soapModel.setCommerceOrderItemId(model.getCommerceOrderItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceOrderId(model.getCommerceOrderId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setShippedQuantity(model.getShippedQuantity());
		soapModel.setJson(model.getJson());
		soapModel.setName(model.getName());
		soapModel.setSku(model.getSku());
		soapModel.setPrice(model.getPrice());

		return soapModel;
	}

	public static CommerceOrderItemSoap[] toSoapModels(
		CommerceOrderItem[] models) {
		CommerceOrderItemSoap[] soapModels = new CommerceOrderItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderItemSoap[][] toSoapModels(
		CommerceOrderItem[][] models) {
		CommerceOrderItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceOrderItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceOrderItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderItemSoap[] toSoapModels(
		List<CommerceOrderItem> models) {
		List<CommerceOrderItemSoap> soapModels = new ArrayList<CommerceOrderItemSoap>(models.size());

		for (CommerceOrderItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceOrderItemSoap[soapModels.size()]);
	}

	public CommerceOrderItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceOrderItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceOrderItemId(pk);
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
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

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public int getShippedQuantity() {
		return _shippedQuantity;
	}

	public void setShippedQuantity(int shippedQuantity) {
		_shippedQuantity = shippedQuantity;
	}

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	private long _commerceOrderItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceOrderId;
	private long _CPInstanceId;
	private int _quantity;
	private int _shippedQuantity;
	private String _json;
	private String _name;
	private String _sku;
	private BigDecimal _price;
}