/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author João Victor Alves
 */
public class VariablesUtil {

	public static String applyInputVariables(
		ExecutionContext executionContext, String kaleoNodeSettingName,
		Map<String, String> kaleoNodeSettingValues) {

		return applyInputVariables(
			executionContext, kaleoNodeSettingName, kaleoNodeSettingValues,
			false);
	}

	public static String applyInputVariables(
		ExecutionContext executionContext, String kaleoNodeSettingName,
		Map<String, String> kaleoNodeSettingValues, boolean escapeJSON) {

		Map<String, String> inputVariables = _getInputVariables(
			kaleoNodeSettingValues, executionContext.getWorkflowContext());

		String value = kaleoNodeSettingValues.get(kaleoNodeSettingName);

		for (Map.Entry<String, String> entry : inputVariables.entrySet()) {
			String variableValue = entry.getValue();

			if (escapeJSON) {
				variableValue = StringUtil.replace(
					variableValue,
					new String[] {
						StringPool.BACK_SLASH, StringPool.QUOTE,
						StringPool.NEW_LINE, StringPool.RETURN, StringPool.TAB
					},
					new String[] {
						StringPool.DOUBLE_BACK_SLASH, "\\\"", "\\n", "\\r",
						"\\t"
					});
			}

			value = StringUtil.replace(
				value, "{{" + entry.getKey() + "}}", variableValue);
		}

		return value;
	}

	public static void applyOutputVariables(
			JSONArray outputVariablesJSONArray, String responseBody,
			Map<String, Serializable> workflowContext)
		throws Exception {

		if ((outputVariablesJSONArray == null) ||
			(outputVariablesJSONArray.length() == 0)) {

			return;
		}

		JSONObject responseJSONObject = null;

		for (int i = 0; i < outputVariablesJSONArray.length(); i++) {
			JSONObject outputVariableJSONObject =
				outputVariablesJSONArray.getJSONObject(i);

			String name = outputVariableJSONObject.getString("name");

			String path = outputVariableJSONObject.getString("path");

			if (Validator.isNull(path)) {
				workflowContext.put(name, responseBody);

				continue;
			}

			if (responseJSONObject == null) {
				responseJSONObject = JSONFactoryUtil.createJSONObject(
					responseBody);
			}

			if (path.startsWith("output.")) {
				path = path.substring("output.".length());
			}

			Object value = responseJSONObject.get(path);

			if (value instanceof JSONArray || value instanceof JSONObject) {
				workflowContext.put(name, value.toString());
			}
			else if (value instanceof Serializable) {
				workflowContext.put(name, (Serializable)value);
			}
			else if (value != null) {
				workflowContext.put(name, String.valueOf(value));
			}
		}
	}

	public static JSONArray getVariablesJSONArray(
		String kaleoNodeSettingName,
		Map<String, String> kaleoNodeSettingValues) {

		String variables = kaleoNodeSettingValues.get(kaleoNodeSettingName);

		if (variables == null) {
			return null;
		}

		try {
			return JSONFactoryUtil.createJSONArray(variables);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	private static Map<String, String> _getInputVariables(
		Map<String, String> kaleoNodeSettingValues,
		Map<String, Serializable> workflowContext) {

		JSONArray jsonArray = getVariablesJSONArray(
			"inputVariables", kaleoNodeSettingValues);

		if (jsonArray == null) {
			return Map.of();
		}

		Map<String, String> inputVariables = new HashMap<>();

		Iterator<JSONObject> iterator = jsonArray.iterator();

		iterator.forEachRemaining(
			jsonObject -> {
				String name = jsonObject.getString("name");

				inputVariables.put(
					name, GetterUtil.getString(workflowContext.get(name)));
			});

		return inputVariables;
	}

	private static final Log _log = LogFactoryUtil.getLog(VariablesUtil.class);

}