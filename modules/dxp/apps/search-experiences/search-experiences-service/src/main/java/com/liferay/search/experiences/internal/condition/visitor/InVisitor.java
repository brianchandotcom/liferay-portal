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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.search.experiences.exception.ParameterEvaluationException;
import com.liferay.search.experiences.parameter.DoubleParameter;
import com.liferay.search.experiences.parameter.FloatParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.StringParameter;
import com.liferay.search.experiences.parameter.visitor.EvaluationVisitor;

import java.util.stream.IntStream;

/**
 * @author Petteri Karttunen
 */
public class InVisitor
	extends BaseEvaluationVisitor implements EvaluationVisitor {

	public InVisitor(JSONObject conditionJSONObject) {
		super(conditionJSONObject);
	}

	@Override
	public boolean visit(DoubleParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = getConditionValueJSONArray(conditionJSONObject);

		return IntStream.range(
			0, jsonArray.length()
		).anyMatch(
			i -> parameter.equalsTo(jsonArray.getDouble(i))
		);
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = getConditionValueJSONArray(conditionJSONObject);

		return IntStream.range(
			0, jsonArray.length()
		).anyMatch(
			i -> parameter.equalsTo(GetterUtil.getFloat(jsonArray.get(i)))
		);
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = getConditionValueJSONArray(conditionJSONObject);

		return IntStream.range(
			0, jsonArray.length()
		).anyMatch(
			i -> parameter.equalsTo(jsonArray.getInt(i))
		);
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = getConditionValueJSONArray(conditionJSONObject);

		return IntStream.range(
			0, jsonArray.length()
		).anyMatch(
			i -> parameter.equalsTo(jsonArray.getLong(i))
		);
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = getConditionValueJSONArray(conditionJSONObject);

		String parameterValue = parameter.getValue();

		return IntStream.range(
			0, jsonArray.length()
		).anyMatch(
			i -> parameterValue.equals(jsonArray.getString(i))
		);
	}

}