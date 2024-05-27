/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.upgrade.v2_0_1;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Avalos
 */
public class BackgroundTaskCompanyIdUpgradeProcess extends UpgradeProcess {

	public static void removeCompanyId(Map<String, Serializable> map) {
		Map<String, Serializable> taskContextMap =
			(Map<String, Serializable>)map.get("map");

		taskContextMap.remove("companyId");

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)taskContextMap.get("threadLocalValues");

		threadLocalValues = (Map<String, Serializable>)threadLocalValues.get(
			"map");

		threadLocalValues.remove("companyId");
	}

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
						ObjectMapper mapper = new ObjectMapper();

						Map<String, Serializable> taskContextMap =
							mapper.readValue(
								taskContextMapValue, LinkedHashMap.class);

						removeCompanyId(taskContextMap);

						taskContextMapValue = mapper.writeValueAsString(
							taskContextMap);

						preparedStatement.setString(1, taskContextMapValue);

						preparedStatement.setLong(2, (Long)values[0]);

						preparedStatement.addBatch();
					}
				},
				"Unable to remove companyId");
		}
	}

}