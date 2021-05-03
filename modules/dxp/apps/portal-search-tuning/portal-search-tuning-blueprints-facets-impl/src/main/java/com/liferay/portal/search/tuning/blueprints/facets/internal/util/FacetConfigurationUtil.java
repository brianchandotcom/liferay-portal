/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.facets.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;

import java.util.Collections;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class FacetConfigurationUtil {

	public static String getAggregationName(
		JSONObject configurationJSONObject) {

		String name = configurationJSONObject.getString(
			FacetConfigurationKeys.AGGREGATION_NAME.getJsonKey());

		if (!Validator.isBlank(name)) {
			return name;
		}

		return getFieldName(configurationJSONObject);
	}

	public static List<String> getExcludeValues(
		JSONObject configurationJSONObject) {

		if (!configurationJSONObject.has(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey())) {

			return Collections.emptyList();
		}

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		JSONArray excludeValuesJSONArray =
			handlerParametersJSONObject.getJSONArray(
				FacetConfigurationKeys.EXCLUDE_VALUES.getJsonKey());

		if ((excludeValuesJSONArray == null) ||
			(excludeValuesJSONArray.length() == 0)) {

			Collections.emptyList();
		}

		return JSONUtil.toStringList(excludeValuesJSONArray);
	}

	public static String getFacetName(JSONObject configurationJSONObject) {
		String name = configurationJSONObject.getString(
			FacetConfigurationKeys.NAME.getJsonKey());

		if (!Validator.isBlank(name)) {
			return name;
		}

		return getFieldName(configurationJSONObject);
	}

	public static String getFieldName(JSONObject configurationJSONObject) {
		return configurationJSONObject.getString(
			FacetConfigurationKeys.FIELD.getJsonKey());
	}

	public static List<String> getIncludeValues(
		JSONObject configurationJSONObject) {

		if (!configurationJSONObject.has(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey())) {

			return Collections.emptyList();
		}

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		JSONArray excludeValuesJSONArray =
			handlerParametersJSONObject.getJSONArray(
				FacetConfigurationKeys.INCLUDE_VALUES.getJsonKey());

		if ((excludeValuesJSONArray == null) ||
			(excludeValuesJSONArray.length() == 0)) {

			Collections.emptyList();
		}

		return JSONUtil.toStringList(excludeValuesJSONArray);
	}

	public static String getLabel(JSONObject configurationJSONObject) {
		String name = configurationJSONObject.getString(
			FacetConfigurationKeys.LABEL.getJsonKey());

		if (!Validator.isBlank(name)) {
			return name;
		}

		return getFieldName(configurationJSONObject);
	}

	public static String getParameterName(JSONObject configurationJSONObject) {
		String name = configurationJSONObject.getString(
			FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());

		if (!Validator.isBlank(name)) {
			return name;
		}

		return getFieldName(configurationJSONObject);
	}

	public static boolean includeValue(
		String value, List<String> includeValues, List<String> excludeValues) {

		if (!includeValues.isEmpty()) {
			if (!includeValues.contains(value)) {
				return false;
			}
		}
		else if (!excludeValues.isEmpty() && excludeValues.contains(value)) {
			return false;
		}

		return true;
	}

}