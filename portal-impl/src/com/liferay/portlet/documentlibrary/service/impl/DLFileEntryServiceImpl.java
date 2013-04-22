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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, checking in/out, deleting,
 * locking/unlocking, moving, reverting, updating, and verifying document
 * library file entries. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryServiceImpl extends DLFileEntryServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), groupId, repositoryId, folderId, sourceFileName,
			mimeType, title, description, changeLog, fileEntryTypeId, fieldsMap,
			file, is, size, serviceContext);
	}

	public DLFileVersion cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		try {
			if (!hasFileEntryLock(fileEntryId)) {
				throw new PrincipalException();
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		return dlFileEntryLocalService.cancelCheckOut(getUserId(), fileEntryId);
	}

	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			if (!hasFileEntryLock(fileEntryId)) {
				throw new PrincipalException();
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		dlFileEntryLocalService.checkInFileEntry(
			getUserId(), fileEntryId, major, changeLog, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		checkInFileEntry(fileEntryId, lockUuid, new ServiceContext());
	}

	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			if (!hasFileEntryLock(fileEntryId)) {
				throw new PrincipalException();
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		dlFileEntryLocalService.checkInFileEntry(
			getUserId(), fileEntryId, lockUuid, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	public DLFileEntry checkOutFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return checkOutFileEntry(fileEntryId, new ServiceContext());
	}

	public DLFileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return checkOutFileEntry(
			fileEntryId, null, DLFileEntryImpl.LOCK_EXPIRATION_TIME,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	public DLFileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		return checkOutFileEntry(
			fileEntryId, owner, expirationTime, new ServiceContext());
	}

	public DLFileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		return dlFileEntryLocalService.checkOutFileEntry(
			getUserId(), fileEntryId, owner, expirationTime, serviceContext);
	}

	public DLFileEntry copyFileEntry(
			long groupId, long repositoryId, long fileEntryId,
			long destFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		String sourceFileName = "A." + dlFileEntry.getExtension();
		InputStream inputStream = DLStoreUtil.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getFolderId(),
			dlFileEntry.getName());

		DLFileEntry newDlFileEntry = addFileEntry(
			groupId, repositoryId, destFolderId, sourceFileName,
			dlFileEntry.getMimeType(), dlFileEntry.getTitle(),
			dlFileEntry.getDescription(), null,
			dlFileEntry.getFileEntryTypeId(), null, null, inputStream,
			dlFileEntry.getSize(), serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLFileVersion newDlFileVersion = newDlFileEntry.getFileVersion();

		dlFileEntryLocalService.copyFileEntryMetadata(
			dlFileVersion.getCompanyId(), dlFileVersion.getFileEntryTypeId(),
			fileEntryId, newDlFileVersion.getFileVersionId(),
			dlFileVersion.getFileVersionId(), serviceContext);

		return newDlFileEntry;
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.DELETE);

		dlFileEntryLocalService.deleteFileEntry(getUserId(), fileEntryId);
	}

	public void deleteFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(groupId, folderId, title);

		deleteFileEntry(dlFileEntry.getFileEntryId());
	}

	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.DELETE);

		dlFileEntryLocalService.deleteFileVersion(
			getUserId(), fileEntryId, version);
	}

	public DLFileEntry fetchFileEntryByImageId(long imageId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryFinder.fetchByAnyImageId(imageId);

		if (dlFileEntry != null) {
			DLFileEntryPermission.check(
				getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);
		}

		return dlFileEntry;
	}

	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileAsStream(
			getGuestOrUserId(), fileEntryId, version);
	}

	public InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileAsStream(
			getGuestOrUserId(), fileEntryId, version, incrementCounter);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		QueryDefinition queryDefinition = new QueryDefinition(
			status, false, start, end, obc);

		return dlFileEntryFinder.filterFindByG_F(
			groupId, folderIds, queryDefinition);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getFileEntries(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED, start, end,
			obc);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterFindByG_F_F(
			groupId, folderId, fileEntryTypeId, start, end, obc);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_IN_TRASH, true, start, end, obc);

		return dlFileEntryFinder.findByG_U_F_M(
			groupId, 0, folderIds, mimeTypes, queryDefinition);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return getFileEntriesCount(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED);
	}

	public int getFileEntriesCount(long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFileEntryFinder.filterCountByG_F(
			groupId, folderIds, new QueryDefinition(status));
	}

	public int getFileEntriesCount(
			long groupId, long folderId, long fileEntryTypeId)
		throws SystemException {

		return dlFileEntryPersistence.filterCountByG_F_F(
			groupId, folderId, fileEntryTypeId);
	}

	public int getFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFileEntryFinder.countByG_U_F_M(
			groupId, 0, folderIds, mimeTypes,
			new QueryDefinition(WorkflowConstants.STATUS_ANY));
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			groupId, folderId, title);

		DLFileEntryPermission.check(
			getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);

		return dlFileEntry;
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByUUID_G(
			uuid, groupId);

		DLFileEntryPermission.check(
			getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);

		return dlFileEntry;
	}

	public Lock getFileEntryLock(long fileEntryId) {
		try {
			return lockLocalService.getLock(
				DLFileEntry.class.getName(), fileEntryId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return dlFileEntryFinder.filterCountByG_F(
				groupId, folderIds, queryDefinition);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int filesCount = dlFileEntryFinder.filterCountByG_F(
				groupId, folderIds.subList(start, end), queryDefinition);

			folderIds.subList(start, end).clear();

			filesCount += getFoldersFileEntriesCount(
				groupId, folderIds, status);

			return filesCount;
		}
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.size() == 0) {
			return Collections.emptyList();
		}
		else if (userId <= 0) {
			return dlFileEntryPersistence.filterFindByG_F(
				groupId, ArrayUtil.toLongArray(folderIds), start, end, obc);
		}
		else {
			return dlFileEntryPersistence.filterFindByG_U_F(
				groupId, userId, ArrayUtil.toLongArray(folderIds), start, end,
				obc);
		}
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.size() == 0) {
			return Collections.emptyList();
		}

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return dlFileEntryFinder.findByG_U_F_M(
			groupId, userId, folderIds, mimeTypes, queryDefinition);
	}

	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException, SystemException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.size() == 0) {
			return 0;
		}
		else if (userId <= 0) {
			return dlFileEntryPersistence.filterCountByG_F(
				groupId, ArrayUtil.toLongArray(folderIds));
		}
		else {
			return dlFileEntryPersistence.filterCountByG_U_F(
				groupId, userId, ArrayUtil.toLongArray(folderIds));
		}
	}

	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws PortalException, SystemException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.size() == 0) {
			return 0;
		}

		return dlFileEntryFinder.countByG_U_F_M(
			groupId, userId, folderIds, mimeTypes, new QueryDefinition(status));
	}

	public boolean hasFileEntryLock(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		long folderId = dlFileEntry.getFolderId();

		boolean hasLock = lockLocalService.hasLock(
			getUserId(), DLFileEntry.class.getName(), fileEntryId);

		if (!hasLock &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = dlFolderService.hasInheritableLock(folderId);
		}

		if (DLFileEntryPermission.contains(
				getPermissionChecker(), fileEntryId,
				ActionKeys.OVERRIDE_CHECKOUT)) {

			hasLock = true;
		}

		return hasLock;
	}

	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId);
	}

	public DLFileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		DLFileEntryPermission.check(
			permissionChecker, fileEntryId, ActionKeys.UPDATE);

		DLFolderPermission.check(
			permissionChecker, serviceContext.getScopeGroupId(), newFolderId,
			ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.moveFileEntry(
			getUserId(), fileEntryId, newFolderId, serviceContext);
	}

	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		return lockLocalService.refresh(lockUuid, companyId, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		dlFileEntryLocalService.revertFileEntry(
			getUserId(), fileEntryId, version, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		return dlFileEntryLocalService.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, fileEntryTypeId, fieldsMap,
			file, is, size, serviceContext);
	}

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryLocalService.verifyFileEntryLock(
			fileEntryId, lockUuid);
	}

}