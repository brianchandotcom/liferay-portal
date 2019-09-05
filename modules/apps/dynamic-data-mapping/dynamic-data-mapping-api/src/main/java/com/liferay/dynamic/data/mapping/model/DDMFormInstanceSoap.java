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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceSoap implements Serializable {

	public static DDMFormInstanceSoap toSoapModel(DDMFormInstance model) {
		DDMFormInstanceSoap soapModel = new DDMFormInstanceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFormInstanceId(model.getFormInstanceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setVersion(model.getVersion());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setSettings(model.getSettings());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DDMFormInstanceSoap[] toSoapModels(DDMFormInstance[] models) {
		DDMFormInstanceSoap[] soapModels =
			new DDMFormInstanceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceSoap[][] toSoapModels(
		DDMFormInstance[][] models) {

		DDMFormInstanceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMFormInstanceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFormInstanceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceSoap[] toSoapModels(
		List<DDMFormInstance> models) {

		List<DDMFormInstanceSoap> soapModels =
			new ArrayList<DDMFormInstanceSoap>(models.size());

		for (DDMFormInstance model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMFormInstanceSoap[soapModels.size()]);
	}

	public DDMFormInstanceSoap() {
	}

	public long getPrimaryKey() {
		return _formInstanceId;
	}

	public void setPrimaryKey(long pk) {
		setFormInstanceId(pk);
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

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
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

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
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

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _formInstanceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _structureId;
	private String _version;
	private String _name;
	private String _description;
	private String _settings;
	private Date _lastPublishDate;

}