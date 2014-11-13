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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LiferayTrashCapability implements TrashCapability {

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		deleteTrashEntry(fileEntry);

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getGroupFileEntries(
				folder.getGroupId(), 0, folder.getRepositoryId(),
				folder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			DLAppHelperLocalServiceUtil.deleteFileEntry(fileEntry);

			deleteTrashEntry(fileEntry);
		}

		DLAppHelperLocalServiceUtil.deleteFolder(folder);

		deleteTrashEntry(folder);

		DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	public boolean isInTrash(Folder folder) {
		DLFolder dlFolder = (DLFolder)folder.getModel();

		return dlFolder.isInTrash();
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, Folder newFolder,
			ServiceContext serviceContext)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFileEntryFromTrash(
			userId, fileEntry, newFolder.getFolderId(), serviceContext);
	}

	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			userId, fileEntry);
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFolderFromTrash(
			userId, folder, destinationFolder.getFolderId(), serviceContext);
	}

	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFolderToTrash(userId, folder);
	}

	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			new DeleteFileEntryRepositoryEventListener());
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, Folder.class,
			new DeleteFolderRepositoryEventListener());
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, LocalRepository.class,
			new DeleteLocalRepositoryEventListener());
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		DLAppHelperLocalServiceUtil.restoreFileEntryFromTrash(
			userId, fileEntry);
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

		DLAppHelperLocalServiceUtil.restoreFolderFromTrash(userId, folder);
	}

	protected void deleteRepositoryTrashEntries(
		long repositoryId, String className) {

		List<TrashEntry> trashEntries = TrashEntryLocalServiceUtil.getEntries(
			repositoryId, className);

		for (TrashEntry trashEntry : trashEntries) {
			TrashEntryLocalServiceUtil.deleteTrashEntry(trashEntry);
		}
	}

	protected void deleteTrashEntries(long repositoryId)
		throws PortalException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			repositoryId);

		if (repository == null) {
			deleteRepositoryTrashEntries(
				repositoryId, DLFileEntry.class.getName());
			deleteRepositoryTrashEntries(
				repositoryId, DLFolder.class.getName());
		}
		else {
			deleteTrashEntries(
				repository.getGroupId(), repository.getDlFolderId());
		}
	}

	protected void deleteTrashEntries(long groupId, long dlFolderId)
		throws PortalException {

		QueryDefinition<Object> queryDefinition = new QueryDefinition<Object>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			DLFolderLocalServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, dlFolderId, null, true, queryDefinition);

		for (Object folderFileEntryOrFileShortcut :
				foldersAndFileEntriesAndFileShortcuts) {

			if (folderFileEntryOrFileShortcut instanceof DLFileEntry) {
				deleteTrashEntry((DLFileEntry)folderFileEntryOrFileShortcut);
			}
			else if (folderFileEntryOrFileShortcut instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)folderFileEntryOrFileShortcut;

				deleteTrashEntries(
					dlFolder.getGroupId(), dlFolder.getFolderId());

				deleteTrashEntry(dlFolder);
			}
		}
	}

	protected void deleteTrashEntry(DLFileEntry dlFileEntry)
		throws PortalException {

		if (!dlFileEntry.isInTrash()) {
			return;
		}

		if (dlFileEntry.isInTrashExplicitly()) {
			TrashEntryLocalServiceUtil.deleteEntry(
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId());
		}
		else {
			List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (DLFileVersion dlFileVersion : dlFileVersions) {
				TrashVersionLocalServiceUtil.deleteTrashVersion(
					DLFileVersion.class.getName(),
					dlFileVersion.getFileVersionId());
			}
		}
	}

	protected void deleteTrashEntry(DLFolder dlFolder) throws PortalException {
		if (!dlFolder.isInTrash()) {
			return;
		}

		if (dlFolder.isInTrashExplicitly()) {
			TrashEntryLocalServiceUtil.deleteEntry(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
		else {
			TrashVersionLocalServiceUtil.deleteTrashVersion(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
	}

	protected void deleteTrashEntry(FileEntry fileEntry)
		throws PortalException {

		deleteTrashEntry((DLFileEntry)fileEntry.getModel());
	}

	protected void deleteTrashEntry(Folder folder) throws PortalException {
		deleteTrashEntry((DLFolder)folder.getModel());
	}

	private class DeleteFileEntryRepositoryEventListener
		implements RepositoryEventListener
			<RepositoryEventType.Delete, FileEntry> {

		@Override
		public void execute(FileEntry fileEntry) throws PortalException {
			LiferayTrashCapability.this.deleteTrashEntry(fileEntry);
		}

	}

	private class DeleteFolderRepositoryEventListener
		implements RepositoryEventListener<RepositoryEventType.Delete, Folder> {

		@Override
		public void execute(Folder folder) throws PortalException {
			LiferayTrashCapability.this.deleteTrashEntry(folder);
		}

	}

	private class DeleteLocalRepositoryEventListener
		implements RepositoryEventListener
			<RepositoryEventType.Delete, LocalRepository> {

		@Override
		public void execute(LocalRepository localRepository)
			throws PortalException {

			LiferayTrashCapability.this.deleteTrashEntries(
				localRepository.getRepositoryId());
		}

	}

}