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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.BackgroundTask;

/**
 * @author Andrew Betts
 */
@ProviderType
public class BackgroundTaskDisplayFactoryUtil {

	public static BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return getBackgroundTaskDisplayFactory().getBackgroundTaskDisplay(
			backgroundTask);
	}

	public static BackgroundTaskDisplay getBackgroundTaskDisplay(
		long backgroundTaskId) {

		return getBackgroundTaskDisplayFactory().getBackgroundTaskDisplay(
			backgroundTaskId);
	}

	public static BackgroundTaskDisplayFactory
		getBackgroundTaskDisplayFactory() {

		PortalRuntimePermission.checkGetBeanProperty(
			BackgroundTaskStatusRegistryUtil.class);

		return _backgroundTaskDisplayFactory;
	}

	public void setBackgroundTaskDisplayFactory(
		BackgroundTaskDisplayFactory backgroundTaskDisplayFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_backgroundTaskDisplayFactory = backgroundTaskDisplayFactory;
	}

	private static BackgroundTaskDisplayFactory _backgroundTaskDisplayFactory;

}