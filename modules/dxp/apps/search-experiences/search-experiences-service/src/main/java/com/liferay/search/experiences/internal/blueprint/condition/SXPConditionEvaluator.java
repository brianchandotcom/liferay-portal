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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.search.experiences.blueprint.exception.InvalidParameterException;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.rest.dto.v1_0.Condition;
import com.liferay.search.experiences.rest.dto.v1_0.Contains;
import com.liferay.search.experiences.rest.dto.v1_0.Equals;
import com.liferay.search.experiences.rest.dto.v1_0.Exists;
import com.liferay.search.experiences.rest.dto.v1_0.In;
import com.liferay.search.experiences.rest.dto.v1_0.Range;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public class SXPConditionEvaluator {

	public SXPConditionEvaluator(SXPParameterData sxpParameterData) {
		_sxpParameterData = sxpParameterData;
	}

	public boolean evaluate(Condition condition) {
		if (condition == null) {
			return true;
		}

		if (!_evaluateAllConditions(condition.getAllConditions()) ||
			!_evaluateAnyConditions(condition.getAnyConditions()) ||
			!_evaluateContains(condition.getContains()) ||
			!_evaluateEquals(condition.getEquals()) ||
			!_evaluateExists(condition.getExists()) ||
			!_evaluateIn(condition.getIn()) ||
			!_evaluateNot(condition.getNot()) ||
			!_evaluateRange(condition.getRange())) {

			return false;
		}

		return true;
	}

	private boolean _evaluateAllConditions(Condition[] conditions) {
		if (conditions == null) {
			return true;
		}

		for (Condition condition : conditions) {
			if (!evaluate(condition)) {
				return false;
			}
		}

		return true;
	}

	private boolean _evaluateAnyConditions(Condition[] conditions) {
		if (conditions == null) {
			return true;
		}

		for (Condition condition : conditions) {
			if (evaluate(condition)) {
				return true;
			}
		}

		return false;
	}

	private boolean _evaluateContains(Contains contains) {
		if (contains == null) {
			return true;
		}

		SXPParameter sxpParameter = _getSXPParameter(
			contains.getParameterName());

		return sxpParameter.evaluateContains(_getValue(contains.getValue()));
	}

	private boolean _evaluateEquals(Equals equals) {
		if (equals == null) {
			return true;
		}

		SXPParameter sxpParameter = _getSXPParameter(equals.getParameterName());

		if (equals.getFormat() != null) {
			return sxpParameter.evaluateEquals(
				equals.getFormat(), _getValue(equals.getValue()));
		}

		return sxpParameter.evaluateEquals(_getValue(equals.getValue()));
	}

	private boolean _evaluateExists(Exists exists) {
		if (exists == null) {
			return true;
		}

		if (Objects.isNull(
				_sxpParameterData.getSXPParameterByName(
					exists.getParameterName()))) {

			return false;
		}

		return true;
	}

	private boolean _evaluateIn(In in) {
		if (in == null) {
			return true;
		}

		SXPParameter sxpParameter = _getSXPParameter(in.getParameterName());

		return sxpParameter.evaluateIn(_getValue(in.getValue()));
	}

	private boolean _evaluateNot(Condition condition) {
		if (condition == null) {
			return true;
		}

		if (evaluate(condition)) {
			return false;
		}

		return true;
	}

	private boolean _evaluateRange(Range range) {
		if (range == null) {
			return true;
		}

		SXPParameter sxpParameter = _getSXPParameter(range.getParameterName());

		if (range.getFormat() != null) {
			return sxpParameter.evaluateRange(
				range.getFormat(), range.getGt(), range.getGte(), range.getLt(),
				range.getLte());
		}

		return sxpParameter.evaluateRange(
			range.getGt(), range.getGte(), range.getLt(), range.getLte());
	}

	private SXPParameter _getSXPParameter(String name) {
		SXPParameter sxpParameter = _sxpParameterData.getSXPParameterByName(
			name);

		if (sxpParameter != null) {
			return sxpParameter;
		}

		throw InvalidParameterException.with(name);
	}

	private Object _getValue(Object value) {
		if (value instanceof JSONArray) {
			return JSONUtil.toObjectArray((JSONArray)value);
		}

		return value;
	}

	private final SXPParameterData _sxpParameterData;

}