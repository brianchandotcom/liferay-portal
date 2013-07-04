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

package com.liferay.portal.kernel.backgroundtask.status;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusRegistryUtil {

	public static BackgroundTaskStatus fetch(long backgroundTaskId) {
		return getBackgroundTaskStatusRegistry().fetch(backgroundTaskId);
	}

	public static BackgroundTaskStatusRegistry
		getBackgroundTaskStatusRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			BackgroundTaskStatusRegistry.class);

		return _backgroundTaskStatusRegistry;
	}

	public static BackgroundTaskStatus register(long backgroundTaskId) {
		return getBackgroundTaskStatusRegistry().register(backgroundTaskId);
	}

	public static BackgroundTaskStatus unregister(long backgroundTaskId) {
		return getBackgroundTaskStatusRegistry().unregister(backgroundTaskId);
	}

	public void setBackgroundTaskStatusRegistry(
		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_backgroundTaskStatusRegistry = backgroundTaskStatusRegistry;
	}

	private static BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

}