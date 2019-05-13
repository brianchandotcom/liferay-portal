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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.spi.field.type.BaseFieldType;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.definition.SPIDataDefinitionField;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.icon=icon-font",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class FieldSetFieldType extends BaseFieldType {

	@Override
	public String getName() {
		return "fieldset";
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		Map<String, List<Object>> map = CustomPropertiesUtil.getMap(
			spiDataDefinitionField.getCustomProperties(), "nestedFields");

		if (!map.isEmpty()) {
			List<Object> nestedFields = _getNestedFields(
				map,
				_getNestedFieldNames(
					CustomPropertiesUtil.getString(
						spiDataDefinitionField.getCustomProperties(),
						"nestedFieldNames"),
					map.keySet()));

			context.put(
				"columnSize",
				_getColumnSize(
					nestedFields.size(),
					CustomPropertiesUtil.getString(
						spiDataDefinitionField.getCustomProperties(),
						"orientation", "horizontal")));
			context.put("nestedFields", nestedFields);
		}

		if (context.containsKey("label")) {
			context.put("showLabel", true);
		}
	}

	private static List<Object> _getNestedFields(
		Map<String, List<Object>> nestedFieldsMap,
		Set<String> nestedFieldNames) {

		Set<Map.Entry<String, List<Object>>> entrySet =
			nestedFieldsMap.entrySet();

		Stream<Map.Entry<String, List<Object>>> stream = entrySet.stream();

		return stream.filter(
			entry -> nestedFieldNames.contains(entry.getKey())
		).map(
			entry -> entry.getValue()
		).collect(
			Collectors.toList()
		);
	}

	private int _getColumnSize(int nestedFieldsSize, String orientation) {
		if (Objects.equals(orientation, "vertical")) {
			return _COLUMN_SIZE_FULL;
		}

		if (nestedFieldsSize == 0) {
			return 0;
		}

		return _COLUMN_SIZE_FULL / nestedFieldsSize;
	}

	private Set<String> _getNestedFieldNames(
		String nestedFieldNames, Set<String> defaultNestedFieldNames) {

		if (Validator.isNotNull(nestedFieldNames)) {
			return Stream.of(
				StringUtil.split(nestedFieldNames)
			).collect(
				Collectors.toSet()
			);
		}

		return defaultNestedFieldNames;
	}

	private static final int _COLUMN_SIZE_FULL = 12;

}