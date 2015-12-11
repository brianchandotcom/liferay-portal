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
import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.util.BatchEvent;
import com.liferay.sync.engine.documentlibrary.util.BatchEventManager;
import com.liferay.sync.engine.documentlibrary.util.FileEventManager;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncFileModelListener;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.FilenameUtils;

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

					_pendingTypePKSyncFileIds.remove(syncFile.getSyncFileId());

					for (SyncWatchEvent syncWatchEvent : syncWatchEvents) {
						try {
							if (_logger.isDebugEnabled()) {
								_logger.debug(
									"Processing queued event {} {}",
									syncWatchEvent.getFilePathName(),
									syncWatchEvent.getEventType());
							}

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

		count = SyncFileService.getSyncFilesCount(
			_syncAccountId, SyncFile.UI_EVENT_DOWNLOADING);

		if (count > 0) {
			return true;
		}

		count = SyncFileService.getSyncFilesCount(
			_syncAccountId, SyncFile.UI_EVENT_UPLOADING);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void run() {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		if (syncAccount.getState() != SyncAccount.STATE_CONNECTED) {
			return;
		}

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

		if (Files.notExists(targetFilePath) ||
			sanitizeFileName(targetFilePath) ||
			isInErrorState(targetFilePath)) {

			return;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		final SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(!parentSyncFile.isSystem() && (parentSyncFile.getTypePK() == 0))) {

			queueSyncWatchEvent(
				parentTargetFilePath.toString(), syncWatchEvent);

			return;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			targetFilePath.toString());

		if (syncFile == null) {
			syncFile = SyncFileService.fetchSyncFile(
				FileKeyUtil.getFileKey(targetFilePath));

			if (!verifySite(syncFile, parentSyncFile)) {
				syncFile = null;
			}
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
						Throwable throwable = e.getCause();

						String message = throwable.getMessage();

						if (!message.contains(
								"Unique index or primary key violation") &&
							_logger.isTraceEnabled()) {

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
				if ((Files.size(targetFilePath) == 0) ||
					FileUtil.isModified(syncFile, targetFilePath) ||
					isInErrorState(sourceFilePath)) {

					SyncFileService.addFileSyncFile(
						targetFilePath, parentSyncFile.getTypePK(),
						parentSyncFile.getRepositoryId(), _syncAccountId);
				}
				else {
					SyncFileService.copySyncFile(
						syncFile, targetFilePath, parentSyncFile.getTypePK(),
						parentSyncFile.getRepositoryId(), _syncAccountId);
				}
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

		if (sanitizeFileName(targetFilePath) ||
			isInErrorState(targetFilePath)) {

			return;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(!parentSyncFile.isSystem() && (parentSyncFile.getTypePK() == 0))) {

			queueSyncWatchEvent(
				parentTargetFilePath.toString(), syncWatchEvent);

			return;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			targetFilePath.toString());

		if (syncFile == null) {
			syncFile = SyncFileService.fetchSyncFile(
				FileKeyUtil.getFileKey(targetFilePath));

			if (!verifySite(syncFile, parentSyncFile)) {
				syncFile = null;
			}
		}

		if (syncFile == null) {
			SyncFileService.addFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(),
				parentSyncFile.getRepositoryId(), _syncAccountId);

			return;
		}

		Path sourceFilePath = Paths.get(syncFile.getFilePathName());

		if (targetFilePath.equals(sourceFilePath)) {
			FileKeyUtil.writeFileKey(
				targetFilePath, String.valueOf(syncFile.getSyncFileId()), true);
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
			!Files.notExists(Paths.get(syncFile.getFilePathName()))) {

			return;
		}
		else if ((syncFile.getState() == SyncFile.STATE_ERROR) ||
				 (syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			SyncFileService.deleteSyncFile(syncFile, false);

			return;
		}
		else if (syncFile.getState() == SyncFile.STATE_IN_PROGRESS) {
			Set<Event> events = FileEventManager.getEvents(
				syncFile.getSyncFileId());

			for (Event event : events) {
				event.cancel();
			}

			if (isPendingTypePK(syncFile)) {
				SyncFileService.deleteSyncFile(syncFile, false);

				return;
			}
		}
		else if (isPendingTypePK(syncFile)) {
			queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

			return;
		}

		String type = syncFile.getType();

		if (type.equals(SyncFile.TYPE_FILE)) {
			FileEventUtil.deleteFile(_syncAccountId, syncFile);
		}
		else {
			FileEventUtil.deleteFolder(_syncAccountId, syncFile);
		}
	}

	protected void doRun() throws Exception {
		SyncWatchEvent lastSyncWatchEvent =
			SyncWatchEventService.getLastSyncWatchEvent(_syncAccountId);

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

		List<SyncWatchEvent> syncWatchEvents = null;

		if (OSDetector.isApple()) {
			syncWatchEvents = SyncWatchEventService.findBySyncAccountId(
				_syncAccountId);
		}
		else {
			syncWatchEvents = SyncWatchEventService.findBySyncAccountId(
				_syncAccountId, "eventType", true);
		}

		for (SyncWatchEvent syncWatchEvent : syncWatchEvents) {
			processSyncWatchEvent(syncWatchEvent);
		}

		for (Map.Entry<String, List<SyncWatchEvent>> entry :
				_dependentSyncWatchEventsMaps.entrySet()) {

			SyncFile syncFile = SyncFileService.fetchSyncFile(entry.getKey());

			if ((syncFile != null) && (syncFile.getTypePK() > 0)) {
				for (SyncWatchEvent syncWatchEvent : entry.getValue()) {
					processSyncWatchEvent(syncWatchEvent);
				}
			}
		}

		SyncEngineUtil.fireSyncEngineStateChanged(
			_syncAccountId, SyncEngineUtil.SYNC_ENGINE_STATE_PROCESSED);

		_processedSyncWatchEventIds.clear();
	}

	protected boolean isInErrorState(Path filePath) {
		while (true) {
			if (filePath == null) {
				return false;
			}

			SyncFile syncFile = SyncFileService.fetchSyncFile(
				filePath.toString());

			if (syncFile != null) {
				if (syncFile.isSystem()) {
					break;
				}

				if (syncFile.getState() == SyncFile.STATE_ERROR) {
					return true;
				}
			}

			filePath = filePath.getParent();
		}

		return false;
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

	protected void moveFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		if (Files.notExists(targetFilePath) ||
			sanitizeFileName(targetFilePath) ||
			isInErrorState(targetFilePath)) {

			return;
		}

		Path parentTargetFilePath = targetFilePath.getParent();

		SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
			parentTargetFilePath.toString());

		if ((parentSyncFile == null) ||
			(!parentSyncFile.isSystem() && (parentSyncFile.getTypePK() == 0))) {

			queueSyncWatchEvent(
				parentTargetFilePath.toString(), syncWatchEvent);

			return;
		}

		Path sourceFilePath = Paths.get(
			syncWatchEvent.getPreviousFilePathName());

		SyncFile sourceSyncFile = SyncFileService.fetchSyncFile(
			sourceFilePath.toString());

		SyncFile targetSyncFile = SyncFileService.fetchSyncFile(
			targetFilePath.toString());

		if ((sourceSyncFile == null) || (targetSyncFile != null)) {
			if (Files.isDirectory(targetFilePath)) {
				addFolder(syncWatchEvent);
			}
			else {
				addFile(syncWatchEvent);
			}

			return;
		}
		else if (isPendingTypePK(sourceSyncFile)) {
			queueSyncWatchEvent(
				sourceSyncFile.getFilePathName(), syncWatchEvent);

			return;
		}

		String fileType = sourceSyncFile.getType();

		if (fileType.equals(SyncFile.TYPE_FILE)) {
			SyncFileService.moveFileSyncFile(
				targetFilePath, parentSyncFile.getTypePK(), _syncAccountId,
				sourceSyncFile);
		}
		else {
			SyncFileService.moveFolderSyncFile(
				targetFilePath, parentSyncFile.getTypePK(), _syncAccountId,
				sourceSyncFile);
		}

		renameFile(syncWatchEvent);
	}

	protected synchronized void processSyncWatchEvent(
			SyncWatchEvent syncWatchEvent)
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

		String eventType = syncWatchEvent.getEventType();

		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_FROM)) {
			eventType = SyncWatchEvent.EVENT_TYPE_DELETE;

			syncWatchEvent.setEventType(eventType);

			SyncWatchEventService.update(syncWatchEvent);
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Processing sync watch event {}", syncWatchEvent.toString());
		}

		String fileType = syncWatchEvent.getFileType();

		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_CREATE)) {
			if (fileType.equals(SyncFile.TYPE_FILE)) {
				addFile(syncWatchEvent);
			}
			else {
				addFolder(syncWatchEvent);
			}
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_DELETE)) {
			deleteFile(syncWatchEvent);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_MODIFY)) {
			modifyFile(syncWatchEvent);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_MOVE)) {
			moveFile(syncWatchEvent);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME)) {
			renameFile(syncWatchEvent);
		}

		syncAccount = SyncAccountService.fetchSyncAccount(_syncAccountId);

		if (syncAccount.getState() == SyncAccount.STATE_CONNECTED) {
			SyncWatchEventService.deleteSyncWatchEvent(
				syncWatchEvent.getSyncWatchEventId());
		}
	}

	protected void queueSyncWatchEvent(
		String parentFilePathName, SyncWatchEvent syncWatchEvent) {

		List<SyncWatchEvent> syncWatchEvents =
			_dependentSyncWatchEventsMaps.get(parentFilePathName);

		if (syncWatchEvents == null) {
			syncWatchEvents = new ArrayList<>();

			_dependentSyncWatchEventsMaps.put(
				parentFilePathName, syncWatchEvents);
		}
		else {
			String eventType = syncWatchEvent.getEventType();
			String filePathName = syncWatchEvent.getFilePathName();

			SyncWatchEvent lastSyncWatchEvent = syncWatchEvents.get(
				syncWatchEvents.size() - 1);

			if (filePathName.equals(lastSyncWatchEvent.getFilePathName()) &&
				eventType.equals(lastSyncWatchEvent.getEventType())) {

				return;
			}
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Queueing event {} {}", syncWatchEvent.getEventType(),
				syncWatchEvent.getFilePathName());
		}

		syncWatchEvents.add(syncWatchEvent);
	}

	protected void renameFile(SyncWatchEvent syncWatchEvent) throws Exception {
		Path sourceFilePath = Paths.get(
			syncWatchEvent.getPreviousFilePathName());

		SyncFile syncFile = SyncFileService.fetchSyncFile(
			sourceFilePath.toString());

		Path targetFilePath = Paths.get(syncWatchEvent.getFilePathName());

		if (sanitizeFileName(targetFilePath)) {
			return;
		}

		if (syncFile == null) {
			if (Files.isDirectory(targetFilePath)) {
				addFolder(syncWatchEvent);
			}
			else {
				addFile(syncWatchEvent);
			}

			return;
		}
		else if (isPendingTypePK(syncFile)) {
			queueSyncWatchEvent(syncFile.getFilePathName(), syncWatchEvent);

			return;
		}

		String fileType = syncFile.getType();

		if (fileType.equals(SyncFile.TYPE_FILE)) {
			SyncFileService.renameFileSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
		else {
			SyncFileService.renameFolderSyncFile(
				targetFilePath, _syncAccountId, syncFile);
		}
	}

	protected boolean sanitizeFileName(Path filePath) {
		if (OSDetector.isWindows()) {
			return false;
		}

		String fileName = String.valueOf(filePath.getFileName());

		String sanitizedFileName = FileUtil.getSanitizedFileName(
			fileName, FilenameUtils.getExtension(fileName));

		if (!sanitizedFileName.equals(fileName)) {
			String sanitizedFilePathName = FileUtil.getFilePathName(
				String.valueOf(filePath.getParent()), sanitizedFileName);

			sanitizedFilePathName = FileUtil.getNextFilePathName(
				sanitizedFilePathName);

			FileUtil.checkFilePath(filePath);

			FileUtil.moveFile(
				filePath, java.nio.file.Paths.get(sanitizedFilePathName));

			return true;
		}

		return false;
	}

	protected boolean verifySite(SyncFile syncFile, SyncFile parentSyncFile) {
		if ((syncFile != null) &&
			((syncFile.getRepositoryId() !=
				parentSyncFile.getRepositoryId()) ||
			 (syncFile.getSyncAccountId() !=
				 parentSyncFile.getSyncAccountId()))) {

			return false;
		}

		return true;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventProcessor.class);

	private static final ExecutorService _executorService =
		SyncEngine.getEventProcessorExecutorService();

	private final Map<String, List<SyncWatchEvent>>
		_dependentSyncWatchEventsMaps = new ConcurrentHashMap<>();
	private final Set<Long> _pendingTypePKSyncFileIds = new HashSet<>();
	private final Set<Long> _processedSyncWatchEventIds = new HashSet<>();
	private final long _syncAccountId;

}