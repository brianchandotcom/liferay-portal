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

package com.liferay.sync.engine;

import com.j256.ormlite.support.ConnectionSource;

import com.liferay.sync.engine.documentlibrary.event.GetSyncDLObjectUpdateEvent;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.documentlibrary.util.ServerEventUtil;
import com.liferay.sync.engine.filesystem.SyncSiteWatchEventListener;
import com.liferay.sync.engine.filesystem.SyncWatchEventProcessor;
import com.liferay.sync.engine.filesystem.WatchEventListener;
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncPropService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.service.persistence.SyncAccountPersistence;
import com.liferay.sync.engine.upgrade.util.UpgradeUtil;
import com.liferay.sync.engine.util.ConnectionRetryUtil;
import com.liferay.sync.engine.util.LoggerUtil;
import com.liferay.sync.engine.util.PropsValues;
import com.liferay.sync.engine.util.SyncClientUpdater;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class SyncEngine {

	public static synchronized void cancelSyncAccountTasks(long syncAccountId)
		throws Exception {

		if (!_running) {
			return;
		}

		Object[] syncAccountTasks = _syncAccountTasks.get(syncAccountId);

		if (syncAccountTasks == null) {
			return;
		}

		Watcher watcher = (Watcher)syncAccountTasks[0];

		watcher.close();

		ScheduledFuture<?> scheduledFuture =
			(ScheduledFuture<?>)syncAccountTasks[1];

		scheduledFuture.cancel(false);
	}

	public static ExecutorService getExecutorService() {
		return _executorService;
	}

	public static synchronized boolean isRunning() {
		return _running;
	}

	public static synchronized void scheduleSyncAccountTasks(
		final long syncAccountId) {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					doScheduleSyncAccountTasks(syncAccountId);
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}

		};

		_executorService.execute(runnable);
	}

	public static synchronized void start() {
		if (_running) {
			return;
		}

		try {
			doStart();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	public static synchronized void stop() {
		if (!_running) {
			return;
		}

		try {
			doStop();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected static void doScheduleSyncAccountTasks(long syncAccountId)
		throws Exception {

		if (!_running) {
			return;
		}

		SyncWatchEventService.deleteSyncWatchEvents(syncAccountId);

		SyncAccount syncAccount = ServerEventUtil.synchronizeSyncAccount(
			syncAccountId);

		if (!ConnectionRetryUtil.retryInProgress(syncAccountId)) {
			syncAccount.setState(SyncAccount.STATE_CONNECTED);

			SyncAccountService.update(syncAccount);

			ServerEventUtil.synchronizeSyncSites(syncAccountId);
		}

		Path filePath = Paths.get(syncAccount.getFilePathName());

		SyncWatchEventProcessor syncWatchEventProcessor =
			new SyncWatchEventProcessor(syncAccountId);

		_syncWatchEventProcessorExecutorService.scheduleAtFixedRate(
			syncWatchEventProcessor, 0, 3, TimeUnit.SECONDS);

		WatchEventListener watchEventListener = new SyncSiteWatchEventListener(
			syncAccountId);

		Watcher watcher = new Watcher(filePath, true, watchEventListener);

		_executorService.execute(watcher);

		synchronizeSyncFiles(filePath, syncAccountId, watchEventListener);

		scheduleGetSyncDLObjectUpdateEvent(
			syncAccount, syncWatchEventProcessor, watcher);
	}

	protected static void doStart() throws Exception {
		_running = true;

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STARTING);

		LoggerUtil.initLogger();

		_logger.info("Starting {}", PropsValues.SYNC_PRODUCT_NAME);

		UpgradeUtil.upgrade();

		SyncClientUpdater.scheduleAutoUpdateChecker(
			SyncPropService.getInteger("updateCheckInterval", 1440));

		for (long activeSyncAccountId :
				SyncAccountService.getActiveSyncAccountIds()) {

			scheduleSyncAccountTasks(activeSyncAccountId);
		}

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STARTED);
	}

	protected static void doStop() throws Exception {
		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STOPPING);

		_logger.info("Stopping {}", PropsValues.SYNC_PRODUCT_NAME);

		for (long syncAccountId : _syncAccountTasks.keySet()) {
			cancelSyncAccountTasks(syncAccountId);
		}

		_eventScheduledExecutorService.shutdownNow();
		_executorService.shutdownNow();
		_syncWatchEventProcessorExecutorService.shutdownNow();

		SyncClientUpdater.cancelAutoUpdateChecker();

		SyncAccountPersistence syncAccountPersistence =
			SyncAccountService.getSyncAccountPersistence();

		ConnectionSource connectionSource =
			syncAccountPersistence.getConnectionSource();

		connectionSource.closeQuietly();

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STOPPED);

		_running = false;
	}

	protected static void fireDeleteEvents(
			Path filePath, WatchEventListener watchEventListener)
		throws IOException {

		long startTime = System.currentTimeMillis();

		Files.walkFileTree(
			filePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

			}
		);

		List<SyncFile> deletedSyncFiles = SyncFileService.findSyncFiles(
			filePath.toString(), startTime);

		for (SyncFile deletedSyncFile : deletedSyncFiles) {
			if (deletedSyncFile.getUiEvent() ==
					SyncFile.UI_EVENT_FILE_NAME_TOO_LONG) {

				continue;
			}

			watchEventListener.watchEvent(
				SyncWatchEvent.EVENT_TYPE_DELETE,
				Paths.get(deletedSyncFile.getFilePathName()));
		}
	}

	protected static void scheduleGetSyncDLObjectUpdateEvent(
		final SyncAccount syncAccount,
		final SyncWatchEventProcessor syncWatchEventProcessor,
		Watcher watcher) {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					doRun();
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}

			protected void doRun() {
				SyncAccount updatedSyncAccount =
					SyncAccountService.fetchSyncAccount(
						syncAccount.getSyncAccountId());

				if ((updatedSyncAccount.getState() !=
						SyncAccount.STATE_CONNECTED) ||
					syncWatchEventProcessor.isInProgress()) {

					return;
				}

				Set<Long> syncSiteIds = SyncSiteService.getActiveSyncSiteIds(
					syncAccount.getSyncAccountId());

				for (long syncSiteId : syncSiteIds) {
					SyncSite syncSite = SyncSiteService.fetchSyncSite(
						syncSiteId);

					Map<String, Object> parameters =
						new HashMap<String, Object>();

					parameters.put("companyId", syncSite.getCompanyId());
					parameters.put("repositoryId", syncSite.getGroupId());
					parameters.put("syncSite", syncSite);

					GetSyncDLObjectUpdateEvent getSyncDLObjectUpdateEvent =
						new GetSyncDLObjectUpdateEvent(
							syncAccount.getSyncAccountId(), parameters);

					getSyncDLObjectUpdateEvent.run();
				}
			}

		};

		ScheduledFuture<?> scheduledFuture =
			_eventScheduledExecutorService.scheduleAtFixedRate(
				runnable, 0, syncAccount.getPollInterval(), TimeUnit.SECONDS);

		_syncAccountTasks.put(
			syncAccount.getSyncAccountId(),
			new Object[] {watcher, scheduledFuture});
	}

	protected static void synchronizeSyncFiles(
			Path filePath, long syncAccountId,
			WatchEventListener watchEventListener)
		throws IOException {

		fireDeleteEvents(filePath, watchEventListener);

		FileEventUtil.retryFileTransfers(syncAccountId);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncEngine.class);

	private static final ScheduledExecutorService
		_eventScheduledExecutorService = Executors.newScheduledThreadPool(5);
	private static final ExecutorService _executorService =
		Executors.newCachedThreadPool();
	private static boolean _running;
	private static final Map<Long, Object[]> _syncAccountTasks =
		new HashMap<Long, Object[]>();
	private static final ScheduledExecutorService
		_syncWatchEventProcessorExecutorService =
			Executors.newScheduledThreadPool(5);

}