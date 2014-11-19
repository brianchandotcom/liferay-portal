/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
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

	@Override
	public Object clone() {
		FolderProxyBean folderProxyBean = newFolderProxyBean(_folder);

		folderProxyBean.setCompanyId(getCompanyId());
		folderProxyBean.setCreateDate(getCreateDate());
		folderProxyBean.setGroupId(getGroupId());
		folderProxyBean.setModifiedDate(getModifiedDate());
		folderProxyBean.setPrimaryKeyObj(getPrimaryKeyObj());
		folderProxyBean.setUserId(getUserId());
		folderProxyBean.setUserName(getUserName());

		try {
			folderProxyBean.setUserUuid(getUserUuid());
		}
		catch (SystemException se) {
		}

		folderProxyBean.setUuid(getUuid());

		return folderProxyBean;
	}

	@Override
	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		return _folder.containsPermission(permissionChecker, actionId);
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		repositoryModelOperation.execute(this);
	}

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException {
		return _folder.getAncestorFolderIds();
	}

	@Override
	public List<Folder> getAncestors() throws PortalException {
		List<Folder> folders = _folder.getAncestors();

		return toFolderProxyBeans(folders);
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
	public Date getCreateDate() {
		return _folder.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _folder.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		ExpandoBridge expandoBridge = _folder.getExpandoBridge();

		return (ExpandoBridge)newProxyInstance(
			expandoBridge, ExpandoBridge.class);
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
	public Date getLastPostDate() {
		return _folder.getLastPostDate();
	}

	@Override
	public Object getModel() {
		return _folder.getModel();
	}

	@Override
	public Class<?> getModelClass() {
		return _folder.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _folder.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _folder.getModifiedDate();
	}

	@Override
	public String getName() {
		return _folder.getName();
	}

	@Override
	public Folder getParentFolder() throws PortalException {
		Folder folder = _folder.getParentFolder();

		return newFolderProxyBean(folder);
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
	public long getRepositoryId() {
		return _folder.getRepositoryId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _folder.getStagedModelType();
	}

	@Override
	public long getUserId() {
		return _folder.getUserId();
	}

	@Override
	public String getUserName() {
		return _folder.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _folder.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _folder.getUuid();
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
	public void setCompanyId(long companyId) {
		_folder.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_folder.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_folder.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_folder.setModifiedDate(date);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_folder.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_folder.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_folder.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_folder.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_folder.setUuid(uuid);
	}

	@Override
	public Folder toEscapedModel() {
		Folder folder = _folder.toEscapedModel();

		return newFolderProxyBean(folder);
	}

	@Override
	public Folder toUnescapedModel() {
		Folder folder = _folder.toUnescapedModel();

		return newFolderProxyBean(folder);
	}

	private final Folder _folder;

}