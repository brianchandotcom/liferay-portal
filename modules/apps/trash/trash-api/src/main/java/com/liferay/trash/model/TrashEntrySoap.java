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

package com.liferay.trash.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.trash.service.http.TrashEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashEntrySoap implements Serializable {

	public static TrashEntrySoap toSoapModel(TrashEntry model) {
		TrashEntrySoap soapModel = new TrashEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setSystemEventSetKey(model.getSystemEventSetKey());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static TrashEntrySoap[] toSoapModels(TrashEntry[] models) {
		TrashEntrySoap[] soapModels = new TrashEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[][] toSoapModels(TrashEntry[][] models) {
		TrashEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TrashEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TrashEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[] toSoapModels(List<TrashEntry> models) {
		List<TrashEntrySoap> soapModels = new ArrayList<TrashEntrySoap>(
			models.size());

		for (TrashEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TrashEntrySoap[soapModels.size()]);
	}

	public TrashEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getSystemEventSetKey() {
		return _systemEventSetKey;
	}

	public void setSystemEventSetKey(long systemEventSetKey) {
		_systemEventSetKey = systemEventSetKey;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _entryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _classNameId;
	private long _classPK;
	private long _systemEventSetKey;
	private String _typeSettings;
	private int _status;

}