/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import java.util.Date;
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

		soapModel.setFileId(model.getFileId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setEvent(model.getEvent());
		soapModel.setType(model.getType());

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

	public String getPrimaryKey() {
		return _fileId;
	}

	public void setPrimaryKey(String pk) {
		setFileId(pk);
	}

	public String getFileId() {
		return _fileId;
	}

	public void setFileId(String fileId) {
		_fileId = fileId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	private String _fileId;
	private long _companyId;
	private Date _modifiedDate;
	private long _repositoryId;
	private String _event;
	private String _type;
}