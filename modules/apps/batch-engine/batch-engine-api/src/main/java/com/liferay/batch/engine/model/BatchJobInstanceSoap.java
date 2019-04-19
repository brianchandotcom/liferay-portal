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
public class BatchJobInstanceSoap implements Serializable {

	public static BatchJobInstanceSoap toSoapModel(BatchJobInstance model) {
		BatchJobInstanceSoap soapModel = new BatchJobInstanceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setBatchJobInstanceId(model.getBatchJobInstanceId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setJobName(model.getJobName());
		soapModel.setJobKey(model.getJobKey());

		return soapModel;
	}

	public static BatchJobInstanceSoap[] toSoapModels(
		BatchJobInstance[] models) {

		BatchJobInstanceSoap[] soapModels =
			new BatchJobInstanceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchJobInstanceSoap[][] toSoapModels(
		BatchJobInstance[][] models) {

		BatchJobInstanceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchJobInstanceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchJobInstanceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchJobInstanceSoap[] toSoapModels(
		List<BatchJobInstance> models) {

		List<BatchJobInstanceSoap> soapModels =
			new ArrayList<BatchJobInstanceSoap>(models.size());

		for (BatchJobInstance model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchJobInstanceSoap[soapModels.size()]);
	}

	public BatchJobInstanceSoap() {
	}

	public long getPrimaryKey() {
		return _batchJobInstanceId;
	}

	public void setPrimaryKey(long pk) {
		setBatchJobInstanceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getBatchJobInstanceId() {
		return _batchJobInstanceId;
	}

	public void setBatchJobInstanceId(long batchJobInstanceId) {
		_batchJobInstanceId = batchJobInstanceId;
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

	public String getJobName() {
		return _jobName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public String getJobKey() {
		return _jobKey;
	}

	public void setJobKey(String jobKey) {
		_jobKey = jobKey;
	}

	private String _uuid;
	private long _batchJobInstanceId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _jobName;
	private String _jobKey;

}