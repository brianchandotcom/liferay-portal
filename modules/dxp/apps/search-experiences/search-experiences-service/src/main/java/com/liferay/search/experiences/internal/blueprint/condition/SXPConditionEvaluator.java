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

package com.liferay.search.experiences.internal.blueprint.condition;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;

/**
 * @author Petteri Karttunen
 */
public class SXPConditionEvaluator {

	public static boolean evaluate(
		JSONObject jsonObject, SXPParameterData sxpParameterData) {

		if (jsonObject == null) {
			return true;
		}

		return _evaluate(StringPool.BLANK, jsonObject, sxpParameterData);
	}

	private static boolean _evaluate(
		JSONObject jsonObject, String key, SXPParameterData sxpParameterData) {

		SXPParameter sxpParameter =
			sxpParameterData.getSXPParameterByTemplateVariable(
				jsonObject.getString("parameter_name"));

		if (key.equals("exists")) {
			if (sxpParameter != null) {
				return true;
			}

			return false;
		}
		else if (key.equals("not_exists")) {
			if (sxpParameter == null) {
				return true;
			}

			return false;
		}

		if (sxpParameter == null) {
			return false;
		}

		JSONObject valueJSONObject = jsonObject.getJSONObject("value");

		if (valueJSONObject == null) {
			return false;
		}

		if (key.equals("contains")) {
			return sxpParameter.evaluateContains(valueJSONObject);
		}
		else if (key.equals("equals")) {
			return sxpParameter.evaluateEquals(valueJSONObject);
		}
		else if (key.equals("in")) {
			return sxpParameter.evaluateIn(valueJSONObject);
		}
		else if (key.equals("in_range")) {
			return sxpParameter.evaluateInRange(valueJSONObject);
		}
		else if (key.equals("greater_than")) {
			return sxpParameter.evaluateGreaterThan(false, valueJSONObject);
		}
		else if (key.equals("greater_than_equals")) {
			return sxpParameter.evaluateGreaterThan(true, valueJSONObject);
		}
		else if (key.equals("less_than")) {
			return !sxpParameter.evaluateGreaterThan(false, valueJSONObject);
		}
		else if (key.equals("less_than_equals")) {
			return !sxpParameter.evaluateGreaterThan(true, valueJSONObject);
		}
		else if (key.equals("not_contains")) {
			return !sxpParameter.evaluateContains(valueJSONObject);
		}
		else if (key.equals("not_equals")) {
			return !sxpParameter.evaluateEquals(valueJSONObject);
		}
		else if (key.equals("not_in")) {
			return !sxpParameter.evaluateIn(valueJSONObject);
		}
		else if (key.equals("not_in_range")) {
			return !sxpParameter.evaluateInRange(valueJSONObject);
		}

		throw new IllegalArgumentException("Unknown condition " + key);
	}

	private static boolean _evaluate(
		String groupCondition, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		boolean valid = false;

		for (String key : jsonObject.keySet()) {
			if (key.equals("any_of") || key.equals("all_of")) {
				valid = _evaluate(
					key, jsonObject.getJSONObject(key), sxpParameterData);
			}
			else {
				valid = _evaluate(
					jsonObject.getJSONObject(key), key, sxpParameterData);
			}

			if (!valid && !groupCondition.equals("any_of")) {
				return false;
			}
			else if (valid && groupCondition.equals("any_of")) {
				return true;
			}
		}

		return valid;
	}

}