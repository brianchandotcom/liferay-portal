/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFolderLocalServiceImpl extends DLFolderLocalServiceBaseImpl {

	public DLFolder addFolder(
			long userId, long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validateFolder(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		DLFolder dlFolder = dlFolderPersistence.create(folderId);

		dlFolder.setUuid(serviceContext.getUuid());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(user.getCompanyId());
		dlFolder.setUserId(user.getUserId());
		dlFolder.setCreateDate(serviceContext.getCreateDate(now));
		dlFolder.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setMountPoint(mountPoint);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setOverrideFileEntryTypes(false);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFolderResources(
				dlFolder, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFolderResources(
				dlFolder, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.findByPrimaryKey(
				parentFolderId);

			parentDLFolder.setLastPostDate(now);

			dlFolderPersistence.update(parentDLFolder, false);
		}

		// App helper

		dlAppHelperLocalService.addFolder(
			new LiferayFolder(dlFolder), serviceContext);

		return dlFolder;
	}

	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFolder dlFolder : dlFolders) {
			deleteFolder(dlFolder);
		}

		dlFileEntryLocalService.deleteFileEntries(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		try {
			DLStoreUtil.deleteDirectory(
				group.getCompanyId(), groupId, StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(dlFolder);
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		int fileEntriesCount = 0;

		if ((status == WorkflowConstants.STATUS_ANY)) {
			fileEntriesCount = dlFileEntryPersistence.countByG_F(
				groupId, folderId);
		}
		else {
			fileEntriesCount = dlFolderFinder.countFE_ByG_F_S(
				groupId, folderId, status);
		}

		int fileShortcutsCount = dlFileShortcutPersistence.countByG_F_S(
			groupId, folderId, 0);

		return fileEntriesCount + fileShortcutsCount;
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return getFolders(groupId, parentFolderId, true);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.findByG_P(groupId, parentFolderId);
		}
		else {
			return dlFolderPersistence.findByG_P_M(
				groupId, parentFolderId, false);
		}
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.findByG_P(
				groupId, parentFolderId, start, end, obc);
		}
		else {
			return dlFolderPersistence.findByG_P_M(
				groupId, parentFolderId, false, start, end, obc);
		}
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return getFolders(groupId, parentFolderId, true, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_S(
			groupId, folderId, status, null, includeMountFolders, start, end,
			obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, start,
			end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_S(
			groupId, folderId, status, null, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return getFoldersCount(groupId, parentFolderId, true);
	}

	public int getFoldersCount(
			long groupId, long parentFolderId, boolean includeMountfolders)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.countByG_P(groupId, parentFolderId);
		}
		else {
			return dlFolderPersistence.countByG_P_M(
				groupId, parentFolderId, false);
		}
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return dlFileEntryFinder.countByG_F_S(groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int filesCount = dlFileEntryFinder.countByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			filesCount += getFoldersFileEntriesCount(
				groupId, folderIds, status);

			return filesCount;
		}
	}

	public DLFolder getMountFolder(long repositoryId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByRepositoryId(repositoryId);
	}

	public List<DLFolder> getMountFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.findByG_P_M(
			groupId, parentFolderId, true, start, end, obc);
	}

	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.countByG_P_M(groupId, parentFolderId, true);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
			dlFolder.getName());

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, long defaultFileEntryTypeId,
			List<Long> fileEntryTypeIds, boolean overrideFileEntryTypes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry types

		DLFolder dlFolder = null;

		if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFolder = dlFolderLocalService.updateFolderAndFileEntryTypes(
				folderId, parentFolderId, name, description,
				defaultFileEntryTypeId, fileEntryTypeIds,
				overrideFileEntryTypes, serviceContext);

			dlFileEntryTypeLocalService.cascadeFileEntryTypes(
				serviceContext.getUserId(), dlFolder);
		}

		// Workflow definitions

		List<ObjectValuePair<Long, String>> workflowDefinitions =
			new ArrayList<ObjectValuePair<Long, String>>();

		if (fileEntryTypeIds.isEmpty()) {
			fileEntryTypeIds.add(new Long(0));
		}
		else {
			workflowDefinitions.add(
				new ObjectValuePair<Long, String>(
					new Long(0), StringPool.BLANK));
		}

		for (long fileEntryTypeId : fileEntryTypeIds) {
			String workflowDefinition = ParamUtil.getString(
				serviceContext, "workflowDefinition" + fileEntryTypeId);

			workflowDefinitions.add(
				new ObjectValuePair<Long, String>(
					fileEntryTypeId, workflowDefinition));
		}

		workflowDefinitionLinkLocalService.updateWorkflowDefinitionLinks(
			serviceContext.getUserId(), serviceContext.getCompanyId(),
			serviceContext.getScopeGroupId(), DLFolder.class.getName(),
			folderId, workflowDefinitions);

		return dlFolder;
	}

	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateFolder(
			folderId, folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
	}

	public DLFolder updateFolderAndFileEntryTypes(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		if (!overrideFileEntryTypes) {
			fileEntryTypeIds = Collections.emptyList();
		}

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(folderId, dlFolder.getGroupId(), parentFolderId, name);

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);
		dlFolder.setOverrideFileEntryTypes(overrideFileEntryTypes);
		dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);

		dlFolderPersistence.update(dlFolder, false);

		// File entry types

		if (fileEntryTypeIds != null) {
			dlFileEntryTypeLocalService.updateFolderFileEntryTypes(
				dlFolder, fileEntryTypeIds, defaultFileEntryTypeId,
				serviceContext);
		}

		// App helper

		dlAppHelperLocalService.updateFolder(
			new LiferayFolder(dlFolder), serviceContext);

		return dlFolder;
	}

	public void updateLastPostDate(long folderId, Date lastPostDate)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		dlFolder.setLastPostDate(lastPostDate);

		dlFolderPersistence.update(dlFolder, false);
	}

	protected void addFolderResources(
			DLFolder dlFolder, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFolderResources(
			DLFolder dlFolder, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), groupPermissions, guestPermissions);
	}

	protected void addFolderResources(
			long folderId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, addGroupPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			long folderId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, groupPermissions, guestPermissions);
	}

	protected void deleteFolder(DLFolder dlFolder)
		throws PortalException, SystemException {

		// Folders

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		for (DLFolder curDLFolder : dlFolders) {
			deleteFolder(curDLFolder);
		}

		// Folder

		dlFolderPersistence.remove(dlFolder);

		// Resources

		resourceLocalService.deleteResource(
			dlFolder.getCompanyId(), DLFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFolder.getFolderId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// File entries

		dlFileEntryLocalService.deleteFileEntries(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		// File entry types

		dlFileEntryTypeLocalService.deleteFileEntryTypes(
			dlFolder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// App helper

		dlAppHelperLocalService.deleteFolder(new LiferayFolder(dlFolder));

		// Directory

		try {
			DLStoreUtil.deleteDirectory(
				dlFolder.getCompanyId(), dlFolder.getFolderId(),
				StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	protected long getParentFolderId(DLFolder dlFolder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (dlFolder.getFolderId() == parentFolderId) {
			return dlFolder.getParentFolderId();
		}
		else {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(dlFolder.getGroupId() != parentDLFolder.getGroupId())) {

				return dlFolder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, dlFolder.getGroupId(), dlFolder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return dlFolder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(groupId != parentDLFolder.getGroupId())) {

				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		validateFolderName(name);

		try {
			dlFileEntryLocalService.getFileEntry(groupId, parentFolderId, name);

			throw new DuplicateFileException(name);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		DLFolder dlFolder = dlFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((dlFolder != null) && (dlFolder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException(name);
		}
	}

	protected void validateFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validateFolder(folderId, groupId, parentFolderId, name);
	}

	protected void validateFolderName(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFolderLocalServiceImpl.class);

}