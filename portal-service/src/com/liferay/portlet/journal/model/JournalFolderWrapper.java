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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolder
 * @generated
 */
public class JournalFolderWrapper implements JournalFolder,
	ModelWrapper<JournalFolder> {
	public JournalFolderWrapper(JournalFolder journalFolder) {
		_journalFolder = journalFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalFolder.class;
	}

	@Override
	public String getModelClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("folderId", getFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
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

		Long parentFolderId = (Long)attributes.get("parentFolderId");

		if (parentFolderId != null) {
			setParentFolderId(parentFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	* Returns the primary key of this journal folder.
	*
	* @return the primary key of this journal folder
	*/
	@Override
	public long getPrimaryKey() {
		return _journalFolder.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal folder.
	*
	* @param primaryKey the primary key of this journal folder
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalFolder.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal folder.
	*
	* @return the uuid of this journal folder
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalFolder.getUuid();
	}

	/**
	* Sets the uuid of this journal folder.
	*
	* @param uuid the uuid of this journal folder
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalFolder.setUuid(uuid);
	}

	/**
	* Returns the folder ID of this journal folder.
	*
	* @return the folder ID of this journal folder
	*/
	@Override
	public long getFolderId() {
		return _journalFolder.getFolderId();
	}

	/**
	* Sets the folder ID of this journal folder.
	*
	* @param folderId the folder ID of this journal folder
	*/
	@Override
	public void setFolderId(long folderId) {
		_journalFolder.setFolderId(folderId);
	}

	/**
	* Returns the group ID of this journal folder.
	*
	* @return the group ID of this journal folder
	*/
	@Override
	public long getGroupId() {
		return _journalFolder.getGroupId();
	}

	/**
	* Sets the group ID of this journal folder.
	*
	* @param groupId the group ID of this journal folder
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalFolder.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal folder.
	*
	* @return the company ID of this journal folder
	*/
	@Override
	public long getCompanyId() {
		return _journalFolder.getCompanyId();
	}

	/**
	* Sets the company ID of this journal folder.
	*
	* @param companyId the company ID of this journal folder
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalFolder.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal folder.
	*
	* @return the user ID of this journal folder
	*/
	@Override
	public long getUserId() {
		return _journalFolder.getUserId();
	}

	/**
	* Sets the user ID of this journal folder.
	*
	* @param userId the user ID of this journal folder
	*/
	@Override
	public void setUserId(long userId) {
		_journalFolder.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal folder.
	*
	* @return the user uuid of this journal folder
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal folder.
	*
	* @param userUuid the user uuid of this journal folder
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_journalFolder.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal folder.
	*
	* @return the user name of this journal folder
	*/
	@Override
	public java.lang.String getUserName() {
		return _journalFolder.getUserName();
	}

	/**
	* Sets the user name of this journal folder.
	*
	* @param userName the user name of this journal folder
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_journalFolder.setUserName(userName);
	}

	/**
	* Returns the create date of this journal folder.
	*
	* @return the create date of this journal folder
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _journalFolder.getCreateDate();
	}

	/**
	* Sets the create date of this journal folder.
	*
	* @param createDate the create date of this journal folder
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_journalFolder.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal folder.
	*
	* @return the modified date of this journal folder
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _journalFolder.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal folder.
	*
	* @param modifiedDate the modified date of this journal folder
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent folder ID of this journal folder.
	*
	* @return the parent folder ID of this journal folder
	*/
	@Override
	public long getParentFolderId() {
		return _journalFolder.getParentFolderId();
	}

	/**
	* Sets the parent folder ID of this journal folder.
	*
	* @param parentFolderId the parent folder ID of this journal folder
	*/
	@Override
	public void setParentFolderId(long parentFolderId) {
		_journalFolder.setParentFolderId(parentFolderId);
	}

	/**
	* Returns the name of this journal folder.
	*
	* @return the name of this journal folder
	*/
	@Override
	public java.lang.String getName() {
		return _journalFolder.getName();
	}

	/**
	* Sets the name of this journal folder.
	*
	* @param name the name of this journal folder
	*/
	@Override
	public void setName(java.lang.String name) {
		_journalFolder.setName(name);
	}

	/**
	* Returns the description of this journal folder.
	*
	* @return the description of this journal folder
	*/
	@Override
	public java.lang.String getDescription() {
		return _journalFolder.getDescription();
	}

	/**
	* Sets the description of this journal folder.
	*
	* @param description the description of this journal folder
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_journalFolder.setDescription(description);
	}

	/**
	* Returns the status of this journal folder.
	*
	* @return the status of this journal folder
	*/
	@Override
	public int getStatus() {
		return _journalFolder.getStatus();
	}

	/**
	* Sets the status of this journal folder.
	*
	* @param status the status of this journal folder
	*/
	@Override
	public void setStatus(int status) {
		_journalFolder.setStatus(status);
	}

	/**
	* Returns the status by user ID of this journal folder.
	*
	* @return the status by user ID of this journal folder
	*/
	@Override
	public long getStatusByUserId() {
		return _journalFolder.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this journal folder.
	*
	* @param statusByUserId the status by user ID of this journal folder
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_journalFolder.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this journal folder.
	*
	* @return the status by user uuid of this journal folder
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this journal folder.
	*
	* @param statusByUserUuid the status by user uuid of this journal folder
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_journalFolder.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this journal folder.
	*
	* @return the status by user name of this journal folder
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _journalFolder.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this journal folder.
	*
	* @param statusByUserName the status by user name of this journal folder
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_journalFolder.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this journal folder.
	*
	* @return the status date of this journal folder
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _journalFolder.getStatusDate();
	}

	/**
	* Sets the status date of this journal folder.
	*
	* @param statusDate the status date of this journal folder
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_journalFolder.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _journalFolder.getApproved();
	}

	/**
	* Returns <code>true</code> if this journal folder is approved.
	*
	* @return <code>true</code> if this journal folder is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _journalFolder.isApproved();
	}

	/**
	* Returns <code>true</code> if this journal folder is denied.
	*
	* @return <code>true</code> if this journal folder is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _journalFolder.isDenied();
	}

	/**
	* Returns <code>true</code> if this journal folder is a draft.
	*
	* @return <code>true</code> if this journal folder is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _journalFolder.isDraft();
	}

	/**
	* Returns <code>true</code> if this journal folder is expired.
	*
	* @return <code>true</code> if this journal folder is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _journalFolder.isExpired();
	}

	/**
	* Returns <code>true</code> if this journal folder is inactive.
	*
	* @return <code>true</code> if this journal folder is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _journalFolder.isInactive();
	}

	/**
	* Returns <code>true</code> if this journal folder is incomplete.
	*
	* @return <code>true</code> if this journal folder is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _journalFolder.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this journal folder is in the Recycle Bin.
	*
	* @return <code>true</code> if this journal folder is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _journalFolder.isInTrash();
	}

	/**
	* Returns <code>true</code> if this journal folder is pending.
	*
	* @return <code>true</code> if this journal folder is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _journalFolder.isPending();
	}

	/**
	* Returns <code>true</code> if this journal folder is scheduled.
	*
	* @return <code>true</code> if this journal folder is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _journalFolder.isScheduled();
	}

	/**
	* Returns the container model ID of this journal folder.
	*
	* @return the container model ID of this journal folder
	*/
	@Override
	public long getContainerModelId() {
		return _journalFolder.getContainerModelId();
	}

	/**
	* Sets the container model ID of this journal folder.
	*
	* @param container model ID of this journal folder
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_journalFolder.setContainerModelId(containerModelId);
	}

	/**
	* Returns the container name of this journal folder.
	*
	* @return the container name of this journal folder
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _journalFolder.getContainerModelName();
	}

	/**
	* Returns the parent container model ID of this journal folder.
	*
	* @return the parent container model ID of this journal folder
	*/
	@Override
	public long getParentContainerModelId() {
		return _journalFolder.getParentContainerModelId();
	}

	/**
	* Sets the parent container model ID of this journal folder.
	*
	* @param parent container model ID of this journal folder
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_journalFolder.setParentContainerModelId(parentContainerModelId);
	}

	@Override
	public boolean isNew() {
		return _journalFolder.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_journalFolder.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _journalFolder.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalFolder.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _journalFolder.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalFolder.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalFolder.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalFolder.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalFolderWrapper((JournalFolder)_journalFolder.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.journal.model.JournalFolder journalFolder) {
		return _journalFolder.compareTo(journalFolder);
	}

	@Override
	public int hashCode() {
		return _journalFolder.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalFolder> toCacheModel() {
		return _journalFolder.toCacheModel();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder toEscapedModel() {
		return new JournalFolderWrapper(_journalFolder.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder toUnescapedModel() {
		return new JournalFolderWrapper(_journalFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalFolder.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalFolder.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFolder.persist();
	}

	@Override
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getAncestors();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getParentFolder();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFolder getTrashContainer() {
		return _journalFolder.getTrashContainer();
	}

	@Override
	public boolean isInTrashContainer() {
		return _journalFolder.isInTrashContainer();
	}

	@Override
	public boolean isRoot() {
		return _journalFolder.isRoot();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalFolderWrapper)) {
			return false;
		}

		JournalFolderWrapper journalFolderWrapper = (JournalFolderWrapper)obj;

		if (Validator.equals(_journalFolder, journalFolderWrapper._journalFolder)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public JournalFolder getWrappedJournalFolder() {
		return _journalFolder;
	}

	@Override
	public JournalFolder getWrappedModel() {
		return _journalFolder;
	}

	@Override
	public void resetOriginalValues() {
		_journalFolder.resetOriginalValues();
	}

	private JournalFolder _journalFolder;
}