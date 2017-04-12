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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceProductDefinitionOptionRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CommerceProductDefinitionOptionRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelSoap implements Serializable {
	public static CommerceProductDefinitionOptionRelSoap toSoapModel(
		CommerceProductDefinitionOptionRel model) {
		CommerceProductDefinitionOptionRelSoap soapModel = new CommerceProductDefinitionOptionRelSoap();

		soapModel.setCommerceProductDefinitionOptionRelId(model.getCommerceProductDefinitionOptionRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceProductDefinitionId(model.getCommerceProductDefinitionId());
		soapModel.setCommerceProductOptionId(model.getCommerceProductOptionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDDMFormFieldTypeName(model.getDDMFormFieldTypeName());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceProductDefinitionOptionRelSoap[] toSoapModels(
		CommerceProductDefinitionOptionRel[] models) {
		CommerceProductDefinitionOptionRelSoap[] soapModels = new CommerceProductDefinitionOptionRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionOptionRelSoap[][] toSoapModels(
		CommerceProductDefinitionOptionRel[][] models) {
		CommerceProductDefinitionOptionRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductDefinitionOptionRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductDefinitionOptionRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionOptionRelSoap[] toSoapModels(
		List<CommerceProductDefinitionOptionRel> models) {
		List<CommerceProductDefinitionOptionRelSoap> soapModels = new ArrayList<CommerceProductDefinitionOptionRelSoap>(models.size());

		for (CommerceProductDefinitionOptionRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductDefinitionOptionRelSoap[soapModels.size()]);
	}

	public CommerceProductDefinitionOptionRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductDefinitionOptionRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductDefinitionOptionRelId(pk);
	}

	public long getCommerceProductDefinitionOptionRelId() {
		return _commerceProductDefinitionOptionRelId;
	}

	public void setCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId) {
		_commerceProductDefinitionOptionRelId = commerceProductDefinitionOptionRelId;
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

	public long getCommerceProductDefinitionId() {
		return _commerceProductDefinitionId;
	}

	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_commerceProductDefinitionId = commerceProductDefinitionId;
	}

	public long getCommerceProductOptionId() {
		return _commerceProductOptionId;
	}

	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOptionId = commerceProductOptionId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getDDMFormFieldTypeName() {
		return _DDMFormFieldTypeName;
	}

	public void setDDMFormFieldTypeName(String DDMFormFieldTypeName) {
		_DDMFormFieldTypeName = DDMFormFieldTypeName;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _commerceProductDefinitionOptionRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceProductDefinitionId;
	private long _commerceProductOptionId;
	private String _name;
	private String _description;
	private String _DDMFormFieldTypeName;
	private int _priority;
}