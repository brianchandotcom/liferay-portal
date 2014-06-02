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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayFolder extends LiferayModel implements Folder {

	public LiferayFolder(DLFolder dlFolder) {
		_dlFolder = dlFolder;
	}

	public LiferayFolder(DLFolder dlFolder, boolean escapedModel) {
		_dlFolder = dlFolder;
		_escapedModel = escapedModel;
	}

	@Override
	public Object clone() {
		LiferayFolder liferayFolder = new LiferayFolder(
			_dlFolder, _escapedModel);

		liferayFolder.setCompanyId(getCompanyId());
		liferayFolder.setCreateDate(getCreateDate());
		liferayFolder.setGroupId(getGroupId());
		liferayFolder.setModifiedDate(getModifiedDate());
		liferayFolder.setPrimaryKey(getPrimaryKey());
		liferayFolder.setUserId(getUserId());
		liferayFolder.setUserName(getUserName());

		try {
			liferayFolder.setUserUuid(getUserUuid());
		}
		catch (SystemException se) {
		}

		liferayFolder.setUuid(getUuid());

		return liferayFolder;
	}

	@Override
	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return DLFolderPermission.contains(
			permissionChecker, _dlFolder, actionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LiferayFolder)) {
			return false;
		}

		LiferayFolder liferayFolder = (LiferayFolder)obj;

		if (Validator.equals(_dlFolder, liferayFolder._dlFolder)) {
			return true;
		}

		return false;
	}

	@Override
	public List<Long> getAncestorFolderIds()
		throws PortalException, SystemException {

		return _dlFolder.getAncestorFolderIds();
	}

	@Override
	public List<Folder> getAncestors() throws PortalException, SystemException {
		return RepositoryModelUtil.toFolders(_dlFolder.getAncestors());
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _dlFolder.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _dlFolder.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _dlFolder.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFolder.getExpandoBridge();
	}

	@Override
	public long getFolderId() {
		return _dlFolder.getFolderId();
	}

	@Override
	public long getGroupId() {
		return _dlFolder.getGroupId();
	}

	@Override
	public Date getLastPostDate() {
		return _dlFolder.getLastPostDate();
	}

	@Override
	public Object getModel() {
		return _dlFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return LiferayFolder.class;
	}

	@Override
	public String getModelClassName() {
		return LiferayFolder.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _dlFolder.getModifiedDate();
	}

	@Override
	public String getName() {
		return _dlFolder.getName();
	}

	@Override
	public Folder getParentFolder() throws PortalException, SystemException {
		DLFolder dlParentFolder = _dlFolder.getParentFolder();

		if (dlParentFolder == null) {
			return null;
		}
		else {
			return new LiferayFolder(dlParentFolder);
		}
	}

	@Override
	public long getParentFolderId() {
		return _dlFolder.getParentFolderId();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFolder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public long getRepositoryId() {
		return _dlFolder.getRepositoryId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFolderConstants.getClassName());
	}

	@Override
	public long getUserId() {
		return _dlFolder.getUserId();
	}

	@Override
	public String getUserName() {
		return _dlFolder.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _dlFolder.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _dlFolder.getUuid();
	}

	@Override
	public int hashCode() {
		return _dlFolder.hashCode();
	}

	@Override
	public boolean hasInheritableLock() {
		return _dlFolder.hasInheritableLock();
	}

	@Override
	public boolean hasLock() {
		return _dlFolder.hasLock();
	}

	@Override
	public boolean isDefaultRepository() {
		if (_dlFolder.getGroupId() == _dlFolder.getRepositoryId()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isEscapedModel() {
		return _escapedModel;
	}

	@Override
	public boolean isLocked() {
		return _dlFolder.isLocked();
	}

	@Override
	public boolean isMountPoint() {
		return _dlFolder.isMountPoint();
	}

	@Override
	public boolean isRoot() {
		return _dlFolder.isRoot();
	}

	@Override
	public boolean isSupportsLocking() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isSupportsMetadata() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isSupportsMultipleUpload() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isSupportsShortcuts() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isSupportsSocial() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isSupportsSubscribing() {
		if (isMountPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void setCompanyId(long companyId) {
		_dlFolder.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_dlFolder.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_dlFolder.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_dlFolder.setModifiedDate(date);
	}

	public void setPrimaryKey(long primaryKey) {
		_dlFolder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public void setUserId(long userId) {
		_dlFolder.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_dlFolder.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_dlFolder.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_dlFolder.setUuid(uuid);
	}

	@Override
	public Folder toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFolder(_dlFolder.toEscapedModel(), true);
		}
	}

	@Override
	public String toString() {
		return _dlFolder.toString();
	}

	@Override
	public Folder toUnescapedModel() {
		if (isEscapedModel()) {
			return new LiferayFolder(_dlFolder.toUnescapedModel(), true);
		}
		else {
			return this;
		}
	}

	private DLFolder _dlFolder;
	private boolean _escapedModel;

}