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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
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
			name, description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.DELETE);

		boolean hasLock = hasFolderLock(folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = doLockFolder(
				folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {
			dlFolderLocalService.deleteFolder(folderId);
		}
		finally {
			if (!hasLock) {

				// Unlock

				doUnlockFolder(dlFolder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public void deleteFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		deleteFolder(dlFolder.getFolderId());
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.filterFindFE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		int fileEntriesCount = 0;

		if ((status == WorkflowConstants.STATUS_ANY) &&
			!InlineSQLHelperUtil.isEnabled(groupId)) {

			fileEntriesCount = dlFileEntryPersistence.countByG_F(
				groupId, folderId);
		}
		else {
			fileEntriesCount = dlFolderFinder.filterCountFE_ByG_F_S(
				groupId, folderId, status);
		}

		int fileShortcutsCount = dlFileShortcutPersistence.filterCountByG_F_S(
			groupId, folderId, 0);

		return fileEntriesCount + fileShortcutsCount;
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes)
		throws SystemException {

		return dlFolderFinder.filterCountFE_FS_ByG_F_S(groupId, folderId,
			status, mimeTypes);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(
			groupId, parentFolderId, name);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.VIEW);

		return dlFolder;
	}

	public long[] getFolderIds(long groupId, long folderId)
		throws SystemException {

		List<Long> folderIds = getSubfolderIds(groupId, folderId, true);

		folderIds.add(0, folderId);

		return ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()]));
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountfolders,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		if (includeMountfolders) {
			return dlFolderPersistence.filterFindByG_P(
				groupId, parentFolderId, start, end, obc);
		}
		else {
			return dlFolderPersistence.filterFindByG_P_M(
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

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_S(
			groupId, folderId, status, null, includeMountFolders, start, end,
			obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderFinder.filterFindF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, start,
			end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_S(
			groupId, folderId, status, null, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderFinder.filterCountF_FE_FS_ByG_F_S(
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
			return dlFolderPersistence.filterCountByG_P(
				groupId, parentFolderId);
		}
		else {
			return dlFolderPersistence.filterCountByG_P_M(
				groupId, parentFolderId, false);
		}

	}

	public List<DLFolder> getMountFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.filterFindByG_P_M(
			groupId, parentFolderId, true, start, end, obc);
	}

	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.filterCountByG_P_M(
			groupId, parentFolderId, true);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderPersistence.filterFindByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	public List<Long> getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		getSubfolderIds(folderIds, groupId, folderId);

		return folderIds;
	}

	public boolean hasFolderLock(long folderId)
		throws PortalException, SystemException {

		return lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);
	}

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

	public boolean isFolderLocked(long folderId) throws SystemException {
		return lockLocalService.isLocked(DLFolder.class.getName(), folderId);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return lockFolder(
			folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		return doLockFolder(folderId, owner, inheritable, expirationTime);
	}

	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), dlFolder, ActionKeys.UPDATE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = lockFolder(folderId);
		}

		try {
			return dlFolderLocalService.moveFolder(
				folderId, parentFolderId, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(dlFolder.getGroupId(), folderId, lock.getUuid());
			}
		}
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, expirationTime);
	}

	public void unlockFolder(long groupId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		try {
			DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

			DLFolderPermission.check(
				getPermissionChecker(), dlFolder, ActionKeys.UPDATE);
		}
		catch (NoSuchFolderException nsfe) {
		}

		doUnlockFolder(groupId, folderId, lockUuid);
	}

	public void unlockFolder(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws PortalException, SystemException {

		DLFolder dlFolder = getFolder(groupId, parentFolderId, name);

		unlockFolder(groupId, dlFolder.getFolderId(), lockUuid);
	}

	public DLFolder updateFolder(
			long folderId, String name, String description,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			boolean overrideFileEntryTypes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(), folderId,
			ActionKeys.UPDATE);

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFolder.class.getName(), folderId);

		Lock lock = null;

		if (!hasLock) {

			// Lock

			lock = doLockFolder(
				folderId, null, false, DLFolderImpl.LOCK_EXPIRATION_TIME);
		}

		try {
			return dlFolderLocalService.updateFolder(
				folderId, name, description, defaultFileEntryTypeId,
				fileEntryTypeIds, overrideFileEntryTypes, serviceContext);
		}
		finally {
			if (!hasLock) {

				// Unlock

				unlockFolder(
					serviceContext.getScopeGroupId(), folderId, lock.getUuid());
			}
		}
	}

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

	protected Lock doLockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		if ((expirationTime <= 0) ||
			(expirationTime > DLFolderImpl.LOCK_EXPIRATION_TIME)) {

			expirationTime = DLFolderImpl.LOCK_EXPIRATION_TIME;
		}

		return lockLocalService.lock(
			getUserId(), DLFolder.class.getName(), folderId, owner,
			inheritable, expirationTime);
	}

	protected void doUnlockFolder(long groupId, long folderId, String lockUuid)
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

}