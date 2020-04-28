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

package com.liferay.document.library.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DLFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolder
 * @generated
 */
public class DLFolderWrapper
	extends BaseModelWrapper<DLFolder>
	implements DLFolder, ModelWrapper<DLFolder> {

	public DLFolderWrapper(DLFolder dlFolder) {
		super(dlFolder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("folderId", getFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("mountPoint", isMountPoint());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("defaultFileEntryTypeId", getDefaultFileEntryTypeId());
		attributes.put("hidden", isHidden());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public java.util.List<Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorFolderIds();
	}

	@Override
	public java.util.List<DLFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestors();
	}

	/**
	 * Returns the company ID of this document library folder.
	 *
	 * @return the company ID of this document library folder
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this document library folder.
	 *
	 * @return the container model ID of this document library folder
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this document library folder.
	 *
	 * @return the container name of this document library folder
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this document library folder.
	 *
	 * @return the create date of this document library folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this document library folder.
	 *
	 * @return the ct collection ID of this document library folder
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the default file entry type ID of this document library folder.
	 *
	 * @return the default file entry type ID of this document library folder
	 */
	@Override
	public long getDefaultFileEntryTypeId() {
		return model.getDefaultFileEntryTypeId();
	}

	/**
	 * Returns the description of this document library folder.
	 *
	 * @return the description of this document library folder
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the folder ID of this document library folder.
	 *
	 * @return the folder ID of this document library folder
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the group ID of this document library folder.
	 *
	 * @return the group ID of this document library folder
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the hidden of this document library folder.
	 *
	 * @return the hidden of this document library folder
	 */
	@Override
	public boolean getHidden() {
		return model.getHidden();
	}

	/**
	 * Returns the last post date of this document library folder.
	 *
	 * @return the last post date of this document library folder
	 */
	@Override
	public Date getLastPostDate() {
		return model.getLastPostDate();
	}

	/**
	 * Returns the last publish date of this document library folder.
	 *
	 * @return the last publish date of this document library folder
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this document library folder.
	 *
	 * @return the modified date of this document library folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mount point of this document library folder.
	 *
	 * @return the mount point of this document library folder
	 */
	@Override
	public boolean getMountPoint() {
		return model.getMountPoint();
	}

	/**
	 * Returns the mvcc version of this document library folder.
	 *
	 * @return the mvcc version of this document library folder
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this document library folder.
	 *
	 * @return the name of this document library folder
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent container model ID of this document library folder.
	 *
	 * @return the parent container model ID of this document library folder
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	@Override
	public DLFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentFolder();
	}

	/**
	 * Returns the parent folder ID of this document library folder.
	 *
	 * @return the parent folder ID of this document library folder
	 */
	@Override
	public long getParentFolderId() {
		return model.getParentFolderId();
	}

	@Override
	public String getPath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPath();
	}

	@Override
	public String[] getPathArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getPathArray();
	}

	/**
	 * Returns the primary key of this document library folder.
	 *
	 * @return the primary key of this document library folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this document library folder.
	 *
	 * @return the repository ID of this document library folder
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the restriction type of this document library folder.
	 *
	 * @return the restriction type of this document library folder
	 */
	@Override
	public int getRestrictionType() {
		return model.getRestrictionType();
	}

	/**
	 * Returns the status of this document library folder.
	 *
	 * @return the status of this document library folder
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this document library folder.
	 *
	 * @return the status by user ID of this document library folder
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this document library folder.
	 *
	 * @return the status by user name of this document library folder
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this document library folder.
	 *
	 * @return the status by user uuid of this document library folder
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this document library folder.
	 *
	 * @return the status date of this document library folder
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the trash entry created when this document library folder was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this document library folder.
	 *
	 * @return the trash entry created when this document library folder was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this document library folder.
	 *
	 * @return the class primary key of the trash entry for this document library folder
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this document library folder.
	 *
	 * @return the trash handler for this document library folder
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the tree path of this document library folder.
	 *
	 * @return the tree path of this document library folder
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the user ID of this document library folder.
	 *
	 * @return the user ID of this document library folder
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this document library folder.
	 *
	 * @return the user name of this document library folder
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this document library folder.
	 *
	 * @return the user uuid of this document library folder
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this document library folder.
	 *
	 * @return the uuid of this document library folder
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasInheritableLock() {
		return model.hasInheritableLock();
	}

	@Override
	public boolean hasLock() {
		return model.hasLock();
	}

	/**
	 * Returns <code>true</code> if this document library folder is approved.
	 *
	 * @return <code>true</code> if this document library folder is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this document library folder is denied.
	 *
	 * @return <code>true</code> if this document library folder is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this document library folder is a draft.
	 *
	 * @return <code>true</code> if this document library folder is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this document library folder is expired.
	 *
	 * @return <code>true</code> if this document library folder is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this document library folder is hidden.
	 *
	 * @return <code>true</code> if this document library folder is hidden; <code>false</code> otherwise
	 */
	@Override
	public boolean isHidden() {
		return model.isHidden();
	}

	/**
	 * Returns <code>true</code> if this document library folder is inactive.
	 *
	 * @return <code>true</code> if this document library folder is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this document library folder is incomplete.
	 *
	 * @return <code>true</code> if this document library folder is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	@Override
	public boolean isInHiddenFolder() {
		return model.isInHiddenFolder();
	}

	/**
	 * Returns <code>true</code> if this document library folder is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this document library folder is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this document library folder is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this document library folder is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return model.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return model.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return model.isInTrashImplicitly();
	}

	@Override
	public boolean isLocked() {
		return model.isLocked();
	}

	/**
	 * Returns <code>true</code> if this document library folder is mount point.
	 *
	 * @return <code>true</code> if this document library folder is mount point; <code>false</code> otherwise
	 */
	@Override
	public boolean isMountPoint() {
		return model.isMountPoint();
	}

	/**
	 * Returns <code>true</code> if this document library folder is pending.
	 *
	 * @return <code>true</code> if this document library folder is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * Returns <code>true</code> if this document library folder is scheduled.
	 *
	 * @return <code>true</code> if this document library folder is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this document library folder.
	 *
	 * @param companyId the company ID of this document library folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this document library folder.
	 *
	 * @param containerModelId the container model ID of this document library folder
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this document library folder.
	 *
	 * @param createDate the create date of this document library folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this document library folder.
	 *
	 * @param ctCollectionId the ct collection ID of this document library folder
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the default file entry type ID of this document library folder.
	 *
	 * @param defaultFileEntryTypeId the default file entry type ID of this document library folder
	 */
	@Override
	public void setDefaultFileEntryTypeId(long defaultFileEntryTypeId) {
		model.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
	}

	/**
	 * Sets the description of this document library folder.
	 *
	 * @param description the description of this document library folder
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the folder ID of this document library folder.
	 *
	 * @param folderId the folder ID of this document library folder
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this document library folder.
	 *
	 * @param groupId the group ID of this document library folder
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets whether this document library folder is hidden.
	 *
	 * @param hidden the hidden of this document library folder
	 */
	@Override
	public void setHidden(boolean hidden) {
		model.setHidden(hidden);
	}

	/**
	 * Sets the last post date of this document library folder.
	 *
	 * @param lastPostDate the last post date of this document library folder
	 */
	@Override
	public void setLastPostDate(Date lastPostDate) {
		model.setLastPostDate(lastPostDate);
	}

	/**
	 * Sets the last publish date of this document library folder.
	 *
	 * @param lastPublishDate the last publish date of this document library folder
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this document library folder.
	 *
	 * @param modifiedDate the modified date of this document library folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets whether this document library folder is mount point.
	 *
	 * @param mountPoint the mount point of this document library folder
	 */
	@Override
	public void setMountPoint(boolean mountPoint) {
		model.setMountPoint(mountPoint);
	}

	/**
	 * Sets the mvcc version of this document library folder.
	 *
	 * @param mvccVersion the mvcc version of this document library folder
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this document library folder.
	 *
	 * @param name the name of this document library folder
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent container model ID of this document library folder.
	 *
	 * @param parentContainerModelId the parent container model ID of this document library folder
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the parent folder ID of this document library folder.
	 *
	 * @param parentFolderId the parent folder ID of this document library folder
	 */
	@Override
	public void setParentFolderId(long parentFolderId) {
		model.setParentFolderId(parentFolderId);
	}

	/**
	 * Sets the primary key of this document library folder.
	 *
	 * @param primaryKey the primary key of this document library folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this document library folder.
	 *
	 * @param repositoryId the repository ID of this document library folder
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the restriction type of this document library folder.
	 *
	 * @param restrictionType the restriction type of this document library folder
	 */
	@Override
	public void setRestrictionType(int restrictionType) {
		model.setRestrictionType(restrictionType);
	}

	/**
	 * Sets the status of this document library folder.
	 *
	 * @param status the status of this document library folder
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this document library folder.
	 *
	 * @param statusByUserId the status by user ID of this document library folder
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this document library folder.
	 *
	 * @param statusByUserName the status by user name of this document library folder
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this document library folder.
	 *
	 * @param statusByUserUuid the status by user uuid of this document library folder
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this document library folder.
	 *
	 * @param statusDate the status date of this document library folder
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the tree path of this document library folder.
	 *
	 * @param treePath the tree path of this document library folder
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this document library folder.
	 *
	 * @param userId the user ID of this document library folder
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this document library folder.
	 *
	 * @param userName the user name of this document library folder
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this document library folder.
	 *
	 * @param userUuid the user uuid of this document library folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this document library folder.
	 *
	 * @param uuid the uuid of this document library folder
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public Map<String, Function<DLFolder, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DLFolder, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DLFolderWrapper wrap(DLFolder dlFolder) {
		return new DLFolderWrapper(dlFolder);
	}

}