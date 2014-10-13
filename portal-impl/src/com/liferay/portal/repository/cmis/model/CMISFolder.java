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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 * @author Alexander Chow
 */
public class CMISFolder extends CMISModel implements Folder {

	public CMISFolder(
		CMISRepository cmisRepository, String uuid, long folderId,
		org.apache.chemistry.opencmis.client.api.Folder cmisFolder) {

		_cmisRepository = cmisRepository;
		_uuid = uuid;
		_folderId = folderId;
		_cmisFolder = cmisFolder;
	}

	@Override
	public Object clone() {
		CMISFolder cmisFolder = new CMISFolder(
			_cmisRepository, _uuid, _folderId, _cmisFolder);

		cmisFolder.setCompanyId(getCompanyId());
		cmisFolder.setFolderId(getFolderId());
		cmisFolder.setGroupId(getGroupId());

		try {
			cmisFolder.setParentFolder(getParentFolder());
		}
		catch (Exception e) {
		}

		cmisFolder.setPrimaryKey(getPrimaryKey());

		return cmisFolder;
	}

	@Override
	public boolean containsPermission(
		PermissionChecker permissionChecker, String actionId) {

		if (_cmisFolder.isRootFolder() &&
			(actionId.equals(ActionKeys.DELETE) ||
			 actionId.equals(ActionKeys.UPDATE))) {

			try {
				Folder folder = DLAppLocalServiceUtil.getMountFolder(
					getRepositoryId());

				DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
					folder.getFolderId());

				return DLFolderPermission.contains(
					permissionChecker, dlFolder, actionId);
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}
		}
		else {
			return containsPermission(_cmisFolder, actionId);
		}
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		repositoryModelOperation.execute(this);
	}

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException {
		List<Long> folderIds = new ArrayList<Long>();

		Folder folder = this;

		while (!folder.isRoot()) {
			folder = folder.getParentFolder();

			folderIds.add(folder.getFolderId());
		}

		return folderIds;
	}

	@Override
	public List<Folder> getAncestors() throws PortalException {
		List<Folder> folders = new ArrayList<Folder>();

		Folder folder = this;

		while (!folder.isRoot()) {
			folder = folder.getParentFolder();

			folders.add(folder);
		}

		return folders;
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	@Override
	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		Calendar calendar = _cmisFolder.getCreationDate();

		if (calendar != null) {
			return calendar.getTime();
		}
		else {
			return new Date();
		}
	}

	@Override
	public long getFolderId() {
		return _folderId;
	}

	@Override
	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	@Override
	public Date getLastPostDate() {
		return getModifiedDate();
	}

	@Override
	public Object getModel() {
		return _cmisFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return CMISFolder.class;
	}

	@Override
	public String getModelClassName() {
		return CMISFolder.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		Calendar calendar = _cmisFolder.getLastModificationDate();

		if (calendar != null) {
			return calendar.getTime();
		}
		else {
			return new Date();
		}
	}

	@Override
	public String getName() {
		if (_cmisFolder.isRootFolder()) {
			try {
				Folder folder = DLAppLocalServiceUtil.getMountFolder(
					getRepositoryId());

				return folder.getName();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _cmisFolder.getName();
	}

	@Override
	public Folder getParentFolder() throws PortalException {
		Folder parentFolder = null;

		try {
			parentFolder = super.getParentFolder();

			if (parentFolder != null) {
				return parentFolder;
			}
		}
		catch (Exception e) {
		}

		if (_cmisFolder.isRootFolder()) {
			Folder folder = DLAppLocalServiceUtil.getMountFolder(
				getRepositoryId());

			parentFolder = folder.getParentFolder();
		}
		else {
			String path = _cmisFolder.getPath();

			path = path.substring(0, path.lastIndexOf(CharPool.SLASH));

			if (path.length() == 0) {
				path = StringPool.SLASH;
			}

			Session session =
				(Session)CMISRepositoryLocalServiceUtil.getSession(
					getRepositoryId());

			CmisObject parentCmisFolder = session.getObjectByPath(path);

			parentFolder = CMISRepositoryLocalServiceUtil.toFolder(
				getRepositoryId(), parentCmisFolder);
		}

		setParentFolder(parentFolder);

		return parentFolder;
	}

	@Override
	public long getParentFolderId() {
		try {
			Folder parentFolder = getParentFolder();

			if (parentFolder != null) {
				return parentFolder.getFolderId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	@Override
	public long getPrimaryKey() {
		return _folderId;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFolderConstants.getClassName());
	}

	@Override
	public long getUserId() {
		User user = getUser(_cmisFolder.getCreatedBy());

		if (user == null) {
			return 0;
		}
		else {
			return user.getUserId();
		}
	}

	@Override
	public String getUserName() {
		User user = getUser(_cmisFolder.getCreatedBy());

		if (user == null) {
			return StringPool.BLANK;
		}
		else {
			return user.getFullName();
		}
	}

	@Override
	public String getUserUuid() {
		User user = getUser(_cmisFolder.getCreatedBy());

		try {
			return user.getUserUuid();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public boolean hasInheritableLock() {
		return false;
	}

	@Override
	public boolean hasLock() {
		return false;
	}

	@Override
	public boolean isDefaultRepository() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public boolean isMountPoint() {
		return false;
	}

	@Override
	public boolean isRoot() {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isSupportsLocking() {
		return true;
	}

	@Override
	public boolean isSupportsMetadata() {
		return false;
	}

	@Override
	public boolean isSupportsMultipleUpload() {
		return false;
	}

	@Override
	public boolean isSupportsShortcuts() {
		return false;
	}

	@Override
	public boolean isSupportsSocial() {
		return false;
	}

	@Override
	public boolean isSupportsSubscribing() {
		return false;
	}

	@Override
	public void setCompanyId(long companyId) {
		_cmisRepository.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	@Override
	public void setGroupId(long groupId) {
		_cmisRepository.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
	}

	public void setPrimaryKey(long primaryKey) {
		setFolderId(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public void setUserId(long userId) {
	}

	@Override
	public void setUserName(String userName) {
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public void setUuid(String uuid) {
	}

	@Override
	public Folder toEscapedModel() {
		return this;
	}

	@Override
	public Folder toUnescapedModel() {
		return this;
	}

	@Override
	protected CMISRepository getCmisRepository() {
		return _cmisRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMISFolder.class);

	private final org.apache.chemistry.opencmis.client.api.Folder _cmisFolder;
	private final CMISRepository _cmisRepository;
	private long _folderId;
	private final String _uuid;

}