/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Field;

import java.util.List;
import java.util.Map;

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

	public static Object getFieldValue(
		String name, List<Map<String, Object>> maps) {

		for (Map<String, Object> map : maps) {
			if (name.equals(map.get("ddmFieldName"))) {
				Object fieldValue = map.get(map.get("ddmValueFieldName"));

				if (fieldValue != null) {
					return fieldValue;
				}
			}
		}

		return null;
	}

}