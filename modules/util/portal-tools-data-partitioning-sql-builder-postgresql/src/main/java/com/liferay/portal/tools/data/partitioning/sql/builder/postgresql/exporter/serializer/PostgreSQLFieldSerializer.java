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

package com.liferay.portal.tools.data.partitioning.sql.builder.postgresql.exporter.serializer;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.serializer.FieldSerializer;

import java.sql.Date;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLFieldSerializer implements FieldSerializer {

	@Override
	public String serialize(Object object) {
		StringBuilder sb = new StringBuilder();

		if (object == null) {
			sb.append("null");
		}
		else if (object instanceof Date || object instanceof Timestamp) {
			sb.append("to_timestamp('");
			sb.append(_dateFormat.format(object));
			sb.append("', 'YYYY-MM-DD HH24:MI:SS:MS')");
		}
		else if (object instanceof String) {
			sb.append("'");

			String value = (String)object;

			sb.append(value.replace("'", "''"));

			sb.append("'");
		}
		else {
			sb.append("'");
			sb.append(object);
			sb.append("'");
		}

		return sb.toString();
	}

	private final DateFormat _dateFormat = new SimpleDateFormat(
		"YYYY-MM-DD HH:MM:SS");

}