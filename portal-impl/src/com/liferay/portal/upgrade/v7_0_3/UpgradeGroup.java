/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

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

		try (ResultSet resultSet = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				dbInspector.normalizeName("Group_"),
				dbInspector.normalizeName("groupKey"))) {

			if (resultSet.next()) {
				int columnSize = resultSet.getInt("COLUMN_SIZE");
				int dataType = resultSet.getInt("DATA_TYPE");

				if ((dataType != Types.VARCHAR) || (columnSize != 150)) {
					alterColumnType("Group_", "groupKey", "VARCHAR(150) null");
				}
			}
		}
	}

}