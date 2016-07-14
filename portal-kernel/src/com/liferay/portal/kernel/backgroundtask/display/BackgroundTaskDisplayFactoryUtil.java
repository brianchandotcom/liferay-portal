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

package com.liferay.portal.kernel.backgroundtask.display;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Andrew Betts
 */
@ProviderType
public class BackgroundTaskDisplayFactoryUtil {

	public static BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return _getBackgroundTaskDisplayFactory().getBackgroundTaskDisplay(
			backgroundTask);
	}

	public static BackgroundTaskDisplay getBackgroundTaskDisplay(
		long backgroundTaskId) {

		return _getBackgroundTaskDisplayFactory().getBackgroundTaskDisplay(
			backgroundTaskId);
	}

	/**
	 * @deprecated As of 7.1.0, replaced by {@link #_getBackgroundTaskDisplayFactory()}
	 */
	@Deprecated
	public static BackgroundTaskDisplayFactory
		getBackgroundTaskDisplayFactory() {

		return _getBackgroundTaskDisplayFactory();
	}

	private static BackgroundTaskDisplayFactory
		_getBackgroundTaskDisplayFactory() {

		PortalRuntimePermission.checkGetBeanProperty(
			BackgroundTaskStatusRegistryUtil.class);

		return _backgroundTaskDisplayFactory;
	}

	private static volatile BackgroundTaskDisplayFactory
		_backgroundTaskDisplayFactory = ProxyFactory.newServiceTrackedInstance(
			BackgroundTaskDisplayFactory.class,
			BackgroundTaskDisplayFactoryUtil.class,
			"_backgroundTaskDisplayFactory");

}