/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_2_0;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		_upgradeLayoutPageTemplateEntryKey();
	}

	protected void upgradeSchema() throws Exception {
		alterTableAddColumn(
			"LayoutPageTemplateEntry", "layoutPageTemplateEntryKey",
			"VARCHAR(75)");
	}

	private String _generateLayoutPageTemplateEntryKey(String name) {
		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(name.trim());

		return StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);
	}

	private void _upgradeLayoutPageTemplateEntryKey() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select layoutPageTemplateEntryId, name from " +
					"LayoutPageTemplateEntry");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutPageTemplateEntry set " +
						"layoutPageTemplateEntryKey = ? where " +
							"layoutPageTemplateEntryId = ?")) {

			while (resultSet.next()) {
				long layoutPageTemplateEntryId = resultSet.getLong(
					"layoutPageTemplateEntryId");

				String name = resultSet.getString("name");

				preparedStatement.setString(
					1, _generateLayoutPageTemplateEntryKey(name));

				preparedStatement.setLong(2, layoutPageTemplateEntryId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

}