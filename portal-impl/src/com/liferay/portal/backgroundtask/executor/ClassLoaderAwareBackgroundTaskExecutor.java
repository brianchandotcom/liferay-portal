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

package com.liferay.portal.backgroundtask.executor;

import com.liferay.portal.model.BackgroundTask;

/**
 * @author Michael C. Han
 */
public class ClassLoaderAwareBackgroundTaskExecutor
	implements BackgroundTaskExecutor {

	public ClassLoaderAwareBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		ClassLoader classLoader) {

		_backgroundTaskExecutor = backgroundTaskExecutor;
		_classLoader = classLoader;
	}

	@Override
	public void execute(BackgroundTask backgroundTask) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (_classLoader != contextClassLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			_backgroundTaskExecutor.execute(backgroundTask);
		}
		finally {
			if (_classLoader != contextClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	@Override
	public boolean isSerial() {
		return _backgroundTaskExecutor.isSerial();
	}

	private BackgroundTaskExecutor _backgroundTaskExecutor;
	private ClassLoader _classLoader;

}