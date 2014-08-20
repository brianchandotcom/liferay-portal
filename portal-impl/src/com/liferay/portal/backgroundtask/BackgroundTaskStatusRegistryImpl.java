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

package com.liferay.portal.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusRegistryImpl
	implements BackgroundTaskStatusRegistry {

	@Override
	public BackgroundTaskStatus getBackgroundTaskStatus(long backgroundTaskId) {
		Lock lock = _readWriteLock.readLock();

		lock.lock();

		try {
			return _backgroundTaskStatuses.get(backgroundTaskId);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId) {

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			BackgroundTaskStatus backgroundTaskStatus =
				_backgroundTaskStatuses.get(backgroundTaskId);

			if (backgroundTaskStatus == null) {
				backgroundTaskStatus = new BackgroundTaskStatus();

				_backgroundTaskStatuses.put(
					backgroundTaskId, backgroundTaskStatus);
			}

			return backgroundTaskStatus;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			BackgroundTaskStatus backgroundTaskStatus =
				_backgroundTaskStatuses.remove(backgroundTaskId);

			return backgroundTaskStatus;
		}
		finally {
			lock.unlock();
		}
	}

	private final Map<Long, BackgroundTaskStatus> _backgroundTaskStatuses =
		new HashMap<Long, BackgroundTaskStatus>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}