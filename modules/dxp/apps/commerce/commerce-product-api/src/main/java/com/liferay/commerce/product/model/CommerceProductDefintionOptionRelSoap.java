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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CommerceProductDefintionOptionRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CommerceProductDefintionOptionRelServiceSoap
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionRelSoap implements Serializable {
	public static CommerceProductDefintionOptionRelSoap toSoapModel(
		CommerceProductDefintionOptionRel model) {
		CommerceProductDefintionOptionRelSoap soapModel = new CommerceProductDefintionOptionRelSoap();

		soapModel.setCommerceProductDefintionOptionRelId(model.getCommerceProductDefintionOptionRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceProductOptionId(model.getCommerceProductOptionId());
		soapModel.setCommerceProductDefinitionId(model.getCommerceProductDefinitionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDDMFormFieldTypeName(model.getDDMFormFieldTypeName());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CommerceProductDefintionOptionRelSoap[] toSoapModels(
		CommerceProductDefintionOptionRel[] models) {
		CommerceProductDefintionOptionRelSoap[] soapModels = new CommerceProductDefintionOptionRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefintionOptionRelSoap[][] toSoapModels(
		CommerceProductDefintionOptionRel[][] models) {
		CommerceProductDefintionOptionRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductDefintionOptionRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductDefintionOptionRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefintionOptionRelSoap[] toSoapModels(
		List<CommerceProductDefintionOptionRel> models) {
		List<CommerceProductDefintionOptionRelSoap> soapModels = new ArrayList<CommerceProductDefintionOptionRelSoap>(models.size());

		for (CommerceProductDefintionOptionRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductDefintionOptionRelSoap[soapModels.size()]);
	}

	public CommerceProductDefintionOptionRelSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductDefintionOptionRelId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductDefintionOptionRelId(pk);
	}

	public long getCommerceProductDefintionOptionRelId() {
		return _commerceProductDefintionOptionRelId;
	}

	public void setCommerceProductDefintionOptionRelId(
		long commerceProductDefintionOptionRelId) {
		_commerceProductDefintionOptionRelId = commerceProductDefintionOptionRelId;
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

	public long getCommerceProductDefinitionId() {
		return _commerceProductDefinitionId;
	}

	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_commerceProductDefinitionId = commerceProductDefinitionId;
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

	public String getPriority() {
		return _priority;
	}

	public void setPriority(String priority) {
		_priority = priority;
	}

	private long _commerceProductDefintionOptionRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceProductOptionId;
	private long _commerceProductDefinitionId;
	private String _name;
	private String _description;
	private String _DDMFormFieldTypeName;
	private String _priority;
}