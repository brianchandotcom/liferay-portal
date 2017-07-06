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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPOptionCategoryServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPOptionCategoryServiceSoap
 * @generated
 */
@ProviderType
public class CPOptionCategorySoap implements Serializable {
	public static CPOptionCategorySoap toSoapModel(CPOptionCategory model) {
		CPOptionCategorySoap soapModel = new CPOptionCategorySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPOptionCategoryId(model.getCPOptionCategoryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setPriority(model.getPriority());
		soapModel.setKey(model.getKey());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPOptionCategorySoap[] toSoapModels(CPOptionCategory[] models) {
		CPOptionCategorySoap[] soapModels = new CPOptionCategorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPOptionCategorySoap[][] toSoapModels(
		CPOptionCategory[][] models) {
		CPOptionCategorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPOptionCategorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPOptionCategorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPOptionCategorySoap[] toSoapModels(
		List<CPOptionCategory> models) {
		List<CPOptionCategorySoap> soapModels = new ArrayList<CPOptionCategorySoap>(models.size());

		for (CPOptionCategory model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPOptionCategorySoap[soapModels.size()]);
	}

	public CPOptionCategorySoap() {
	}

	public long getPrimaryKey() {
		return _CPOptionCategoryId;
	}

	public void setPrimaryKey(long pk) {
		setCPOptionCategoryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPOptionCategoryId() {
		return _CPOptionCategoryId;
	}

	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_CPOptionCategoryId = CPOptionCategoryId;
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

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
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
	private long _CPOptionCategoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private double _priority;
	private String _key;
	private Date _lastPublishDate;
}