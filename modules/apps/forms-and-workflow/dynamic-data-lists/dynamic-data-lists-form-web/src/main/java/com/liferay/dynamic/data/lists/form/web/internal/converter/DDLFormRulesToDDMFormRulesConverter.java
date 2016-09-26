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

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleCondition;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	protected String convertAction(DDLFormRuleAction ddlFormRuleAction) {
		String functionName = _actionFunctionNameMap.get(
			ddlFormRuleAction.getAction());

		return String.format(
			_setBooleanPropertyFormat, functionName,
			ddlFormRuleAction.getTarget());
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
			_functionCallExpressionFormat, functionName,
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
				_functionCallExpressionFormat, "getValue",
				StringUtil.quote(operand.getValue()));
		}

		if (Validator.isNumber(operand.getValue())) {
			return operand.getValue();
		}
		else {
			return StringUtil.quote(operand.getValue());
		}
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

	private static final Map<String, String> _actionFunctionNameMap =
		new HashMap<>();
	private static final String _comparisonExpressionFormat = "%s %s %s";
	private static final String _functionCallExpressionFormat = "%s(%s)";
	private static final String _notExpressionFormat = "not(%s)";
	private static final Map<String, String> _operatorFunctionNameMap =
		new HashMap<>();
	private static final Map<String, String> _operatorMap = new HashMap<>();
	private static final String _setBooleanPropertyFormat = "%s('%s', true)";

	static {
		_actionFunctionNameMap.put("show", "setVisible");
		_actionFunctionNameMap.put("enable", "setEnabled");
		_actionFunctionNameMap.put("require", "setRequired");
		_actionFunctionNameMap.put("invalidate", "setInvalid");

		_operatorFunctionNameMap.put("contains", "contains");
		_operatorFunctionNameMap.put("not-contains", "contains");
		_operatorFunctionNameMap.put("equals-to", "equals");
		_operatorFunctionNameMap.put("not-equals-to", "equals");

		_operatorMap.put("greater-than", ">");
		_operatorMap.put("greater-than-equals", ">=");
		_operatorMap.put("less-than", "<");
		_operatorMap.put("less-than-equals", "<=");
	}

}