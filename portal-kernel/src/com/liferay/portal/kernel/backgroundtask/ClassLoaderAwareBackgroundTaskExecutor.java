/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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