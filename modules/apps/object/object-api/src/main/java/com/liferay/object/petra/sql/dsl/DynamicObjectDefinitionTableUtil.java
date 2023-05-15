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

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.field.util.DBType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Feliphe Marinho
 */
public class DynamicObjectDefinitionTableUtil {

	/**
	 * @see com.liferay.portal.dao.db.BaseDB#alterTableAddColumn(
	 *      java.sql.Connection, String, String, String)
	 */
	public static String getAlterTableAddColumnSQL(
		String tableName, String columnName, String dbType) {

		String sql = StringBundler.concat(
			"alter table ", tableName, " add ", columnName, StringPool.SPACE,
			DBType.getDataType(dbType), getSQLColumnNull(dbType));

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	public static String getSQLColumnNull(String dbType) {
		if (dbType.equals(DBType.BIG_DECIMAL.getDBType()) ||
			dbType.equals(DBType.DOUBLE.getDBType()) ||
			dbType.equals(DBType.INTEGER.getDBType()) ||
			dbType.equals(DBType.LONG.getDBType())) {

			return " default 0";
		}
		else if (dbType.equals(DBType.BOOLEAN.getDBType())) {
			return " default FALSE";
		}
		else if (dbType.equals(DBType.DATE.getDBType())) {
			return " null";
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicObjectDefinitionTableUtil.class);

}