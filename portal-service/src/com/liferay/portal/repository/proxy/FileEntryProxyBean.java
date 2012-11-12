/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.proxy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mika Koivisto
 */
public class FileEntryProxyBean
	extends RepositoryModelProxyBean implements FileEntry {

	public FileEntryProxyBean(FileEntry fileEntry, ClassLoader classLoader) {
		super(classLoader);

		_fileEntry = fileEntry;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return _fileEntry.containsPermission(permissionChecker, actionId);
	}

	public Map<String, Serializable> getAttributes() {
		return _fileEntry.getAttributes();
	}

	public long getCompanyId() {
		return _fileEntry.getCompanyId();
	}

	public InputStream getContentStream()
		throws PortalException, SystemException {

		return _fileEntry.getContentStream();
	}

	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		return _fileEntry.getContentStream(version);
	}

	public Date getCreateDate() {
		return _fileEntry.getCreateDate();
	}

	public String getDescription() {
		return _fileEntry.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		ExpandoBridge expandoBridge = _fileEntry.getExpandoBridge();

		return (ExpandoBridge)newProxyInstance(
			expandoBridge, ExpandoBridge.class);
	}

	public String getExtension() {
		return _fileEntry.getExtension();
	}

	public long getFileEntryId() {
		return _fileEntry.getFileEntryId();
	}

	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getFileVersion();

		return newFileVersionProxyBean(fileVersion);
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getFileVersion(version);

		return newFileVersionProxyBean(fileVersion);
	}

	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		List<FileVersion> fileVersions = _fileEntry.getFileVersions(status);

		return toFileVersionProxyBeans(fileVersions);
	}

	public Folder getFolder() {
		Folder folder = _fileEntry.getFolder();

		return newFolderProxyBean(folder);
	}

	public long getFolderId() {
		return _fileEntry.getFolderId();
	}

	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	public String getIcon() {
		return _fileEntry.getIcon();
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getLatestFileVersion();

		return newFileVersionProxyBean(fileVersion);
	}

	public Lock getLock() {
		Lock lock = _fileEntry.getLock();

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	public String getMimeType() {
		return _fileEntry.getMimeType();
	}

	public String getMimeType(String version) {
		return _fileEntry.getMimeType(version);
	}

	public Object getModel() {
		return _fileEntry.getModel();
	}

	public Class<?> getModelClass() {
		return _fileEntry.getModelClass();
	}

	public String getModelClassName() {
		return _fileEntry.getModelClassName();
	}

	public Date getModifiedDate() {
		return _fileEntry.getModifiedDate();
	}

	public long getPrimaryKey() {
		return _fileEntry.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return _fileEntry.getPrimaryKeyObj();
	}

	public int getReadCount() {
		return _fileEntry.getReadCount();
	}

	public long getRepositoryId() {
		return _fileEntry.getRepositoryId();
	}

	public long getSize() {
		return _fileEntry.getSize();
	}

	public String getTitle() {
		return _fileEntry.getTitle();
	}

	public long getUserId() {
		return _fileEntry.getUserId();
	}

	public String getUserName() {
		return _fileEntry.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _fileEntry.getUserUuid();
	}

	public String getUuid() {
		return _fileEntry.getUuid();
	}

	public String getVersion() {
		return _fileEntry.getVersion();
	}

	public long getVersionUserId() {
		return _fileEntry.getVersionUserId();
	}

	public String getVersionUserName() {
		return _fileEntry.getVersionUserName();
	}

	public String getVersionUserUuid() throws SystemException {
		return _fileEntry.getVersionUserUuid();
	}

	public boolean hasLock() {
		return _fileEntry.hasLock();
	}

	public boolean isCheckedOut() {
		return _fileEntry.isCheckedOut();
	}

	public boolean isDefaultRepository() {
		return _fileEntry.isDefaultRepository();
	}

	public boolean isEscapedModel() {
		return _fileEntry.isEscapedModel();
	}

	public boolean isManualCheckInRequired() {
		return _fileEntry.isManualCheckInRequired();
	}

	public boolean isSupportsLocking() {
		return _fileEntry.isSupportsLocking();
	}

	public boolean isSupportsMetadata() {
		return _fileEntry.isSupportsMetadata();
	}

	public boolean isSupportsSocial() {
		return _fileEntry.isSupportsSocial();
	}

	public void setCompanyId(long companyId) {
		_fileEntry.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
		_fileEntry.setCreateDate(date);
	}

	public void setGroupId(long groupId) {
		_fileEntry.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
		_fileEntry.setModifiedDate(date);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public void setUserId(long userId) {
		_fileEntry.setUserId(userId);
	}

	public void setUserName(String userName) {
		_fileEntry.setUserName(userName);
	}

	public void setUserUuid(String userUuid) {
		_fileEntry.setUserUuid(userUuid);
	}

	public FileEntry toEscapedModel() {
		FileEntry fileEntry = _fileEntry.toEscapedModel();

		return newFileEntryProxyBean(fileEntry);
	}

	public FileEntry toUnescapedModel() {
		FileEntry fileEntry = _fileEntry.toUnescapedModel();

		return newFileEntryProxyBean(fileEntry);
	}

	private FileEntry _fileEntry;

}