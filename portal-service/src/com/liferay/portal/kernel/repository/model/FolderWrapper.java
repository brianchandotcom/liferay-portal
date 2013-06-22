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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle Stiemann
 */
public class FolderWrapper implements Folder, ModelWrapper<Folder> {

	public FolderWrapper(Folder folder) {
		_folder = folder;
	}

	@Override
	public Object clone() {
		return new FolderWrapper((Folder) _folder.clone());
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return _folder.containsPermission(permissionChecker, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		FolderWrapper other = (FolderWrapper)obj;

		return _folder.equals(other.getWrappedModel());
	}

	public List<Long> getAncestorFolderIds()
		throws PortalException, SystemException {

		return _folder.getAncestorFolderIds();
	}

	public List<Folder> getAncestors() throws PortalException, SystemException {
		return _folder.getAncestors();
	}

	public Map<String, Serializable> getAttributes() {
		return _folder.getAttributes();
	}

	public long getCompanyId() {
		return _folder.getCompanyId();
	}

	public Date getCreateDate() {
		return _folder.getCreateDate();
	}

	public String getDescription() {
		return _folder.getDescription();
	}

	public ExpandoBridge getExpandoBridge() {
		return _folder.getExpandoBridge();
	}

	public long getFolderId() {
		return _folder.getFolderId();
	}

	public long getGroupId() {
		return _folder.getGroupId();
	}

	public Date getLastPostDate() {
		return _folder.getLastPostDate();
	}

	public Object getModel() {
		return _folder.getModel();
	}

	public Class<?> getModelClass() {
		return Folder.class;
	}

	public String getModelClassName() {
		return Folder.class.getName();
	}

	public Date getModifiedDate() {
		return _folder.getModifiedDate();
	}

	public String getName() {
		return _folder.getName();
	}

	public Folder getParentFolder() throws PortalException, SystemException {
		return _folder.getParentFolder();
	}

	public long getParentFolderId() {
		return _folder.getParentFolderId();
	}

	public long getPrimaryKey() {
		return _folder.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return _folder.getPrimaryKeyObj();
	}

	public long getRepositoryId() {
		return _folder.getRepositoryId();
	}

	public long getUserId() {
		return _folder.getUserId();
	}

	public String getUserName() {
		return _folder.getUserName();
	}

	public String getUserUuid() throws SystemException {
		return _folder.getUserUuid();
	}

	public String getUuid() {
		return _folder.getUuid();
	}

	public Folder getWrappedModel() {
		return _folder;
	}

	@Override
	public int hashCode() {
		return _folder.hashCode();
	}

	public boolean hasInheritableLock() {
		return _folder.hasInheritableLock();
	}

	public boolean hasLock() {
		return _folder.hasLock();
	}

	public boolean isDefaultRepository() {
		return _folder.isDefaultRepository();
	}

	public boolean isEscapedModel() {
		return _folder.isEscapedModel();
	}

	public boolean isLocked() {
		return _folder.isLocked();
	}

	public boolean isMountPoint() {
		return _folder.isMountPoint();
	}

	public boolean isRoot() {
		return _folder.isRoot();
	}

	public boolean isSupportsLocking() {
		return _folder.isSupportsLocking();
	}

	public boolean isSupportsMetadata() {
		return _folder.isSupportsMetadata();
	}

	public boolean isSupportsMultipleUpload() {
		return _folder.isSupportsMultipleUpload();
	}

	public boolean isSupportsShortcuts() {
		return _folder.isSupportsShortcuts();
	}

	public boolean isSupportsSocial() {
		return _folder.isSupportsSocial();
	}

	public boolean isSupportsSubscribing() {
		return _folder.isSupportsSubscribing();
	}

	public void setCompanyId(long companyId) {
		_folder.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
		_folder.setCreateDate(date);
	}

	public void setGroupId(long groupId) {
		_folder.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
		_folder.setModifiedDate(date);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_folder.setPrimaryKeyObj(primaryKeyObj);
	}

	public void setUserId(long userId) {
		_folder.setUserId(userId);
	}

	public void setUserName(String userName) {
		_folder.setUserName(userName);
	}

	public void setUserUuid(String userUuid) {
		_folder.setUserUuid(userUuid);
	}

	public void setUuid(String uuid) {
		_folder.setUuid(uuid);
	}

	public Folder toEscapedModel() {
		return new FolderWrapper(_folder.toEscapedModel());
	}

	@Override
	public String toString() {
		return _folder.toString();
	}

	public Folder toUnescapedModel() {
		return new FolderWrapper(_folder.toUnescapedModel());
	}

	private Folder _folder;

}