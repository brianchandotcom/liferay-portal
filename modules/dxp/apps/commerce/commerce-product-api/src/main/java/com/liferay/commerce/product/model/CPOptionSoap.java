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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPOptionServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPOptionServiceSoap
 * @generated
 */
@ProviderType
public class CPOptionSoap implements Serializable {
	public static CPOptionSoap toSoapModel(CPOption model) {
		CPOptionSoap soapModel = new CPOptionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPOptionId(model.getCPOptionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setDDMFormFieldTypeName(model.getDDMFormFieldTypeName());
		soapModel.setFacetable(model.isFacetable());
		soapModel.setRequired(model.isRequired());
		soapModel.setSkuContributor(model.isSkuContributor());
		soapModel.setKey(model.getKey());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPOptionSoap[] toSoapModels(CPOption[] models) {
		CPOptionSoap[] soapModels = new CPOptionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPOptionSoap[][] toSoapModels(CPOption[][] models) {
		CPOptionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPOptionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPOptionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPOptionSoap[] toSoapModels(List<CPOption> models) {
		List<CPOptionSoap> soapModels = new ArrayList<CPOptionSoap>(models.size());

		for (CPOption model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPOptionSoap[soapModels.size()]);
	}

	public CPOptionSoap() {
	}

	public long getPrimaryKey() {
		return _CPOptionId;
	}

	public void setPrimaryKey(long pk) {
		setCPOptionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPOptionId() {
		return _CPOptionId;
	}

	public void setCPOptionId(long CPOptionId) {
		_CPOptionId = CPOptionId;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
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

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPOptionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private String _DDMFormFieldTypeName;
	private boolean _facetable;
	private boolean _required;
	private boolean _skuContributor;
	private String _key;
	private Date _lastPublishDate;
}