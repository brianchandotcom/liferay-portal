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

package com.liferay.sync.engine.filesystem.listener;

import com.liferay.sync.engine.BaseTestCase;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.test.SyncSiteTestUtil;

import java.nio.file.Paths;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class SyncSiteWatchEventListenerTest extends BaseTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_syncSite = SyncSiteTestUtil.addSyncSite(
			10158, FileUtil.getFilePathName(filePathName, "test-site"), 10185,
			syncAccount.getSyncAccountId());
		_syncSite2 = SyncSiteTestUtil.addSyncSite(
			10158, FileUtil.getFilePathName(filePathName, "test-site2"), 10186,
			syncAccount.getSyncAccountId());

		Set<Long> activeSyncSiteIds = SyncSiteService.getActiveSyncSiteIds(
			syncAccount.getSyncAccountId());

		activeSyncSiteIds.add(_syncSite.getSyncSiteId());
		activeSyncSiteIds.add(_syncSite2.getSyncSiteId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		Set<Long> activeSyncSiteIds = SyncSiteService.getActiveSyncSiteIds(
			syncAccount.getSyncAccountId());

		activeSyncSiteIds.remove(_syncSite2.getSyncSiteId());
		activeSyncSiteIds.remove(_syncSite.getSyncSiteId());

		SyncSiteService.deleteSyncSite(_syncSite2.getSyncSiteId());
		SyncSiteService.deleteSyncSite(_syncSite.getSyncSiteId());

		super.tearDown();
	}

	@Test
	public void testWatchEvent() {
		SyncSiteWatchEventListener syncSiteWatchEventListener =
			new SyncSiteWatchEventListener(syncAccount.getSyncAccountId());

		String sourceFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site", "a.txt");

		syncSiteWatchEventListener.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_FROM,
			Paths.get(sourceFilePathName));

		String targetFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site2", "a.txt");

		syncSiteWatchEventListener.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_TO, Paths.get(targetFilePathName));

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(
				syncAccount.getSyncAccountId());

		Assert.assertEquals(
			SyncWatchEvent.EVENT_TYPE_CREATE,
			lastSyncWatchEvent.getEventType());
	}

	@Test
	public void testWatchEvent2() {
		SyncSiteWatchEventListener syncSiteWatchEventListener =
			new SyncSiteWatchEventListener(syncAccount.getSyncAccountId());

		String sourceFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site", "a.txt");

		syncSiteWatchEventListener.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_FROM,
			Paths.get(sourceFilePathName));

		String targetFilePathName = FileUtil.getFilePathName(
			filePathName, "test-site", "test", "a.txt");

		syncSiteWatchEventListener.watchEvent(
			SyncWatchEvent.EVENT_TYPE_RENAME_TO, Paths.get(targetFilePathName));

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(
				syncAccount.getSyncAccountId());

		Assert.assertEquals(
			SyncWatchEvent.EVENT_TYPE_MOVE, lastSyncWatchEvent.getEventType());
	}

	private static SyncSite _syncSite;
	private static SyncSite _syncSite2;

}