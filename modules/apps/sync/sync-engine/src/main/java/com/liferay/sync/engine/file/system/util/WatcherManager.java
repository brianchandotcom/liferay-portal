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

package com.liferay.sync.engine.file.system.util;

import com.liferay.sync.engine.file.system.BarbaryWatcher;
import com.liferay.sync.engine.file.system.DummyWatcher;
import com.liferay.sync.engine.file.system.JPathWatcher;
import com.liferay.sync.engine.file.system.Watcher;
import com.liferay.sync.engine.file.system.listener.SyncSiteWatchEventListener;
import com.liferay.sync.engine.file.system.listener.WatchEventListener;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.util.OSDetector;

import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class WatcherManager {

	public static synchronized Watcher getWatcher(long syncAccountId) {
		Watcher watcher = _watchers.get(syncAccountId);

		if (watcher != null) {
			return watcher;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		if (syncAccount == null) {
			return _dummyWatcher;
		}

		WatchEventListener watchEventListener = new SyncSiteWatchEventListener(
			syncAccountId);

		if (OSDetector.isApple()) {
			watcher = new BarbaryWatcher(
				Paths.get(syncAccount.getFilePathName()), watchEventListener);
		}
		else {
			watcher = new JPathWatcher(
				Paths.get(syncAccount.getFilePathName()), watchEventListener);
		}

		_watchers.put(syncAccountId, watcher);

		return watcher;
	}

	public static synchronized void removeWatcher(long syncAccountId) {
		_watchers.remove(syncAccountId);
	}

	private static final Watcher _dummyWatcher = new DummyWatcher();
	private static final Map<Long, Watcher> _watchers = new HashMap<>();

}