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

	@Override
	public Object clone() {
		FileEntryProxyBean fileEntryProxyBean = newFileEntryProxyBean(
			_fileEntry);

		fileEntryProxyBean.setCompanyId(getCompanyId());
		fileEntryProxyBean.setCreateDate(getCreateDate());
		fileEntryProxyBean.setGroupId(getGroupId());
		fileEntryProxyBean.setModifiedDate(getModifiedDate());
		fileEntryProxyBean.setPrimaryKeyObj(getPrimaryKeyObj());
		fileEntryProxyBean.setUserId(getUserId());
		fileEntryProxyBean.setUserName(getUserName());

		try {
			fileEntryProxyBean.setUserUuid(getUserUuid());
		}
		catch (SystemException se) {
		}

		fileEntryProxyBean.setUuid(getUuid());

		return fileEntryProxyBean;
	}

	@Override
	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return _fileEntry.containsPermission(permissionChecker, actionId);
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return _fileEntry.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _fileEntry.getCompanyId();
	}

	@Override
	public InputStream getContentStream()
		throws PortalException, SystemException {

		return _fileEntry.getContentStream();
	}

	@Override
	public InputStream getContentStream(String version)
		throws PortalException, SystemException {

		return _fileEntry.getContentStream(version);
	}

	@Override
	public Date getCreateDate() {
		return _fileEntry.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _fileEntry.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		ExpandoBridge expandoBridge = _fileEntry.getExpandoBridge();

		return (ExpandoBridge)newProxyInstance(
			expandoBridge, ExpandoBridge.class);
	}

	@Override
	public String getExtension() {
		return _fileEntry.getExtension();
	}

	@Override
	public long getFileEntryId() {
		return _fileEntry.getFileEntryId();
	}

	@Override
	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getFileVersion();

		return newFileVersionProxyBean(fileVersion);
	}

	@Override
	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getFileVersion(version);

		return newFileVersionProxyBean(fileVersion);
	}

	@Override
	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		List<FileVersion> fileVersions = _fileEntry.getFileVersions(status);

		return toFileVersionProxyBeans(fileVersions);
	}

	@Override
	public Folder getFolder() {
		Folder folder = _fileEntry.getFolder();

		return newFolderProxyBean(folder);
	}

	@Override
	public long getFolderId() {
		return _fileEntry.getFolderId();
	}

	@Override
	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	@Override
	public String getIcon() {
		return _fileEntry.getIcon();
	}

	@Override
	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		FileVersion fileVersion = _fileEntry.getLatestFileVersion();

		return newFileVersionProxyBean(fileVersion);
	}

	@Override
	public Lock getLock() {
		Lock lock = _fileEntry.getLock();

		return (Lock)newProxyInstance(lock, Lock.class);
	}

	@Override
	public String getMimeType() {
		return _fileEntry.getMimeType();
	}

	@Override
	public String getMimeType(String version) {
		return _fileEntry.getMimeType(version);
	}

	@Override
	public Object getModel() {
		return _fileEntry.getModel();
	}

	@Override
	public Class<?> getModelClass() {
		return _fileEntry.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _fileEntry.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _fileEntry.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _fileEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fileEntry.getPrimaryKeyObj();
	}

	@Override
	public int getReadCount() {
		return _fileEntry.getReadCount();
	}

	@Override
	public long getRepositoryId() {
		return _fileEntry.getRepositoryId();
	}

	@Override
	public long getSize() {
		return _fileEntry.getSize();
	}

	@Override
	public String getTitle() {
		return _fileEntry.getTitle();
	}

	@Override
	public long getUserId() {
		return _fileEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _fileEntry.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _fileEntry.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _fileEntry.getUuid();
	}

	@Override
	public String getVersion() {
		return _fileEntry.getVersion();
	}

	@Override
	public long getVersionUserId() {
		return _fileEntry.getVersionUserId();
	}

	@Override
	public String getVersionUserName() {
		return _fileEntry.getVersionUserName();
	}

	@Override
	public String getVersionUserUuid() throws SystemException {
		return _fileEntry.getVersionUserUuid();
	}

	@Override
	public boolean hasLock() {
		return _fileEntry.hasLock();
	}

	@Override
	public boolean isCheckedOut() {
		return _fileEntry.isCheckedOut();
	}

	@Override
	public boolean isDefaultRepository() {
		return _fileEntry.isDefaultRepository();
	}

	@Override
	public boolean isEscapedModel() {
		return _fileEntry.isEscapedModel();
	}

	@Override
	public boolean isManualCheckInRequired() {
		return _fileEntry.isManualCheckInRequired();
	}

	@Override
	public boolean isSupportsLocking() {
		return _fileEntry.isSupportsLocking();
	}

	@Override
	public boolean isSupportsMetadata() {
		return _fileEntry.isSupportsMetadata();
	}

	@Override
	public boolean isSupportsSocial() {
		return _fileEntry.isSupportsSocial();
	}

	@Override
	public void setCompanyId(long companyId) {
		_fileEntry.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_fileEntry.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_fileEntry.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_fileEntry.setModifiedDate(date);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_fileEntry.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_fileEntry.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_fileEntry.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_fileEntry.setUuid(uuid);
	}

	@Override
	public FileEntry toEscapedModel() {
		FileEntry fileEntry = _fileEntry.toEscapedModel();

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry toUnescapedModel() {
		FileEntry fileEntry = _fileEntry.toUnescapedModel();

		return newFileEntryProxyBean(fileEntry);
	}

	private FileEntry _fileEntry;

}