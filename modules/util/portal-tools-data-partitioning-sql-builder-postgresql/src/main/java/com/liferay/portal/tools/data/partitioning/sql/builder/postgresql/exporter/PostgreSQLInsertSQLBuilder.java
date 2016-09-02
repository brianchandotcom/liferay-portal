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

package com.liferay.portal.tools.data.partitioning.sql.builder.postgresql.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.InsertSQLBuilder;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLInsertSQLBuilder extends InsertSQLBuilder {

	@Override
	public String serializeTableField(Object field) {
		StringBuilder sb = new StringBuilder();

		if (field == null) {
			sb.append("null");
		}
		else if ((field instanceof Date) || (field instanceof Timestamp)) {
			sb.append("to_timestamp('");
			sb.append(formatDateTime(field));
			sb.append("', 'YYYY-MM-DD HH24:MI:SS:MS')");
		}
		else if (field instanceof String) {
			sb.append("'");

			String value = (String)field;

			sb.append(value.replace("'", "''"));

			sb.append("'");
		}
		else {
			sb.append("'");
			sb.append(field);
			sb.append("'");
		}

		return sb.toString();
	}

}