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

package com.liferay.portal.model;

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
public class RepositoryEntrySoap implements Serializable {
	public static RepositoryEntrySoap toSoapModel(RepositoryEntry model) {
		RepositoryEntrySoap soapModel = new RepositoryEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRepositoryEntryId(model.getRepositoryEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setMappedId(model.getMappedId());
		soapModel.setManualCheckInRequired(model.getManualCheckInRequired());
		soapModel.setMvccVersion(model.getMvccVersion());

		return soapModel;
	}

	public static RepositoryEntrySoap[] toSoapModels(RepositoryEntry[] models) {
		RepositoryEntrySoap[] soapModels = new RepositoryEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositoryEntrySoap[][] toSoapModels(
		RepositoryEntry[][] models) {
		RepositoryEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositoryEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositoryEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositoryEntrySoap[] toSoapModels(
		List<RepositoryEntry> models) {
		List<RepositoryEntrySoap> soapModels = new ArrayList<RepositoryEntrySoap>(models.size());

		for (RepositoryEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositoryEntrySoap[soapModels.size()]);
	}

	public RepositoryEntrySoap() {
	}

	public long getPrimaryKey() {
		return _repositoryEntryId;
	}

	public void setPrimaryKey(long pk) {
		setRepositoryEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRepositoryEntryId() {
		return _repositoryEntryId;
	}

	public void setRepositoryEntryId(long repositoryEntryId) {
		_repositoryEntryId = repositoryEntryId;
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

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public String getMappedId() {
		return _mappedId;
	}

	public void setMappedId(String mappedId) {
		_mappedId = mappedId;
	}

	public boolean getManualCheckInRequired() {
		return _manualCheckInRequired;
	}

	public boolean isManualCheckInRequired() {
		return _manualCheckInRequired;
	}

	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		_manualCheckInRequired = manualCheckInRequired;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	private String _uuid;
	private long _repositoryEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _repositoryId;
	private String _mappedId;
	private boolean _manualCheckInRequired;
	private long _mvccVersion;
}