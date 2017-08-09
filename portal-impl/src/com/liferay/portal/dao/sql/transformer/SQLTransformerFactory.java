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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBType;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class SQLTransformerFactory {

	public static SQLTransformer getSQLTransformer(DB db) {
		DBType dbType = db.getDBType();

		SQLTransformerLogic sqlTransformerLogic = null;

		if (dbType == DBType.DB2) {
			sqlTransformerLogic = new DB2SQLTransformerLogic(db);
		}
		else if (dbType == DBType.HYPERSONIC) {
			sqlTransformerLogic = new HypersonicSQLTransformerLogic(db);
		}
		else if ((dbType == DBType.MARIADB) || (dbType == DBType.MYSQL)) {
			sqlTransformerLogic = new MySQLSQLTransformerLogic(db);
		}
		else if (dbType == DBType.ORACLE) {
			sqlTransformerLogic = new OracleSQLTransformerLogic(db);
		}
		else if (dbType == DBType.POSTGRESQL) {
			sqlTransformerLogic = new PostgreSQLTransformerLogic(db);
		}
		else if (dbType == DBType.SQLSERVER) {
			sqlTransformerLogic = new SQLServerSQLTransformerLogic(db);
		}
		else if (dbType == DBType.SYBASE) {
			sqlTransformerLogic = new SybaseSQLTransformerLogic(db);
		}
		else {
			return sql -> sql;
		}

		return new DefaultSQLTransformer(sqlTransformerLogic.getFunctions());
	}

}