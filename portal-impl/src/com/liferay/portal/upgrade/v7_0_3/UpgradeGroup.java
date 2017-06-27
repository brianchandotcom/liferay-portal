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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_0_3.util.GroupTable;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * @author Alberto Chaparro
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		DBInspector dbInspector = new DBInspector(connection);

		try (ResultSet rs = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				dbInspector.normalizeName("Group_"),
				dbInspector.normalizeName("groupKey"))) {

			if (rs.next()) {
				int columnSize = rs.getInt("COLUMN_SIZE");
				int dataType = rs.getInt("DATA_TYPE");

				if ((dataType != Types.VARCHAR) || (columnSize != 150)) {
					alter(
						GroupTable.class,
						new AlterColumnType("groupKey", "VARCHAR(150) null"));
				}
			}
		}
	}

}