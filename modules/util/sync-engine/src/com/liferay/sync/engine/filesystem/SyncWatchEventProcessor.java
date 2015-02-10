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
import com.liferay.sync.engine.documentlibrary.util.BatchEvent;
import com.liferay.sync.engine.documentlibrary.util.BatchEventManager;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncFileModelListener;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

		SyncFileModelListener syncFileModelListener =
			new SyncFileModelListener() {

				@Override
				public void onUpdate(
					SyncFile syncFile, Map<String, Object> originalValues) {

					if ((syncFile.getSyncAccountId() != _syncAccountId) ||
						(syncFile.getTypePK() == 0) ||
						!originalValues.containsKey("typePK")) {

						return;
					}

					List<SyncWatchEvent> syncWatchEvents =
						_dependentSyncWatchEventsMaps.remove(
							syncFile.getFilePathName());

					if (syncWatchEvents == null) {
						return;
					}

					for (SyncWatchEvent syncWatchEvent : syncWatchEvents) {
						try {
							processSyncWatchEvent(syncWatchEvent);
						}
						catch (Exception e) {
							_logger.error(e.getMessage(), e);
						}
					}
				}

			};

		SyncFileService.registerModelListener(syncFileModelListener);
	}

	public boolean isInProgress() {
		long count = SyncWatchEventService.getSyncWatchEventsCount(
			_syncAccountId);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}

		BatchEvent batchEvent = BatchEventManager.getBatchEvent(_syncAccountId);

		batchEvent.fireBatchEvent();
	}

	protected void addFile(SyncWatchEvent syncWatchEvent) throws Exception {
		final Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		if (Files.notExists(targetFilePath) || isInErrorState(targetFilePath)) {
			return;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		final SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(!parentSyncFile.isSystem() &&
			 (parentSyncFile.getTypePK() == 0))) {

			queueSyncWatchEvent(
				parentTargetFilePath.toString(), syncWatchEvent);

			return;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			FileUtil.getFileKey(targetFilePath));

		if (syncFile == null) {
			syncFile = SyncFileService.fetchSyncFile(targetFilePath.toString());
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

			return;
		}

		Path sourceFilePath = Paths.get(syncFile.getFilePathName());

		if (targetFilePath.equals(sourceFilePath)) {
			if (isPendingTypePK(syncFile)) {
				queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

				return;
			}

			if (FileUtil.isModified(syncFile)) {
				SyncFileService.updateFileSyncFile(
					targetFilePath, _syncAccountId, syncFile);
			}
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

			return;
		}
		else if (parentTargetFilePath.equals(sourceFilePath.getParent())) {
			if (isPendingTypePK(syncFile)) {
				queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

				return;
			}

			SyncFileService.updateFileSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
		else {
			if (isPendingTypePK(syncFile)) {
				queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

				return;
			}

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
	}

	protected void addFolder(SyncWatchEvent syncWatchEvent) throws Exception {
		Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		if (isInErrorState(targetFilePath)) {
			return;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(!parentSyncFile.isSystem() &&
			 (parentSyncFile.getTypePK() == 0))) {

			queueSyncWatchEvent(
				parentTargetFilePath.toString(), syncWatchEvent);

			return;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			FileUtil.getFileKey(targetFilePath));

		if (syncFile == null) {
			syncFile = SyncFileService.fetchSyncFile(targetFilePath.toString());
		}

		if (syncFile == null) {
			SyncFileService.addFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(),
				parentSyncFile.getRepositoryId(), _syncAccountId);

			return;
		}

		Path sourceFilePath = Paths.get(syncFile.getFilePathName());

		if (targetFilePath.equals(sourceFilePath)) {
			FileUtil.writeFileKey(
				targetFilePath, String.valueOf(syncFile.getSyncFileId()));
		}
		else if (Files.exists(sourceFilePath)) {
			SyncFileService.addFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(),
				parentSyncFile.getRepositoryId(), _syncAccountId);

			return;
		}
		else if (parentTargetFilePath.equals(sourceFilePath.getParent())) {
			if (isPendingTypePK(syncFile)) {
				queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

				return;
			}

			SyncFileService.updateFolderSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
		else {
			if (isPendingTypePK(syncFile)) {
				queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

				return;
			}

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
	}

	protected void deleteFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if ((syncFile == null) ||
			Files.exists(Paths.get(syncFile.getFilePathName()))) {

			return;
		}
		else if ((syncFile.getState() == SyncFile.STATE_ERROR) ||
				 (syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			SyncFileService.deleteSyncFile(syncFile);

			return;
		}
		else if (isPendingTypePK(syncFile)) {
			queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

			return;
		}

		FileEventUtil.deleteFile(_syncAccountId, syncFile);
	}

	protected void deleteFolder(SyncWatchEvent syncWatchEvent)
		throws Exception {

		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if ((syncFile == null) ||
			Files.exists(Paths.get(syncFile.getFilePathName()))) {

			return;
		}
		else if ((syncFile.getState() == SyncFile.STATE_ERROR) ||
				 (syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			SyncFileService.deleteSyncFile(syncFile);

			return;
		}
		else if (isPendingTypePK(syncFile)) {
			queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

			return;
		}

		FileEventUtil.deleteFolder(_syncAccountId, syncFile);
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
			SyncEngineUtil.fireSyncEngineStateChanged(
				_syncAccountId, SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSING);

			return;
		}

		if (_logger.isTraceEnabled()) {
			_logger.trace("Processing sync watch events");
		}

		_pendingTypePKSyncFileIds.clear();

		List<SyncWatchEvent> syncWatchEvents =
			SyncWatchEventService.findBySyncAccountId(
				_syncAccountId, "eventType", true);

		for (SyncWatchEvent syncWatchEvent : syncWatchEvents) {
			processSyncWatchEvent(syncWatchEvent);
		}

		SyncEngineUtil.fireSyncEngineStateChanged(
			_syncAccountId, SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);

		_processedSyncWatchEventIds.clear();
	}

	protected boolean isInErrorState(Path filePath) {
		while (true) {
			filePath = filePath.getParent();

			if (filePath == null) {
				return false;
			}

			SyncFile syncFile = SyncFileService.fetchSyncFile(
				filePath.toString());

			if (syncFile == null) {
				continue;
			}

			if (!syncFile.isSystem() &&
				(syncFile.getState() == SyncFile.STATE_ERROR)) {

				return true;
			}

			return false;
		}
	}

	protected boolean isPendingTypePK(SyncFile syncFile) {
		if (_pendingTypePKSyncFileIds.contains(syncFile.getSyncFileId())) {
			return true;
		}
		else if (syncFile.getTypePK() == 0) {
			_pendingTypePKSyncFileIds.add(syncFile.getSyncFileId());

			return true;
		}

		return false;
	}

	protected void modifyFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path filePath = Paths.get(syncWatchEvent.getFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if (syncFile == null) {
			return;
		}
		else if (isPendingTypePK(syncFile)) {
			queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

			return;
		}
		else if (!FileUtil.isModified(syncFile)) {
			return;
		}

		SyncFileService.updateFileSyncFile(filePath, _syncAccountId, syncFile);
	}

	protected void processSyncWatchEvent(SyncWatchEvent syncWatchEvent)
		throws Exception {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		if (syncAccount.getState() != SyncAccount.STATE_CONNECTED) {
			return;
		}

		if (_processedSyncWatchEventIds.contains(
				syncWatchEvent.getSyncWatchEventId())) {

			SyncWatchEventService.deleteSyncWatchEvent(
				syncWatchEvent.getSyncWatchEventId());

			return;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Event type {} file path {} file type {} timestamp {}",
				syncWatchEvent.getEventType(), syncWatchEvent.getFilePathName(),
				syncWatchEvent.getFileType(), syncWatchEvent.getTimestamp());
		}

		String fileType = syncWatchEvent.getFileType();

		String eventType = syncWatchEvent.getEventType();

		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_CREATE)) {
			if (fileType.equals(SyncFile.TYPE_FILE)) {
				addFile(syncWatchEvent);
			}
			else {
				addFolder(syncWatchEvent);
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

		if (syncAccount.getState() == SyncAccount.STATE_CONNECTED) {
			SyncWatchEventService.deleteSyncWatchEvent(
				syncWatchEvent.getSyncWatchEventId());
		}
	}

	protected void queueSyncWatchEvent(
		String filePathName, SyncWatchEvent syncWatchEvent) {

		List<SyncWatchEvent> syncWatchEvents =
			_dependentSyncWatchEventsMaps.get(filePathName);

		if (syncWatchEvents == null) {
			syncWatchEvents = new ArrayList<>();

			_dependentSyncWatchEventsMaps.put(filePathName, syncWatchEvents);
		}

		syncWatchEvents.add(syncWatchEvent);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventProcessor.class);

	private static final ExecutorService _executorService =
		SyncEngine.getEventProcessorExecutorService();

	private final Map<String, List<SyncWatchEvent>>
		_dependentSyncWatchEventsMaps = new HashMap<>();
	private final Set<Long> _pendingTypePKSyncFileIds = new HashSet<>();
	private final Set<Long> _processedSyncWatchEventIds = new HashSet<>();
	private final long _syncAccountId;

}