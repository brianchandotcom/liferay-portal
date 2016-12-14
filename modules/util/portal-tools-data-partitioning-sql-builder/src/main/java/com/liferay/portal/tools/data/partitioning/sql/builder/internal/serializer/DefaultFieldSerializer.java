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

package com.liferay.portal.tools.data.partitioning.sql.builder.internal.serializer;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.serializer.FieldSerializer;

import java.sql.Date;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Miguel Pastor
 */
public class DefaultFieldSerializer implements FieldSerializer {

	public DefaultFieldSerializer() {
		_dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
	}

	public DefaultFieldSerializer(DateFormat dateFormat) {
		_dateFormat = dateFormat;
	}

	@Override
	public String serialize(Object object) {
		StringBuilder sb = new StringBuilder();

		if (object == null) {
			sb.append("null");
		}
		else if (object instanceof Date || object instanceof Timestamp) {
			sb.append("'");
			sb.append(_dateFormat.format(object));
			sb.append("'");
		}
		else if (object instanceof String) {
			String value = (String)object;

			value = value.replace("'", "''");

			sb.append("'");
			sb.append(value);
			sb.append("'");
		}
		else {
			sb.append("'");
			sb.append(object);
			sb.append("'");
		}

		return sb.toString();
	}

	private final DateFormat _dateFormat;

}