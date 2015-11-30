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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DLGroupServiceSettings;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFolderServiceImpl extends DLFolderServiceBaseImpl {

	@Override
	public DLFolder addFolder(
			long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), groupId, repositoryId, mountPoint, parentFolderId,
			name, description, false, serviceContext);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		deleteFolder(folderId, true);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.DELETE);

		dlFolderLocalService.deleteFolder(
			getUserId(), folderId, includeTrashedEntries);
	}

	@Override
	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		deleteFolder(dlFolder.getFolderId());
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, null);

		return dlFolderFinder.filterFindFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return dlFolderFinder.filterCountFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return dlFolderFinder.filterCountFE_FS_ByG_F_M(
			groupId, folderId, mimeTypes, queryDefinition);
	}

	@Override
	public DLFolder getFolder(long folderId) throws PortalException {
		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	@Override
	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	@Override
	public List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return folderIds;
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders, int start, int end,
			OrderByComparator<DLFolder> obc)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, parentFolderId,
				ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		if (includeMountfolders) {
			return dlFolderPersistence.filterFindByG_P_H_S(
				groupId, parentFolderId, false, status, start, end, obc);
		}
		else {
			return dlFolderPersistence.filterFindByG_M_P_H_S(
				groupId, false, parentFolderId, false, status, start, end, obc);
		}
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator<DLFolder> obc)
		throws PortalException {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, true,
			start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, (OrderByComparator<Object>)obc);

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, (OrderByComparator<Object>)obc);

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws PortalException {

		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, true);
	}

	@Override
	public int getFoldersCount(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, parentFolderId,
				ActionKeys.VIEW)) {

			return 0;
		}

		if (includeMountfolders) {
			return dlFolderPersistence.filterCountByG_P_H_S(
				groupId, parentFolderId, false, status);
		}
		else {
			return dlFolderPersistence.filterCountByG_M_P_H_S(
				groupId, false, parentFolderId, false, status);
		}
	}

	@Override
	public List<DLFolder> getMountFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator<DLFolder> obc)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, parentFolderId,
				ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		DLGroupServiceSettings dlGroupServiceSettings =
			DLGroupServiceSettings.getInstance(groupId);

		if (dlGroupServiceSettings.isShowHiddenMountFolders()) {
			return dlFolderPersistence.filterFindByG_M_P(
				groupId, true, parentFolderId, start, end, obc);
		}
		else {
			return dlFolderPersistence.filterFindByG_M_P_H(
				groupId, true, parentFolderId, false, start, end, obc);
		}
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, parentFolderId,
				ActionKeys.VIEW)) {

			return 0;
		}

		return dlFolderPersistence.filterCountByG_M_P_H(
			groupId, true, parentFolderId, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	 *             long, boolean)}
	 */
	@Deprecated
	@Override
	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws PortalException {

		getSubfolderIds(folderIds, groupId, folderId, true);
	}

	@Override
	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId, boolean recurse)
		throws PortalException {

		if (!DLFolderPermission.contains(
				getPermissionChecker(), groupId, folderId, ActionKeys.VIEW)) {

			return;
		}

		List<DLFolder> dlFolders = dlFolderPersistence.filterFindByG_P_H_S(
			groupId, folderId, false, WorkflowConstants.STATUS_APPROVED);

		for (DLFolder dlFolder : dlFolders) {
			if (dlFolder.isInHiddenFolder() || dlFolder.isInTrash()) {
				continue;
			}

			folderIds.add(dlFolder.getFolderId());

			if (recurse) {
				getSubfolderIds(
					folderIds, dlFolder.getGroupId(), dlFolder.getFolderId(),
					recurse);
			}
		}
	}

	@Override
	public List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws PortalException {

		List<Long> folderIds = new ArrayList<>();

		getSubfolderIds(folderIds, groupId, folderId, recurse);

		return folderIds;
	}

	@Override
	public boolean hasFolderLock(long folderId) throws PortalException {
		return LockManagerUtil.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);
	}

	@Override
	public boolean hasInheritableLock(long folderId) throws PortalException {
		return dlFolderLocalService.hasInheritableLock(folderId);
	}

	@Override
	public boolean isFolderLocked(long folderId) {
		return LockManagerUtil.isLocked(DLFolder.class.getName(), folderId);
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		return dlFolderLocalService.lockFolder(
			getUserId(), folderId, owner, inheritable, expirationTime);
	}

	@Override
	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			permissionChecker, dlFolder, ActionKeys.UPDATE);

		DLFolderPermission.check(
			permissionChecker, serviceContext.getScopeGroupId(), parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.moveFolder(
			getUserId(), folderId, parentFolderId, serviceContext);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return LockManagerUtil.refresh(lockUuid, companyId, expirationTime);
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

		DLFolder dlFolder = dlFolderLocalService.fetchFolder(folderId);

		if (dlFolder != null) {
			DLFolderPermission.check(
				getPermissionChecker(), dlFolder, ActionKeys.UPDATE);
		}

		dlFolderLocalService.unlockFolder(folderId, lockUuid);
	}

	@Override
	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType, ServiceContext serviceContext)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(), folderId,
			ActionKeys.UPDATE);

		serviceContext.setUserId(getUserId());

		return dlFolderLocalService.updateFolder(
			folderId, parentFolderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by more general {@link
	 *             #updateFolder(long, String, String, long, List, int,
	 *             ServiceContext)}
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

		return dlFolderLocalService.updateFolder(
			folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	@Override
	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			int restrictionType, ServiceContext serviceContext)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(), folderId,
			ActionKeys.UPDATE);

		serviceContext.setUserId(getUserId());

		return dlFolderLocalService.updateFolder(
			folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, restrictionType, serviceContext);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		return dlFolderLocalService.verifyInheritableLock(folderId, lockUuid);
	}

}