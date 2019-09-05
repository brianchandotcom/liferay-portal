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

package com.liferay.message.boards.model;

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
public class MBThreadFlagSoap implements Serializable {

	public static MBThreadFlagSoap toSoapModel(MBThreadFlag model) {
		MBThreadFlagSoap soapModel = new MBThreadFlagSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setThreadFlagId(model.getThreadFlagId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setThreadId(model.getThreadId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static MBThreadFlagSoap[] toSoapModels(MBThreadFlag[] models) {
		MBThreadFlagSoap[] soapModels = new MBThreadFlagSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MBThreadFlagSoap[][] toSoapModels(MBThreadFlag[][] models) {
		MBThreadFlagSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MBThreadFlagSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MBThreadFlagSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MBThreadFlagSoap[] toSoapModels(List<MBThreadFlag> models) {
		List<MBThreadFlagSoap> soapModels = new ArrayList<MBThreadFlagSoap>(
			models.size());

		for (MBThreadFlag model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBThreadFlagSoap[soapModels.size()]);
	}

	public MBThreadFlagSoap() {
	}

	public long getPrimaryKey() {
		return _threadFlagId;
	}

	public void setPrimaryKey(long pk) {
		setThreadFlagId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getThreadFlagId() {
		return _threadFlagId;
	}

	public void setThreadFlagId(long threadFlagId) {
		_threadFlagId = threadFlagId;
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

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _threadFlagId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _threadId;
	private Date _lastPublishDate;

}