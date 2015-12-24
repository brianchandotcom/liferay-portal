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

package com.liferay.portal.background.task.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BackgroundTask}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTask
 * @generated
 */
@ProviderType
public class BackgroundTaskWrapper implements BackgroundTask,
	ModelWrapper<BackgroundTask> {
	public BackgroundTaskWrapper(BackgroundTask backgroundTask) {
		_backgroundTask = backgroundTask;
	}

	@Override
	public Class<?> getModelClass() {
		return BackgroundTask.class;
	}

	@Override
	public String getModelClassName() {
		return BackgroundTask.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("backgroundTaskId", getBackgroundTaskId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("servletContextNames", getServletContextNames());
		attributes.put("taskExecutorClassName", getTaskExecutorClassName());
		attributes.put("taskContextMap", getTaskContextMap());
		attributes.put("completed", getCompleted());
		attributes.put("completionDate", getCompletionDate());
		attributes.put("status", getStatus());
		attributes.put("statusMessage", getStatusMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long backgroundTaskId = (Long)attributes.get("backgroundTaskId");

		if (backgroundTaskId != null) {
			setBackgroundTaskId(backgroundTaskId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String servletContextNames = (String)attributes.get(
				"servletContextNames");

		if (servletContextNames != null) {
			setServletContextNames(servletContextNames);
		}

		String taskExecutorClassName = (String)attributes.get(
				"taskExecutorClassName");

		if (taskExecutorClassName != null) {
			setTaskExecutorClassName(taskExecutorClassName);
		}

		Map<String, Serializable> taskContextMap = (Map<String, Serializable>)attributes.get(
				"taskContextMap");

		if (taskContextMap != null) {
			setTaskContextMap(taskContextMap);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Date completionDate = (Date)attributes.get("completionDate");

		if (completionDate != null) {
			setCompletionDate(completionDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}
	}

	@Override
	public void addAttachment(long userId, java.lang.String fileName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTask.addAttachment(userId, fileName, file);
	}

	@Override
	public void addAttachment(long userId, java.lang.String fileName,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTask.addAttachment(userId, fileName, inputStream);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTask.addAttachmentsFolder();
	}

	@Override
	public java.lang.Object clone() {
		return new BackgroundTaskWrapper((BackgroundTask)_backgroundTask.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask) {
		return _backgroundTask.compareTo(backgroundTask);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTask.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTask.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTask.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId() {
		return _backgroundTask.getAttachmentsFolderId();
	}

	/**
	* Returns the background task ID of this background task.
	*
	* @return the background task ID of this background task
	*/
	@Override
	public long getBackgroundTaskId() {
		return _backgroundTask.getBackgroundTaskId();
	}

	/**
	* Returns the company ID of this background task.
	*
	* @return the company ID of this background task
	*/
	@Override
	public long getCompanyId() {
		return _backgroundTask.getCompanyId();
	}

	/**
	* Returns the completed of this background task.
	*
	* @return the completed of this background task
	*/
	@Override
	public boolean getCompleted() {
		return _backgroundTask.getCompleted();
	}

	/**
	* Returns the completion date of this background task.
	*
	* @return the completion date of this background task
	*/
	@Override
	public Date getCompletionDate() {
		return _backgroundTask.getCompletionDate();
	}

	/**
	* Returns the create date of this background task.
	*
	* @return the create date of this background task
	*/
	@Override
	public Date getCreateDate() {
		return _backgroundTask.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _backgroundTask.getExpandoBridge();
	}

	/**
	* Returns the group ID of this background task.
	*
	* @return the group ID of this background task
	*/
	@Override
	public long getGroupId() {
		return _backgroundTask.getGroupId();
	}

	/**
	* Returns the modified date of this background task.
	*
	* @return the modified date of this background task
	*/
	@Override
	public Date getModifiedDate() {
		return _backgroundTask.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this background task.
	*
	* @return the mvcc version of this background task
	*/
	@Override
	public long getMvccVersion() {
		return _backgroundTask.getMvccVersion();
	}

	/**
	* Returns the name of this background task.
	*
	* @return the name of this background task
	*/
	@Override
	public java.lang.String getName() {
		return _backgroundTask.getName();
	}

	/**
	* Returns the primary key of this background task.
	*
	* @return the primary key of this background task
	*/
	@Override
	public long getPrimaryKey() {
		return _backgroundTask.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _backgroundTask.getPrimaryKeyObj();
	}

	/**
	* Returns the servlet context names of this background task.
	*
	* @return the servlet context names of this background task
	*/
	@Override
	public java.lang.String getServletContextNames() {
		return _backgroundTask.getServletContextNames();
	}

	/**
	* Returns the status of this background task.
	*
	* @return the status of this background task
	*/
	@Override
	public int getStatus() {
		return _backgroundTask.getStatus();
	}

	@Override
	public java.lang.String getStatusLabel() {
		return _backgroundTask.getStatusLabel();
	}

	/**
	* Returns the status message of this background task.
	*
	* @return the status message of this background task
	*/
	@Override
	public java.lang.String getStatusMessage() {
		return _backgroundTask.getStatusMessage();
	}

	/**
	* Returns the task context map of this background task.
	*
	* @return the task context map of this background task
	*/
	@Override
	public Map<java.lang.String, Serializable> getTaskContextMap() {
		return _backgroundTask.getTaskContextMap();
	}

	/**
	* Returns the task executor class name of this background task.
	*
	* @return the task executor class name of this background task
	*/
	@Override
	public java.lang.String getTaskExecutorClassName() {
		return _backgroundTask.getTaskExecutorClassName();
	}

	/**
	* Returns the user ID of this background task.
	*
	* @return the user ID of this background task
	*/
	@Override
	public long getUserId() {
		return _backgroundTask.getUserId();
	}

	/**
	* Returns the user name of this background task.
	*
	* @return the user name of this background task
	*/
	@Override
	public java.lang.String getUserName() {
		return _backgroundTask.getUserName();
	}

	/**
	* Returns the user uuid of this background task.
	*
	* @return the user uuid of this background task
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _backgroundTask.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _backgroundTask.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _backgroundTask.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this background task is completed.
	*
	* @return <code>true</code> if this background task is completed; <code>false</code> otherwise
	*/
	@Override
	public boolean isCompleted() {
		return _backgroundTask.isCompleted();
	}

	@Override
	public boolean isEscapedModel() {
		return _backgroundTask.isEscapedModel();
	}

	@Override
	public boolean isInProgress() {
		return _backgroundTask.isInProgress();
	}

	@Override
	public boolean isNew() {
		return _backgroundTask.isNew();
	}

	@Override
	public void persist() {
		_backgroundTask.persist();
	}

	/**
	* Sets the background task ID of this background task.
	*
	* @param backgroundTaskId the background task ID of this background task
	*/
	@Override
	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTask.setBackgroundTaskId(backgroundTaskId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_backgroundTask.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this background task.
	*
	* @param companyId the company ID of this background task
	*/
	@Override
	public void setCompanyId(long companyId) {
		_backgroundTask.setCompanyId(companyId);
	}

	/**
	* Sets whether this background task is completed.
	*
	* @param completed the completed of this background task
	*/
	@Override
	public void setCompleted(boolean completed) {
		_backgroundTask.setCompleted(completed);
	}

	/**
	* Sets the completion date of this background task.
	*
	* @param completionDate the completion date of this background task
	*/
	@Override
	public void setCompletionDate(Date completionDate) {
		_backgroundTask.setCompletionDate(completionDate);
	}

	/**
	* Sets the create date of this background task.
	*
	* @param createDate the create date of this background task
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_backgroundTask.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_backgroundTask.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_backgroundTask.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_backgroundTask.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this background task.
	*
	* @param groupId the group ID of this background task
	*/
	@Override
	public void setGroupId(long groupId) {
		_backgroundTask.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this background task.
	*
	* @param modifiedDate the modified date of this background task
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_backgroundTask.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this background task.
	*
	* @param mvccVersion the mvcc version of this background task
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_backgroundTask.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this background task.
	*
	* @param name the name of this background task
	*/
	@Override
	public void setName(java.lang.String name) {
		_backgroundTask.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_backgroundTask.setNew(n);
	}

	/**
	* Sets the primary key of this background task.
	*
	* @param primaryKey the primary key of this background task
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_backgroundTask.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_backgroundTask.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the servlet context names of this background task.
	*
	* @param servletContextNames the servlet context names of this background task
	*/
	@Override
	public void setServletContextNames(java.lang.String servletContextNames) {
		_backgroundTask.setServletContextNames(servletContextNames);
	}

	/**
	* Sets the status of this background task.
	*
	* @param status the status of this background task
	*/
	@Override
	public void setStatus(int status) {
		_backgroundTask.setStatus(status);
	}

	/**
	* Sets the status message of this background task.
	*
	* @param statusMessage the status message of this background task
	*/
	@Override
	public void setStatusMessage(java.lang.String statusMessage) {
		_backgroundTask.setStatusMessage(statusMessage);
	}

	/**
	* Sets the task context map of this background task.
	*
	* @param taskContextMap the task context map of this background task
	*/
	@Override
	public void setTaskContextMap(
		Map<java.lang.String, Serializable> taskContextMap) {
		_backgroundTask.setTaskContextMap(taskContextMap);
	}

	/**
	* Sets the task executor class name of this background task.
	*
	* @param taskExecutorClassName the task executor class name of this background task
	*/
	@Override
	public void setTaskExecutorClassName(java.lang.String taskExecutorClassName) {
		_backgroundTask.setTaskExecutorClassName(taskExecutorClassName);
	}

	/**
	* Sets the user ID of this background task.
	*
	* @param userId the user ID of this background task
	*/
	@Override
	public void setUserId(long userId) {
		_backgroundTask.setUserId(userId);
	}

	/**
	* Sets the user name of this background task.
	*
	* @param userName the user name of this background task
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_backgroundTask.setUserName(userName);
	}

	/**
	* Sets the user uuid of this background task.
	*
	* @param userUuid the user uuid of this background task
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_backgroundTask.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.background.task.model.BackgroundTask> toCacheModel() {
		return _backgroundTask.toCacheModel();
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask toEscapedModel() {
		return new BackgroundTaskWrapper(_backgroundTask.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _backgroundTask.toString();
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask toUnescapedModel() {
		return new BackgroundTaskWrapper(_backgroundTask.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _backgroundTask.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BackgroundTaskWrapper)) {
			return false;
		}

		BackgroundTaskWrapper backgroundTaskWrapper = (BackgroundTaskWrapper)obj;

		if (Validator.equals(_backgroundTask,
					backgroundTaskWrapper._backgroundTask)) {
			return true;
		}

		return false;
	}

	@Override
	public BackgroundTask getWrappedModel() {
		return _backgroundTask;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _backgroundTask.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _backgroundTask.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_backgroundTask.resetOriginalValues();
	}

	private final BackgroundTask _backgroundTask;
}