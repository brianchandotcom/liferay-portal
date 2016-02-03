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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileShortcut}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcut
 * @generated
 */
@ProviderType
public class DLFileShortcutWrapper implements DLFileShortcut,
	ModelWrapper<DLFileShortcut> {
	public DLFileShortcutWrapper(DLFileShortcut dlFileShortcut) {
		_dlFileShortcut = dlFileShortcut;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileShortcut.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileShortcut.class.getName();
	}

	@Override
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
		attributes.put("treePath", getTreePath());
		attributes.put("active", getActive());
		attributes.put("lastPublishDate", getLastPublishDate());
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

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
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

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcut.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileShortcutWrapper((DLFileShortcut)_dlFileShortcut.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		return _dlFileShortcut.compareTo(dlFileShortcut);
	}

	/**
	* Returns the active of this document library file shortcut.
	*
	* @return the active of this document library file shortcut
	*/
	@Override
	public boolean getActive() {
		return _dlFileShortcut.getActive();
	}

	/**
	* Returns the company ID of this document library file shortcut.
	*
	* @return the company ID of this document library file shortcut
	*/
	@Override
	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	/**
	* Returns the create date of this document library file shortcut.
	*
	* @return the create date of this document library file shortcut
	*/
	@Override
	public Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcut.getDLFolder();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	/**
	* Returns the file shortcut ID of this document library file shortcut.
	*
	* @return the file shortcut ID of this document library file shortcut
	*/
	@Override
	public long getFileShortcutId() {
		return _dlFileShortcut.getFileShortcutId();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcut.getFileVersion();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcut.getFolder();
	}

	/**
	* Returns the folder ID of this document library file shortcut.
	*
	* @return the folder ID of this document library file shortcut
	*/
	@Override
	public long getFolderId() {
		return _dlFileShortcut.getFolderId();
	}

	/**
	* Returns the group ID of this document library file shortcut.
	*
	* @return the group ID of this document library file shortcut
	*/
	@Override
	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	/**
	* Returns the last publish date of this document library file shortcut.
	*
	* @return the last publish date of this document library file shortcut
	*/
	@Override
	public Date getLastPublishDate() {
		return _dlFileShortcut.getLastPublishDate();
	}

	/**
	* Returns the modified date of this document library file shortcut.
	*
	* @return the modified date of this document library file shortcut
	*/
	@Override
	public Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	/**
	* Returns the primary key of this document library file shortcut.
	*
	* @return the primary key of this document library file shortcut
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlFileShortcut.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this document library file shortcut.
	*
	* @return the repository ID of this document library file shortcut
	*/
	@Override
	public long getRepositoryId() {
		return _dlFileShortcut.getRepositoryId();
	}

	/**
	* Returns the status of this document library file shortcut.
	*
	* @return the status of this document library file shortcut
	*/
	@Override
	public int getStatus() {
		return _dlFileShortcut.getStatus();
	}

	/**
	* Returns the status by user ID of this document library file shortcut.
	*
	* @return the status by user ID of this document library file shortcut
	*/
	@Override
	public long getStatusByUserId() {
		return _dlFileShortcut.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this document library file shortcut.
	*
	* @return the status by user name of this document library file shortcut
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _dlFileShortcut.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this document library file shortcut.
	*
	* @return the status by user uuid of this document library file shortcut
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _dlFileShortcut.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this document library file shortcut.
	*
	* @return the status date of this document library file shortcut
	*/
	@Override
	public Date getStatusDate() {
		return _dlFileShortcut.getStatusDate();
	}

	/**
	* Returns the to file entry ID of this document library file shortcut.
	*
	* @return the to file entry ID of this document library file shortcut
	*/
	@Override
	public long getToFileEntryId() {
		return _dlFileShortcut.getToFileEntryId();
	}

	@Override
	public java.lang.String getToTitle() {
		return _dlFileShortcut.getToTitle();
	}

	/**
	* Returns the trash entry created when this document library file shortcut was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this document library file shortcut.
	*
	* @return the trash entry created when this document library file shortcut was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileShortcut.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this document library file shortcut.
	*
	* @return the class primary key of the trash entry for this document library file shortcut
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _dlFileShortcut.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this document library file shortcut.
	*
	* @return the trash handler for this document library file shortcut
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _dlFileShortcut.getTrashHandler();
	}

	/**
	* Returns the tree path of this document library file shortcut.
	*
	* @return the tree path of this document library file shortcut
	*/
	@Override
	public java.lang.String getTreePath() {
		return _dlFileShortcut.getTreePath();
	}

	/**
	* Returns the user ID of this document library file shortcut.
	*
	* @return the user ID of this document library file shortcut
	*/
	@Override
	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	/**
	* Returns the user name of this document library file shortcut.
	*
	* @return the user name of this document library file shortcut
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	/**
	* Returns the user uuid of this document library file shortcut.
	*
	* @return the user uuid of this document library file shortcut
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _dlFileShortcut.getUserUuid();
	}

	/**
	* Returns the uuid of this document library file shortcut.
	*
	* @return the uuid of this document library file shortcut
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	@Override
	public int hashCode() {
		return _dlFileShortcut.hashCode();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is active.
	*
	* @return <code>true</code> if this document library file shortcut is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _dlFileShortcut.isActive();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is approved.
	*
	* @return <code>true</code> if this document library file shortcut is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _dlFileShortcut.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileShortcut.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is denied.
	*
	* @return <code>true</code> if this document library file shortcut is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _dlFileShortcut.isDenied();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is a draft.
	*
	* @return <code>true</code> if this document library file shortcut is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _dlFileShortcut.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileShortcut.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is expired.
	*
	* @return <code>true</code> if this document library file shortcut is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _dlFileShortcut.isExpired();
	}

	@Override
	public boolean isInHiddenFolder() {
		return _dlFileShortcut.isInHiddenFolder();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is in the Recycle Bin.
	*
	* @return <code>true</code> if this document library file shortcut is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _dlFileShortcut.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this document library file shortcut is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this document library file shortcut is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _dlFileShortcut.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _dlFileShortcut.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _dlFileShortcut.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is inactive.
	*
	* @return <code>true</code> if this document library file shortcut is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _dlFileShortcut.isInactive();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is incomplete.
	*
	* @return <code>true</code> if this document library file shortcut is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _dlFileShortcut.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _dlFileShortcut.isNew();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is pending.
	*
	* @return <code>true</code> if this document library file shortcut is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _dlFileShortcut.isPending();
	}

	/**
	* Returns <code>true</code> if this document library file shortcut is scheduled.
	*
	* @return <code>true</code> if this document library file shortcut is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _dlFileShortcut.isScheduled();
	}

	@Override
	public void persist() {
		_dlFileShortcut.persist();
	}

	/**
	* Sets whether this document library file shortcut is active.
	*
	* @param active the active of this document library file shortcut
	*/
	@Override
	public void setActive(boolean active) {
		_dlFileShortcut.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileShortcut.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this document library file shortcut.
	*
	* @param companyId the company ID of this document library file shortcut
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this document library file shortcut.
	*
	* @param createDate the create date of this document library file shortcut
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlFileShortcut.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFileShortcut.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlFileShortcut.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlFileShortcut.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file shortcut ID of this document library file shortcut.
	*
	* @param fileShortcutId the file shortcut ID of this document library file shortcut
	*/
	@Override
	public void setFileShortcutId(long fileShortcutId) {
		_dlFileShortcut.setFileShortcutId(fileShortcutId);
	}

	/**
	* Sets the folder ID of this document library file shortcut.
	*
	* @param folderId the folder ID of this document library file shortcut
	*/
	@Override
	public void setFolderId(long folderId) {
		_dlFileShortcut.setFolderId(folderId);
	}

	/**
	* Sets the group ID of this document library file shortcut.
	*
	* @param groupId the group ID of this document library file shortcut
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this document library file shortcut.
	*
	* @param lastPublishDate the last publish date of this document library file shortcut
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFileShortcut.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this document library file shortcut.
	*
	* @param modifiedDate the modified date of this document library file shortcut
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFileShortcut.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileShortcut.setNew(n);
	}

	/**
	* Sets the primary key of this document library file shortcut.
	*
	* @param primaryKey the primary key of this document library file shortcut
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileShortcut.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlFileShortcut.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this document library file shortcut.
	*
	* @param repositoryId the repository ID of this document library file shortcut
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlFileShortcut.setRepositoryId(repositoryId);
	}

	/**
	* Sets the status of this document library file shortcut.
	*
	* @param status the status of this document library file shortcut
	*/
	@Override
	public void setStatus(int status) {
		_dlFileShortcut.setStatus(status);
	}

	/**
	* Sets the status by user ID of this document library file shortcut.
	*
	* @param statusByUserId the status by user ID of this document library file shortcut
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_dlFileShortcut.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this document library file shortcut.
	*
	* @param statusByUserName the status by user name of this document library file shortcut
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileShortcut.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this document library file shortcut.
	*
	* @param statusByUserUuid the status by user uuid of this document library file shortcut
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileShortcut.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this document library file shortcut.
	*
	* @param statusDate the status date of this document library file shortcut
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_dlFileShortcut.setStatusDate(statusDate);
	}

	/**
	* Sets the to file entry ID of this document library file shortcut.
	*
	* @param toFileEntryId the to file entry ID of this document library file shortcut
	*/
	@Override
	public void setToFileEntryId(long toFileEntryId) {
		_dlFileShortcut.setToFileEntryId(toFileEntryId);
	}

	/**
	* Sets the tree path of this document library file shortcut.
	*
	* @param treePath the tree path of this document library file shortcut
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_dlFileShortcut.setTreePath(treePath);
	}

	/**
	* Sets the user ID of this document library file shortcut.
	*
	* @param userId the user ID of this document library file shortcut
	*/
	@Override
	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	/**
	* Sets the user name of this document library file shortcut.
	*
	* @param userName the user name of this document library file shortcut
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	/**
	* Sets the user uuid of this document library file shortcut.
	*
	* @param userUuid the user uuid of this document library file shortcut
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this document library file shortcut.
	*
	* @param uuid the uuid of this document library file shortcut
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileShortcut> toCacheModel() {
		return _dlFileShortcut.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut toEscapedModel() {
		return new DLFileShortcutWrapper(_dlFileShortcut.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileShortcut.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut toUnescapedModel() {
		return new DLFileShortcutWrapper(_dlFileShortcut.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileShortcut.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_dlFileShortcut.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileShortcutWrapper)) {
			return false;
		}

		DLFileShortcutWrapper dlFileShortcutWrapper = (DLFileShortcutWrapper)obj;

		if (Validator.equals(_dlFileShortcut,
					dlFileShortcutWrapper._dlFileShortcut)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFileShortcut.getStagedModelType();
	}

	@Override
	public DLFileShortcut getWrappedModel() {
		return _dlFileShortcut;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileShortcut.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileShortcut.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileShortcut.resetOriginalValues();
	}

	private final DLFileShortcut _dlFileShortcut;
}