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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectActionServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectActionSoap implements Serializable {

	public static ObjectActionSoap toSoapModel(ObjectAction model) {
		ObjectActionSoap soapModel = new ObjectActionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectActionId(model.getObjectActionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setActive(model.isActive());
		soapModel.setName(model.getName());
		soapModel.setObjectActionExecutorKey(
			model.getObjectActionExecutorKey());
		soapModel.setParameters(model.getParameters());

		return soapModel;
	}

	public static ObjectActionSoap[] toSoapModels(ObjectAction[] models) {
		ObjectActionSoap[] soapModels = new ObjectActionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectActionSoap[][] toSoapModels(ObjectAction[][] models) {
		ObjectActionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ObjectActionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectActionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectActionSoap[] toSoapModels(List<ObjectAction> models) {
		List<ObjectActionSoap> soapModels = new ArrayList<ObjectActionSoap>(
			models.size());

		for (ObjectAction model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectActionSoap[soapModels.size()]);
	}

	public ObjectActionSoap() {
	}

	public long getPrimaryKey() {
		return _objectActionId;
	}

	public void setPrimaryKey(long pk) {
		setObjectActionId(pk);
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

	public long getObjectActionId() {
		return _objectActionId;
	}

	public void setObjectActionId(long objectActionId) {
		_objectActionId = objectActionId;
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

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getObjectActionExecutorKey() {
		return _objectActionExecutorKey;
	}

	public void setObjectActionExecutorKey(String objectActionExecutorKey) {
		_objectActionExecutorKey = objectActionExecutorKey;
	}

	public String getParameters() {
		return _parameters;
	}

	public void setParameters(String parameters) {
		_parameters = parameters;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectActionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectDefinitionId;
	private boolean _active;
	private String _name;
	private String _objectActionExecutorKey;
	private String _parameters;

}