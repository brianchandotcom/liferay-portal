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
 * This class is a wrapper for {@link BatchEngineExportTask}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTask
 * @generated
 */
public class BatchEngineExportTaskWrapper
	implements BatchEngineExportTask, ModelWrapper<BatchEngineExportTask> {

	public BatchEngineExportTaskWrapper(
		BatchEngineExportTask batchEngineExportTask) {

		_batchEngineExportTask = batchEngineExportTask;
	}

	@Override
	public Class<?> getModelClass() {
		return BatchEngineExportTask.class;
	}

	@Override
	public String getModelClassName() {
		return BatchEngineExportTask.class.getName();
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

		String taskItemDelegateName = (String)attributes.get(
			"taskItemDelegateName");

		if (taskItemDelegateName != null) {
			setTaskItemDelegateName(taskItemDelegateName);
		}
	}

	@Override
	public Object clone() {
		return new BatchEngineExportTaskWrapper(
			(BatchEngineExportTask)_batchEngineExportTask.clone());
	}

	@Override
	public int compareTo(BatchEngineExportTask batchEngineExportTask) {
		return _batchEngineExportTask.compareTo(batchEngineExportTask);
	}

	/**
	 * Returns the batch engine export task ID of this batch engine export task.
	 *
	 * @return the batch engine export task ID of this batch engine export task
	 */
	@Override
	public long getBatchEngineExportTaskId() {
		return _batchEngineExportTask.getBatchEngineExportTaskId();
	}

	/**
	 * Returns the callback url of this batch engine export task.
	 *
	 * @return the callback url of this batch engine export task
	 */
	@Override
	public String getCallbackURL() {
		return _batchEngineExportTask.getCallbackURL();
	}

	/**
	 * Returns the class name of this batch engine export task.
	 *
	 * @return the class name of this batch engine export task
	 */
	@Override
	public String getClassName() {
		return _batchEngineExportTask.getClassName();
	}

	/**
	 * Returns the company ID of this batch engine export task.
	 *
	 * @return the company ID of this batch engine export task
	 */
	@Override
	public long getCompanyId() {
		return _batchEngineExportTask.getCompanyId();
	}

	/**
	 * Returns the content of this batch engine export task.
	 *
	 * @return the content of this batch engine export task
	 */
	@Override
	public Blob getContent() {
		return _batchEngineExportTask.getContent();
	}

	/**
	 * Returns the content type of this batch engine export task.
	 *
	 * @return the content type of this batch engine export task
	 */
	@Override
	public String getContentType() {
		return _batchEngineExportTask.getContentType();
	}

	/**
	 * Returns the create date of this batch engine export task.
	 *
	 * @return the create date of this batch engine export task
	 */
	@Override
	public Date getCreateDate() {
		return _batchEngineExportTask.getCreateDate();
	}

	/**
	 * Returns the end time of this batch engine export task.
	 *
	 * @return the end time of this batch engine export task
	 */
	@Override
	public Date getEndTime() {
		return _batchEngineExportTask.getEndTime();
	}

	/**
	 * Returns the error message of this batch engine export task.
	 *
	 * @return the error message of this batch engine export task
	 */
	@Override
	public String getErrorMessage() {
		return _batchEngineExportTask.getErrorMessage();
	}

	/**
	 * Returns the execute status of this batch engine export task.
	 *
	 * @return the execute status of this batch engine export task
	 */
	@Override
	public String getExecuteStatus() {
		return _batchEngineExportTask.getExecuteStatus();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _batchEngineExportTask.getExpandoBridge();
	}

	/**
	 * Returns the field names of this batch engine export task.
	 *
	 * @return the field names of this batch engine export task
	 */
	@Override
	public String getFieldNames() {
		return _batchEngineExportTask.getFieldNames();
	}

	@Override
	public java.util.List<String> getFieldNamesList() {
		return _batchEngineExportTask.getFieldNamesList();
	}

	/**
	 * Returns the modified date of this batch engine export task.
	 *
	 * @return the modified date of this batch engine export task
	 */
	@Override
	public Date getModifiedDate() {
		return _batchEngineExportTask.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch engine export task.
	 *
	 * @return the mvcc version of this batch engine export task
	 */
	@Override
	public long getMvccVersion() {
		return _batchEngineExportTask.getMvccVersion();
	}

	/**
	 * Returns the parameters of this batch engine export task.
	 *
	 * @return the parameters of this batch engine export task
	 */
	@Override
	public Map<String, Serializable> getParameters() {
		return _batchEngineExportTask.getParameters();
	}

	/**
	 * Returns the primary key of this batch engine export task.
	 *
	 * @return the primary key of this batch engine export task
	 */
	@Override
	public long getPrimaryKey() {
		return _batchEngineExportTask.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _batchEngineExportTask.getPrimaryKeyObj();
	}

	/**
	 * Returns the start time of this batch engine export task.
	 *
	 * @return the start time of this batch engine export task
	 */
	@Override
	public Date getStartTime() {
		return _batchEngineExportTask.getStartTime();
	}

	/**
	 * Returns the task item delegate name of this batch engine export task.
	 *
	 * @return the task item delegate name of this batch engine export task
	 */
	@Override
	public String getTaskItemDelegateName() {
		return _batchEngineExportTask.getTaskItemDelegateName();
	}

	/**
	 * Returns the user ID of this batch engine export task.
	 *
	 * @return the user ID of this batch engine export task
	 */
	@Override
	public long getUserId() {
		return _batchEngineExportTask.getUserId();
	}

	/**
	 * Returns the user uuid of this batch engine export task.
	 *
	 * @return the user uuid of this batch engine export task
	 */
	@Override
	public String getUserUuid() {
		return _batchEngineExportTask.getUserUuid();
	}

	/**
	 * Returns the uuid of this batch engine export task.
	 *
	 * @return the uuid of this batch engine export task
	 */
	@Override
	public String getUuid() {
		return _batchEngineExportTask.getUuid();
	}

	@Override
	public int hashCode() {
		return _batchEngineExportTask.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _batchEngineExportTask.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _batchEngineExportTask.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _batchEngineExportTask.isNew();
	}

	@Override
	public void persist() {
		_batchEngineExportTask.persist();
	}

	/**
	 * Sets the batch engine export task ID of this batch engine export task.
	 *
	 * @param batchEngineExportTaskId the batch engine export task ID of this batch engine export task
	 */
	@Override
	public void setBatchEngineExportTaskId(long batchEngineExportTaskId) {
		_batchEngineExportTask.setBatchEngineExportTaskId(
			batchEngineExportTaskId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_batchEngineExportTask.setCachedModel(cachedModel);
	}

	/**
	 * Sets the callback url of this batch engine export task.
	 *
	 * @param callbackURL the callback url of this batch engine export task
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		_batchEngineExportTask.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the class name of this batch engine export task.
	 *
	 * @param className the class name of this batch engine export task
	 */
	@Override
	public void setClassName(String className) {
		_batchEngineExportTask.setClassName(className);
	}

	/**
	 * Sets the company ID of this batch engine export task.
	 *
	 * @param companyId the company ID of this batch engine export task
	 */
	@Override
	public void setCompanyId(long companyId) {
		_batchEngineExportTask.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this batch engine export task.
	 *
	 * @param content the content of this batch engine export task
	 */
	@Override
	public void setContent(Blob content) {
		_batchEngineExportTask.setContent(content);
	}

	/**
	 * Sets the content type of this batch engine export task.
	 *
	 * @param contentType the content type of this batch engine export task
	 */
	@Override
	public void setContentType(String contentType) {
		_batchEngineExportTask.setContentType(contentType);
	}

	/**
	 * Sets the create date of this batch engine export task.
	 *
	 * @param createDate the create date of this batch engine export task
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_batchEngineExportTask.setCreateDate(createDate);
	}

	/**
	 * Sets the end time of this batch engine export task.
	 *
	 * @param endTime the end time of this batch engine export task
	 */
	@Override
	public void setEndTime(Date endTime) {
		_batchEngineExportTask.setEndTime(endTime);
	}

	/**
	 * Sets the error message of this batch engine export task.
	 *
	 * @param errorMessage the error message of this batch engine export task
	 */
	@Override
	public void setErrorMessage(String errorMessage) {
		_batchEngineExportTask.setErrorMessage(errorMessage);
	}

	/**
	 * Sets the execute status of this batch engine export task.
	 *
	 * @param executeStatus the execute status of this batch engine export task
	 */
	@Override
	public void setExecuteStatus(String executeStatus) {
		_batchEngineExportTask.setExecuteStatus(executeStatus);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_batchEngineExportTask.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_batchEngineExportTask.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_batchEngineExportTask.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the field names of this batch engine export task.
	 *
	 * @param fieldNames the field names of this batch engine export task
	 */
	@Override
	public void setFieldNames(String fieldNames) {
		_batchEngineExportTask.setFieldNames(fieldNames);
	}

	@Override
	public void setFieldNamesList(java.util.List<String> fieldNamesList) {
		_batchEngineExportTask.setFieldNamesList(fieldNamesList);
	}

	/**
	 * Sets the modified date of this batch engine export task.
	 *
	 * @param modifiedDate the modified date of this batch engine export task
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_batchEngineExportTask.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch engine export task.
	 *
	 * @param mvccVersion the mvcc version of this batch engine export task
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_batchEngineExportTask.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_batchEngineExportTask.setNew(n);
	}

	/**
	 * Sets the parameters of this batch engine export task.
	 *
	 * @param parameters the parameters of this batch engine export task
	 */
	@Override
	public void setParameters(Map<String, Serializable> parameters) {
		_batchEngineExportTask.setParameters(parameters);
	}

	/**
	 * Sets the primary key of this batch engine export task.
	 *
	 * @param primaryKey the primary key of this batch engine export task
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_batchEngineExportTask.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_batchEngineExportTask.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the start time of this batch engine export task.
	 *
	 * @param startTime the start time of this batch engine export task
	 */
	@Override
	public void setStartTime(Date startTime) {
		_batchEngineExportTask.setStartTime(startTime);
	}

	/**
	 * Sets the task item delegate name of this batch engine export task.
	 *
	 * @param taskItemDelegateName the task item delegate name of this batch engine export task
	 */
	@Override
	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_batchEngineExportTask.setTaskItemDelegateName(taskItemDelegateName);
	}

	/**
	 * Sets the user ID of this batch engine export task.
	 *
	 * @param userId the user ID of this batch engine export task
	 */
	@Override
	public void setUserId(long userId) {
		_batchEngineExportTask.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this batch engine export task.
	 *
	 * @param userUuid the user uuid of this batch engine export task
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_batchEngineExportTask.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this batch engine export task.
	 *
	 * @param uuid the uuid of this batch engine export task
	 */
	@Override
	public void setUuid(String uuid) {
		_batchEngineExportTask.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BatchEngineExportTask>
		toCacheModel() {

		return _batchEngineExportTask.toCacheModel();
	}

	@Override
	public BatchEngineExportTask toEscapedModel() {
		return new BatchEngineExportTaskWrapper(
			_batchEngineExportTask.toEscapedModel());
	}

	@Override
	public String toString() {
		return _batchEngineExportTask.toString();
	}

	@Override
	public BatchEngineExportTask toUnescapedModel() {
		return new BatchEngineExportTaskWrapper(
			_batchEngineExportTask.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _batchEngineExportTask.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineExportTaskWrapper)) {
			return false;
		}

		BatchEngineExportTaskWrapper batchEngineExportTaskWrapper =
			(BatchEngineExportTaskWrapper)object;

		if (Objects.equals(
				_batchEngineExportTask,
				batchEngineExportTaskWrapper._batchEngineExportTask)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _batchEngineExportTask.getStagedModelType();
	}

	@Override
	public BatchEngineExportTask getWrappedModel() {
		return _batchEngineExportTask;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _batchEngineExportTask.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _batchEngineExportTask.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_batchEngineExportTask.resetOriginalValues();
	}

	private final BatchEngineExportTask _batchEngineExportTask;

}