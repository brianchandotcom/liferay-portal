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

package com.liferay.portal.search.tuning.blueprints.engine.internal.condition.handler;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.ConditionConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.engine.exception.ParameterEvaluationException;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.AnyWordInVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.ContainsVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.EqualsVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.GreaterThanVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.InRangeVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor.InVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ConditionEvaluationVisitor;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ConditionHandler;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintTemplateVariableParser;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=default",
	service = ConditionHandler.class
)
public class DefaultConditionHandler implements ConditionHandler {

	@Override
	public boolean isTrue(
		JSONObject configurationJSONObject, ParameterData parameterData,
		Messages messages) {

		if (!_validateCondition(messages, configurationJSONObject)) {
			return false;
		}

		EvaluationType evaluationType = _resolveEvaluationType(
			configurationJSONObject, messages);

		if (evaluationType == null) {
			return false;
		}

		Optional<Parameter> parameterOptional =
			parameterData.getByTemplateVariableNameOptional(
				configurationJSONObject.getString(
					ConditionConfigurationKeys.PARAMETER_NAME.getJsonKey()));

		if (EvaluationType.EXISTS.equals(evaluationType) ||
			EvaluationType.NOT_EXISTS.equals(evaluationType)) {

			return _evaluateExistsCondition(evaluationType, parameterOptional);
		}

		if (!parameterOptional.isPresent()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Clause condition parameter is not present [ " +
						configurationJSONObject + " ].");
			}

			return false;
		}

		Optional<Object> parsedValueOptional =
			_blueprintTemplateVariableParser.parseObject(
				configurationJSONObject.get(
					ConditionConfigurationKeys.VALUE.getJsonKey()),
				parameterData, messages);

		if (!parsedValueOptional.isPresent()) {
			return false;
		}

		configurationJSONObject.put(
			ConditionConfigurationKeys.VALUE.getJsonKey(),
			parsedValueOptional.get());

		Parameter parameter = parameterOptional.get();

		ConditionEvaluationVisitor conditionEvaluationVisitor = _resolveVisitor(
			parameter, configurationJSONObject, evaluationType, messages);

		if (conditionEvaluationVisitor == null) {
			return false;
		}

		return _evaluate(parameter, conditionEvaluationVisitor, messages);
	}

	private boolean _evaluate(
		Parameter parameter, ConditionEvaluationVisitor visitor,
		Messages messages) {

		try {
			return parameter.accept(visitor);
		}
		catch (ParameterEvaluationException parameterEvaluationException) {
			messages.addMessage(
				parameterEvaluationException.getDetailsMessage());

			return false;
		}
	}

	private boolean _evaluateExistsCondition(
		EvaluationType evaluationType, Optional<Parameter> parameterOptional) {

		if (EvaluationType.EXISTS.equals(evaluationType)) {
			if (parameterOptional.isPresent()) {
				return true;
			}

			return false;
		}

		if (!parameterOptional.isPresent()) {
			return true;
		}

		return false;
	}

	private EvaluationType _getEvaluationType(String s)
		throws IllegalArgumentException {

		return EvaluationType.valueOf(StringUtil.toUpperCase(s));
	}

	private ConditionEvaluationVisitor _getEvaluationVisitor(
		Parameter parameter, JSONObject configurationJSONObject,
		EvaluationType evaluationType) {

		ConditionEvaluationVisitor visitor = null;

		List<EvaluationType> supportedEvaluationTypes =
			parameter.getSupportedEvaluationTypes();

		if (EvaluationType.ANY_WORD_IN.equals(evaluationType) &&
			supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new AnyWordInVisitor(configurationJSONObject, false);
		}
		else if (EvaluationType.CONTAINS.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new ContainsVisitor(configurationJSONObject, false);
		}
		else if (EvaluationType.EQ.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new EqualsVisitor(configurationJSONObject, false);
		}
		else if (EvaluationType.GT.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new GreaterThanVisitor(
				configurationJSONObject, false, false);
		}
		else if (EvaluationType.GTE.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new GreaterThanVisitor(
				configurationJSONObject, false, true);
		}
		else if (EvaluationType.IN.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new InVisitor(configurationJSONObject, false);
		}
		else if (EvaluationType.IN_RANGE.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new InRangeVisitor(configurationJSONObject, false);
		}
		else if (EvaluationType.LT.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new GreaterThanVisitor(
				configurationJSONObject, true, false);
		}
		else if (EvaluationType.LTE.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new GreaterThanVisitor(
				configurationJSONObject, true, true);
		}
		else if (EvaluationType.NE.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new EqualsVisitor(configurationJSONObject, true);
		}
		else if (EvaluationType.NO_WORD_IN.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new AnyWordInVisitor(configurationJSONObject, true);
		}
		else if (EvaluationType.NOT_CONTAINS.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new ContainsVisitor(configurationJSONObject, true);
		}
		else if (EvaluationType.NOT_IN.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new InRangeVisitor(configurationJSONObject, true);
		}
		else if (EvaluationType.NOT_IN_RANGE.equals(evaluationType) &&
				 supportedEvaluationTypes.contains(evaluationType)) {

			visitor = new InRangeVisitor(configurationJSONObject, true);
		}

		return visitor;
	}

	private EvaluationType _resolveEvaluationType(
		JSONObject configurationJSONObject, Messages messages) {

		String evaluationTypeString = configurationJSONObject.getString(
			ConditionConfigurationKeys.EVALUATION_TYPE.getJsonKey());

		try {
			return _getEvaluationType(evaluationTypeString);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-clause-condition-evaluation-type"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					ConditionConfigurationKeys.EVALUATION_TYPE.getJsonKey()
				).rootValue(
					evaluationTypeString
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unknown clause condition evaluation type " +
						evaluationTypeString + ".",
					illegalArgumentException);
			}
		}

		return null;
	}

	private ConditionEvaluationVisitor _resolveVisitor(
		Parameter parameter, JSONObject configurationJSONObject,
		EvaluationType evaluationType, Messages messages) {

		ConditionEvaluationVisitor visitor = _getEvaluationVisitor(
			parameter, configurationJSONObject, evaluationType);

		if (visitor != null) {
			return visitor;
		}

		messages.addMessage(
			new Message.Builder().className(
				getClass().getName()
			).localizationKey(
				"core.error.unknown-clause-condition-evaluation-type"
			).msg(
				"Unknown clause condition evaluation type"
			).rootObject(
				configurationJSONObject
			).rootProperty(
				ConditionConfigurationKeys.EVALUATION_TYPE.getJsonKey()
			).rootValue(
				evaluationType.name()
			).severity(
				Severity.ERROR
			).build());

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unknown clause condition evaluation type " +
					evaluationType.name() + ".");
		}

		return null;
	}

	private boolean _validateCondition(
		Messages messages, JSONObject configurationJSONObject) {

		boolean valid = true;

		if (!configurationJSONObject.has(
				ConditionConfigurationKeys.PARAMETER_NAME.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-clause-condition-parameter"
				).msg(
					"Clause condition parameter is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					ConditionConfigurationKeys.PARAMETER_NAME.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Clause condition parameter is undefined [ " +
						configurationJSONObject + " ].");
			}

			valid = false;
		}

		if (!configurationJSONObject.has(
				ConditionConfigurationKeys.EVALUATION_TYPE.getJsonKey())) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-clause-condition-evaluation-type"
				).msg(
					"Clause condition evaluation type is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					ConditionConfigurationKeys.EVALUATION_TYPE.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Clause condition evaluation type is undefined [ " +
						configurationJSONObject + " ].");
			}

			valid = false;
		}

		return valid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultConditionHandler.class);

	@Reference
	private BlueprintTemplateVariableParser _blueprintTemplateVariableParser;

}