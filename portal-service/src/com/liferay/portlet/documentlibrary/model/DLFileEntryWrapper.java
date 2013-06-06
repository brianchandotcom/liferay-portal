/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntry
 * @generated
 */
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
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("name", getName());
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
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

	/**
	* Sets the primary key of this document library file entry.
	*
	* @param primaryKey the primary key of this document library file entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntry.setPrimaryKey(primaryKey);
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
	* Sets the uuid of this document library file entry.
	*
	* @param uuid the uuid of this document library file entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
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
	* Sets the file entry ID of this document library file entry.
	*
	* @param fileEntryId the file entry ID of this document library file entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
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
	* Returns the company ID of this document library file entry.
	*
	* @return the company ID of this document library file entry
	*/
	@Override
	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
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
	* Returns the user ID of this document library file entry.
	*
	* @return the user ID of this document library file entry
	*/
	@Override
	public long getUserId() {
		return _dlFileEntry.getUserId();
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
	* Returns the user uuid of this document library file entry.
	*
	* @return the user uuid of this document library file entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getUserUuid();
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
	* Returns the user name of this document library file entry.
	*
	* @return the user name of this document library file entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
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
	* Returns the version user ID of this document library file entry.
	*
	* @return the version user ID of this document library file entry
	*/
	@Override
	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	/**
	* Sets the version user ID of this document library file entry.
	*
	* @param versionUserId the version user ID of this document library file entry
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_dlFileEntry.setVersionUserId(versionUserId);
	}

	/**
	* Returns the version user uuid of this document library file entry.
	*
	* @return the version user uuid of this document library file entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	/**
	* Sets the version user uuid of this document library file entry.
	*
	* @param versionUserUuid the version user uuid of this document library file entry
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_dlFileEntry.setVersionUserUuid(versionUserUuid);
	}

	/**
	* Returns the version user name of this document library file entry.
	*
	* @return the version user name of this document library file entry
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	/**
	* Sets the version user name of this document library file entry.
	*
	* @param versionUserName the version user name of this document library file entry
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_dlFileEntry.setVersionUserName(versionUserName);
	}

	/**
	* Returns the create date of this document library file entry.
	*
	* @return the create date of this document library file entry
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	/**
	* Sets the create date of this document library file entry.
	*
	* @param createDate the create date of this document library file entry
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this document library file entry.
	*
	* @return the modified date of this document library file entry
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this document library file entry.
	*
	* @param modifiedDate the modified date of this document library file entry
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
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

	@Override
	public void setClassName(java.lang.String className) {
		_dlFileEntry.setClassName(className);
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
	* Sets the class name ID of this document library file entry.
	*
	* @param classNameId the class name ID of this document library file entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_dlFileEntry.setClassNameId(classNameId);
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
	* Sets the class p k of this document library file entry.
	*
	* @param classPK the class p k of this document library file entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_dlFileEntry.setClassPK(classPK);
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
	* Sets the repository ID of this document library file entry.
	*
	* @param repositoryId the repository ID of this document library file entry
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_dlFileEntry.setRepositoryId(repositoryId);
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
	* Sets the folder ID of this document library file entry.
	*
	* @param folderId the folder ID of this document library file entry
	*/
	@Override
	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
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
	* Sets the name of this document library file entry.
	*
	* @param name the name of this document library file entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
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
	* Sets the extension of this document library file entry.
	*
	* @param extension the extension of this document library file entry
	*/
	@Override
	public void setExtension(java.lang.String extension) {
		_dlFileEntry.setExtension(extension);
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
	* Sets the mime type of this document library file entry.
	*
	* @param mimeType the mime type of this document library file entry
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_dlFileEntry.setMimeType(mimeType);
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
	* Sets the title of this document library file entry.
	*
	* @param title the title of this document library file entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
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

	/**
	* Sets the description of this document library file entry.
	*
	* @param description the description of this document library file entry
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
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

	/**
	* Sets the extra settings of this document library file entry.
	*
	* @param extraSettings the extra settings of this document library file entry
	*/
	@Override
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
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
	* Sets the file entry type ID of this document library file entry.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry
	*/
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
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
	* Sets the version of this document library file entry.
	*
	* @param version the version of this document library file entry
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_dlFileEntry.setVersion(version);
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
	* Sets the size of this document library file entry.
	*
	* @param size the size of this document library file entry
	*/
	@Override
	public void setSize(long size) {
		_dlFileEntry.setSize(size);
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
	* Sets the read count of this document library file entry.
	*
	* @param readCount the read count of this document library file entry
	*/
	@Override
	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
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
	* Sets the small image ID of this document library file entry.
	*
	* @param smallImageId the small image ID of this document library file entry
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_dlFileEntry.setSmallImageId(smallImageId);
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
	* Sets the large image ID of this document library file entry.
	*
	* @param largeImageId the large image ID of this document library file entry
	*/
	@Override
	public void setLargeImageId(long largeImageId) {
		_dlFileEntry.setLargeImageId(largeImageId);
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
	* Sets the custom1 image ID of this document library file entry.
	*
	* @param custom1ImageId the custom1 image ID of this document library file entry
	*/
	@Override
	public void setCustom1ImageId(long custom1ImageId) {
		_dlFileEntry.setCustom1ImageId(custom1ImageId);
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
	* Returns the manual check in required of this document library file entry.
	*
	* @return the manual check in required of this document library file entry
	*/
	@Override
	public boolean getManualCheckInRequired() {
		return _dlFileEntry.getManualCheckInRequired();
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

	/**
	* Sets whether this document library file entry is manual check in required.
	*
	* @param manualCheckInRequired the manual check in required of this document library file entry
	*/
	@Override
	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		_dlFileEntry.setManualCheckInRequired(manualCheckInRequired);
	}

	@Override
	public boolean isNew() {
		return _dlFileEntry.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntry.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntry.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntry.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntry.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntry.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
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

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryWrapper((DLFileEntry)_dlFileEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	@Override
	public int hashCode() {
		return _dlFileEntry.hashCode();
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
	public com.liferay.portlet.documentlibrary.model.DLFileEntry toUnescapedModel() {
		return new DLFileEntryWrapper(_dlFileEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntry.persist();
	}

	@Override
	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream();
	}

	@Override
	public java.io.InputStream getContentStream(java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream(version);
	}

	@Override
	public long getDataRepositoryId() {
		return _dlFileEntry.getDataRepositoryId();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	@Override
	public java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> getFieldsMap(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFieldsMap(fileVersionId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion(version);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersions(status);
	}

	@Override
	public int getFileVersionsCount(int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersionsCount(status);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder() {
		return _dlFileEntry.getFolder();
	}

	@Override
	public java.lang.String getIcon() {
		return _dlFileEntry.getIcon();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		boolean trusted)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getLatestFileVersion(trusted);
	}

	@Override
	public com.liferay.portal.model.Lock getLock() {
		return _dlFileEntry.getLock();
	}

	@Override
	public java.lang.String getLuceneProperties() {
		return _dlFileEntry.getLuceneProperties();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFolder getTrashContainer() {
		return _dlFileEntry.getTrashContainer();
	}

	@Override
	public boolean hasLock() {
		return _dlFileEntry.hasLock();
	}

	@Override
	public boolean isCheckedOut() {
		return _dlFileEntry.isCheckedOut();
	}

	@Override
	public boolean isInHiddenFolder() {
		return _dlFileEntry.isInHiddenFolder();
	}

	@Override
	public boolean isInTrashContainer() {
		return _dlFileEntry.isInTrashContainer();
	}

	@Override
	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DLFileEntry getWrappedDLFileEntry() {
		return _dlFileEntry;
	}

	@Override
	public DLFileEntry getWrappedModel() {
		return _dlFileEntry;
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntry.resetOriginalValues();
	}

	private DLFileEntry _dlFileEntry;
}