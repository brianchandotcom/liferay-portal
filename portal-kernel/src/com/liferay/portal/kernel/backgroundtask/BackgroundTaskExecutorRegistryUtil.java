/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskExecutorRegistryUtil {

	public static BackgroundTaskExecutor getBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		BackgroundTaskExecutorRegistry backgroundTaskExecutorRegistry =
			_backgroundTaskExecutorRegistrySnapshot.get();

		return backgroundTaskExecutorRegistry.getBackgroundTaskExecutor(
			backgroundTaskExecutorClassName);
	}

	private static final Snapshot<BackgroundTaskExecutorRegistry>
		_backgroundTaskExecutorRegistrySnapshot = new Snapshot<>(
			BackgroundTaskExecutorRegistryUtil.class,
			BackgroundTaskExecutorRegistry.class);

}