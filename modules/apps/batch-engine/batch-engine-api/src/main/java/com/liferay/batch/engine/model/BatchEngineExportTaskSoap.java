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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchEngineExportTaskSoap implements Serializable {

	public static BatchEngineExportTaskSoap toSoapModel(
		BatchEngineExportTask model) {

		BatchEngineExportTaskSoap soapModel = new BatchEngineExportTaskSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setBatchEngineExportTaskId(
			model.getBatchEngineExportTaskId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCallbackURL(model.getCallbackURL());
		soapModel.setClassName(model.getClassName());
		soapModel.setContent(model.getContent());
		soapModel.setContentType(model.getContentType());
		soapModel.setEndTime(model.getEndTime());
		soapModel.setErrorMessage(model.getErrorMessage());
		soapModel.setFieldNames(model.getFieldNames());
		soapModel.setExecuteStatus(model.getExecuteStatus());
		soapModel.setParameters(model.getParameters());
		soapModel.setStartTime(model.getStartTime());
		soapModel.setTaskItemDelegateName(model.getTaskItemDelegateName());

		return soapModel;
	}

	public static BatchEngineExportTaskSoap[] toSoapModels(
		BatchEngineExportTask[] models) {

		BatchEngineExportTaskSoap[] soapModels =
			new BatchEngineExportTaskSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineExportTaskSoap[][] toSoapModels(
		BatchEngineExportTask[][] models) {

		BatchEngineExportTaskSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchEngineExportTaskSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchEngineExportTaskSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineExportTaskSoap[] toSoapModels(
		List<BatchEngineExportTask> models) {

		List<BatchEngineExportTaskSoap> soapModels =
			new ArrayList<BatchEngineExportTaskSoap>(models.size());

		for (BatchEngineExportTask model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new BatchEngineExportTaskSoap[soapModels.size()]);
	}

	public BatchEngineExportTaskSoap() {
	}

	public long getPrimaryKey() {
		return _batchEngineExportTaskId;
	}

	public void setPrimaryKey(long pk) {
		setBatchEngineExportTaskId(pk);
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

	public long getBatchEngineExportTaskId() {
		return _batchEngineExportTaskId;
	}

	public void setBatchEngineExportTaskId(long batchEngineExportTaskId) {
		_batchEngineExportTaskId = batchEngineExportTaskId;
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

	public String getFieldNames() {
		return _fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		_fieldNames = fieldNames;
	}

	public String getExecuteStatus() {
		return _executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		_executeStatus = executeStatus;
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

	public String getTaskItemDelegateName() {
		return _taskItemDelegateName;
	}

	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_taskItemDelegateName = taskItemDelegateName;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _batchEngineExportTaskId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _callbackURL;
	private String _className;
	private Blob _content;
	private String _contentType;
	private Date _endTime;
	private String _errorMessage;
	private String _fieldNames;
	private String _executeStatus;
	private Map<String, Serializable> _parameters;
	private Date _startTime;
	private String _taskItemDelegateName;

}