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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceRecordServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceRecordSoap implements Serializable {

	public static DDMFormInstanceRecordSoap toSoapModel(
		DDMFormInstanceRecord model) {

		DDMFormInstanceRecordSoap soapModel = new DDMFormInstanceRecordSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFormInstanceRecordId(model.getFormInstanceRecordId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFormInstanceId(model.getFormInstanceId());
		soapModel.setFormInstanceVersion(model.getFormInstanceVersion());
		soapModel.setStorageId(model.getStorageId());
		soapModel.setVersion(model.getVersion());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DDMFormInstanceRecordSoap[] toSoapModels(
		DDMFormInstanceRecord[] models) {

		DDMFormInstanceRecordSoap[] soapModels =
			new DDMFormInstanceRecordSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceRecordSoap[][] toSoapModels(
		DDMFormInstanceRecord[][] models) {

		DDMFormInstanceRecordSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMFormInstanceRecordSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFormInstanceRecordSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceRecordSoap[] toSoapModels(
		List<DDMFormInstanceRecord> models) {

		List<DDMFormInstanceRecordSoap> soapModels =
			new ArrayList<DDMFormInstanceRecordSoap>(models.size());

		for (DDMFormInstanceRecord model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDMFormInstanceRecordSoap[soapModels.size()]);
	}

	public DDMFormInstanceRecordSoap() {
	}

	public long getPrimaryKey() {
		return _formInstanceRecordId;
	}

	public void setPrimaryKey(long pk) {
		setFormInstanceRecordId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFormInstanceRecordId() {
		return _formInstanceRecordId;
	}

	public void setFormInstanceRecordId(long formInstanceRecordId) {
		_formInstanceRecordId = formInstanceRecordId;
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

	public long getVersionUserId() {
		return _versionUserId;
	}

	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	public String getVersionUserName() {
		return _versionUserName;
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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

	public long getStorageId() {
		return _storageId;
	}

	public void setStorageId(long storageId) {
		_storageId = storageId;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _formInstanceRecordId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _formInstanceId;
	private String _formInstanceVersion;
	private long _storageId;
	private String _version;
	private Date _lastPublishDate;

}