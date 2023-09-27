/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Eudaldo Alonso
 */
public class DDMFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMField set fieldName = ? where fieldId = ?");
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select fieldId, fieldName from DDMField")) {

			while (resultSet.next()) {
				String fieldName = resultSet.getString("fieldName");

				if ((fieldName == null) ||
					(fieldName.length() <= _MAX_LENGTH_FIELD_NAME)) {

					continue;
				}

				long fieldId = resultSet.getLong("fieldId");

				preparedStatement.setString(
					1, StringUtil.shorten(fieldName, _MAX_LENGTH_FIELD_NAME));
				preparedStatement.setLong(2, fieldId);

				preparedStatement.addBatch();

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Truncated the ", fieldName, " value for field ID ",
							fieldId, " because it is too long"));
				}
			}

			preparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.alterColumnType(
				"DDMField", "fieldName", "VARCHAR(255) null")
		};
	}

	private static final int _MAX_LENGTH_FIELD_NAME = 255;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldUpgradeProcess.class);

}