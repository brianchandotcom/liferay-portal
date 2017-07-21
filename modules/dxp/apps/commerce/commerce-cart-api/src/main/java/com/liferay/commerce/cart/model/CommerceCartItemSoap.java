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

package com.liferay.commerce.cart.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.cart.service.http.CommerceCartItemServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.cart.service.http.CommerceCartItemServiceSoap
 * @generated
 */
@ProviderType
public class CommerceCartItemSoap implements Serializable {
	public static CommerceCartItemSoap toSoapModel(CommerceCartItem model) {
		CommerceCartItemSoap soapModel = new CommerceCartItemSoap();

		soapModel.setCommerceCartItemId(model.getCommerceCartItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceCartId(model.getCommerceCartId());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setJson(model.getJson());

		return soapModel;
	}

	public static CommerceCartItemSoap[] toSoapModels(CommerceCartItem[] models) {
		CommerceCartItemSoap[] soapModels = new CommerceCartItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceCartItemSoap[][] toSoapModels(
		CommerceCartItem[][] models) {
		CommerceCartItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceCartItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceCartItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceCartItemSoap[] toSoapModels(
		List<CommerceCartItem> models) {
		List<CommerceCartItemSoap> soapModels = new ArrayList<CommerceCartItemSoap>(models.size());

		for (CommerceCartItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceCartItemSoap[soapModels.size()]);
	}

	public CommerceCartItemSoap() {
	}

	public long getPrimaryKey() {
		return _commerceCartItemId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceCartItemId(pk);
	}

	public long getCommerceCartItemId() {
		return _commerceCartItemId;
	}

	public void setCommerceCartItemId(long commerceCartItemId) {
		_commerceCartItemId = commerceCartItemId;
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

	public long getCommerceCartId() {
		return _commerceCartId;
	}

	public void setCommerceCartId(long commerceCartId) {
		_commerceCartId = commerceCartId;
	}

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
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

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
	}

	private long _commerceCartItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceCartId;
	private long _CPDefinitionId;
	private long _CPInstanceId;
	private int _quantity;
	private String _json;
}