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

import java.util.List;
import java.util.function.Function;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class SQLTransformerFactory {

	public static SQLTransformer getSQLTransformer(DB db) {
		DBType dbType = db.getDBType();

		List<Function<String, String>> functions = null;

		if (dbType == DBType.DB2) {
			functions = DB2SQLTransformerLogic.getFunctions(db);
		}
		else if (dbType == DBType.HYPERSONIC) {
			functions = HypersonicSQLTransformerLogic.getFunctions(db);
		}
		else if ((dbType == DBType.MARIADB) || (dbType == DBType.MYSQL)) {
			functions = MySQLSQLTransformerLogic.getFunctions(db);
		}
		else if (dbType == DBType.ORACLE) {
			functions = OracleSQLTransformerLogic.getFunctions(db);
		}
		else if (dbType == DBType.POSTGRESQL) {
			functions = PostgreSQLTransformerLogic.getFunctions(db);
		}
		else if (dbType == DBType.SQLSERVER) {
			functions = SQLServerSQLTransformerLogic.getFunctions(db);
		}
		else if (dbType == DBType.SYBASE) {
			functions = SybaseSQLTransformerLogic.getFunctions(db);
		}
		else {
			return sql -> sql;
		}

		return new DefaultSQLTransformer(functions);
	}

}