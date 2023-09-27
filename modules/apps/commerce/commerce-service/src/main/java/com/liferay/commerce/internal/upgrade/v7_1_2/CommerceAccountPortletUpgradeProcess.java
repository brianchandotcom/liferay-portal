/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v7_1_2;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ivica Cardic
 */
public class CommerceAccountPortletUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String updateLayout =
			"update Layout set typeSettings = ? where layoutId = ?";

		String selectLayout = StringBundler.concat(
			"select layoutId, typeSettings from Layout where typeSettings ",
			"like '%", _PORTLET_ID, "%'");

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateLayout);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectLayout)) {

			while (resultSet.next()) {
				long layoutId = resultSet.getLong("layoutId");

				String typeSettings = resultSet.getString("typeSettings");

				preparedStatement.setString(
					1,
					StringUtil.replace(
						typeSettings, _PORTLET_ID,
						AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT));

				preparedStatement.setLong(2, layoutId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private static final String _PORTLET_ID =
		"com_liferay_commerce_account_web_internal_portlet_" +
			"CommerceAccountPortlet";

}