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

	public static void registerBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		_backgroundTaskExecutorRegistry.registerBackgroundTaskExecutor(
			backgroundTaskExecutorClassName, backgroundTaskExecutor);
	}

	public static void unregisterBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		_backgroundTaskExecutorRegistry.unregisterBackgroundTaskExecutor(
			backgroundTaskExecutorClassName);
	}

	private static volatile BackgroundTaskExecutorRegistry
		_backgroundTaskExecutorRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				BackgroundTaskExecutorRegistry.class,
				BackgroundTaskExecutorRegistryUtil.class,
				"_backgroundTaskExecutorRegistry", false);

}