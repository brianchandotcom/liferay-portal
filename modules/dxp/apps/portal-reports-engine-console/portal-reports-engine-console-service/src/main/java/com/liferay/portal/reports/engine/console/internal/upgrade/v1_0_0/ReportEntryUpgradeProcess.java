/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wesley Gong
 * @author Calvin Keum
 */
public class ReportEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("Reports_Entry")) {
			_updateReportEntries();
		}
	}

	private String _updateEntryParameters(String reportParameters) {
		Matcher matcher = _pattern.matcher(reportParameters);

		if (!matcher.find()) {
			return reportParameters;
		}

		JSONArray reportParametersJSONArray = JSONFactoryUtil.createJSONArray();

		String[] keyValuePairs = StringUtil.split(reportParameters);

		for (String keyValuePair : keyValuePairs) {
			if (Validator.isNull(keyValuePair) ||
				!keyValuePair.contains(StringPool.EQUAL)) {

				continue;
			}

			reportParametersJSONArray.put(
				JSONUtil.put(
					"key", keyValuePair.split(StringPool.EQUAL)[0]
				).put(
					"value", keyValuePair.split(StringPool.EQUAL)[1]
				));
		}

		return reportParametersJSONArray.toString();
	}

	private void _updateReportEntries() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, entryId, reportParameters from " +
					"Reports_Entry")) {

			try (PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update Reports_Entry set reportParameters = ? where " +
							"companyId = ? and entryId = ?");
				ResultSet resultSet = preparedStatement1.executeQuery()) {

				while (resultSet.next()) {
					String reportParameters = resultSet.getString(
						"reportParameters");

					String updatedReportParameters = _updateEntryParameters(
						reportParameters);

					if (Validator.isNotNull(reportParameters) &&
						reportParameters.equals(updatedReportParameters)) {

						continue;
					}

					preparedStatement2.setString(1, updatedReportParameters);
					preparedStatement2.setLong(
						2, resultSet.getLong("companyId"));
					preparedStatement2.setLong(3, resultSet.getLong("entryId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final Pattern _pattern = Pattern.compile("[.*=.*]+");

}