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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceWarehouseItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceWarehouseItemServiceSoap
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemSoap implements Serializable {
	public static CommerceWarehouseItemSoap toSoapModel(
		CommerceWarehouseItem model) {
		CommerceWarehouseItemSoap soapModel = new CommerceWarehouseItemSoap();

		soapModel.setCommerceWarehouseItemId(model.getCommerceWarehouseItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceWarehouseId(model.getCommerceWarehouseId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setQuantity(model.getQuantity());

		return soapModel;
	}

	public static CommerceWarehouseItemSoap[] toSoapModels(
		CommerceWarehouseItem[] models) {
		CommerceWarehouseItemSoap[] soapModels = new CommerceWarehouseItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceWarehouseItemSoap[][] toSoapModels(
		CommerceWarehouseItem[][] models) {
		CommerceWarehouseItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceWarehouseItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceWarehouseItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceWarehouseItemSoap[] toSoapModels(
		List<CommerceWarehouseItem> models) {
		List<CommerceWarehouseItemSoap> soapModels = new ArrayList<CommerceWarehouseItemSoap>(models.size());

		for (CommerceWarehouseItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceWarehouseItemSoap[soapModels.size()]);
	}

	public CommerceWarehouseItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceWarehouseItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceWarehouseItemId(pk);
	}

	public long getCommerceWarehouseItemId() {
		return _commerceWarehouseItemId;
	}

	public void setCommerceWarehouseItemId(long commerceWarehouseItemId) {
		_commerceWarehouseItemId = commerceWarehouseItemId;
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

	public long getCommerceWarehouseId() {
		return _commerceWarehouseId;
	}

	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceWarehouseId = commerceWarehouseId;
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

	private long _commerceWarehouseItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceWarehouseId;
	private long _CPInstanceId;
	private int _quantity;
}