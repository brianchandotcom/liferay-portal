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

package com.liferay.sync.engine.util.test;

import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Shinn Lok
 */
public class SyncFileTestUtil {

	public static SyncFile addFileSyncFile(
			String filePathName, long parentFolderId, long syncAccountId)
		throws Exception {

		return addFileSyncFile(
			filePathName, parentFolderId, 0, syncAccountId, 0);
	}

	public static SyncFile addFileSyncFile(
			String filePathName, long parentFolderId, long repositoryId,
			long syncAccountId, long typePK)
		throws Exception {

		Files.createFile(Paths.get(filePathName));

		SyncFile syncFile = SyncFileService.addSyncFile(
			null, null, true, null, filePathName, null, null, parentFolderId,
			repositoryId, SyncFile.STATE_SYNCED, 0, syncAccountId,
			SyncFile.TYPE_FILE);

		if (typePK == 0) {
			syncFile.setTypePK(syncFile.getSyncFileId());

			SyncFileService.update(syncFile);
		}

		return syncFile;
	}

	public static SyncFile addFolderSyncFile(
			String filePathName, long syncAccountId)
		throws Exception {

		return addFolderSyncFile(filePathName, 0, syncAccountId);
	}

	public static SyncFile addFolderSyncFile(
			String filePathName, long parentFolderId, long syncAccountId)
		throws Exception {

		return addFolderSyncFile(
			filePathName, parentFolderId, 0, syncAccountId, 0);
	}

	public static SyncFile addFolderSyncFile(
			String filePathName, long parentFolderId, long repositoryId,
			long syncAccountId, long typePK)
		throws Exception {

		Files.createDirectory(Paths.get(filePathName));

		SyncFile syncFile = SyncFileService.addSyncFile(
			null, null, false, null, filePathName, null, null, parentFolderId,
			repositoryId, SyncFile.STATE_SYNCED, 0, syncAccountId,
			SyncFile.TYPE_FOLDER);

		if (typePK == 0) {
			syncFile.setTypePK(syncFile.getSyncFileId());

			SyncFileService.update(syncFile);
		}

		return syncFile;
	}

}