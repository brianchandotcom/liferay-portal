/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskExecutorRegistryUtil {

	public static BackgroundTaskExecutor getBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		return _backgroundTaskExecutorRegistry.getBackgroundTaskExecutor(
			backgroundTaskExecutorClassName);
	}

	private static volatile BackgroundTaskExecutorRegistry
		_backgroundTaskExecutorRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				BackgroundTaskExecutorRegistry.class,
				BackgroundTaskExecutorRegistryUtil.class,
				"_backgroundTaskExecutorRegistry", false);

}