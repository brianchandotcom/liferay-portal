/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.partition;

import com.liferay.portal.db.partition.db.DBPartitionDB;
import com.liferay.portal.db.partition.db.DBPartitionMySQLDB;
import com.liferay.portal.db.partition.db.DBPartitionPostgreSQLDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaPartitionUtil {

	public static void setPartition(Connection connection, long companyId)
		throws SQLException {

		if (!DBPartition.isPartitionEnabled() ||
			(companyId == PortalInstancePool.getDefaultCompanyId())) {

			return;
		}

		DB db = DBManagerUtil.getDB();

		DBPartitionDB dbPartitionDB = null;

		if (db.getDBType() == DBType.MYSQL) {
			dbPartitionDB = new DBPartitionMySQLDB();
		}
		else {
			dbPartitionDB = new DBPartitionPostgreSQLDB();
		}

		dbPartitionDB.setPartition(
			connection, _DATABASE_PARTITION_SCHEMA_NAME_PREFIX + companyId);
	}

	private static final String _DATABASE_PARTITION_SCHEMA_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("database.partition.schema.name.prefix"),
			"lpartition_");

}