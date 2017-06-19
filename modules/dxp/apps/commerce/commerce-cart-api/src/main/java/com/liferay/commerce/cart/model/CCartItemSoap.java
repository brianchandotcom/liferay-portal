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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.cart.service.http.CCartItemServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.cart.service.http.CCartItemServiceSoap
 * @generated
 */
@ProviderType
public class CCartItemSoap implements Serializable {
	public static CCartItemSoap toSoapModel(CCartItem model) {
		CCartItemSoap soapModel = new CCartItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCCartItemId(model.getCCartItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCCartId(model.getCCartId());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setJson(model.getJson());

		return soapModel;
	}

	public static CCartItemSoap[] toSoapModels(CCartItem[] models) {
		CCartItemSoap[] soapModels = new CCartItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CCartItemSoap[][] toSoapModels(CCartItem[][] models) {
		CCartItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CCartItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CCartItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CCartItemSoap[] toSoapModels(List<CCartItem> models) {
		List<CCartItemSoap> soapModels = new ArrayList<CCartItemSoap>(models.size());

		for (CCartItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CCartItemSoap[soapModels.size()]);
	}

	public CCartItemSoap() {
	}

	public long getPrimaryKey() {
		return _CCartItemId;
	}

	public void setPrimaryKey(long pk) {
		setCCartItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCCartItemId() {
		return _CCartItemId;
	}

	public void setCCartItemId(long CCartItemId) {
		_CCartItemId = CCartItemId;
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

	public long getCCartId() {
		return _CCartId;
	}

	public void setCCartId(long CCartId) {
		_CCartId = CCartId;
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

	private String _uuid;
	private long _CCartItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CCartId;
	private long _CPDefinitionId;
	private long _CPInstanceId;
	private int _quantity;
	private String _json;
}