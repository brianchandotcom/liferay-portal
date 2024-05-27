/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Jorge Avalos
 */
public class BackgroundTaskCompanyIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"select backgroundTaskId, taskContextMap from BackgroundTask",
				"update BackgroundTask set taskContextMap = ? where " +
					"backgroundTaskId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("backgroundTaskId"),
					resultSet.getString("taskContextMap")
				},
				(values, preparedStatement) -> {
					String taskContextMapValue = (String)values[1];

					if (taskContextMapValue != null) {
						Map<String, Serializable> taskContextMap =
							(Map<String, Serializable>)
								JSONFactoryUtil.deserialize(
									taskContextMapValue);

						taskContextMap.remove("companyId");

						Map<String, Serializable> threadLocalValues =
							(Map<String, Serializable>)taskContextMap.get(
								"threadLocalValues");

						if (threadLocalValues != null) {
							threadLocalValues.remove("companyId");
						}

						preparedStatement.setString(
							1, JSONFactoryUtil.serialize(taskContextMap));

						preparedStatement.setLong(2, (Long)values[0]);

						preparedStatement.addBatch();
					}
				},
				"Unable to remove companyId");
		}
	}

}