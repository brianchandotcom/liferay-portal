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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.cart.service.http.CCartServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.cart.service.http.CCartServiceSoap
 * @generated
 */
@ProviderType
public class CCartSoap implements Serializable {
	public static CCartSoap toSoapModel(CCart model) {
		CCartSoap soapModel = new CCartSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCCartId(model.getCCartId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCartUserId(model.getCartUserId());
		soapModel.setTitle(model.getTitle());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static CCartSoap[] toSoapModels(CCart[] models) {
		CCartSoap[] soapModels = new CCartSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CCartSoap[][] toSoapModels(CCart[][] models) {
		CCartSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CCartSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CCartSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CCartSoap[] toSoapModels(List<CCart> models) {
		List<CCartSoap> soapModels = new ArrayList<CCartSoap>(models.size());

		for (CCart model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CCartSoap[soapModels.size()]);
	}

	public CCartSoap() {
	}

	public long getPrimaryKey() {
		return _CCartId;
	}

	public void setPrimaryKey(long pk) {
		setCCartId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCCartId() {
		return _CCartId;
	}

	public void setCCartId(long CCartId) {
		_CCartId = CCartId;
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

	public long getCartUserId() {
		return _cartUserId;
	}

	public void setCartUserId(long cartUserId) {
		_cartUserId = cartUserId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _uuid;
	private long _CCartId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _cartUserId;
	private String _title;
	private int _type;
}