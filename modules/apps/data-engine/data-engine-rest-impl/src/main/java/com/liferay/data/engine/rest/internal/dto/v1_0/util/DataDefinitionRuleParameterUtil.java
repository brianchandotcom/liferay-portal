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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionRuleParameterUtil {

	public static Map<String, Object> toDataDefinitionRuleParameters(
		JSONObject jsonObject) {

		Map<String, Object> dataDefinitionRuleParameters = new HashMap<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String parameterKey = keys.next();

			dataDefinitionRuleParameters.put(
				parameterKey, jsonObject.get(parameterKey));
		}

		return dataDefinitionRuleParameters;
	}

	public static JSONObject toJSONObject(
		Map<String, Object> dataDefinitionRuleParameters) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(dataDefinitionRuleParameters)) {
			return jsonObject;
		}

		for (Map.Entry<String, Object> entry :
				dataDefinitionRuleParameters.entrySet()) {

			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

}