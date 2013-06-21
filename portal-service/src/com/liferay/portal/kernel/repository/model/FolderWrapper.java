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
 * @author Kyle Stiemann
 */
public class FolderWrapper implements Folder, ModelWrapper<Folder> {

	public FolderWrapper(Folder folder) {
		this._folder = folder;
	}

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException,
			SystemException {
		return _folder.getAncestorFolderIds();
	}

	@Override
	public List<Folder> getAncestors() throws PortalException, SystemException {
		return _folder.getAncestors();
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return _folder.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _folder.getCompanyId();
	}

	@Override
	public void setCompanyId(long companyId) {
		_folder.setCompanyId(companyId);
	}

	@Override
	public Date getCreateDate() {
		return _folder.getCreateDate();
	}

	@Override
	public void setCreateDate(Date date) {
		_folder.setCreateDate(date);
	}

	@Override
	public String getDescription() {
		return _folder.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _folder.getExpandoBridge();
	}

	@Override
	public long getFolderId() {
		return _folder.getFolderId();
	}

	@Override
	public long getGroupId() {
		return _folder.getGroupId();
	}

	@Override
	public void setGroupId(long groupId) {
		_folder.setGroupId(groupId);
	}

	@Override
	public Date getLastPostDate() {
		return _folder.getLastPostDate();
	}

	@Override
	public Object getModel() {
		return _folder.getModel();
	}

	@Override
	public Class<?> getModelClass() {
		return Folder.class;
	}

	@Override
	public String getModelClassName() {
		return Folder.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _folder.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date date) {
		_folder.setModifiedDate(date);
	}

	@Override
	public String getName() {
		return _folder.getName();
	}

	@Override
	public Folder getParentFolder() throws PortalException, SystemException {
		return _folder.getParentFolder();
	}

	@Override
	public long getParentFolderId() {
		return _folder.getParentFolderId();
	}

	@Override
	public long getPrimaryKey() {
		return _folder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _folder.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_folder.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public long getRepositoryId() {
		return _folder.getRepositoryId();
	}

	@Override
	public long getUserId() {
		return _folder.getUserId();
	}

	@Override
	public void setUserId(long userId) {
		_folder.setUserId(userId);
	}

	@Override
	public String getUserName() {
		return _folder.getUserName();
	}

	@Override
	public void setUserName(String userName) {
		_folder.setUserName(userName);
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _folder.getUserUuid();
	}

	@Override
	public void setUserUuid(String userUuid) {
		_folder.setUserUuid(userUuid);
	}

	@Override
	public String getUuid() {
		return _folder.getUuid();
	}

	@Override
	public void setUuid(String uuid) {
		_folder.setUuid(uuid);
	}

	@Override
	public Folder getWrappedModel() {
		return _folder;
	}

	@Override
	public boolean hasInheritableLock() {
		return _folder.hasInheritableLock();
	}

	@Override
	public boolean hasLock() {
		return _folder.hasLock();
	}

	@Override
	public boolean isDefaultRepository() {
		return _folder.isDefaultRepository();
	}

	@Override
	public boolean isEscapedModel() {
		return _folder.isEscapedModel();
	}

	@Override
	public boolean isLocked() {
		return _folder.isLocked();
	}

	@Override
	public boolean isMountPoint() {
		return _folder.isMountPoint();
	}

	@Override
	public boolean isRoot() {
		return _folder.isRoot();
	}

	@Override
	public boolean isSupportsLocking() {
		return _folder.isSupportsLocking();
	}

	@Override
	public boolean isSupportsMetadata() {
		return _folder.isSupportsMetadata();
	}

	@Override
	public boolean isSupportsMultipleUpload() {
		return _folder.isSupportsMultipleUpload();
	}

	@Override
	public boolean isSupportsShortcuts() {
		return _folder.isSupportsShortcuts();
	}

	@Override
	public boolean isSupportsSocial() {
		return _folder.isSupportsSocial();
	}

	@Override
	public boolean isSupportsSubscribing() {
		return _folder.isSupportsSubscribing();
	}

	@Override
	public java.lang.Object clone() {
		return new FolderWrapper((Folder) _folder.clone());
	}

	@Override
	public boolean containsPermission(PermissionChecker permissionChecker,
			String actionId) throws PortalException, SystemException {
		return _folder.containsPermission(permissionChecker, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		FolderWrapper other = (FolderWrapper) obj;
		return _folder.equals(other.getWrappedModel());
	}

	@Override
	public int hashCode() {
		return _folder.hashCode();
	}

	@Override
	public Folder toEscapedModel() {
		return new FolderWrapper(_folder.toEscapedModel());
	}

	@Override
	public Folder toUnescapedModel() {
		return new FolderWrapper(_folder.toUnescapedModel());
	}

	private Folder _folder;
}