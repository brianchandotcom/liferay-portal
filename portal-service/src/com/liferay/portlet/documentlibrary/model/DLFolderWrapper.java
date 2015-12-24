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

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolder
 * @generated
 */
@ProviderType
public class DLFolderWrapper implements DLFolder, ModelWrapper<DLFolder> {
	public DLFolderWrapper(DLFolder dlFolder) {
		_dlFolder = dlFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFolder.class;
	}

	@Override
	public String getModelClassName() {
		return DLFolder.class.getName();
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
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("mountPoint", getMountPoint());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("defaultFileEntryTypeId", getDefaultFileEntryTypeId());
		attributes.put("hidden", getHidden());
		attributes.put("restrictionType", getRestrictionType());
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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Boolean mountPoint = (Boolean)attributes.get("mountPoint");

		if (mountPoint != null) {
			setMountPoint(mountPoint);
		}

		Long parentFolderId = (Long)attributes.get("parentFolderId");

		if (parentFolderId != null) {
			setParentFolderId(parentFolderId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}

		Long defaultFileEntryTypeId = (Long)attributes.get(
				"defaultFileEntryTypeId");

		if (defaultFileEntryTypeId != null) {
			setDefaultFileEntryTypeId(defaultFileEntryTypeId);
		}

		Boolean hidden = (Boolean)attributes.get("hidden");

		if (hidden != null) {
			setHidden(hidden);
		}

		Integer restrictionType = (Integer)attributes.get("restrictionType");

		if (restrictionType != null) {
			setRestrictionType(restrictionType);
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
		return _dlFolder.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new DLFolderWrapper((DLFolder)_dlFolder.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder) {
		return _dlFolder.compareTo(dlFolder);
	}

	@Override
	public java.util.List<java.lang.Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getAncestorFolderIds();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getAncestors();
	}

	/**
	* Returns the company ID of this document library folder.
	*
	* @return the company ID of this document library folder
	*/
	@Override
	public long getCompanyId() {
		return _dlFolder.getCompanyId();
	}

	/**
	* Returns the container model ID of this document library folder.
	*
	* @return the container model ID of this document library folder
	*/
	@Override
	public long getContainerModelId() {
		return _dlFolder.getContainerModelId();
	}

	/**
	* Returns the container name of this document library folder.
	*
	* @return the container name of this document library folder
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _dlFolder.getContainerModelName();
	}

	/**
	* Returns the create date of this document library folder.
	*
	* @return the create date of this document library folder
	*/
	@Override
	public Date getCreateDate() {
		return _dlFolder.getCreateDate();
	}

	/**
	* Returns the default file entry type ID of this document library folder.
	*
	* @return the default file entry type ID of this document library folder
	*/
	@Override
	public long getDefaultFileEntryTypeId() {
		return _dlFolder.getDefaultFileEntryTypeId();
	}

	/**
	* Returns the description of this document library folder.
	*
	* @return the description of this document library folder
	*/
	@Override
	public java.lang.String getDescription() {
		return _dlFolder.getDescription();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFolder.getExpandoBridge();
	}

	/**
	* Returns the folder ID of this document library folder.
	*
	* @return the folder ID of this document library folder
	*/
	@Override
	public long getFolderId() {
		return _dlFolder.getFolderId();
	}

	/**
	* Returns the group ID of this document library folder.
	*
	* @return the group ID of this document library folder
	*/
	@Override
	public long getGroupId() {
		return _dlFolder.getGroupId();
	}

	/**
	* Returns the hidden of this document library folder.
	*
	* @return the hidden of this document library folder
	*/
	@Override
	public boolean getHidden() {
		return _dlFolder.getHidden();
	}

	/**
	* Returns the last post date of this document library folder.
	*
	* @return the last post date of this document library folder
	*/
	@Override
	public Date getLastPostDate() {
		return _dlFolder.getLastPostDate();
	}

	/**
	* Returns the last publish date of this document library folder.
	*
	* @return the last publish date of this document library folder
	*/
	@Override
	public Date getLastPublishDate() {
		return _dlFolder.getLastPublishDate();
	}

	/**
	* Returns the modified date of this document library folder.
	*
	* @return the modified date of this document library folder
	*/
	@Override
	public Date getModifiedDate() {
		return _dlFolder.getModifiedDate();
	}

	/**
	* Returns the mount point of this document library folder.
	*
	* @return the mount point of this document library folder
	*/
	@Override
	public boolean getMountPoint() {
		return _dlFolder.getMountPoint();
	}

	/**
	* Returns the name of this document library folder.
	*
	* @return the name of this document library folder
	*/
	@Override
	public java.lang.String getName() {
		return _dlFolder.getName();
	}

	/**
	* Returns the parent container model ID of this document library folder.
	*
	* @return the parent container model ID of this document library folder
	*/
	@Override
	public long getParentContainerModelId() {
		return _dlFolder.getParentContainerModelId();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getParentFolder();
	}

	/**
	* Returns the parent folder ID of this document library folder.
	*
	* @return the parent folder ID of this document library folder
	*/
	@Override
	public long getParentFolderId() {
		return _dlFolder.getParentFolderId();
	}

	@Override
	public java.lang.String getPath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getPath();
	}

	@Override
	public java.lang.String[] getPathArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getPathArray();
	}

	/**
	* Returns the primary key of this document library folder.
	*
	* @return the primary key of this document library folder
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFolder.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFolder.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this document library folder.
	*
	* @return the repository ID of this document library folder
	*/
	@Override
	public long getRepositoryId() {
		return _dlFolder.getRepositoryId();
	}

	/**
	* Returns the restriction type of this document library folder.
	*
	* @return the restriction type of this document library folder
	*/
	@Override
	public int getRestrictionType() {
		return _dlFolder.getRestrictionType();
	}

	/**
	* Returns the status of this document library folder.
	*
	* @return the status of this document library folder
	*/
	@Override
	public int getStatus() {
		return _dlFolder.getStatus();
	}

	/**
	* Returns the status by user ID of this document library folder.
	*
	* @return the status by user ID of this document library folder
	*/
	@Override
	public long getStatusByUserId() {
		return _dlFolder.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this document library folder.
	*
	* @return the status by user name of this document library folder
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _dlFolder.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this document library folder.
	*
	* @return the status by user uuid of this document library folder
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _dlFolder.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this document library folder.
	*
	* @return the status date of this document library folder
	*/
	@Override
	public Date getStatusDate() {
		return _dlFolder.getStatusDate();
	}

	/**
	* Returns the trash entry created when this document library folder was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this document library folder.
	*
	* @return the trash entry created when this document library folder was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.portlet.trash.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFolder.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this document library folder.
	*
	* @return the class primary key of the trash entry for this document library folder
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _dlFolder.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this document library folder.
	*
	* @return the trash handler for this document library folder
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _dlFolder.getTrashHandler();
	}

	/**
	* Returns the tree path of this document library folder.
	*
	* @return the tree path of this document library folder
	*/
	@Override
	public java.lang.String getTreePath() {
		return _dlFolder.getTreePath();
	}

	/**
	* Returns the user ID of this document library folder.
	*
	* @return the user ID of this document library folder
	*/
	@Override
	public long getUserId() {
		return _dlFolder.getUserId();
	}

	/**
	* Returns the user name of this document library folder.
	*
	* @return the user name of this document library folder
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFolder.getUserName();
	}

	/**
	* Returns the user uuid of this document library folder.
	*
	* @return the user uuid of this document library folder
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _dlFolder.getUserUuid();
	}

	/**
	* Returns the uuid of this document library folder.
	*
	* @return the uuid of this document library folder
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFolder.getUuid();
	}

	@Override
	public boolean hasInheritableLock() {
		return _dlFolder.hasInheritableLock();
	}

	@Override
	public boolean hasLock() {
		return _dlFolder.hasLock();
	}

	@Override
	public int hashCode() {
		return _dlFolder.hashCode();
	}

	/**
	* Returns <code>true</code> if this document library folder is approved.
	*
	* @return <code>true</code> if this document library folder is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _dlFolder.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFolder.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this document library folder is denied.
	*
	* @return <code>true</code> if this document library folder is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _dlFolder.isDenied();
	}

	/**
	* Returns <code>true</code> if this document library folder is a draft.
	*
	* @return <code>true</code> if this document library folder is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _dlFolder.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFolder.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this document library folder is expired.
	*
	* @return <code>true</code> if this document library folder is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _dlFolder.isExpired();
	}

	/**
	* Returns <code>true</code> if this document library folder is hidden.
	*
	* @return <code>true</code> if this document library folder is hidden; <code>false</code> otherwise
	*/
	@Override
	public boolean isHidden() {
		return _dlFolder.isHidden();
	}

	@Override
	public boolean isInHiddenFolder() {
		return _dlFolder.isInHiddenFolder();
	}

	/**
	* Returns <code>true</code> if this document library folder is in the Recycle Bin.
	*
	* @return <code>true</code> if this document library folder is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _dlFolder.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this document library folder is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this document library folder is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _dlFolder.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _dlFolder.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _dlFolder.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this document library folder is inactive.
	*
	* @return <code>true</code> if this document library folder is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _dlFolder.isInactive();
	}

	/**
	* Returns <code>true</code> if this document library folder is incomplete.
	*
	* @return <code>true</code> if this document library folder is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _dlFolder.isIncomplete();
	}

	@Override
	public boolean isLocked() {
		return _dlFolder.isLocked();
	}

	/**
	* Returns <code>true</code> if this document library folder is mount point.
	*
	* @return <code>true</code> if this document library folder is mount point; <code>false</code> otherwise
	*/
	@Override
	public boolean isMountPoint() {
		return _dlFolder.isMountPoint();
	}

	@Override
	public boolean isNew() {
		return _dlFolder.isNew();
	}

	/**
	* Returns <code>true</code> if this document library folder is pending.
	*
	* @return <code>true</code> if this document library folder is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _dlFolder.isPending();
	}

	@Override
	public boolean isRoot() {
		return _dlFolder.isRoot();
	}

	/**
	* Returns <code>true</code> if this document library folder is scheduled.
	*
	* @return <code>true</code> if this document library folder is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _dlFolder.isScheduled();
	}

	@Override
	public void persist() {
		_dlFolder.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFolder.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this document library folder.
	*
	* @param companyId the company ID of this document library folder
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFolder.setCompanyId(companyId);
	}

	/**
	* Sets the container model ID of this document library folder.
	*
	* @param containerModelId the container model ID of this document library folder
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_dlFolder.setContainerModelId(containerModelId);
	}

	/**
	* Sets the create date of this document library folder.
	*
	* @param createDate the create date of this document library folder
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlFolder.setCreateDate(createDate);
	}

	/**
	* Sets the default file entry type ID of this document library folder.
	*
	* @param defaultFileEntryTypeId the default file entry type ID of this document library folder
	*/
	@Override
	public void setDefaultFileEntryTypeId(long defaultFileEntryTypeId) {
		_dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
	}

	/**
	* Sets the description of this document library folder.
	*
	* @param description the description of this document library folder
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_dlFolder.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFolder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the folder ID of this document library folder.
	*
	* @param folderId the folder ID of this document library folder
	*/
	@Override
	public void setFolderId(long folderId) {
		_dlFolder.setFolderId(folderId);
	}

	/**
	* Sets the group ID of this document library folder.
	*
	* @param groupId the group ID of this document library folder
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFolder.setGroupId(groupId);
	}

	/**
	* Sets whether this document library folder is hidden.
	*
	* @param hidden the hidden of this document library folder
	*/
	@Override
	public void setHidden(boolean hidden) {
		_dlFolder.setHidden(hidden);
	}

	/**
	* Sets the last post date of this document library folder.
	*
	* @param lastPostDate the last post date of this document library folder
	*/
	@Override
	public void setLastPostDate(Date lastPostDate) {
		_dlFolder.setLastPostDate(lastPostDate);
	}

	/**
	* Sets the last publish date of this document library folder.
	*
	* @param lastPublishDate the last publish date of this document library folder
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFolder.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this document library folder.
	*
	* @param modifiedDate the modified date of this document library folder
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Sets whether this document library folder is mount point.
	*
	* @param mountPoint the mount point of this document library folder
	*/
	@Override
	public void setMountPoint(boolean mountPoint) {
		_dlFolder.setMountPoint(mountPoint);
	}

	/**
	* Sets the name of this document library folder.
	*
	* @param name the name of this document library folder
	*/
	@Override
	public void setName(java.lang.String name) {
		_dlFolder.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_dlFolder.setNew(n);
	}

	/**
	* Sets the parent container model ID of this document library folder.
	*
	* @param parentContainerModelId the parent container model ID of this document library folder
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_dlFolder.setParentContainerModelId(parentContainerModelId);
	}

	/**
	* Sets the parent folder ID of this document library folder.
	*
	* @param parentFolderId the parent folder ID of this document library folder
	*/
	@Override
	public void setParentFolderId(long parentFolderId) {
		_dlFolder.setParentFolderId(parentFolderId);
	}

	/**
	* Sets the primary key of this document library folder.
	*
	* @param primaryKey the primary key of this document library folder
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFolder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this document library folder.
	*
	* @param repositoryId the repository ID of this document library folder
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlFolder.setRepositoryId(repositoryId);
	}

	/**
	* Sets the restriction type of this document library folder.
	*
	* @param restrictionType the restriction type of this document library folder
	*/
	@Override
	public void setRestrictionType(int restrictionType) {
		_dlFolder.setRestrictionType(restrictionType);
	}

	/**
	* Sets the status of this document library folder.
	*
	* @param status the status of this document library folder
	*/
	@Override
	public void setStatus(int status) {
		_dlFolder.setStatus(status);
	}

	/**
	* Sets the status by user ID of this document library folder.
	*
	* @param statusByUserId the status by user ID of this document library folder
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_dlFolder.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this document library folder.
	*
	* @param statusByUserName the status by user name of this document library folder
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFolder.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this document library folder.
	*
	* @param statusByUserUuid the status by user uuid of this document library folder
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFolder.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this document library folder.
	*
	* @param statusDate the status date of this document library folder
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_dlFolder.setStatusDate(statusDate);
	}

	/**
	* Sets the tree path of this document library folder.
	*
	* @param treePath the tree path of this document library folder
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_dlFolder.setTreePath(treePath);
	}

	/**
	* Sets the user ID of this document library folder.
	*
	* @param userId the user ID of this document library folder
	*/
	@Override
	public void setUserId(long userId) {
		_dlFolder.setUserId(userId);
	}

	/**
	* Sets the user name of this document library folder.
	*
	* @param userName the user name of this document library folder
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFolder.setUserName(userName);
	}

	/**
	* Sets the user uuid of this document library folder.
	*
	* @param userUuid the user uuid of this document library folder
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_dlFolder.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this document library folder.
	*
	* @param uuid the uuid of this document library folder
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFolder.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFolder> toCacheModel() {
		return _dlFolder.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder toEscapedModel() {
		return new DLFolderWrapper(_dlFolder.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFolder.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder toUnescapedModel() {
		return new DLFolderWrapper(_dlFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFolder.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_dlFolder.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFolderWrapper)) {
			return false;
		}

		DLFolderWrapper dlFolderWrapper = (DLFolderWrapper)obj;

		if (Validator.equals(_dlFolder, dlFolderWrapper._dlFolder)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFolder.getStagedModelType();
	}

	@Override
	public DLFolder getWrappedModel() {
		return _dlFolder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFolder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFolder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFolder.resetOriginalValues();
	}

	private final DLFolder _dlFolder;
}