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
import com.liferay.portal.kernel.util.TreeModelTasksAdapter;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.TreeModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.InvalidFolderException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.RequiredFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLValidatorUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FolderIdComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		throws PortalException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(
			groupId, repositoryId, parentFolderId);
		Date now = new Date();

		validateFolder(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		DLFolder dlFolder = dlFolderPersistence.create(folderId);

		dlFolder.setUuid(serviceContext.getUuid());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(user.getCompanyId());
		dlFolder.setUserId(user.getUserId());
		dlFolder.setUserName(user.getFullName());
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setMountPoint(mountPoint);
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setTreePath(dlFolder.buildTreePath());
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setLastPostDate(now);
		dlFolder.setHidden(hidden);
		dlFolder.setRestrictionType(DLFolderConstants.RESTRICTION_TYPE_INHERIT);
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
		throws PortalException {

		return addFolder(
			userId, groupId, repositoryId, mountPoint, parentFolderId, name,
			description, false, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deleteAllByGroup(long)}
	 */
	@Deprecated
	@Override
	public void deleteAll(long groupId) throws PortalException {
		deleteAllByGroup(groupId);
	}

	@Override
	public void deleteAllByGroup(long groupId) throws PortalException {
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

	@Override
	public void deleteAllByRepository(long repositoryId)
		throws PortalException {

		Repository repository = repositoryLocalService.fetchRepository(
			repositoryId);

		long groupId = repositoryId;

		if (repository != null) {
			groupId = repository.getGroupId();
		}

		Group group = groupLocalService.getGroup(groupId);

		List<DLFolder> dlFolders = dlFolderPersistence.findByRepositoryId(
			repositoryId);

		for (DLFolder dlFolder : dlFolders) {
			dlFolderLocalService.deleteFolder(dlFolder);
		}

		if (repository != null) {
			dlFileEntryLocalService.deleteRepositoryFileEntries(
				repository.getRepositoryId(), repository.getDlFolderId());
		}
		else {
			dlFileEntryLocalService.deleteFileEntries(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			dlFileShortcutLocalService.deleteFileShortcuts(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		try {
			DLStoreUtil.deleteDirectory(
				group.getCompanyId(), repositoryId, StringPool.BLANK);
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
		type = SystemEventConstants.TYPE_DELETE
	)
	public DLFolder deleteFolder(DLFolder dlFolder) throws PortalException {
		return deleteFolder(dlFolder, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public DLFolder deleteFolder(
			DLFolder dlFolder, boolean includeTrashedEntries)
		throws PortalException {

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

		List<Long> fileEntryTypeIds = new ArrayList<>();

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
	public DLFolder deleteFolder(long folderId) throws PortalException {
		return dlFolderLocalService.deleteFolder(folderId, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		return dlFolderLocalService.deleteFolder(
			dlFolder, includeTrashedEntries);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteFolder(
			long userId, long folderId, boolean includeTrashedEntries)
		throws PortalException {

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
	public DLFolder fetchFolder(long folderId) {
		return dlFolderPersistence.fetchByPrimaryKey(folderId);
	}

	@Override
	public DLFolder fetchFolder(
		long groupId, long parentFolderId, String name) {

		return dlFolderPersistence.fetchByG_P_N(groupId, parentFolderId, name);
	}

	@Override
	public List<DLFolder> getCompanyFolders(
		long companyId, int start, int end) {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId) {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getFileEntriesAndFileShortcuts(long, long, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, null);

		return getFileEntriesAndFileShortcuts(
			groupId, folderId, queryDefinition);
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

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
		long groupId, long folderId, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return getFileEntriesAndFileShortcutsCount(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

		return dlFolderFinder.countFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public DLFolder getFolder(long folderId) throws PortalException {
		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	@Override
	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	@Override
	public long getFolderId(long companyId, long folderId) {
		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getGroupFolderIds(long,
	 *             long)}
	 */
	@Deprecated
	@Override
	public List<Long> getFolderIds(long groupId, long parentFolderId) {
		return getGroupFolderIds(groupId, parentFolderId);
	}

	@Override
	public List<DLFolder> getFolders(long groupId, long parentFolderId) {
		return getFolders(groupId, parentFolderId, true);
	}

	@Override
	public List<DLFolder> getFolders(
		long groupId, long parentFolderId, boolean includeMountfolders) {

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
		int start, int end, OrderByComparator<DLFolder> obc) {

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
		OrderByComparator<DLFolder> obc) {

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
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end, OrderByComparator<?> obc) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, (OrderByComparator<Object>)obc);

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
		OrderByComparator<?> obc) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, (OrderByComparator<Object>)obc);

		return getFoldersAndFileEntriesAndFileShortcuts(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition) {

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
		long groupId, long folderId, int status, boolean includeMountFolders) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

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
		boolean includeMountFolders) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition) {

		return dlFolderFinder.countF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId) {
		return getFoldersCount(groupId, parentFolderId, true);
	}

	@Override
	public int getFoldersCount(
		long groupId, long parentFolderId, boolean includeMountfolders) {

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
		boolean includeMountfolders) {

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
	public List<Long> getGroupFolderIds(long groupId, long parentFolderId) {
		List<Long> folderIds = new ArrayList<>();

		folderIds.add(parentFolderId);

		getGroupSubfolderIds(folderIds, groupId, parentFolderId);

		return folderIds;
	}

	@Override
	public void getGroupSubfolderIds(
		List<Long> folderIds, long groupId, long folderId) {

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getGroupSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	@Override
	public DLFolder getMountFolder(long repositoryId) throws PortalException {
		return dlFolderPersistence.findByR_M(repositoryId, true);
	}

	@Override
	public List<DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<DLFolder> obc) {

		return dlFolderPersistence.findByG_M_P_H(
			groupId, true, parentFolderId, false, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId) {
		return dlFolderPersistence.countByG_M_P_H(
			groupId, true, parentFolderId, false);
	}

	@Override
	public List<DLFolder> getNoAssetFolders() {
		return dlFolderFinder.findF_ByNoAssets();
	}

	@Override
	public List<Long> getRepositoryFolderIds(
		long repositoryId, long parentFolderId) {

		List<Long> folderIds = new ArrayList<>();

		folderIds.add(parentFolderId);

		getRepositorySubfolderIds(folderIds, repositoryId, parentFolderId);

		return folderIds;
	}

	@Override
	public List<DLFolder> getRepositoryFolders(
		long repositoryId, int start, int end) {

		return dlFolderPersistence.findByRepositoryId(repositoryId, start, end);
	}

	@Override
	public int getRepositoryFoldersCount(long repositoryId) {
		return dlFolderPersistence.countByRepositoryId(repositoryId);
	}

	@Override
	public void getRepositorySubfolderIds(
		List<Long> folderIds, long repositoryId, long folderId) {

		List<DLFolder> dlFolders = dlFolderPersistence.findByR_P(
			repositoryId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getRepositorySubfolderIds(
				folderIds, dlFolder.getRepositoryId(), dlFolder.getFolderId());
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getGroupSubfolderIds(List,
	 *             long, long)}
	 */
	@Deprecated
	@Override
	public void getSubfolderIds(
		List<Long> folderIds, long groupId, long folderId) {

		getGroupSubfolderIds(folderIds, groupId, folderId);
	}

	@Override
	public boolean hasFolderLock(long userId, long folderId) {
		return lockLocalService.hasLock(
			userId, DLFolder.class.getName(), folderId);
	}

	@Override
	public Lock lockFolder(long userId, long folderId) throws PortalException {
		return lockFolder(
			userId, folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	public Lock lockFolder(
			long userId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

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
		throws PortalException {

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

			dlFolder.setParentFolderId(parentFolderId);
			dlFolder.setTreePath(dlFolder.buildTreePath());
			dlFolder.setExpandoBridgeAttributes(serviceContext);

			dlFolderPersistence.update(dlFolder);

			rebuildTree(
				dlFolder.getCompanyId(), folderId, dlFolder.getTreePath(),
				true);

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
	public void rebuildTree(long companyId) throws PortalException {
		rebuildTree(
			companyId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringPool.SLASH, false);
	}

	@Override
	public void rebuildTree(
			long companyId, long parentFolderId, String parentTreePath,
			final boolean reindex)
		throws PortalException {

		TreePathUtil.rebuildTree(
			companyId, parentFolderId, parentTreePath,
			new TreeModelTasksAdapter<DLFolder>() {

				@Override
				public List<DLFolder> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return dlFolderPersistence.findByF_C_P_NotS(
						previousId, companyId, parentPrimaryKey,
						WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
						size, new FolderIdComparator(true));
				}

				@Override
				public void rebuildDependentModelsTreePaths(
						long parentPrimaryKey, String treePath)
					throws PortalException {

					dlFileEntryLocalService.setTreePaths(
						parentPrimaryKey, treePath, false);
					dlFileShortcutLocalService.setTreePaths(
						parentPrimaryKey, treePath);
					dlFileVersionLocalService.setTreePaths(
						parentPrimaryKey, treePath);
				}

				@Override
				public void reindexTreeModels(List<TreeModel> treeModels)
					throws PortalException {

					if (!reindex) {
						return;
					}

					Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
						DLFolder.class);

					for (TreeModel treeModel : treeModels) {
						indexer.reindex(treeModel);
					}
				}

			}
		);
	}

	@Override
	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		unlockFolder(dlFolder.getFolderId(), lockUuid);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFolder(long, long,
	 *             String, String, long, List, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException {

		int restrictionType = DLFolderConstants.RESTRICTION_TYPE_INHERIT;

		if (overrideFileEntryTypes) {
			restrictionType =
				DLFolderConstants.
					RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW;
		}

		return updateFolder(
			folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType, ServiceContext serviceContext)
		throws PortalException {

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

			Set<Long> originalFileEntryTypeIds = new HashSet<>();

			if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				originalFileEntryTypeIds = getFileEntryTypeIds(
					dlFolderPersistence.getDLFileEntryTypes(folderId));

				dlFolder = dlFolderLocalService.updateFolderAndFileEntryTypes(
					serviceContext.getUserId(), folderId, parentFolderId, name,
					description, defaultFileEntryTypeId, fileEntryTypeIds,
					restrictionType, serviceContext);

				dlFileEntryTypeLocalService.cascadeFileEntryTypes(
					serviceContext.getUserId(), dlFolder);
			}

			// Workflow definitions

			List<ObjectValuePair<Long, String>> workflowDefinitionOVPs =
				new ArrayList<>();

			if (restrictionType ==
					DLFolderConstants.
						RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {

				workflowDefinitionOVPs.add(
					new ObjectValuePair<Long, String>(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
						StringPool.BLANK));

				for (long fileEntryTypeId : fileEntryTypeIds) {
					String workflowDefinition = ParamUtil.getString(
						serviceContext, "workflowDefinition" + fileEntryTypeId);

					workflowDefinitionOVPs.add(
						new ObjectValuePair<Long, String>(
							fileEntryTypeId, workflowDefinition));
				}
			}
			else if (restrictionType ==
						DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				if (originalFileEntryTypeIds.isEmpty()) {
					originalFileEntryTypeIds.add(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);
				}

				for (long originalFileEntryTypeId : originalFileEntryTypeIds) {
					workflowDefinitionOVPs.add(
						new ObjectValuePair<Long, String>(
							originalFileEntryTypeId, StringPool.BLANK));
				}
			}
			else if (restrictionType ==
						DLFolderConstants.RESTRICTION_TYPE_WORKFLOW) {

				String workflowDefinition = ParamUtil.getString(
					serviceContext,
					"workflowDefinition" +
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL);

				workflowDefinitionOVPs.add(
					new ObjectValuePair<Long, String>(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
						workflowDefinition));

				for (long originalFileEntryTypeId : originalFileEntryTypeIds) {
					workflowDefinitionOVPs.add(
						new ObjectValuePair<Long, String>(
							originalFileEntryTypeId, StringPool.BLANK));
				}
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

	/**
	 * @deprecated As of 7.0.0, replaced {@link #updateFolder(long, long,
	 *             String, String, long, List, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException {

		int restrictionType = DLFolderConstants.RESTRICTION_TYPE_INHERIT;

		if (overrideFileEntryTypes) {
			restrictionType =
				DLFolderConstants.
					RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW;
		}

		return updateFolder(
			serviceContext.getScopeGroupId(), folderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType, ServiceContext serviceContext)
		throws PortalException {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = getDLFolder(folderId);

			parentFolderId = dlFolder.getParentFolderId();
		}

		return updateFolder(
			folderId, parentFolderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #
	 *             updateFolderAndFileEntryTypes(long, long, long, String,
	 *             String, long, List, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFolder updateFolderAndFileEntryTypes(
			long userId, long folderId, long parentFolderId, String name,
			String description, long defaultFileEntryTypeId,
			List<Long> fileEntryTypeIds, boolean overrideFileEntryTypes,
			ServiceContext serviceContext)
		throws PortalException {

		int restrictionType = DLFolderConstants.RESTRICTION_TYPE_INHERIT;

		if (overrideFileEntryTypes) {
			restrictionType =
				DLFolderConstants.
					RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW;
		}

		return updateFolderAndFileEntryTypes(
			userId, folderId, parentFolderId, name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);
	}

	@Override
	public DLFolder updateFolderAndFileEntryTypes(
			long userId, long folderId, long parentFolderId, String name,
			String description, long defaultFileEntryTypeId,
			List<Long> fileEntryTypeIds, int restrictionType,
			ServiceContext serviceContext)
		throws PortalException {

		if ((restrictionType ==
				DLFolderConstants.
					RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) &&
			fileEntryTypeIds.isEmpty()) {

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

			if (restrictionType == DLFolderConstants.RESTRICTION_TYPE_INHERIT) {
				fileEntryTypeIds = Collections.emptyList();
			}

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			parentFolderId = getParentFolderId(dlFolder, parentFolderId);

			validateFolder(
				folderId, dlFolder.getGroupId(), parentFolderId, name);

			dlFolder.setParentFolderId(parentFolderId);
			dlFolder.setTreePath(dlFolder.buildTreePath());
			dlFolder.setName(name);
			dlFolder.setDescription(description);
			dlFolder.setExpandoBridgeAttributes(serviceContext);
			dlFolder.setRestrictionType(restrictionType);
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
		incrementClass = DateOverrideIncrement.class
	)
	@Override
	public void updateLastPostDate(long folderId, Date lastPostDate)
		throws PortalException {

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
		throws PortalException {

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
		throws PortalException {

		resourceLocalService.addResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFolderResources(
			DLFolder dlFolder, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), groupPermissions, guestPermissions);
	}

	protected void addFolderResources(
			long folderId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, addGroupPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			long folderId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, groupPermissions, guestPermissions);
	}

	protected Set<Long> getFileEntryTypeIds(
		List<DLFileEntryType> dlFileEntryTypes) {

		Set<Long> fileEntryTypeIds = new HashSet<>();

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
		}

		return fileEntryTypeIds;
	}

	protected long getParentFolderId(DLFolder dlFolder, long parentFolderId)
		throws PortalException {

		parentFolderId = getParentFolderId(
			dlFolder.getGroupId(), dlFolder.getRepositoryId(), parentFolderId);

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (dlFolder.getFolderId() == parentFolderId) {
			throw new InvalidFolderException(
				InvalidFolderException.CANNOT_MOVE_INTO_ITSELF, parentFolderId);
		}

		List<Long> subfolderIds = new ArrayList<>();

		getGroupSubfolderIds(
			subfolderIds, dlFolder.getGroupId(), dlFolder.getFolderId());

		if (subfolderIds.contains(parentFolderId)) {
			throw new InvalidFolderException(
				InvalidFolderException.CANNOT_MOVE_INTO_CHILD_FOLDER,
				parentFolderId);
		}

		return parentFolderId;
	}

	protected long getParentFolderId(
			long groupId, long repositoryId, long parentFolderId)
		throws PortalException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		DLFolder parentDLFolder = dlFolderPersistence.findByPrimaryKey(
			parentFolderId);

		if (parentDLFolder.getGroupId() != groupId) {
			throw new NoSuchFolderException(
				String.format(
					"No folder exists with the primary key %s in group %s",
					parentFolderId, groupId));
		}

		if ((parentDLFolder.getRepositoryId() != repositoryId) &&
			(parentDLFolder.getRepositoryId() != groupId)) {

			Repository repository = repositoryLocalService.getRepository(
				repositoryId);

			if (repository.getGroupId() != parentDLFolder.getGroupId()) {
				throw new NoSuchFolderException(
					String.format(
						"No folder exists with the primary key %s in " +
							"repository %s",
						parentFolderId, repositoryId));
			}
		}

		return parentDLFolder.getFolderId();
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException {

		validateFolderName(name);

		DLValidatorUtil.validateDirectoryName(name);

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
		throws PortalException {

		long folderId = 0;

		validateFolder(folderId, groupId, parentFolderId, name);
	}

	protected void validateFolderName(String folderName)
		throws PortalException {

		if (folderName.contains(StringPool.SLASH)) {
			throw new FolderNameException(folderName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderLocalServiceImpl.class);

}