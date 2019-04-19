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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link BatchJobExecution}.
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobExecution
 * @generated
 */
@ProviderType
public class BatchJobExecutionWrapper
	extends BaseModelWrapper<BatchJobExecution>
	implements BatchJobExecution, ModelWrapper<BatchJobExecution> {

	public BatchJobExecutionWrapper(BatchJobExecution batchJobExecution) {
		super(batchJobExecution);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("batchJobExecutionId", getBatchJobExecutionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchJobInstanceId", getBatchJobInstanceId());
		attributes.put("status", getStatus());
		attributes.put("startTime", getStartTime());
		attributes.put("endTime", getEndTime());
		attributes.put("jobSettings", getJobSettings());
		attributes.put("error", getError());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long batchJobExecutionId = (Long)attributes.get("batchJobExecutionId");

		if (batchJobExecutionId != null) {
			setBatchJobExecutionId(batchJobExecutionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long batchJobInstanceId = (Long)attributes.get("batchJobInstanceId");

		if (batchJobInstanceId != null) {
			setBatchJobInstanceId(batchJobInstanceId);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Date startTime = (Date)attributes.get("startTime");

		if (startTime != null) {
			setStartTime(startTime);
		}

		Date endTime = (Date)attributes.get("endTime");

		if (endTime != null) {
			setEndTime(endTime);
		}

		String jobSettings = (String)attributes.get("jobSettings");

		if (jobSettings != null) {
			setJobSettings(jobSettings);
		}

		String error = (String)attributes.get("error");

		if (error != null) {
			setError(error);
		}
	}

	/**
	 * Returns the batch job execution ID of this batch job execution.
	 *
	 * @return the batch job execution ID of this batch job execution
	 */
	@Override
	public long getBatchJobExecutionId() {
		return model.getBatchJobExecutionId();
	}

	/**
	 * Returns the batch job instance ID of this batch job execution.
	 *
	 * @return the batch job instance ID of this batch job execution
	 */
	@Override
	public long getBatchJobInstanceId() {
		return model.getBatchJobInstanceId();
	}

	/**
	 * Returns the company ID of this batch job execution.
	 *
	 * @return the company ID of this batch job execution
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch job execution.
	 *
	 * @return the create date of this batch job execution
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the end time of this batch job execution.
	 *
	 * @return the end time of this batch job execution
	 */
	@Override
	public Date getEndTime() {
		return model.getEndTime();
	}

	/**
	 * Returns the error of this batch job execution.
	 *
	 * @return the error of this batch job execution
	 */
	@Override
	public String getError() {
		return model.getError();
	}

	/**
	 * Returns the job settings of this batch job execution.
	 *
	 * @return the job settings of this batch job execution
	 */
	@Override
	public String getJobSettings() {
		return model.getJobSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getJobSettingsProperties() {

		return model.getJobSettingsProperties();
	}

	/**
	 * Returns the modified date of this batch job execution.
	 *
	 * @return the modified date of this batch job execution
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this batch job execution.
	 *
	 * @return the primary key of this batch job execution
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start time of this batch job execution.
	 *
	 * @return the start time of this batch job execution
	 */
	@Override
	public Date getStartTime() {
		return model.getStartTime();
	}

	/**
	 * Returns the status of this batch job execution.
	 *
	 * @return the status of this batch job execution
	 */
	@Override
	public String getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the uuid of this batch job execution.
	 *
	 * @return the uuid of this batch job execution
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the batch job execution ID of this batch job execution.
	 *
	 * @param batchJobExecutionId the batch job execution ID of this batch job execution
	 */
	@Override
	public void setBatchJobExecutionId(long batchJobExecutionId) {
		model.setBatchJobExecutionId(batchJobExecutionId);
	}

	/**
	 * Sets the batch job instance ID of this batch job execution.
	 *
	 * @param batchJobInstanceId the batch job instance ID of this batch job execution
	 */
	@Override
	public void setBatchJobInstanceId(long batchJobInstanceId) {
		model.setBatchJobInstanceId(batchJobInstanceId);
	}

	/**
	 * Sets the company ID of this batch job execution.
	 *
	 * @param companyId the company ID of this batch job execution
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch job execution.
	 *
	 * @param createDate the create date of this batch job execution
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the end time of this batch job execution.
	 *
	 * @param endTime the end time of this batch job execution
	 */
	@Override
	public void setEndTime(Date endTime) {
		model.setEndTime(endTime);
	}

	/**
	 * Sets the error of this batch job execution.
	 *
	 * @param error the error of this batch job execution
	 */
	@Override
	public void setError(String error) {
		model.setError(error);
	}

	/**
	 * Sets the job settings of this batch job execution.
	 *
	 * @param jobSettings the job settings of this batch job execution
	 */
	@Override
	public void setJobSettings(String jobSettings) {
		model.setJobSettings(jobSettings);
	}

	@Override
	public void setJobSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			jobSettingsProperties) {

		model.setJobSettingsProperties(jobSettingsProperties);
	}

	/**
	 * Sets the modified date of this batch job execution.
	 *
	 * @param modifiedDate the modified date of this batch job execution
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this batch job execution.
	 *
	 * @param primaryKey the primary key of this batch job execution
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start time of this batch job execution.
	 *
	 * @param startTime the start time of this batch job execution
	 */
	@Override
	public void setStartTime(Date startTime) {
		model.setStartTime(startTime);
	}

	/**
	 * Sets the status of this batch job execution.
	 *
	 * @param status the status of this batch job execution
	 */
	@Override
	public void setStatus(String status) {
		model.setStatus(status);
	}

	/**
	 * Sets the uuid of this batch job execution.
	 *
	 * @param uuid the uuid of this batch job execution
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected BatchJobExecutionWrapper wrap(
		BatchJobExecution batchJobExecution) {

		return new BatchJobExecutionWrapper(batchJobExecution);
	}

}