/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Daniel Raposo
 */
public class BackgroundTaskProgressUtil {

	public static Integer getPercentage(long backgroundTaskId) {
		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTaskId);

		if (backgroundTaskStatus == null) {
			return null;
		}

		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));

		long allCounters =
			allModelAdditionCountersTotal + allPortletAdditionCounter;

		if (allCounters <= 0) {
			return 0;
		}

		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				"currentModelAdditionCountersTotal"));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		long currentCounters =
			currentModelAdditionCountersTotal + currentPortletAdditionCounter;

		long scaledCounters = currentCounters * 100;

		return (int)(scaledCounters / allCounters);
	}

}