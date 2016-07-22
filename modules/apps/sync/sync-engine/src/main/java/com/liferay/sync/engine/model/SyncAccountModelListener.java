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

package com.liferay.sync.engine.model;

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.document.library.util.ServerEventUtil;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.session.rate.limiter.RateLimiterManager;
import com.liferay.sync.engine.util.FileKeyUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinn Lok
 */
public class SyncAccountModelListener implements ModelListener<SyncAccount> {

	@Override
	public void onCreate(SyncAccount syncAccount) {
	}

	@Override
	public void onRemove(SyncAccount syncAccount) {
		deactivateSyncAccount(syncAccount);

		SessionManager.removeSession(syncAccount.getSyncAccountId());
	}

	@Override
	public void onUpdate(
		SyncAccount syncAccount, Map<String, Object> originalValues) {

		if (originalValues.containsKey("active")) {
			if ((boolean)originalValues.get("active")) {
				deactivateSyncAccount(syncAccount);
			}
			else {
				activateSyncAccount(syncAccount);
			}
		}

		if (originalValues.containsKey("login") ||
			originalValues.containsKey("maxConnections") ||
			originalValues.containsKey("oAuthToken") ||
			originalValues.containsKey("oAuthTokenSecret") ||
			originalValues.containsKey("password")) {

			SessionManager.removeSession(syncAccount.getSyncAccountId());

			ServerEventUtil.retryServerConnection(
				syncAccount.getSyncAccountId(), 0, TimeUnit.SECONDS);
		}

		if (originalValues.containsKey("maxDownloadRate")) {
			RateLimiterManager.updateDownloadRateLimits();
		}

		if (originalValues.containsKey("maxUploadRate")) {
			RateLimiterManager.updateUploadRateLimits();
		}

		if (originalValues.containsKey("pollInterval")) {
			deactivateSyncAccount(syncAccount);

			activateSyncAccount(syncAccount);
		}

		if (originalValues.containsKey("syncContextModifiedTime")) {
			ServerEventUtil.synchronizeSyncAccount(
				syncAccount.getSyncAccountId());
		}

		if (originalValues.containsKey("uiEvent")) {
			if (syncAccount.getUiEvent() ==
					SyncAccount.UI_EVENT_SYNC_ACCOUNT_FOLDER_MISSING) {

				retryMissingSyncAccountFolder(syncAccount);
			}
			else {
				if (_scheduledFuture != null) {
					_scheduledFuture.cancel(true);
				}
			}
		}
	}

	protected void activateSyncAccount(SyncAccount syncAccount) {
		Set<Long> activeSyncAccountIds =
			SyncAccountService.getActiveSyncAccountIds();

		activeSyncAccountIds.add(syncAccount.getSyncAccountId());

		SyncEngine.scheduleSyncAccountTasks(syncAccount.getSyncAccountId());
	}

	protected void deactivateSyncAccount(SyncAccount syncAccount) {
		Set<Long> activeSyncAccountIds =
			SyncAccountService.getActiveSyncAccountIds();

		activeSyncAccountIds.remove(syncAccount.getSyncAccountId());

		SyncEngine.cancelSyncAccountTasks(syncAccount.getSyncAccountId());
	}

	protected void retryMissingSyncAccountFolder(
		final SyncAccount syncAccount) {

		if (_scheduledFuture != null) {
			_scheduledFuture.cancel(true);
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Path filePath = Paths.get(syncAccount.getFilePathName());

				SyncFile syncFile = SyncFileService.fetchSyncFile(
					syncAccount.getFilePathName());

				if (FileKeyUtil.hasFileKey(
						filePath, syncFile.getSyncFileId())) {

					syncAccount.setActive(true);
					syncAccount.setUiEvent(SyncAccount.UI_EVENT_NONE);

					SyncAccountService.update(syncAccount);
				}
			}

		};

		_scheduledFuture = _scheduledExecutorService.scheduleWithFixedDelay(
			runnable, 0, 5, TimeUnit.SECONDS);
	}

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(5);
	private static ScheduledFuture _scheduledFuture;

}