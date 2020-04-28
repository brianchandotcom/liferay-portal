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
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntry
 * @generated
 */
public class DLFileEntryWrapper
	extends BaseModelWrapper<DLFileEntry>
	implements DLFileEntry, ModelWrapper<DLFileEntry> {

	public DLFileEntryWrapper(DLFileEntry dlFileEntry) {
		super(dlFileEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("fileName", getFileName());
		attributes.put("extension", getExtension());
		attributes.put("mimeType", getMimeType());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("extraSettings", getExtraSettings());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("version", getVersion());
		attributes.put("size", getSize());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("largeImageId", getLargeImageId());
		attributes.put("custom1ImageId", getCustom1ImageId());
		attributes.put("custom2ImageId", getCustom2ImageId());
		attributes.put("manualCheckInRequired", isManualCheckInRequired());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
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

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		Long largeImageId = (Long)attributes.get("largeImageId");

		if (largeImageId != null) {
			setLargeImageId(largeImageId);
		}

		Long custom1ImageId = (Long)attributes.get("custom1ImageId");

		if (custom1ImageId != null) {
			setCustom1ImageId(custom1ImageId);
		}

		Long custom2ImageId = (Long)attributes.get("custom2ImageId");

		if (custom2ImageId != null) {
			setCustom2ImageId(custom2ImageId);
		}

		Boolean manualCheckInRequired = (Boolean)attributes.get(
			"manualCheckInRequired");

		if (manualCheckInRequired != null) {
			setManualCheckInRequired(manualCheckInRequired);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	/**
	 * Returns the fully qualified class name of this document library file entry.
	 *
	 * @return the fully qualified class name of this document library file entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this document library file entry.
	 *
	 * @return the class name ID of this document library file entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this document library file entry.
	 *
	 * @return the class pk of this document library file entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this document library file entry.
	 *
	 * @return the company ID of this document library file entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getContentStream();
	}

	@Override
	public java.io.InputStream getContentStream(String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getContentStream(version);
	}

	/**
	 * Returns the create date of this document library file entry.
	 *
	 * @return the create date of this document library file entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this document library file entry.
	 *
	 * @return the ct collection ID of this document library file entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the custom1 image ID of this document library file entry.
	 *
	 * @return the custom1 image ID of this document library file entry
	 */
	@Override
	public long getCustom1ImageId() {
		return model.getCustom1ImageId();
	}

	/**
	 * Returns the custom2 image ID of this document library file entry.
	 *
	 * @return the custom2 image ID of this document library file entry
	 */
	@Override
	public long getCustom2ImageId() {
		return model.getCustom2ImageId();
	}

	@Override
	public long getDataRepositoryId() {
		return model.getDataRepositoryId();
	}

	@Override
	public Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
			getDDMFormValuesMap(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMFormValuesMap(fileVersionId);
	}

	/**
	 * Returns the description of this document library file entry.
	 *
	 * @return the description of this document library file entry
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
	 * Returns the extension of this document library file entry.
	 *
	 * @return the extension of this document library file entry
	 */
	@Override
	public String getExtension() {
		return model.getExtension();
	}

	/**
	 * Returns the extra settings of this document library file entry.
	 *
	 * @return the extra settings of this document library file entry
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

	/**
	 * Returns the file entry ID of this document library file entry.
	 *
	 * @return the file entry ID of this document library file entry
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the file entry type ID of this document library file entry.
	 *
	 * @return the file entry type ID of this document library file entry
	 */
	@Override
	public long getFileEntryTypeId() {
		return model.getFileEntryTypeId();
	}

	/**
	 * Returns the file name of this document library file entry.
	 *
	 * @return the file name of this document library file entry
	 */
	@Override
	public String getFileName() {
		return model.getFileName();
	}

	@Override
	public java.util.List<DLFileShortcut> getFileShortcuts() {
		return model.getFileShortcuts();
	}

	@Override
	public DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileVersion();
	}

	@Override
	public DLFileVersion getFileVersion(String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileVersion(version);
	}

	@Override
	public java.util.List<DLFileVersion> getFileVersions(int status) {
		return model.getFileVersions(status);
	}

	@Override
	public int getFileVersionsCount(int status) {
		return model.getFileVersionsCount(status);
	}

	@Override
	public DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this document library file entry.
	 *
	 * @return the folder ID of this document library file entry
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the group ID of this document library file entry.
	 *
	 * @return the group ID of this document library file entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public String getIcon() {
		return model.getIcon();
	}

	@Override
	public String getIconCssClass() {
		return model.getIconCssClass();
	}

	/**
	 * Returns the large image ID of this document library file entry.
	 *
	 * @return the large image ID of this document library file entry
	 */
	@Override
	public long getLargeImageId() {
		return model.getLargeImageId();
	}

	/**
	 * Returns the last publish date of this document library file entry.
	 *
	 * @return the last publish date of this document library file entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public DLFileVersion getLatestFileVersion(boolean trusted)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLatestFileVersion(trusted);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock getLock() {
		return model.getLock();
	}

	@Override
	public String getLuceneProperties() {
		return model.getLuceneProperties();
	}

	/**
	 * Returns the manual check in required of this document library file entry.
	 *
	 * @return the manual check in required of this document library file entry
	 */
	@Override
	public boolean getManualCheckInRequired() {
		return model.getManualCheckInRequired();
	}

	/**
	 * Returns the mime type of this document library file entry.
	 *
	 * @return the mime type of this document library file entry
	 */
	@Override
	public String getMimeType() {
		return model.getMimeType();
	}

	/**
	 * Returns the modified date of this document library file entry.
	 *
	 * @return the modified date of this document library file entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this document library file entry.
	 *
	 * @return the mvcc version of this document library file entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this document library file entry.
	 *
	 * @return the name of this document library file entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this document library file entry.
	 *
	 * @return the primary key of this document library file entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public long getReadCount() {
		return model.getReadCount();
	}

	/**
	 * Returns the repository ID of this document library file entry.
	 *
	 * @return the repository ID of this document library file entry
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the size of this document library file entry.
	 *
	 * @return the size of this document library file entry
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the small image ID of this document library file entry.
	 *
	 * @return the small image ID of this document library file entry
	 */
	@Override
	public long getSmallImageId() {
		return model.getSmallImageId();
	}

	/**
	 * Returns the status of this document library file entry.
	 *
	 * @return the status of this document library file entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the title of this document library file entry.
	 *
	 * @return the title of this document library file entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the trash entry created when this document library file entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this document library file entry.
	 *
	 * @return the trash entry created when this document library file entry was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this document library file entry.
	 *
	 * @return the class primary key of the trash entry for this document library file entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this document library file entry.
	 *
	 * @return the trash handler for this document library file entry
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the tree path of this document library file entry.
	 *
	 * @return the tree path of this document library file entry
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the user ID of this document library file entry.
	 *
	 * @return the user ID of this document library file entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this document library file entry.
	 *
	 * @return the user name of this document library file entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this document library file entry.
	 *
	 * @return the user uuid of this document library file entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this document library file entry.
	 *
	 * @return the uuid of this document library file entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this document library file entry.
	 *
	 * @return the version of this document library file entry
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public boolean hasLock() {
		return model.hasLock();
	}

	@Override
	public boolean isCheckedOut() {
		return model.isCheckedOut();
	}

	@Override
	public boolean isInHiddenFolder() {
		return model.isInHiddenFolder();
	}

	/**
	 * Returns <code>true</code> if this document library file entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this document library file entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this document library file entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this document library file entry is in the Recycle Bin; <code>false</code> otherwise
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

	/**
	 * Returns <code>true</code> if this document library file entry is manual check in required.
	 *
	 * @return <code>true</code> if this document library file entry is manual check in required; <code>false</code> otherwise
	 */
	@Override
	public boolean isManualCheckInRequired() {
		return model.isManualCheckInRequired();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this document library file entry.
	 *
	 * @param classNameId the class name ID of this document library file entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this document library file entry.
	 *
	 * @param classPK the class pk of this document library file entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this document library file entry.
	 *
	 * @param companyId the company ID of this document library file entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this document library file entry.
	 *
	 * @param createDate the create date of this document library file entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this document library file entry.
	 *
	 * @param ctCollectionId the ct collection ID of this document library file entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the custom1 image ID of this document library file entry.
	 *
	 * @param custom1ImageId the custom1 image ID of this document library file entry
	 */
	@Override
	public void setCustom1ImageId(long custom1ImageId) {
		model.setCustom1ImageId(custom1ImageId);
	}

	/**
	 * Sets the custom2 image ID of this document library file entry.
	 *
	 * @param custom2ImageId the custom2 image ID of this document library file entry
	 */
	@Override
	public void setCustom2ImageId(long custom2ImageId) {
		model.setCustom2ImageId(custom2ImageId);
	}

	/**
	 * Sets the description of this document library file entry.
	 *
	 * @param description the description of this document library file entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the extension of this document library file entry.
	 *
	 * @param extension the extension of this document library file entry
	 */
	@Override
	public void setExtension(String extension) {
		model.setExtension(extension);
	}

	/**
	 * Sets the extra settings of this document library file entry.
	 *
	 * @param extraSettings the extra settings of this document library file entry
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
	 * Sets the file entry ID of this document library file entry.
	 *
	 * @param fileEntryId the file entry ID of this document library file entry
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the file entry type ID of this document library file entry.
	 *
	 * @param fileEntryTypeId the file entry type ID of this document library file entry
	 */
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		model.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	 * Sets the file name of this document library file entry.
	 *
	 * @param fileName the file name of this document library file entry
	 */
	@Override
	public void setFileName(String fileName) {
		model.setFileName(fileName);
	}

	/**
	 * Sets the folder ID of this document library file entry.
	 *
	 * @param folderId the folder ID of this document library file entry
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this document library file entry.
	 *
	 * @param groupId the group ID of this document library file entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the large image ID of this document library file entry.
	 *
	 * @param largeImageId the large image ID of this document library file entry
	 */
	@Override
	public void setLargeImageId(long largeImageId) {
		model.setLargeImageId(largeImageId);
	}

	/**
	 * Sets the last publish date of this document library file entry.
	 *
	 * @param lastPublishDate the last publish date of this document library file entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets whether this document library file entry is manual check in required.
	 *
	 * @param manualCheckInRequired the manual check in required of this document library file entry
	 */
	@Override
	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		model.setManualCheckInRequired(manualCheckInRequired);
	}

	/**
	 * Sets the mime type of this document library file entry.
	 *
	 * @param mimeType the mime type of this document library file entry
	 */
	@Override
	public void setMimeType(String mimeType) {
		model.setMimeType(mimeType);
	}

	/**
	 * Sets the modified date of this document library file entry.
	 *
	 * @param modifiedDate the modified date of this document library file entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this document library file entry.
	 *
	 * @param mvccVersion the mvcc version of this document library file entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this document library file entry.
	 *
	 * @param name the name of this document library file entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this document library file entry.
	 *
	 * @param primaryKey the primary key of this document library file entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this document library file entry.
	 *
	 * @param repositoryId the repository ID of this document library file entry
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the size of this document library file entry.
	 *
	 * @param size the size of this document library file entry
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the small image ID of this document library file entry.
	 *
	 * @param smallImageId the small image ID of this document library file entry
	 */
	@Override
	public void setSmallImageId(long smallImageId) {
		model.setSmallImageId(smallImageId);
	}

	/**
	 * Sets the title of this document library file entry.
	 *
	 * @param title the title of this document library file entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the tree path of this document library file entry.
	 *
	 * @param treePath the tree path of this document library file entry
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this document library file entry.
	 *
	 * @param userId the user ID of this document library file entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this document library file entry.
	 *
	 * @param userName the user name of this document library file entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this document library file entry.
	 *
	 * @param userUuid the user uuid of this document library file entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this document library file entry.
	 *
	 * @param uuid the uuid of this document library file entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this document library file entry.
	 *
	 * @param version the version of this document library file entry
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
	public Map<String, Function<DLFileEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DLFileEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DLFileEntryWrapper wrap(DLFileEntry dlFileEntry) {
		return new DLFileEntryWrapper(dlFileEntry);
	}

}