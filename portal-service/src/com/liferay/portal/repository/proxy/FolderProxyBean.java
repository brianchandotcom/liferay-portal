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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mika Koivisto
 */
public class FolderProxyBean
	extends RepositoryModelProxyBean implements Folder {

	public FolderProxyBean(Folder folder, ClassLoader classLoader) {
		super(classLoader);

		_folder = folder;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return _folder.containsPermission(permissionChecker, actionId);
	}

	public List<Long> getAncestorFolderIds()
		throws PortalException, SystemException {

		return _folder.getAncestorFolderIds();
	}

	public List<Folder> getAncestors() throws PortalException, SystemException {
		List<Folder> folders = _folder.getAncestors();

		return toFolderProxyBeans(folders);
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
		ExpandoBridge expandoBridge = _folder.getExpandoBridge();

		return (ExpandoBridge)newProxyInstance(
			expandoBridge, ExpandoBridge.class);
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
		return _folder.getModelClass();
	}

	public String getModelClassName() {
		return _folder.getModelClassName();
	}

	public Date getModifiedDate() {
		return _folder.getModifiedDate();
	}

	public String getName() {
		return _folder.getName();
	}

	public Folder getParentFolder() throws PortalException, SystemException {
		Folder folder = _folder.getParentFolder();

		return newFolderProxyBean(folder);
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

	public Folder toEscapedModel() {
		Folder folder = _folder.toEscapedModel();

		return newFolderProxyBean(folder);
	}

	public Folder toUnescapedModel() {
		Folder folder = _folder.toUnescapedModel();

		return newFolderProxyBean(folder);
	}

	private Folder _folder;

}