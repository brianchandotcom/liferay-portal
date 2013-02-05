/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.documentlibrary.service.http.DLSyncServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.documentlibrary.service.http.DLSyncServiceSoap
 * @generated
 */
public class DLSyncSoap implements Serializable {
	public static DLSyncSoap toSoapModel(DLSync model) {
		DLSyncSoap soapModel = new DLSyncSoap();

		soapModel.setSyncId(model.getSyncId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFileId(model.getFileId());
		soapModel.setFileUuid(model.getFileUuid());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setParentFolderId(model.getParentFolderId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setEvent(model.getEvent());
		soapModel.setType(model.getType());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static DLSyncSoap[] toSoapModels(DLSync[] models) {
		DLSyncSoap[] soapModels = new DLSyncSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLSyncSoap[][] toSoapModels(DLSync[][] models) {
		DLSyncSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLSyncSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLSyncSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLSyncSoap[] toSoapModels(List<DLSync> models) {
		List<DLSyncSoap> soapModels = new ArrayList<DLSyncSoap>(models.size());

		for (DLSync model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLSyncSoap[soapModels.size()]);
	}

	public DLSyncSoap() {
	}

	public long getPrimaryKey() {
		return _syncId;
	}

	public void setPrimaryKey(long pk) {
		setSyncId(pk);
	}

	public long getSyncId() {
		return _syncId;
	}

	public void setSyncId(long syncId) {
		_syncId = syncId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public long getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getFileId() {
		return _fileId;
	}

	public void setFileId(long fileId) {
		_fileId = fileId;
	}

	public String getFileUuid() {
		return _fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		_fileUuid = fileUuid;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public long getParentFolderId() {
		return _parentFolderId;
	}

	public void setParentFolderId(long parentFolderId) {
		_parentFolderId = parentFolderId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getEvent() {
		return _event;
	}

	public void setEvent(String event) {
		_event = event;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private long _syncId;
	private long _companyId;
	private long _createDate;
	private long _modifiedDate;
	private long _fileId;
	private String _fileUuid;
	private long _repositoryId;
	private long _parentFolderId;
	private String _name;
	private String _description;
	private String _event;
	private String _type;
	private String _version;
}