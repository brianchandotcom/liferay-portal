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
		Map<String, Long> jobCompanyIds = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select job_name, job_data from QUARTZ_JOB_DETAILS where " +
					"job_name not like '%@%'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				JobDataMap jobDataMap = _deserializeJobData(
					resultSet.getBinaryStream("job_data"));

				_fetchCompanyId(
					jobCompanyIds, resultSet.getString("job_name"), jobDataMap);
			}
		}

		_updateTables(
			jobCompanyIds, "job_name",
			new String[] {
				"QUARTZ_FIRED_TRIGGERS", "QUARTZ_JOB_DETAILS", "QUARTZ_TRIGGERS"
			});

		_updateTables(
			jobCompanyIds, "trigger_name",
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

	private void _fetchCompanyId(
			Map<String, Long> jobCompanyIds, String jobName,
			JobDataMap jobDataMap)
		throws Exception {

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		if (destinationName.equals(_LAYOUTS_LOCAL_PUBLISHER) ||
			destinationName.equals(_LAYOUTS_REMOTE_PUBLISHER)) {

			return;
		}

		Message message = (Message)_jsonFactory.deserialize(
			jobDataMap.getString(SchedulerEngine.MESSAGE));

		if (message.contains("companyId")) {
			jobCompanyIds.put(jobName, message.getLong("companyId"));

			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (jobCompanyIds.containsKey(jobName)) {
					return;
				}

				if (destinationName.equals(_CT_COLLECTION_SCHEDULED_PUBLISH)) {
					long ctCollectionId = message.getLong("ctCollectionId");

					if (_containsColumnId(
							"CTCollection", "ctCollectionId", ctCollectionId)) {

						jobCompanyIds.put(jobName, companyId);
					}
				}
				else if (destinationName.equals(_DISPATCH_EXECUTOR)) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(
						(String)message.getPayload());

					long dispatchTriggerId = jsonObject.getLong(
						"dispatchTriggerId");

					if (_containsColumnId(
							"DispatchTrigger", "dispatchTriggerId",
							dispatchTriggerId)) {

						jobCompanyIds.put(jobName, companyId);
					}
				}
			});
	}

	private void _updateTables(
			Map<String, Long> jobCompanyIds, String columnName,
			String[] tableNames)
		throws Exception {

		for (String tableName : tableNames) {
			try (PreparedStatement preparedStatement =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						StringBundler.concat(
							"update ", tableName, " set ", columnName,
							" = ? where ", columnName, " = ?"))) {

				for (Map.Entry<String, Long> entry : jobCompanyIds.entrySet()) {
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

	private static final String _CT_COLLECTION_SCHEDULED_PUBLISH =
		"liferay/ct_collection_scheduled_publish";

	private static final String _DISPATCH_EXECUTOR =
		"liferay/dispatch/executor";

	private static final String _LAYOUTS_LOCAL_PUBLISHER =
		"liferay/layouts_local_publisher";

	private static final String _LAYOUTS_REMOTE_PUBLISHER =
		"liferay/layouts_remote_publisher";

	private final CompanyLocalService _companyLocalService;
	private final JSONFactory _jsonFactory;

}