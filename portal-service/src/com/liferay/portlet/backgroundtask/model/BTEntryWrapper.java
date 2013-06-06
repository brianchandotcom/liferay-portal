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

package com.liferay.portlet.backgroundtask.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BTEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BTEntry
 * @generated
 */
public class BTEntryWrapper implements BTEntry, ModelWrapper<BTEntry> {
	public BTEntryWrapper(BTEntry btEntry) {
		_btEntry = btEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return BTEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BTEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("btEntryId", getBtEntryId());
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
		Long btEntryId = (Long)attributes.get("btEntryId");

		if (btEntryId != null) {
			setBtEntryId(btEntryId);
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
	* Returns the primary key of this b t entry.
	*
	* @return the primary key of this b t entry
	*/
	@Override
	public long getPrimaryKey() {
		return _btEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this b t entry.
	*
	* @param primaryKey the primary key of this b t entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_btEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the bt entry ID of this b t entry.
	*
	* @return the bt entry ID of this b t entry
	*/
	@Override
	public long getBtEntryId() {
		return _btEntry.getBtEntryId();
	}

	/**
	* Sets the bt entry ID of this b t entry.
	*
	* @param btEntryId the bt entry ID of this b t entry
	*/
	@Override
	public void setBtEntryId(long btEntryId) {
		_btEntry.setBtEntryId(btEntryId);
	}

	/**
	* Returns the group ID of this b t entry.
	*
	* @return the group ID of this b t entry
	*/
	@Override
	public long getGroupId() {
		return _btEntry.getGroupId();
	}

	/**
	* Sets the group ID of this b t entry.
	*
	* @param groupId the group ID of this b t entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_btEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this b t entry.
	*
	* @return the company ID of this b t entry
	*/
	@Override
	public long getCompanyId() {
		return _btEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this b t entry.
	*
	* @param companyId the company ID of this b t entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_btEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this b t entry.
	*
	* @return the user ID of this b t entry
	*/
	@Override
	public long getUserId() {
		return _btEntry.getUserId();
	}

	/**
	* Sets the user ID of this b t entry.
	*
	* @param userId the user ID of this b t entry
	*/
	@Override
	public void setUserId(long userId) {
		_btEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this b t entry.
	*
	* @return the user uuid of this b t entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this b t entry.
	*
	* @param userUuid the user uuid of this b t entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_btEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this b t entry.
	*
	* @return the user name of this b t entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _btEntry.getUserName();
	}

	/**
	* Sets the user name of this b t entry.
	*
	* @param userName the user name of this b t entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_btEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this b t entry.
	*
	* @return the create date of this b t entry
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _btEntry.getCreateDate();
	}

	/**
	* Sets the create date of this b t entry.
	*
	* @param createDate the create date of this b t entry
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_btEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this b t entry.
	*
	* @return the modified date of this b t entry
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _btEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this b t entry.
	*
	* @param modifiedDate the modified date of this b t entry
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_btEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this b t entry.
	*
	* @return the name of this b t entry
	*/
	@Override
	public java.lang.String getName() {
		return _btEntry.getName();
	}

	/**
	* Sets the name of this b t entry.
	*
	* @param name the name of this b t entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_btEntry.setName(name);
	}

	/**
	* Returns the servlet context names of this b t entry.
	*
	* @return the servlet context names of this b t entry
	*/
	@Override
	public java.lang.String getServletContextNames() {
		return _btEntry.getServletContextNames();
	}

	/**
	* Sets the servlet context names of this b t entry.
	*
	* @param servletContextNames the servlet context names of this b t entry
	*/
	@Override
	public void setServletContextNames(java.lang.String servletContextNames) {
		_btEntry.setServletContextNames(servletContextNames);
	}

	/**
	* Returns the task executor class name of this b t entry.
	*
	* @return the task executor class name of this b t entry
	*/
	@Override
	public java.lang.String getTaskExecutorClassName() {
		return _btEntry.getTaskExecutorClassName();
	}

	/**
	* Sets the task executor class name of this b t entry.
	*
	* @param taskExecutorClassName the task executor class name of this b t entry
	*/
	@Override
	public void setTaskExecutorClassName(java.lang.String taskExecutorClassName) {
		_btEntry.setTaskExecutorClassName(taskExecutorClassName);
	}

	/**
	* Returns the task context of this b t entry.
	*
	* @return the task context of this b t entry
	*/
	@Override
	public java.lang.String getTaskContext() {
		return _btEntry.getTaskContext();
	}

	/**
	* Sets the task context of this b t entry.
	*
	* @param taskContext the task context of this b t entry
	*/
	@Override
	public void setTaskContext(java.lang.String taskContext) {
		_btEntry.setTaskContext(taskContext);
	}

	/**
	* Returns the completed of this b t entry.
	*
	* @return the completed of this b t entry
	*/
	@Override
	public boolean getCompleted() {
		return _btEntry.getCompleted();
	}

	/**
	* Returns <code>true</code> if this b t entry is completed.
	*
	* @return <code>true</code> if this b t entry is completed; <code>false</code> otherwise
	*/
	@Override
	public boolean isCompleted() {
		return _btEntry.isCompleted();
	}

	/**
	* Sets whether this b t entry is completed.
	*
	* @param completed the completed of this b t entry
	*/
	@Override
	public void setCompleted(boolean completed) {
		_btEntry.setCompleted(completed);
	}

	/**
	* Returns the completion date of this b t entry.
	*
	* @return the completion date of this b t entry
	*/
	@Override
	public java.util.Date getCompletionDate() {
		return _btEntry.getCompletionDate();
	}

	/**
	* Sets the completion date of this b t entry.
	*
	* @param completionDate the completion date of this b t entry
	*/
	@Override
	public void setCompletionDate(java.util.Date completionDate) {
		_btEntry.setCompletionDate(completionDate);
	}

	/**
	* Returns the status of this b t entry.
	*
	* @return the status of this b t entry
	*/
	@Override
	public int getStatus() {
		return _btEntry.getStatus();
	}

	/**
	* Sets the status of this b t entry.
	*
	* @param status the status of this b t entry
	*/
	@Override
	public void setStatus(int status) {
		_btEntry.setStatus(status);
	}

	@Override
	public boolean isNew() {
		return _btEntry.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_btEntry.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _btEntry.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_btEntry.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _btEntry.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _btEntry.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_btEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _btEntry.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_btEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_btEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_btEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BTEntryWrapper((BTEntry)_btEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry) {
		return _btEntry.compareTo(btEntry);
	}

	@Override
	public int hashCode() {
		return _btEntry.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.backgroundtask.model.BTEntry> toCacheModel() {
		return _btEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.backgroundtask.model.BTEntry toEscapedModel() {
		return new BTEntryWrapper(_btEntry.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.backgroundtask.model.BTEntry toUnescapedModel() {
		return new BTEntryWrapper(_btEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _btEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _btEntry.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_btEntry.persist();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.addAttachmentsFolder();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntry.getAttachmentsFolderId();
	}

	@Override
	public java.util.Map<java.lang.String, java.io.Serializable> getTaskContextMap() {
		return _btEntry.getTaskContextMap();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BTEntry getWrappedBTEntry() {
		return _btEntry;
	}

	@Override
	public BTEntry getWrappedModel() {
		return _btEntry;
	}

	@Override
	public void resetOriginalValues() {
		_btEntry.resetOriginalValues();
	}

	private BTEntry _btEntry;
}