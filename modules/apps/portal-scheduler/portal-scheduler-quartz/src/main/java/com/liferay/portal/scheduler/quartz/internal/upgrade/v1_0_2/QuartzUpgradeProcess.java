/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.v1_0_2;

import com.liferay.petra.io.ProtectedObjectInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
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

	public QuartzUpgradeProcess(JSONFactory jsonFactory) {
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

				companyIds.put(
					resultSet.getString("job_name"),
					message.getLong("companyId"));
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

	private JobDataMap _deserializeJobData(InputStream inputStream)
		throws Exception {

		try (ProtectedObjectInputStream protectedObjectInputStream =
				new ProtectedObjectInputStream(inputStream)) {

			return (JobDataMap)protectedObjectInputStream.readObject();
		}
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

	private final JSONFactory _jsonFactory;

}