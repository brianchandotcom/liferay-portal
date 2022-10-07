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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.constants.DDMExpressionConstants;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ConditionExpressionVisitor;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleCondition;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@Component(service = SPIDDMFormRuleConverter.class)
public class DDMFormRuleConverterImpl implements SPIDDMFormRuleConverter {

	@Override
	public List<SPIDDMFormRule> convert(List<DDMFormRule> ddmFormRules) {
		List<SPIDDMFormRule> spiDDMFormRules = new ArrayList<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			spiDDMFormRules.add(_convertRule(ddmFormRule));
		}

		return spiDDMFormRules;
	}

	@Override
	public List<DDMFormRule> convert(
		List<SPIDDMFormRule> spiDDMFormRules,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		Stream<SPIDDMFormRule> spiDDMFormRulesStream = spiDDMFormRules.stream();

		Stream<DDMFormRule> ddmFormRuleStream = spiDDMFormRulesStream.map(
			formRule -> _convertRule(
				formRule, spiDDMFormRuleSerializerContext));

		return ddmFormRuleStream.collect(Collectors.toList());
	}

	protected void setSPIDDMFormRuleActions(
		SPIDDMFormRule spiDDMFormRule, List<String> actions) {

		List<SPIDDMFormRuleAction> spiDDMFormRuleActions = new ArrayList<>();

		for (String action : actions) {
			spiDDMFormRuleActions.add(_convertAction(action));
		}

		spiDDMFormRule.setSPIDDMFormRuleActions(spiDDMFormRuleActions);
	}

	protected void setSPIDDMFormRuleConditions(
		SPIDDMFormRule spiDDMFormRule, String conditionExpressionString) {

		Expression conditionExpression = _createExpression(
			conditionExpressionString);

		ConditionExpressionVisitor conditionExpressionVisitor =
			new ConditionExpressionVisitor();

		conditionExpression.accept(conditionExpressionVisitor);

		spiDDMFormRule.setSPIDDMFormRuleConditions(
			conditionExpressionVisitor.getSPIDDMFormRuleConditions());
		spiDDMFormRule.setLogicalOperator(
			conditionExpressionVisitor.getLogicalOperator());
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	private SPIDDMFormRuleAction _convertAction(String actionExpressionString) {
		Expression actionExpression = _createExpression(actionExpressionString);

		return (SPIDDMFormRuleAction)actionExpression.accept(
			new ActionExpressionVisitor());
	}

	private String _convertCondition(
		SPIDDMFormRuleCondition spiDDMFormRuleCondition) {

		String operator = spiDDMFormRuleCondition.getOperator();

		List<SPIDDMFormRuleCondition.Operand> operands =
			spiDDMFormRuleCondition.getOperands();

		if (_operators.containsKey(operator)) {
			if (operands.size() < 2) {
				return StringPool.BLANK;
			}

			return String.format(
				_COMPARISON_EXPRESSION_FORMAT, _convertOperand(operands.get(0)),
				_operators.get(operator), _convertOperand(operands.get(1)));
		}

		String functionName = _operatorFunctionNames.getOrDefault(
			operator, operator);

		String condition = _createCondition(functionName, operands);

		if (operator.startsWith("not")) {
			return String.format(_NOT_EXPRESSION_FORMAT, condition);
		}

		return condition;
	}

	private String _convertConditions(
		String logicalOperator,
		List<SPIDDMFormRuleCondition> spiDDMFormRuleConditions) {

		if (spiDDMFormRuleConditions.size() == 1) {
			return _convertCondition(spiDDMFormRuleConditions.get(0));
		}

		StringBundler sb = new StringBundler(
			spiDDMFormRuleConditions.size() * 4);

		for (SPIDDMFormRuleCondition spiDDMFormRuleCondition :
				spiDDMFormRuleConditions) {

			sb.append(_convertCondition(spiDDMFormRuleCondition));
			sb.append(StringPool.SPACE);
			sb.append(logicalOperator);
			sb.append(StringPool.SPACE);
		}

		sb.setIndex(sb.index() - 3);

		return sb.toString();
	}

	private String _convertOperand(SPIDDMFormRuleCondition.Operand operand) {
		if (Objects.equals("field", operand.getType())) {
			return String.format(
				_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getValue",
				StringUtil.quote(operand.getValue()));
		}
		else if (Objects.equals("json", operand.getType())) {
			return String.format(
				_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getJSONValue",
				StringUtil.quote(operand.getValue()));
		}

		String value = operand.getValue();

		if (_isNumericConstant(operand.getType())) {
			return value;
		}

		if (Objects.equals("string", operand.getType())) {
			return StringUtil.quote(value);
		}

		String[] values = StringUtil.split(value);

		UnaryOperator<String> quoteOperation = StringUtil::quote;
		UnaryOperator<String> trimOperation = StringUtil::trim;

		return Stream.of(
			values
		).map(
			trimOperation.andThen(quoteOperation)
		).collect(
			_getCollector(operand.getType())
		);
	}

	private String _convertOperands(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		StringBundler sb = new StringBundler(operands.size());

		boolean hasNestedFunction = _hasNestedFunction(operands);

		for (int i = 0; i < operands.size(); i++) {
			SPIDDMFormRuleCondition.Operand operand = operands.get(i);

			if (hasNestedFunction) {
				sb.append(operand.getValue());
			}
			else {
				if ((i > 0) && Objects.equals("option", operand.getType())) {
					SPIDDMFormRuleCondition.Operand previousOperand =
						operands.get(i - 1);

					sb.append(
						String.format(
							_FUNCTION_CALL_BINARY_EXPRESSION_FORMAT,
							"getOptionLabel",
							StringUtil.quote(previousOperand.getValue()),
							StringUtil.quote(operand.getValue())));
				}
				else {
					sb.append(_convertOperand(operand));
				}
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private SPIDDMFormRule _convertRule(DDMFormRule ddmFormRule) {
		SPIDDMFormRule spiDDMFormRule = new SPIDDMFormRule();

		spiDDMFormRule.setName(ddmFormRule.getName());

		setSPIDDMFormRuleConditions(spiDDMFormRule, ddmFormRule.getCondition());
		setSPIDDMFormRuleActions(spiDDMFormRule, ddmFormRule.getActions());

		return spiDDMFormRule;
	}

	private DDMFormRule _convertRule(
		SPIDDMFormRule spiDDMFormRule,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		String condition = _convertConditions(
			spiDDMFormRule.getLogicalOperator(),
			spiDDMFormRule.getSPIDDMFormRuleConditions());

		List<String> actions = new ArrayList<>();

		for (SPIDDMFormRuleAction spiDDMFormRuleAction :
				spiDDMFormRule.getSPIDDMFormRuleActions()) {

			actions.add(
				spiDDMFormRuleAction.serialize(
					spiDDMFormRuleSerializerContext));
		}

		return new DDMFormRule(actions, condition, spiDDMFormRule.getName());
	}

	private String _createCondition(
		String functionName, List<SPIDDMFormRuleCondition.Operand> operands) {

		if (Objects.equals(functionName, "belongsTo")) {
			operands.removeIf(
				operand -> StringUtil.equals(operand.getType(), "user"));
		}

		return String.format(
			_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, functionName,
			_convertOperands(operands));
	}

	private Expression _createExpression(String expressionString) {
		try {
			CreateExpressionRequest createExpressionRequest =
				CreateExpressionRequest.Builder.newBuilder(
					expressionString
				).build();

			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createExpression(createExpressionRequest);

			return ddmExpression.getModel();
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new IllegalStateException(
				String.format(
					"Unable to parse expression \"%s\"", expressionString),
				ddmExpressionException);
		}
	}

	private Collector<CharSequence, ?, String> _getCollector(
		String operandType) {

		if (operandType.equals("list")) {
			return Collectors.joining(
				StringPool.COMMA_AND_SPACE, StringPool.OPEN_BRACKET,
				StringPool.CLOSE_BRACKET);
		}

		return Collectors.joining(StringPool.COMMA_AND_SPACE);
	}

	private boolean _hasNestedFunction(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		Stream<SPIDDMFormRuleCondition.Operand> operandsStream =
			operands.stream();

		return operandsStream.anyMatch(
			operand -> _isNestedFunction(operand.getValue()));
	}

	private boolean _isNestedFunction(String operandValue) {
		return operandValue.matches(
			DDMExpressionConstants.NESTED_FUNCTION_REGEX);
	}

	private boolean _isNumericConstant(String operandType) {
		if (operandType.equals("integer") || operandType.equals("double")) {
			return true;
		}

		return false;
	}

	private static final String _COMPARISON_EXPRESSION_FORMAT = "%s %s %s";

	private static final String _FUNCTION_CALL_BINARY_EXPRESSION_FORMAT =
		"%s(%s, %s)";

	private static final String _FUNCTION_CALL_UNARY_EXPRESSION_FORMAT =
		"%s(%s)";

	private static final String _NOT_EXPRESSION_FORMAT = "not(%s)";

	private static final Map<String, String> _operatorFunctionNames =
		HashMapBuilder.put(
			"belongs-to", "belongsTo"
		).put(
			"contains", "contains"
		).put(
			"equals-to", "equals"
		).put(
			"is-empty", "isEmpty"
		).put(
			"not-contains", "contains"
		).put(
			"not-equals-to", "equals"
		).put(
			"not-is-empty", "isEmpty"
		).build();
	private static final Map<String, String> _operators = HashMapBuilder.put(
		"greater-than", ">"
	).put(
		"greater-than-equals", ">="
	).put(
		"less-than", "<"
	).put(
		"less-than-equals", "<="
	).build();

}