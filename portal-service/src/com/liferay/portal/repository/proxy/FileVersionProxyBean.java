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
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Mika Koivisto
 */
public class FileVersionProxyBean
	extends RepositoryModelProxyBean implements FileVersion {

	public FileVersionProxyBean(
		FileVersion fileVersion, ClassLoader classLoader) {

		super(classLoader);

		_fileVersion = fileVersion;
	}

	public Map<String, Serializable> getAttributes() {
		return _fileVersion.getAttributes();
	}

	public String getChangeLog() {
		return _fileVersion.getChangeLog();
	}

	public long getCompanyId() {
		return _fileVersion.getCompanyId();
	}

	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException {

		return _fileVersion.getContentStream(incrementCounter);
	}

	public Date getCreateDate() {
		return _fileVersion.getCreateDate();
	}

	public String getDescription() {
		return _fileVersion.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		ExpandoBridge expandoBridge = _fileVersion.getExpandoBridge();

		return (ExpandoBridge)newProxyInstance(
			expandoBridge, ExpandoBridge.class);
	}

	public String getExtension() {
		return _fileVersion.getExtension();
	}

	public String getExtraSettings() {
		return _fileVersion.getExtraSettings();
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		FileEntry fileEntry = _fileVersion.getFileEntry();

		return newFileEntryProxyBean(fileEntry);
	}

	public long getFileEntryId() {
		return _fileVersion.getFileEntryId();
	}

	public long getFileVersionId() {
		return _fileVersion.getFileVersionId();
	}

	public long getGroupId() {
		return _fileVersion.getGroupId();
	}

	public String getIcon() {
		return _fileVersion.getIcon();
	}

	public String getMimeType() {
		return _fileVersion.getMimeType();
	}

	public Object getModel() {
		return _fileVersion.getModel();
	}

	public Class<?> getModelClass() {
		return _fileVersion.getModelClass();
	}

	public String getModelClassName() {
		return _fileVersion.getModelClassName();
	}

	public Date getModifiedDate() {
		return _fileVersion.getModifiedDate();
	}

	public long getPrimaryKey() {
		return _fileVersion.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return _fileVersion.getPrimaryKeyObj();
	}

	public long getRepositoryId() {
		return _fileVersion.getRepositoryId();
	}

	public long getSize() {
		return _fileVersion.getSize();
	}

	public int getStatus() {
		return _fileVersion.getStatus();
	}

	public long getStatusByUserId() {
		return _fileVersion.getStatusByUserId();
	}

	public String getStatusByUserName() {
		return _fileVersion.getStatusByUserName();
	}

	public String getStatusByUserUuid() throws SystemException {
		return _fileVersion.getStatusByUserUuid();
	}

	public Date getStatusDate() {
		return _fileVersion.getStatusDate();
	}

	public String getTitle() {
		return _fileVersion.getTitle();
	}

	public long getUserId() {
		return _fileVersion.getUserId();
	}

	public String getUserName() {
		return _fileVersion.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _fileVersion.getUserUuid();
	}

	public String getUuid() {
		return _fileVersion.getUuid();
	}

	public String getVersion() {
		return _fileVersion.getVersion();
	}

	public boolean isApproved() {
		return _fileVersion.isApproved();
	}

	public boolean isDefaultRepository() {
		return _fileVersion.isDefaultRepository();
	}

	public boolean isDraft() {
		return _fileVersion.isDraft();
	}

	public boolean isEscapedModel() {
		return _fileVersion.isEscapedModel();
	}

	public boolean isExpired() {
		return _fileVersion.isExpired();
	}

	public boolean isInTrash() {
		return _fileVersion.isInTrash();
	}

	public boolean isInTrashFolder() {
		return _fileVersion.isInTrashFolder();
	}

	public boolean isPending() {
		return _fileVersion.isPending();
	}

	public void setCompanyId(long companyId) {
		_fileVersion.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
		_fileVersion.setCreateDate(date);
	}

	public void setGroupId(long groupId) {
		_fileVersion.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
		_fileVersion.setModifiedDate(date);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fileVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	public void setUserId(long userId) {
		_fileVersion.setUserId(userId);
	}

	public void setUserName(String userName) {
		_fileVersion.setUserName(userName);
	}

	public void setUserUuid(String userUuid) {
		_fileVersion.setUserUuid(userUuid);
	}

	public FileVersion toEscapedModel() {
		FileVersion fileVersion = _fileVersion.toEscapedModel();

		return newFileVersionProxyBean(fileVersion);
	}

	private FileVersion _fileVersion;

}