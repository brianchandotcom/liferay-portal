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
 * This class is used by SOAP remote services, specifically {@link com.liferay.object.service.http.ObjectActionEntryServiceSoap}.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectActionEntrySoap implements Serializable {

	public static ObjectActionEntrySoap toSoapModel(ObjectActionEntry model) {
		ObjectActionEntrySoap soapModel = new ObjectActionEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectActionEntryId(model.getObjectActionEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectDefinitionId(model.getObjectDefinitionId());
		soapModel.setActive(model.isActive());
		soapModel.setTriggerName(model.getTriggerName());
		soapModel.setName(model.getName());
		soapModel.setSettings(model.getSettings());

		return soapModel;
	}

	public static ObjectActionEntrySoap[] toSoapModels(
		ObjectActionEntry[] models) {

		ObjectActionEntrySoap[] soapModels =
			new ObjectActionEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectActionEntrySoap[][] toSoapModels(
		ObjectActionEntry[][] models) {

		ObjectActionEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectActionEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectActionEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectActionEntrySoap[] toSoapModels(
		List<ObjectActionEntry> models) {

		List<ObjectActionEntrySoap> soapModels =
			new ArrayList<ObjectActionEntrySoap>(models.size());

		for (ObjectActionEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectActionEntrySoap[soapModels.size()]);
	}

	public ObjectActionEntrySoap() {
	}

	public long getPrimaryKey() {
		return _objectActionEntryId;
	}

	public void setPrimaryKey(long pk) {
		setObjectActionEntryId(pk);
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

	public long getObjectActionEntryId() {
		return _objectActionEntryId;
	}

	public void setObjectActionEntryId(long objectActionEntryId) {
		_objectActionEntryId = objectActionEntryId;
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

	public String getTriggerName() {
		return _triggerName;
	}

	public void setTriggerName(String triggerName) {
		_triggerName = triggerName;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectActionEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectDefinitionId;
	private boolean _active;
	private String _triggerName;
	private String _name;
	private String _settings;

}