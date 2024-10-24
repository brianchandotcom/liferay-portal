/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.IdEntityField;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Magdalena Jedraszak
 */
public class EntityFieldsMapFactory {

	public static Map<String, EntityField> create(EntityField... entityFields) {
		Map<String, EntityField> entityFieldsMap = new HashMap<>();

		for (EntityField entityField : entityFields) {
			entityFieldsMap.put(entityField.getName(), entityField);
		}

		entityFieldsMap.put(
			"id",
			new IdEntityField(
				"id", locale -> Field.ENTRY_CLASS_PK, String::valueOf));

		return entityFieldsMap;
	}

}