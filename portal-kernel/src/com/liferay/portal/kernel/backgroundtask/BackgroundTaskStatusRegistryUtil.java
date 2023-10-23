/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusRegistryUtil {

	public static BackgroundTaskStatus getBackgroundTaskStatus(
		long backgroundTaskId) {

		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry =
			_backgroundTaskStatusRegistrySnapshot.get();

		return backgroundTaskStatusRegistry.getBackgroundTaskStatus(
			backgroundTaskId);
	}

	public static BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry =
			_backgroundTaskStatusRegistrySnapshot.get();

		return backgroundTaskStatusRegistry.registerBackgroundTaskStatus(
			backgroundTaskId, backgroundTaskStatusMessageTranslator);
	}

	public static BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry =
			_backgroundTaskStatusRegistrySnapshot.get();

		return backgroundTaskStatusRegistry.unregisterBackgroundTaskStatus(
			backgroundTaskId);
	}

	private static final Snapshot<BackgroundTaskStatusRegistry>
		_backgroundTaskStatusRegistrySnapshot = new Snapshot<>(
			BackgroundTaskStatusRegistryUtil.class,
			BackgroundTaskStatusRegistry.class);

}