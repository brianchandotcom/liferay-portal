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

package com.liferay.portlet.journal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.journal.service.http.JournalStructureServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.journal.service.http.JournalStructureServiceSoap
 * @generated
 */
public class JournalStructureSoap implements Serializable {
	public static JournalStructureSoap toSoapModel(JournalStructure model) {
		JournalStructureSoap soapModel = new JournalStructureSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setId(model.getId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setParentStructureId(model.getParentStructureId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setXsd(model.getXsd());

		return soapModel;
	}

	public static JournalStructureSoap[] toSoapModels(JournalStructure[] models) {
		JournalStructureSoap[] soapModels = new JournalStructureSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static JournalStructureSoap[][] toSoapModels(
		JournalStructure[][] models) {
		JournalStructureSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new JournalStructureSoap[models.length][models[0].length];
		}
		else {
			soapModels = new JournalStructureSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static JournalStructureSoap[] toSoapModels(
		List<JournalStructure> models) {
		List<JournalStructureSoap> soapModels = new ArrayList<JournalStructureSoap>(models.size());

		for (JournalStructure model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new JournalStructureSoap[soapModels.size()]);
	}

	public JournalStructureSoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
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

	public String getStructureId() {
		return _structureId;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getParentStructureId() {
		return _parentStructureId;
	}

	public void setParentStructureId(String parentStructureId) {
		_parentStructureId = parentStructureId;
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

	private String _uuid;
	private long _id;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _structureId;
	private String _parentStructureId;
	private String _name;
	private String _description;
	private String _xsd;
}