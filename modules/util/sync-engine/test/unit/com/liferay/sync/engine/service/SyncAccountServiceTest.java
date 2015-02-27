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
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.test.SyncFileTestUtil;
import com.liferay.sync.engine.util.test.SyncSiteTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class SyncAccountServiceTest extends BaseTestCase {

	@Test
	public void testAddAccount() throws Exception {
		syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccount.getSyncAccountId());

		Assert.assertNotNull(syncAccount);
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
	}

}