/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExportImportConfigurationSoap implements Serializable {
	public static ExportImportConfigurationSoap toSoapModel(
		ExportImportConfiguration model) {
		ExportImportConfigurationSoap soapModel = new ExportImportConfigurationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setExportImportConfigurationId(model.getExportImportConfigurationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());
		soapModel.setSettings(model.getSettings());

		return soapModel;
	}

	public static ExportImportConfigurationSoap[] toSoapModels(
		ExportImportConfiguration[] models) {
		ExportImportConfigurationSoap[] soapModels = new ExportImportConfigurationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExportImportConfigurationSoap[][] toSoapModels(
		ExportImportConfiguration[][] models) {
		ExportImportConfigurationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExportImportConfigurationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExportImportConfigurationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExportImportConfigurationSoap[] toSoapModels(
		List<ExportImportConfiguration> models) {
		List<ExportImportConfigurationSoap> soapModels = new ArrayList<ExportImportConfigurationSoap>(models.size());

		for (ExportImportConfiguration model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExportImportConfigurationSoap[soapModels.size()]);
	}

	public ExportImportConfigurationSoap() {
	}

	public long getPrimaryKey() {
		return _exportImportConfigurationId;
	}

	public void setPrimaryKey(long pk) {
		setExportImportConfigurationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getExportImportConfigurationId() {
		return _exportImportConfigurationId;
	}

	public void setExportImportConfigurationId(long exportImportConfigurationId) {
		_exportImportConfigurationId = exportImportConfigurationId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	private long _mvccVersion;
	private long _exportImportConfigurationId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private int _type;
	private String _settings;
}