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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPDefinitionSpecificationOptionValueServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPDefinitionSpecificationOptionValueServiceSoap
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueSoap implements Serializable {
	public static CPDefinitionSpecificationOptionValueSoap toSoapModel(
		CPDefinitionSpecificationOptionValue model) {
		CPDefinitionSpecificationOptionValueSoap soapModel = new CPDefinitionSpecificationOptionValueSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionSpecificationOptionValueId(model.getCPDefinitionSpecificationOptionValueId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPSpecificationOptionId(model.getCPSpecificationOptionId());
		soapModel.setCPOptionCategoryId(model.getCPOptionCategoryId());
		soapModel.setValue(model.getValue());
		soapModel.setPriority(model.getPriority());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPDefinitionSpecificationOptionValueSoap[] toSoapModels(
		CPDefinitionSpecificationOptionValue[] models) {
		CPDefinitionSpecificationOptionValueSoap[] soapModels = new CPDefinitionSpecificationOptionValueSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionSpecificationOptionValueSoap[][] toSoapModels(
		CPDefinitionSpecificationOptionValue[][] models) {
		CPDefinitionSpecificationOptionValueSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionSpecificationOptionValueSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionSpecificationOptionValueSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionSpecificationOptionValueSoap[] toSoapModels(
		List<CPDefinitionSpecificationOptionValue> models) {
		List<CPDefinitionSpecificationOptionValueSoap> soapModels = new ArrayList<CPDefinitionSpecificationOptionValueSoap>(models.size());

		for (CPDefinitionSpecificationOptionValue model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionSpecificationOptionValueSoap[soapModels.size()]);
	}

	public CPDefinitionSpecificationOptionValueSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionSpecificationOptionValueId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionSpecificationOptionValueId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionSpecificationOptionValueId() {
		return _CPDefinitionSpecificationOptionValueId;
	}

	public void setCPDefinitionSpecificationOptionValueId(
		long CPDefinitionSpecificationOptionValueId) {
		_CPDefinitionSpecificationOptionValueId = CPDefinitionSpecificationOptionValueId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public long getCPSpecificationOptionId() {
		return _CPSpecificationOptionId;
	}

	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		_CPSpecificationOptionId = CPSpecificationOptionId;
	}

	public long getCPOptionCategoryId() {
		return _CPOptionCategoryId;
	}

	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_CPOptionCategoryId = CPOptionCategoryId;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPDefinitionSpecificationOptionValueId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _CPSpecificationOptionId;
	private long _CPOptionCategoryId;
	private String _value;
	private double _priority;
	private Date _lastPublishDate;
}