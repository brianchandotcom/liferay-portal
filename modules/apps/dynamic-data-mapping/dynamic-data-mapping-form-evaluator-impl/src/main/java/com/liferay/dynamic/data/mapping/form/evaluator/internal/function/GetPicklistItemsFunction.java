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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mateus Santana
 */
public class GetPicklistItemsFunction
	implements DDMExpressionFunction.Function1<String, Map<String, Object>>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "getPicklistItems";

	public GetPicklistItemsFunction(
		JSONFactory jsonFactory,
		ListTypeEntryLocalService listTypeEntryLocalService) {

		_jsonFactory = jsonFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
	}

	@Override
	public Map<String, Object> apply(String field) {
		JSONObject jsonObject = _getJSONObject(
			field.replaceAll("\\[|\\]|\"", StringPool.BLANK),
			_ddmExpressionParameterAccessor.getObjectFieldsJSONArray());

		if (jsonObject == null) {
			return null;
		}

		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(
				(Integer)jsonObject.get("listTypeDefinitionId"));

		if (ListUtil.isEmpty(listTypeEntries)) {
			return null;
		}

		Map<String, Object> localizedValues = new HashMap<>();

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			Map<Locale, String> nameMap = listTypeEntry.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				String languageId = LocaleUtil.toLanguageId(entry.getKey());

				List<Object> options = (List<Object>)localizedValues.get(
					languageId);

				if (options == null) {
					options = new ArrayList<>();
				}

				options.add(
					HashMapBuilder.put(
						"label", entry.getValue()
					).put(
						"reference", listTypeEntry.getKey()
					).put(
						"value", listTypeEntry.getKey()
					).build());

				localizedValues.put(languageId, options);
			}
		}

		return localizedValues;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private JSONObject _getJSONObject(String field, JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (Objects.equals(jsonObject.getString("name"), field)) {
				return jsonObject;
			}
		}

		return null;
	}

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;
	private final JSONFactory _jsonFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;

}