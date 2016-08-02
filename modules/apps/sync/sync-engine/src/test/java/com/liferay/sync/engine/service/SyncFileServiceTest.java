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

package com.liferay.sync.engine.service;

import com.liferay.sync.engine.BaseTestCase;
import com.liferay.sync.engine.document.library.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.persistence.SyncFilePersistence;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.test.SyncFileTestUtil;

import java.io.File;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Shinn Lok
 */
@PrepareForTest(FileEventUtil.class)
@RunWith(PowerMockRunner.class)
public class SyncFileServiceTest extends BaseTestCase {

	@Test
	public void testDeleteFolderSyncFile() throws Exception {
		List<SyncFile> syncFiles = SyncFileService.findSyncFiles(
			syncAccount.getSyncAccountId());

		Assert.assertEquals(1, syncFiles.size());

		SyncFile folderSyncFileA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a"),
			syncAccount.getSyncAccountId());

		SyncFile folderSyncFileAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "b"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a", "a"),
			folderSyncFileAA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileTestUtil.addFileSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "b.txt"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileTestUtil.addFileSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "c.txt"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileTestUtil.addFileSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a", "a.txt"),
			folderSyncFileAA.getTypePK(), syncAccount.getSyncAccountId());

		syncFiles = SyncFileService.findSyncFiles(
			syncAccount.getSyncAccountId());

		Assert.assertEquals(8, syncFiles.size());

		SyncFileService.deleteSyncFile(folderSyncFileA);

		syncFiles = SyncFileService.findSyncFiles(
			syncAccount.getSyncAccountId());

		Assert.assertEquals(1, syncFiles.size());
	}

	@Test
	public void testDoUpdateFolderSyncFile() throws Exception {
		SyncFile folderSyncFileA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a"),
			syncAccount.getSyncAccountId());

		SyncFile folderSyncFileB = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "b"),
			syncAccount.getSyncAccountId());

		SyncFile folderSyncFileAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFile fileSyncFileAA = SyncFileTestUtil.addFileSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a.txt"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		SyncFileService.updateSyncFile(
			Paths.get(FileUtil.getFilePathName(filePathName, "b", "a")),
			folderSyncFileB.getTypePK(), folderSyncFileA);

		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		folderSyncFileAA = syncFilePersistence.queryForId(
			folderSyncFileAA.getTypePK());

		Assert.assertEquals(
			FileUtil.getFilePathName(filePathName, "b", "a", "a"),
			folderSyncFileAA.getFilePathName());

		fileSyncFileAA = syncFilePersistence.queryForId(
			fileSyncFileAA.getTypePK());

		Assert.assertEquals(
			FileUtil.getFilePathName(filePathName, "b", "a", "a.txt"),
			fileSyncFileAA.getFilePathName());
	}

	@Test
	public void testResyncFolders() throws Exception {
		testResyncFolders(
			new int[] {
				SyncFile.STATE_UNSYNCED, SyncFile.STATE_UNSYNCED,
				SyncFile.STATE_UNSYNCED, SyncFile.STATE_UNSYNCED
			},
			1, 4);

		testResyncFolders(
			new int[] {
				SyncFile.STATE_SYNCED, SyncFile.STATE_UNSYNCED,
				SyncFile.STATE_UNSYNCED, SyncFile.STATE_UNSYNCED
			},
			2, 3);

		testResyncFolders(
			new int[] {
				SyncFile.STATE_SYNCED, SyncFile.STATE_SYNCED,
				SyncFile.STATE_UNSYNCED, SyncFile.STATE_UNSYNCED
			},
			2, 2);

		testResyncFolders(
			new int[] {
				SyncFile.STATE_SYNCED, SyncFile.STATE_SYNCED,
				SyncFile.STATE_SYNCED, SyncFile.STATE_UNSYNCED
			},
			1, 1);
	}

	@Test
	public void testUnsyncFolders() throws Exception {
		List<SyncFile> syncFiles = new ArrayList<>();

		SyncFile folderSyncFileA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a"),
			syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileA);

		SyncFile folderSyncFileAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAA);

		SyncFile folderSyncFileAB = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "b"),
			folderSyncFileA.getTypePK(), syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAB);

		SyncFile folderSyncFileAAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a", "a"),
			folderSyncFileAA.getTypePK(), syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAAA);

		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		List<SyncFile> syncedSyncFiles = syncFilePersistence.queryForEq(
			"state", SyncFile.STATE_SYNCED);

		int previousSyncedSyncFilesSize = syncedSyncFiles.size();

		SyncFileService.unsyncFolders(
			syncAccount.getSyncAccountId(), syncFiles);

		syncedSyncFiles = syncFilePersistence.queryForEq(
			"state", SyncFile.STATE_SYNCED);

		Assert.assertEquals(
			previousSyncedSyncFilesSize - 4, syncedSyncFiles.size());

		for (SyncFile syncFile : syncFiles) {
			syncFilePersistence.delete(syncFile);
		}
	}

	protected void testResyncFolders(
			int[] syncFileStates, int expectedExecutionCount,
			int expectedModifiedCount)
		throws Exception {

		List<SyncFile> syncFiles = new ArrayList<>();

		SyncFile folderSyncFileA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a"), syncFileStates[0],
			syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileA);

		SyncFile folderSyncFileAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a"),
			folderSyncFileA.getTypePK(), syncFileStates[1],
			syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAA);

		SyncFile folderSyncFileAB = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "b"),
			folderSyncFileA.getTypePK(), syncFileStates[2],
			syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAB);

		SyncFile folderSyncFileAAA = SyncFileTestUtil.addFolderSyncFile(
			FileUtil.getFilePathName(filePathName, "a", "a", "a"),
			folderSyncFileAA.getTypePK(), syncFileStates[3],
			syncAccount.getSyncAccountId());

		syncFiles.add(folderSyncFileAAA);

		SyncFilePersistence syncFilePersistence =
			SyncFileService.getSyncFilePersistence();

		PowerMockito.mockStatic(FileEventUtil.class);

		SyncFileService.resyncFolders(
			syncAccount.getSyncAccountId(), syncFiles);

		PowerMockito.verifyStatic(Mockito.times(expectedExecutionCount));

		FileEventUtil.resyncFolder(
			Mockito.anyLong(), Mockito.any(SyncFile.class));

		List<SyncFile> resyncingSyncFiles = syncFilePersistence.queryForEq(
			"uiEvent", SyncFile.UI_EVENT_RESYNCING);

		Assert.assertEquals(expectedModifiedCount, resyncingSyncFiles.size());

		for (SyncFile syncFile : syncFiles) {
			syncFilePersistence.delete(syncFile);
		}

		FileUtils.deleteDirectory(new File(folderSyncFileA.getFilePathName()));
	}

}