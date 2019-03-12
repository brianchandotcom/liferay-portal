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
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class SQLTransformerFunctionFactory {

	public static Function<String, String> create() {
		DB db = DBManagerUtil.getDB();

		List<Function<String, String>> functions = new ArrayList<>();

		functions.add(
			sql -> CommonReplaces.replaceBoolean(
				sql, db.getTemplateFalse(), db.getTemplateTrue()));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(CommonReplaces::replaceLower);
		}

		DBType dbType = db.getDBType();

		if (dbType == DBType.DB2) {
			DB2Replaces.addReplaces(functions);
		}
		else if (dbType == DBType.HYPERSONIC) {
			HypersonicReplaces.addReplaces(functions);
		}
		else if ((dbType == DBType.MARIADB) || (dbType == DBType.MYSQL)) {
			MySQLReplaces.addReplaces(functions);
		}
		else if (dbType == DBType.ORACLE) {
			OracleReplaces.addReplaces(functions);
		}
		else if (dbType == DBType.POSTGRESQL) {
			PostgreSQLReplaces.addReplaces(functions);
		}
		else if (dbType == DBType.SQLSERVER) {
			SQLServerReplaces.addReplaces(functions);
		}
		else if (dbType == DBType.SYBASE) {
			SybaseReplaces.addReplaces(functions);
		}
		else {
			return Function.identity();
		}

		return sql -> _transform(sql, functions);
	}

	private static String _transform(
		String sql, List<Function<String, String>> functions) {

		if (Validator.isBlank(sql)) {
			return sql;
		}

		String transformedSQL = sql;

		for (Function<String, String> function : functions) {
			transformedSQL = function.apply(transformedSQL);
		}

		return transformedSQL;
	}

}