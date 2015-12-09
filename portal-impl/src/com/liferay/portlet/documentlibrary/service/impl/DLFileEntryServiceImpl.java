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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.dynamicdatamapping.DDMFormValues;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

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

	@Override
	public DLFileEntry addFileEntry(
			long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.addFileEntry(
			getUserId(), groupId, repositoryId, folderId, sourceFileName,
			mimeType, title, description, changeLog, fileEntryTypeId,
			ddmFormValuesMap, file, is, size, serviceContext);
	}

	@Override
	public DLFileVersion cancelCheckOut(long fileEntryId)
		throws PortalException {

		boolean locked = LockManagerUtil.isLocked(
			DLFileEntry.class.getName(), fileEntryId);

		if (locked && !hasFileEntryLock(fileEntryId) &&
			!_hasOverrideCheckoutPermission(fileEntryId)) {

			throw new PrincipalException.MustHavePermission(
				getUserId(), DLFileEntry.class.getName(), fileEntryId,
				ActionKeys.OVERRIDE_CHECKOUT);
		}

		return dlFileEntryLocalService.cancelCheckOut(getUserId(), fileEntryId);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		boolean isLocked = LockManagerUtil.isLocked(
			DLFileEntry.class.getName(), fileEntryId);

		if (isLocked && !hasFileEntryLock(fileEntryId)) {
			throw new PrincipalException.MustHavePermission(
				getUserId(), DLFileEntry.class.getName(), fileEntryId,
				ActionKeys.OVERRIDE_CHECKOUT);
		}

		dlFileEntryLocalService.checkInFileEntry(
			getUserId(), fileEntryId, major, changeLog, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException {

		checkInFileEntry(fileEntryId, lockUuid, new ServiceContext());
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		if (!hasFileEntryLock(fileEntryId)) {
			throw new PrincipalException.MustHavePermission(
				getUserId(), DLFileEntry.class.getName(), fileEntryId,
				ActionKeys.OVERRIDE_CHECKOUT);
		}

		dlFileEntryLocalService.checkInFileEntry(
			getUserId(), fileEntryId, lockUuid, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntry checkOutFileEntry(long fileEntryId)
		throws PortalException {

		return checkOutFileEntry(fileEntryId, new ServiceContext());
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		return checkOutFileEntry(
			fileEntryId, null, DLFileEntryImpl.LOCK_EXPIRATION_TIME,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException {

		return checkOutFileEntry(
			fileEntryId, owner, expirationTime, new ServiceContext());
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		return dlFileEntryLocalService.checkOutFileEntry(
			getUserId(), fileEntryId, owner, expirationTime, serviceContext);
	}

	@Override
	public DLFileEntry copyFileEntry(
			long groupId, long repositoryId, long fileEntryId,
			long destFolderId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		DLFolderPermission.check(
			getPermissionChecker(), groupId, destFolderId,
			ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.copyFileEntry(
			getUserId(), groupId, repositoryId, fileEntryId, destFolderId,
			serviceContext);
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.DELETE);

		dlFileEntryLocalService.deleteFileEntry(getUserId(), fileEntryId);
	}

	@Override
	public void deleteFileEntry(long groupId, long folderId, String title)
		throws PortalException {

		DLFileEntry dlFileEntry = getFileEntry(groupId, folderId, title);

		deleteFileEntry(dlFileEntry.getFileEntryId());
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.DELETE);

		dlFileEntryLocalService.deleteFileVersion(
			getUserId(), fileEntryId, version);
	}

	@Override
	public DLFileEntry fetchFileEntryByImageId(long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryFinder.fetchByAnyImageId(imageId);

		if (dlFileEntry != null) {
			DLFileEntryPermission.check(
				getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);
		}

		return dlFileEntry;
	}

	@Override
	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileAsStream(fileEntryId, version);
	}

	@Override
	public InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileAsStream(
			fileEntryId, version, incrementCounter);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			status, false, start, end, obc);

		return dlFileEntryFinder.filterFindByG_F(
			groupId, folderIds, queryDefinition);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		return getFileEntries(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED, start, end,
			obc);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, long fileEntryTypeId, int start,
			int end, OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		return dlFileEntryPersistence.filterFindByG_F_F(
			groupId, folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_IN_TRASH, true, start, end, obc);

		return dlFileEntryFinder.filterFindByG_U_F_M(
			groupId, 0, folderIds, mimeTypes, queryDefinition);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId) {
		return getFileEntriesCount(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId, int status) {
		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		return dlFileEntryFinder.filterCountByG_F(
			groupId, folderIds, new QueryDefinition<DLFileEntry>(status));
	}

	@Override
	public int getFileEntriesCount(
		long groupId, long folderId, long fileEntryTypeId) {

		return dlFileEntryPersistence.filterCountByG_F_F(
			groupId, folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(
		long groupId, long folderId, String[] mimeTypes) {

		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		return dlFileEntryFinder.filterCountByG_U_F_M(
			groupId, 0, folderIds, mimeTypes,
			new QueryDefinition<DLFileEntry>(WorkflowConstants.STATUS_ANY));
	}

	@Override
	public DLFileEntry getFileEntry(long fileEntryId) throws PortalException {
		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.getFileEntry(fileEntryId);
	}

	@Override
	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			groupId, folderId, title);

		DLFileEntryPermission.check(
			getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);

		return dlFileEntry;
	}

	@Override
	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByUUID_G(
			uuid, groupId);

		DLFileEntryPermission.check(
			getPermissionChecker(), dlFileEntry, ActionKeys.VIEW);

		return dlFileEntry;
	}

	@Override
	public Lock getFileEntryLock(long fileEntryId) {
		try {
			return LockManagerUtil.getLock(
				DLFileEntry.class.getName(), fileEntryId);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public int getFoldersFileEntriesCount(
		long groupId, List<Long> folderIds, int status) {

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			status);

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

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, int start, int end,
			OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
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

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long repositoryId, long rootFolderId,
			String[] mimeTypes, int status, int start, int end,
			OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		List<Long> repositoryIds = new ArrayList<>();

		if (repositoryId != 0) {
			repositoryIds.add(repositoryId);
		}

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			status, start, end, obc);

		if (rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return dlFileEntryFinder.filterFindByG_U_R_F_M(
				groupId, userId, repositoryIds, new ArrayList<Long>(),
				mimeTypes, queryDefinition);
		}

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
			return Collections.emptyList();
		}

		return dlFileEntryFinder.filterFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status, int start, int end, OrderByComparator<DLFileEntry> obc)
		throws PortalException {

		return getGroupFileEntries(
			groupId, userId, 0, rootFolderId, mimeTypes, status, start, end,
			obc);
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException {

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
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

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long repositoryId, long rootFolderId,
			String[] mimeTypes, int status)
		throws PortalException {

		List<Long> repositoryIds = new ArrayList<>();

		if (repositoryId != 0) {
			repositoryIds.add(repositoryId);
		}

		if (rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return dlFileEntryFinder.filterCountByG_U_R_F_M(
				groupId, userId, repositoryIds, new ArrayList<Long>(),
				mimeTypes, new QueryDefinition<DLFileEntry>(status));
		}

		List<Long> folderIds = dlFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
			return 0;
		}

		return dlFileEntryFinder.filterCountByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			new QueryDefinition<DLFileEntry>(status));
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws PortalException {

		return getGroupFileEntriesCount(
			groupId, userId, 0, rootFolderId, mimeTypes, status);
	}

	@Override
	public boolean hasFileEntryLock(long fileEntryId) throws PortalException {
		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		long folderId = dlFileEntry.getFolderId();

		boolean hasLock = LockManagerUtil.hasLock(
			getUserId(), DLFileEntry.class.getName(), fileEntryId);

		if (!hasLock &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = dlFolderLocalService.hasInheritableLock(folderId);
		}

		return hasLock;
	}

	@Override
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException {

		return dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId);
	}

	@Override
	public boolean isKeepFileVersionLabel(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		DLFileEntryPermission.check(
			permissionChecker, fileEntryId, ActionKeys.VIEW);

		return dlFileEntryLocalService.isKeepFileVersionLabel(
			fileEntryId, serviceContext);
	}

	@Override
	public DLFileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		DLFileEntryPermission.check(
			permissionChecker, fileEntryId, ActionKeys.UPDATE);

		DLFolderPermission.check(
			permissionChecker, serviceContext.getScopeGroupId(), newFolderId,
			ActionKeys.ADD_DOCUMENT);

		return dlFileEntryLocalService.moveFileEntry(
			getUserId(), fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return LockManagerUtil.refresh(lockUuid, companyId, expirationTime);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		dlFileEntryLocalService.revertFileEntry(
			getUserId(), fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return dlFileEntryLocalService.search(
			groupId, getUserId(), creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, long folderId, String[] mimeTypes,
			int status, int start, int end)
		throws PortalException {

		return dlFileEntryLocalService.search(
			groupId, getUserId(), creatorUserId, folderId, mimeTypes, status,
			start, end);
	}

	@Override
	public DLFileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.UPDATE);

		return dlFileEntryLocalService.updateFileEntry(
			getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, fileEntryTypeId,
			ddmFormValuesMap, file, is, size, serviceContext);
	}

	@Override
	public DLFileEntry updateStatus(
			long userId, long fileVersionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileVersionId);

		DLFileEntryPermission.check(
			getPermissionChecker(), dlFileVersion.getFileEntryId(),
			ActionKeys.UPDATE);

		return dlFileEntryLocalService.updateStatus(
			userId, fileVersionId, status, serviceContext, workflowContext);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		return dlFileEntryLocalService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		return dlFileEntryLocalService.verifyFileEntryLock(
			fileEntryId, lockUuid);
	}

	private boolean _hasOverrideCheckoutPermission(long fileEntryId)
		throws PortalException {

		return DLFileEntryPermission.contains(
			getPermissionChecker(), fileEntryId, ActionKeys.OVERRIDE_CHECKOUT);
	}

}