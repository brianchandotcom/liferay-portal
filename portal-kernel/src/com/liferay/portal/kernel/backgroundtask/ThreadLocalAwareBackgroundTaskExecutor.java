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

package com.liferay.portal.kernel.backgroundtask;

import java.io.Serializable;

import java.util.Map;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.background.task.internal.ThreadLocalAwareBackgroundTaskExecutor}
 */
@Deprecated
public class ThreadLocalAwareBackgroundTaskExecutor
	extends DelegatingBackgroundTaskExecutor {

	public ThreadLocalAwareBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		BackgroundTaskThreadLocalManager backgroundTaskThreadLocalManager) {

		super(backgroundTaskExecutor);

		_backgroundTaskThreadLocalManager = backgroundTaskThreadLocalManager;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new ThreadLocalAwareBackgroundTaskExecutor(
			getBackgroundTaskExecutor(), _backgroundTaskThreadLocalManager);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> threadLocalValues =
			_backgroundTaskThreadLocalManager.getThreadLocalValues();

		try {
			_backgroundTaskThreadLocalManager.deserializeThreadLocals(
				backgroundTask.getTaskContextMap());

			return super.execute(backgroundTask);
		}
		finally {
			_backgroundTaskThreadLocalManager.setThreadLocalValues(
				threadLocalValues);
		}
	}

	private final BackgroundTaskThreadLocalManager
		_backgroundTaskThreadLocalManager;

}