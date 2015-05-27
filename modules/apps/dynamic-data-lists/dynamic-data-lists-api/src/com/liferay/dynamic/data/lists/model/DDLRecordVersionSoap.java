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

package com.liferay.portlet.dynamicdatalists.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.dynamicdatalists.service.http.DDLRecordVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatalists.service.http.DDLRecordVersionServiceSoap
 * @generated
 */
@ProviderType
public class DDLRecordVersionSoap implements Serializable {
	public static DDLRecordVersionSoap toSoapModel(DDLRecordVersion model) {
		DDLRecordVersionSoap soapModel = new DDLRecordVersionSoap();

		soapModel.setRecordVersionId(model.getRecordVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setDDMStorageId(model.getDDMStorageId());
		soapModel.setRecordSetId(model.getRecordSetId());
		soapModel.setRecordId(model.getRecordId());
		soapModel.setVersion(model.getVersion());
		soapModel.setDisplayIndex(model.getDisplayIndex());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static DDLRecordVersionSoap[] toSoapModels(DDLRecordVersion[] models) {
		DDLRecordVersionSoap[] soapModels = new DDLRecordVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordVersionSoap[][] toSoapModels(
		DDLRecordVersion[][] models) {
		DDLRecordVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDLRecordVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDLRecordVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordVersionSoap[] toSoapModels(
		List<DDLRecordVersion> models) {
		List<DDLRecordVersionSoap> soapModels = new ArrayList<DDLRecordVersionSoap>(models.size());

		for (DDLRecordVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDLRecordVersionSoap[soapModels.size()]);
	}

	public DDLRecordVersionSoap() {
	}

	public long getPrimaryKey() {
		return _recordVersionId;
	}

	public void setPrimaryKey(long pk) {
		setRecordVersionId(pk);
	}

	public long getRecordVersionId() {
		return _recordVersionId;
	}

	public void setRecordVersionId(long recordVersionId) {
		_recordVersionId = recordVersionId;
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

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
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

	private long _recordVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _DDMStorageId;
	private long _recordSetId;
	private long _recordId;
	private String _version;
	private int _displayIndex;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
}