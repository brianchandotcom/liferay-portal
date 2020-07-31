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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Field;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class NestedDDMFieldArrayUtil {

	public static Field createField(
		String name, String valueFieldName, Object value) {

		Field field = new Field("");

		field.addField(new Field("ddmFieldName", name));
		field.addField(new Field("ddmValueFieldName", valueFieldName));

		if (value instanceof String) {
			field.addField(new Field(valueFieldName, (String)value));
		}
		else {
			field.addField(new Field(valueFieldName, (String[])value));
		}

		return field;
	}

	public static Optional<Object> getFieldValue(
		String name, Stream<Map<String, Object>> stream) {

		return stream.filter(
			map -> name.equals(map.get("ddmFieldName"))
		).map(
			map -> map.get(map.get("ddmValueFieldName"))
		).findAny();
	}

}