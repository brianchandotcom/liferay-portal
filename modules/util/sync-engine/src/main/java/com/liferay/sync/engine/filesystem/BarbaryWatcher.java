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

import com.barbarysoftware.watchservice.ClosedWatchServiceException;
import com.barbarysoftware.watchservice.ExtendedWatchEventKind;
import com.barbarysoftware.watchservice.StandardWatchEventKind;
import com.barbarysoftware.watchservice.WatchEvent;
import com.barbarysoftware.watchservice.WatchKey;
import com.barbarysoftware.watchservice.WatchService;
import com.barbarysoftware.watchservice.WatchableFile;

import com.liferay.sync.engine.filesystem.listener.WatchEventListener;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BarbaryWatcher extends Watcher {

	public BarbaryWatcher(Path filePath, WatchEventListener watchEventListener)
		throws IOException {

		super(filePath, watchEventListener);
	}

	@Override
	public void close() {
		try {
			_watchService.close();
		}
		catch (Exception e) {
		}

		super.close();
	}

	@Override
	public void registerFilePath(Path filePath) throws IOException {
		if (!filePath.equals(getBaseFilePath())) {
			return;
		}

		WatchableFile watchableFile = new WatchableFile(filePath.toFile());

		watchableFile.register(
			_watchService, ExtendedWatchEventKind.ENTRY_RENAME_FROM,
			ExtendedWatchEventKind.ENTRY_RENAME_TO,
			StandardWatchEventKind.ENTRY_CREATE,
			StandardWatchEventKind.ENTRY_DELETE,
			StandardWatchEventKind.ENTRY_MODIFY);

		if (_logger.isTraceEnabled()) {
			_logger.trace("Registered file path {}", filePath);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				WatchKey watchKey = null;

				try {
					watchKey = _watchService.take();
				}
				catch (ClosedWatchServiceException cwse) {
					break;
				}
				catch (Exception e) {
					if (_logger.isTraceEnabled()) {
						_logger.trace(e.getMessage(), e);
					}

					continue;
				}

				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();

				for (int i = 0; i < watchEvents.size(); i++) {
					WatchEvent<File> watchEvent =
						(WatchEvent<File>)watchEvents.get(i);

					File file = watchEvent.context();

					if (file == null) {
						continue;
					}

					WatchEvent.Kind<?> kind = watchEvent.kind();

					processWatchEvent(kind.name(), file.toPath());
				}

				processFailedFilePaths();

				if (!watchKey.reset()) {
					if (_logger.isTraceEnabled()) {
						_logger.trace(
							"Unregistered file path {}", getBaseFilePath());
					}

					processMissingFilePath(getBaseFilePath());
				}
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void unregisterFilePath(Path filePath) {
	}

	@Override
	protected void init() {
		_watchService = WatchService.newWatchService();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BarbaryWatcher.class);

	private WatchService _watchService;

}