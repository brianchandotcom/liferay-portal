/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Andrew Betts
 */
@ProviderType
public interface BackgroundTaskDisplayFactory {

	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask);

	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		long backgroundTaskId);

}