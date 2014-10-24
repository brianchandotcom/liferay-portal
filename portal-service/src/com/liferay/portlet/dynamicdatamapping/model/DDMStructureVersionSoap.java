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

package com.liferay.portlet.dynamicdatamapping.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureVersionServiceSoap
 * @generated
 */
@ProviderType
public class DDMStructureVersionSoap implements Serializable {
	public static DDMStructureVersionSoap toSoapModel(DDMStructureVersion model) {
		DDMStructureVersionSoap soapModel = new DDMStructureVersionSoap();

		soapModel.setStructureVersionId(model.getStructureVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setVersion(model.getVersion());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDefinition(model.getDefinition());
		soapModel.setStorageType(model.getStorageType());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static DDMStructureVersionSoap[] toSoapModels(
		DDMStructureVersion[] models) {
		DDMStructureVersionSoap[] soapModels = new DDMStructureVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureVersionSoap[][] toSoapModels(
		DDMStructureVersion[][] models) {
		DDMStructureVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMStructureVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMStructureVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureVersionSoap[] toSoapModels(
		List<DDMStructureVersion> models) {
		List<DDMStructureVersionSoap> soapModels = new ArrayList<DDMStructureVersionSoap>(models.size());

		for (DDMStructureVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMStructureVersionSoap[soapModels.size()]);
	}

	public DDMStructureVersionSoap() {
	}

	public long getPrimaryKey() {
		return _structureVersionId;
	}

	public void setPrimaryKey(long pk) {
		setStructureVersionId(pk);
	}

	public long getStructureVersionId() {
		return _structureVersionId;
	}

	public void setStructureVersionId(long structureVersionId) {
		_structureVersionId = structureVersionId;
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

	public String getDefinition() {
		return _definition;
	}

	public void setDefinition(String definition) {
		_definition = definition;
	}

	public String getStorageType() {
		return _storageType;
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _structureVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _structureId;
	private String _version;
	private String _name;
	private String _description;
	private String _definition;
	private String _storageType;
	private int _type;
}