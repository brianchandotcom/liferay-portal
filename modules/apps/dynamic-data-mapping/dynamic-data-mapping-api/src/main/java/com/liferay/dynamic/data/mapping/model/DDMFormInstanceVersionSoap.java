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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceVersionSoap implements Serializable {

	public static DDMFormInstanceVersionSoap toSoapModel(
		DDMFormInstanceVersion model) {

		DDMFormInstanceVersionSoap soapModel = new DDMFormInstanceVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setFormInstanceVersionId(model.getFormInstanceVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setFormInstanceId(model.getFormInstanceId());
		soapModel.setStructureVersionId(model.getStructureVersionId());
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

	public static DDMFormInstanceVersionSoap[] toSoapModels(
		DDMFormInstanceVersion[] models) {

		DDMFormInstanceVersionSoap[] soapModels =
			new DDMFormInstanceVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceVersionSoap[][] toSoapModels(
		DDMFormInstanceVersion[][] models) {

		DDMFormInstanceVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMFormInstanceVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFormInstanceVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceVersionSoap[] toSoapModels(
		List<DDMFormInstanceVersion> models) {

		List<DDMFormInstanceVersionSoap> soapModels =
			new ArrayList<DDMFormInstanceVersionSoap>(models.size());

		for (DDMFormInstanceVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDMFormInstanceVersionSoap[soapModels.size()]);
	}

	public DDMFormInstanceVersionSoap() {
	}

	public long getPrimaryKey() {
		return _formInstanceVersionId;
	}

	public void setPrimaryKey(long pk) {
		setFormInstanceVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getFormInstanceVersionId() {
		return _formInstanceVersionId;
	}

	public void setFormInstanceVersionId(long formInstanceVersionId) {
		_formInstanceVersionId = formInstanceVersionId;
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

	public long getStructureVersionId() {
		return _structureVersionId;
	}

	public void setStructureVersionId(long structureVersionId) {
		_structureVersionId = structureVersionId;
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
	private long _formInstanceVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _formInstanceId;
	private long _structureVersionId;
	private String _name;
	private String _description;
	private String _settings;
	private String _version;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}