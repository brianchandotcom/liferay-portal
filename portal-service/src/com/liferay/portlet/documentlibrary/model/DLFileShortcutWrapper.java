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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileShortcut}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcut
 * @generated
 */
public class DLFileShortcutWrapper implements DLFileShortcut,
	ModelWrapper<DLFileShortcut> {
	public DLFileShortcutWrapper(DLFileShortcut dlFileShortcut) {
		_dlFileShortcut = dlFileShortcut;
	}

	public Class<?> getModelClass() {
		return DLFileShortcut.class;
	}

	public String getModelClassName() {
		return DLFileShortcut.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileShortcutId", getFileShortcutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("toFileEntryId", getToFileEntryId());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("active", getActive());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileShortcutId = (Long)attributes.get("fileShortcutId");

		if (fileShortcutId != null) {
			setFileShortcutId(fileShortcutId);
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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		Long toFileEntryId = (Long)attributes.get("toFileEntryId");

		if (toFileEntryId != null) {
			setToFileEntryId(toFileEntryId);
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the primary key of this document library file shortcut.
	*
	* @return the primary key of this document library file shortcut
	*/
	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	/**
	* Sets the primary key of this document library file shortcut.
	*
	* @param primaryKey the primary key of this document library file shortcut
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlFileShortcut.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this document library file shortcut.
	*
	* @return the uuid of this document library file shortcut
	*/
	public java.lang.String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	/**
	* Sets the uuid of this document library file shortcut.
	*
	* @param uuid the uuid of this document library file shortcut
	*/
	public void setUuid(java.lang.String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	/**
	* Returns the file shortcut ID of this document library file shortcut.
	*
	* @return the file shortcut ID of this document library file shortcut
	*/
	public long getFileShortcutId() {
		return _dlFileShortcut.getFileShortcutId();
	}

	/**
	* Sets the file shortcut ID of this document library file shortcut.
	*
	* @param fileShortcutId the file shortcut ID of this document library file shortcut
	*/
	public void setFileShortcutId(long fileShortcutId) {
		_dlFileShortcut.setFileShortcutId(fileShortcutId);
	}

	/**
	* Returns the group ID of this document library file shortcut.
	*
	* @return the group ID of this document library file shortcut
	*/
	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	/**
	* Sets the group ID of this document library file shortcut.
	*
	* @param groupId the group ID of this document library file shortcut
	*/
	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this document library file shortcut.
	*
	* @return the company ID of this document library file shortcut
	*/
	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	/**
	* Sets the company ID of this document library file shortcut.
	*
	* @param companyId the company ID of this document library file shortcut
	*/
	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this document library file shortcut.
	*
	* @return the user ID of this document library file shortcut
	*/
	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	/**
	* Sets the user ID of this document library file shortcut.
	*
	* @param userId the user ID of this document library file shortcut
	*/
	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	/**
	* Returns the user uuid of this document library file shortcut.
	*
	* @return the user uuid of this document library file shortcut
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getUserUuid();
	}

	/**
	* Sets the user uuid of this document library file shortcut.
	*
	* @param userUuid the user uuid of this document library file shortcut
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this document library file shortcut.
	*
	* @return the user name of this document library file shortcut
	*/
	public java.lang.String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	/**
	* Sets the user name of this document library file shortcut.
	*
	* @param userName the user name of this document library file shortcut
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	/**
	* Returns the create date of this document library file shortcut.
	*
	* @return the create date of this document library file shortcut
	*/
	public java.util.Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	/**
	* Sets the create date of this document library file shortcut.
	*
	* @param createDate the create date of this document library file shortcut
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileShortcut.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this document library file shortcut.
	*
	* @return the modified date of this document library file shortcut
	*/
	public java.util.Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	/**
	* Sets the modified date of this document library file shortcut.
	*
	* @param modifiedDate the modified date of this document library file shortcut
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileShortcut.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the repository ID of this document library file shortcut.
	*
	* @return the repository ID of this document library file shortcut
	*/
	public long getRepositoryId() {
		return _dlFileShortcut.getRepositoryId();
	}

	/**
	* Sets the repository ID of this document library file shortcut.
	*
	* @param repositoryId the repository ID of this document library file shortcut
	*/
	public void setRepositoryId(long repositoryId) {
		_dlFileShortcut.setRepositoryId(repositoryId);
	}

	/**
	* Returns the folder ID of this document library file shortcut.
	*
	* @return the folder ID of this document library file shortcut
	*/
	public long getFolderId() {
		return _dlFileShortcut.getFolderId();
	}

	/**
	* Sets the folder ID of this document library file shortcut.
	*
	* @param folderId the folder ID of this document library file shortcut
	*/
	public void setFolderId(long folderId) {
		_dlFileShortcut.setFolderId(folderId);
	}

	/**
	* Returns the to file entry ID of this document library file shortcut.
	*
	* @return the to file entry ID of this document library file shortcut
	*/
	public long getToFileEntryId() {
		return _dlFileShortcut.getToFileEntryId();
	}

	/**
	* Sets the to file entry ID of this document library file shortcut.
	*
	* @param toFileEntryId the to file entry ID of this document library file shortcut
	*/
	public void setToFileEntryId(long toFileEntryId) {
		_dlFileShortcut.setToFileEntryId(toFileEntryId);
	}

	/**
	* Returns the status of this document library file shortcut.
	*
	* @return the status of this document library file shortcut
	*/
	public int getStatus() {
		return _dlFileShortcut.getStatus();
	}

	/**
	* Sets the status of this document library file shortcut.
	*
	* @param status the status of this document library file shortcut
	*/
	public void setStatus(int status) {
		_dlFileShortcut.setStatus(status);
	}

	/**
	* Returns the status by user ID of this document library file shortcut.
	*
	* @return the status by user ID of this document library file shortcut
	*/
	public long getStatusByUserId() {
		return _dlFileShortcut.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this document library file shortcut.
	*
	* @param statusByUserId the status by user ID of this document library file shortcut
	*/
	public void setStatusByUserId(long statusByUserId) {
		_dlFileShortcut.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this document library file shortcut.
	*
	* @return the status by user uuid of this document library file shortcut
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcut.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this document library file shortcut.
	*
	* @param statusByUserUuid the status by user uuid of this document library file shortcut
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileShortcut.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this document library file shortcut.
	*
	* @return the status by user name of this document library file shortcut
	*/
	public java.lang.String getStatusByUserName() {
		return _dlFileShortcut.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this document library file shortcut.
	*
	* @param statusByUserName the status by user name of this document library file shortcut
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileShortcut.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this document library file shortcut.
	*
	* @return the status date of this document library file shortcut
	*/
	public java.util.Date getStatusDate() {
		return _dlFileShortcut.getStatusDate();
	}

	/**
	* Sets the status date of this document library file shortcut.
	*
	* @param statusDate the status date of this document library file shortcut
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_dlFileShortcut.setStatusDate(statusDate);
	}

	/**
	* Returns the active of this document library file shortcut.
	*
	* @return the active of this document library file shortcut
	*/
	public boolean getActive() {
		return _dlFileShortcut.getActive();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is active.
	*
	* @return <code>true</code> if this document library file shortcut is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _dlFileShortcut.isActive();
	}

	/**
	* Sets whether this document library file shortcut is active.
	*
	* @param active the active of this document library file shortcut
	*/
	public void setActive(boolean active) {
		_dlFileShortcut.setActive(active);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _dlFileShortcut.getApproved();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is approved.
	*
	* @return <code>true</code> if this document library file shortcut is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _dlFileShortcut.isApproved();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is a draft.
	*
	* @return <code>true</code> if this document library file shortcut is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _dlFileShortcut.isDraft();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is expired.
	*
	* @return <code>true</code> if this document library file shortcut is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _dlFileShortcut.isExpired();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is pending.
	*
	* @return <code>true</code> if this document library file shortcut is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _dlFileShortcut.isPending();
	}

	public boolean isNew() {
		return _dlFileShortcut.isNew();
	}

	public void setNew(boolean n) {
		_dlFileShortcut.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileShortcut.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileShortcut.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileShortcut.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileShortcut.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileShortcut.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileShortcut.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileShortcutWrapper((DLFileShortcut)_dlFileShortcut.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcut.compareTo(dlFileShortcut);
	}

	@Override
	public int hashCode() {
		return _dlFileShortcut.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileShortcut> toCacheModel() {
		return _dlFileShortcut.toCacheModel();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut toEscapedModel() {
		return new DLFileShortcutWrapper(_dlFileShortcut.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileShortcut.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileShortcut.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcut.persist();
	}

	public com.liferay.portal.kernel.repository.model.Folder getFolder() {
		return _dlFileShortcut.getFolder();
	}

	public java.lang.String getToTitle() {
		return _dlFileShortcut.getToTitle();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DLFileShortcut getWrappedDLFileShortcut() {
		return _dlFileShortcut;
	}

	public DLFileShortcut getWrappedModel() {
		return _dlFileShortcut;
	}

	public void resetOriginalValues() {
		_dlFileShortcut.resetOriginalValues();
	}

	private DLFileShortcut _dlFileShortcut;
}