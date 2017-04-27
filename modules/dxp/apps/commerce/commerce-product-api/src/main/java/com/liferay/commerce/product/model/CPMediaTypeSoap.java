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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPMediaTypeServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPMediaTypeServiceSoap
 * @generated
 */
@ProviderType
public class CPMediaTypeSoap implements Serializable {
	public static CPMediaTypeSoap toSoapModel(CPMediaType model) {
		CPMediaTypeSoap soapModel = new CPMediaTypeSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPMediaTypeId(model.getCPMediaTypeId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static CPMediaTypeSoap[] toSoapModels(CPMediaType[] models) {
		CPMediaTypeSoap[] soapModels = new CPMediaTypeSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPMediaTypeSoap[][] toSoapModels(CPMediaType[][] models) {
		CPMediaTypeSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPMediaTypeSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPMediaTypeSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPMediaTypeSoap[] toSoapModels(List<CPMediaType> models) {
		List<CPMediaTypeSoap> soapModels = new ArrayList<CPMediaTypeSoap>(models.size());

		for (CPMediaType model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPMediaTypeSoap[soapModels.size()]);
	}

	public CPMediaTypeSoap() {
	}

	public long getPrimaryKey() {
		return _CPMediaTypeId;
	}

	public void setPrimaryKey(long pk) {
		setCPMediaTypeId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPMediaTypeId() {
		return _CPMediaTypeId;
	}

	public void setCPMediaTypeId(long CPMediaTypeId) {
		_CPMediaTypeId = CPMediaTypeId;
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

	public long getTitle() {
		return _title;
	}

	public void setTitle(long title) {
		_title = title;
	}

	public long getDescription() {
		return _description;
	}

	public void setDescription(long description) {
		_description = description;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private String _uuid;
	private long _CPMediaTypeId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _title;
	private long _description;
	private int _priority;
}