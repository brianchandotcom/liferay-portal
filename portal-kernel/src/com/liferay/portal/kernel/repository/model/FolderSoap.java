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

package com.liferay.portal.kernel.repository.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class FolderSoap implements Serializable {

	public static FolderSoap toSoapModel(Folder model) {
		FolderSoap soapModel = new FolderSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setParentFolderId(model.getParentFolderId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setLastPostDate(model.getLastPostDate());

		return soapModel;
	}

	public static FolderSoap[] toSoapModels(Folder[] models) {
		FolderSoap[] soapModels = new FolderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FolderSoap[][] toSoapModels(Folder[][] models) {
		FolderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FolderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FolderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FolderSoap[] toSoapModels(List<Folder> models) {
		List<FolderSoap> soapModels = new ArrayList<>(models.size());

		for (Folder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FolderSoap[0]);
	}

	public FolderSoap() {
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public long getParentFolderId() {
		return _parentFolderId;
	}

	public long getPrimaryKey() {
		return _folderId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentFolderId(long parentFolderId) {
		_parentFolderId = parentFolderId;
	}

	public void setPrimaryKey(long pk) {
		setFolderId(pk);
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private long _companyId;
	private Date _createDate;
	private String _description;
	private long _folderId;
	private long _groupId;
	private Date _lastPostDate;
	private Date _modifiedDate;
	private String _name;
	private long _parentFolderId;
	private long _repositoryId;
	private long _userId;
	private String _userName;
	private String _uuid;

}