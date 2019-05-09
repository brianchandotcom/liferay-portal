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
public class FileEntrySoap implements Serializable {

	public static FileEntrySoap toSoapModel(FileEntry model) {
		FileEntrySoap soapModel = new FileEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setExtension(model.getExtension());
		soapModel.setMimeType(model.getMimeType());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setVersion(model.getVersion());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static FileEntrySoap[] toSoapModels(FileEntry[] models) {
		FileEntrySoap[] soapModels = new FileEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FileEntrySoap[][] toSoapModels(FileEntry[][] models) {
		FileEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FileEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FileEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FileEntrySoap[] toSoapModels(List<FileEntry> models) {
		List<FileEntrySoap> soapModels = new ArrayList<>(models.size());

		for (FileEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FileEntrySoap[0]);
	}

	public FileEntrySoap() {
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

	public String getExtension() {
		return _extension;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getSize() {
		return _size;
	}

	public String getTitle() {
		return _title;
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

	public String getVersion() {
		return _version;
	}

	public long getVersionUserId() {
		return _versionUserId;
	}

	public String getVersionUserName() {
		return _versionUserName;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date date) {
		_createDate = date;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setExtension(String extension) {
		_extension = extension;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public void setModifiedDate(Date date) {
		_modifiedDate = date;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryId(pk);
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setSize(long size) {
		_size = size;
	}

	public void setTitle(String title) {
		_title = title;
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

	public void setVersion(String version) {
		_version = version;
	}

	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
	}

	private long _companyId;
	private Date _createDate;
	private String _description;
	private String _extension;
	private long _fileEntryId;
	private long _folderId;
	private long _groupId;
	private String _mimeType;
	private Date _modifiedDate;
	private long _repositoryId;
	private long _size;
	private String _title;
	private long _userId;
	private String _userName;
	private String _uuid;
	private String _version;
	private long _versionUserId;
	private String _versionUserName;

}