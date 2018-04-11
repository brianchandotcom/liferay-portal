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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPTaxCategoryServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPTaxCategoryServiceSoap
 * @generated
 */
@ProviderType
public class CPTaxCategorySoap implements Serializable {
	public static CPTaxCategorySoap toSoapModel(CPTaxCategory model) {
		CPTaxCategorySoap soapModel = new CPTaxCategorySoap();

		soapModel.setCPTaxCategoryId(model.getCPTaxCategoryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static CPTaxCategorySoap[] toSoapModels(CPTaxCategory[] models) {
		CPTaxCategorySoap[] soapModels = new CPTaxCategorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPTaxCategorySoap[][] toSoapModels(CPTaxCategory[][] models) {
		CPTaxCategorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPTaxCategorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPTaxCategorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPTaxCategorySoap[] toSoapModels(List<CPTaxCategory> models) {
		List<CPTaxCategorySoap> soapModels = new ArrayList<CPTaxCategorySoap>(models.size());

		for (CPTaxCategory model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPTaxCategorySoap[soapModels.size()]);
	}

	public CPTaxCategorySoap() {
	}

	public long getPrimaryKey() {
		return _CPTaxCategoryId;
	}

	public void setPrimaryKey(long pk) {
		setCPTaxCategoryId(pk);
	}

	public long getCPTaxCategoryId() {
		return _CPTaxCategoryId;
	}

	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		_CPTaxCategoryId = CPTaxCategoryId;
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

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private long _CPTaxCategoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
}