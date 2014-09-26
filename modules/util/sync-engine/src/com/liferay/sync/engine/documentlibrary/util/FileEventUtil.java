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
import com.liferay.sync.engine.documentlibrary.event.MoveFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFileEntryToTrashEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFolderEvent;
import com.liferay.sync.engine.documentlibrary.event.MoveFolderToTrashEvent;
import com.liferay.sync.engine.documentlibrary.event.PatchFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.UpdateFileEntryEvent;
import com.liferay.sync.engine.documentlibrary.event.UpdateFolderEvent;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.PropsValues;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
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