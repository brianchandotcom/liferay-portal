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

import com.liferay.expando.kernel.model.ExpandoBridge;
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
 * This class is a wrapper for {@link DLFileVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersion
 * @generated
 */
public class DLFileVersionWrapper
	extends BaseModelWrapper<DLFileVersion>
	implements DLFileVersion, ModelWrapper<DLFileVersion> {

	public DLFileVersionWrapper(DLFileVersion dlFileVersion) {
		super(dlFileVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("reviewDate", getReviewDate());
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

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date reviewDate = (Date)attributes.get("reviewDate");

		if (reviewDate != null) {
			setReviewDate(reviewDate);
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

	/**
	 * Returns the change log of this document library file version.
	 *
	 * @return the change log of this document library file version
	 */
	@Override
	public String getChangeLog() {
		return model.getChangeLog();
	}

	/**
	 * Returns the checksum of this document library file version.
	 *
	 * @return the checksum of this document library file version
	 */
	@Override
	public String getChecksum() {
		return model.getChecksum();
	}

	/**
	 * Returns the company ID of this document library file version.
	 *
	 * @return the company ID of this document library file version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public java.io.InputStream getContentStream(boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getContentStream(incrementCounter);
	}

	/**
	 * Returns the create date of this document library file version.
	 *
	 * @return the create date of this document library file version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this document library file version.
	 *
	 * @return the ct collection ID of this document library file version
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			getDDMStructures()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMStructures();
	}

	/**
	 * Returns the description of this document library file version.
	 *
	 * @return the description of this document library file version
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public DLFileEntryType getDLFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDLFileEntryType();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return model.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this document library file version.
	 *
	 * @return the expiration date of this document library file version
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the extension of this document library file version.
	 *
	 * @return the extension of this document library file version
	 */
	@Override
	public String getExtension() {
		return model.getExtension();
	}

	/**
	 * Returns the extra settings of this document library file version.
	 *
	 * @return the extra settings of this document library file version
	 */
	@Override
	public String getExtraSettings() {
		return model.getExtraSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getExtraSettingsProperties() {

		return model.getExtraSettingsProperties();
	}

	@Override
	public DLFileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this document library file version.
	 *
	 * @return the file entry ID of this document library file version
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the file entry type ID of this document library file version.
	 *
	 * @return the file entry type ID of this document library file version
	 */
	@Override
	public long getFileEntryTypeId() {
		return model.getFileEntryTypeId();
	}

	/**
	 * Returns the file name of this document library file version.
	 *
	 * @return the file name of this document library file version
	 */
	@Override
	public String getFileName() {
		return model.getFileName();
	}

	/**
	 * Returns the file version ID of this document library file version.
	 *
	 * @return the file version ID of this document library file version
	 */
	@Override
	public long getFileVersionId() {
		return model.getFileVersionId();
	}

	@Override
	public DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this document library file version.
	 *
	 * @return the folder ID of this document library file version
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the group ID of this document library file version.
	 *
	 * @return the group ID of this document library file version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public String getIcon() {
		return model.getIcon();
	}

	/**
	 * Returns the last publish date of this document library file version.
	 *
	 * @return the last publish date of this document library file version
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the mime type of this document library file version.
	 *
	 * @return the mime type of this document library file version
	 */
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	 * Returns the modified date of this document library file version.
	 *
	 * @return the modified date of this document library file version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this document library file version.
	 *
	 * @return the mvcc version of this document library file version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this document library file version.
	 *
	 * @return the primary key of this document library file version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this document library file version.
	 *
	 * @return the repository ID of this document library file version
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the review date of this document library file version.
	 *
	 * @return the review date of this document library file version
	 */
	@Override
	public Date getReviewDate() {
		return model.getReviewDate();
	}

	/**
	 * Returns the size of this document library file version.
	 *
	 * @return the size of this document library file version
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the status of this document library file version.
	 *
	 * @return the status of this document library file version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this document library file version.
	 *
	 * @return the status by user ID of this document library file version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this document library file version.
	 *
	 * @return the status by user name of this document library file version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this document library file version.
	 *
	 * @return the status by user uuid of this document library file version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this document library file version.
	 *
	 * @return the status date of this document library file version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the title of this document library file version.
	 *
	 * @return the title of this document library file version
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the tree path of this document library file version.
	 *
	 * @return the tree path of this document library file version
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the user ID of this document library file version.
	 *
	 * @return the user ID of this document library file version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this document library file version.
	 *
	 * @return the user name of this document library file version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this document library file version.
	 *
	 * @return the user uuid of this document library file version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this document library file version.
	 *
	 * @return the uuid of this document library file version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this document library file version.
	 *
	 * @return the version of this document library file version
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this document library file version is approved.
	 *
	 * @return <code>true</code> if this document library file version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this document library file version is denied.
	 *
	 * @return <code>true</code> if this document library file version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this document library file version is a draft.
	 *
	 * @return <code>true</code> if this document library file version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this document library file version is expired.
	 *
	 * @return <code>true</code> if this document library file version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this document library file version is inactive.
	 *
	 * @return <code>true</code> if this document library file version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this document library file version is incomplete.
	 *
	 * @return <code>true</code> if this document library file version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this document library file version is pending.
	 *
	 * @return <code>true</code> if this document library file version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this document library file version is scheduled.
	 *
	 * @return <code>true</code> if this document library file version is scheduled; <code>false</code> otherwise
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
	 * Sets the change log of this document library file version.
	 *
	 * @param changeLog the change log of this document library file version
	 */
	@Override
	public void setChangeLog(String changeLog) {
		model.setChangeLog(changeLog);
	}

	/**
	 * Sets the checksum of this document library file version.
	 *
	 * @param checksum the checksum of this document library file version
	 */
	@Override
	public void setChecksum(String checksum) {
		model.setChecksum(checksum);
	}

	/**
	 * Sets the company ID of this document library file version.
	 *
	 * @param companyId the company ID of this document library file version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this document library file version.
	 *
	 * @param createDate the create date of this document library file version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this document library file version.
	 *
	 * @param ctCollectionId the ct collection ID of this document library file version
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this document library file version.
	 *
	 * @param description the description of this document library file version
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the expiration date of this document library file version.
	 *
	 * @param expirationDate the expiration date of this document library file version
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the extension of this document library file version.
	 *
	 * @param extension the extension of this document library file version
	 */
	@Override
	public void setExtension(String extension) {
		model.setExtension(extension);
	}

	/**
	 * Sets the extra settings of this document library file version.
	 *
	 * @param extraSettings the extra settings of this document library file version
	 */
	@Override
	public void setExtraSettings(String extraSettings) {
		model.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			extraSettingsUnicodeProperties) {

		model.setExtraSettingsProperties(extraSettingsUnicodeProperties);
	}

	/**
	 * Sets the file entry ID of this document library file version.
	 *
	 * @param fileEntryId the file entry ID of this document library file version
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the file entry type ID of this document library file version.
	 *
	 * @param fileEntryTypeId the file entry type ID of this document library file version
	 */
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		model.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	 * Sets the file name of this document library file version.
	 *
	 * @param fileName the file name of this document library file version
	 */
	@Override
	public void setFileName(String fileName) {
		model.setFileName(fileName);
	}

	/**
	 * Sets the file version ID of this document library file version.
	 *
	 * @param fileVersionId the file version ID of this document library file version
	 */
	@Override
	public void setFileVersionId(long fileVersionId) {
		model.setFileVersionId(fileVersionId);
	}

	/**
	 * Sets the folder ID of this document library file version.
	 *
	 * @param folderId the folder ID of this document library file version
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this document library file version.
	 *
	 * @param groupId the group ID of this document library file version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this document library file version.
	 *
	 * @param lastPublishDate the last publish date of this document library file version
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the mime type of this document library file version.
	 *
	 * @param mimeType the mime type of this document library file version
	 */
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	/**
	 * Sets the modified date of this document library file version.
	 *
	 * @param modifiedDate the modified date of this document library file version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this document library file version.
	 *
	 * @param mvccVersion the mvcc version of this document library file version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this document library file version.
	 *
	 * @param primaryKey the primary key of this document library file version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this document library file version.
	 *
	 * @param repositoryId the repository ID of this document library file version
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the review date of this document library file version.
	 *
	 * @param reviewDate the review date of this document library file version
	 */
	@Override
	public void setReviewDate(Date reviewDate) {
		model.setReviewDate(reviewDate);
	}

	/**
	 * Sets the size of this document library file version.
	 *
	 * @param size the size of this document library file version
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the status of this document library file version.
	 *
	 * @param status the status of this document library file version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this document library file version.
	 *
	 * @param statusByUserId the status by user ID of this document library file version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this document library file version.
	 *
	 * @param statusByUserName the status by user name of this document library file version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this document library file version.
	 *
	 * @param statusByUserUuid the status by user uuid of this document library file version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this document library file version.
	 *
	 * @param statusDate the status date of this document library file version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the title of this document library file version.
	 *
	 * @param title the title of this document library file version
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the tree path of this document library file version.
	 *
	 * @param treePath the tree path of this document library file version
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this document library file version.
	 *
	 * @param userId the user ID of this document library file version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this document library file version.
	 *
	 * @param userName the user name of this document library file version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this document library file version.
	 *
	 * @param userUuid the user uuid of this document library file version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this document library file version.
	 *
	 * @param uuid the uuid of this document library file version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this document library file version.
	 *
	 * @param version the version of this document library file version
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public Map<String, Function<DLFileVersion, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DLFileVersion, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DLFileVersionWrapper wrap(DLFileVersion dlFileVersion) {
		return new DLFileVersionWrapper(dlFileVersion);
	}

}