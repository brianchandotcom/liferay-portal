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

package com.liferay.document.library.content.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DLContentSoap implements Serializable {

	public static DLContentSoap toSoapModel(DLContent model) {
		DLContentSoap soapModel = new DLContentSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setContentId(model.getContentId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setPath(model.getPath());
		soapModel.setVersion(model.getVersion());
		soapModel.setData(model.getData());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static DLContentSoap[] toSoapModels(DLContent[] models) {
		DLContentSoap[] soapModels = new DLContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLContentSoap[][] toSoapModels(DLContent[][] models) {
		DLContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLContentSoap[] toSoapModels(List<DLContent> models) {
		List<DLContentSoap> soapModels = new ArrayList<DLContentSoap>(
			models.size());

		for (DLContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLContentSoap[soapModels.size()]);
	}

	public DLContentSoap() {
	}

	public long getPrimaryKey() {
		return _contentId;
	}

	public void setPrimaryKey(long pk) {
		setContentId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getContentId() {
		return _contentId;
	}

	public void setContentId(long contentId) {
		_contentId = contentId;
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

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public Blob getData() {
		return _data;
	}

	public void setData(Blob data) {
		_data = data;
	}

	public long getSize() {
		return _size;
	}

	public void setSize(long size) {
		_size = size;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _contentId;
	private long _groupId;
	private long _companyId;
	private long _repositoryId;
	private String _path;
	private String _version;
	private Blob _data;
	private long _size;

}