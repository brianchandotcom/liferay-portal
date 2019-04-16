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

package com.liferay.dynamic.data.lists.internal.upgrade.v2_1_1;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * @author Christopher Kian
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet rs = databaseMetaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName("DDLRecordSet"), null)) {

				while (rs.next()) {
					String columnName = StringUtil.toLowerCase(
						rs.getString("COLUMN_NAME"));

					if (columnName.equals("versionuserid")) {
						int dataType = rs.getInt("DATA_TYPE");

						if (dataType == Types.VARCHAR) {
							String template = StringUtil.read(
								UpgradeSchema.class.getResourceAsStream(
									"dependencies/update.sql"));

							runSQLTemplateString(template, false, false);
						}

						return;
					}
				}
			}
		}
	}

}