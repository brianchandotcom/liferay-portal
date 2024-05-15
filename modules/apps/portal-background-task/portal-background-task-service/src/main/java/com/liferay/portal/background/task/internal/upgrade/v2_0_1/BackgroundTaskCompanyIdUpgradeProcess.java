/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @author Jorge Avalos
 */

package com.liferay.portal.background.task.internal.upgrade.v2_0_1;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.Serializable;

import java.util.Map;

public class BackgroundTaskCompanyIdUpgradeProcess extends UpgradeProcess {

	public BackgroundTaskCompanyIdUpgradeProcess(
		BackgroundTaskLocalService backgroundTaskLocalService) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"Select backgroundTaskId from BackgroundTask",
				resultSet -> new Object[] {
					resultSet.getLong("backgroundTaskId")
				},
				values -> {
					BackgroundTask backgroundTask =
						_backgroundTaskLocalService.getBackgroundTask(
							(Long)values[0]);

					Map<String, Serializable> taskContextMap =
						backgroundTask.getTaskContextMap();

					taskContextMap.remove("companyId");

					Map<String, Serializable> threadLocalValues =
						(Map<String, Serializable>)taskContextMap.get(
							"threadLocalValues");

					threadLocalValues.remove("companyId");

					taskContextMap.replace(
						"threadLocalValues", (Serializable)threadLocalValues);

					backgroundTask.setTaskContextMap(taskContextMap);

					_backgroundTaskLocalService.updateBackgroundTask(
						backgroundTask);
				},
				"Failed to clear companyId from task context map");
		}
	}

	private final BackgroundTaskLocalService _backgroundTaskLocalService;

}