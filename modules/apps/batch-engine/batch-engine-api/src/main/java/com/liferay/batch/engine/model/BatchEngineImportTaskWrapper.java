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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements BatchEngineImportTask, ModelWrapper<BatchEngineImportTask> {

	public BatchEngineImportTaskWrapper(
		BatchEngineImportTask batchEngineImportTask) {

		_batchEngineImportTask = batchEngineImportTask;
	}

	@Override
	public Class<?> getModelClass() {
		return BatchEngineImportTask.class;
	}

	@Override
	public String getModelClassName() {
		return BatchEngineImportTask.class.getName();
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
		attributes.put("taskItemDelegateName", getTaskItemDelegateName());

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

		String taskItemDelegateName = (String)attributes.get(
			"taskItemDelegateName");

		if (taskItemDelegateName != null) {
			setTaskItemDelegateName(taskItemDelegateName);
		}
	}

	@Override
	public Object clone() {
		return new BatchEngineImportTaskWrapper(
			(BatchEngineImportTask)_batchEngineImportTask.clone());
	}

	@Override
	public int compareTo(BatchEngineImportTask batchEngineImportTask) {
		return _batchEngineImportTask.compareTo(batchEngineImportTask);
	}

	/**
	 * Returns the batch engine import task ID of this batch engine import task.
	 *
	 * @return the batch engine import task ID of this batch engine import task
	 */
	@Override
	public long getBatchEngineImportTaskId() {
		return _batchEngineImportTask.getBatchEngineImportTaskId();
	}

	/**
	 * Returns the batch size of this batch engine import task.
	 *
	 * @return the batch size of this batch engine import task
	 */
	@Override
	public long getBatchSize() {
		return _batchEngineImportTask.getBatchSize();
	}

	/**
	 * Returns the callback url of this batch engine import task.
	 *
	 * @return the callback url of this batch engine import task
	 */
	@Override
	public String getCallbackURL() {
		return _batchEngineImportTask.getCallbackURL();
	}

	/**
	 * Returns the class name of this batch engine import task.
	 *
	 * @return the class name of this batch engine import task
	 */
	@Override
	public String getClassName() {
		return _batchEngineImportTask.getClassName();
	}

	/**
	 * Returns the company ID of this batch engine import task.
	 *
	 * @return the company ID of this batch engine import task
	 */
	@Override
	public long getCompanyId() {
		return _batchEngineImportTask.getCompanyId();
	}

	/**
	 * Returns the content of this batch engine import task.
	 *
	 * @return the content of this batch engine import task
	 */
	@Override
	public Blob getContent() {
		return _batchEngineImportTask.getContent();
	}

	/**
	 * Returns the content type of this batch engine import task.
	 *
	 * @return the content type of this batch engine import task
	 */
	@Override
	public String getContentType() {
		return _batchEngineImportTask.getContentType();
	}

	/**
	 * Returns the create date of this batch engine import task.
	 *
	 * @return the create date of this batch engine import task
	 */
	@Override
	public Date getCreateDate() {
		return _batchEngineImportTask.getCreateDate();
	}

	/**
	 * Returns the end time of this batch engine import task.
	 *
	 * @return the end time of this batch engine import task
	 */
	@Override
	public Date getEndTime() {
		return _batchEngineImportTask.getEndTime();
	}

	/**
	 * Returns the error message of this batch engine import task.
	 *
	 * @return the error message of this batch engine import task
	 */
	@Override
	public String getErrorMessage() {
		return _batchEngineImportTask.getErrorMessage();
	}

	/**
	 * Returns the execute status of this batch engine import task.
	 *
	 * @return the execute status of this batch engine import task
	 */
	@Override
	public String getExecuteStatus() {
		return _batchEngineImportTask.getExecuteStatus();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _batchEngineImportTask.getExpandoBridge();
	}

	/**
	 * Returns the field name mapping of this batch engine import task.
	 *
	 * @return the field name mapping of this batch engine import task
	 */
	@Override
	public Map<String, Serializable> getFieldNameMapping() {
		return _batchEngineImportTask.getFieldNameMapping();
	}

	/**
	 * Returns the modified date of this batch engine import task.
	 *
	 * @return the modified date of this batch engine import task
	 */
	@Override
	public Date getModifiedDate() {
		return _batchEngineImportTask.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch engine import task.
	 *
	 * @return the mvcc version of this batch engine import task
	 */
	@Override
	public long getMvccVersion() {
		return _batchEngineImportTask.getMvccVersion();
	}

	/**
	 * Returns the operation of this batch engine import task.
	 *
	 * @return the operation of this batch engine import task
	 */
	@Override
	public String getOperation() {
		return _batchEngineImportTask.getOperation();
	}

	/**
	 * Returns the parameters of this batch engine import task.
	 *
	 * @return the parameters of this batch engine import task
	 */
	@Override
	public Map<String, Serializable> getParameters() {
		return _batchEngineImportTask.getParameters();
	}

	/**
	 * Returns the primary key of this batch engine import task.
	 *
	 * @return the primary key of this batch engine import task
	 */
	@Override
	public long getPrimaryKey() {
		return _batchEngineImportTask.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _batchEngineImportTask.getPrimaryKeyObj();
	}

	/**
	 * Returns the start time of this batch engine import task.
	 *
	 * @return the start time of this batch engine import task
	 */
	@Override
	public Date getStartTime() {
		return _batchEngineImportTask.getStartTime();
	}

	/**
	 * Returns the task item delegate name of this batch engine import task.
	 *
	 * @return the task item delegate name of this batch engine import task
	 */
	@Override
	public String getTaskItemDelegateName() {
		return _batchEngineImportTask.getTaskItemDelegateName();
	}

	/**
	 * Returns the user ID of this batch engine import task.
	 *
	 * @return the user ID of this batch engine import task
	 */
	@Override
	public long getUserId() {
		return _batchEngineImportTask.getUserId();
	}

	/**
	 * Returns the user uuid of this batch engine import task.
	 *
	 * @return the user uuid of this batch engine import task
	 */
	@Override
	public String getUserUuid() {
		return _batchEngineImportTask.getUserUuid();
	}

	/**
	 * Returns the uuid of this batch engine import task.
	 *
	 * @return the uuid of this batch engine import task
	 */
	@Override
	public String getUuid() {
		return _batchEngineImportTask.getUuid();
	}

	@Override
	public int hashCode() {
		return _batchEngineImportTask.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _batchEngineImportTask.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _batchEngineImportTask.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _batchEngineImportTask.isNew();
	}

	@Override
	public void persist() {
		_batchEngineImportTask.persist();
	}

	/**
	 * Sets the batch engine import task ID of this batch engine import task.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID of this batch engine import task
	 */
	@Override
	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		_batchEngineImportTask.setBatchEngineImportTaskId(
			batchEngineImportTaskId);
	}

	/**
	 * Sets the batch size of this batch engine import task.
	 *
	 * @param batchSize the batch size of this batch engine import task
	 */
	@Override
	public void setBatchSize(long batchSize) {
		_batchEngineImportTask.setBatchSize(batchSize);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_batchEngineImportTask.setCachedModel(cachedModel);
	}

	/**
	 * Sets the callback url of this batch engine import task.
	 *
	 * @param callbackURL the callback url of this batch engine import task
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		_batchEngineImportTask.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the class name of this batch engine import task.
	 *
	 * @param className the class name of this batch engine import task
	 */
	@Override
	public void setClassName(String className) {
		_batchEngineImportTask.setClassName(className);
	}

	/**
	 * Sets the company ID of this batch engine import task.
	 *
	 * @param companyId the company ID of this batch engine import task
	 */
	@Override
	public void setCompanyId(long companyId) {
		_batchEngineImportTask.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this batch engine import task.
	 *
	 * @param content the content of this batch engine import task
	 */
	@Override
	public void setContent(Blob content) {
		_batchEngineImportTask.setContent(content);
	}

	/**
	 * Sets the content type of this batch engine import task.
	 *
	 * @param contentType the content type of this batch engine import task
	 */
	@Override
	public void setContentType(String contentType) {
		_batchEngineImportTask.setContentType(contentType);
	}

	/**
	 * Sets the create date of this batch engine import task.
	 *
	 * @param createDate the create date of this batch engine import task
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_batchEngineImportTask.setCreateDate(createDate);
	}

	/**
	 * Sets the end time of this batch engine import task.
	 *
	 * @param endTime the end time of this batch engine import task
	 */
	@Override
	public void setEndTime(Date endTime) {
		_batchEngineImportTask.setEndTime(endTime);
	}

	/**
	 * Sets the error message of this batch engine import task.
	 *
	 * @param errorMessage the error message of this batch engine import task
	 */
	@Override
	public void setErrorMessage(String errorMessage) {
		_batchEngineImportTask.setErrorMessage(errorMessage);
	}

	/**
	 * Sets the execute status of this batch engine import task.
	 *
	 * @param executeStatus the execute status of this batch engine import task
	 */
	@Override
	public void setExecuteStatus(String executeStatus) {
		_batchEngineImportTask.setExecuteStatus(executeStatus);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_batchEngineImportTask.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_batchEngineImportTask.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_batchEngineImportTask.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the field name mapping of this batch engine import task.
	 *
	 * @param fieldNameMapping the field name mapping of this batch engine import task
	 */
	@Override
	public void setFieldNameMapping(
		Map<String, Serializable> fieldNameMapping) {

		_batchEngineImportTask.setFieldNameMapping(fieldNameMapping);
	}

	/**
	 * Sets the modified date of this batch engine import task.
	 *
	 * @param modifiedDate the modified date of this batch engine import task
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_batchEngineImportTask.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch engine import task.
	 *
	 * @param mvccVersion the mvcc version of this batch engine import task
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_batchEngineImportTask.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_batchEngineImportTask.setNew(n);
	}

	/**
	 * Sets the operation of this batch engine import task.
	 *
	 * @param operation the operation of this batch engine import task
	 */
	@Override
	public void setOperation(String operation) {
		_batchEngineImportTask.setOperation(operation);
	}

	/**
	 * Sets the parameters of this batch engine import task.
	 *
	 * @param parameters the parameters of this batch engine import task
	 */
	@Override
	public void setParameters(Map<String, Serializable> parameters) {
		_batchEngineImportTask.setParameters(parameters);
	}

	/**
	 * Sets the primary key of this batch engine import task.
	 *
	 * @param primaryKey the primary key of this batch engine import task
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_batchEngineImportTask.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_batchEngineImportTask.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the start time of this batch engine import task.
	 *
	 * @param startTime the start time of this batch engine import task
	 */
	@Override
	public void setStartTime(Date startTime) {
		_batchEngineImportTask.setStartTime(startTime);
	}

	/**
	 * Sets the task item delegate name of this batch engine import task.
	 *
	 * @param taskItemDelegateName the task item delegate name of this batch engine import task
	 */
	@Override
	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_batchEngineImportTask.setTaskItemDelegateName(taskItemDelegateName);
	}

	/**
	 * Sets the user ID of this batch engine import task.
	 *
	 * @param userId the user ID of this batch engine import task
	 */
	@Override
	public void setUserId(long userId) {
		_batchEngineImportTask.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this batch engine import task.
	 *
	 * @param userUuid the user uuid of this batch engine import task
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_batchEngineImportTask.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this batch engine import task.
	 *
	 * @param uuid the uuid of this batch engine import task
	 */
	@Override
	public void setUuid(String uuid) {
		_batchEngineImportTask.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BatchEngineImportTask>
		toCacheModel() {

		return _batchEngineImportTask.toCacheModel();
	}

	@Override
	public BatchEngineImportTask toEscapedModel() {
		return new BatchEngineImportTaskWrapper(
			_batchEngineImportTask.toEscapedModel());
	}

	@Override
	public String toString() {
		return _batchEngineImportTask.toString();
	}

	@Override
	public BatchEngineImportTask toUnescapedModel() {
		return new BatchEngineImportTaskWrapper(
			_batchEngineImportTask.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _batchEngineImportTask.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineImportTaskWrapper)) {
			return false;
		}

		BatchEngineImportTaskWrapper batchEngineImportTaskWrapper =
			(BatchEngineImportTaskWrapper)object;

		if (Objects.equals(
				_batchEngineImportTask,
				batchEngineImportTaskWrapper._batchEngineImportTask)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _batchEngineImportTask.getStagedModelType();
	}

	@Override
	public BatchEngineImportTask getWrappedModel() {
		return _batchEngineImportTask;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _batchEngineImportTask.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _batchEngineImportTask.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_batchEngineImportTask.resetOriginalValues();
	}

	private final BatchEngineImportTask _batchEngineImportTask;

}