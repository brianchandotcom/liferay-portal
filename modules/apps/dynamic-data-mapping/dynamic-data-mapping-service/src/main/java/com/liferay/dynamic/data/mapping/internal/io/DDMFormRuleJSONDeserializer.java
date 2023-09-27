/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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