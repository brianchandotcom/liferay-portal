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
		String tableName, String columnName, String type) {

		String sql = StringBundler.concat(
			"alter table ", tableName, " add ", columnName, StringPool.SPACE,
			DataType.getDBType(type), getSQLColumnNull(type));

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	public static String getSQLColumnNull(String type) {
		if (type.equals("BigDecimal") || type.equals("Double") ||
			type.equals("Integer") || type.equals("Long")) {

			return " default 0";
		}
		else if (type.equals("Boolean")) {
			return " default FALSE";
		}
		else if (type.equals("Date")) {
			return " null";
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicObjectDefinitionTableUtil.class);

}