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
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntry
 * @generated
 */
@ProviderType
public class DLFileEntryWrapper implements DLFileEntry,
	ModelWrapper<DLFileEntry> {
	public DLFileEntryWrapper(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
		attributes.put("readCount", getReadCount());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("largeImageId", getLargeImageId());
		attributes.put("custom1ImageId", getCustom1ImageId());
		attributes.put("custom2ImageId", getCustom2ImageId());
		attributes.put("manualCheckInRequired", getManualCheckInRequired());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Integer readCount = (Integer)attributes.get("readCount");

		if (readCount != null) {
			setReadCount(readCount);
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
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryWrapper((DLFileEntry)_dlFileEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	/**
	* Returns the fully qualified class name of this document library file entry.
	*
	* @return the fully qualified class name of this document library file entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _dlFileEntry.getClassName();
	}

	/**
	* Returns the class name ID of this document library file entry.
	*
	* @return the class name ID of this document library file entry
	*/
	@Override
	public long getClassNameId() {
		return _dlFileEntry.getClassNameId();
	}

	/**
	* Returns the class p k of this document library file entry.
	*
	* @return the class p k of this document library file entry
	*/
	@Override
	public long getClassPK() {
		return _dlFileEntry.getClassPK();
	}

	/**
	* Returns the company ID of this document library file entry.
	*
	* @return the company ID of this document library file entry
	*/
	@Override
	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	@Override
	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getContentStream();
	}

	@Override
	public java.io.InputStream getContentStream(java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getContentStream(version);
	}

	/**
	* Returns the create date of this document library file entry.
	*
	* @return the create date of this document library file entry
	*/
	@Override
	public Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	/**
	* Returns the custom1 image ID of this document library file entry.
	*
	* @return the custom1 image ID of this document library file entry
	*/
	@Override
	public long getCustom1ImageId() {
		return _dlFileEntry.getCustom1ImageId();
	}

	/**
	* Returns the custom2 image ID of this document library file entry.
	*
	* @return the custom2 image ID of this document library file entry
	*/
	@Override
	public long getCustom2ImageId() {
		return _dlFileEntry.getCustom2ImageId();
	}

	@Override
	public Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> getDDMFormValuesMap(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getDDMFormValuesMap(fileVersionId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getDLFileEntryType();
	}

	@Override
	public long getDataRepositoryId() {
		return _dlFileEntry.getDataRepositoryId();
	}

	/**
	* Returns the description of this document library file entry.
	*
	* @return the description of this document library file entry
	*/
	@Override
	public java.lang.String getDescription() {
		return _dlFileEntry.getDescription();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
	}

	/**
	* Returns the extension of this document library file entry.
	*
	* @return the extension of this document library file entry
	*/
	@Override
	public java.lang.String getExtension() {
		return _dlFileEntry.getExtension();
	}

	/**
	* Returns the extra settings of this document library file entry.
	*
	* @return the extra settings of this document library file entry
	*/
	@Override
	public java.lang.String getExtraSettings() {
		return _dlFileEntry.getExtraSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	/**
	* Returns the file entry ID of this document library file entry.
	*
	* @return the file entry ID of this document library file entry
	*/
	@Override
	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	/**
	* Returns the file entry type ID of this document library file entry.
	*
	* @return the file entry type ID of this document library file entry
	*/
	@Override
	public long getFileEntryTypeId() {
		return _dlFileEntry.getFileEntryTypeId();
	}

	/**
	* Returns the file name of this document library file entry.
	*
	* @return the file name of this document library file entry
	*/
	@Override
	public java.lang.String getFileName() {
		return _dlFileEntry.getFileName();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts() {
		return _dlFileEntry.getFileShortcuts();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getFileVersion();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getFileVersion(version);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		int status) {
		return _dlFileEntry.getFileVersions(status);
	}

	@Override
	public int getFileVersionsCount(int status) {
		return _dlFileEntry.getFileVersionsCount(status);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getFolder();
	}

	/**
	* Returns the folder ID of this document library file entry.
	*
	* @return the folder ID of this document library file entry
	*/
	@Override
	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	/**
	* Returns the group ID of this document library file entry.
	*
	* @return the group ID of this document library file entry
	*/
	@Override
	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	@Override
	public java.lang.String getIcon() {
		return _dlFileEntry.getIcon();
	}

	@Override
	public java.lang.String getIconCssClass() {
		return _dlFileEntry.getIconCssClass();
	}

	/**
	* Returns the large image ID of this document library file entry.
	*
	* @return the large image ID of this document library file entry
	*/
	@Override
	public long getLargeImageId() {
		return _dlFileEntry.getLargeImageId();
	}

	/**
	* Returns the last publish date of this document library file entry.
	*
	* @return the last publish date of this document library file entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _dlFileEntry.getLastPublishDate();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		boolean trusted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getLatestFileVersion(trusted);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock getLock() {
		return _dlFileEntry.getLock();
	}

	@Override
	public java.lang.String getLuceneProperties() {
		return _dlFileEntry.getLuceneProperties();
	}

	/**
	* Returns the manual check in required of this document library file entry.
	*
	* @return the manual check in required of this document library file entry
	*/
	@Override
	public boolean getManualCheckInRequired() {
		return _dlFileEntry.getManualCheckInRequired();
	}

	/**
	* Returns the mime type of this document library file entry.
	*
	* @return the mime type of this document library file entry
	*/
	@Override
	public java.lang.String getMimeType() {
		return _dlFileEntry.getMimeType();
	}

	/**
	* Returns the modified date of this document library file entry.
	*
	* @return the modified date of this document library file entry
	*/
	@Override
	public Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	/**
	* Returns the name of this document library file entry.
	*
	* @return the name of this document library file entry
	*/
	@Override
	public java.lang.String getName() {
		return _dlFileEntry.getName();
	}

	/**
	* Returns the primary key of this document library file entry.
	*
	* @return the primary key of this document library file entry
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the read count of this document library file entry.
	*
	* @return the read count of this document library file entry
	*/
	@Override
	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	/**
	* Returns the repository ID of this document library file entry.
	*
	* @return the repository ID of this document library file entry
	*/
	@Override
	public long getRepositoryId() {
		return _dlFileEntry.getRepositoryId();
	}

	/**
	* Returns the size of this document library file entry.
	*
	* @return the size of this document library file entry
	*/
	@Override
	public long getSize() {
		return _dlFileEntry.getSize();
	}

	/**
	* Returns the small image ID of this document library file entry.
	*
	* @return the small image ID of this document library file entry
	*/
	@Override
	public long getSmallImageId() {
		return _dlFileEntry.getSmallImageId();
	}

	/**
	* Returns the status of this document library file entry.
	*
	* @return the status of this document library file entry
	*/
	@Override
	public int getStatus() {
		return _dlFileEntry.getStatus();
	}

	/**
	* Returns the title of this document library file entry.
	*
	* @return the title of this document library file entry
	*/
	@Override
	public java.lang.String getTitle() {
		return _dlFileEntry.getTitle();
	}

	/**
	* Returns the trash entry created when this document library file entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this document library file entry.
	*
	* @return the trash entry created when this document library file entry was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.portlet.trash.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntry.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this document library file entry.
	*
	* @return the class primary key of the trash entry for this document library file entry
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _dlFileEntry.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this document library file entry.
	*
	* @return the trash handler for this document library file entry
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _dlFileEntry.getTrashHandler();
	}

	/**
	* Returns the tree path of this document library file entry.
	*
	* @return the tree path of this document library file entry
	*/
	@Override
	public java.lang.String getTreePath() {
		return _dlFileEntry.getTreePath();
	}

	/**
	* Returns the user ID of this document library file entry.
	*
	* @return the user ID of this document library file entry
	*/
	@Override
	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	/**
	* Returns the user name of this document library file entry.
	*
	* @return the user name of this document library file entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
	}

	/**
	* Returns the user uuid of this document library file entry.
	*
	* @return the user uuid of this document library file entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _dlFileEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this document library file entry.
	*
	* @return the uuid of this document library file entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileEntry.getUuid();
	}

	/**
	* Returns the version of this document library file entry.
	*
	* @return the version of this document library file entry
	*/
	@Override
	public java.lang.String getVersion() {
		return _dlFileEntry.getVersion();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserId()}
	*/
	@Deprecated
	@Override
	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserName()}
	*/
	@Deprecated
	@Override
	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserUuid()}
	*/
	@Deprecated
	@Override
	public java.lang.String getVersionUserUuid() {
		return _dlFileEntry.getVersionUserUuid();
	}

	@Override
	public boolean hasLock() {
		return _dlFileEntry.hasLock();
	}

	@Override
	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntry.isCachedModel();
	}

	@Override
	public boolean isCheckedOut() {
		return _dlFileEntry.isCheckedOut();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntry.isEscapedModel();
	}

	@Override
	public boolean isInHiddenFolder() {
		return _dlFileEntry.isInHiddenFolder();
	}

	/**
	* Returns <code>true</code> if this document library file entry is in the Recycle Bin.
	*
	* @return <code>true</code> if this document library file entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _dlFileEntry.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this document library file entry is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this document library file entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _dlFileEntry.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _dlFileEntry.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _dlFileEntry.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this document library file entry is manual check in required.
	*
	* @return <code>true</code> if this document library file entry is manual check in required; <code>false</code> otherwise
	*/
	@Override
	public boolean isManualCheckInRequired() {
		return _dlFileEntry.isManualCheckInRequired();
	}

	@Override
	public boolean isNew() {
		return _dlFileEntry.isNew();
	}

	@Override
	public void persist() {
		_dlFileEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_dlFileEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this document library file entry.
	*
	* @param classNameId the class name ID of this document library file entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_dlFileEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this document library file entry.
	*
	* @param classPK the class p k of this document library file entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_dlFileEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this document library file entry.
	*
	* @param companyId the company ID of this document library file entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this document library file entry.
	*
	* @param createDate the create date of this document library file entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	/**
	* Sets the custom1 image ID of this document library file entry.
	*
	* @param custom1ImageId the custom1 image ID of this document library file entry
	*/
	@Override
	public void setCustom1ImageId(long custom1ImageId) {
		_dlFileEntry.setCustom1ImageId(custom1ImageId);
	}

	/**
	* Sets the custom2 image ID of this document library file entry.
	*
	* @param custom2ImageId the custom2 image ID of this document library file entry
	*/
	@Override
	public void setCustom2ImageId(long custom2ImageId) {
		_dlFileEntry.setCustom2ImageId(custom2ImageId);
	}

	/**
	* Sets the description of this document library file entry.
	*
	* @param description the description of this document library file entry
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFileEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlFileEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the extension of this document library file entry.
	*
	* @param extension the extension of this document library file entry
	*/
	@Override
	public void setExtension(java.lang.String extension) {
		_dlFileEntry.setExtension(extension);
	}

	/**
	* Sets the extra settings of this document library file entry.
	*
	* @param extraSettings the extra settings of this document library file entry
	*/
	@Override
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);
	}

	/**
	* Sets the file entry ID of this document library file entry.
	*
	* @param fileEntryId the file entry ID of this document library file entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the file entry type ID of this document library file entry.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry
	*/
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Sets the file name of this document library file entry.
	*
	* @param fileName the file name of this document library file entry
	*/
	@Override
	public void setFileName(java.lang.String fileName) {
		_dlFileEntry.setFileName(fileName);
	}

	/**
	* Sets the folder ID of this document library file entry.
	*
	* @param folderId the folder ID of this document library file entry
	*/
	@Override
	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
	}

	/**
	* Sets the group ID of this document library file entry.
	*
	* @param groupId the group ID of this document library file entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	/**
	* Sets the large image ID of this document library file entry.
	*
	* @param largeImageId the large image ID of this document library file entry
	*/
	@Override
	public void setLargeImageId(long largeImageId) {
		_dlFileEntry.setLargeImageId(largeImageId);
	}

	/**
	* Sets the last publish date of this document library file entry.
	*
	* @param lastPublishDate the last publish date of this document library file entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFileEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets whether this document library file entry is manual check in required.
	*
	* @param manualCheckInRequired the manual check in required of this document library file entry
	*/
	@Override
	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		_dlFileEntry.setManualCheckInRequired(manualCheckInRequired);
	}

	/**
	* Sets the mime type of this document library file entry.
	*
	* @param mimeType the mime type of this document library file entry
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_dlFileEntry.setMimeType(mimeType);
	}

	/**
	* Sets the modified date of this document library file entry.
	*
	* @param modifiedDate the modified date of this document library file entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this document library file entry.
	*
	* @param name the name of this document library file entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntry.setNew(n);
	}

	/**
	* Sets the primary key of this document library file entry.
	*
	* @param primaryKey the primary key of this document library file entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the read count of this document library file entry.
	*
	* @param readCount the read count of this document library file entry
	*/
	@Override
	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
	}

	/**
	* Sets the repository ID of this document library file entry.
	*
	* @param repositoryId the repository ID of this document library file entry
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlFileEntry.setRepositoryId(repositoryId);
	}

	/**
	* Sets the size of this document library file entry.
	*
	* @param size the size of this document library file entry
	*/
	@Override
	public void setSize(long size) {
		_dlFileEntry.setSize(size);
	}

	/**
	* Sets the small image ID of this document library file entry.
	*
	* @param smallImageId the small image ID of this document library file entry
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_dlFileEntry.setSmallImageId(smallImageId);
	}

	/**
	* Sets the title of this document library file entry.
	*
	* @param title the title of this document library file entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
	}

	/**
	* Sets the tree path of this document library file entry.
	*
	* @param treePath the tree path of this document library file entry
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_dlFileEntry.setTreePath(treePath);
	}

	/**
	* Sets the user ID of this document library file entry.
	*
	* @param userId the user ID of this document library file entry
	*/
	@Override
	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this document library file entry.
	*
	* @param userName the user name of this document library file entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFileEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this document library file entry.
	*
	* @param userUuid the user uuid of this document library file entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this document library file entry.
	*
	* @param uuid the uuid of this document library file entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	/**
	* Sets the version of this document library file entry.
	*
	* @param version the version of this document library file entry
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_dlFileEntry.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileEntry> toCacheModel() {
		return _dlFileEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntry toEscapedModel() {
		return new DLFileEntryWrapper(_dlFileEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntry toUnescapedModel() {
		return new DLFileEntryWrapper(_dlFileEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_dlFileEntry.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryWrapper)) {
			return false;
		}

		DLFileEntryWrapper dlFileEntryWrapper = (DLFileEntryWrapper)obj;

		if (Validator.equals(_dlFileEntry, dlFileEntryWrapper._dlFileEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFileEntry.getStagedModelType();
	}

	@Override
	public DLFileEntry getWrappedModel() {
		return _dlFileEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntry.resetOriginalValues();
	}

	private final DLFileEntry _dlFileEntry;
}