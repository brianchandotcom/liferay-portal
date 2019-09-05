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

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Andrew Betts
 */
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
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #_getBackgroundTaskDisplayFactory()}
	 */
	@Deprecated
	public static BackgroundTaskDisplayFactory
		getBackgroundTaskDisplayFactory() {

		return _getBackgroundTaskDisplayFactory();
	}

	private static BackgroundTaskDisplayFactory
		_getBackgroundTaskDisplayFactory() {

		return _backgroundTaskDisplayFactory;
	}

	private static volatile BackgroundTaskDisplayFactory
		_backgroundTaskDisplayFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				BackgroundTaskDisplayFactory.class,
				BackgroundTaskDisplayFactoryUtil.class,
				"_backgroundTaskDisplayFactory", false);

}