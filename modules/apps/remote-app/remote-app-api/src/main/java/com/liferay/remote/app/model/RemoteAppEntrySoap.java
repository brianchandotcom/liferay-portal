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

package com.liferay.remote.app.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RemoteAppEntrySoap implements Serializable {

	public static RemoteAppEntrySoap toSoapModel(RemoteAppEntry model) {
		RemoteAppEntrySoap soapModel = new RemoteAppEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setRemoteAppEntryId(model.getRemoteAppEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setUrl(model.getUrl());

		return soapModel;
	}

	public static RemoteAppEntrySoap[] toSoapModels(RemoteAppEntry[] models) {
		RemoteAppEntrySoap[] soapModels = new RemoteAppEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RemoteAppEntrySoap[][] toSoapModels(
		RemoteAppEntry[][] models) {

		RemoteAppEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RemoteAppEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RemoteAppEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RemoteAppEntrySoap[] toSoapModels(
		List<RemoteAppEntry> models) {

		List<RemoteAppEntrySoap> soapModels = new ArrayList<RemoteAppEntrySoap>(
			models.size());

		for (RemoteAppEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RemoteAppEntrySoap[soapModels.size()]);
	}

	public RemoteAppEntrySoap() {
	}

	public long getPrimaryKey() {
		return _remoteAppEntryId;
	}

	public void setPrimaryKey(long pk) {
		setRemoteAppEntryId(pk);
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

	public long getRemoteAppEntryId() {
		return _remoteAppEntryId;
	}

	public void setRemoteAppEntryId(long remoteAppEntryId) {
		_remoteAppEntryId = remoteAppEntryId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _remoteAppEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _url;

}