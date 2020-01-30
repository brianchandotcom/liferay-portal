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
 * This class is a wrapper for {@link BatchEngineExportTask}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTask
 * @generated
 */
public class BatchEngineExportTaskWrapper
	extends BaseModelWrapper<BatchEngineExportTask>
	implements BatchEngineExportTask, ModelWrapper<BatchEngineExportTask> {

	public BatchEngineExportTaskWrapper(
		BatchEngineExportTask batchEngineExportTask) {

		super(batchEngineExportTask);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("batchEngineExportTaskId", getBatchEngineExportTaskId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("callbackURL", getCallbackURL());
		attributes.put("className", getClassName());
		attributes.put("content", getContent());
		attributes.put("contentType", getContentType());
		attributes.put("endTime", getEndTime());
		attributes.put("errorMessage", getErrorMessage());
		attributes.put("fieldNames", getFieldNames());
		attributes.put("executeStatus", getExecuteStatus());
		attributes.put("parameters", getParameters());
		attributes.put("startTime", getStartTime());

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

		Long batchEngineExportTaskId = (Long)attributes.get(
			"batchEngineExportTaskId");

		if (batchEngineExportTaskId != null) {
			setBatchEngineExportTaskId(batchEngineExportTaskId);
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

		String fieldNames = (String)attributes.get("fieldNames");

		if (fieldNames != null) {
			setFieldNames(fieldNames);
		}

		String executeStatus = (String)attributes.get("executeStatus");

		if (executeStatus != null) {
			setExecuteStatus(executeStatus);
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
	}

	/**
	 * Returns the batch engine export task ID of this batch engine export task.
	 *
	 * @return the batch engine export task ID of this batch engine export task
	 */
	@Override
	public long getBatchEngineExportTaskId() {
		return model.getBatchEngineExportTaskId();
	}

	/**
	 * Returns the callback url of this batch engine export task.
	 *
	 * @return the callback url of this batch engine export task
	 */
	@Override
	public String getCallbackURL() {
		return model.getCallbackURL();
	}

	/**
	 * Returns the class name of this batch engine export task.
	 *
	 * @return the class name of this batch engine export task
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the company ID of this batch engine export task.
	 *
	 * @return the company ID of this batch engine export task
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this batch engine export task.
	 *
	 * @return the content of this batch engine export task
	 */
	@Override
	public Blob getContent() {
		return model.getContent();
	}

	/**
	 * Returns the content type of this batch engine export task.
	 *
	 * @return the content type of this batch engine export task
	 */
	@Override
	public String getContentType() {
		return model.getContentType();
	}

	/**
	 * Returns the create date of this batch engine export task.
	 *
	 * @return the create date of this batch engine export task
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the end time of this batch engine export task.
	 *
	 * @return the end time of this batch engine export task
	 */
	@Override
	public Date getEndTime() {
		return model.getEndTime();
	}

	/**
	 * Returns the error message of this batch engine export task.
	 *
	 * @return the error message of this batch engine export task
	 */
	@Override
	public String getErrorMessage() {
		return model.getErrorMessage();
	}

	/**
	 * Returns the execute status of this batch engine export task.
	 *
	 * @return the execute status of this batch engine export task
	 */
	@Override
	public String getExecuteStatus() {
		return model.getExecuteStatus();
	}

	/**
	 * Returns the field names of this batch engine export task.
	 *
	 * @return the field names of this batch engine export task
	 */
	@Override
	public String getFieldNames() {
		return model.getFieldNames();
	}

	@Override
	public java.util.List<String> getFieldNamesList() {
		return model.getFieldNamesList();
	}

	/**
	 * Returns the modified date of this batch engine export task.
	 *
	 * @return the modified date of this batch engine export task
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch engine export task.
	 *
	 * @return the mvcc version of this batch engine export task
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parameters of this batch engine export task.
	 *
	 * @return the parameters of this batch engine export task
	 */
	@Override
	public Map<String, Serializable> getParameters() {
		return model.getParameters();
	}

	/**
	 * Returns the primary key of this batch engine export task.
	 *
	 * @return the primary key of this batch engine export task
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start time of this batch engine export task.
	 *
	 * @return the start time of this batch engine export task
	 */
	@Override
	public Date getStartTime() {
		return model.getStartTime();
	}

	/**
	 * Returns the user ID of this batch engine export task.
	 *
	 * @return the user ID of this batch engine export task
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this batch engine export task.
	 *
	 * @return the user uuid of this batch engine export task
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this batch engine export task.
	 *
	 * @return the uuid of this batch engine export task
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
	 * Sets the batch engine export task ID of this batch engine export task.
	 *
	 * @param batchEngineExportTaskId the batch engine export task ID of this batch engine export task
	 */
	@Override
	public void setBatchEngineExportTaskId(long batchEngineExportTaskId) {
		model.setBatchEngineExportTaskId(batchEngineExportTaskId);
	}

	/**
	 * Sets the callback url of this batch engine export task.
	 *
	 * @param callbackURL the callback url of this batch engine export task
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		model.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the class name of this batch engine export task.
	 *
	 * @param className the class name of this batch engine export task
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the company ID of this batch engine export task.
	 *
	 * @param companyId the company ID of this batch engine export task
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this batch engine export task.
	 *
	 * @param content the content of this batch engine export task
	 */
	@Override
	public void setContent(Blob content) {
		model.setContent(content);
	}

	/**
	 * Sets the content type of this batch engine export task.
	 *
	 * @param contentType the content type of this batch engine export task
	 */
	@Override
	public void setContentType(String contentType) {
		model.setContentType(contentType);
	}

	/**
	 * Sets the create date of this batch engine export task.
	 *
	 * @param createDate the create date of this batch engine export task
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the end time of this batch engine export task.
	 *
	 * @param endTime the end time of this batch engine export task
	 */
	@Override
	public void setEndTime(Date endTime) {
		model.setEndTime(endTime);
	}

	/**
	 * Sets the error message of this batch engine export task.
	 *
	 * @param errorMessage the error message of this batch engine export task
	 */
	@Override
	public void setErrorMessage(String errorMessage) {
		model.setErrorMessage(errorMessage);
	}

	/**
	 * Sets the execute status of this batch engine export task.
	 *
	 * @param executeStatus the execute status of this batch engine export task
	 */
	@Override
	public void setExecuteStatus(String executeStatus) {
		model.setExecuteStatus(executeStatus);
	}

	/**
	 * Sets the field names of this batch engine export task.
	 *
	 * @param fieldNames the field names of this batch engine export task
	 */
	@Override
	public void setFieldNames(String fieldNames) {
		model.setFieldNames(fieldNames);
	}

	@Override
	public void setFieldNamesList(java.util.List<String> fieldNamesList) {
		model.setFieldNamesList(fieldNamesList);
	}

	/**
	 * Sets the modified date of this batch engine export task.
	 *
	 * @param modifiedDate the modified date of this batch engine export task
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch engine export task.
	 *
	 * @param mvccVersion the mvcc version of this batch engine export task
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parameters of this batch engine export task.
	 *
	 * @param parameters the parameters of this batch engine export task
	 */
	@Override
	public void setParameters(Map<String, Serializable> parameters) {
		model.setParameters(parameters);
	}

	/**
	 * Sets the primary key of this batch engine export task.
	 *
	 * @param primaryKey the primary key of this batch engine export task
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start time of this batch engine export task.
	 *
	 * @param startTime the start time of this batch engine export task
	 */
	@Override
	public void setStartTime(Date startTime) {
		model.setStartTime(startTime);
	}

	/**
	 * Sets the user ID of this batch engine export task.
	 *
	 * @param userId the user ID of this batch engine export task
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this batch engine export task.
	 *
	 * @param userUuid the user uuid of this batch engine export task
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this batch engine export task.
	 *
	 * @param uuid the uuid of this batch engine export task
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
	protected BatchEngineExportTaskWrapper wrap(
		BatchEngineExportTask batchEngineExportTask) {

		return new BatchEngineExportTaskWrapper(batchEngineExportTask);
	}

}