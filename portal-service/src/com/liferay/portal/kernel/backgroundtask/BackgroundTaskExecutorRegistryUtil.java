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

import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskExecutorRegistryUtil {

	public static BackgroundTaskExecutor getBackgroundTaskExecutor(
		String className) {

		return _backgroundTaskExecutorRegistry.getBackgroundTaskExecutor(
			className);
	}

	public static void registerBackgroundTaskExecutor(
		String className, BackgroundTaskExecutor backgroundTaskExecutor) {

		_backgroundTaskExecutorRegistry.registerBackgroundTaskExecutor(
			className, backgroundTaskExecutor);
	}

	public static void unregisterBackgroundTaskExecutor(String className) {
		_backgroundTaskExecutorRegistry.unregisterBackgroundTaskExecutor(
			className);
	}

	private static final BackgroundTaskExecutorRegistry
		_backgroundTaskExecutorRegistry =
			ProxyFactory.newServiceTrackedInstance(
				BackgroundTaskExecutorRegistry.class);

}