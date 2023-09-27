/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

/**
 * @author Michael C. Han
 */
public interface BackgroundTaskStatusRegistry {

	public BackgroundTaskStatus getBackgroundTaskStatus(long backgroundTaskId);

	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator(long backgroundTaskId);

	public BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator);

	public BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId);

}