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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceProductOptionValueServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CommerceProductOptionValueServiceSoap
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueSoap implements Serializable {
	public static CommerceProductOptionValueSoap toSoapModel(
		CommerceProductOptionValue model) {
		CommerceProductOptionValueSoap soapModel = new CommerceProductOptionValueSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceProductOptionValueId(model.getCommerceProductOptionValueId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceProductOptionId(model.getCommerceProductOptionId());
		soapModel.setTitle(model.getTitle());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceProductOptionValueSoap[] toSoapModels(
		CommerceProductOptionValue[] models) {
		CommerceProductOptionValueSoap[] soapModels = new CommerceProductOptionValueSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductOptionValueSoap[][] toSoapModels(
		CommerceProductOptionValue[][] models) {
		CommerceProductOptionValueSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductOptionValueSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductOptionValueSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductOptionValueSoap[] toSoapModels(
		List<CommerceProductOptionValue> models) {
		List<CommerceProductOptionValueSoap> soapModels = new ArrayList<CommerceProductOptionValueSoap>(models.size());

		for (CommerceProductOptionValue model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductOptionValueSoap[soapModels.size()]);
	}

	public CommerceProductOptionValueSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductOptionValueId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductOptionValueId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceProductOptionValueId() {
		return _commerceProductOptionValueId;
	}

	public void setCommerceProductOptionValueId(
		long commerceProductOptionValueId) {
		_commerceProductOptionValueId = commerceProductOptionValueId;
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

	public long getCommerceProductOptionId() {
		return _commerceProductOptionId;
	}

	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOptionId = commerceProductOptionId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private String _uuid;
	private long _commerceProductOptionValueId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceProductOptionId;
	private String _title;
	private int _priority;
}