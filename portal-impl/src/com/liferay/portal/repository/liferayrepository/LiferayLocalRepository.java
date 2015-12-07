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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;
import com.liferay.portlet.documentlibrary.util.comparator.DLFileEntryOrderByComparator;
import com.liferay.portlet.documentlibrary.util.comparator.DLFolderOrderByComparator;
import com.liferay.portlet.dynamicdatamapping.DDMFormValues;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayLocalRepository
	extends LiferayRepositoryBase implements LocalRepository {

	public LiferayLocalRepository(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileShortcutLocalService dlFileShortcutLocalService,
		DLFileShortcutService dlFileShortcutService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService,
		ResourceLocalService resourceLocalService, long groupId,
		long repositoryId, long dlFolderId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileShortcutLocalService,
			dlFileShortcutService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, groupId, repositoryId, dlFolderId);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId",
			getDefaultFileEntryTypeId(serviceContext, folderId));

		Map<String, DDMFormValues> ddmFormValuesMap = getDDMFormValuesMap(
			serviceContext, fileEntryTypeId);

		long size = 0;

		if (file != null) {
			size = file.length();
		}

		DLFileEntry dlFileEntry = dlFileEntryLocalService.addFileEntry(
			userId, getGroupId(), getRepositoryId(), toFolderId(folderId),
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, ddmFormValuesMap, file, null, size,
			serviceContext);

		addFileEntryResources(dlFileEntry, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId",
			getDefaultFileEntryTypeId(serviceContext, folderId));

		Map<String, DDMFormValues> ddmFormValuesMap = getDDMFormValuesMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.addFileEntry(
			userId, getGroupId(), getRepositoryId(), toFolderId(folderId),
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, ddmFormValuesMap, null, is, size, serviceContext);

		addFileEntryResources(dlFileEntry, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			dlFileShortcutLocalService.addFileShortcut(
				userId, getGroupId(), getRepositoryId(), toFolderId(folderId),
				toFileEntryId, serviceContext);

		return new LiferayFileShortcut(dlFileShortcut);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		boolean mountPoint = ParamUtil.getBoolean(serviceContext, "mountPoint");

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			userId, getGroupId(), getRepositoryId(), mountPoint,
			toFolderId(parentFolderId), name, description, false,
			serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryLocalService.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryLocalService.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.copyFileEntry(
			userId, groupId, getRepositoryId(), fileEntryId,
			toFolderId(destFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public void deleteAll() throws PortalException {
		dlFolderLocalService.deleteAllByRepository(getRepositoryId());
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		dlFileEntryLocalService.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		dlFileShortcutLocalService.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		DLFolder dlFolder = dlFolderLocalService.fetchFolder(folderId);

		if (dlFolder != null) {
			dlFolderLocalService.deleteFolder(folderId);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
		long folderId, int status, int start, int end,
		OrderByComparator<FileEntry> obc) {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryLocalService.getFileEntries(
				getGroupId(), toFolderId(folderId), status, start, end,
				DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
		long folderId, int start, int end, OrderByComparator<FileEntry> obc) {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryLocalService.getFileEntries(
				getGroupId(), toFolderId(folderId), start, end,
				DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
		long folderId, int status, int start, int end) {

		QueryDefinition<RepositoryEntry> queryDefinition =
			new QueryDefinition<>(status, start, end, null);

		List<Object> dlFileEntriesAndFileShortcuts =
			dlFolderLocalService.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), queryDefinition);

		return RepositoryModelUtil.toRepositoryEntries(
			dlFileEntriesAndFileShortcuts);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status) {
		QueryDefinition<RepositoryEntry> queryDefinition =
			new QueryDefinition<>(status);

		return dlFolderLocalService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), queryDefinition);
	}

	@Override
	public int getFileEntriesCount(long folderId) {
		return dlFileEntryLocalService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	@Override
	public int getFileEntriesCount(long folderId, int status) {
		return dlFileEntryLocalService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId), status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		DLFileEntry dlFileEntry =
			dlFileEntryLocalService.getFileEntryByUuidAndGroupId(
				uuid, getGroupId());

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);

		return new LiferayFileShortcut(dlFileShortcut);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(dlFileVersion);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		DLFolder dlFolder = dlFolderLocalService.getFolder(
			toFolderId(folderId));

		return new LiferayFolder(dlFolder);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.getFolder(
			getGroupId(), toFolderId(parentFolderId), name);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public List<Folder> getFolders(
		long parentFolderId, boolean includeMountfolders, int start, int end,
		OrderByComparator<Folder> obc) {

		return getFolders(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
		long parentFolderId, int status, boolean includeMountfolders, int start,
		int end, OrderByComparator<Folder> obc) {

		List<DLFolder> dlFolders = dlFolderLocalService.getFolders(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders, start, end,
			DLFolderOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
		long folderId, int status, boolean includeMountFolders, int start,
		int end, OrderByComparator<?> obc) {

		QueryDefinition<Object> queryDefinition = new QueryDefinition<>(
			status, start, end, (OrderByComparator<Object>)obc);

		List<Object> dlFoldersAndDLFileEntriesAndDLFileShortcuts =
			dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), null, includeMountFolders,
				queryDefinition);

		return RepositoryModelUtil.toRepositoryEntries(
			dlFoldersAndDLFileEntriesAndDLFileShortcuts);
	}

	@Override
	public int getFoldersCount(
		long parentFolderId, boolean includeMountfolders) {

		return getFoldersCount(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders);
	}

	@Override
	public int getFoldersCount(
		long parentFolderId, int status, boolean includeMountfolders) {

		return dlFolderLocalService.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
		long userId, long rootFolderId, int start, int end,
		OrderByComparator<FileEntry> obc) {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryLocalService.getGroupFileEntries(
				getGroupId(), 0, getRepositoryId(), toFolderId(rootFolderId),
				start, end,
				DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryLocalService.moveFileEntry(
			userId, fileEntryId, toFolderId(newFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFolder dlFolder = dlFolderLocalService.moveFolder(
			userId, toFolderId(folderId), toFolderId(parentFolderId),
			serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryLocalService.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException {

		dlAppHelperLocalService.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);

		Map<String, DDMFormValues> ddmFormValuesMap = getDDMFormValuesMap(
			serviceContext, fileEntryTypeId);

		long size = 0;

		if (file != null) {
			size = file.length();
		}

		DLFileEntry dlFileEntry = dlFileEntryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, ddmFormValuesMap, file,
			null, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);

		Map<String, DDMFormValues> ddmFormValuesMap = getDDMFormValuesMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, ddmFormValuesMap, null,
			is, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			dlFileShortcutLocalService.updateFileShortcut(
				userId, fileShortcutId, getRepositoryId(), toFolderId(folderId),
				toFileEntryId, serviceContext);

		return new LiferayFileShortcut(dlFileShortcut);
	}

	@Override
	public void updateFileShortcuts(
		long oldToFileEntryId, long newToFileEntryId) {

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		long defaultFileEntryTypeId = ParamUtil.getLong(
			serviceContext, "defaultFileEntryTypeId");
		SortedArrayList<Long> fileEntryTypeIds = getLongList(
			serviceContext, "dlFileEntryTypesSearchContainerPrimaryKeys");
		int restrictionType = ParamUtil.getInteger(
			serviceContext, "restrictionType");

		DLFolder dlFolder = dlFolderLocalService.updateFolder(
			toFolderId(folderId), toFolderId(parentFolderId), name, description,
			defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
			serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public UnicodeProperties updateRepository(
		UnicodeProperties typeSettingsProperties) {

		return typeSettingsProperties;
	}

}