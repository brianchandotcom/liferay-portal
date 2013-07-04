/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.backgroundtask.status;

import com.liferay.portal.kernel.backgroundtask.status.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.status.BackgroundTaskStatusRegistry;

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
	public BackgroundTaskStatus fetch(long backgroundTaskId) {
		Lock readLock = _readWriteLock.readLock();

		readLock.lock();

		try {
			return _backgroundTaskStatusMap.get(backgroundTaskId);
		}
		finally {
			readLock.unlock();
		}
	}

	@Override
	public BackgroundTaskStatus register(long backgroundTaskId) {
		Lock writeLock = _readWriteLock.writeLock();

		writeLock.lock();

		try {
			BackgroundTaskStatus backgroundTaskStatus =
				_backgroundTaskStatusMap.get(backgroundTaskId);

			if (backgroundTaskStatus == null) {
				backgroundTaskStatus = new BackgroundTaskStatus();

				_backgroundTaskStatusMap.put(
					backgroundTaskId, backgroundTaskStatus);
			}

			return backgroundTaskStatus;
		}
		finally {
			writeLock.unlock();
		}
	}

	@Override
	public BackgroundTaskStatus unregister(long backgroundTaskId) {
		Lock writeLock = _readWriteLock.writeLock();

		writeLock.lock();

		try {
			BackgroundTaskStatus backgroundTaskStatus =
				_backgroundTaskStatusMap.remove(backgroundTaskId);

			return backgroundTaskStatus;
		}
		finally {
			writeLock.unlock();
		}
	}

	private Map<Long, BackgroundTaskStatus> _backgroundTaskStatusMap =
		new HashMap<Long, BackgroundTaskStatus>();
	private ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}