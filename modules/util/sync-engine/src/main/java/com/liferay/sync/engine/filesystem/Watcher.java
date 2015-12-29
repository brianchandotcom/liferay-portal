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

import com.liferay.sync.engine.filesystem.listener.WatchEventListener;
import com.liferay.sync.engine.filesystem.util.WatcherRegistry;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.OSDetector;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 * @author Shinn Lok
 */
public abstract class Watcher implements Runnable {

	public Watcher(Path filePath, WatchEventListener watchEventListener)
		throws IOException {

		_baseFilePath = filePath;
		_watchEventListener = watchEventListener;

		init();

		WatcherRegistry.register(_watchEventListener.getSyncAccountId(), this);
	}

	public void close() {
		WatcherRegistry.unregister(_watchEventListener.getSyncAccountId());
	}

	public List<String> getDeletedFilePathNames() {
		return _deletedFilePathNames;
	}

	public List<String> getDownloadedFilePathNames() {
		return _downloadedFilePathNames;
	}

	public abstract void registerFilePath(Path filePath) throws IOException;

	public abstract void unregisterFilePath(Path filePath);

	public void walkFileTree(Path filePath) throws IOException {
		if (isIgnoredFilePath(filePath)) {
			return;
		}

		Files.walkFileTree(
			filePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path filePath, IOException ioe)
					throws IOException {

					if (ioe != null) {
						_failedFilePaths.add(filePath);

						return FileVisitResult.CONTINUE;
					}

					return super.postVisitDirectory(filePath, null);
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (filePath.equals(_baseFilePath.resolve(".data")) ||
						isIgnoredFilePath(filePath)) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						fireWatchEventListener(
							SyncWatchEvent.EVENT_TYPE_CREATE, filePath);
					}

					try {
						registerFilePath(filePath);
					}
					catch (IOException ioe) {
						_logger.error(ioe.getMessage(), ioe);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (Files.notExists(filePath) ||
						isIgnoredFilePath(filePath)) {

						return FileVisitResult.CONTINUE;
					}

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						fireWatchEventListener(
							SyncWatchEvent.EVENT_TYPE_CREATE, filePath);
					}
					else if (FileUtil.isModified(syncFile, filePath)) {
						fireWatchEventListener(
							SyncWatchEvent.EVENT_TYPE_MODIFY, filePath);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
						Path filePath, IOException ioe)
					throws IOException {

					_failedFilePaths.add(filePath);

					return FileVisitResult.CONTINUE;
				}

			}
		);
	}

	protected void addCreatedFilePathName(String filePathName) {
		clearCreatedFilePathNames();

		long now = System.currentTimeMillis();

		while (_createdFilePathNames.putIfAbsent(now, filePathName) != null) {
			now++;
		}
	}

	protected void clearCreatedFilePathNames() {
		Map<Long, String> map = _createdFilePathNames.headMap(
			System.currentTimeMillis() - 5000);

		map.clear();
	}

	protected void fireWatchEventListener(String eventType, Path filePath) {
		_watchEventListener.watchEvent(eventType, filePath);
	}

	protected Path getBaseFilePath() {
		return _baseFilePath;
	}

	protected List<Path> getFailedFilePaths() {
		return _failedFilePaths;
	}

	protected abstract void init();

	protected boolean isIgnoredFilePath(Path filePath) {
		if (Files.notExists(filePath)) {
			return true;
		}

		String fileName = String.valueOf(filePath.getFileName());

		if (FileUtil.isIgnoredFilePath(filePath) || (fileName.length() > 255)) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Ignored file path {}", filePath);
			}

			return true;
		}

		return false;
	}

	protected void processFailedFilePaths() throws IOException {
		List<Path> failedFilePaths = getFailedFilePaths();

		for (Path failedFilePath : failedFilePaths) {
			if (Files.notExists(failedFilePath)) {
				failedFilePaths.remove(failedFilePath);

				continue;
			}

			if (!Files.isReadable(failedFilePath)) {
				continue;
			}

			failedFilePaths.remove(failedFilePath);

			if (Files.isDirectory(failedFilePath)) {
				registerFilePath(failedFilePath);
			}

			SyncFile syncFile = SyncFileService.fetchSyncFile(
				failedFilePath.toString());

			if (syncFile == null) {
				fireWatchEventListener(
					SyncWatchEvent.EVENT_TYPE_CREATE, failedFilePath);
			}
			else if (FileUtil.isModified(syncFile, failedFilePath)) {
				fireWatchEventListener(
					SyncWatchEvent.EVENT_TYPE_MODIFY, failedFilePath);
			}
		}
	}

	protected void processMissingFilePath(Path missingFilePath) {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_watchEventListener.getSyncAccountId());

		Path syncAccountFilePath = java.nio.file.Paths.get(
			syncAccount.getFilePathName());

		if (!FileUtil.exists(syncAccountFilePath)) {
			if (_logger.isTraceEnabled()) {
				_logger.trace(
					"Missing sync account file path {}", syncAccountFilePath);
			}

			syncAccount.setActive(false);
			syncAccount.setUiEvent(
				SyncAccount.UI_EVENT_SYNC_ACCOUNT_FOLDER_MISSING);

			SyncAccountService.update(syncAccount);
		}
		else {
			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				missingFilePath.toString(), syncAccount.getSyncAccountId());

			if ((syncSite != null) && syncSite.isActive() &&
				!FileUtil.exists(missingFilePath)) {

				if (_logger.isTraceEnabled()) {
					_logger.trace(
						"Missing sync site file path {}", missingFilePath);
				}

				syncSite.setUiEvent(SyncSite.UI_EVENT_SYNC_SITE_FOLDER_MISSING);

				SyncSiteService.update(syncSite);

				SyncSiteService.deactivateSyncSite(syncSite.getSyncSiteId());
			}
		}
	}

	protected void processWatchEvent(String eventType, Path filePath)
		throws IOException {

		if (!OSDetector.isLinux() &&
			filePath.startsWith(_baseFilePath.resolve(".data"))) {

			return;
		}

		_watcherEventsLogger.trace("{}: {}", eventType, filePath);

		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_CREATE)) {
			if (isIgnoredFilePath(filePath)) {
				return;
			}

			if (!Files.isDirectory(filePath)) {
				FileUtil.checkFilePath(filePath);
			}

			addCreatedFilePathName(filePath.toString());

			if (_downloadedFilePathNames.remove(filePath.toString())) {
				return;
			}

			fireWatchEventListener(eventType, filePath);

			if (!OSDetector.isApple() && Files.isDirectory(filePath)) {
				walkFileTree(filePath);
			}
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_DELETE)) {
			if (Files.exists(filePath)) {
				return;
			}

			removeCreatedFilePathName(filePath.toString());

			if (_deletedFilePathNames.remove(filePath.toString())) {
				return;
			}

			processMissingFilePath(filePath);

			if (Files.notExists(filePath.getParent())) {
				return;
			}

			fireWatchEventListener(SyncWatchEvent.EVENT_TYPE_DELETE, filePath);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_MODIFY)) {
			if (_downloadedFilePathNames.remove(filePath.toString()) ||
				(removeCreatedFilePathName(filePath.toString()) &&
				 !FileUtil.isValidChecksum(filePath)) ||
				FileUtil.isIgnoredFileName(
					String.valueOf(filePath.getFileName())) ||
				Files.notExists(filePath) || Files.isDirectory(filePath)) {

				return;
			}

			fireWatchEventListener(SyncWatchEvent.EVENT_TYPE_MODIFY, filePath);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_FROM)) {
			removeCreatedFilePathName(filePath.toString());

			processMissingFilePath(filePath);

			fireWatchEventListener(
				SyncWatchEvent.EVENT_TYPE_RENAME_FROM, filePath);
		}
		else if (eventType.equals(SyncWatchEvent.EVENT_TYPE_RENAME_TO)) {
			if (_downloadedFilePathNames.remove(filePath.toString()) ||
				isIgnoredFilePath(filePath)) {

				return;
			}

			fireWatchEventListener(
				SyncWatchEvent.EVENT_TYPE_RENAME_TO, filePath);
		}
	}

	protected boolean removeCreatedFilePathName(String filePathName) {
		clearCreatedFilePathNames();

		Collection<String> filePathNames = _createdFilePathNames.values();

		return filePathNames.remove(filePathName);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		Watcher.class);

	private static final Logger _watcherEventsLogger = LoggerFactory.getLogger(
		"WATCHER_EVENTS_LOGGER");

	private final Path _baseFilePath;
	private final ConcurrentNavigableMap<Long, String> _createdFilePathNames =
		new ConcurrentSkipListMap<>();
	private final List<String> _deletedFilePathNames =
		new CopyOnWriteArrayList<>();
	private final List<String> _downloadedFilePathNames =
		new CopyOnWriteArrayList<>();
	private final List<Path> _failedFilePaths = new CopyOnWriteArrayList<>();
	private final WatchEventListener _watchEventListener;

}