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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPDefinitionOptionRelServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPDefinitionOptionRelServiceSoap
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelSoap implements Serializable {
	public static CPDefinitionOptionRelSoap toSoapModel(
		CPDefinitionOptionRel model) {
		CPDefinitionOptionRelSoap soapModel = new CPDefinitionOptionRelSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionOptionRelId(model.getCPDefinitionOptionRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setCPOptionId(model.getCPOptionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDDMFormFieldTypeName(model.getDDMFormFieldTypeName());
		soapModel.setPriority(model.getPriority());
		soapModel.setFacetable(model.isFacetable());
		soapModel.setRequired(model.isRequired());
		soapModel.setSkuContributor(model.isSkuContributor());

		return soapModel;
	}

	public static CPDefinitionOptionRelSoap[] toSoapModels(
		CPDefinitionOptionRel[] models) {
		CPDefinitionOptionRelSoap[] soapModels = new CPDefinitionOptionRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionOptionRelSoap[][] toSoapModels(
		CPDefinitionOptionRel[][] models) {
		CPDefinitionOptionRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionOptionRelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionOptionRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionOptionRelSoap[] toSoapModels(
		List<CPDefinitionOptionRel> models) {
		List<CPDefinitionOptionRelSoap> soapModels = new ArrayList<CPDefinitionOptionRelSoap>(models.size());

		for (CPDefinitionOptionRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionOptionRelSoap[soapModels.size()]);
	}

	public CPDefinitionOptionRelSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionOptionRelId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionOptionRelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionOptionRelId() {
		return _CPDefinitionOptionRelId;
	}

	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		_CPDefinitionOptionRelId = CPDefinitionOptionRelId;
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

	public long getCPOptionId() {
		return _CPOptionId;
	}

	public void setCPOptionId(long CPOptionId) {
		_CPOptionId = CPOptionId;
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

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public boolean getFacetable() {
		return _facetable;
	}

	public boolean isFacetable() {
		return _facetable;
	}

	public void setFacetable(boolean facetable) {
		_facetable = facetable;
	}

	public boolean getRequired() {
		return _required;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public boolean getSkuContributor() {
		return _skuContributor;
	}

	public boolean isSkuContributor() {
		return _skuContributor;
	}

	public void setSkuContributor(boolean skuContributor) {
		_skuContributor = skuContributor;
	}

	private String _uuid;
	private long _CPDefinitionOptionRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _CPOptionId;
	private String _name;
	private String _description;
	private String _DDMFormFieldTypeName;
	private double _priority;
	private boolean _facetable;
	private boolean _required;
	private boolean _skuContributor;
}