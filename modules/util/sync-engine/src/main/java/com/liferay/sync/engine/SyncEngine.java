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

import com.liferay.sync.engine.documentlibrary.util.BatchDownloadEvent;
import com.liferay.sync.engine.documentlibrary.util.BatchEventManager;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.documentlibrary.util.ServerEventUtil;
import com.liferay.sync.engine.filesystem.BarbaryWatcher;
import com.liferay.sync.engine.filesystem.JPathWatcher;
import com.liferay.sync.engine.filesystem.SyncWatchEventProcessor;
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.listener.SyncSiteWatchEventListener;
import com.liferay.sync.engine.filesystem.listener.WatchEventListener;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.service.persistence.SyncAccountPersistence;
import com.liferay.sync.engine.upgrade.util.UpgradeUtil;
import com.liferay.sync.engine.util.ConnectionRetryUtil;
import com.liferay.sync.engine.util.FileKeyUtil;
import com.liferay.sync.engine.util.FileLockRetryUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.LoggerUtil;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.SyncEngineUtil;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.HashSet;
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

	public static synchronized void cancelSyncAccountTasks(long syncAccountId) {
		if (!_running) {
			return;
		}

		Object[] syncAccountTasks = _syncAccountTasks.get(syncAccountId);

		if (syncAccountTasks == null) {
			return;
		}

		Watcher watcher = (Watcher)syncAccountTasks[0];

		watcher.close();

		ScheduledFuture<?> localEventsScheduledFuture =
			(ScheduledFuture<?>)syncAccountTasks[1];

		localEventsScheduledFuture.cancel(true);

		ScheduledFuture<?> remoteEventsScheduledFuture =
			(ScheduledFuture<?>)syncAccountTasks[2];

		remoteEventsScheduledFuture.cancel(true);
	}

	public static ExecutorService getEventProcessorExecutorService() {
		return _eventProcessorExecutorService;
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

		SyncAccount syncAccount = ServerEventUtil.synchronizeSyncAccount(
			syncAccountId);

		syncAccount.setState(SyncAccount.STATE_CONNECTED);
		syncAccount.setUiEvent(SyncAccount.UI_EVENT_NONE);

		SyncAccountService.update(syncAccount);

		Path syncAccountFilePath = Paths.get(syncAccount.getFilePathName());

		SyncFile syncAccountFile = SyncFileService.fetchSyncFile(
			syncAccount.getFilePathName());

		if (!FileKeyUtil.hasFileKey(
				syncAccountFilePath, syncAccountFile.getSyncFileId())) {

			if (_logger.isTraceEnabled()) {
				_logger.trace(
					"Missing sync account file path {}", syncAccountFilePath);
			}

			syncAccount.setActive(false);
			syncAccount.setUiEvent(
				SyncAccount.UI_EVENT_SYNC_ACCOUNT_FOLDER_MISSING);

			SyncAccountService.update(syncAccount);

			return;
		}
		else if (!syncAccount.isActive()) {
			SyncAccountService.activateSyncAccount(syncAccountId, false);

			return;
		}

		List<SyncSite> syncSites = SyncSiteService.findSyncSites(syncAccountId);

		for (SyncSite syncSite : syncSites) {
			syncSite.setState(SyncSite.STATE_SYNCED);

			SyncSiteService.update(syncSite);

			if (!syncSite.isActive() || (syncSite.getRemoteSyncTime() == -1)) {
				continue;
			}

			Path syncSiteFilePath = Paths.get(syncSite.getFilePathName());

			if (Files.notExists(syncSiteFilePath)) {
				if (_logger.isTraceEnabled()) {
					_logger.trace(
						"Missing sync site file path {}", syncSiteFilePath);
				}

				syncSite.setUiEvent(SyncSite.UI_EVENT_SYNC_SITE_FOLDER_MISSING);

				SyncSiteService.update(syncSite);

				SyncSiteService.deactivateSyncSite(syncSite.getSyncSiteId());
			}
		}

		if (!ConnectionRetryUtil.retryInProgress(syncAccountId)) {
			ServerEventUtil.synchronizeSyncSites(syncAccountId);
		}

		SyncWatchEventService.deleteSyncWatchEvents(syncAccountId);

		WatchEventListener watchEventListener = new SyncSiteWatchEventListener(
			syncAccountId);

		Watcher watcher = null;

		if (OSDetector.isApple()) {
			watcher = new BarbaryWatcher(
				syncAccountFilePath, watchEventListener);
		}
		else {
			watcher = new JPathWatcher(syncAccountFilePath, watchEventListener);
		}

		watcher.walkFileTree(syncAccountFilePath);

		SyncWatchEventProcessor syncWatchEventProcessor =
			new SyncWatchEventProcessor(syncAccountId);

		syncWatchEventProcessor.run();

		if (!ConnectionRetryUtil.retryInProgress(syncAccountId)) {
			synchronizeSyncFiles(syncAccountId);
		}

		scheduleEvents(syncAccount, syncWatchEventProcessor, watcher);
	}

	protected static void doStart() throws Exception {
		_running = true;

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STARTING);

		LoggerUtil.init();

		_logger.info("Starting Sync engine");

		UpgradeUtil.upgrade();

		FileLockRetryUtil.init();

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			scheduleSyncAccountTasks(syncAccount.getSyncAccountId());
		}

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STARTED);
	}

	protected static void doStop() throws Exception {
		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STOPPING);

		_logger.info("Stopping Sync engine");

		for (long syncAccountId : _syncAccountTasks.keySet()) {
			cancelSyncAccountTasks(syncAccountId);
		}

		_eventProcessorExecutorService.shutdownNow();
		_executorService.shutdownNow();
		_localEventsScheduledExecutorService.shutdownNow();
		_remoteEventsScheduledExecutorService.shutdownNow();

		FileLockRetryUtil.shutdown();

		LoggerUtil.shutdown();

		SyncAccountPersistence syncAccountPersistence =
			SyncAccountService.getSyncAccountPersistence();

		ConnectionSource connectionSource =
			syncAccountPersistence.getConnectionSource();

		connectionSource.closeQuietly();

		SyncEngineUtil.fireSyncEngineStateChanged(
			SyncEngineUtil.SYNC_ENGINE_STATE_STOPPED);

		_running = false;
	}

	protected static void scheduleEvents(
		final SyncAccount syncAccount,
		final SyncWatchEventProcessor syncWatchEventProcessor,
		Watcher watcher) {

		_executorService.execute(watcher);

		ScheduledFuture<?> localEventsScheduledFuture =
			_localEventsScheduledExecutorService.scheduleWithFixedDelay(
				syncWatchEventProcessor, 0, 3, TimeUnit.SECONDS);

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

				for (long syncSiteId : new HashSet<Long>(syncSiteIds)) {
					SyncSite syncSite = SyncSiteService.fetchSyncSite(
						syncSiteId);

					if (syncSite.getState() == SyncSite.STATE_IN_PROGRESS) {
						continue;
					}

					FileEventUtil.getUpdates(
						syncSite.getGroupId(), syncAccount.getSyncAccountId(),
						syncSite, true);
				}

				BatchDownloadEvent batchDownloadEvent =
					BatchEventManager.getBatchDownloadEvent(
						syncAccount.getSyncAccountId());

				batchDownloadEvent.fireBatchEvent();
			}

		};

		ScheduledFuture<?> remoteEventsScheduledFuture =
			_remoteEventsScheduledExecutorService.scheduleWithFixedDelay(
				runnable, 0, syncAccount.getPollInterval(), TimeUnit.SECONDS);

		_syncAccountTasks.put(
			syncAccount.getSyncAccountId(),
			new Object[] {
				watcher, localEventsScheduledFuture, remoteEventsScheduledFuture
			});
	}

	protected static void synchronizeSyncFiles(long syncAccountId)
		throws IOException {

		List<SyncSite> syncSites = SyncSiteService.findSyncSites(syncAccountId);

		for (SyncSite syncSite : syncSites) {
			if (!syncSite.isActive()) {
				continue;
			}

			FileUtil.fireDeleteEvents(Paths.get(syncSite.getFilePathName()));
		}

		FileEventUtil.retryFileTransfers(syncAccountId);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SyncEngine.class);

	private static final ExecutorService _eventProcessorExecutorService =
		Executors.newFixedThreadPool(5);
	private static final ExecutorService _executorService =
		Executors.newCachedThreadPool();
	private static final ScheduledExecutorService
		_localEventsScheduledExecutorService = Executors.newScheduledThreadPool(
			5);
	private static final ScheduledExecutorService
		_remoteEventsScheduledExecutorService =
			Executors.newScheduledThreadPool(5);
	private static boolean _running;
	private static final Map<Long, Object[]> _syncAccountTasks =
		new HashMap<>();

}