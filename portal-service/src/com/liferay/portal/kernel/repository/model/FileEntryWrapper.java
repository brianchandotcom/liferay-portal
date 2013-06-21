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
 * @author  Neil Griffin
 * @author 	Kyle Stiemann
 */
public class FileEntryWrapper implements FileEntry, ModelWrapper<FileEntry> {

	private static final long serialVersionUID = 6851314478796948660L;
	FileEntry wrappedFileEntry;

	public FileEntryWrapper(FileEntry fileEntry) {
		wrappedFileEntry = fileEntry;
	}

	@Override
	public java.lang.Object clone() {
		return new FileEntryWrapper((FileEntry)wrappedFileEntry.clone());
	}
	
	@Override
	public int hashCode() {
		return wrappedFileEntry.hashCode();
	}

	public boolean containsPermission(PermissionChecker permissionChecker, String actionId) throws PortalException,
		SystemException {
		return wrappedFileEntry.containsPermission(permissionChecker, actionId);
	}

	public boolean hasLock() {
		return wrappedFileEntry.hasLock();
	}

	public FileEntry toEscapedModel() {
		return wrappedFileEntry.toEscapedModel();
	}

	public boolean isSupportsMetadata() {
		return wrappedFileEntry.isSupportsMetadata();
	}

	public Map<String, Serializable> getAttributes() {
		return wrappedFileEntry.getAttributes();
	}

	public long getCompanyId() {
		return wrappedFileEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		wrappedFileEntry.setCompanyId(companyId);
	}

	public InputStream getContentStream() throws PortalException, SystemException {
		return wrappedFileEntry.getContentStream();
	}

	public InputStream getContentStream(String version) throws PortalException, SystemException {
		return wrappedFileEntry.getContentStream();
	}

	public Date getCreateDate() {
		return wrappedFileEntry.getCreateDate();
	}

	public void setCreateDate(Date date) {
		wrappedFileEntry.setCreateDate(date);
	}

	public String getDescription() {
		return wrappedFileEntry.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		return wrappedFileEntry.getExpandoBridge();
	}

	public String getExtension() {
		return wrappedFileEntry.getExtension();
	}

	public long getFileEntryId() {
		return wrappedFileEntry.getFileEntryId();
	}

	public FileVersion getFileVersion() throws PortalException, SystemException {
		return wrappedFileEntry.getFileVersion();
	}

	public FileVersion getFileVersion(String version) throws PortalException, SystemException {
		return wrappedFileEntry.getFileVersion();
	}

	public List<FileVersion> getFileVersions(int status) throws SystemException {
		return wrappedFileEntry.getFileVersions(status);
	}

	public Folder getFolder() {
		return wrappedFileEntry.getFolder();
	}

	public long getFolderId() {
		return wrappedFileEntry.getFolderId();
	}

	public boolean isSupportsLocking() {
		return wrappedFileEntry.isSupportsLocking();
	}

	public long getGroupId() {
		return wrappedFileEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		wrappedFileEntry.setGroupId(groupId);
	}

	public String getIcon() {
		return wrappedFileEntry.getIcon();
	}

	public boolean isEscapedModel() {
		return wrappedFileEntry.isEscapedModel();
	}

	public boolean isSupportsSocial() {
		return wrappedFileEntry.isSupportsSocial();
	}

	public FileVersion getLatestFileVersion() throws PortalException, SystemException {
		return wrappedFileEntry.getLatestFileVersion();
	}

	public Lock getLock() {
		return wrappedFileEntry.getLock();
	}

	public String getMimeType() {
		return wrappedFileEntry.getMimeType();
	}

	public String getMimeType(String version) {
		return wrappedFileEntry.getMimeType(version);
	}

	public Object getModel() {
		return wrappedFileEntry.getModel();
	}

	public Class<?> getModelClass() {
		return wrappedFileEntry.getModelClass();
	}

	public String getModelClassName() {
		return wrappedFileEntry.getModelClassName();
	}

	public Date getModifiedDate() {
		return wrappedFileEntry.getModifiedDate();
	}

	public void setModifiedDate(Date date) {
		wrappedFileEntry.setModifiedDate(date);
	}

	public long getPrimaryKey() {
		return wrappedFileEntry.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return wrappedFileEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		wrappedFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public int getReadCount() {
		return wrappedFileEntry.getReadCount();
	}

	public long getRepositoryId() {
		return wrappedFileEntry.getRepositoryId();
	}

	public long getSize() {
		return wrappedFileEntry.getSize();
	}

	public boolean isCheckedOut() {
		return wrappedFileEntry.isCheckedOut();
	}

	public String getTitle() {
		return wrappedFileEntry.getTitle();
	}

	public long getUserId() {
		return wrappedFileEntry.getUserId();
	}

	public void setUserId(long userId) {
		wrappedFileEntry.setUserId(userId);
	}

	public String getUserName() {
		return wrappedFileEntry.getUserName();
	}

	public void setUserName(String userName) {
		wrappedFileEntry.setUserName(userName);
	}

	public String getUserUuid() throws SystemException {
		return wrappedFileEntry.getUserUuid();
	}

	public void setUserUuid(String userUuid) {
		wrappedFileEntry.setUserUuid(userUuid);
	}

	public String getUuid() {
		return wrappedFileEntry.getUuid();
	}

	public String getVersion() {
		return wrappedFileEntry.getVersion();
	}

	public long getVersionUserId() {
		return wrappedFileEntry.getVersionUserId();
	}

	public String getVersionUserName() {
		return wrappedFileEntry.getVersionUserName();
	}

	public String getVersionUserUuid() throws SystemException {
		return wrappedFileEntry.getVersionUserUuid();
	}

	public FileEntry getWrappedModel() {
		return wrappedFileEntry;
	}

	public boolean isDefaultRepository() {
		return wrappedFileEntry.isDefaultRepository();
	}

	@Override
	public FileEntry toUnescapedModel() {
		// TODO Auto-generated method stub
		return wrappedFileEntry.toUnescapedModel();
	}

	@Override
	public void setUuid(String uuid) {
		//TODO
		wrappedFileEntry.setUuid(uuid);
		
	}

	@Override
	public boolean isManualCheckInRequired() {
		// TODO Auto-generated method stub
		return wrappedFileEntry.isManualCheckInRequired();
	}

}