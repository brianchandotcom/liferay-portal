/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.service.CompanyLocalService;
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

	public QuartzUpgradeProcess(
		CompanyLocalService companyLocalService, JSONFactory jsonFactory) {

		_companyLocalService = companyLocalService;
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

				_loadJobNames(
					jobNamesMap, resultSet.getString("job_name"), jobDataMap);
			}
		}

		return jobNamesMap;
	}

	private boolean _containsColumnId(
			String tableName, String columnId, long columnValue)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select 1 from ", tableName, " where ", columnId, " = ",
					columnValue));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			return resultSet.next();
		}
	}

	private void _loadJobNames(
			Map<String, String> jobNamesMap, String jobName,
			JobDataMap jobDataMap)
		throws Exception {

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		if (destinationName.equals("liferay/layouts_local_publisher") ||
			destinationName.equals("liferay/layouts_remote_publisher")) {

			return;
		}

		Message message = (Message)_jsonFactory.deserialize(
			jobDataMap.getString(SchedulerEngine.MESSAGE));

		if (message.contains("companyId")) {
			jobNamesMap.put(
				jobName,
				jobName.concat(StringPool.AT + message.getLong("companyId")));

			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (jobNamesMap.containsKey(jobName)) {
					return;
				}

				if (destinationName.equals(
						"liferay/ct_collection_scheduled_publish")) {

					long ctCollectionId = message.getLong("ctCollectionId");

					if (_containsColumnId(
							"CTCollection", "ctCollectionId", ctCollectionId)) {

						jobNamesMap.put(
							jobName, jobName.concat(StringPool.AT + companyId));
					}
				}
				else if (destinationName.equals("liferay/dispatch/executor")) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(
						(String)message.getPayload());

					long dispatchTriggerId = jsonObject.getLong(
						"dispatchTriggerId");

					if (_containsColumnId(
							"DispatchTrigger", "dispatchTriggerId",
							dispatchTriggerId)) {

						jobNamesMap.put(
							jobName, jobName.concat(StringPool.AT + companyId));
					}
				}
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final JSONFactory _jsonFactory;

}