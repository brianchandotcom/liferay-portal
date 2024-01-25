/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade;

import com.liferay.petra.io.ProtectedClassLoaderObjectInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.InputStream;

import java.sql.PreparedStatement;

import java.util.Map;

import org.quartz.JobDataMap;

/**
 * @author Kevin Lee
 */
public abstract class BaseQuartzRenameJobsUpgradeProcess
	extends UpgradeProcess {

	protected JobDataMap deserializeJobDataMap(InputStream inputStream)
		throws Exception {

		try (ProtectedClassLoaderObjectInputStream
				protectedClassLoaderObjectInputStream =
					new ProtectedClassLoaderObjectInputStream(
						inputStream,
						BaseQuartzRenameJobsUpgradeProcess.class.
							getClassLoader())) {

			return (JobDataMap)
				protectedClassLoaderObjectInputStream.readObject();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, String> jobNamesMap = getJobNamesMap();

		_updateTables(
			jobNamesMap, "job_name",
			new String[] {
				"QUARTZ_FIRED_TRIGGERS", "QUARTZ_JOB_DETAILS", "QUARTZ_TRIGGERS"
			});

		_updateTables(
			jobNamesMap, "trigger_name",
			new String[] {
				"QUARTZ_BLOB_TRIGGERS", "QUARTZ_CRON_TRIGGERS",
				"QUARTZ_FIRED_TRIGGERS", "QUARTZ_SIMPLE_TRIGGERS",
				"QUARTZ_SIMPROP_TRIGGERS", "QUARTZ_TRIGGERS"
			});
	}

	protected abstract Map<String, String> getJobNamesMap() throws Exception;

	private void _updateTables(
			Map<String, String> jobNamesMap, String columnName,
			String[] tableNames)
		throws Exception {

		for (String tableName : tableNames) {
			try (PreparedStatement preparedStatement =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection,
						StringBundler.concat(
							"update ", tableName, " set ", columnName,
							" = ? where ", columnName, " = ?"))) {

				for (Map.Entry<String, String> entry : jobNamesMap.entrySet()) {
					preparedStatement.setString(1, entry.getValue());
					preparedStatement.setString(2, entry.getKey());
					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

}