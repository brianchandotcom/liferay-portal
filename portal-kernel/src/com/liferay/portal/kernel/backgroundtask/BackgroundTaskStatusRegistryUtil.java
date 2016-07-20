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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusRegistryUtil {

	public static BackgroundTaskStatus getBackgroundTaskStatus(
		long backgroundTaskId) {

		return _getBackgroundTaskStatusRegistry().getBackgroundTaskStatus(
			backgroundTaskId);
	}

	/**
	 * @deprecated As of 7.1.0, replaced by {@link #_getBackgroundTaskStatusRegistry()}
	 */
	@Deprecated
	public static BackgroundTaskStatusRegistry
		getBackgroundTaskStatusRegistry() {

		return _getBackgroundTaskStatusRegistry();
	}

	public static BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId) {

		return _getBackgroundTaskStatusRegistry().registerBackgroundTaskStatus(
			backgroundTaskId);
	}

	public static BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		return _getBackgroundTaskStatusRegistry().
			unregisterBackgroundTaskStatus(backgroundTaskId);
	}

	private static BackgroundTaskStatusRegistry
		_getBackgroundTaskStatusRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			BackgroundTaskStatusRegistryUtil.class);

		return _backgroundTaskStatusRegistry;
	}

	private static volatile BackgroundTaskStatusRegistry
		_backgroundTaskStatusRegistry = ProxyFactory.newServiceTrackedInstance(
			BackgroundTaskStatusRegistry.class,
			BackgroundTaskStatusRegistryUtil.class,
			"_backgroundTaskStatusRegistry");

}