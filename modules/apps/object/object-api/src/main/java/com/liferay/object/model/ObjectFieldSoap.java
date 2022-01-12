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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectFieldServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectFieldSoap implements Serializable {

	public static ObjectFieldSoap toSoapModel(ObjectField model) {
		ObjectFieldSoap soapModel = new ObjectFieldSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectFieldId(model.getObjectFieldId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setListTypeDefinitionId(model.getListTypeDefinitionId());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setBusinessType(model.getBusinessType());
		soapModel.setDBColumnName(model.getDBColumnName());
		soapModel.setDBTableName(model.getDBTableName());
		soapModel.setIndexed(model.isIndexed());
		soapModel.setIndexedAsKeyword(model.isIndexedAsKeyword());
		soapModel.setIndexedLanguageId(model.getIndexedLanguageId());
		soapModel.setLabel(model.getLabel());
		soapModel.setName(model.getName());
		soapModel.setRelationshipType(model.getRelationshipType());
		soapModel.setRequired(model.isRequired());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ObjectFieldSoap[] toSoapModels(ObjectField[] models) {
		ObjectFieldSoap[] soapModels = new ObjectFieldSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectFieldSoap[][] toSoapModels(ObjectField[][] models) {
		ObjectFieldSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ObjectFieldSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectFieldSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectFieldSoap[] toSoapModels(List<ObjectField> models) {
		List<ObjectFieldSoap> soapModels = new ArrayList<ObjectFieldSoap>(
			models.size());

		for (ObjectField model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectFieldSoap[soapModels.size()]);
	}

	public ObjectFieldSoap() {
	}

	public long getPrimaryKey() {
		return _objectFieldId;
	}

	public void setPrimaryKey(long pk) {
		setObjectFieldId(pk);
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

	public long getObjectFieldId() {
		return _objectFieldId;
	}

	public void setObjectFieldId(long objectFieldId) {
		_objectFieldId = objectFieldId;
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

	public long getListTypeDefinitionId() {
		return _listTypeDefinitionId;
	}

	public void setListTypeDefinitionId(long listTypeDefinitionId) {
		_listTypeDefinitionId = listTypeDefinitionId;
	}

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public String getBusinessType() {
		return _businessType;
	}

	public void setBusinessType(String businessType) {
		_businessType = businessType;
	}

	public String getDBColumnName() {
		return _dbColumnName;
	}

	public void setDBColumnName(String dbColumnName) {
		_dbColumnName = dbColumnName;
	}

	public String getDBTableName() {
		return _dbTableName;
	}

	public void setDBTableName(String dbTableName) {
		_dbTableName = dbTableName;
	}

	public boolean getIndexed() {
		return _indexed;
	}

	public boolean isIndexed() {
		return _indexed;
	}

	public void setIndexed(boolean indexed) {
		_indexed = indexed;
	}

	public boolean getIndexedAsKeyword() {
		return _indexedAsKeyword;
	}

	public boolean isIndexedAsKeyword() {
		return _indexedAsKeyword;
	}

	public void setIndexedAsKeyword(boolean indexedAsKeyword) {
		_indexedAsKeyword = indexedAsKeyword;
	}

	public String getIndexedLanguageId() {
		return _indexedLanguageId;
	}

	public void setIndexedLanguageId(String indexedLanguageId) {
		_indexedLanguageId = indexedLanguageId;
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

	public String getRelationshipType() {
		return _relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		_relationshipType = relationshipType;
	}

	public boolean getRequired() {
		return _required;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectFieldId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _listTypeDefinitionId;
	private long _objectDefinitionId;
	private String _businessType;
	private String _dbColumnName;
	private String _dbTableName;
	private boolean _indexed;
	private boolean _indexedAsKeyword;
	private String _indexedLanguageId;
	private String _label;
	private String _name;
	private String _relationshipType;
	private boolean _required;
	private String _type;

}