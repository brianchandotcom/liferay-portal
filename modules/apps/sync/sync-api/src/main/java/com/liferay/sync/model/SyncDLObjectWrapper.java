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

package com.liferay.sync.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SyncDLObject}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObject
 * @generated
 */
public class SyncDLObjectWrapper
	extends BaseModelWrapper<SyncDLObject>
	implements ModelWrapper<SyncDLObject>, SyncDLObject {

	public SyncDLObjectWrapper(SyncDLObject syncDLObject) {
		super(syncDLObject);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("syncDLObjectId", getSyncDLObjectId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("extension", getExtension());
		attributes.put("mimeType", getMimeType());
		attributes.put("description", getDescription());
		attributes.put("changeLog", getChangeLog());
		attributes.put("extraSettings", getExtraSettings());
		attributes.put("version", getVersion());
		attributes.put("versionId", getVersionId());
		attributes.put("size", getSize());
		attributes.put("checksum", getChecksum());
		attributes.put("event", getEvent());
		attributes.put("lanTokenKey", getLanTokenKey());
		attributes.put(
			"lastPermissionChangeDate", getLastPermissionChangeDate());
		attributes.put("lockExpirationDate", getLockExpirationDate());
		attributes.put("lockUserId", getLockUserId());
		attributes.put("lockUserName", getLockUserName());
		attributes.put("type", getType());
		attributes.put("typePK", getTypePK());
		attributes.put("typeUuid", getTypeUuid());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long syncDLObjectId = (Long)attributes.get("syncDLObjectId");

		if (syncDLObjectId != null) {
			setSyncDLObjectId(syncDLObjectId);
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

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
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

		String extension = (String)attributes.get("extension");

		if (extension != null) {
			setExtension(extension);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String changeLog = (String)attributes.get("changeLog");

		if (changeLog != null) {
			setChangeLog(changeLog);
		}

		String extraSettings = (String)attributes.get("extraSettings");

		if (extraSettings != null) {
			setExtraSettings(extraSettings);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long versionId = (Long)attributes.get("versionId");

		if (versionId != null) {
			setVersionId(versionId);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String checksum = (String)attributes.get("checksum");

		if (checksum != null) {
			setChecksum(checksum);
		}

		String event = (String)attributes.get("event");

		if (event != null) {
			setEvent(event);
		}

		String lanTokenKey = (String)attributes.get("lanTokenKey");

		if (lanTokenKey != null) {
			setLanTokenKey(lanTokenKey);
		}

		Date lastPermissionChangeDate = (Date)attributes.get(
			"lastPermissionChangeDate");

		if (lastPermissionChangeDate != null) {
			setLastPermissionChangeDate(lastPermissionChangeDate);
		}

		Date lockExpirationDate = (Date)attributes.get("lockExpirationDate");

		if (lockExpirationDate != null) {
			setLockExpirationDate(lockExpirationDate);
		}

		Long lockUserId = (Long)attributes.get("lockUserId");

		if (lockUserId != null) {
			setLockUserId(lockUserId);
		}

		String lockUserName = (String)attributes.get("lockUserName");

		if (lockUserName != null) {
			setLockUserName(lockUserName);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long typePK = (Long)attributes.get("typePK");

		if (typePK != null) {
			setTypePK(typePK);
		}

		String typeUuid = (String)attributes.get("typeUuid");

		if (typeUuid != null) {
			setTypeUuid(typeUuid);
		}
	}

	@Override
	public String buildTreePath() {
		return model.buildTreePath();
	}

	/**
	 * Returns the change log of this sync dl object.
	 *
	 * @return the change log of this sync dl object
	 */
	@Override
	public String getChangeLog() {
		return model.getChangeLog();
	}

	/**
	 * Returns the checksum of this sync dl object.
	 *
	 * @return the checksum of this sync dl object
	 */
	@Override
	public String getChecksum() {
		return model.getChecksum();
	}

	/**
	 * Returns the company ID of this sync dl object.
	 *
	 * @return the company ID of this sync dl object
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create time of this sync dl object.
	 *
	 * @return the create time of this sync dl object
	 */
	@Override
	public long getCreateTime() {
		return model.getCreateTime();
	}

	/**
	 * Returns the description of this sync dl object.
	 *
	 * @return the description of this sync dl object
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the event of this sync dl object.
	 *
	 * @return the event of this sync dl object
	 */
	@Override
	public String getEvent() {
		return model.getEvent();
	}

	/**
	 * Returns the extension of this sync dl object.
	 *
	 * @return the extension of this sync dl object
	 */
	@Override
	public String getExtension() {
		return model.getExtension();
	}

	/**
	 * Returns the extra settings of this sync dl object.
	 *
	 * @return the extra settings of this sync dl object
	 */
	@Override
	public String getExtraSettings() {
		return model.getExtraSettings();
	}

	/**
	 * Returns the lan token key of this sync dl object.
	 *
	 * @return the lan token key of this sync dl object
	 */
	@Override
	public String getLanTokenKey() {
		return model.getLanTokenKey();
	}

	/**
	 * Returns the last permission change date of this sync dl object.
	 *
	 * @return the last permission change date of this sync dl object
	 */
	@Override
	public Date getLastPermissionChangeDate() {
		return model.getLastPermissionChangeDate();
	}

	/**
	 * Returns the lock expiration date of this sync dl object.
	 *
	 * @return the lock expiration date of this sync dl object
	 */
	@Override
	public Date getLockExpirationDate() {
		return model.getLockExpirationDate();
	}

	/**
	 * Returns the lock user ID of this sync dl object.
	 *
	 * @return the lock user ID of this sync dl object
	 */
	@Override
	public long getLockUserId() {
		return model.getLockUserId();
	}

	/**
	 * Returns the lock user name of this sync dl object.
	 *
	 * @return the lock user name of this sync dl object
	 */
	@Override
	public String getLockUserName() {
		return model.getLockUserName();
	}

	/**
	 * Returns the lock user uuid of this sync dl object.
	 *
	 * @return the lock user uuid of this sync dl object
	 */
	@Override
	public String getLockUserUuid() {
		return model.getLockUserUuid();
	}

	/**
	 * Returns the mime type of this sync dl object.
	 *
	 * @return the mime type of this sync dl object
	 */
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	 * Returns the modified time of this sync dl object.
	 *
	 * @return the modified time of this sync dl object
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the name of this sync dl object.
	 *
	 * @return the name of this sync dl object
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the parent folder ID of this sync dl object.
	 *
	 * @return the parent folder ID of this sync dl object
	 */
	@Override
	public long getParentFolderId() {
		return model.getParentFolderId();
	}

	/**
	 * Returns the primary key of this sync dl object.
	 *
	 * @return the primary key of this sync dl object
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this sync dl object.
	 *
	 * @return the repository ID of this sync dl object
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the size of this sync dl object.
	 *
	 * @return the size of this sync dl object
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the sync dl object ID of this sync dl object.
	 *
	 * @return the sync dl object ID of this sync dl object
	 */
	@Override
	public long getSyncDLObjectId() {
		return model.getSyncDLObjectId();
	}

	/**
	 * Returns the tree path of this sync dl object.
	 *
	 * @return the tree path of this sync dl object
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the type of this sync dl object.
	 *
	 * @return the type of this sync dl object
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type pk of this sync dl object.
	 *
	 * @return the type pk of this sync dl object
	 */
	@Override
	public long getTypePK() {
		return model.getTypePK();
	}

	/**
	 * Returns the type uuid of this sync dl object.
	 *
	 * @return the type uuid of this sync dl object
	 */
	@Override
	public String getTypeUuid() {
		return model.getTypeUuid();
	}

	/**
	 * Returns the user ID of this sync dl object.
	 *
	 * @return the user ID of this sync dl object
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sync dl object.
	 *
	 * @return the user name of this sync dl object
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sync dl object.
	 *
	 * @return the user uuid of this sync dl object
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the version of this sync dl object.
	 *
	 * @return the version of this sync dl object
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the version ID of this sync dl object.
	 *
	 * @return the version ID of this sync dl object
	 */
	@Override
	public long getVersionId() {
		return model.getVersionId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sync dl object model instance should use the <code>SyncDLObject</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the change log of this sync dl object.
	 *
	 * @param changeLog the change log of this sync dl object
	 */
	@Override
	public void setChangeLog(String changeLog) {
		model.setChangeLog(changeLog);
	}

	/**
	 * Sets the checksum of this sync dl object.
	 *
	 * @param checksum the checksum of this sync dl object
	 */
	@Override
	public void setChecksum(String checksum) {
		model.setChecksum(checksum);
	}

	/**
	 * Sets the company ID of this sync dl object.
	 *
	 * @param companyId the company ID of this sync dl object
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the create time of this sync dl object.
	 *
	 * @param createTime the create time of this sync dl object
	 */
	@Override
	public void setCreateTime(long createTime) {
		model.setCreateTime(createTime);
	}

	/**
	 * Sets the description of this sync dl object.
	 *
	 * @param description the description of this sync dl object
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the event of this sync dl object.
	 *
	 * @param event the event of this sync dl object
	 */
	@Override
	public void setEvent(String event) {
		model.setEvent(event);
	}

	/**
	 * Sets the extension of this sync dl object.
	 *
	 * @param extension the extension of this sync dl object
	 */
	@Override
	public void setExtension(String extension) {
		model.setExtension(extension);
	}

	/**
	 * Sets the extra settings of this sync dl object.
	 *
	 * @param extraSettings the extra settings of this sync dl object
	 */
	@Override
	public void setExtraSettings(String extraSettings) {
		model.setExtraSettings(extraSettings);
	}

	/**
	 * Sets the lan token key of this sync dl object.
	 *
	 * @param lanTokenKey the lan token key of this sync dl object
	 */
	@Override
	public void setLanTokenKey(String lanTokenKey) {
		model.setLanTokenKey(lanTokenKey);
	}

	/**
	 * Sets the last permission change date of this sync dl object.
	 *
	 * @param lastPermissionChangeDate the last permission change date of this sync dl object
	 */
	@Override
	public void setLastPermissionChangeDate(Date lastPermissionChangeDate) {
		model.setLastPermissionChangeDate(lastPermissionChangeDate);
	}

	/**
	 * Sets the lock expiration date of this sync dl object.
	 *
	 * @param lockExpirationDate the lock expiration date of this sync dl object
	 */
	@Override
	public void setLockExpirationDate(Date lockExpirationDate) {
		model.setLockExpirationDate(lockExpirationDate);
	}

	/**
	 * Sets the lock user ID of this sync dl object.
	 *
	 * @param lockUserId the lock user ID of this sync dl object
	 */
	@Override
	public void setLockUserId(long lockUserId) {
		model.setLockUserId(lockUserId);
	}

	/**
	 * Sets the lock user name of this sync dl object.
	 *
	 * @param lockUserName the lock user name of this sync dl object
	 */
	@Override
	public void setLockUserName(String lockUserName) {
		model.setLockUserName(lockUserName);
	}

	/**
	 * Sets the lock user uuid of this sync dl object.
	 *
	 * @param lockUserUuid the lock user uuid of this sync dl object
	 */
	@Override
	public void setLockUserUuid(String lockUserUuid) {
		model.setLockUserUuid(lockUserUuid);
	}

	/**
	 * Sets the mime type of this sync dl object.
	 *
	 * @param mimeType the mime type of this sync dl object
	 */
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the modified time of this sync dl object.
	 *
	 * @param modifiedTime the modified time of this sync dl object
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the name of this sync dl object.
	 *
	 * @param name the name of this sync dl object
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent folder ID of this sync dl object.
	 *
	 * @param parentFolderId the parent folder ID of this sync dl object
	 */
	@Override
	public void setParentFolderId(long parentFolderId) {
		model.setParentFolderId(parentFolderId);
	}

	/**
	 * Sets the primary key of this sync dl object.
	 *
	 * @param primaryKey the primary key of this sync dl object
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this sync dl object.
	 *
	 * @param repositoryId the repository ID of this sync dl object
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the size of this sync dl object.
	 *
	 * @param size the size of this sync dl object
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the sync dl object ID of this sync dl object.
	 *
	 * @param syncDLObjectId the sync dl object ID of this sync dl object
	 */
	@Override
	public void setSyncDLObjectId(long syncDLObjectId) {
		model.setSyncDLObjectId(syncDLObjectId);
	}

	/**
	 * Sets the tree path of this sync dl object.
	 *
	 * @param treePath the tree path of this sync dl object
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the type of this sync dl object.
	 *
	 * @param type the type of this sync dl object
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type pk of this sync dl object.
	 *
	 * @param typePK the type pk of this sync dl object
	 */
	@Override
	public void setTypePK(long typePK) {
		model.setTypePK(typePK);
	}

	/**
	 * Sets the type uuid of this sync dl object.
	 *
	 * @param typeUuid the type uuid of this sync dl object
	 */
	@Override
	public void setTypeUuid(String typeUuid) {
		model.setTypeUuid(typeUuid);
	}

	/**
	 * Sets the user ID of this sync dl object.
	 *
	 * @param userId the user ID of this sync dl object
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sync dl object.
	 *
	 * @param userName the user name of this sync dl object
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sync dl object.
	 *
	 * @param userUuid the user uuid of this sync dl object
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the version of this sync dl object.
	 *
	 * @param version the version of this sync dl object
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	/**
	 * Sets the version ID of this sync dl object.
	 *
	 * @param versionId the version ID of this sync dl object
	 */
	@Override
	public void setVersionId(long versionId) {
		model.setVersionId(versionId);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	protected SyncDLObjectWrapper wrap(SyncDLObject syncDLObject) {
		return new SyncDLObjectWrapper(syncDLObject);
	}

}