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
public class LayoutPageTemplateCollectionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		_upgradeLayoutPageTemplateCollectionKey();
	}

	protected void upgradeSchema() throws Exception {
		alterTableAddColumn(
			"LayoutPageTemplateCollection", "lptCollectionKey", "VARCHAR(75)");
	}

	private String _generateLayoutPageTemplateCollectionKey(String name) {
		return StringUtil.replace(
			StringUtil.toLowerCase(name.trim()),
			new char[] {CharPool.FORWARD_SLASH, CharPool.SPACE},
			new char[] {CharPool.DASH, CharPool.DASH});
	}

	private void _upgradeLayoutPageTemplateCollectionKey() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select layoutPageTemplateCollectionId, name from " +
					"LayoutPageTemplateCollection");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update LayoutPageTemplateCollection set " +
						"lptCollectionKey = ? where " +
							"layoutPageTemplateCollectionId = ?")) {

			while (resultSet.next()) {
				long layoutPageTemplateCollectionId = resultSet.getLong(
					"layoutPageTemplateCollectionId");

				String name = resultSet.getString("name");

				preparedStatement.setString(
					1, _generateLayoutPageTemplateCollectionKey(name));

				preparedStatement.setLong(2, layoutPageTemplateCollectionId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

}