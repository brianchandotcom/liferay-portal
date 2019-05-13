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

package com.liferay.data.engine.spi.definition.util;

import com.liferay.data.engine.spi.definition.SPIDataDefinition;
import com.liferay.data.engine.spi.definition.SPIDataDefinitionField;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.field.type.FieldTypeTracker;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionUtil {

	public static SPIDataDefinition toDataDefinition(
			FieldTypeTracker fieldTypeTracker, String json)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		return new SPIDataDefinition() {
			{
				setDataDefinitionFields(
					JSONUtil.toArray(
						jsonObject.getJSONArray("fields"),
						fieldJSONObject -> _toDataDefinitionField(
							fieldTypeTracker, fieldJSONObject),
						SPIDataDefinitionField.class));
			}
		};
	}

	private static SPIDataDefinitionField _toDataDefinitionField(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		FieldType fieldType = fieldTypeTracker.getFieldType(
			jsonObject.getString("type"));

		if (fieldType == null) {
			return null;
		}

		return fieldType.deserialize(jsonObject);
	}

}