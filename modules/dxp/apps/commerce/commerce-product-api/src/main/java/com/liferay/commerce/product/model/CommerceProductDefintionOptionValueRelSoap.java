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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceProductDefintionOptionValueRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CommerceProductDefintionOptionValueRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionValueRelSoap implements Serializable {
	public static CommerceProductDefintionOptionValueRelSoap toSoapModel(
		CommerceProductDefintionOptionValueRel model) {
		CommerceProductDefintionOptionValueRelSoap soapModel = new CommerceProductDefintionOptionValueRelSoap();

		soapModel.setCommerceProductDefintionOptionValueRelId(model.getCommerceProductDefintionOptionValueRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceProductDefintionOptionRelId(model.getCommerceProductDefintionOptionRelId());
		soapModel.setTitle(model.getTitle());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceProductDefintionOptionValueRelSoap[] toSoapModels(
		CommerceProductDefintionOptionValueRel[] models) {
		CommerceProductDefintionOptionValueRelSoap[] soapModels = new CommerceProductDefintionOptionValueRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefintionOptionValueRelSoap[][] toSoapModels(
		CommerceProductDefintionOptionValueRel[][] models) {
		CommerceProductDefintionOptionValueRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductDefintionOptionValueRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductDefintionOptionValueRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefintionOptionValueRelSoap[] toSoapModels(
		List<CommerceProductDefintionOptionValueRel> models) {
		List<CommerceProductDefintionOptionValueRelSoap> soapModels = new ArrayList<CommerceProductDefintionOptionValueRelSoap>(models.size());

		for (CommerceProductDefintionOptionValueRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductDefintionOptionValueRelSoap[soapModels.size()]);
	}

	public CommerceProductDefintionOptionValueRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductDefintionOptionValueRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductDefintionOptionValueRelId(pk);
	}

	public long getCommerceProductDefintionOptionValueRelId() {
		return _commerceProductDefintionOptionValueRelId;
	}

	public void setCommerceProductDefintionOptionValueRelId(
		long commerceProductDefintionOptionValueRelId) {
		_commerceProductDefintionOptionValueRelId = commerceProductDefintionOptionValueRelId;
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

	public long getCommerceProductDefintionOptionRelId() {
		return _commerceProductDefintionOptionRelId;
	}

	public void setCommerceProductDefintionOptionRelId(
		long commerceProductDefintionOptionRelId) {
		_commerceProductDefintionOptionRelId = commerceProductDefintionOptionRelId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public long getPriority() {
		return _priority;
	}

	public void setPriority(long priority) {
		_priority = priority;
	}

	private long _commerceProductDefintionOptionValueRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceProductDefintionOptionRelId;
	private String _title;
	private long _priority;
}