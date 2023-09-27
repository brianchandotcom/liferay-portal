/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

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

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String parameterKey = iterator.next();

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