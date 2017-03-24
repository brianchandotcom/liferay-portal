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

package com.liferay.sync.engine.file.system;

import com.liferay.sync.engine.BaseTestCase;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.test.SyncSiteTestUtil;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class WatcherTest extends BaseTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_syncSite1 = SyncSiteTestUtil.addSyncSite(
			10158, FileUtil.getFilePathName(filePathName, "test-site1"), 10185,
			syncAccount.getSyncAccountId());

		SyncSiteService.activateSyncSite(
			_syncSite1.getSyncSiteId(), Collections.<SyncFile>emptyList(),
			true);

		_syncSite2 = SyncSiteTestUtil.addSyncSite(
			10158, FileUtil.getFilePathName(filePathName, "test-site2"), 10186,
			syncAccount.getSyncAccountId());

		SyncSiteService.activateSyncSite(
			_syncSite2.getSyncSiteId(), Collections.<SyncFile>emptyList(),
			true);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		try {
			SyncSiteService.deleteSyncSite(_syncSite1.getSyncSiteId());
			SyncSiteService.deleteSyncSite(_syncSite2.getSyncSiteId());
		}
		finally {
			super.tearDown();
		}
	}

	@Test
	public void testSyncWatchEventTypeCreate() {
		TestWatcher testWatcher = new TestWatcher(
			syncAccount.getSyncAccountId());

		String sourceFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site1", "a.txt");

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_FROM,
			Paths.get(sourceFilePathName));

		String targetFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site2", "a.txt");

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_TO, Paths.get(targetFilePathName));

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(
				syncAccount.getSyncAccountId());

		Assert.assertEquals(
			SyncWatchEvent.EVENT_TYPE_CREATE,
			lastSyncWatchEvent.getEventType());
	}

	@Test
	public void testSyncWatchEventTypeMove() {
		TestWatcher testWatcher = new TestWatcher(
			syncAccount.getSyncAccountId());

		String sourceFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site1", "a.txt");

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_FROM,
			Paths.get(sourceFilePathName));

		String targetFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site1", "test", "a.txt");

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_TO, Paths.get(targetFilePathName));

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(
				syncAccount.getSyncAccountId());

		Assert.assertEquals(
			SyncWatchEvent.EVENT_TYPE_MOVE, lastSyncWatchEvent.getEventType());
	}

	@Test
	public void testSyncWatchEventTypeRename() throws IOException {
		TestWatcher testWatcher = new TestWatcher(
			syncAccount.getSyncAccountId());

		String sourceFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site1", "a");

		Path sourceFilePath = Files.createDirectory(
			Paths.get(sourceFilePathName));

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_FROM, sourceFilePath);

		String targetFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site1", "A");

		Path targetFilePath = Paths.get(targetFilePathName);

		Files.move(sourceFilePath, targetFilePath);

		testWatcher.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_TO, targetFilePath);

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(
				syncAccount.getSyncAccountId());

		Assert.assertEquals(
			SyncWatchEvent.EVENT_TYPE_RENAME,
			lastSyncWatchEvent.getEventType());

		Files.deleteIfExists(targetFilePath);
		Files.deleteIfExists(sourceFilePath);
	}

	private static SyncSite _syncSite1;
	private static SyncSite _syncSite2;

	private class TestWatcher extends Watcher {

		public TestWatcher(long syncAccountId) {
			super(syncAccountId, null);
		}

		@Override
		public void registerFilePath(Path filePath) throws IOException {
		}

		@Override
		public void run() {
		}

		@Override
		public void unregisterFilePath(Path filePath) {
		}

		@Override
		protected void init() {
		}

	}

}