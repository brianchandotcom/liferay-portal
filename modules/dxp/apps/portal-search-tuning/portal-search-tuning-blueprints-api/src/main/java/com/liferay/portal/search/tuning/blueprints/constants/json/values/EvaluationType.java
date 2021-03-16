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

package com.liferay.portal.search.tuning.blueprints.constants.json.values;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public enum EvaluationType {

	ANY_WORD_IN("any_word_in"), CONTAINS("contains"), EQ("eq"),
	EXISTS("exists"), GT("gt"), GTE("gte"), IN("in"), IN_RANGE("in_range"),
	LT("lt"), LTE("lte"), NE("ne"), NO_WORD_IN("no_word_in"),
	NOT_CONTAINS("not_contains"), NOT_EXISTS("not_exists"), NOT_IN("not_in"),
	NOT_IN_RANGE("not_in_range");

	public static final EvaluationType findByjsonValue(String jsonValue) {
		Stream<EvaluationType> evaluationTypeStream = Arrays.stream(
			EvaluationType.values());

		return evaluationTypeStream.filter(
			value -> value._jsonValue.equals(jsonValue)
		).findFirst(
		).orElse(
			null
		);
	}

	public String getjsonValue() {
		return _jsonValue;
	}

	private EvaluationType(String jsonValue) {
		_jsonValue = jsonValue;
	}

	private final String _jsonValue;

}