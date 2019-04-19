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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchJobExecutionSoap implements Serializable {

	public static BatchJobExecutionSoap toSoapModel(BatchJobExecution model) {
		BatchJobExecutionSoap soapModel = new BatchJobExecutionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setBatchJobExecutionId(model.getBatchJobExecutionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBatchJobInstanceId(model.getBatchJobInstanceId());
		soapModel.setStatus(model.getStatus());
		soapModel.setStartTime(model.getStartTime());
		soapModel.setEndTime(model.getEndTime());
		soapModel.setJobSettings(model.getJobSettings());
		soapModel.setError(model.getError());

		return soapModel;
	}

	public static BatchJobExecutionSoap[] toSoapModels(
		BatchJobExecution[] models) {

		BatchJobExecutionSoap[] soapModels =
			new BatchJobExecutionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchJobExecutionSoap[][] toSoapModels(
		BatchJobExecution[][] models) {

		BatchJobExecutionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchJobExecutionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchJobExecutionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchJobExecutionSoap[] toSoapModels(
		List<BatchJobExecution> models) {

		List<BatchJobExecutionSoap> soapModels =
			new ArrayList<BatchJobExecutionSoap>(models.size());

		for (BatchJobExecution model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchJobExecutionSoap[soapModels.size()]);
	}

	public BatchJobExecutionSoap() {
	}

	public long getPrimaryKey() {
		return _batchJobExecutionId;
	}

	public void setPrimaryKey(long pk) {
		setBatchJobExecutionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getBatchJobExecutionId() {
		return _batchJobExecutionId;
	}

	public void setBatchJobExecutionId(long batchJobExecutionId) {
		_batchJobExecutionId = batchJobExecutionId;
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

	public long getBatchJobInstanceId() {
		return _batchJobInstanceId;
	}

	public void setBatchJobInstanceId(long batchJobInstanceId) {
		_batchJobInstanceId = batchJobInstanceId;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
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

	public String getJobSettings() {
		return _jobSettings;
	}

	public void setJobSettings(String jobSettings) {
		_jobSettings = jobSettings;
	}

	public String getError() {
		return _error;
	}

	public void setError(String error) {
		_error = error;
	}

	private String _uuid;
	private long _batchJobExecutionId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _batchJobInstanceId;
	private String _status;
	private Date _startTime;
	private Date _endTime;
	private String _jobSettings;
	private String _error;

}