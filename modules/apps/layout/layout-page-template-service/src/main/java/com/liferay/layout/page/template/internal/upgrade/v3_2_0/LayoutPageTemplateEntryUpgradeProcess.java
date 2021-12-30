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

package com.liferay.layout.page.template.internal.upgrade.v3_2_0;

import com.liferay.layout.page.template.internal.upgrade.v3_2_0.util.LayoutPageTemplateEntryTable;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		alter(
			LayoutPageTemplateEntryTable.class,
			new AlterTableAddColumn(
				"layoutPageTemplateEntryKey", "VARCHAR(75)"));
	}

	private String _generateLayoutPageTemplateEntryKey(String name) {
		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(name.trim());

		return StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);
	}

	private void _upgradeLayoutPageTemplateEntryKey() throws SQLException {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select layoutPageTemplateEntryId, name from " +
					"LayoutPageTemplateEntry");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update LayoutPageTemplateEntry set " +
							"layoutPageTemplateEntryKey = ? where " +
								"layoutPageTemplateEntryId = ?"))) {

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