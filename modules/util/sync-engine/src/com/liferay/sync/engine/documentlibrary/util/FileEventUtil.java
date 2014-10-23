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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.documentlibrary.event.AddFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.AddFolderEvent;
import com.liferay.sync.engine.documentlibrary.event.CancelCheckOutEvent;
import com.liferay.sync.engine.documentlibrary.event.CheckInFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.CheckOutFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.DownloadFileEvent;
import com.liferay.sync.engine.documentlibrary.event.GetAllFolderSyncDLObjectsEvent;
import com.liferay.sync.engine.documentlibrary.event.GetSyncDLObjectUpdateEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFileEntryToTrashEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFolderEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFolderToTrashEvent;
import com.liferay.sync.engine.documentlibrary.event.PatchFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.UpdateFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.UpdateFolderEvent;
import com.liferay.sync.engine.documentlibrary.handler.GetAllFolderSyncDLObjectsHandler;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.PropsValues;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class FileEventUtil {

	public static void addFile(
		Path filePath, long folderId, long repositoryId, long syncAccountId,
		String checksum, String name, String mimeType, SyncFile syncFile) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("changeLog", "");
		parameters.put("checksum", checksum);
		parameters.put("description", "");
		parameters.put("filePath", filePath);
		parameters.put("folderId", folderId);
		parameters.put("mimeType", mimeType);
		parameters.put("repositoryId", repositoryId);
		parameters.put("serviceContext.attributes.overwrite", true);

		SyncSite syncSite = SyncSiteService.fetchSyncSite(
			repositoryId, syncAccountId);

		if (syncSite.getType() != SyncSite.TYPE_SYSTEM) {
			parameters.put(
				"serviceContext.groupPermissions",
				"ADD_DISCUSSION,DELETE,UPDATE,VIEW");
		}

		if (syncSite.getType() == SyncSite.TYPE_OPEN) {
			parameters.put(
				"serviceContext.guestPermissions", "ADD_DISCUSSION,VIEW");
		}

		parameters.put("sourceFileName", name);
		parameters.put("syncFile", syncFile);
		parameters.put("title", name);

		AddFileEntryEvent addFileEntryEvent = new AddFileEntryEvent(
			syncAccountId, parameters);

		addFileEntryEvent.run();
	}

	public static void addFolder(
		long parentFolderId, long repositoryId, long syncAccountId, String name,
		SyncFile syncFile) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("description", "");
		parameters.put("name", name);
		parameters.put("parentFolderId", parentFolderId);
		parameters.put("repositoryId", repositoryId);
		parameters.put("serviceContext.attributes.overwrite", true);

		SyncSite syncSite = SyncSiteService.fetchSyncSite(
			repositoryId, syncAccountId);

		if (syncSite.getType() != SyncSite.TYPE_SYSTEM) {
			parameters.put(
				"serviceContext.groupPermissions",
				"ADD_DOCUMENT,ADD_SUBFOLDER,ADD_SHORTCUT,UPDATE,VIEW");
		}

		if (syncSite.getType() == SyncSite.TYPE_OPEN) {
			parameters.put("serviceContext.guestPermissions", "VIEW");
		}

		parameters.put("syncFile", syncFile);

		AddFolderEvent addFolderEvent = new AddFolderEvent(
			syncAccountId, parameters);

		addFolderEvent.run();
	}

	public static void cancelCheckOut(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("syncFile", syncFile);

		CancelCheckOutEvent cancelCheckOutEvent = new CancelCheckOutEvent(
			syncAccountId, parameters);

		cancelCheckOutEvent.run();
	}

	public static void checkInFile(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("changeLog", syncFile.getChangeLog());
		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("majorVersion", false);
		parameters.put("syncFile", syncFile);

		CheckInFileEntryEvent checkInFileEntryEvent = new CheckInFileEntryEvent(
			syncAccountId, parameters);

		checkInFileEntryEvent.run();
	}

	public static void checkOutFile(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("syncFile", syncFile);

		CheckOutFileEntryEvent checkOutFileEntryEvent =
			new CheckOutFileEntryEvent(syncAccountId, parameters);

		checkOutFileEntryEvent.run();
	}

	public static void deleteFile(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("syncFile", syncFile);

		MoveFileEntryToTrashEvent moveFileEntryToTrashEvent =
			new MoveFileEntryToTrashEvent(syncAccountId, parameters);

		moveFileEntryToTrashEvent.run();
	}

	public static void deleteFolder(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("folderId", syncFile.getTypePK());
		parameters.put("syncFile", syncFile);

		MoveFolderToTrashEvent moveFolderToTrashEvent =
			new MoveFolderToTrashEvent(syncAccountId, parameters);

		moveFolderToTrashEvent.run();
	}

	public static void downloadFile(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("patch", false);
		parameters.put("syncFile", syncFile);

		DownloadFileEvent downloadFileEvent = new DownloadFileEvent(
			syncAccountId, parameters);

		downloadFileEvent.run();
	}

	public static void downloadPatch(
		String sourceVersion, long syncAccountId, SyncFile syncFile,
		String targetVersion) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("patch", true);
		parameters.put("sourceVersion", sourceVersion);
		parameters.put("syncFile", syncFile);
		parameters.put("targetVersion", targetVersion);

		DownloadFileEvent downloadFileEvent = new DownloadFileEvent(
			syncAccountId, parameters);

		downloadFileEvent.run();
	}

	public static List<SyncFile> getAllFolders(
		long companyId, long repositoryId, long syncAccountId) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("companyId", companyId);
		parameters.put("repositoryId", repositoryId);

		GetAllFolderSyncDLObjectsEvent getAllFolderSyncDLObjectsEvent =
			new GetAllFolderSyncDLObjectsEvent(syncAccountId, parameters);

		getAllFolderSyncDLObjectsEvent.run();

		GetAllFolderSyncDLObjectsHandler getAllFolderSyncDLObjectsHandler =
			(GetAllFolderSyncDLObjectsHandler)getAllFolderSyncDLObjectsEvent.
				getHandler();

		return getAllFolderSyncDLObjectsHandler.getSyncFiles();
	}

	public static void moveFile(
		long folderId, long syncAccountId, SyncFile syncFile) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("newFolderId", folderId);
		parameters.put(
			"serviceContext.scopeGroupId", syncFile.getRepositoryId());
		parameters.put("syncFile", syncFile);

		MoveFileEntryEvent moveFileEntryEvent = new MoveFileEntryEvent(
			syncAccountId, parameters);

		moveFileEntryEvent.run();
	}

	public static void moveFolder(
		long parentFolderId, long syncAccountId, SyncFile syncFile) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("folderId", syncFile.getTypePK());
		parameters.put("parentFolderId", parentFolderId);
		parameters.put(
			"serviceContext.scopeGroupId", syncFile.getRepositoryId());
		parameters.put("syncFile", syncFile);

		MoveFolderEvent moveFolderEvent = new MoveFolderEvent(
			syncAccountId, parameters);

		moveFolderEvent.run();
	}

	public static void resyncFolder(long syncAccountId, SyncFile syncFile) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("companyId", syncFile.getCompanyId());
		parameters.put("lastAccessTime", 0);
		parameters.put("parentFolderId", syncFile.getTypePK());
		parameters.put("repositoryId", syncFile.getRepositoryId());

		GetSyncDLObjectUpdateEvent getSyncDLObjectUpdateEvent =
			new GetSyncDLObjectUpdateEvent(syncAccountId, parameters);

		getSyncDLObjectUpdateEvent.run();
	}

	public static void retryFileTransfers(long syncAccountId)
		throws IOException {

		List<SyncFile> downloadingSyncFiles = SyncFileService.findSyncFiles(
			syncAccountId, SyncFile.UI_EVENT_DOWNLOADING);

		for (SyncFile downloadingSyncFile : downloadingSyncFiles) {
			downloadFile(syncAccountId, downloadingSyncFile);
		}

		List<SyncFile> uploadingSyncFiles = SyncFileService.findSyncFiles(
			syncAccountId, SyncFile.UI_EVENT_UPLOADING);

		for (SyncFile uploadingSyncFile : uploadingSyncFiles) {
			Path filePath = Paths.get(uploadingSyncFile.getFilePathName());

			if (Files.notExists(filePath)) {
				continue;
			}

			if (uploadingSyncFile.isFolder()) {
				if (uploadingSyncFile.getTypePK() > 0) {
					updateFolder(
						filePath, uploadingSyncFile.getSyncAccountId(),
						uploadingSyncFile);
				}
				else {
					addFolder(
						uploadingSyncFile.getParentFolderId(),
						uploadingSyncFile.getRepositoryId(), syncAccountId,
						uploadingSyncFile.getName(), uploadingSyncFile);
				}

				continue;
			}

			String checksum = FileUtil.getChecksum(filePath);

			uploadingSyncFile.setChecksum(checksum);

			SyncFileService.update(uploadingSyncFile);

			if (uploadingSyncFile.getTypePK() > 0) {
				updateFile(
					filePath, syncAccountId, uploadingSyncFile, null,
					uploadingSyncFile.getName(), "", null, null, checksum);
			}
			else {
				addFile(
					filePath, uploadingSyncFile.getParentFolderId(),
					uploadingSyncFile.getRepositoryId(), syncAccountId,
					checksum, uploadingSyncFile.getName(),
					uploadingSyncFile.getMimeType(), uploadingSyncFile);
			}
		}
	}

	public static void updateFile(
			Path filePath, long syncAccountId, SyncFile syncFile,
			Path deltaFilePath, String name, String sourceChecksum,
			String sourceFileName, String sourceVersion, String targetChecksum)
		throws IOException {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("changeLog", syncFile.getChangeLog());
		parameters.put("checksum", targetChecksum);
		parameters.put("description", syncFile.getDescription());
		parameters.put("fileEntryId", syncFile.getTypePK());
		parameters.put("majorVersion", false);
		parameters.put("mimeType", syncFile.getMimeType());
		parameters.put("sourceFileName", name);
		parameters.put("syncFile", syncFile);
		parameters.put("title", name);

		if (sourceChecksum.equals(targetChecksum)) {
			parameters.put("-file", null);
		}
		else {
			if ((deltaFilePath != null) &&
				(Files.size(filePath) / Files.size(deltaFilePath)) >=
					PropsValues.SYNC_FILE_PATCHING_THRESHOLD_SIZE_RATIO) {

				parameters.put("deltaFilePath", deltaFilePath);
				parameters.put("sourceFileName", sourceFileName);
				parameters.put("sourceVersion", sourceVersion);

				PatchFileEntryEvent patchFileEntryEvent =
					new PatchFileEntryEvent(syncAccountId, parameters);

				patchFileEntryEvent.run();

				return;
			}

			parameters.put("filePath", filePath);
		}

		UpdateFileEntryEvent updateFileEntryEvent = new UpdateFileEntryEvent(
			syncAccountId, parameters);

		updateFileEntryEvent.run();
	}

	public static void updateFolder(
		Path filePath, long syncAccountId, SyncFile syncFile) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("description", syncFile.getDescription());
		parameters.put("folderId", syncFile.getTypePK());
		parameters.put("name", filePath.getFileName());
		parameters.put("syncFile", syncFile);

		UpdateFolderEvent updateFolderEvent = new UpdateFolderEvent(
			syncAccountId, parameters);

		updateFolderEvent.run();
	}

}