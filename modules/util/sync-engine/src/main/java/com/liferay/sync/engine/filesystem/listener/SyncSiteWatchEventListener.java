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
import com.liferay.sync.engine.filesystem.util.WatcherManager;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.MSOfficeFileUtil;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;

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

	protected synchronized void addSyncWatchEvent(
		String eventType, Path filePath) {

		try {
			String filePathName = filePath.toString();

			if (isDuplicateEvent(eventType, filePathName, getSyncAccountId())) {
				return;
			}

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				getSyncAccountId());

			if (filePathName.equals(syncAccount.getFilePathName())) {
				return;
			}

			Path parentFilePath = filePath.getParent();

			String parentFilePathName = parentFilePath.toString();

			if (parentFilePathName.equals(syncAccount.getFilePathName())) {
				SyncSite syncSite = SyncSiteService.fetchSyncSite(
					filePathName, getSyncAccountId());

				if ((syncSite == null) || syncSite.isActive()) {
					return;
				}

				SyncFile syncFile = SyncFileService.fetchSyncFile(filePathName);

				if (FileKeyUtil.hasFileKey(
						filePath, syncFile.getSyncFileId())) {

					if (_logger.isDebugEnabled()) {
						_logger.debug(
							"Sync site {} reactivated.", syncSite.getName());
					}

					SyncSiteService.activateSyncSite(
						syncSite.getSyncSiteId(),
						Collections.<SyncFile>emptyList(), false);
				}

				return;
			}

			long repositoryId = getRepositoryId(filePath);

			if (repositoryId <= 0) {
				return;
			}

			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				repositoryId, getSyncAccountId());

			if (!syncSite.isActive()) {
				return;
			}

			if (!eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_TO)) {
				SyncWatchEventService.addSyncWatchEvent(
					eventType, filePathName, getFileType(eventType, filePath),
					null, getSyncAccountId());

				return;
			}

			String previousEventType = null;
			Path previousFilePath = null;
			long previousRepositoryId = 0;

			SyncWatchEvent lastSyncWatchEvent =
				SyncWatchEventService.getLastSyncWatchEvent(getSyncAccountId());

			if (lastSyncWatchEvent != null) {
				previousEventType = lastSyncWatchEvent.getEventType();
				previousFilePath = Paths.get(
					lastSyncWatchEvent.getFilePathName());
				previousRepositoryId = getRepositoryId(previousFilePath);
			}

			String fileType = getFileType(eventType, filePath);

			if ((lastSyncWatchEvent == null) ||
				!previousEventType.equals(
					SyncWatchEvent.EVENT_TYPE_RENAME_FROM) ||
				(previousRepositoryId != repositoryId)) {

				eventType = SyncWatchEvent.EVENT_TYPE_CREATE;

				SyncWatchEventService.addSyncWatchEvent(
					eventType, filePathName, getFileType(eventType, filePath),
					null, getSyncAccountId());

				if (fileType.equals(SyncFile.TYPE_FOLDER)) {
					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePathName);

					if (syncFile != null) {
						FileUtil.fireDeleteEvents(Paths.get(filePathName));
					}

					Watcher watcher = WatcherManager.getWatcher(
						getSyncAccountId());

					watcher.walkFileTree(Paths.get(filePathName));
				}
			}
			else if (filePath.equals(previousFilePath)) {
				lastSyncWatchEvent.setEventType(
					SyncWatchEvent.EVENT_TYPE_MODIFY);

				SyncWatchEventService.update(lastSyncWatchEvent);
			}
			else if (parentFilePath.equals(previousFilePath.getParent())) {
				if (MSOfficeFileUtil.isTempRenamedFile(
						previousFilePath, filePath)) {

					SyncWatchEventService.deleteSyncWatchEvent(
						lastSyncWatchEvent.getSyncWatchEventId());

					return;
				}

				lastSyncWatchEvent.setEventType(
					SyncWatchEvent.EVENT_TYPE_RENAME);
				lastSyncWatchEvent.setFilePathName(filePathName);
				lastSyncWatchEvent.setPreviousFilePathName(
					previousFilePath.toString());

				SyncWatchEventService.update(lastSyncWatchEvent);

				if (fileType.equals(SyncFile.TYPE_FOLDER)) {
					Watcher watcher = WatcherManager.getWatcher(
						getSyncAccountId());

					watcher.walkFileTree(Paths.get(filePathName));
				}
			}
			else {
				SyncFile syncFile = SyncFileService.fetchSyncFile(filePathName);

				if ((syncFile != null) &&
					fileType.equals(SyncFile.TYPE_FOLDER)) {

					FileUtil.fireDeleteEvents(Paths.get(filePathName));

					Watcher watcher = WatcherManager.getWatcher(
						getSyncAccountId());

					watcher.walkFileTree(Paths.get(filePathName));

					watchEvent(SyncWatchEvent.EVENT_TYPE_DELETE, filePath);
				}
				else {
					lastSyncWatchEvent.setEventType(
						SyncWatchEvent.EVENT_TYPE_MOVE);
					lastSyncWatchEvent.setFilePathName(filePathName);
					lastSyncWatchEvent.setPreviousFilePathName(
						previousFilePath.toString());

					SyncWatchEventService.update(lastSyncWatchEvent);

					if (fileType.equals(SyncFile.TYPE_FOLDER)) {
						Watcher watcher = WatcherManager.getWatcher(
							getSyncAccountId());

						watcher.walkFileTree(Paths.get(filePathName));
					}
				}
			}
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
			SyncWatchEventService.getLastSyncWatchEvent(syncAccountId);

		if ((lastSyncWatchEvent == null) ||
			!filePathName.equals(lastSyncWatchEvent.getFilePathName()) ||
			!eventType.equals(lastSyncWatchEvent.getEventType())) {

			return false;
		}

		return true;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventService.class);

}