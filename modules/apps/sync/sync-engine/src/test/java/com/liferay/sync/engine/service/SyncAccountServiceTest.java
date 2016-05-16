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
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.test.SyncFileTestUtil;
import com.liferay.sync.engine.util.test.SyncSiteTestUtil;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Shinn Lok
 */
@PrepareForTest(FileUtil.class)
@RunWith(PowerMockRunner.class)
public class SyncAccountServiceTest extends BaseTestCase {

	@Test
	public void testAddAccount() throws Exception {
		String targetFilePathName = FileUtil.getFilePathName(
			System.getProperty("user.home"), "liferay-sync-test3");

		SyncAccount syncAccount2 = SyncAccountService.addSyncAccount(
			targetFilePathName, "test3@liferay.com", 1, "", "", false, "", "",
			"test", 5, Collections.<SyncSite, List<SyncFile>>emptyMap(), null,
			false, "http://localhost:8080");

		syncAccount2 = SyncAccountService.fetchSyncAccount(
			syncAccount2.getSyncAccountId());

		Assert.assertNotNull(syncAccount2);

		SyncFile syncFile = SyncFileService.fetchSyncFile(targetFilePathName);

		Assert.assertTrue(
			FileKeyUtil.hasFileKey(
				Paths.get(targetFilePathName), syncFile.getSyncFileId()));

		SyncAccountService.deleteSyncAccount(syncAccount2.getSyncAccountId());
	}

	@Test
	public void testSetFilePathName() throws Exception {
		SyncSite syncSite = SyncSiteTestUtil.addSyncSite(
			10158, FileUtil.getFilePathName(filePathName, "test-site"), 10185,
			syncAccount.getSyncAccountId());

		SyncFile syncFile = SyncFileTestUtil.addFileSyncFile(
			FileUtil.getFilePathName(syncSite.getFilePathName(), "test.txt"), 0,
			syncAccount.getSyncAccountId());

		String targetFilePathName = FileUtil.getFilePathName(
			System.getProperty("user.home"), "liferay-sync-test2");

		SyncAccountService.setFilePathName(
			syncAccount.getSyncAccountId(), targetFilePathName);

		Assert.assertNull(SyncFileService.fetchSyncFile(filePathName));
		Assert.assertNotNull(SyncFileService.fetchSyncFile(targetFilePathName));

		syncSite = SyncSiteService.fetchSyncSite(syncSite.getSyncSiteId());

		Assert.assertEquals(
			FileUtil.getFilePathName(targetFilePathName, "test-site"),
			syncSite.getFilePathName());

		syncFile = SyncFileService.fetchSyncFile(syncFile.getSyncFileId());

		Assert.assertEquals(
			FileUtil.getFilePathName(
				targetFilePathName, "test-site", "test.txt"),
			syncFile.getFilePathName());

		Files.deleteIfExists(Paths.get(targetFilePathName));
	}

	@Test
	public void testUpdateSyncAccountSyncFile() throws Exception {
		String targetFilePathName = FileUtil.getFilePathName(
			System.getProperty("user.home"), "liferay-sync-test2");

		PowerMockito.stub(
			PowerMockito.method(
				FileUtil.class, "moveFile", Path.class, Path.class,
				boolean.class)
		).toThrow(

			// DirectoryNotEmptyException is thrown when a file is moved across
			// file system drives. For cleaner tests, we can just simulate this
			// behavior by mocking it.

			new DirectoryNotEmptyException(targetFilePathName)
		);

		SyncAccountService.updateSyncAccountSyncFile(
			Paths.get(targetFilePathName), syncAccount.getSyncAccountId(),
			true);

		SyncFile syncFile = SyncFileService.fetchSyncFile(targetFilePathName);

		Assert.assertTrue(
			FileKeyUtil.hasFileKey(
				Paths.get(syncFile.getFilePathName()),
				syncFile.getSyncFileId()));
	}

}