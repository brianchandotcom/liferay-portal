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

package com.liferay.change.tracking.store.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class CTSContentSoap implements Serializable {

	public static CTSContentSoap toSoapModel(CTSContent model) {
		CTSContentSoap soapModel = new CTSContentSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setCtsContentId(model.getCtsContentId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setPath(model.getPath());
		soapModel.setVersion(model.getVersion());
		soapModel.setData(model.getData());
		soapModel.setSize(model.getSize());
		soapModel.setStoreType(model.getStoreType());

		return soapModel;
	}

	public static CTSContentSoap[] toSoapModels(CTSContent[] models) {
		CTSContentSoap[] soapModels = new CTSContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTSContentSoap[][] toSoapModels(CTSContent[][] models) {
		CTSContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTSContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTSContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTSContentSoap[] toSoapModels(List<CTSContent> models) {
		List<CTSContentSoap> soapModels = new ArrayList<CTSContentSoap>(
			models.size());

		for (CTSContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTSContentSoap[soapModels.size()]);
	}

	public CTSContentSoap() {
	}

	public long getPrimaryKey() {
		return _ctsContentId;
	}

	public void setPrimaryKey(long pk) {
		setCtsContentId(pk);
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

	public long getCtsContentId() {
		return _ctsContentId;
	}

	public void setCtsContentId(long ctsContentId) {
		_ctsContentId = ctsContentId;
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

	public String getStoreType() {
		return _storeType;
	}

	public void setStoreType(String storeType) {
		_storeType = storeType;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _ctsContentId;
	private long _companyId;
	private long _repositoryId;
	private String _path;
	private String _version;
	private Blob _data;
	private long _size;
	private String _storeType;

}