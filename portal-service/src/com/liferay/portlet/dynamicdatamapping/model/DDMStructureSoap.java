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

package com.liferay.portlet.dynamicdatamapping.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureServiceSoap
 * @generated
 */
public class DDMStructureSoap implements Serializable {
	public static DDMStructureSoap toSoapModel(DDMStructure model) {
		DDMStructureSoap soapModel = new DDMStructureSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setParentStructureId(model.getParentStructureId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setStructureKey(model.getStructureKey());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setXsd(model.getXsd());
		soapModel.setStorageType(model.getStorageType());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static DDMStructureSoap[] toSoapModels(DDMStructure[] models) {
		DDMStructureSoap[] soapModels = new DDMStructureSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureSoap[][] toSoapModels(DDMStructure[][] models) {
		DDMStructureSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMStructureSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMStructureSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureSoap[] toSoapModels(List<DDMStructure> models) {
		List<DDMStructureSoap> soapModels = new ArrayList<DDMStructureSoap>(models.size());

		for (DDMStructure model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMStructureSoap[soapModels.size()]);
	}

	public DDMStructureSoap() {
	}

	public long getPrimaryKey() {
		return _structureId;
	}

	public void setPrimaryKey(long pk) {
		setStructureId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
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

	public long getParentStructureId() {
		return _parentStructureId;
	}

	public void setParentStructureId(long parentStructureId) {
		_parentStructureId = parentStructureId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getStructureKey() {
		return _structureKey;
	}

	public void setStructureKey(String structureKey) {
		_structureKey = structureKey;
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

	public String getXsd() {
		return _xsd;
	}

	public void setXsd(String xsd) {
		_xsd = xsd;
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

	private String _uuid;
	private long _structureId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentStructureId;
	private long _classNameId;
	private String _structureKey;
	private String _name;
	private String _description;
	private String _xsd;
	private String _storageType;
	private int _type;
}