/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntry
 * @generated
 */
public class DLFileEntryWrapper implements DLFileEntry,
	ModelWrapper<DLFileEntry> {
	public DLFileEntryWrapper(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	public Class<?> getModelClass() {
		return DLFileEntry.class;
	}

	public String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	/**
	* Returns the primary key of this document library file entry.
	*
	* @return the primary key of this document library file entry
	*/
	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this document library file entry.
	*
	* @param primaryKey the primary key of this document library file entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this document library file entry.
	*
	* @return the uuid of this document library file entry
	*/
	public java.lang.String getUuid() {
		return _dlFileEntry.getUuid();
	}

	/**
	* Sets the uuid of this document library file entry.
	*
	* @param uuid the uuid of this document library file entry
	*/
	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	/**
	* Returns the file entry ID of this document library file entry.
	*
	* @return the file entry ID of this document library file entry
	*/
	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	/**
	* Sets the file entry ID of this document library file entry.
	*
	* @param fileEntryId the file entry ID of this document library file entry
	*/
	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Returns the group ID of this document library file entry.
	*
	* @return the group ID of this document library file entry
	*/
	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	/**
	* Sets the group ID of this document library file entry.
	*
	* @param groupId the group ID of this document library file entry
	*/
	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this document library file entry.
	*
	* @return the company ID of this document library file entry
	*/
	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this document library file entry.
	*
	* @param companyId the company ID of this document library file entry
	*/
	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this document library file entry.
	*
	* @return the user ID of this document library file entry
	*/
	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	/**
	* Sets the user ID of this document library file entry.
	*
	* @param userId the user ID of this document library file entry
	*/
	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this document library file entry.
	*
	* @return the user uuid of this document library file entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this document library file entry.
	*
	* @param userUuid the user uuid of this document library file entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this document library file entry.
	*
	* @return the user name of this document library file entry
	*/
	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
	}

	/**
	* Sets the user name of this document library file entry.
	*
	* @param userName the user name of this document library file entry
	*/
	public void setUserName(java.lang.String userName) {
		_dlFileEntry.setUserName(userName);
	}

	/**
	* Returns the version user ID of this document library file entry.
	*
	* @return the version user ID of this document library file entry
	*/
	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	/**
	* Sets the version user ID of this document library file entry.
	*
	* @param versionUserId the version user ID of this document library file entry
	*/
	public void setVersionUserId(long versionUserId) {
		_dlFileEntry.setVersionUserId(versionUserId);
	}

	/**
	* Returns the version user uuid of this document library file entry.
	*
	* @return the version user uuid of this document library file entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	/**
	* Sets the version user uuid of this document library file entry.
	*
	* @param versionUserUuid the version user uuid of this document library file entry
	*/
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_dlFileEntry.setVersionUserUuid(versionUserUuid);
	}

	/**
	* Returns the version user name of this document library file entry.
	*
	* @return the version user name of this document library file entry
	*/
	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	/**
	* Sets the version user name of this document library file entry.
	*
	* @param versionUserName the version user name of this document library file entry
	*/
	public void setVersionUserName(java.lang.String versionUserName) {
		_dlFileEntry.setVersionUserName(versionUserName);
	}

	/**
	* Returns the create date of this document library file entry.
	*
	* @return the create date of this document library file entry
	*/
	public java.util.Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	/**
	* Sets the create date of this document library file entry.
	*
	* @param createDate the create date of this document library file entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this document library file entry.
	*
	* @return the modified date of this document library file entry
	*/
	public java.util.Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this document library file entry.
	*
	* @param modifiedDate the modified date of this document library file entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the repository ID of this document library file entry.
	*
	* @return the repository ID of this document library file entry
	*/
	public long getRepositoryId() {
		return _dlFileEntry.getRepositoryId();
	}

	/**
	* Sets the repository ID of this document library file entry.
	*
	* @param repositoryId the repository ID of this document library file entry
	*/
	public void setRepositoryId(long repositoryId) {
		_dlFileEntry.setRepositoryId(repositoryId);
	}

	/**
	* Returns the folder ID of this document library file entry.
	*
	* @return the folder ID of this document library file entry
	*/
	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	/**
	* Sets the folder ID of this document library file entry.
	*
	* @param folderId the folder ID of this document library file entry
	*/
	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
	}

	/**
	* Returns the name of this document library file entry.
	*
	* @return the name of this document library file entry
	*/
	public java.lang.String getName() {
		return _dlFileEntry.getName();
	}

	/**
	* Sets the name of this document library file entry.
	*
	* @param name the name of this document library file entry
	*/
	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
	}

	/**
	* Returns the extension of this document library file entry.
	*
	* @return the extension of this document library file entry
	*/
	public java.lang.String getExtension() {
		return _dlFileEntry.getExtension();
	}

	/**
	* Sets the extension of this document library file entry.
	*
	* @param extension the extension of this document library file entry
	*/
	public void setExtension(java.lang.String extension) {
		_dlFileEntry.setExtension(extension);
	}

	/**
	* Returns the mime type of this document library file entry.
	*
	* @return the mime type of this document library file entry
	*/
	public java.lang.String getMimeType() {
		return _dlFileEntry.getMimeType();
	}

	/**
	* Sets the mime type of this document library file entry.
	*
	* @param mimeType the mime type of this document library file entry
	*/
	public void setMimeType(java.lang.String mimeType) {
		_dlFileEntry.setMimeType(mimeType);
	}

	/**
	* Returns the title of this document library file entry.
	*
	* @return the title of this document library file entry
	*/
	public java.lang.String getTitle() {
		return _dlFileEntry.getTitle();
	}

	/**
	* Sets the title of this document library file entry.
	*
	* @param title the title of this document library file entry
	*/
	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
	}

	/**
	* Returns the description of this document library file entry.
	*
	* @return the description of this document library file entry
	*/
	public java.lang.String getDescription() {
		return _dlFileEntry.getDescription();
	}

	/**
	* Sets the description of this document library file entry.
	*
	* @param description the description of this document library file entry
	*/
	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
	}

	/**
	* Returns the extra settings of this document library file entry.
	*
	* @return the extra settings of this document library file entry
	*/
	public java.lang.String getExtraSettings() {
		return _dlFileEntry.getExtraSettings();
	}

	/**
	* Sets the extra settings of this document library file entry.
	*
	* @param extraSettings the extra settings of this document library file entry
	*/
	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
	}

	/**
	* Returns the file entry type ID of this document library file entry.
	*
	* @return the file entry type ID of this document library file entry
	*/
	public long getFileEntryTypeId() {
		return _dlFileEntry.getFileEntryTypeId();
	}

	/**
	* Sets the file entry type ID of this document library file entry.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry
	*/
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns the version of this document library file entry.
	*
	* @return the version of this document library file entry
	*/
	public java.lang.String getVersion() {
		return _dlFileEntry.getVersion();
	}

	/**
	* Sets the version of this document library file entry.
	*
	* @param version the version of this document library file entry
	*/
	public void setVersion(java.lang.String version) {
		_dlFileEntry.setVersion(version);
	}

	/**
	* Returns the size of this document library file entry.
	*
	* @return the size of this document library file entry
	*/
	public long getSize() {
		return _dlFileEntry.getSize();
	}

	/**
	* Sets the size of this document library file entry.
	*
	* @param size the size of this document library file entry
	*/
	public void setSize(long size) {
		_dlFileEntry.setSize(size);
	}

	/**
	* Returns the read count of this document library file entry.
	*
	* @return the read count of this document library file entry
	*/
	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	/**
	* Sets the read count of this document library file entry.
	*
	* @param readCount the read count of this document library file entry
	*/
	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
	}

	/**
	* Returns the small image ID of this document library file entry.
	*
	* @return the small image ID of this document library file entry
	*/
	public long getSmallImageId() {
		return _dlFileEntry.getSmallImageId();
	}

	/**
	* Sets the small image ID of this document library file entry.
	*
	* @param smallImageId the small image ID of this document library file entry
	*/
	public void setSmallImageId(long smallImageId) {
		_dlFileEntry.setSmallImageId(smallImageId);
	}

	/**
	* Returns the large image ID of this document library file entry.
	*
	* @return the large image ID of this document library file entry
	*/
	public long getLargeImageId() {
		return _dlFileEntry.getLargeImageId();
	}

	/**
	* Sets the large image ID of this document library file entry.
	*
	* @param largeImageId the large image ID of this document library file entry
	*/
	public void setLargeImageId(long largeImageId) {
		_dlFileEntry.setLargeImageId(largeImageId);
	}

	/**
	* Returns the custom1 image ID of this document library file entry.
	*
	* @return the custom1 image ID of this document library file entry
	*/
	public long getCustom1ImageId() {
		return _dlFileEntry.getCustom1ImageId();
	}

	/**
	* Sets the custom1 image ID of this document library file entry.
	*
	* @param custom1ImageId the custom1 image ID of this document library file entry
	*/
	public void setCustom1ImageId(long custom1ImageId) {
		_dlFileEntry.setCustom1ImageId(custom1ImageId);
	}

	/**
	* Returns the custom2 image ID of this document library file entry.
	*
	* @return the custom2 image ID of this document library file entry
	*/
	public long getCustom2ImageId() {
		return _dlFileEntry.getCustom2ImageId();
	}

	/**
	* Sets the custom2 image ID of this document library file entry.
	*
	* @param custom2ImageId the custom2 image ID of this document library file entry
	*/
	public void setCustom2ImageId(long custom2ImageId) {
		_dlFileEntry.setCustom2ImageId(custom2ImageId);
	}

	public boolean isNew() {
		return _dlFileEntry.isNew();
	}

	public void setNew(boolean n) {
		_dlFileEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryWrapper((DLFileEntry)_dlFileEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	@Override
	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileEntry> toCacheModel() {
		return _dlFileEntry.toCacheModel();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry toEscapedModel() {
		return new DLFileEntryWrapper(_dlFileEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntry.persist();
	}

	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream();
	}

	public java.io.InputStream getContentStream(java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getContentStream(version);
	}

	public long getDataRepositoryId() {
		return _dlFileEntry.getDataRepositoryId();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	public java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> getFieldsMap(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFieldsMap(fileVersionId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersion(version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getFileVersions(status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder() {
		return _dlFileEntry.getFolder();
	}

	public java.lang.String getIcon() {
		return _dlFileEntry.getIcon();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		boolean trusted)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntry.getLatestFileVersion(trusted);
	}

	public com.liferay.portal.model.Lock getLock() {
		return _dlFileEntry.getLock();
	}

	public java.lang.String getLuceneProperties() {
		return _dlFileEntry.getLuceneProperties();
	}

	public boolean hasLock() {
		return _dlFileEntry.hasLock();
	}

	public boolean isCheckedOut() {
		return _dlFileEntry.isCheckedOut();
	}

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties) {
		_dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);
	}

	public void setFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		_dlFileEntry.setFileVersion(dlFileVersion);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DLFileEntry getWrappedDLFileEntry() {
		return _dlFileEntry;
	}

	public DLFileEntry getWrappedModel() {
		return _dlFileEntry;
	}

	public void resetOriginalValues() {
		_dlFileEntry.resetOriginalValues();
	}

	private DLFileEntry _dlFileEntry;
}