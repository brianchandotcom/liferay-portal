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

package com.liferay.portal.search.tuning.blueprints.engine.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.ConditionConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.FilterMode;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.Operator;
import com.liferay.portal.search.tuning.blueprints.engine.exception.ParameterEvaluationException;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class BlueprintValueUtil {

	public static JSONArray getConditionValueJSONArray(
			JSONObject conditionJSONObject)
		throws ParameterEvaluationException {

		Object object = conditionJSONObject.get(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		if (!(object instanceof JSONArray)) {
			throw new ParameterEvaluationException(
				new Message.Builder().className(
					BlueprintValueUtil.class.getName()
				).localizationKey(
					"core.error.expected-array-clause-condition-match-value"
				).msg(
					"Excepted an array clause condition match value"
				).rootObject(
					conditionJSONObject
				).rootProperty(
					ConditionConfigurationKeys.VALUE.getJsonKey()
				).rootValue(
					object.toString()
				).severity(
					Severity.ERROR
				).build());
		}

		return (JSONArray)object;
	}

	public static FilterMode getFilterMode(String filter)
		throws IllegalArgumentException {

		filter = StringUtil.toUpperCase(filter);

		return FilterMode.valueOf(filter);
	}

	public static Operator getOperator(String operator)
		throws IllegalArgumentException {

		operator = StringUtil.toUpperCase(operator);

		return Operator.valueOf(operator);
	}

	public static String mapToString(Map<?, ?> map) {
		Set<?> set = map.keySet();

		Stream<?> stream = set.stream();

		return stream.map(
			key -> key + "=" + map.get(key)
		).collect(
			Collectors.joining(", ", "[", "]")
		);
	}

	public static Optional<Double> stringToDoubleOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			double value = Double.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Float> stringToFloatOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			float value = Float.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Integer> stringToIntegerOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			int value = Integer.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<Long> stringToLongOptional(String s) {
		if (Validator.isNull(s)) {
			return Optional.empty();
		}

		try {
			long value = Long.valueOf(s);

			return Optional.of(value);
		}
		catch (NumberFormatException numberFormatException) {
			_log.error(
				numberFormatException.getMessage(), numberFormatException);
		}

		return Optional.empty();
	}

	public static Optional<String> toStringOptional(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintValueUtil.class);

}