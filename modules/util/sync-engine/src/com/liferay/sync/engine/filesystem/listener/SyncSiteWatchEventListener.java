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

import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.util.WatcherRegistry;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 */
public class SyncSiteWatchEventListener extends BaseWatchEventListener {

	public SyncSiteWatchEventListener(long syncAccountId) {
		super(syncAccountId);
	}

	@Override
	public void watchEvent(String eventType, Path filePath) {
		addSyncWatchEvent(eventType, filePath);
	}

	protected void addSyncWatchEvent(String eventType, Path filePath) {
		try {
			String filePathName = filePath.toString();

			Path parentFilePath = filePath.getParent();

			String parentFilePathName = parentFilePath.toString();

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				getSyncAccountId());

			if (isDuplicateEvent(
					eventType, filePath.toString(), getSyncAccountId())) {

				return;
			}

			if (filePathName.equals(syncAccount.getFilePathName()) ||
				parentFilePathName.equals(syncAccount.getFilePathName())) {

				return;
			}

			long repositoryId = getRepositoryId(filePath);

			if (repositoryId <= 0) {
				return;
			}

			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				repositoryId, getSyncAccountId());

			Set<Long> activeSyncSiteIds = SyncSiteService.getActiveSyncSiteIds(
				getSyncAccountId());

			if (!activeSyncSiteIds.contains(syncSite.getSyncSiteId())) {
				return;
			}

			if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_FROM)) {
				_previousFilePath = filePath;

				return;
			}

			String previousFilePathName = null;

			if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_TO)) {
				if (_previousFilePath == null) {
					Watcher watcher = WatcherRegistry.getWatcher(
						getSyncAccountId());

					watcher.walkFileTree(Paths.get(filePathName));

					eventType = SyncWatchEvent.EVENT_TYPE_CREATE;
				}
				else {
					if (parentFilePath.equals(_previousFilePath.getParent())) {
						eventType = SyncWatchEvent.EVENT_TYPE_RENAME;
					}
					else {
						eventType = SyncWatchEvent.EVENT_TYPE_MOVE;
					}

					previousFilePathName = _previousFilePath.toString();
				}
			}
			else if (_previousFilePath != null) {
				eventType = SyncWatchEvent.EVENT_TYPE_DELETE;
				filePathName = _previousFilePath.toString();
			}

			SyncWatchEventService.addSyncWatchEvent(
				eventType, filePathName, getFileType(eventType, filePath),
				previousFilePathName, getSyncAccountId());

			_previousFilePath = null;
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected String getFileType(String eventType, Path filePath) {
		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_DELETE)) {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				filePath.toString());

			if (syncFile != null) {
				return syncFile.getType();
			}
		}

		if (Files.isDirectory(filePath, LinkOption.NOFOLLOW_LINKS)) {
			return SyncFile.TYPE_FOLDER;
		}

		return SyncFile.TYPE_FILE;
	}

	protected long getRepositoryId(Path filePath) {
		while (true) {
			filePath = filePath.getParent();

			if (filePath == null) {
				return 0;
			}

			SyncFile syncFile = SyncFileService.fetchSyncFile(
				filePath.toString());

			if (syncFile != null) {
				return syncFile.getRepositoryId();
			}
		}
	}

	protected boolean isDuplicateEvent(
		String eventType, String filePathName, long syncAccountId) {

		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.fetchLastSyncWatchEvent(syncAccountId);

		if ((lastSyncWatchEvent == null) ||
			!filePathName.equals(lastSyncWatchEvent.getFilePathName()) ||
			!eventType.equals(lastSyncWatchEvent.getEventType())) {

			return false;
		}

		return true;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventService.class);

	private Path _previousFilePath;

}