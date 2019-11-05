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
import java.util.Map;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineImportTaskSoap implements Serializable {

	public static BatchEngineImportTaskSoap toSoapModel(
		BatchEngineImportTask model) {

		BatchEngineImportTaskSoap soapModel = new BatchEngineImportTaskSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setBatchEngineImportTaskId(
			model.getBatchEngineImportTaskId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBatchSize(model.getBatchSize());
		soapModel.setCallbackURL(model.getCallbackURL());
		soapModel.setClassName(model.getClassName());
		soapModel.setContent(model.getContent());
		soapModel.setContentType(model.getContentType());
		soapModel.setEndTime(model.getEndTime());
		soapModel.setErrorMessage(model.getErrorMessage());
		soapModel.setExecuteStatus(model.getExecuteStatus());
		soapModel.setFieldNameMapping(model.getFieldNameMapping());
		soapModel.setOperation(model.getOperation());
		soapModel.setParameters(model.getParameters());
		soapModel.setStartTime(model.getStartTime());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static BatchEngineImportTaskSoap[] toSoapModels(
		BatchEngineImportTask[] models) {

		BatchEngineImportTaskSoap[] soapModels =
			new BatchEngineImportTaskSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineImportTaskSoap[][] toSoapModels(
		BatchEngineImportTask[][] models) {

		BatchEngineImportTaskSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchEngineImportTaskSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchEngineImportTaskSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineImportTaskSoap[] toSoapModels(
		List<BatchEngineImportTask> models) {

		List<BatchEngineImportTaskSoap> soapModels =
			new ArrayList<BatchEngineImportTaskSoap>(models.size());

		for (BatchEngineImportTask model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new BatchEngineImportTaskSoap[soapModels.size()]);
	}

	public BatchEngineImportTaskSoap() {
	}

	public long getPrimaryKey() {
		return _batchEngineImportTaskId;
	}

	public void setPrimaryKey(long pk) {
		setBatchEngineImportTaskId(pk);
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

	public long getBatchEngineImportTaskId() {
		return _batchEngineImportTaskId;
	}

	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		_batchEngineImportTaskId = batchEngineImportTaskId;
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

	public long getBatchSize() {
		return _batchSize;
	}

	public void setBatchSize(long batchSize) {
		_batchSize = batchSize;
	}

	public String getCallbackURL() {
		return _callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		_callbackURL = callbackURL;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
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

	public Date getEndTime() {
		return _endTime;
	}

	public void setEndTime(Date endTime) {
		_endTime = endTime;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public String getExecuteStatus() {
		return _executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		_executeStatus = executeStatus;
	}

	public Map<String, Serializable> getFieldNameMapping() {
		return _fieldNameMapping;
	}

	public void setFieldNameMapping(
		Map<String, Serializable> fieldNameMapping) {

		_fieldNameMapping = fieldNameMapping;
	}

	public String getOperation() {
		return _operation;
	}

	public void setOperation(String operation) {
		_operation = operation;
	}

	public Map<String, Serializable> getParameters() {
		return _parameters;
	}

	public void setParameters(Map<String, Serializable> parameters) {
		_parameters = parameters;
	}

	public Date getStartTime() {
		return _startTime;
	}

	public void setStartTime(Date startTime) {
		_startTime = startTime;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _batchEngineImportTaskId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _batchSize;
	private String _callbackURL;
	private String _className;
	private Blob _content;
	private String _contentType;
	private Date _endTime;
	private String _errorMessage;
	private String _executeStatus;
	private Map<String, Serializable> _fieldNameMapping;
	private String _operation;
	private Map<String, Serializable> _parameters;
	private Date _startTime;
	private String _version;

}