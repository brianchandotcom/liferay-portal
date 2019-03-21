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

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRuleParameter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionRuleParameterUtil {

	public static DataDefinitionRuleParameter[] toDataDefinitionRuleParameters(
		JSONObject jsonObject) {

		List<DataDefinitionRuleParameter> dataDefinitionRuleParameters =
			new ArrayList<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String parameterKey = keys.next();

			DataDefinitionRuleParameter dataDefinitionRuleParameter =
				new DataDefinitionRuleParameter() {
					{
						key = parameterKey;
						value = jsonObject.get(parameterKey);
					}
				};

			dataDefinitionRuleParameters.add(dataDefinitionRuleParameter);
		}

		return dataDefinitionRuleParameters.toArray(
			new DataDefinitionRuleParameter
				[dataDefinitionRuleParameters.size()]);
	}

	public static Map<String, Object> toDataDefinitionRuleParametersMap(
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters) {

		Map<String, Object> dataDefinitionRuleParameterMap = new HashMap<>();

		for (DataDefinitionRuleParameter dataDefinitionRuleParameter :
				dataDefinitionRuleParameters) {

			dataDefinitionRuleParameterMap.put(
				dataDefinitionRuleParameter.getKey(),
				dataDefinitionRuleParameter.getValue());
		}

		return dataDefinitionRuleParameterMap;
	}

	public static JSONObject toJSONObject(
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (ArrayUtil.isEmpty(dataDefinitionRuleParameters)) {
			return jsonObject;
		}

		for (DataDefinitionRuleParameter dataDefinitionRuleParameter :
				dataDefinitionRuleParameters) {

			jsonObject.put(
				dataDefinitionRuleParameter.getKey(),
				dataDefinitionRuleParameter.getValue());
		}

		return jsonObject;
	}

}