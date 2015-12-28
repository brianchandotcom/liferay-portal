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
 * This class is a wrapper for {@link DLFileVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersion
 * @generated
 */
@ProviderType
public class DLFileVersionWrapper implements DLFileVersion,
	ModelWrapper<DLFileVersion> {
	public DLFileVersionWrapper(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("treePath", getTreePath());
		attributes.put("fileName", getFileName());
		attributes.put("extension", getExtension());
		attributes.put("mimeType", getMimeType());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("changeLog", getChangeLog());
		attributes.put("extraSettings", getExtraSettings());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("version", getVersion());
		attributes.put("size", getSize());
		attributes.put("checksum", getChecksum());
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

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String fileName = (String)attributes.get("fileName");

		if (fileName != null) {
			setFileName(fileName);
		}

		String extension = (String)attributes.get("extension");

		if (extension != null) {
			setExtension(extension);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
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

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String checksum = (String)attributes.get("checksum");

		if (checksum != null) {
			setChecksum(checksum);
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
		return _dlFileVersion.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileVersionWrapper((DLFileVersion)_dlFileVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return _dlFileVersion.compareTo(dlFileVersion);
	}

	/**
	* Returns the change log of this document library file version.
	*
	* @return the change log of this document library file version
	*/
	@Override
	public java.lang.String getChangeLog() {
		return _dlFileVersion.getChangeLog();
	}

	/**
	* Returns the checksum of this document library file version.
	*
	* @return the checksum of this document library file version
	*/
	@Override
	public java.lang.String getChecksum() {
		return _dlFileVersion.getChecksum();
	}

	/**
	* Returns the company ID of this document library file version.
	*
	* @return the company ID of this document library file version
	*/
	@Override
	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	@Override
	public java.io.InputStream getContentStream(boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileVersion.getContentStream(incrementCounter);
	}

	/**
	* Returns the create date of this document library file version.
	*
	* @return the create date of this document library file version
	*/
	@Override
	public Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.DDMStructure> getDDMStructures()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileVersion.getDDMStructures();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileVersion.getDLFileEntryType();
	}

	/**
	* Returns the description of this document library file version.
	*
	* @return the description of this document library file version
	*/
	@Override
	public java.lang.String getDescription() {
		return _dlFileVersion.getDescription();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	/**
	* Returns the extension of this document library file version.
	*
	* @return the extension of this document library file version
	*/
	@Override
	public java.lang.String getExtension() {
		return _dlFileVersion.getExtension();
	}

	/**
	* Returns the extra settings of this document library file version.
	*
	* @return the extra settings of this document library file version
	*/
	@Override
	public java.lang.String getExtraSettings() {
		return _dlFileVersion.getExtraSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileVersion.getExtraSettingsProperties();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileVersion.getFileEntry();
	}

	/**
	* Returns the file entry ID of this document library file version.
	*
	* @return the file entry ID of this document library file version
	*/
	@Override
	public long getFileEntryId() {
		return _dlFileVersion.getFileEntryId();
	}

	/**
	* Returns the file entry type ID of this document library file version.
	*
	* @return the file entry type ID of this document library file version
	*/
	@Override
	public long getFileEntryTypeId() {
		return _dlFileVersion.getFileEntryTypeId();
	}

	/**
	* Returns the file name of this document library file version.
	*
	* @return the file name of this document library file version
	*/
	@Override
	public java.lang.String getFileName() {
		return _dlFileVersion.getFileName();
	}

	/**
	* Returns the file version ID of this document library file version.
	*
	* @return the file version ID of this document library file version
	*/
	@Override
	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileVersion.getFolder();
	}

	/**
	* Returns the folder ID of this document library file version.
	*
	* @return the folder ID of this document library file version
	*/
	@Override
	public long getFolderId() {
		return _dlFileVersion.getFolderId();
	}

	/**
	* Returns the group ID of this document library file version.
	*
	* @return the group ID of this document library file version
	*/
	@Override
	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	@Override
	public java.lang.String getIcon() {
		return _dlFileVersion.getIcon();
	}

	/**
	* Returns the last publish date of this document library file version.
	*
	* @return the last publish date of this document library file version
	*/
	@Override
	public Date getLastPublishDate() {
		return _dlFileVersion.getLastPublishDate();
	}

	/**
	* Returns the mime type of this document library file version.
	*
	* @return the mime type of this document library file version
	*/
	@Override
	public java.lang.String getMimeType() {
		return _dlFileVersion.getMimeType();
	}

	/**
	* Returns the modified date of this document library file version.
	*
	* @return the modified date of this document library file version
	*/
	@Override
	public Date getModifiedDate() {
		return _dlFileVersion.getModifiedDate();
	}

	/**
	* Returns the primary key of this document library file version.
	*
	* @return the primary key of this document library file version
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this document library file version.
	*
	* @return the repository ID of this document library file version
	*/
	@Override
	public long getRepositoryId() {
		return _dlFileVersion.getRepositoryId();
	}

	/**
	* Returns the size of this document library file version.
	*
	* @return the size of this document library file version
	*/
	@Override
	public long getSize() {
		return _dlFileVersion.getSize();
	}

	/**
	* Returns the status of this document library file version.
	*
	* @return the status of this document library file version
	*/
	@Override
	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	/**
	* Returns the status by user ID of this document library file version.
	*
	* @return the status by user ID of this document library file version
	*/
	@Override
	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this document library file version.
	*
	* @return the status by user name of this document library file version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this document library file version.
	*
	* @return the status by user uuid of this document library file version
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _dlFileVersion.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this document library file version.
	*
	* @return the status date of this document library file version
	*/
	@Override
	public Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	/**
	* Returns the title of this document library file version.
	*
	* @return the title of this document library file version
	*/
	@Override
	public java.lang.String getTitle() {
		return _dlFileVersion.getTitle();
	}

	/**
	* Returns the tree path of this document library file version.
	*
	* @return the tree path of this document library file version
	*/
	@Override
	public java.lang.String getTreePath() {
		return _dlFileVersion.getTreePath();
	}

	/**
	* Returns the user ID of this document library file version.
	*
	* @return the user ID of this document library file version
	*/
	@Override
	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	/**
	* Returns the user name of this document library file version.
	*
	* @return the user name of this document library file version
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileVersion.getUserName();
	}

	/**
	* Returns the user uuid of this document library file version.
	*
	* @return the user uuid of this document library file version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _dlFileVersion.getUserUuid();
	}

	/**
	* Returns the uuid of this document library file version.
	*
	* @return the uuid of this document library file version
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileVersion.getUuid();
	}

	/**
	* Returns the version of this document library file version.
	*
	* @return the version of this document library file version
	*/
	@Override
	public java.lang.String getVersion() {
		return _dlFileVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _dlFileVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this document library file version is approved.
	*
	* @return <code>true</code> if this document library file version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this document library file version is denied.
	*
	* @return <code>true</code> if this document library file version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _dlFileVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this document library file version is a draft.
	*
	* @return <code>true</code> if this document library file version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _dlFileVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this document library file version is expired.
	*
	* @return <code>true</code> if this document library file version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _dlFileVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this document library file version is inactive.
	*
	* @return <code>true</code> if this document library file version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _dlFileVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this document library file version is incomplete.
	*
	* @return <code>true</code> if this document library file version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _dlFileVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _dlFileVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this document library file version is pending.
	*
	* @return <code>true</code> if this document library file version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _dlFileVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this document library file version is scheduled.
	*
	* @return <code>true</code> if this document library file version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _dlFileVersion.isScheduled();
	}

	@Override
	public void persist() {
		_dlFileVersion.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the change log of this document library file version.
	*
	* @param changeLog the change log of this document library file version
	*/
	@Override
	public void setChangeLog(java.lang.String changeLog) {
		_dlFileVersion.setChangeLog(changeLog);
	}

	/**
	* Sets the checksum of this document library file version.
	*
	* @param checksum the checksum of this document library file version
	*/
	@Override
	public void setChecksum(java.lang.String checksum) {
		_dlFileVersion.setChecksum(checksum);
	}

	/**
	* Sets the company ID of this document library file version.
	*
	* @param companyId the company ID of this document library file version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this document library file version.
	*
	* @param createDate the create date of this document library file version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlFileVersion.setCreateDate(createDate);
	}

	/**
	* Sets the description of this document library file version.
	*
	* @param description the description of this document library file version
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_dlFileVersion.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFileVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlFileVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the extension of this document library file version.
	*
	* @param extension the extension of this document library file version
	*/
	@Override
	public void setExtension(java.lang.String extension) {
		_dlFileVersion.setExtension(extension);
	}

	/**
	* Sets the extra settings of this document library file version.
	*
	* @param extraSettings the extra settings of this document library file version
	*/
	@Override
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileVersion.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileVersion.setExtraSettingsProperties(extraSettingsProperties);
	}

	/**
	* Sets the file entry ID of this document library file version.
	*
	* @param fileEntryId the file entry ID of this document library file version
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileVersion.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry type ID of this document library file version.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file version
	*/
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileVersion.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Sets the file name of this document library file version.
	*
	* @param fileName the file name of this document library file version
	*/
	@Override
	public void setFileName(java.lang.String fileName) {
		_dlFileVersion.setFileName(fileName);
	}

	/**
	* Sets the file version ID of this document library file version.
	*
	* @param fileVersionId the file version ID of this document library file version
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_dlFileVersion.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the folder ID of this document library file version.
	*
	* @param folderId the folder ID of this document library file version
	*/
	@Override
	public void setFolderId(long folderId) {
		_dlFileVersion.setFolderId(folderId);
	}

	/**
	* Sets the group ID of this document library file version.
	*
	* @param groupId the group ID of this document library file version
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this document library file version.
	*
	* @param lastPublishDate the last publish date of this document library file version
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFileVersion.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the mime type of this document library file version.
	*
	* @param mimeType the mime type of this document library file version
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_dlFileVersion.setMimeType(mimeType);
	}

	/**
	* Sets the modified date of this document library file version.
	*
	* @param modifiedDate the modified date of this document library file version
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFileVersion.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileVersion.setNew(n);
	}

	/**
	* Sets the primary key of this document library file version.
	*
	* @param primaryKey the primary key of this document library file version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this document library file version.
	*
	* @param repositoryId the repository ID of this document library file version
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlFileVersion.setRepositoryId(repositoryId);
	}

	/**
	* Sets the size of this document library file version.
	*
	* @param size the size of this document library file version
	*/
	@Override
	public void setSize(long size) {
		_dlFileVersion.setSize(size);
	}

	/**
	* Sets the status of this document library file version.
	*
	* @param status the status of this document library file version
	*/
	@Override
	public void setStatus(int status) {
		_dlFileVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this document library file version.
	*
	* @param statusByUserId the status by user ID of this document library file version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_dlFileVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this document library file version.
	*
	* @param statusByUserName the status by user name of this document library file version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this document library file version.
	*
	* @param statusByUserUuid the status by user uuid of this document library file version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this document library file version.
	*
	* @param statusDate the status date of this document library file version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_dlFileVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the title of this document library file version.
	*
	* @param title the title of this document library file version
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_dlFileVersion.setTitle(title);
	}

	/**
	* Sets the tree path of this document library file version.
	*
	* @param treePath the tree path of this document library file version
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_dlFileVersion.setTreePath(treePath);
	}

	/**
	* Sets the user ID of this document library file version.
	*
	* @param userId the user ID of this document library file version
	*/
	@Override
	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this document library file version.
	*
	* @param userName the user name of this document library file version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFileVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this document library file version.
	*
	* @param userUuid the user uuid of this document library file version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this document library file version.
	*
	* @param uuid the uuid of this document library file version
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileVersion.setUuid(uuid);
	}

	/**
	* Sets the version of this document library file version.
	*
	* @param version the version of this document library file version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_dlFileVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileVersion> toCacheModel() {
		return _dlFileVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion toEscapedModel() {
		return new DLFileVersionWrapper(_dlFileVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileVersion.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion toUnescapedModel() {
		return new DLFileVersionWrapper(_dlFileVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileVersion.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_dlFileVersion.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileVersionWrapper)) {
			return false;
		}

		DLFileVersionWrapper dlFileVersionWrapper = (DLFileVersionWrapper)obj;

		if (Validator.equals(_dlFileVersion, dlFileVersionWrapper._dlFileVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFileVersion.getStagedModelType();
	}

	@Override
	public DLFileVersion getWrappedModel() {
		return _dlFileVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileVersion.resetOriginalValues();
	}

	private final DLFileVersion _dlFileVersion;
}