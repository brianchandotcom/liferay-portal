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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMStructureLayoutServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DDMStructureLayoutSoap implements Serializable {

	public static DDMStructureLayoutSoap toSoapModel(DDMStructureLayout model) {
		DDMStructureLayoutSoap soapModel = new DDMStructureLayoutSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUuid(model.getUuid());
		soapModel.setStructureLayoutId(model.getStructureLayoutId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setStructureLayoutKey(model.getStructureLayoutKey());
		soapModel.setStructureVersionId(model.getStructureVersionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDefinition(model.getDefinition());

		return soapModel;
	}

	public static DDMStructureLayoutSoap[] toSoapModels(
		DDMStructureLayout[] models) {

		DDMStructureLayoutSoap[] soapModels =
			new DDMStructureLayoutSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureLayoutSoap[][] toSoapModels(
		DDMStructureLayout[][] models) {

		DDMStructureLayoutSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMStructureLayoutSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMStructureLayoutSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureLayoutSoap[] toSoapModels(
		List<DDMStructureLayout> models) {

		List<DDMStructureLayoutSoap> soapModels =
			new ArrayList<DDMStructureLayoutSoap>(models.size());

		for (DDMStructureLayout model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDMStructureLayoutSoap[soapModels.size()]);
	}

	public DDMStructureLayoutSoap() {
	}

	public long getPrimaryKey() {
		return _structureLayoutId;
	}

	public void setPrimaryKey(long pk) {
		setStructureLayoutId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getStructureLayoutId() {
		return _structureLayoutId;
	}

	public void setStructureLayoutId(long structureLayoutId) {
		_structureLayoutId = structureLayoutId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getStructureLayoutKey() {
		return _structureLayoutKey;
	}

	public void setStructureLayoutKey(String structureLayoutKey) {
		_structureLayoutKey = structureLayoutKey;
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

	public String getDefinition() {
		return _definition;
	}

	public void setDefinition(String definition) {
		_definition = definition;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _structureLayoutId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private String _structureLayoutKey;
	private long _structureVersionId;
	private String _name;
	private String _description;
	private String _definition;

}