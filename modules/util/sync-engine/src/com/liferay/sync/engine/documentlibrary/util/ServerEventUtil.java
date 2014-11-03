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
import com.liferay.sync.engine.documentlibrary.event.RetryServerConnectionEvent;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinn Lok
 */
public class ServerEventUtil {

	public static void retryServerConnection(long syncAccountId, long delay) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("uuid", null);

		RetryServerConnectionEvent retryServerConnectionEvent =
			new RetryServerConnectionEvent(syncAccountId, parameters);

		_scheduledExecutorService.schedule(
			retryServerConnectionEvent, delay, TimeUnit.MILLISECONDS);
	}

	public static SyncAccount synchronizeSyncAccount(long syncAccountId) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("uuid", null);

		GetSyncContextEvent getSyncContextEvent = new GetSyncContextEvent(
			syncAccountId, parameters);

		getSyncContextEvent.run();

		return SyncAccountService.fetchSyncAccount(syncAccountId);
	}

	public static void synchronizeSyncSites(long syncAccountId) {
		GetUserSitesGroupsEvent getUserSitesGroupsEvent =
			new GetUserSitesGroupsEvent(
				syncAccountId, Collections.<String, Object>emptyMap());

		getUserSitesGroupsEvent.run();
	}

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(5);

}