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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceProductDefinitionOptionValueRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CommerceProductDefinitionOptionValueRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelSoap implements Serializable {
	public static CommerceProductDefinitionOptionValueRelSoap toSoapModel(
		CommerceProductDefinitionOptionValueRel model) {
		CommerceProductDefinitionOptionValueRelSoap soapModel = new CommerceProductDefinitionOptionValueRelSoap();

		soapModel.setCommerceProductDefinitionOptionValueRelId(model.getCommerceProductDefinitionOptionValueRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceProductDefinitionOptionRelId(model.getCommerceProductDefinitionOptionRelId());
		soapModel.setTitle(model.getTitle());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceProductDefinitionOptionValueRelSoap[] toSoapModels(
		CommerceProductDefinitionOptionValueRel[] models) {
		CommerceProductDefinitionOptionValueRelSoap[] soapModels = new CommerceProductDefinitionOptionValueRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionOptionValueRelSoap[][] toSoapModels(
		CommerceProductDefinitionOptionValueRel[][] models) {
		CommerceProductDefinitionOptionValueRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductDefinitionOptionValueRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductDefinitionOptionValueRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionOptionValueRelSoap[] toSoapModels(
		List<CommerceProductDefinitionOptionValueRel> models) {
		List<CommerceProductDefinitionOptionValueRelSoap> soapModels = new ArrayList<CommerceProductDefinitionOptionValueRelSoap>(models.size());

		for (CommerceProductDefinitionOptionValueRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductDefinitionOptionValueRelSoap[soapModels.size()]);
	}

	public CommerceProductDefinitionOptionValueRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductDefinitionOptionValueRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductDefinitionOptionValueRelId(pk);
	}

	public long getCommerceProductDefinitionOptionValueRelId() {
		return _commerceProductDefinitionOptionValueRelId;
	}

	public void setCommerceProductDefinitionOptionValueRelId(
		long commerceProductDefinitionOptionValueRelId) {
		_commerceProductDefinitionOptionValueRelId = commerceProductDefinitionOptionValueRelId;
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

	public long getCommerceProductDefinitionOptionRelId() {
		return _commerceProductDefinitionOptionRelId;
	}

	public void setCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId) {
		_commerceProductDefinitionOptionRelId = commerceProductDefinitionOptionRelId;
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

	private long _commerceProductDefinitionOptionValueRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceProductDefinitionOptionRelId;
	private String _title;
	private long _priority;
}