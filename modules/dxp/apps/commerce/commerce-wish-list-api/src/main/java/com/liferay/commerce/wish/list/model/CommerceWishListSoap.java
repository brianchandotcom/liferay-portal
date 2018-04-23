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

package com.liferay.commerce.wish.list.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.wish.list.service.http.CommerceWishListServiceSoap}.
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.wish.list.service.http.CommerceWishListServiceSoap
 * @generated
 */
@ProviderType
public class CommerceWishListSoap implements Serializable {
	public static CommerceWishListSoap toSoapModel(CommerceWishList model) {
		CommerceWishListSoap soapModel = new CommerceWishListSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceWishListId(model.getCommerceWishListId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDefaultWishList(model.isDefaultWishList());

		return soapModel;
	}

	public static CommerceWishListSoap[] toSoapModels(CommerceWishList[] models) {
		CommerceWishListSoap[] soapModels = new CommerceWishListSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceWishListSoap[][] toSoapModels(
		CommerceWishList[][] models) {
		CommerceWishListSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceWishListSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceWishListSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceWishListSoap[] toSoapModels(
		List<CommerceWishList> models) {
		List<CommerceWishListSoap> soapModels = new ArrayList<CommerceWishListSoap>(models.size());

		for (CommerceWishList model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceWishListSoap[soapModels.size()]);
	}

	public CommerceWishListSoap() {
	}

	public long getPrimaryKey() {
		return _commerceWishListId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceWishListId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceWishListId() {
		return _commerceWishListId;
	}

	public void setCommerceWishListId(long commerceWishListId) {
		_commerceWishListId = commerceWishListId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean getDefaultWishList() {
		return _defaultWishList;
	}

	public boolean isDefaultWishList() {
		return _defaultWishList;
	}

	public void setDefaultWishList(boolean defaultWishList) {
		_defaultWishList = defaultWishList;
	}

	private String _uuid;
	private long _commerceWishListId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private boolean _defaultWishList;
}