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
public class EqualsVisitor
	extends BaseEvaluationVisitor implements ConditionEvaluationVisitor {

	public EqualsVisitor(JSONObject conditionJSONObject, boolean not) {
		super(conditionJSONObject, not);
	}

	@Override
	public boolean visit(BooleanParameter parameter)
		throws ParameterEvaluationException {

		Boolean value = conditionJSONObject.getBoolean(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		Boolean parameterValue = parameter.getValue();

		if (returnValue(
				value.booleanValue() == parameterValue.booleanValue())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean visit(DateParameter parameter)
		throws ParameterEvaluationException {

		String dateString = conditionJSONObject.getString(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		String dateFormatString = conditionJSONObject.getString(
			ConditionConfigurationKeys.DATE_FORMAT.getJsonKey());

		if (Validator.isNull(dateFormatString)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Clause condition date format missing [ " +
						conditionJSONObject + " ].");
			}

			throw new ParameterEvaluationException(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.clause-condition-date-format-missing"
				).msg(
					"Date format is not defined"
				).rootObject(
					conditionJSONObject
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

			return returnValue(parameterValue.equals(date));
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
					conditionJSONObject
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

		Double value = conditionJSONObject.getDouble(
			ConditionConfigurationKeys.VALUE.getJsonKey());

		return returnValue(parameter.equalsTo(value));
	}

	@Override
	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException {

		return returnValue(
			parameter.equalsTo(
				GetterUtil.getFloat(
					conditionJSONObject.get(
						ConditionConfigurationKeys.VALUE.getJsonKey()))));
	}

	@Override
	public boolean visit(IntegerArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException {

		return returnValue(
			parameter.equalsTo(
				conditionJSONObject.getInt(
					ConditionConfigurationKeys.VALUE.getJsonKey())));
	}

	@Override
	public boolean visit(LongArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException {

		return returnValue(
			parameter.equalsTo(
				conditionJSONObject.getLong(
					ConditionConfigurationKeys.VALUE.getJsonKey())));
	}

	@Override
	public boolean visit(StringArrayParameter parameter)
		throws ParameterEvaluationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException {

		String parameterValue = parameter.getValue();

		return returnValue(
			parameterValue.equals(
				conditionJSONObject.getString(
					ConditionConfigurationKeys.VALUE.getJsonKey())));
	}

	private static final Log _log = LogFactoryUtil.getLog(EqualsVisitor.class);

}