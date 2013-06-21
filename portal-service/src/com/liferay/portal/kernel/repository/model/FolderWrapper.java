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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.security.permission.PermissionChecker;

import com.liferay.portlet.expando.model.ExpandoBridge;


/**
 * @author  Neil Griffin
 */
public class FolderWrapper implements Folder, ModelWrapper<Folder> {

	private static final long serialVersionUID = 1886656716155971783L;

	private Folder wrappedFolder;

	public FolderWrapper(Folder folder) {
		this.wrappedFolder = folder;
	}
	
	
	@Override
	public java.lang.Object clone() {
		return new FolderWrapper((Folder)wrappedFolder.clone());
	}


	public boolean containsPermission(PermissionChecker permissionChecker, String actionId) throws PortalException,
		SystemException {
		return wrappedFolder.containsPermission(permissionChecker, actionId);
	}

	public boolean hasInheritableLock() {
		return wrappedFolder.hasInheritableLock();
	}

	public boolean hasLock() {
		return wrappedFolder.hasLock();
	}

	public Folder toEscapedModel() {
		return wrappedFolder.toEscapedModel();
	}

	public boolean isSupportsMetadata() {
		return wrappedFolder.isSupportsMetadata();
	}

	public List<Folder> getAncestors() throws PortalException, SystemException {
		return wrappedFolder.getAncestors();
	}

	public Map<String, Serializable> getAttributes() {
		return wrappedFolder.getAttributes();
	}

	public long getCompanyId() {
		return wrappedFolder.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		wrappedFolder.setCompanyId(companyId);
	}

	public Date getCreateDate() {
		return wrappedFolder.getCreateDate();
	}

	public void setCreateDate(Date date) {
		wrappedFolder.setCreateDate(date);
	}

	public boolean isLocked() {
		return wrappedFolder.isLocked();
	}

	public boolean isSupportsMultipleUpload() {
		return wrappedFolder.isSupportsMultipleUpload();
	}

	public String getDescription() {
		return wrappedFolder.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		return wrappedFolder.getExpandoBridge();
	}

	public long getFolderId() {
		return wrappedFolder.getFolderId();
	}

	public boolean isSupportsLocking() {
		return wrappedFolder.isSupportsLocking();
	}

	public long getGroupId() {
		return wrappedFolder.getGroupId();
	}

	public void setGroupId(long groupId) {
		wrappedFolder.setGroupId(groupId);
	}

	public boolean isEscapedModel() {
		return wrappedFolder.isEscapedModel();
	}

	public boolean isSupportsSocial() {
		return wrappedFolder.isSupportsSocial();
	}

	public Date getLastPostDate() {
		return wrappedFolder.getLastPostDate();
	}

	public Object getModel() {
		return wrappedFolder.getModel();
	}

	public Class<?> getModelClass() {
		return wrappedFolder.getModelClass();
	}

	public String getModelClassName() {
		return wrappedFolder.getModelClassName();
	}

	public Date getModifiedDate() {
		return wrappedFolder.getModifiedDate();
	}

	public void setModifiedDate(Date date) {
		wrappedFolder.setModifiedDate(date);
	}

	public String getName() {
		return wrappedFolder.getName();
	}

	public Folder getParentFolder() throws PortalException, SystemException {
		return wrappedFolder.getParentFolder();
	}

	public long getParentFolderId() {
		return wrappedFolder.getParentFolderId();
	}

	public long getPrimaryKey() {
		return wrappedFolder.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return wrappedFolder.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		wrappedFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	public long getRepositoryId() {
		return wrappedFolder.getRepositoryId();
	}

	public boolean isSupportsShortcuts() {
		return wrappedFolder.isSupportsShortcuts();
	}

	public boolean isMountPoint() {
		return wrappedFolder.isMountPoint();
	}

	public boolean isRoot() {
		return wrappedFolder.isRoot();
	}

	public long getUserId() {
		return wrappedFolder.getUserId();
	}

	public void setUserId(long userId) {
		wrappedFolder.setUserId(userId);
	}

	public String getUserName() {
		return wrappedFolder.getUserName();
	}

	public void setUserName(String userName) {
		wrappedFolder.setUserName(userName);
	}

	public String getUserUuid() throws SystemException {
		return wrappedFolder.getUserUuid();
	}

	public void setUserUuid(String userUuid) {
		wrappedFolder.setUserUuid(userUuid);
	}

	public String getUuid() {
		return wrappedFolder.getUuid();
	}

	public Folder getWrappedModel() {
		return wrappedFolder;
	}

	public boolean isDefaultRepository() {
		return wrappedFolder.isDefaultRepository();
	}

	@Override
	public Folder toUnescapedModel() {
		// TODO Auto-generated method stub
		return wrappedFolder.toUnescapedModel();
	}

	@Override
	public void setUuid(String uuid) {
		// TODO Auto-generated method stub
		wrappedFolder.setUuid(uuid);
	}

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		return wrappedFolder.getAncestorFolderIds();
	}

	@Override
	public boolean isSupportsSubscribing() {
		// TODO Auto-generated method stub
		return wrappedFolder.isSupportsSubscribing();
	}
}