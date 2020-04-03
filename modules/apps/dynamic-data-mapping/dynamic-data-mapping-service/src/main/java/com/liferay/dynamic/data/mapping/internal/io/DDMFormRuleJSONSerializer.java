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