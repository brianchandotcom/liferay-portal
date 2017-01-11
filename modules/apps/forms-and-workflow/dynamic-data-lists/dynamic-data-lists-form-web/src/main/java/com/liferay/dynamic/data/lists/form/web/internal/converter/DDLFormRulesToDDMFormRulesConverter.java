/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.lists.form.web.internal.converter;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.AutoFillDDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleCondition;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DefaultDDLFormRuleAction;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, service = DDLFormRulesToDDMFormRulesConverter.class
)
public class DDLFormRulesToDDMFormRulesConverter {

	public List<DDMFormRule> convert(List<DDLFormRule> ddlFormRules) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (DDLFormRule ddlFormRule : ddlFormRules) {
			ddmFormRules.add(convertRule(ddlFormRule));
		}

		return ddmFormRules;
	}

	protected String convertAutoFillInputParameters(
		Map<String, String> inputParametersMapper) {
		StringBundler sb = new StringBundler(
			inputParametersMapper.size() * 4 - 1);

		for (Entry<String, String> inputParameterMapper :
				inputParametersMapper.entrySet()) {

			sb.append(inputParameterMapper.getKey());
			sb.append(CharPool.EQUAL);
			sb.append(inputParameterMapper.getValue());
			sb.append(CharPool.SEMICOLON);
		}

		sb.setIndex(sb.index() - 1);

		return StringUtil.quote(sb.toString());
	}

	protected String convertAutoFillOutputParameters(
		Map<String, String> outputParametersMapper) {

		StringBundler sb = new StringBundler(
			outputParametersMapper.size() * 4 - 1);

		for (Entry<String, String> outputParameterMapper :
				outputParametersMapper.entrySet()) {

			sb.append(outputParameterMapper.getValue());
			sb.append(CharPool.EQUAL);
			sb.append(outputParameterMapper.getKey());
			sb.append(CharPool.SEMICOLON);
		}

		sb.setIndex(sb.index() - 1);

		return StringUtil.quote(sb.toString());
	}

	protected String convertAction(DDLFormRuleAction ddlFormRuleAction) {
		String action = ddlFormRuleAction.getAction();

		if (ddlFormRuleAction instanceof AutoFillDDLFormRuleAction) {
			AutoFillDDLFormRuleAction autoFillDDLFormRuleAction =
				(AutoFillDDLFormRuleAction)ddlFormRuleAction;

			return String.format(
				_functionCallTernaryExpressionFormat,
				_actionFunctionNameMap.get(action),
				StringUtil.quote(
					autoFillDDLFormRuleAction.getDDMDataProviderInstanceUUID()),
				convertAutoFillInputParameters(
					autoFillDDLFormRuleAction.getInputParametersMapper()),
				convertAutoFillOutputParameters(
					autoFillDDLFormRuleAction.getOutputParametersMapper()));
		}
		else if (ddlFormRuleAction instanceof DefaultDDLFormRuleAction) {
			DefaultDDLFormRuleAction defaultDDLFormRuleAction =
				(DefaultDDLFormRuleAction)ddlFormRuleAction;

			if (_actionBooleanFunctionNameMap.containsKey(action)) {
				String functionName = _actionBooleanFunctionNameMap.get(action);

				return String.format(
					_setBooleanPropertyFormat, functionName,
					defaultDDLFormRuleAction.getTarget());
			}
			else if (_actionFunctionNameMap.containsKey(action)) {
				String functionName = _actionFunctionNameMap.get(action);

				return String.format(
					_functionCallBinaryExpressionFormat, functionName,
					defaultDDLFormRuleAction.getSource(),
					defaultDDLFormRuleAction.getTarget());
			}
		}

		throw new UnsupportedOperationException(
			String.format("Unable to convert the %s action ", action));
	}

	protected String convertCondition(
		DDLFormRuleCondition ddlFormRuleCondition) {

		String operator = ddlFormRuleCondition.getOperator();

		String functionName = _operatorFunctionNameMap.get(operator);

		List<DDLFormRuleCondition.Operand> operands =
			ddlFormRuleCondition.getOperands();

		if (functionName == null) {
			return String.format(
				_comparisonExpressionFormat, convertOperand(operands.get(0)),
				_operatorMap.get(operator), convertOperand(operands.get(1)));
		}

		String action = String.format(
			_functionCallUnaryExpressionFormat, functionName,
			convertOperands(operands));

		if (operator.startsWith("not")) {
			return String.format(_notExpressionFormat, action);
		}

		return action;
	}

	protected String convertConditions(
		String logicalOperator,
		List<DDLFormRuleCondition> ddlFormRuleConditions) {

		if (ddlFormRuleConditions.size() == 1) {
			return convertCondition(ddlFormRuleConditions.get(0));
		}

		StringBundler sb = new StringBundler(ddlFormRuleConditions.size() * 4);

		for (DDLFormRuleCondition ddlFormRuleCondition :
				ddlFormRuleConditions) {

			sb.append(convertCondition(ddlFormRuleCondition));
			sb.append(StringPool.SPACE);
			sb.append(logicalOperator);
			sb.append(StringPool.SPACE);
		}

		sb.setIndex(sb.index() - 3);

		return sb.toString();
	}

	protected String convertOperand(DDLFormRuleCondition.Operand operand) {
		if (Objects.equals("field", operand.getType())) {
			return String.format(
				_functionCallUnaryExpressionFormat, "getValue",
				StringUtil.quote(operand.getValue()));
		}

		return StringUtil.quote(operand.getValue());
	}

	protected String convertOperands(
		List<DDLFormRuleCondition.Operand> operands) {

		StringBundler sb = new StringBundler(operands.size());

		for (DDLFormRuleCondition.Operand operand : operands) {
			sb.append(convertOperand(operand));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected DDMFormRule convertRule(DDLFormRule ddlFormRule) {
		String condition = convertConditions(
			ddlFormRule.getLogicalOperator(),
			ddlFormRule.getDDLFormRuleConditions());

		List<String> actions = new ArrayList<>();

		for (DDLFormRuleAction ddlFormRuleAction :
				ddlFormRule.getDDLFormRuleActions()) {

			actions.add(convertAction(ddlFormRuleAction));
		}

		return new DDMFormRule(condition, actions);
	}

	private static final Map<String, String> _actionBooleanFunctionNameMap =
		new HashMap<>();
	private static final Map<String, String> _actionFunctionNameMap =
		new HashMap<>();
	private static final String _comparisonExpressionFormat = "%s %s %s";
	private static final String _functionCallBinaryExpressionFormat =
		"%s(%s, %s)";
	private static final String _functionCallTernaryExpressionFormat =
		"%s(%s, %s, %s)";
	private static final String _functionCallUnaryExpressionFormat = "%s(%s)";
	private static final String _notExpressionFormat = "not(%s)";
	private static final Map<String, String> _operatorFunctionNameMap =
		new HashMap<>();
	private static final Map<String, String> _operatorMap = new HashMap<>();
	private static final String _setBooleanPropertyFormat = "%s('%s', true)";

	static {
		_actionBooleanFunctionNameMap.put("enable", "setEnabled");
		_actionBooleanFunctionNameMap.put("invalidate", "setInvalid");
		_actionBooleanFunctionNameMap.put("require", "setRequired");
		_actionBooleanFunctionNameMap.put("show", "setVisible");

		_actionFunctionNameMap.put("auto-fill", "call");
		_actionFunctionNameMap.put("jump-to-page", "jumpPage");

		_operatorFunctionNameMap.put("contains", "contains");
		_operatorFunctionNameMap.put("equals-to", "equals");
		_operatorFunctionNameMap.put("not-contains", "contains");
		_operatorFunctionNameMap.put("not-equals-to", "equals");

		_operatorMap.put("greater-than", ">");
		_operatorMap.put("greater-than-equals", ">=");
		_operatorMap.put("less-than", "<");
		_operatorMap.put("less-than-equals", "<=");
	}

}