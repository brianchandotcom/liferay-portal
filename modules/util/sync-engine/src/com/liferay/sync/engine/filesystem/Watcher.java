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

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.BidirectionalMap;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.OSDetector;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import name.pachler.nio.file.FileSystem;
import name.pachler.nio.file.FileSystems;
import name.pachler.nio.file.Paths;
import name.pachler.nio.file.StandardWatchEventKind;
import name.pachler.nio.file.WatchEvent;
import name.pachler.nio.file.WatchKey;
import name.pachler.nio.file.WatchService;
import name.pachler.nio.file.ext.ExtendedWatchEventKind;
import name.pachler.nio.file.ext.ExtendedWatchEventModifier;
import name.pachler.nio.file.impl.PathImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 */
public class Watcher implements Runnable {

	public Watcher(
			Path filePath, boolean recursive,
			WatchEventListener watchEventListener)
		throws IOException {

		_recursive = recursive;
		_watchEventListener = watchEventListener;

		_dataFilePath = filePath.resolve(".data");

		FileSystem fileSystem = FileSystems.getDefault();

		_watchService = fileSystem.newWatchService();

		registerFilePath(filePath, recursive);

		WatcherRegistry.register(_watchEventListener.getSyncAccountId(), this);
	}

	public void close() {
		WatcherRegistry.unregister(_watchEventListener.getSyncAccountId());

		try {
			_watchService.close();
		}
		catch (Exception e) {
		}
		finally {
			_watchService = null;
		}
	}

	public List<String> getDownloadedFilePathNames() {
		return _downloadedFilePathNames;
	}

	public void registerFilePath(Path filePath, boolean recursive)
		throws IOException {

		if (Files.notExists(filePath)) {
			processMissingFilePath(filePath);

			return;
		}

		doRegister(filePath, recursive);
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (_watchService == null) {
					break;
				}

				WatchKey watchKey = null;

				try {
					watchKey = _watchService.take();
				}
				catch (Exception e) {
					if (_logger.isTraceEnabled()) {
						_logger.trace(e.getMessage(), e);
					}

					continue;
				}

				Path parentFilePath = _filePaths.get(watchKey);

				if (parentFilePath == null) {
					continue;
				}

				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();

				for (int i = 0; i < watchEvents.size(); i++) {
					WatchEvent<Path> watchEvent =
						(WatchEvent<Path>)watchEvents.get(i);

					PathImpl pathImpl = (PathImpl)watchEvent.context();

					if (pathImpl == null) {
						continue;
					}

					WatchEvent.Kind<?> kind = watchEvent.kind();

					Path childFilePath = parentFilePath.resolve(
						pathImpl.toString());

					if (OSDetector.isWindows() &&
						childFilePath.startsWith(_dataFilePath)) {

						continue;
					}

					if (kind == StandardWatchEventKind.ENTRY_CREATE) {
						if (isIgnoredFilePath(childFilePath)) {
							continue;
						}

						addCreatedFilePathName(childFilePath.toString());

						if (_downloadedFilePathNames.remove(
								childFilePath.toString())) {

							continue;
						}

						fireWatchEventListener(childFilePath, watchEvent);

						if (_recursive &&
							Files.isDirectory(
								childFilePath, LinkOption.NOFOLLOW_LINKS)) {

							registerFilePath(childFilePath, true);
						}
					}
					else if (kind == StandardWatchEventKind.ENTRY_DELETE) {
						processMissingFilePath(childFilePath);

						fireWatchEventListener(childFilePath, watchEvent);
					}
					else if (kind == StandardWatchEventKind.ENTRY_MODIFY) {
						if ((removeCreatedFilePathName(
								childFilePath.toString()) &&
							 !FileUtil.isValidChecksum(childFilePath)) ||
							Files.isDirectory(childFilePath)) {

							continue;
						}

						fireWatchEventListener(childFilePath, watchEvent);
					}
				}

				for (Path failedFilePath : _failedFilePaths) {
					if (Files.notExists(failedFilePath)) {
						_failedFilePaths.remove(failedFilePath);

						continue;
					}

					if (!Files.isReadable(failedFilePath)) {
						continue;
					}

					_failedFilePaths.remove(failedFilePath);

					if (Files.isDirectory(failedFilePath)) {
						registerFilePath(failedFilePath, true);
					}
					else {
						SyncFile syncFile = SyncFileService.fetchSyncFile(
							failedFilePath.toString());

						if (syncFile == null) {
							fireWatchEventListener(
								SyncWatchEvent.EVENT_TYPE_CREATE,
								failedFilePath);
						}
						else if (FileUtil.hasFileChanged(
									syncFile, failedFilePath)) {

							fireWatchEventListener(
								SyncWatchEvent.EVENT_TYPE_MODIFY,
								failedFilePath);
						}
					}
				}

				if (!watchKey.reset()) {
					Path filePath = _filePaths.remove(watchKey);

					if (_logger.isTraceEnabled()) {
						_logger.trace("Unregistered file path {}", filePath);
					}

					processMissingFilePath(filePath);

					if (_filePaths.isEmpty()) {
						break;
					}
				}
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}
	}

	public void unregisterFilePath(Path filePath) {
		WatchKey watchKey = _filePaths.removeValue(filePath);

		if (watchKey == null) {
			return;
		}

		watchKey.cancel();

		if (_logger.isTraceEnabled()) {
			_logger.trace("Unregistered file path {}", filePath);
		}
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

	protected void doRegister(Path filePath, boolean recursive)
		throws IOException {

		if (isIgnoredFilePath(filePath)) {
			return;
		}

		if (recursive) {
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

						return super.postVisitDirectory(filePath, ioe);
					}

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (!filePath.equals(_dataFilePath)) {
							doRegister(filePath, false);
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
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
						else if (FileUtil.hasFileChanged(syncFile, filePath)) {
							fireWatchEventListener(
								SyncWatchEvent.EVENT_TYPE_MODIFY, filePath);
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(
							Path filePath, IOException ioe)
						throws IOException {

						if (ioe != null) {
							_failedFilePaths.add(filePath);

							return FileVisitResult.CONTINUE;
						}

						return super.visitFileFailed(filePath, ioe);
					}

				}
			);
		}
		else {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				filePath.toString());

			if (syncFile == null) {
				fireWatchEventListener(
					SyncWatchEvent.EVENT_TYPE_CREATE, filePath);
			}

			if (OSDetector.isWindows() && !_filePaths.isEmpty()) {
				return;
			}

			WatchKey watchKey = null;

			name.pachler.nio.file.Path jpathwatchFilePath = Paths.get(
				filePath.toString());

			if (OSDetector.isWindows()) {
				watchKey = jpathwatchFilePath.register(
					_watchService,
					new WatchEvent.Kind[] {
						ExtendedWatchEventKind.KEY_INVALID,
						StandardWatchEventKind.ENTRY_CREATE,
						StandardWatchEventKind.ENTRY_DELETE,
						StandardWatchEventKind.ENTRY_MODIFY
					},
					ExtendedWatchEventModifier.FILE_TREE);
			}
			else {
				watchKey = jpathwatchFilePath.register(
					_watchService, ExtendedWatchEventKind.KEY_INVALID,
					StandardWatchEventKind.ENTRY_CREATE,
					StandardWatchEventKind.ENTRY_DELETE,
					StandardWatchEventKind.ENTRY_MODIFY);
			}

			_filePaths.put(watchKey, filePath);

			if (_logger.isTraceEnabled()) {
				_logger.trace("Registered file path {}", filePath);
			}
		}
	}

	protected void fireWatchEventListener(
		Path filePath, WatchEvent<Path> watchEvent) {

		WatchEvent.Kind<?> kind = watchEvent.kind();

		fireWatchEventListener(kind.name(), filePath);
	}

	protected void fireWatchEventListener(String eventType, Path filePath) {
		_watchEventListener.watchEvent(eventType, filePath);
	}

	protected boolean isIgnoredFilePath(Path filePath) {
		if (Files.notExists(filePath)) {
			return true;
		}

		String fileName = String.valueOf(filePath.getFileName());

		if (FileUtil.isIgnoredFilePath(filePath) ||
			((Files.isDirectory(filePath) && (fileName.length() > 100)) ||
			 (!Files.isDirectory(filePath) && (fileName.length() > 255)))) {

			if (_logger.isDebugEnabled()) {
				_logger.debug("Ignored file path {}", filePath);
			}

			return true;
		}

		return false;
	}

	protected void processMissingFilePath(Path filePath) {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_watchEventListener.getSyncAccountId());

		String filePathName = filePath.toString();

		if (filePathName.equals(syncAccount.getFilePathName())) {
			syncAccount.setActive(false);
			syncAccount.setUiEvent(
				SyncAccount.UI_EVENT_SYNC_ACCOUNT_FOLDER_MISSING);

			SyncAccountService.update(syncAccount);
		}
		else {
			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				filePath.toString(), syncAccount.getSyncAccountId());

			if (syncSite != null) {
				syncSite.setActive(false);
				syncSite.setUiEvent(SyncSite.UI_EVENT_SYNC_SITE_FOLDER_MISSING);

				SyncSiteService.update(syncSite);
			}
		}
	}

	protected boolean removeCreatedFilePathName(String filePathName) {
		clearCreatedFilePathNames();

		Collection<String> filePathNames = _createdFilePathNames.values();

		return filePathNames.remove(filePathName);
	}

	private static Logger _logger = LoggerFactory.getLogger(Watcher.class);

	private ConcurrentNavigableMap<Long, String> _createdFilePathNames =
		new ConcurrentSkipListMap<Long, String>();
	private Path _dataFilePath;
	private List<String> _downloadedFilePathNames = new ArrayList<String>();
	private List<Path> _failedFilePaths = new CopyOnWriteArrayList<Path>();
	private BidirectionalMap<WatchKey, Path> _filePaths =
		new BidirectionalMap<WatchKey, Path>();
	private boolean _recursive;
	private WatchEventListener _watchEventListener;
	private WatchService _watchService;

}