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

package com.liferay.frontend.taglib.clay.internal.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.TABLE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class TableClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof BaseTableClayDataSetDisplayView) {
			return _serialize(
				(BaseTableClayDataSetDisplayView)clayDataSetDisplayView,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTableClayDataSetDisplayView baseTableClayDataSetDisplayView,
		Locale locale) {

		JSONArray fieldsJSONArray = _jsonFactory.createJSONArray();

		ClayTableSchema clayTableSchema =
			baseTableClayDataSetDisplayView.getClayTableSchema(locale);

		Map<String, ClayTableSchemaField> fieldsMap =
			clayTableSchema.getClayTableSchemaFieldsMap();

		ResourceBundle resourceBundle =
			baseTableClayDataSetDisplayView.getResourceBundle(locale);

		for (ClayTableSchemaField clayTableSchemaField : fieldsMap.values()) {
			String label = LanguageUtil.get(
				resourceBundle, clayTableSchemaField.getLabel());

			if (Validator.isNull(label)) {
				label = StringPool.BLANK;
			}

			fieldsJSONArray.put(
				JSONUtil.put(
					"actionId", clayTableSchemaField.getActionId()
				).put(
					"contentRenderer", clayTableSchemaField.getContentRenderer()
				).put(
					"contentRendererModuleURL",
					clayTableSchemaField.getContentRendererModuleURL()
				).put(
					"expand", clayTableSchemaField.isExpand()
				).put(
					"fieldName",
					() -> {
						String fieldName = clayTableSchemaField.getFieldName();

						if (fieldName.contains(StringPool.PERIOD)) {
							return StringUtil.split(
								fieldName, StringPool.PERIOD);
						}

						return fieldName;
					}
				).put(
					"label", label
				).put(
					"sortable", clayTableSchemaField.isSortable()
				).put(
					"sortingOrder",
					() -> {
						ClayTableSchemaField.SortingOrder sortingOrder =
							clayTableSchemaField.getSortingOrder();

						if (sortingOrder != null) {
							return StringUtil.toLowerCase(
								sortingOrder.toString());
						}

						return null;
					}
				));
		}

		return HashMapBuilder.<String, Object>put(
			"schema", JSONUtil.put("fields", fieldsJSONArray)
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}