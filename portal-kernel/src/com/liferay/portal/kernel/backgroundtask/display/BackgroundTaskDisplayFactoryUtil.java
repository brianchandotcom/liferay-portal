/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return _backgroundTaskDisplayFactory.getBackgroundTaskDisplay(
			backgroundTask);
	}

	public static BackgroundTaskDisplay getBackgroundTaskDisplay(
		long backgroundTaskId) {

		return _backgroundTaskDisplayFactory.getBackgroundTaskDisplay(
			backgroundTaskId);
	}

	private static volatile BackgroundTaskDisplayFactory
		_backgroundTaskDisplayFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				BackgroundTaskDisplayFactory.class,
				BackgroundTaskDisplayFactoryUtil.class,
				"_backgroundTaskDisplayFactory", false);

}