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
package com.liferay.portal.kernel.repository.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.security.permission.PermissionChecker;

import com.liferay.portlet.expando.model.ExpandoBridge;

/**
 * @author Kyle Stiemann
 */
public class FileEntryWrapper implements FileEntry, ModelWrapper<FileEntry> {

	public FileEntryWrapper(FileEntry fileEntry) {
		_fileEntry = fileEntry;
	}
	
	public Map<String, Serializable> getAttributes() {
		return _fileEntry.getAttributes();
	}

	public long getCompanyId() {
		return _fileEntry.getCompanyId();
	}
		
	public void setCompanyId(long companyId) {
		_fileEntry.setCompanyId(companyId);
	}

	public InputStream getContentStream() throws PortalException,
			SystemException {
		return _fileEntry.getContentStream();
	}

	public InputStream getContentStream(String version) throws PortalException,
			SystemException {
		return _fileEntry.getContentStream();
	}
	
	public Date getCreateDate() {
		return _fileEntry.getCreateDate();
	}
	
	public void setCreateDate(Date date) {
		_fileEntry.setCreateDate(date);
	}
	
	public String getDescription() {
		return _fileEntry.getDescription();
	}
	
	public ExpandoBridge getExpandoBridge() {
		return _fileEntry.getExpandoBridge();
	}
	
	public String getExtension() {
		return _fileEntry.getExtension();
	}
	
	public long getFileEntryId() {
		return _fileEntry.getFileEntryId();
	}
	
	public FileVersion getFileVersion() throws PortalException, SystemException {
		return _fileEntry.getFileVersion();
	}

	public FileVersion getFileVersion(String version) throws PortalException,
			SystemException {
		return _fileEntry.getFileVersion();
	}
	
	public List<FileVersion> getFileVersions(int status) throws SystemException {
		return _fileEntry.getFileVersions(status);
	}
	
	public Folder getFolder() {
		return _fileEntry.getFolder();
	}
	
	public long getFolderId() {
		return _fileEntry.getFolderId();
	}

	public long getGroupId() {
		return _fileEntry.getGroupId();
	}
		
	public void setGroupId(long groupId) {
		_fileEntry.setGroupId(groupId);
	}
	
	public String getIcon() {
		return _fileEntry.getIcon();
	}

	public FileVersion getLatestFileVersion() throws PortalException,
			SystemException {
		return _fileEntry.getLatestFileVersion();
	}
	
	public Lock getLock() {
		return _fileEntry.getLock();
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
		return FileEntry.class;
	}
	
	public String getModelClassName() {
		return FileEntry.class.getName();
	}
	
	public Date getModifiedDate() {
		return _fileEntry.getModifiedDate();
	}
	
	public void setModifiedDate(Date date) {
		_fileEntry.setModifiedDate(date);
	}
	
	public long getPrimaryKey() {
		return _fileEntry.getPrimaryKey();
	}
	
	public Serializable getPrimaryKeyObj() {
		return _fileEntry.getPrimaryKeyObj();
	}
	
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fileEntry.setPrimaryKeyObj(primaryKeyObj);
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
	
	public void setUserId(long userId) {
		_fileEntry.setUserId(userId);
	}

	public String getUserName() {
		return _fileEntry.getUserName();
	}
		
	public void setUserName(String userName) {
		_fileEntry.setUserName(userName);
	}

	public String getUserUuid() throws SystemException {
		return _fileEntry.getUserUuid();
	}
	
	public void setUserUuid(String userUuid) {
		_fileEntry.setUserUuid(userUuid);
	}

	public String getUuid() {
		return _fileEntry.getUuid();
	}
	
	public void setUuid(String uuid) {
		_fileEntry.setUuid(uuid);
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

	public FileEntry getWrappedModel() {
		return _fileEntry;
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

	@Override
	public java.lang.Object clone() {
		return new FileEntryWrapper((FileEntry) _fileEntry.clone());
	}

	public boolean containsPermission(PermissionChecker permissionChecker,
			String actionId) throws PortalException, SystemException {
		return _fileEntry.containsPermission(permissionChecker, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		FileEntryWrapper other = (FileEntryWrapper) obj;
		return _fileEntry.equals(other.getWrappedModel());
	}
	
	@Override
	public int hashCode() {
		return _fileEntry.hashCode();
	}
	
	public FileEntry toEscapedModel() {
		return new FileEntryWrapper(_fileEntry.toEscapedModel());
	}

	public FileEntry toUnescapedModel() {
		return new FileEntryWrapper(_fileEntry.toUnescapedModel());
	}
	
	@Override
	public String toString() {
		return _fileEntry.toString();
	}

	FileEntry _fileEntry;
}