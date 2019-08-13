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

package com.liferay.batch.engine.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shuyang Zhou
 * @generated
 */
@ProviderType
public class BatchTaskSoap implements Serializable {

	public static BatchTaskSoap toSoapModel(BatchTask model) {
		BatchTaskSoap soapModel = new BatchTaskSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setBatchTaskId(model.getBatchTaskId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassName(model.getClassName());
		soapModel.setVersion(model.getVersion());
		soapModel.setContent(model.getContent());
		soapModel.setContentType(model.getContentType());
		soapModel.setOperation(model.getOperation());
		soapModel.setBatchSize(model.getBatchSize());
		soapModel.setStartTime(model.getStartTime());
		soapModel.setEndTime(model.getEndTime());
		soapModel.setStatus(model.getStatus());
		soapModel.setErrorMessage(model.getErrorMessage());

		return soapModel;
	}

	public static BatchTaskSoap[] toSoapModels(BatchTask[] models) {
		BatchTaskSoap[] soapModels = new BatchTaskSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchTaskSoap[][] toSoapModels(BatchTask[][] models) {
		BatchTaskSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new BatchTaskSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchTaskSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchTaskSoap[] toSoapModels(List<BatchTask> models) {
		List<BatchTaskSoap> soapModels = new ArrayList<BatchTaskSoap>(
			models.size());

		for (BatchTask model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchTaskSoap[soapModels.size()]);
	}

	public BatchTaskSoap() {
	}

	public long getPrimaryKey() {
		return _batchTaskId;
	}

	public void setPrimaryKey(long pk) {
		setBatchTaskId(pk);
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

	public long getBatchTaskId() {
		return _batchTaskId;
	}

	public void setBatchTaskId(long batchTaskId) {
		_batchTaskId = batchTaskId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public Blob getContent() {
		return _content;
	}

	public void setContent(Blob content) {
		_content = content;
	}

	public String getContentType() {
		return _contentType;
	}

	public void setContentType(String contentType) {
		_contentType = contentType;
	}

	public String getOperation() {
		return _operation;
	}

	public void setOperation(String operation) {
		_operation = operation;
	}

	public long getBatchSize() {
		return _batchSize;
	}

	public void setBatchSize(long batchSize) {
		_batchSize = batchSize;
	}

	public Date getStartTime() {
		return _startTime;
	}

	public void setStartTime(Date startTime) {
		_startTime = startTime;
	}

	public Date getEndTime() {
		return _endTime;
	}

	public void setEndTime(Date endTime) {
		_endTime = endTime;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _batchTaskId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _version;
	private Blob _content;
	private String _contentType;
	private String _operation;
	private long _batchSize;
	private Date _startTime;
	private Date _endTime;
	private String _status;
	private String _errorMessage;

}