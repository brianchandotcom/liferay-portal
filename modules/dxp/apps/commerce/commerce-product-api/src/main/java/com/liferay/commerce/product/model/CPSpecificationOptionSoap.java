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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPSpecificationOptionServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPSpecificationOptionServiceSoap
 * @generated
 */
@ProviderType
public class CPSpecificationOptionSoap implements Serializable {
	public static CPSpecificationOptionSoap toSoapModel(
		CPSpecificationOption model) {
		CPSpecificationOptionSoap soapModel = new CPSpecificationOptionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPSpecificationOptionId(model.getCPSpecificationOptionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPOptionCategoryId(model.getCPOptionCategoryId());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setFacetable(model.isFacetable());
		soapModel.setKey(model.getKey());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPSpecificationOptionSoap[] toSoapModels(
		CPSpecificationOption[] models) {
		CPSpecificationOptionSoap[] soapModels = new CPSpecificationOptionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPSpecificationOptionSoap[][] toSoapModels(
		CPSpecificationOption[][] models) {
		CPSpecificationOptionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPSpecificationOptionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPSpecificationOptionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPSpecificationOptionSoap[] toSoapModels(
		List<CPSpecificationOption> models) {
		List<CPSpecificationOptionSoap> soapModels = new ArrayList<CPSpecificationOptionSoap>(models.size());

		for (CPSpecificationOption model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPSpecificationOptionSoap[soapModels.size()]);
	}

	public CPSpecificationOptionSoap() {
	}

	public long getPrimaryKey() {
		return _CPSpecificationOptionId;
	}

	public void setPrimaryKey(long pk) {
		setCPSpecificationOptionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPSpecificationOptionId() {
		return _CPSpecificationOptionId;
	}

	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		_CPSpecificationOptionId = CPSpecificationOptionId;
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

	public long getCPOptionCategoryId() {
		return _CPOptionCategoryId;
	}

	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_CPOptionCategoryId = CPOptionCategoryId;
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

	public boolean getFacetable() {
		return _facetable;
	}

	public boolean isFacetable() {
		return _facetable;
	}

	public void setFacetable(boolean facetable) {
		_facetable = facetable;
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
	private long _CPSpecificationOptionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPOptionCategoryId;
	private String _title;
	private String _description;
	private boolean _facetable;
	private String _key;
	private Date _lastPublishDate;
}