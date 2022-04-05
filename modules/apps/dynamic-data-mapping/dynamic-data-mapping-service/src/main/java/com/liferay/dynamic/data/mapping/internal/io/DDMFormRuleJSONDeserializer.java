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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormRuleJSONDeserializer {

	public static List<DDMFormRule> deserialize(JSONArray jsonArray) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormRules.add(_getDDMFormRule(jsonArray.getJSONObject(i)));
		}

		return ddmFormRules;
	}

	private static DDMFormRule _getDDMFormRule(JSONObject jsonObject) {
		String condition = jsonObject.getString("condition");

		List<String> actions = _getDDMFormRuleActions(
			jsonObject.getJSONArray("actions"));

		DDMFormRule ddmFormRule = new DDMFormRule(actions, condition);

		ddmFormRule.setEnabled(jsonObject.getBoolean("enabled", true));
		ddmFormRule.setName(
			LocalizedValueUtil.toLocalizedValue(
				jsonObject.getJSONObject("name")));

		return ddmFormRule;
	}

	private static List<String> _getDDMFormRuleActions(JSONArray jsonArray) {
		List<String> actions = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			actions.add(jsonArray.getString(i));
		}

		return actions;
	}

}