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

import java.io.Serializable;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BatchEngineImportTask}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTask
 * @generated
 */
public class BatchEngineImportTaskWrapper
	extends BaseModelWrapper<BatchEngineImportTask>
	implements BatchEngineImportTask, ModelWrapper<BatchEngineImportTask> {

	public BatchEngineImportTaskWrapper(
		BatchEngineImportTask batchEngineImportTask) {

		super(batchEngineImportTask);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("batchEngineImportTaskId", getBatchEngineImportTaskId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchSize", getBatchSize());
		attributes.put("callbackURL", getCallbackURL());
		attributes.put("className", getClassName());
		attributes.put("content", getContent());
		attributes.put("contentType", getContentType());
		attributes.put("endTime", getEndTime());
		attributes.put("errorMessage", getErrorMessage());
		attributes.put("executeStatus", getExecuteStatus());
		attributes.put("fieldNameMapping", getFieldNameMapping());
		attributes.put("operation", getOperation());
		attributes.put("parameters", getParameters());
		attributes.put("startTime", getStartTime());
		attributes.put("version", getVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long batchEngineImportTaskId = (Long)attributes.get(
			"batchEngineImportTaskId");

		if (batchEngineImportTaskId != null) {
			setBatchEngineImportTaskId(batchEngineImportTaskId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long batchSize = (Long)attributes.get("batchSize");

		if (batchSize != null) {
			setBatchSize(batchSize);
		}

		String callbackURL = (String)attributes.get("callbackURL");

		if (callbackURL != null) {
			setCallbackURL(callbackURL);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Blob content = (Blob)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String contentType = (String)attributes.get("contentType");

		if (contentType != null) {
			setContentType(contentType);
		}

		Date endTime = (Date)attributes.get("endTime");

		if (endTime != null) {
			setEndTime(endTime);
		}

		String errorMessage = (String)attributes.get("errorMessage");

		if (errorMessage != null) {
			setErrorMessage(errorMessage);
		}

		String executeStatus = (String)attributes.get("executeStatus");

		if (executeStatus != null) {
			setExecuteStatus(executeStatus);
		}

		Map<String, Serializable> fieldNameMapping =
			(Map<String, Serializable>)attributes.get("fieldNameMapping");

		if (fieldNameMapping != null) {
			setFieldNameMapping(fieldNameMapping);
		}

		String operation = (String)attributes.get("operation");

		if (operation != null) {
			setOperation(operation);
		}

		Map<String, Serializable> parameters =
			(Map<String, Serializable>)attributes.get("parameters");

		if (parameters != null) {
			setParameters(parameters);
		}

		Date startTime = (Date)attributes.get("startTime");

		if (startTime != null) {
			setStartTime(startTime);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	/**
	 * Returns the batch engine import task ID of this batch engine import task.
	 *
	 * @return the batch engine import task ID of this batch engine import task
	 */
	@Override
	public long getBatchEngineImportTaskId() {
		return model.getBatchEngineImportTaskId();
	}

	/**
	 * Returns the batch size of this batch engine import task.
	 *
	 * @return the batch size of this batch engine import task
	 */
	@Override
	public long getBatchSize() {
		return model.getBatchSize();
	}

	/**
	 * Returns the callback url of this batch engine import task.
	 *
	 * @return the callback url of this batch engine import task
	 */
	@Override
	public String getCallbackURL() {
		return model.getCallbackURL();
	}

	/**
	 * Returns the class name of this batch engine import task.
	 *
	 * @return the class name of this batch engine import task
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the company ID of this batch engine import task.
	 *
	 * @return the company ID of this batch engine import task
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this batch engine import task.
	 *
	 * @return the content of this batch engine import task
	 */
	@Override
	public Blob getContent() {
		return model.getContent();
	}

	/**
	 * Returns the content type of this batch engine import task.
	 *
	 * @return the content type of this batch engine import task
	 */
	@Override
	public String getContentType() {
		return model.getContentType();
	}

	/**
	 * Returns the create date of this batch engine import task.
	 *
	 * @return the create date of this batch engine import task
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the end time of this batch engine import task.
	 *
	 * @return the end time of this batch engine import task
	 */
	@Override
	public Date getEndTime() {
		return model.getEndTime();
	}

	/**
	 * Returns the error message of this batch engine import task.
	 *
	 * @return the error message of this batch engine import task
	 */
	@Override
	public String getErrorMessage() {
		return model.getErrorMessage();
	}

	/**
	 * Returns the execute status of this batch engine import task.
	 *
	 * @return the execute status of this batch engine import task
	 */
	@Override
	public String getExecuteStatus() {
		return model.getExecuteStatus();
	}

	/**
	 * Returns the field name mapping of this batch engine import task.
	 *
	 * @return the field name mapping of this batch engine import task
	 */
	@Override
	public Map<String, Serializable> getFieldNameMapping() {
		return model.getFieldNameMapping();
	}

	/**
	 * Returns the modified date of this batch engine import task.
	 *
	 * @return the modified date of this batch engine import task
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch engine import task.
	 *
	 * @return the mvcc version of this batch engine import task
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the operation of this batch engine import task.
	 *
	 * @return the operation of this batch engine import task
	 */
	@Override
	public String getOperation() {
		return model.getOperation();
	}

	/**
	 * Returns the parameters of this batch engine import task.
	 *
	 * @return the parameters of this batch engine import task
	 */
	@Override
	public Map<String, Serializable> getParameters() {
		return model.getParameters();
	}

	/**
	 * Returns the primary key of this batch engine import task.
	 *
	 * @return the primary key of this batch engine import task
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start time of this batch engine import task.
	 *
	 * @return the start time of this batch engine import task
	 */
	@Override
	public Date getStartTime() {
		return model.getStartTime();
	}

	/**
	 * Returns the user ID of this batch engine import task.
	 *
	 * @return the user ID of this batch engine import task
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this batch engine import task.
	 *
	 * @return the user uuid of this batch engine import task
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this batch engine import task.
	 *
	 * @return the uuid of this batch engine import task
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this batch engine import task.
	 *
	 * @return the version of this batch engine import task
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a batch engine import task model instance should use the <code>BatchEngineImportTask</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the batch engine import task ID of this batch engine import task.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID of this batch engine import task
	 */
	@Override
	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		model.setBatchEngineImportTaskId(batchEngineImportTaskId);
	}

	/**
	 * Sets the batch size of this batch engine import task.
	 *
	 * @param batchSize the batch size of this batch engine import task
	 */
	@Override
	public void setBatchSize(long batchSize) {
		model.setBatchSize(batchSize);
	}

	/**
	 * Sets the callback url of this batch engine import task.
	 *
	 * @param callbackURL the callback url of this batch engine import task
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		model.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the class name of this batch engine import task.
	 *
	 * @param className the class name of this batch engine import task
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the company ID of this batch engine import task.
	 *
	 * @param companyId the company ID of this batch engine import task
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this batch engine import task.
	 *
	 * @param content the content of this batch engine import task
	 */
	@Override
	public void setContent(Blob content) {
		model.setContent(content);
	}

	/**
	 * Sets the content type of this batch engine import task.
	 *
	 * @param contentType the content type of this batch engine import task
	 */
	@Override
	public void setContentType(String contentType) {
		model.setContentType(contentType);
	}

	/**
	 * Sets the create date of this batch engine import task.
	 *
	 * @param createDate the create date of this batch engine import task
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the end time of this batch engine import task.
	 *
	 * @param endTime the end time of this batch engine import task
	 */
	@Override
	public void setEndTime(Date endTime) {
		model.setEndTime(endTime);
	}

	/**
	 * Sets the error message of this batch engine import task.
	 *
	 * @param errorMessage the error message of this batch engine import task
	 */
	@Override
	public void setErrorMessage(String errorMessage) {
		model.setErrorMessage(errorMessage);
	}

	/**
	 * Sets the execute status of this batch engine import task.
	 *
	 * @param executeStatus the execute status of this batch engine import task
	 */
	@Override
	public void setExecuteStatus(String executeStatus) {
		model.setExecuteStatus(executeStatus);
	}

	/**
	 * Sets the field name mapping of this batch engine import task.
	 *
	 * @param fieldNameMapping the field name mapping of this batch engine import task
	 */
	@Override
	public void setFieldNameMapping(
		Map<String, Serializable> fieldNameMapping) {

		model.setFieldNameMapping(fieldNameMapping);
	}

	/**
	 * Sets the modified date of this batch engine import task.
	 *
	 * @param modifiedDate the modified date of this batch engine import task
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch engine import task.
	 *
	 * @param mvccVersion the mvcc version of this batch engine import task
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the operation of this batch engine import task.
	 *
	 * @param operation the operation of this batch engine import task
	 */
	@Override
	public void setOperation(String operation) {
		model.setOperation(operation);
	}

	/**
	 * Sets the parameters of this batch engine import task.
	 *
	 * @param parameters the parameters of this batch engine import task
	 */
	@Override
	public void setParameters(Map<String, Serializable> parameters) {
		model.setParameters(parameters);
	}

	/**
	 * Sets the primary key of this batch engine import task.
	 *
	 * @param primaryKey the primary key of this batch engine import task
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start time of this batch engine import task.
	 *
	 * @param startTime the start time of this batch engine import task
	 */
	@Override
	public void setStartTime(Date startTime) {
		model.setStartTime(startTime);
	}

	/**
	 * Sets the user ID of this batch engine import task.
	 *
	 * @param userId the user ID of this batch engine import task
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this batch engine import task.
	 *
	 * @param userUuid the user uuid of this batch engine import task
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this batch engine import task.
	 *
	 * @param uuid the uuid of this batch engine import task
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this batch engine import task.
	 *
	 * @param version the version of this batch engine import task
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected BatchEngineImportTaskWrapper wrap(
		BatchEngineImportTask batchEngineImportTask) {

		return new BatchEngineImportTaskWrapper(batchEngineImportTask);
	}

}