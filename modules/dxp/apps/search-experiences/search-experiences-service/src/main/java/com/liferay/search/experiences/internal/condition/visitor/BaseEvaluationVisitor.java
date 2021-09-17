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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.exception.ParameterEvaluationException;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.DateParameter;
import com.liferay.search.experiences.parameter.DoubleParameter;
import com.liferay.search.experiences.parameter.FloatParameter;
import com.liferay.search.experiences.parameter.IntegerArrayParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.LongArrayParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.StringArrayParameter;
import com.liferay.search.experiences.parameter.StringParameter;
import com.liferay.search.experiences.parameter.visitor.EvaluationVisitor;
import com.liferay.search.experiences.problem.Problem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseEvaluationVisitor implements EvaluationVisitor {

	public BaseEvaluationVisitor(JSONObject conditionJSONObject) {
		this.conditionJSONObject = conditionJSONObject;
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

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(IntegerArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(LongArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(StringArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	protected JSONArray getConditionValueJSONArray(
			JSONObject conditionJSONObject)
		throws ParameterEvaluationException {

		Object object = conditionJSONObject.get("value");

		if (!(object instanceof JSONArray)) {
			throw new ParameterEvaluationException(
				toErrorMessage(
					getClass().getName(), "match-value-has-to-be-an-array",
					"value", GetterUtil.getString(object),
					new Throwable("Match value has to be an array")));
		}

		return (JSONArray)object;
	}

	protected String getDateAsString(Date date, String dateFormatString)
		throws ParameterEvaluationException {

		if (Validator.isBlank(dateFormatString)) {
			throw new ParameterEvaluationException(
				toErrorMessage(
					getClass().getName(), "date-format-is-required",
					"date_format", dateFormatString,
					new Throwable("Date format is required")));
		}

		try {
			DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

			return dateFormat.format(date);
		}
		catch (Exception exception) {
			throw new ParameterEvaluationException(
				toErrorMessage(
					getClass().getName(), "date-parsing-failed", "value",
					GetterUtil.getString(date), exception));
		}
	}

	protected String getDateFormatString() throws ParameterEvaluationException {
		String dateFormatString = conditionJSONObject.getString("date_format");

		if (Validator.isBlank(dateFormatString)) {
			throw new ParameterEvaluationException(
				toErrorMessage(
					getClass().getName(), "date-format-is-required", "value",
					dateFormatString,
					new Throwable("Date format is required")));
		}

		return dateFormatString;
	}

	protected Date getDateValue(JSONObject conditionJSONObject)
		throws ParameterEvaluationException {

		String dateString = conditionJSONObject.getString("value");

		String dateFormatString = getDateFormatString();

		try {
			DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

			return dateFormat.parse(dateString);
		}
		catch (Exception exception) {
			throw new ParameterEvaluationException(
				toErrorMessage(
					getClass().getName(), "date-parsing-failed", "value",
					dateString, exception));
		}
	}

	protected Problem toErrorMessage(
		String className, String languageKey, String rootJSONObjectPropertyKey,
		String rootJSONObjectValue, Throwable throwable) {

		return new Problem.Builder().className(
			className
		).languageKey(
			languageKey
		).rootJSONObject(
			conditionJSONObject
		).rootJSONObjectPropertyKey(
			rootJSONObjectPropertyKey
		).rootJSONObjectValue(
			rootJSONObjectValue
		).throwable(
			throwable
		).build();
	}

	protected final JSONObject conditionJSONObject;

}