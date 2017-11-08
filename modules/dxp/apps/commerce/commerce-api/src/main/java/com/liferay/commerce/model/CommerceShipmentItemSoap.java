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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceShipmentItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceShipmentItemServiceSoap
 * @generated
 */
@ProviderType
public class CommerceShipmentItemSoap implements Serializable {
	public static CommerceShipmentItemSoap toSoapModel(
		CommerceShipmentItem model) {
		CommerceShipmentItemSoap soapModel = new CommerceShipmentItemSoap();

		soapModel.setCommerceShipmentItemId(model.getCommerceShipmentItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceShipmentId(model.getCommerceShipmentId());
		soapModel.setCommerceOrderItemId(model.getCommerceOrderItemId());
		soapModel.setQuantity(model.getQuantity());

		return soapModel;
	}

	public static CommerceShipmentItemSoap[] toSoapModels(
		CommerceShipmentItem[] models) {
		CommerceShipmentItemSoap[] soapModels = new CommerceShipmentItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceShipmentItemSoap[][] toSoapModels(
		CommerceShipmentItem[][] models) {
		CommerceShipmentItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceShipmentItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceShipmentItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceShipmentItemSoap[] toSoapModels(
		List<CommerceShipmentItem> models) {
		List<CommerceShipmentItemSoap> soapModels = new ArrayList<CommerceShipmentItemSoap>(models.size());

		for (CommerceShipmentItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceShipmentItemSoap[soapModels.size()]);
	}

	public CommerceShipmentItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceShipmentItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceShipmentItemId(pk);
	}

	public long getCommerceShipmentItemId() {
		return _commerceShipmentItemId;
	}

	public void setCommerceShipmentItemId(long commerceShipmentItemId) {
		_commerceShipmentItemId = commerceShipmentItemId;
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

	public long getCommerceShipmentId() {
		return _commerceShipmentId;
	}

	public void setCommerceShipmentId(long commerceShipmentId) {
		_commerceShipmentId = commerceShipmentId;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	private long _commerceShipmentItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceShipmentId;
	private long _commerceOrderItemId;
	private int _quantity;
}