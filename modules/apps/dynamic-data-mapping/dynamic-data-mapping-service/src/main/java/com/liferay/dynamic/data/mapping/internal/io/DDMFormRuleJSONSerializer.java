/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormRuleJSONSerializer {

	public static JSONArray serialize(List<DDMFormRule> ddmFormRules) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			jsonArray.put(_toJSONObject(ddmFormRule));
		}

		return jsonArray;
	}

	private static JSONArray _ruleActionsToJSONArray(List<String> ruleActions) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String ruleAction : ruleActions) {
			jsonArray.put(ruleAction);
		}

		return jsonArray;
	}

	private static JSONObject _toJSONObject(DDMFormRule ddmFormRule) {
		return JSONUtil.put(
			"actions", _ruleActionsToJSONArray(ddmFormRule.getActions())
		).put(
			"condition", ddmFormRule.getCondition()
		).put(
			"enabled", ddmFormRule.isEnabled()
		).put(
			"name", LocalizedValueUtil.toJSONObject(ddmFormRule.getName())
		);
	}

}