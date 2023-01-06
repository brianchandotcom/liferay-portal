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

package com.liferay.journal.web.internal.portlet.action.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class DataDefinitionFieldNameUtil {

	public static void normalizeFieldName(
		DataDefinition dataDefinition, DataLayout dataLayout) {

		Map<String, String> fieldReferences = new HashMap<>();

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			Map<String, Object> customProperties =
				dataDefinitionField.getCustomProperties();

			String fieldReference = GetterUtil.getString(
				customProperties.get("fieldReference"));

			fieldReferences.put(dataDefinitionField.getName(), fieldReference);

			dataDefinitionField.setName(fieldReference);

			Map<String, Object[]> optionsMap =
				(Map<String, Object[]>)customProperties.get("options");

			if (MapUtil.isEmpty(optionsMap)) {
				continue;
			}

			for (Object[] options : optionsMap.values()) {
				for (Object option : options) {
					Map<String, String> optionMap = (Map<String, String>)option;

					optionMap.put("value", optionMap.get("reference"));
				}
			}
		}

		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			for (DataLayoutRow dataLayoutRows :
					dataLayoutPage.getDataLayoutRows()) {

				for (DataLayoutColumn dataLayoutColumn :
						dataLayoutRows.getDataLayoutColumns()) {

					String[] fieldNames = dataLayoutColumn.getFieldNames();

					for (int i = 0; i < fieldNames.length; i++) {
						fieldNames[i] = fieldReferences.get(fieldNames[i]);
					}
				}
			}
		}
	}

}