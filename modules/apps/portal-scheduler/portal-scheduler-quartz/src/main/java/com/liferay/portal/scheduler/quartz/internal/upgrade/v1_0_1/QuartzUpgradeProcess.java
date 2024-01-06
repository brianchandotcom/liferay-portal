/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_1;

import com.liferay.petra.io.ProtectedObjectInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;

/**
 * @author Kevin Lee
 */
public class QuartzUpgradeProcess extends UpgradeProcess {

	public QuartzUpgradeProcess(
		CompanyLocalService companyLocalService, JSONFactory jsonFactory) {

		_companyLocalService = companyLocalService;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, Long> companyIds = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select job_name, job_data from QUARTZ_JOB_DETAILS where " +
					"job_name not like '%@%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				JobDataMap jobDataMap = _deserializeJobData(
					resultSet.getBinaryStream("job_data"));

				_loadCompanyIds(
					companyIds, resultSet.getString("job_name"), jobDataMap);
			}
		}

		_updateTables(
			companyIds, "job_name",
			new String[] {
				"QUARTZ_FIRED_TRIGGERS", "QUARTZ_JOB_DETAILS", "QUARTZ_TRIGGERS"
			});

		_updateTables(
			companyIds, "trigger_name",
			new String[] {
				"QUARTZ_BLOB_TRIGGERS", "QUARTZ_CRON_TRIGGERS",
				"QUARTZ_FIRED_TRIGGERS", "QUARTZ_SIMPLE_TRIGGERS",
				"QUARTZ_SIMPROP_TRIGGERS", "QUARTZ_TRIGGERS"
			});
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

	private JobDataMap _deserializeJobData(InputStream inputStream)
		throws Exception {

		try (ProtectedObjectInputStream protectedObjectInputStream =
				new ProtectedObjectInputStream(inputStream)) {

			return (JobDataMap)protectedObjectInputStream.readObject();
		}
	}

	private void _loadCompanyIds(
			Map<String, Long> companyIds, String jobName, JobDataMap jobDataMap)
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
			companyIds.put(jobName, message.getLong("companyId"));

			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (companyIds.containsKey(jobName)) {
					return;
				}

				if (destinationName.equals(
						"liferay/ct_collection_scheduled_publish")) {

					long ctCollectionId = message.getLong("ctCollectionId");

					if (_containsColumnId(
							"CTCollection", "ctCollectionId", ctCollectionId)) {

						companyIds.put(jobName, companyId);
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

						companyIds.put(jobName, companyId);
					}
				}
			});
	}

	private void _updateTables(
			Map<String, Long> companyIds, String columnName,
			String[] tableNames)
		throws Exception {

		for (String tableName : tableNames) {
			try (PreparedStatement preparedStatement =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						StringBundler.concat(
							"update ", tableName, " set ", columnName,
							" = ? where ", columnName, " = ?"))) {

				for (Map.Entry<String, Long> entry : companyIds.entrySet()) {
					preparedStatement.setString(
						1,
						StringBundler.concat(
							entry.getKey(), StringPool.AT, entry.getValue()));

					preparedStatement.setString(2, entry.getKey());

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final JSONFactory _jsonFactory;

}