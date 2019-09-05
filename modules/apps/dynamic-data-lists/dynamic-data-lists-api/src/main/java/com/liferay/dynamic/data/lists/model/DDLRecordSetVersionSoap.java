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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.lists.service.http.DDLRecordSetVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordSetVersionSoap implements Serializable {

	public static DDLRecordSetVersionSoap toSoapModel(
		DDLRecordSetVersion model) {

		DDLRecordSetVersionSoap soapModel = new DDLRecordSetVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRecordSetVersionId(model.getRecordSetVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setRecordSetId(model.getRecordSetId());
		soapModel.setDDMStructureVersionId(model.getDDMStructureVersionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setSettings(model.getSettings());
		soapModel.setVersion(model.getVersion());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static DDLRecordSetVersionSoap[] toSoapModels(
		DDLRecordSetVersion[] models) {

		DDLRecordSetVersionSoap[] soapModels =
			new DDLRecordSetVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSetVersionSoap[][] toSoapModels(
		DDLRecordSetVersion[][] models) {

		DDLRecordSetVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDLRecordSetVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDLRecordSetVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSetVersionSoap[] toSoapModels(
		List<DDLRecordSetVersion> models) {

		List<DDLRecordSetVersionSoap> soapModels =
			new ArrayList<DDLRecordSetVersionSoap>(models.size());

		for (DDLRecordSetVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDLRecordSetVersionSoap[soapModels.size()]);
	}

	public DDLRecordSetVersionSoap() {
	}

	public long getPrimaryKey() {
		return _recordSetVersionId;
	}

	public void setPrimaryKey(long pk) {
		setRecordSetVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRecordSetVersionId() {
		return _recordSetVersionId;
	}

	public void setRecordSetVersionId(long recordSetVersionId) {
		_recordSetVersionId = recordSetVersionId;
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

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public long getDDMStructureVersionId() {
		return _DDMStructureVersionId;
	}

	public void setDDMStructureVersionId(long DDMStructureVersionId) {
		_DDMStructureVersionId = DDMStructureVersionId;
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

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
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

	private long _mvccVersion;
	private long _recordSetVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _recordSetId;
	private long _DDMStructureVersionId;
	private String _name;
	private String _description;
	private String _settings;
	private String _version;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}