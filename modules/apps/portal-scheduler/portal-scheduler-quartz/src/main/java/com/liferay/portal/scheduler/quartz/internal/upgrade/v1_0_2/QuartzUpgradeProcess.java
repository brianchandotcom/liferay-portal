/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.scheduler.quartz.internal.upgrade.BaseQuartzRenameJobsUpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;

/**
 * @author Kevin Lee
 */
public class QuartzUpgradeProcess extends BaseQuartzRenameJobsUpgradeProcess {

	public QuartzUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected Map<String, String> getJobNamesMap() throws Exception {
		Map<String, String> jobNamesMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select job_name, job_data from QUARTZ_JOB_DETAILS where " +
					"job_name not like '%@%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				JobDataMap jobDataMap = deserializeJobDataMap(
					resultSet.getBinaryStream("job_data"));

				String destinationName = jobDataMap.getString(
					SchedulerEngine.DESTINATION_NAME);

				if (!destinationName.equals(
						"liferay/layouts_local_publisher") &&
					!destinationName.equals(
						"liferay/layouts_remote_publisher")) {

					continue;
				}

				Message message = (Message)_jsonFactory.deserialize(
					jobDataMap.getString(SchedulerEngine.MESSAGE));

				String jobName = resultSet.getString("job_name");

				jobNamesMap.put(
					jobName,
					jobName.concat(
						StringPool.AT + message.getLong("companyId")));
			}
		}

		return jobNamesMap;
	}

	private final JSONFactory _jsonFactory;

}