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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.ConditionConfigurationKeys;
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
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Petteri Karttunen
 */
public class InRangeVisitor implements ConditionEvaluationVisitor {

	public InRangeVisitor(JSONObject conditionJSONObject, boolean not) {
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

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		_checkRangeValue(jsonArray);

		String dateFormatString = _conditionJSONObject.getString(
			ConditionConfigurationKeys.DATE_FORMAT.getJsonKey());

		String dateString = _conditionJSONObject.getString(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		if (Validator.isNull(dateFormatString)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Clause condition date parameter format is missing [ " +
						_conditionJSONObject + " ].");
			}

			throw new ParameterEvaluationException(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.clause-condition-date-format-missing"
				).msg(
					"Clause condition date format is missing"
				).rootObject(
					_conditionJSONObject
				).rootProperty(
					ConditionConfigurationKeys.DATE_FORMAT.getJsonKey()
				).rootValue(
					dateFormatString
				).severity(
					Severity.ERROR
				).build());
		}

		try {
			DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

			String lowerBoundString = jsonArray.getString(0);
			String upperBoundString = jsonArray.getString(1);

			Date lowerBound = dateFormat.parse(lowerBoundString);
			Date upperBound = dateFormat.parse(upperBoundString);

			Date parameterValue = parameter.getValue();

			boolean inRange = false;

			if (parameterValue.after(lowerBound) &&
				parameterValue.before(upperBound)) {

				inRange = true;
			}

			if (_not) {
				return !inRange;
			}

			return inRange;
		}
		catch (IllegalArgumentException | NullPointerException | ParseException
					exception) {

			_log.error(
				"Unable to parse clause condition date " + dateString + ".",
				exception);

			throw new ParameterEvaluationException(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.clause-condition-date-parsing-error"
				).msg(
					exception.getMessage()
				).rootObject(
					_conditionJSONObject
				).rootProperty(
					ConditionConfigurationKeys.VALUE.getJsonKey()
				).rootValue(
					dateString
				).severity(
					Severity.ERROR
				).throwable(
					exception
				).build());
		}
	}

	@Override
	public boolean visit(DoubleParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		_checkRangeValue(jsonArray);

		Double lowerBound = jsonArray.getDouble(0);
		Double upperBound = jsonArray.getDouble(1);

		Double parameterValue = parameter.getValue();

		boolean inRange = false;

		if ((parameterValue.compareTo(lowerBound) >= 0) &&
			(parameterValue.compareTo(upperBound) <= 0)) {

			inRange = true;
		}

		if (_not) {
			return !inRange;
		}

		return inRange;
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		JSONArray jsonArray = BlueprintValueUtil.getConditionValueJSONArray(
			_conditionJSONObject);

		_checkRangeValue(jsonArray);

		Float lowerBound = GetterUtil.getFloat(jsonArray.get(0));
		Float upperBound = GetterUtil.getFloat(jsonArray.get(1));

		Float parameterValue = parameter.getValue();

		boolean inRange = false;

		if ((parameterValue.compareTo(lowerBound) >= 0) &&
			(parameterValue.compareTo(upperBound) <= 0)) {

			inRange = true;
		}

		if (_not) {
			return !inRange;
		}

		return inRange;
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

		_checkRangeValue(jsonArray);

		Integer lowerBound = jsonArray.getInt(0);
		Integer upperBound = jsonArray.getInt(1);

		Integer parameterValue = parameter.getValue();

		boolean inRange = false;

		if ((parameterValue.compareTo(lowerBound) >= 0) &&
			(parameterValue.compareTo(upperBound) <= 0)) {

			inRange = true;
		}

		if (_not) {
			return !inRange;
		}

		return inRange;
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

		_checkRangeValue(jsonArray);

		Long lowerBound = jsonArray.getLong(0);
		Long upperBound = jsonArray.getLong(1);

		Long parameterValue = parameter.getValue();

		boolean inRange = false;

		if ((parameterValue.compareTo(lowerBound) >= 0) &&
			(parameterValue.compareTo(upperBound) <= 0)) {

			inRange = true;
		}

		if (_not) {
			return !inRange;
		}

		return inRange;
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

	private void _checkRangeValue(JSONArray jsonArray)
		throws ParameterEvaluationException {

		if (jsonArray.length() != 2) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid clause condition range value " +
						jsonArray.toString() + ".");
			}

			throw new ParameterEvaluationException(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.invalid-clause-condition-range-value"
				).msg(
					"Invalid clause condition range value"
				).rootObject(
					_conditionJSONObject
				).rootProperty(
					ConditionConfigurationKeys.VALUE.getJsonKey()
				).rootValue(
					jsonArray.toString()
				).severity(
					Severity.ERROR
				).build());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(InRangeVisitor.class);

	private final JSONObject _conditionJSONObject;
	private final boolean _not;

}