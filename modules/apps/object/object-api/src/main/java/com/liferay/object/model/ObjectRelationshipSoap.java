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

package com.liferay.object.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectRelationshipServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectRelationshipSoap implements Serializable {

	public static ObjectRelationshipSoap toSoapModel(ObjectRelationship model) {
		ObjectRelationshipSoap soapModel = new ObjectRelationshipSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectRelationshipId(model.getObjectRelationshipId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectDefinitionId1(model.getObjectDefinitionId1());
		soapModel.setObjectDefinitionId2(model.getObjectDefinitionId2());
		soapModel.setObjectFieldId2(model.getObjectFieldId2());
		soapModel.setDBTableName(model.getDBTableName());
		soapModel.setLabel(model.getLabel());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ObjectRelationshipSoap[] toSoapModels(
		ObjectRelationship[] models) {

		ObjectRelationshipSoap[] soapModels =
			new ObjectRelationshipSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectRelationshipSoap[][] toSoapModels(
		ObjectRelationship[][] models) {

		ObjectRelationshipSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectRelationshipSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectRelationshipSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectRelationshipSoap[] toSoapModels(
		List<ObjectRelationship> models) {

		List<ObjectRelationshipSoap> soapModels =
			new ArrayList<ObjectRelationshipSoap>(models.size());

		for (ObjectRelationship model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ObjectRelationshipSoap[soapModels.size()]);
	}

	public ObjectRelationshipSoap() {
	}

	public long getPrimaryKey() {
		return _objectRelationshipId;
	}

	public void setPrimaryKey(long pk) {
		setObjectRelationshipId(pk);
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

	public long getObjectRelationshipId() {
		return _objectRelationshipId;
	}

	public void setObjectRelationshipId(long objectRelationshipId) {
		_objectRelationshipId = objectRelationshipId;
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

	public long getObjectDefinitionId1() {
		return _objectDefinitionId1;
	}

	public void setObjectDefinitionId1(long objectDefinitionId1) {
		_objectDefinitionId1 = objectDefinitionId1;
	}

	public long getObjectDefinitionId2() {
		return _objectDefinitionId2;
	}

	public void setObjectDefinitionId2(long objectDefinitionId2) {
		_objectDefinitionId2 = objectDefinitionId2;
	}

	public long getObjectFieldId2() {
		return _objectFieldId2;
	}

	public void setObjectFieldId2(long objectFieldId2) {
		_objectFieldId2 = objectFieldId2;
	}

	public String getDBTableName() {
		return _dbTableName;
	}

	public void setDBTableName(String dbTableName) {
		_dbTableName = dbTableName;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectRelationshipId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectDefinitionId1;
	private long _objectDefinitionId2;
	private long _objectFieldId2;
	private String _dbTableName;
	private String _label;
	private String _name;
	private String _type;

}