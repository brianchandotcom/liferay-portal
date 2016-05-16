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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.documentlibrary.event.GetSyncContextEvent;
import com.liferay.sync.engine.documentlibrary.event.GetUserSitesGroupsEvent;
import com.liferay.sync.engine.documentlibrary.event.RegisterSyncDeviceEvent;
import com.liferay.sync.engine.documentlibrary.event.RetryServerConnectionEvent;
import com.liferay.sync.engine.documentlibrary.event.UnregisterSyncDeviceEvent;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.ReleaseInfo;
import com.liferay.sync.engine.util.ServerInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinn Lok
 */
public class ServerEventUtil {

	public static void registerSyncDevice(long syncAccountId) {
		if (!ServerInfo.supportsDeviceRegistration(syncAccountId)) {
			return;
		}

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("buildNumber", ReleaseInfo.getBuildNumber());
		parameters.put("featureSet", ReleaseInfo.getFeatureSet());

		String type = null;

		if (OSDetector.isApple()) {
			type = "desktop-mac";
		}
		else if (OSDetector.isLinux()) {
			type = "desktop-linux";
		}
		else if (OSDetector.isWindows()) {
			type = "desktop-windows";
		}

		parameters.put("type", type);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		parameters.put("uuid", syncAccount.getUuid());

		RegisterSyncDeviceEvent registerSyncDeviceEvent =
			new RegisterSyncDeviceEvent(syncAccountId, parameters);

		registerSyncDeviceEvent.run();
	}

	public static synchronized void retryServerConnection(
		long syncAccountId, long delay, TimeUnit timeUnit) {

		ScheduledFuture scheduledFuture =
			_retryServerConnectionScheduledFutures.get(syncAccountId);

		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}

		RetryServerConnectionEvent retryServerConnectionEvent =
			new RetryServerConnectionEvent(
				syncAccountId, Collections.<String, Object>emptyMap());

		scheduledFuture = _scheduledExecutorService.schedule(
			retryServerConnectionEvent, delay, timeUnit);

		_retryServerConnectionScheduledFutures.put(
			syncAccountId, scheduledFuture);
	}

	public static SyncAccount synchronizeSyncAccount(long syncAccountId) {
		GetSyncContextEvent getSyncContextEvent = new GetSyncContextEvent(
			syncAccountId, Collections.<String, Object>emptyMap());

		getSyncContextEvent.run();

		return SyncAccountService.fetchSyncAccount(syncAccountId);
	}

	public static void synchronizeSyncSites(long syncAccountId) {
		GetUserSitesGroupsEvent getUserSitesGroupsEvent =
			new GetUserSitesGroupsEvent(
				syncAccountId, Collections.<String, Object>emptyMap());

		getUserSitesGroupsEvent.run();
	}

	public static void unregisterSyncDevice(long syncAccountId) {
		if (!ServerInfo.supportsDeviceRegistration(syncAccountId)) {
			return;
		}

		Map<String, Object> parameters = new HashMap<>();

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		parameters.put("uuid", syncAccount.getUuid());

		UnregisterSyncDeviceEvent unregisterSyncDeviceEvent =
			new UnregisterSyncDeviceEvent(syncAccountId, parameters);

		unregisterSyncDeviceEvent.run();
	}

	private static final Map<Long, ScheduledFuture>
		_retryServerConnectionScheduledFutures = new HashMap<>();
	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(5);

}