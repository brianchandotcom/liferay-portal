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

package com.liferay.sync.engine.filesystem;

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 */
public class SyncWatchEventProcessor implements Runnable {

	public SyncWatchEventProcessor(long syncAccountId) {
		_syncAccountId = syncAccountId;
	}

	public boolean isInProgress() {
		return _inProgress;
	}

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected boolean addFile(SyncWatchEvent syncWatchEvent) throws Exception {
		final Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		if (Files.notExists(targetFilePath)) {
			return true;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		final SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(parentSyncFile.getState() == SyncFile.STATE_ERROR)) {

			return true;
		}

		if (!parentSyncFile.isSystem() && (parentSyncFile.getTypePK() == 0)) {
			return false;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			targetFilePath.toString());

		if (syncFile == null) {
			syncFile = SyncFileService.fetchSyncFileByFileKey(
				FileUtil.getFileKey(targetFilePath), _syncAccountId);
		}

		if (syncFile == null) {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						SyncFileService.addFileSyncFile(
							targetFilePath, parentSyncFile.getTypePK(),
							parentSyncFile.getRepositoryId(), _syncAccountId);
					}
					catch (Exception e) {
						if (_logger.isTraceEnabled()) {
							_logger.trace(e.getMessage(), e);
						}
					}
				}

			};

			_executorService.execute(runnable);

			return true;
		}

		Path sourceFilePath = Paths.get(syncFile.getFilePathName());

		if (targetFilePath.equals(sourceFilePath) &&
			!FileUtil.hasFileChanged(syncFile)) {
		}
		else if (Files.exists(sourceFilePath)) {
			try {
				SyncFileService.addFileSyncFile(
					targetFilePath, parentSyncFile.getTypePK(),
					parentSyncFile.getRepositoryId(), _syncAccountId);
			}
			catch (Exception e) {
				if (_logger.isTraceEnabled()) {
					_logger.trace(e.getMessage(), e);
				}
			}

			return true;
		}
		else if (parentTargetFilePath.equals(sourceFilePath.getParent())) {
			SyncFileService.updateFileSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
		else {
			SyncFileService.moveFileSyncFile(
				targetFilePath, parentSyncFile.getTypePK(), _syncAccountId,
				syncFile);

			Path sourceFileNameFilePath = sourceFilePath.getFileName();

			if (!sourceFileNameFilePath.equals(targetFilePath.getFileName())) {
				SyncFileService.updateFileSyncFile(
					targetFilePath, _syncAccountId, syncFile);
			}
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		if (syncAccount.getState() == SyncAccount.STATE_CONNECTED) {
			SyncWatchEvent relatedSyncWatchEvent =
				SyncWatchEventService.fetchSyncWatchEvent(
					SyncWatchEvent.EVENT_TYPE_DELETE,
					syncWatchEvent.getFilePathName(),
					syncWatchEvent.getTimestamp());

			if (relatedSyncWatchEvent != null) {
				_processedSyncWatchEventIds.add(
					relatedSyncWatchEvent.getSyncWatchEventId());
			}
		}

		return true;
	}

	protected boolean addFolder(SyncWatchEvent syncWatchEvent)
		throws Exception {

		Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		Path parentTargetFilePath = targetFilePath.getParent();

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(parentSyncFile.getState() == SyncFile.STATE_ERROR)) {

			return true;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFileByFileKey(
			FileUtil.getFileKey(targetFilePath), _syncAccountId);

		if (syncFile == null) {
			SyncFileService.addFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(),
				parentSyncFile.getRepositoryId(), _syncAccountId);

			return true;
		}

		if (!parentSyncFile.isSystem() && (parentSyncFile.getTypePK() == 0)) {
			return false;
		}

		Path sourceFilePath = Paths.get(syncFile.getFilePathName());

		if (targetFilePath.equals(sourceFilePath)) {
		}
		else if (Files.exists(sourceFilePath)) {
			SyncFileService.addFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(),
				parentSyncFile.getRepositoryId(), _syncAccountId);

			return true;
		}
		else if (parentTargetFilePath.equals(sourceFilePath.getParent())) {
			SyncFileService.updateFolderSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
		else {
			SyncFileService.moveFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(), _syncAccountId,
				syncFile);

			Path sourceFileNameFilePath = sourceFilePath.getFileName();

			if (!sourceFileNameFilePath.equals(targetFilePath.getFileName())) {
				SyncFileService.updateFolderSyncFile(
					targetFilePath, _syncAccountId, syncFile);
			}
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		if (syncAccount.getState() == SyncAccount.STATE_CONNECTED) {
			SyncWatchEvent relatedSyncWatchEvent =
				SyncWatchEventService.fetchSyncWatchEvent(
					SyncWatchEvent.EVENT_TYPE_DELETE,
					syncWatchEvent.getFilePathName(),
					syncWatchEvent.getTimestamp());

			if (relatedSyncWatchEvent != null) {
				_processedSyncWatchEventIds.add(
					relatedSyncWatchEvent.getSyncWatchEventId());
			}
		}

		return true;
	}

	protected void deleteFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if (syncFile == null) {
			return;
		}

		SyncFileService.deleteFileSyncFile(_syncAccountId, syncFile);
	}

	protected void deleteFolder(SyncWatchEvent syncWatchEvent)
		throws Exception {

		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if (syncFile == null) {
			return;
		}

		SyncFileService.deleteFolderSyncFile(_syncAccountId, syncFile);
	}

	protected void doRun() throws Exception {
		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.fetchLastSyncWatchEvent(_syncAccountId);

		if (lastSyncWatchEvent == null) {
			return;
		}

		long delta =
			System.currentTimeMillis() - lastSyncWatchEvent.getTimestamp();

		if (delta <= 500) {
			_inProgress = true;

			SyncEngineUtil.fireSyncEngineStateChanged(
				_syncAccountId, SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSING);

			return;
		}

		Watcher watcher = WatcherRegistry.getWatcher(_syncAccountId);

		if (watcher != null) {
			List<String> createdFilePathNames =
				watcher.getCreatedFilePathNames();

			createdFilePathNames.clear();
		}

		if (_logger.isTraceEnabled()) {
			_logger.trace("Processing sync watch events");
		}

		List<SyncWatchEvent> syncWatchEvents =
			SyncWatchEventService.findBySyncAccountId(
				_syncAccountId, "eventType", true);

		for (SyncWatchEvent syncWatchEvent : syncWatchEvents) {
			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				_syncAccountId);

			if (syncAccount.getState() != SyncAccount.STATE_CONNECTED) {
				continue;
			}

			if (_processedSyncWatchEventIds.contains(
					syncWatchEvent.getSyncWatchEventId())) {

				SyncWatchEventService.deleteSyncWatchEvent(
					syncWatchEvent.getSyncWatchEventId());

				continue;
			}

			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Event type {} file path {} file type {} timestamp {}",
					syncWatchEvent.getEventType(),
					syncWatchEvent.getFilePathName(),
					syncWatchEvent.getFileType(),
					syncWatchEvent.getTimestamp());
			}

			boolean syncWatchEventProcessed = true;

			String fileType = syncWatchEvent.getFileType();

			String eventType = syncWatchEvent.getEventType();

			if (eventType.equals(SyncWatchEvent.EVENT_TYPE_CREATE)) {
				if (fileType.equals(SyncFile.TYPE_FILE)) {
					syncWatchEventProcessed = addFile(syncWatchEvent);
				}
				else {
					syncWatchEventProcessed = addFolder(syncWatchEvent);
				}
			}
			else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_DELETE)) {
				if (fileType.equals(SyncFile.TYPE_FILE)) {
					deleteFile(syncWatchEvent);
				}
				else {
					deleteFolder(syncWatchEvent);
				}
			}
			else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_MODIFY)) {
				if (fileType.equals(SyncFile.TYPE_FILE)) {
					modifyFile(syncWatchEvent);
				}
			}

			syncAccount = SyncAccountService.fetchSyncAccount(_syncAccountId);

			if (syncWatchEventProcessed &&
				(syncAccount.getState() == SyncAccount.STATE_CONNECTED)) {

				SyncWatchEventService.deleteSyncWatchEvent(
					syncWatchEvent.getSyncWatchEventId());
			}
		}

		_inProgress = false;

		SyncEngineUtil.fireSyncEngineStateChanged(
			_syncAccountId, SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);

		_processedSyncWatchEventIds.clear();
	}

	protected void modifyFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if ((syncFile == null) || !FileUtil.hasFileChanged(syncFile)) {
			return;
		}

		SyncFileService.updateFileSyncFile(filePath, _syncAccountId, syncFile);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventProcessor.class);

	private static final ExecutorService _executorService =
		SyncEngine.getExecutorService();

	private boolean _inProgress;
	private final Set<Long> _processedSyncWatchEventIds = new HashSet<Long>();
	private final long _syncAccountId;

}