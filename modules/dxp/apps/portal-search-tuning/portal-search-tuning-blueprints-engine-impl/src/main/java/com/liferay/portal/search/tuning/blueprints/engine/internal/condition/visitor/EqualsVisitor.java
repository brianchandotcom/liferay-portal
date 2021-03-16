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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.ConditionConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.exception.ParameterEvaluationException;
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
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Petteri Karttunen
 */
public class EqualsVisitor implements ConditionEvaluationVisitor {

	public EqualsVisitor(JSONObject conditionJSONObject, boolean not) {
		_conditionJSONObject = conditionJSONObject;
		_not = not;
	}

	@Override
	public boolean visit(BooleanParameter parameter)
		throws ParameterEvaluationException {

		Boolean value = _conditionJSONObject.getBoolean(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		Boolean parameterValue = parameter.getValue();

		boolean equals = false;

		if (value.booleanValue() == parameterValue.booleanValue()) {
			equals = true;
		}

		if (_not) {
			return !equals;
		}

		return equals;
	}

	@Override
	public boolean visit(DateParameter parameter)
		throws ParameterEvaluationException {

		String dateString = _conditionJSONObject.getString(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		String dateFormatString = _conditionJSONObject.getString(
			ConditionConfigurationKeys.DATE_FORMAT.getJsonKey());

		if (Validator.isNull(dateFormatString)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Clause condition date format missing [ " +
						_conditionJSONObject + " ].");
			}

			throw new ParameterEvaluationException(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.clause-condition-date-format-missing"
				).msg(
					"Date format is not defined"
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

			Date date = dateFormat.parse(dateString);

			Date parameterValue = parameter.getValue();

			if (_not) {
				return !parameterValue.equals(date);
			}

			return parameterValue.equals(date);
		}
		catch (Exception exception) {
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

		Double value = _conditionJSONObject.getDouble(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		if (_not) {
			return !parameter.equalsTo(value);
		}

		return parameter.equalsTo(value);
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		Float value = GetterUtil.getFloat(
			_conditionJSONObject.get(
				ConditionConfigurationKeys.VALUE.getJsonKey()));

		if (_not) {
			return !parameter.equalsTo(value);
		}

		return parameter.equalsTo(value);
	}

	@Override
	public boolean visit(IntegerArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		Integer value = _conditionJSONObject.getInt(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		if (_not) {
			return !parameter.equalsTo(value);
		}

		return parameter.equalsTo(value);
	}

	@Override
	public boolean visit(LongArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		Long value = _conditionJSONObject.getLong(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		if (_not) {
			return !parameter.equalsTo(value);
		}

		return parameter.equalsTo(value);
	}

	@Override
	public boolean visit(StringArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		String value = _conditionJSONObject.getString(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		String parameterValue = parameter.getValue();

		if (_not) {
			return !parameterValue.equals(value);
		}

		return parameterValue.equals(value);
	}

	private static final Log _log = LogFactoryUtil.getLog(EqualsVisitor.class);

	private final JSONObject _conditionJSONObject;
	private final boolean _not;

}