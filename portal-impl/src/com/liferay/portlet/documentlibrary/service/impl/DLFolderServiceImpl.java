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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.ArrayList;
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
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId,
			ActionKeys.ADD_FOLDER);

		return dlFolderLocalService.addFolder(
			getUserId(), groupId, repositoryId, mountPoint, parentFolderId,
			name, description, false, serviceContext);
	}

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		deleteFolder(folderId, true);
	}

	@Override
	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.DELETE);

		dlFolderLocalService.deleteFolder(
			getUserId(), folderId, includeTrashedEntries);
	}

	@Override
	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		deleteFolder(dlFolder.getFolderId());
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return dlFolderFinder.filterFindFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return dlFolderFinder.filterCountFE_FS_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return dlFolderFinder.filterCountFE_FS_ByG_F_M(
			groupId, folderId, mimeTypes, queryDefinition);
	}

	@Override
	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	@Override
	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	@Override
	public List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return folderIds;
	}

	@Override
	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId, ActionKeys.VIEW);

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
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, true,
			start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, null, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, true);
	}

	@Override
	public int getFoldersCount(
			long groupId, long parentFolderId, int status,
			boolean includeMountfolders)
		throws SystemException {

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
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, parentFolderId, ActionKeys.VIEW);

		return dlFolderPersistence.filterFindByG_M_P_H(
			groupId, true, parentFolderId, false, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.filterCountByG_M_P_H(
			groupId, true, parentFolderId, false);
	}

	@Override
	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<DLFolder> dlFolders = dlFolderPersistence.filterFindByG_P_H_S(
			groupId, folderId, false, WorkflowConstants.STATUS_APPROVED);

		for (DLFolder dlFolder : dlFolders) {
			if (dlFolder.isInHiddenFolder() || dlFolder.isInTrashContainer()) {
				continue;
			}

			folderIds.add(dlFolder.getFolderId());

			getSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	@Override
	public List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws PortalException, SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		getSubfolderIds(folderIds, groupId, folderId);

		return folderIds;
	}

	@Override
	public boolean hasFolderLock(long folderId)
		throws PortalException, SystemException {

		return lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);
	}

	@Override
	public boolean hasInheritableLock(long folderId)
		throws PortalException, SystemException {

		boolean inheritable = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFolder.class.getName(), folderId);

			inheritable = lock.isInheritable();
		}
		catch (ExpiredLockException ele) {
		}
		catch (NoSuchLockException nsle) {
		}

		return inheritable;
	}

	@Override
	public boolean isFolderLocked(long folderId) throws SystemException {
		return lockLocalService.isLocked(DLFolder.class.getName(), folderId);
	}

	@Override
	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		return dlFolderLocalService.lockFolder(
			getUserId(), folderId, owner, inheritable, expirationTime);
	}

	@Override
	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, companyId, expirationTime);
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

		try {
			DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

			DLFolderPermission.check(
				getPermissionChecker(), dlFolder, ActionKeys.UPDATE);
		}
		catch (NoSuchFolderException nsfe) {
		}

		dlFolderLocalService.unlockFolder(folderId, lockUuid);
	}

	@Override
	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(), folderId,
			ActionKeys.UPDATE);

		return dlFolderLocalService.updateFolder(
			folderId, name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		boolean verified = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFolder.class.getName(), folderId);

			if (!lock.isInheritable()) {
				throw new NoSuchLockException();
			}

			if (lock.getUuid().equals(lockUuid)) {
				verified = true;
			}
		}
		catch (ExpiredLockException ele) {
			throw new NoSuchLockException(ele);
		}

		return verified;
	}

}