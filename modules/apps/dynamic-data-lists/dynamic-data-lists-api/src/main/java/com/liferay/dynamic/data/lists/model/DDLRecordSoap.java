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

package com.liferay.dynamic.data.lists.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.lists.service.http.DDLRecordServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordSoap implements Serializable {

	public static DDLRecordSoap toSoapModel(DDLRecord model) {
		DDLRecordSoap soapModel = new DDLRecordSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setRecordId(model.getRecordId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDDMStorageId(model.getDDMStorageId());
		soapModel.setRecordSetId(model.getRecordSetId());
		soapModel.setRecordSetVersion(model.getRecordSetVersion());
		soapModel.setVersion(model.getVersion());
		soapModel.setDisplayIndex(model.getDisplayIndex());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DDLRecordSoap[] toSoapModels(DDLRecord[] models) {
		DDLRecordSoap[] soapModels = new DDLRecordSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSoap[][] toSoapModels(DDLRecord[][] models) {
		DDLRecordSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDLRecordSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDLRecordSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSoap[] toSoapModels(List<DDLRecord> models) {
		List<DDLRecordSoap> soapModels = new ArrayList<DDLRecordSoap>(
			models.size());

		for (DDLRecord model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDLRecordSoap[soapModels.size()]);
	}

	public DDLRecordSoap() {
	}

	public long getPrimaryKey() {
		return _recordId;
	}

	public void setPrimaryKey(long pk) {
		setRecordId(pk);
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

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
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

	public long getDDMStorageId() {
		return _DDMStorageId;
	}

	public void setDDMStorageId(long DDMStorageId) {
		_DDMStorageId = DDMStorageId;
	}

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public String getRecordSetVersion() {
		return _recordSetVersion;
	}

	public void setRecordSetVersion(String recordSetVersion) {
		_recordSetVersion = recordSetVersion;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public int getDisplayIndex() {
		return _displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		_displayIndex = displayIndex;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _recordId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _DDMStorageId;
	private long _recordSetId;
	private String _recordSetVersion;
	private String _version;
	private int _displayIndex;
	private Date _lastPublishDate;

}