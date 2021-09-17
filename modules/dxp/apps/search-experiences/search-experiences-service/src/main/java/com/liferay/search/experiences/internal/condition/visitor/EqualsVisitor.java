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

package com.liferay.search.experiences.internal.condition.visitor;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.search.experiences.exception.ParameterEvaluationException;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.DateParameter;
import com.liferay.search.experiences.parameter.DoubleParameter;
import com.liferay.search.experiences.parameter.FloatParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.StringParameter;
import com.liferay.search.experiences.parameter.visitor.EvaluationVisitor;

/**
 * @author Petteri Karttunen
 */
public class EqualsVisitor
	extends BaseEvaluationVisitor implements EvaluationVisitor {

	public EqualsVisitor(JSONObject conditionJSONObject) {
		super(conditionJSONObject);
	}

	@Override
	public boolean visit(BooleanParameter parameter)
		throws ParameterEvaluationException {

		Boolean value = conditionJSONObject.getBoolean("value");

		Boolean parameterValue = parameter.getValue();

		if (value.booleanValue() == parameterValue.booleanValue()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean visit(DateParameter parameter)
		throws ParameterEvaluationException {

		String dateString1 = conditionJSONObject.getString("value");

		String dateFormatString = conditionJSONObject.getString("date_format");

		String dateString2 = getDateAsString(
			parameter.getValue(), dateFormatString);

		return dateString1.equals(dateString2);
	}

	@Override
	public boolean visit(DoubleParameter parameter)
		throws ParameterEvaluationException {

		Double value = conditionJSONObject.getDouble("value");

		return parameter.equalsTo(value);
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		return parameter.equalsTo(
			GetterUtil.getFloat(conditionJSONObject.get("value")));
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		return parameter.equalsTo(conditionJSONObject.getInt("value"));
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		return parameter.equalsTo(conditionJSONObject.getLong("value"));
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		String parameterValue = parameter.getValue();

		return parameterValue.equals(conditionJSONObject.getString("value"));
	}

}