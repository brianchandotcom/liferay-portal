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

package com.liferay.frontend.data.set.internal.view.table;

import com.liferay.frontend.data.set.constants.FrontendDataSetConstants;
import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributor;
import com.liferay.frontend.data.set.view.table.BaseTableFrontendDataSetView;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchema;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaField;
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
	property = "frontend.data.set.view.name=" + FrontendDataSetConstants.TABLE,
	service = FrontendDataSetViewContextContributor.class
)
public class TableFrontendDataSetViewContextContributor
	implements FrontendDataSetViewContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetViewContext(
		FrontendDataSetView frontendDataSetView, Locale locale) {

		if (frontendDataSetView instanceof BaseTableFrontendDataSetView) {
			return _serialize(
				(BaseTableFrontendDataSetView)frontendDataSetView, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTableFrontendDataSetView baseTableFrontendDataSetView,
		Locale locale) {

		JSONArray fieldsJSONArray = _jsonFactory.createJSONArray();

		FrontendDataSetTableSchema frontendDataSetTableSchema =
			baseTableFrontendDataSetView.getFrontendDataSetTableSchema(locale);

		Map<String, FrontendDataSetTableSchemaField> fieldsMap =
			frontendDataSetTableSchema.getFrontendDataSetTableSchemaFieldsMap();

		ResourceBundle resourceBundle =
			baseTableFrontendDataSetView.getResourceBundle(locale);

		for (FrontendDataSetTableSchemaField frontendDataSetTableSchemaField :
				fieldsMap.values()) {

			String label = LanguageUtil.get(
				resourceBundle, frontendDataSetTableSchemaField.getLabel());

			if (Validator.isNull(label)) {
				label = StringPool.BLANK;
			}

			fieldsJSONArray.put(
				JSONUtil.put(
					"actionId", frontendDataSetTableSchemaField.getActionId()
				).put(
					"contentRenderer",
					frontendDataSetTableSchemaField.getContentRenderer()
				).put(
					"contentRendererModuleURL",
					frontendDataSetTableSchemaField.
						getContentRendererModuleURL()
				).put(
					"expand", frontendDataSetTableSchemaField.isExpand()
				).put(
					"fieldName",
					() -> {
						String fieldName =
							frontendDataSetTableSchemaField.getFieldName();

						if (fieldName.contains(StringPool.PERIOD)) {
							return StringUtil.split(
								fieldName, StringPool.PERIOD);
						}

						return fieldName;
					}
				).put(
					"label", label
				).put(
					"sortable", frontendDataSetTableSchemaField.isSortable()
				).put(
					"sortingOrder",
					() -> {
						FrontendDataSetTableSchemaField.SortingOrder
							sortingOrder =
								frontendDataSetTableSchemaField.
									getSortingOrder();

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