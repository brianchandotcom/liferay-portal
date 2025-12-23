/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author João Victor Alves
 */
public class InputVariablesUtil {

	public static String applyInputVariables(
		ExecutionContext executionContext, String kaleoNodeSettingName,
		Map<String, String> kaleoNodeSettingValues) {

		Map<String, String> inputVariables = _getInputVariables(
			kaleoNodeSettingValues, executionContext.getWorkflowContext());

		String value = kaleoNodeSettingValues.get(kaleoNodeSettingName);

		for (Map.Entry<String, String> entry : inputVariables.entrySet()) {
			value = StringUtil.replace(
				value, "{{" + entry.getKey() + "}}", entry.getValue());
		}

		return value;
	}

	private static Map<String, String> _getInputVariables(
		Map<String, String> kaleoNodeSettingValues,
		Map<String, Serializable> workflowContext) {

		String inputVariablesString = kaleoNodeSettingValues.get(
			"inputVariables");

		if (inputVariablesString == null) {
			return Map.of();
		}

		Map<String, String> inputVariables = new HashMap<>();

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				inputVariablesString);

			Iterator<JSONObject> iterator = jsonArray.iterator();

			iterator.forEachRemaining(
				jsonObject -> {
					String name = jsonObject.getString("name");

					inputVariables.put(
						name, GetterUtil.getString(workflowContext.get(name)));
				});
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}

		return inputVariables;
	}

}