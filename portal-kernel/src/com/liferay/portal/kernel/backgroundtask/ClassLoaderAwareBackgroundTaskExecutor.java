/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;

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

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			BackgroundTaskExecutor backgroundTaskExecutor =
				getBackgroundTaskExecutor();

			return backgroundTaskExecutor.execute(backgroundTask);
		}
	}

	private final ClassLoader _classLoader;

}