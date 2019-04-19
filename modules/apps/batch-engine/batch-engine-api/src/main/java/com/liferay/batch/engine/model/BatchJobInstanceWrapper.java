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
 * This class is a wrapper for {@link BatchJobInstance}.
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobInstance
 * @generated
 */
@ProviderType
public class BatchJobInstanceWrapper
	extends BaseModelWrapper<BatchJobInstance>
	implements BatchJobInstance, ModelWrapper<BatchJobInstance> {

	public BatchJobInstanceWrapper(BatchJobInstance batchJobInstance) {
		super(batchJobInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("batchJobInstanceId", getBatchJobInstanceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("jobName", getJobName());
		attributes.put("jobKey", getJobKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long batchJobInstanceId = (Long)attributes.get("batchJobInstanceId");

		if (batchJobInstanceId != null) {
			setBatchJobInstanceId(batchJobInstanceId);
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

		String jobName = (String)attributes.get("jobName");

		if (jobName != null) {
			setJobName(jobName);
		}

		String jobKey = (String)attributes.get("jobKey");

		if (jobKey != null) {
			setJobKey(jobKey);
		}
	}

	/**
	 * Returns the batch job instance ID of this batch job instance.
	 *
	 * @return the batch job instance ID of this batch job instance
	 */
	@Override
	public long getBatchJobInstanceId() {
		return model.getBatchJobInstanceId();
	}

	/**
	 * Returns the company ID of this batch job instance.
	 *
	 * @return the company ID of this batch job instance
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch job instance.
	 *
	 * @return the create date of this batch job instance
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the job key of this batch job instance.
	 *
	 * @return the job key of this batch job instance
	 */
	@Override
	public String getJobKey() {
		return model.getJobKey();
	}

	/**
	 * Returns the job name of this batch job instance.
	 *
	 * @return the job name of this batch job instance
	 */
	@Override
	public String getJobName() {
		return model.getJobName();
	}

	/**
	 * Returns the modified date of this batch job instance.
	 *
	 * @return the modified date of this batch job instance
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this batch job instance.
	 *
	 * @return the primary key of this batch job instance
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this batch job instance.
	 *
	 * @return the uuid of this batch job instance
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
	 * Sets the batch job instance ID of this batch job instance.
	 *
	 * @param batchJobInstanceId the batch job instance ID of this batch job instance
	 */
	@Override
	public void setBatchJobInstanceId(long batchJobInstanceId) {
		model.setBatchJobInstanceId(batchJobInstanceId);
	}

	/**
	 * Sets the company ID of this batch job instance.
	 *
	 * @param companyId the company ID of this batch job instance
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch job instance.
	 *
	 * @param createDate the create date of this batch job instance
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the job key of this batch job instance.
	 *
	 * @param jobKey the job key of this batch job instance
	 */
	@Override
	public void setJobKey(String jobKey) {
		model.setJobKey(jobKey);
	}

	/**
	 * Sets the job name of this batch job instance.
	 *
	 * @param jobName the job name of this batch job instance
	 */
	@Override
	public void setJobName(String jobName) {
		model.setJobName(jobName);
	}

	/**
	 * Sets the modified date of this batch job instance.
	 *
	 * @param modifiedDate the modified date of this batch job instance
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this batch job instance.
	 *
	 * @param primaryKey the primary key of this batch job instance
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this batch job instance.
	 *
	 * @param uuid the uuid of this batch job instance
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
	protected BatchJobInstanceWrapper wrap(BatchJobInstance batchJobInstance) {
		return new BatchJobInstanceWrapper(batchJobInstance);
	}

}