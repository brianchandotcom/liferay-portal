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

/**
 * @author Michael C. Han
 */
public class ClassLoaderAwareBackgroundTaskExecutor
	extends DelegatingBackgroundTaskExecutor {

	public ClassLoaderAwareBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		ClassLoader classLoader) {

		super(backgroundTaskExecutor);

		_classLoader = classLoader;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new ClassLoaderAwareBackgroundTaskExecutor(
			getBackgroundTaskExecutor(), _classLoader);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (_classLoader != contextClassLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			BackgroundTaskExecutor backgroundTaskExecutor =
				getBackgroundTaskExecutor();

			return backgroundTaskExecutor.execute(backgroundTask);
		}
		finally {
			if (_classLoader != contextClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private final ClassLoader _classLoader;

}