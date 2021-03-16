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

package com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.tuning.blueprints.engine.exception.ParameterEvaluationException;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintValueUtil;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.BooleanParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ConditionEvaluationVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DateParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DoubleParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.FloatParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;

/**
 * @author Petteri Karttunen
 */
public class InVisitor implements ConditionEvaluationVisitor {

	public InVisitor(JSONObject conditionJSONObject, boolean not) {
		_conditionJSONObject = conditionJSONObject;
		_not = not;
	}

	@Override
	public boolean visit(BooleanParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(DateParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(DoubleParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		boolean match = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			Double value = jsonArray.getDouble(i);

			if (parameter.equalsTo(value)) {
				match = true;

				break;
			}
		}

		if (_not) {
			return !match;
		}

		return match;
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		boolean match = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			Float value = GetterUtil.getFloat(jsonArray.get(i));

			if (parameter.equalsTo(value)) {
				match = true;

				break;
			}
		}

		if (_not) {
			return !match;
		}

		return match;
	}

	@Override
	public boolean visit(IntegerArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		boolean match = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			Integer value = jsonArray.getInt(i);

			if (parameter.equalsTo(value)) {
				match = true;

				break;
			}
		}

		if (_not) {
			return !match;
		}

		return match;
	}

	@Override
	public boolean visit(LongArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		boolean match = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			Long value = jsonArray.getLong(i);

			if (parameter.equalsTo(value)) {
				match = true;

				break;
			}
		}

		if (_not) {
			return !match;
		}

		return match;
	}

	@Override
	public boolean visit(StringArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		String parameterValue = parameter.getValue();

		boolean match = false;

		for (int i = 0; i < jsonArray.length(); i++) {
			String value = jsonArray.getString(i);

			if (parameterValue.equals(value)) {
				match = true;

				break;
			}
		}

		if (_not) {
			return !match;
		}

		return match;
	}

	private final JSONObject _conditionJSONObject;
	private final boolean _not;

}