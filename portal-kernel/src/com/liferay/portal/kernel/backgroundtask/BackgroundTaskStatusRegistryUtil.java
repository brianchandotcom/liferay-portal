/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusRegistryUtil {

	public static BackgroundTaskStatus getBackgroundTaskStatus(
		long backgroundTaskId) {

		return _backgroundTaskStatusRegistry.getBackgroundTaskStatus(
			backgroundTaskId);
	}

	public static BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		return _backgroundTaskStatusRegistry.registerBackgroundTaskStatus(
			backgroundTaskId, backgroundTaskStatusMessageTranslator);
	}

	public static BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		return _backgroundTaskStatusRegistry.unregisterBackgroundTaskStatus(
			backgroundTaskId);
	}

	private static volatile BackgroundTaskStatusRegistry
		_backgroundTaskStatusRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				BackgroundTaskStatusRegistry.class,
				BackgroundTaskStatusRegistryUtil.class,
				"_backgroundTaskStatusRegistry", false);

}