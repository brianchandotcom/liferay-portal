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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.DateOverrideIncrement;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TreeModelFinder;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.RequiredFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLValidationUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FolderIdComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFolderLocalServiceImpl extends DLFolderLocalServiceBaseImpl {

	@Override
	public DLFolder addFolder(
			long userId, long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			boolean hidden, ServiceContext serviceContext)
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
		dlFolder.setUserName(user.getFullName());
		dlFolder.setCreateDate(serviceContext.getCreateDate(now));
		dlFolder.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setMountPoint(mountPoint);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setTreePath(dlFolder.buildTreePath());
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setLastPostDate(now);
		dlFolder.setHidden(hidden);
		dlFolder.setOverrideFileEntryTypes(false);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFolderResources(
				dlFolder, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			if (serviceContext.isDeriveDefaultPermissions()) {
				serviceContext.deriveDefaultPermissions(
					repositoryId, DLFolderConstants.getClassName());
			}

			addFolderResources(
				dlFolder, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFolderLocalService.updateLastPostDate(parentFolderId, now);
		}

		return dlFolder;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by more general {@link #addFolder(long,
	 *             long, long, boolean, long, String, String, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFolder addFolder(
			long userId, long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFolder(
			userId, groupId, repositoryId, mountPoint, parentFolderId, name,
			description, false, serviceContext);
	}

	@Override
	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		List<DLFolder> dlFolders = dlFolderPersistence.findByGroupId(groupId);

		for (DLFolder dlFolder : dlFolders) {
			dlFolderLocalService.deleteFolder(dlFolder);
		}

		dlFileEntryLocalService.deleteFileEntries(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		dlFileEntryTypeLocalService.deleteFileEntryTypes(groupId);

		dlFileShortcutLocalService.deleteFileShortcuts(
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

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public DLFolder deleteFolder(DLFolder dlFolder)
		throws PortalException, SystemException {

		return deleteFolder(dlFolder, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public DLFolder deleteFolder(
			DLFolder dlFolder, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		// Folders

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		for (DLFolder curDLFolder : dlFolders) {
			if (includeTrashedEntries || !curDLFolder.isInTrashExplicitly()) {
				dlFolderLocalService.deleteFolder(
					curDLFolder, includeTrashedEntries);
			}
		}

		// Resources

		resourceLocalService.deleteResource(
			dlFolder.getCompanyId(), DLFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFolder.getFolderId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// File entries

		dlFileEntryLocalService.deleteFileEntries(
			dlFolder.getGroupId(), dlFolder.getFolderId(),
			includeTrashedEntries);

		// File entry types

		List<Long> fileEntryTypeIds = new ArrayList<Long>();

		for (DLFileEntryType dlFileEntryType :
				dlFileEntryTypeLocalService.getDLFolderDLFileEntryTypes(
					dlFolder.getFolderId())) {

			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
		}

		if (fileEntryTypeIds.isEmpty()) {
			fileEntryTypeIds.add(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);
		}

		dlFileEntryTypeLocalService.unsetFolderFileEntryTypes(
			dlFolder.getFolderId());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			dlFolder.getGroupId(), dlFolder.getFolderId(),
			includeTrashedEntries);

		// Expando

		expandoRowLocalService.deleteRows(dlFolder.getFolderId());

		// Folder

		dlFolderPersistence.remove(dlFolder);

		// Directory

		try {
			if (includeTrashedEntries) {
				DLStoreUtil.deleteDirectory(
					dlFolder.getCompanyId(), dlFolder.getFolderId(),
					StringPool.BLANK);
			}
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}

		// Workflow

		for (long fileEntryTypeId : fileEntryTypeIds) {
			WorkflowDefinitionLink workflowDefinitionLink = null;

			try {
				workflowDefinitionLink =
					workflowDefinitionLinkLocalService.
						getWorkflowDefinitionLink(
							dlFolder.getCompanyId(), dlFolder.getGroupId(),
							DLFolder.class.getName(), dlFolder.getFolderId(),
							fileEntryTypeId);
			}
			catch (NoSuchWorkflowDefinitionLinkException nswdle) {
				continue;
			}

			workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
				workflowDefinitionLink);
		}

		return dlFolder;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderLocalService.deleteFolder(folderId, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		return dlFolderLocalService.deleteFolder(
			dlFolder, includeTrashedEntries);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteFolder(
			long userId, long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		boolean hasLock = hasFolderLock(userId, folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(
				userId, folderId, null, false,
				DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {
			return deleteFolder(folderId, includeTrashedEntries);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
			}
		}
	}

	@Override
	public DLFolder fetchFolder(long folderId) throws SystemException {
		return dlFolderPersistence.fetchByPrimaryKey(folderId);
	}

	@Override
	public DLFolder fetchFolder(long groupId, long parentFolderId, String name)
		throws SystemException {

		return dlFolderPersistence.fetchByG_P_N(groupId, parentFolderId, name);
	}

	@Override
	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFileEntriesAndFileShortcuts(long, long, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getFileEntriesAndFileShortcuts(
			groupId, folderId, queryDefinition);
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFileEntriesAndFileShortcutsCount(long, long,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getFileEntriesAndFileShortcutsCount(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return dlFolderFinder.countFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	@Override
	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	@Override
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

	@Override
	public List<Long> getFolderIds(long groupId, long parentFolderId)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(parentFolderId);

		getSubfolderIds(folderIds, groupId, parentFolderId);

		return folderIds;
	}

	@Override
	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return getFolders(groupId, parentFolderId, true);
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.findByG_P(groupId, parentFolderId);
		}
		else {
			return dlFolderPersistence.findByG_M_P_H(
				groupId, false, parentFolderId, false);
		}
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.findByG_P(
				groupId, parentFolderId, start, end, obc);
		}
		else {
			return dlFolderPersistence.findByG_M_P_H(
				groupId, false, parentFolderId, false, start, end, obc);
		}
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return getFolders(groupId, parentFolderId, true, start, end, obc);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFoldersAndFileEntriesAndFileShortcuts(long, long,
	 *             String[], boolean, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	 *             String[], boolean, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	 *             String[], boolean, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFoldersAndFileEntriesAndFileShortcutsCount(long, long,
	 *             String[], boolean, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return getFoldersCount(groupId, parentFolderId, true);
	}

	@Override
	public int getFoldersCount(
			long groupId, long parentFolderId, boolean includeMountfolders)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.countByG_P(groupId, parentFolderId);
		}
		else {
			return dlFolderPersistence.countByG_M_P_H(
				groupId, false, parentFolderId, false);
		}
	}

	@Override
	public int getFoldersCount(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.countByG_P_H_S(
				groupId, parentFolderId, false, status);
		}
		else {
			return dlFolderPersistence.countByG_M_P_H_S(
				groupId, false, parentFolderId, false, status);
		}
	}

	@Override
	public DLFolder getMountFolder(long repositoryId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByRepositoryId(repositoryId);
	}

	@Override
	public List<DLFolder> getMountFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.findByG_M_P_H(
			groupId, true, parentFolderId, false, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.countByG_M_P_H(
			groupId, true, parentFolderId, false);
	}

	@Override
	public List<DLFolder> getNoAssetFolders() throws SystemException {
		return dlFolderFinder.findF_ByNoAssets();
	}

	@Override
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

	@Override
	public boolean hasFolderLock(long userId, long folderId)
		throws SystemException {

		return lockLocalService.hasLock(
			userId, DLFolder.class.getName(), folderId);
	}

	@Override
	public Lock lockFolder(long userId, long folderId)
		throws PortalException, SystemException {

		return lockFolder(
			userId, folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	public Lock lockFolder(
			long userId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		return lockLocalService.lock(
			userId, DLFolder.class.getName(), folderId, owner, inheritable,
			expirationTime);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean hasLock = hasFolderLock(userId, folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(userId, folderId);
		}

		try {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			parentFolderId = getParentFolderId(dlFolder, parentFolderId);

			validateFolder(
				dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
				dlFolder.getName());

			dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
			dlFolder.setParentFolderId(parentFolderId);
			dlFolder.setTreePath(dlFolder.buildTreePath());
			dlFolder.setExpandoBridgeAttributes(serviceContext);

			dlFolderPersistence.update(dlFolder);

			return dlFolder;
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
			}
		}
	}

	@Override
	public void rebuildTree(long companyId) throws SystemException {
		TreePathUtil.rebuildTree(
			companyId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new TreeModelFinder<DLFolder>() {

				@Override
				public List<DLFolder> findTreeModels(
						long previousId, long companyId, long parentPrimaryKey,
						int size)
					throws SystemException {

					return dlFolderPersistence.findByF_C_P_NotS(
						previousId, companyId, parentPrimaryKey,
						WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
						size, new FolderIdComparator(true));
				}

			}
		);
	}

	@Override
	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		unlockFolder(dlFolder.getFolderId(), lockUuid);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockLocalService.getLock(
					DLFolder.class.getName(), folderId);

				if (!lockUuid.equals(lock.getUuid())) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if (pe instanceof ExpiredLockException ||
					pe instanceof NoSuchLockException) {
				}
				else {
					throw pe;
				}
			}
		}

		lockLocalService.unlock(DLFolder.class.getName(), folderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean hasLock = hasFolderLock(serviceContext.getUserId(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(
				serviceContext.getUserId(), folderId, null, false,
				DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {

			// File entry types

			DLFolder dlFolder = null;

			if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				dlFolder = dlFolderLocalService.updateFolderAndFileEntryTypes(
					serviceContext.getUserId(), folderId, parentFolderId, name,
					description, defaultFileEntryTypeId, fileEntryTypeIds,
					overrideFileEntryTypes, serviceContext);

				dlFileEntryTypeLocalService.cascadeFileEntryTypes(
					serviceContext.getUserId(), dlFolder);
			}

			// Workflow definitions

			List<ObjectValuePair<Long, String>> workflowDefinitionOVPs =
				new ArrayList<ObjectValuePair<Long, String>>();

			if (fileEntryTypeIds.isEmpty()) {
				fileEntryTypeIds.add(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);
			}
			else {
				workflowDefinitionOVPs.add(
					new ObjectValuePair<Long, String>(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
						StringPool.BLANK));
			}

			for (long fileEntryTypeId : fileEntryTypeIds) {
				String workflowDefinition = StringPool.BLANK;

				if (overrideFileEntryTypes ||
					(folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

					workflowDefinition = ParamUtil.getString(
						serviceContext, "workflowDefinition" + fileEntryTypeId);
				}

				workflowDefinitionOVPs.add(
					new ObjectValuePair<Long, String>(
						fileEntryTypeId, workflowDefinition));
			}

			workflowDefinitionLinkLocalService.updateWorkflowDefinitionLinks(
				serviceContext.getUserId(), serviceContext.getCompanyId(),
				serviceContext.getScopeGroupId(), DLFolder.class.getName(),
				folderId, workflowDefinitionOVPs);

			return dlFolder;
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateFolder(
			folderId, folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
	}

	@Override
	public DLFolder updateFolderAndFileEntryTypes(
			long userId, long folderId, long parentFolderId, String name,
			String description, long defaultFileEntryTypeId,
			List<Long> fileEntryTypeIds, boolean overrideFileEntryTypes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (overrideFileEntryTypes && fileEntryTypeIds.isEmpty()) {
			throw new RequiredFileEntryTypeException();
		}

		boolean hasLock = hasFolderLock(userId, folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(
				userId, folderId, null, false,
				DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {

			// Folder

			if (!overrideFileEntryTypes) {
				fileEntryTypeIds = Collections.emptyList();
			}

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			parentFolderId = getParentFolderId(dlFolder, parentFolderId);

			validateFolder(
				folderId, dlFolder.getGroupId(), parentFolderId, name);

			dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
			dlFolder.setParentFolderId(parentFolderId);
			dlFolder.setTreePath(dlFolder.buildTreePath());
			dlFolder.setName(name);
			dlFolder.setDescription(description);
			dlFolder.setExpandoBridgeAttributes(serviceContext);
			dlFolder.setOverrideFileEntryTypes(overrideFileEntryTypes);
			dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);

			dlFolderPersistence.update(dlFolder);

			// File entry types

			if (fileEntryTypeIds != null) {
				dlFileEntryTypeLocalService.updateFolderFileEntryTypes(
					dlFolder, fileEntryTypeIds, defaultFileEntryTypeId,
					serviceContext);
			}

			return dlFolder;
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(folderId, lock.getUuid());
			}
		}
	}

	@BufferedIncrement(
		configuration = "DLFolderEntry",
		incrementClass = DateOverrideIncrement.class)
	@Override
	public void updateLastPostDate(long folderId, Date lastPostDate)
		throws PortalException, SystemException {

		if (ExportImportThreadLocal.isImportInProcess() ||
			(folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ||
			(lastPostDate == null)) {

			return;
		}

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		if (lastPostDate.before(dlFolder.getLastPostDate())) {
			return;
		}

		dlFolder.setLastPostDate(lastPostDate);

		dlFolderPersistence.update(dlFolder);
	}

	@Override
	public DLFolder updateStatus(
			long userId, long folderId, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		int oldStatus = dlFolder.getStatus();

		dlFolder.setStatus(status);
		dlFolder.setStatusByUserId(user.getUserId());
		dlFolder.setStatusByUserName(user.getFullName());
		dlFolder.setStatusDate(new Date());

		dlFolderPersistence.update(dlFolder);

		// Asset

		if (status == WorkflowConstants.STATUS_APPROVED) {
			assetEntryLocalService.updateVisible(
				DLFolder.class.getName(), dlFolder.getFolderId(), true);
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			assetEntryLocalService.updateVisible(
				DLFolder.class.getName(), dlFolder.getFolderId(), false);
		}

		// Indexer

		if (((status == WorkflowConstants.STATUS_APPROVED) ||
			 (status == WorkflowConstants.STATUS_IN_TRASH) ||
			 (oldStatus == WorkflowConstants.STATUS_IN_TRASH)) &&
			((serviceContext == null) || serviceContext.isIndexingEnabled())) {

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DLFolderConstants.getClassName());

			indexer.reindex(dlFolder);
		}

		return dlFolder;
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

	protected long getParentFolderId(DLFolder dlFolder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (dlFolder.getFolderId() == parentFolderId) {
			return dlFolder.getParentFolderId();
		}

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

		DLValidationUtil.validateDirectoryName(name);

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

	protected void validateFolderName(String folderName)
		throws PortalException {

		if (folderName.contains(StringPool.SLASH)) {
			throw new FolderNameException(folderName);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFolderLocalServiceImpl.class);

}