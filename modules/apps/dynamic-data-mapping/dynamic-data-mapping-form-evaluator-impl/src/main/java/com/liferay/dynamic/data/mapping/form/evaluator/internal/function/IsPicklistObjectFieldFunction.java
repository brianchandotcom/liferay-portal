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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mateus Santana
 */
public class IsPicklistObjectFieldFunction
	implements DDMExpressionFunction.Function1<String, List<Object>>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "isPicklistObjectField";

	public IsPicklistObjectFieldFunction(
		JSONFactory jsonFactory,
		ListTypeEntryLocalService listTypeEntryLocalService) {

		this.jsonFactory = jsonFactory;
		this.listTypeEntryLocalService = listTypeEntryLocalService;
	}

	@Override
	public List<Object> apply(String fieldValue) {
		String fieldValueName = fieldValue.replaceAll("\\[|\\]|\"", "");

		JSONObject jsonObject = _getJSONObject(
			fieldValueName,
			_ddmExpressionParameterAccessor.getObjectFieldsJSONArray());

		if (jsonObject != null) {
			List<ListTypeEntry> listTypeEntries =
				listTypeEntryLocalService.getListTypeEntries(
					(Integer)jsonObject.get("listTypeDefinitionId"));

			List<Object> objectList = new ArrayList<>();

			for (ListTypeEntry listTypeEntry : listTypeEntries) {
				objectList.add(
					HashMapBuilder.put(
						"label",
						listTypeEntry.getName(
							listTypeEntry.getDefaultLanguageId())
					).put(
						"reference", listTypeEntry.getKey()
					).put(
						"value", listTypeEntry.getKey()
					).build());
			}

			return objectList;
		}

		return null;
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

	protected JSONFactory jsonFactory;
	protected ListTypeEntryLocalService listTypeEntryLocalService;

	private JSONObject _getJSONObject(
		String fieldValueName, JSONArray objectFieldsJSONArray) {

		for (int i = 0; i < objectFieldsJSONArray.length(); i++) {
			JSONObject jsonObject = objectFieldsJSONArray.getJSONObject(i);

			String jsonObjectName = jsonObject.getString("name");

			if (Objects.equals(jsonObjectName, fieldValueName)) {
				return jsonObject;
			}
		}

		return null;
	}

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}