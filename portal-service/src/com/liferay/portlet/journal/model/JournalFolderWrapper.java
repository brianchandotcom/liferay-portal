/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFolder
 * @generated
 */
public class JournalFolderWrapper implements JournalFolder,
	ModelWrapper<JournalFolder> {
	public JournalFolderWrapper(JournalFolder journalFolder) {
		_journalFolder = journalFolder;
	}

	public Class<?> getModelClass() {
		return JournalFolder.class;
	}

	public String getModelClassName() {
		return JournalFolder.class.getName();
	}

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

		return attributes;
	}

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
	}

	/**
	* Returns the primary key of this journal folder.
	*
	* @return the primary key of this journal folder
	*/
	public long getPrimaryKey() {
		return _journalFolder.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal folder.
	*
	* @param primaryKey the primary key of this journal folder
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalFolder.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal folder.
	*
	* @return the uuid of this journal folder
	*/
	public java.lang.String getUuid() {
		return _journalFolder.getUuid();
	}

	/**
	* Sets the uuid of this journal folder.
	*
	* @param uuid the uuid of this journal folder
	*/
	public void setUuid(java.lang.String uuid) {
		_journalFolder.setUuid(uuid);
	}

	/**
	* Returns the folder ID of this journal folder.
	*
	* @return the folder ID of this journal folder
	*/
	public long getFolderId() {
		return _journalFolder.getFolderId();
	}

	/**
	* Sets the folder ID of this journal folder.
	*
	* @param folderId the folder ID of this journal folder
	*/
	public void setFolderId(long folderId) {
		_journalFolder.setFolderId(folderId);
	}

	/**
	* Returns the group ID of this journal folder.
	*
	* @return the group ID of this journal folder
	*/
	public long getGroupId() {
		return _journalFolder.getGroupId();
	}

	/**
	* Sets the group ID of this journal folder.
	*
	* @param groupId the group ID of this journal folder
	*/
	public void setGroupId(long groupId) {
		_journalFolder.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal folder.
	*
	* @return the company ID of this journal folder
	*/
	public long getCompanyId() {
		return _journalFolder.getCompanyId();
	}

	/**
	* Sets the company ID of this journal folder.
	*
	* @param companyId the company ID of this journal folder
	*/
	public void setCompanyId(long companyId) {
		_journalFolder.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal folder.
	*
	* @return the user ID of this journal folder
	*/
	public long getUserId() {
		return _journalFolder.getUserId();
	}

	/**
	* Sets the user ID of this journal folder.
	*
	* @param userId the user ID of this journal folder
	*/
	public void setUserId(long userId) {
		_journalFolder.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal folder.
	*
	* @return the user uuid of this journal folder
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal folder.
	*
	* @param userUuid the user uuid of this journal folder
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalFolder.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal folder.
	*
	* @return the user name of this journal folder
	*/
	public java.lang.String getUserName() {
		return _journalFolder.getUserName();
	}

	/**
	* Sets the user name of this journal folder.
	*
	* @param userName the user name of this journal folder
	*/
	public void setUserName(java.lang.String userName) {
		_journalFolder.setUserName(userName);
	}

	/**
	* Returns the create date of this journal folder.
	*
	* @return the create date of this journal folder
	*/
	public java.util.Date getCreateDate() {
		return _journalFolder.getCreateDate();
	}

	/**
	* Sets the create date of this journal folder.
	*
	* @param createDate the create date of this journal folder
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalFolder.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal folder.
	*
	* @return the modified date of this journal folder
	*/
	public java.util.Date getModifiedDate() {
		return _journalFolder.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal folder.
	*
	* @param modifiedDate the modified date of this journal folder
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent folder ID of this journal folder.
	*
	* @return the parent folder ID of this journal folder
	*/
	public long getParentFolderId() {
		return _journalFolder.getParentFolderId();
	}

	/**
	* Sets the parent folder ID of this journal folder.
	*
	* @param parentFolderId the parent folder ID of this journal folder
	*/
	public void setParentFolderId(long parentFolderId) {
		_journalFolder.setParentFolderId(parentFolderId);
	}

	/**
	* Returns the name of this journal folder.
	*
	* @return the name of this journal folder
	*/
	public java.lang.String getName() {
		return _journalFolder.getName();
	}

	/**
	* Sets the name of this journal folder.
	*
	* @param name the name of this journal folder
	*/
	public void setName(java.lang.String name) {
		_journalFolder.setName(name);
	}

	/**
	* Returns the description of this journal folder.
	*
	* @return the description of this journal folder
	*/
	public java.lang.String getDescription() {
		return _journalFolder.getDescription();
	}

	/**
	* Sets the description of this journal folder.
	*
	* @param description the description of this journal folder
	*/
	public void setDescription(java.lang.String description) {
		_journalFolder.setDescription(description);
	}

	public boolean isNew() {
		return _journalFolder.isNew();
	}

	public void setNew(boolean n) {
		_journalFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalFolder.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalFolder.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalFolder.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalFolderWrapper((JournalFolder)_journalFolder.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalFolder journalFolder) {
		return _journalFolder.compareTo(journalFolder);
	}

	@Override
	public int hashCode() {
		return _journalFolder.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalFolder> toCacheModel() {
		return _journalFolder.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalFolder toEscapedModel() {
		return new JournalFolderWrapper(_journalFolder.toEscapedModel());
	}

	public com.liferay.portlet.journal.model.JournalFolder toUnescapedModel() {
		return new JournalFolderWrapper(_journalFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _journalFolder.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFolder.persist();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getAncestors();
	}

	public com.liferay.portlet.journal.model.JournalFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolder.getParentFolder();
	}

	public boolean isRoot() {
		return _journalFolder.isRoot();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public JournalFolder getWrappedJournalFolder() {
		return _journalFolder;
	}

	public JournalFolder getWrappedModel() {
		return _journalFolder;
	}

	public void resetOriginalValues() {
		_journalFolder.resetOriginalValues();
	}

	private JournalFolder _journalFolder;
}