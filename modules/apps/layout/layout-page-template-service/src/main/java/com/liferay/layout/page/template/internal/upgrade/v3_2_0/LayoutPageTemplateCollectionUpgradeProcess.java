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

import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateCollectionTable;
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
		alter(
			LayoutPageTemplateCollectionTable.class,
			new AlterTableAddColumn("lptCollectionKey", "VARCHAR(75)"));
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
					connection.prepareStatement(
						"update LayoutPageTemplateCollection set " +
							"lptCollectionKey = ? where " +
								"layoutPageTemplateCollectionId = ?"))) {

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