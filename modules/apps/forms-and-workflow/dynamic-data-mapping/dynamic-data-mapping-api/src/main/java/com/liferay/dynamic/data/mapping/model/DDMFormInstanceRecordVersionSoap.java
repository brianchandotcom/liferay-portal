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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceRecordVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceRecordVersionServiceSoap
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordVersionSoap implements Serializable {
	public static DDMFormInstanceRecordVersionSoap toSoapModel(
		DDMFormInstanceRecordVersion model) {
		DDMFormInstanceRecordVersionSoap soapModel = new DDMFormInstanceRecordVersionSoap();

		soapModel.setFormInstanceRecordVersionId(model.getFormInstanceRecordVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setFormInstanceId(model.getFormInstanceId());
		soapModel.setFormInstanceVersion(model.getFormInstanceVersion());
		soapModel.setFormInstanceRecordId(model.getFormInstanceRecordId());
		soapModel.setVersion(model.getVersion());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setStorageId(model.getStorageId());

		return soapModel;
	}

	public static DDMFormInstanceRecordVersionSoap[] toSoapModels(
		DDMFormInstanceRecordVersion[] models) {
		DDMFormInstanceRecordVersionSoap[] soapModels = new DDMFormInstanceRecordVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceRecordVersionSoap[][] toSoapModels(
		DDMFormInstanceRecordVersion[][] models) {
		DDMFormInstanceRecordVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMFormInstanceRecordVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFormInstanceRecordVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceRecordVersionSoap[] toSoapModels(
		List<DDMFormInstanceRecordVersion> models) {
		List<DDMFormInstanceRecordVersionSoap> soapModels = new ArrayList<DDMFormInstanceRecordVersionSoap>(models.size());

		for (DDMFormInstanceRecordVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMFormInstanceRecordVersionSoap[soapModels.size()]);
	}

	public DDMFormInstanceRecordVersionSoap() {
	}

	public long getPrimaryKey() {
		return _formInstanceRecordVersionId;
	}

	public void setPrimaryKey(long pk) {
		setFormInstanceRecordVersionId(pk);
	}

	public long getFormInstanceRecordVersionId() {
		return _formInstanceRecordVersionId;
	}

	public void setFormInstanceRecordVersionId(long formInstanceRecordVersionId) {
		_formInstanceRecordVersionId = formInstanceRecordVersionId;
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

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
	}

	public String getFormInstanceVersion() {
		return _formInstanceVersion;
	}

	public void setFormInstanceVersion(String formInstanceVersion) {
		_formInstanceVersion = formInstanceVersion;
	}

	public long getFormInstanceRecordId() {
		return _formInstanceRecordId;
	}

	public void setFormInstanceRecordId(long formInstanceRecordId) {
		_formInstanceRecordId = formInstanceRecordId;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public long getStorageId() {
		return _storageId;
	}

	public void setStorageId(long storageId) {
		_storageId = storageId;
	}

	private long _formInstanceRecordVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _formInstanceId;
	private String _formInstanceVersion;
	private long _formInstanceRecordId;
	private String _version;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private long _storageId;
}