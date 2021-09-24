/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.account.internal.upgrade.v9_0_1;

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
			"UPDATE Layout SET typeSettings = ? WHERE layoutId = ?";

		String selectLayout = StringBundler.concat(
			"SELECT layoutId, typeSettings FROM Layout WHERE typeSettings ",
			"LIKE '%", _COMMERCE_ACCOUNT_PORTLET, "%'");

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
						typeSettings, _COMMERCE_ACCOUNT_PORTLET,
						AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT));

				preparedStatement.setLong(2, layoutId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private static final String _COMMERCE_ACCOUNT_PORTLET =
		"com_liferay_commerce_account_web_internal_portlet_" +
			"CommerceAccountPortlet";

}