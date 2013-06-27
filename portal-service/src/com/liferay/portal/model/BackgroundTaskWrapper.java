/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Validator;

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
		attributes.put("taskContext", getTaskContext());
		attributes.put("completed", getCompleted());
		attributes.put("completionDate", getCompletionDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		String taskContext = (String)attributes.get("taskContext");

		if (taskContext != null) {
			setTaskContext(taskContext);
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

	/**
	* Sets the primary key of this background task.
	*
	* @param primaryKey the primary key of this background task
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_backgroundTask.setPrimaryKey(primaryKey);
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
	* Sets the background task ID of this background task.
	*
	* @param backgroundTaskId the background task ID of this background task
	*/
	@Override
	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTask.setBackgroundTaskId(backgroundTaskId);
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
	* Sets the group ID of this background task.
	*
	* @param groupId the group ID of this background task
	*/
	@Override
	public void setGroupId(long groupId) {
		_backgroundTask.setGroupId(groupId);
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
	* Sets the company ID of this background task.
	*
	* @param companyId the company ID of this background task
	*/
	@Override
	public void setCompanyId(long companyId) {
		_backgroundTask.setCompanyId(companyId);
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
	* Sets the user ID of this background task.
	*
	* @param userId the user ID of this background task
	*/
	@Override
	public void setUserId(long userId) {
		_backgroundTask.setUserId(userId);
	}

	/**
	* Returns the user uuid of this background task.
	*
	* @return the user uuid of this background task
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.getUserUuid();
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
	* Sets the user name of this background task.
	*
	* @param userName the user name of this background task
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_backgroundTask.setUserName(userName);
	}

	/**
	* Returns the create date of this background task.
	*
	* @return the create date of this background task
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _backgroundTask.getCreateDate();
	}

	/**
	* Sets the create date of this background task.
	*
	* @param createDate the create date of this background task
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_backgroundTask.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this background task.
	*
	* @return the modified date of this background task
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _backgroundTask.getModifiedDate();
	}

	/**
	* Sets the modified date of this background task.
	*
	* @param modifiedDate the modified date of this background task
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_backgroundTask.setModifiedDate(modifiedDate);
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
	* Sets the name of this background task.
	*
	* @param name the name of this background task
	*/
	@Override
	public void setName(java.lang.String name) {
		_backgroundTask.setName(name);
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
	* Sets the servlet context names of this background task.
	*
	* @param servletContextNames the servlet context names of this background task
	*/
	@Override
	public void setServletContextNames(java.lang.String servletContextNames) {
		_backgroundTask.setServletContextNames(servletContextNames);
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
	* Sets the task executor class name of this background task.
	*
	* @param taskExecutorClassName the task executor class name of this background task
	*/
	@Override
	public void setTaskExecutorClassName(java.lang.String taskExecutorClassName) {
		_backgroundTask.setTaskExecutorClassName(taskExecutorClassName);
	}

	/**
	* Returns the task context of this background task.
	*
	* @return the task context of this background task
	*/
	@Override
	public java.lang.String getTaskContext() {
		return _backgroundTask.getTaskContext();
	}

	/**
	* Sets the task context of this background task.
	*
	* @param taskContext the task context of this background task
	*/
	@Override
	public void setTaskContext(java.lang.String taskContext) {
		_backgroundTask.setTaskContext(taskContext);
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
	* Returns <code>true</code> if this background task is completed.
	*
	* @return <code>true</code> if this background task is completed; <code>false</code> otherwise
	*/
	@Override
	public boolean isCompleted() {
		return _backgroundTask.isCompleted();
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
	* Returns the completion date of this background task.
	*
	* @return the completion date of this background task
	*/
	@Override
	public java.util.Date getCompletionDate() {
		return _backgroundTask.getCompletionDate();
	}

	/**
	* Sets the completion date of this background task.
	*
	* @param completionDate the completion date of this background task
	*/
	@Override
	public void setCompletionDate(java.util.Date completionDate) {
		_backgroundTask.setCompletionDate(completionDate);
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

	/**
	* Sets the status of this background task.
	*
	* @param status the status of this background task
	*/
	@Override
	public void setStatus(int status) {
		_backgroundTask.setStatus(status);
	}

	@Override
	public boolean isNew() {
		return _backgroundTask.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_backgroundTask.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _backgroundTask.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_backgroundTask.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _backgroundTask.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _backgroundTask.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_backgroundTask.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _backgroundTask.getExpandoBridge();
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

	@Override
	public java.lang.Object clone() {
		return new BackgroundTaskWrapper((BackgroundTask)_backgroundTask.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.BackgroundTask backgroundTask) {
		return _backgroundTask.compareTo(backgroundTask);
	}

	@Override
	public int hashCode() {
		return _backgroundTask.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.BackgroundTask> toCacheModel() {
		return _backgroundTask.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.BackgroundTask toEscapedModel() {
		return new BackgroundTaskWrapper(_backgroundTask.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.BackgroundTask toUnescapedModel() {
		return new BackgroundTaskWrapper(_backgroundTask.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _backgroundTask.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _backgroundTask.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_backgroundTask.persist();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.addAttachmentsFolder();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _backgroundTask.getAttachmentsFolderId();
	}

	@Override
	public java.util.Map<java.lang.String, java.io.Serializable> getTaskContextMap() {
		return _backgroundTask.getTaskContextMap();
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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BackgroundTask getWrappedBackgroundTask() {
		return _backgroundTask;
	}

	@Override
	public BackgroundTask getWrappedModel() {
		return _backgroundTask;
	}

	@Override
	public void resetOriginalValues() {
		_backgroundTask.resetOriginalValues();
	}

	private BackgroundTask _backgroundTask;
}