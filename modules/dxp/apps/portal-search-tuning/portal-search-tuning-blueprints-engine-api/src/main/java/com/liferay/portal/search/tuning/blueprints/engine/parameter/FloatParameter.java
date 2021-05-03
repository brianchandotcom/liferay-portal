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

package com.liferay.portal.search.tuning.blueprints.engine.parameter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.engine.exception.ParameterEvaluationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class FloatParameter implements Parameter {

	public FloatParameter(String name, String templateVariable, Float value) {
		_name = name;
		_templateVariable = templateVariable;
		_value = value;
	}

	@Override
	public boolean accept(ConditionEvaluationVisitor visitor)
		throws ParameterEvaluationException {

		return visitor.visit(this);
	}

	@Override
	public String accept(ToStringVisitor visitor, Map<String, String> options)
		throws Exception {

		return visitor.visit(this, options);
	}

	public boolean equalsTo(Float value) {
		if (_value.floatValue() == value.floatValue()) {
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<EvaluationType> getSupportedEvaluationTypes() {
		List<EvaluationType> evaluationTypes = new ArrayList<>();

		evaluationTypes.add(EvaluationType.EQ);
		evaluationTypes.add(EvaluationType.EXISTS);
		evaluationTypes.add(EvaluationType.NE);
		evaluationTypes.add(EvaluationType.GT);
		evaluationTypes.add(EvaluationType.GTE);
		evaluationTypes.add(EvaluationType.LT);
		evaluationTypes.add(EvaluationType.LTE);
		evaluationTypes.add(EvaluationType.IN);
		evaluationTypes.add(EvaluationType.NOT_IN);
		evaluationTypes.add(EvaluationType.IN_RANGE);
		evaluationTypes.add(EvaluationType.NOT_IN_RANGE);

		return evaluationTypes;
	}

	@Override
	public String getTemplateVariable() {
		return _templateVariable;
	}

	@Override
	public Float getValue() {
		return _value;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("FloatParameter [_name=");
		sb.append(_name);
		sb.append(", _templateVariable=");
		sb.append(_templateVariable);
		sb.append(", _value=");
		sb.append(_value);
		sb.append("]");

		return sb.toString();
	}

	private final String _name;
	private final String _templateVariable;
	private final Float _value;

}