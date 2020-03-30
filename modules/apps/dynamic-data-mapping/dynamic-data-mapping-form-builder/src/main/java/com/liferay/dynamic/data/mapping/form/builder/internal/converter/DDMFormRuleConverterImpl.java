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
@Component(immediate = true, service = SPIDDMFormRuleConverter.class)
public class DDMFormRuleConverterImpl implements SPIDDMFormRuleConverter {

	@Override
	public List<SPIDDMFormRule> convert(List<DDMFormRule> ddmFormRules) {
		List<SPIDDMFormRule> spiDDMFormRules = new ArrayList<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			spiDDMFormRules.add(convertRule(ddmFormRule));
		}

		return spiDDMFormRules;
	}

	@Override
	public List<DDMFormRule> convert(
		List<SPIDDMFormRule> spiDDMFormRules,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		Stream<SPIDDMFormRule> spiDDMFormRuleStream = spiDDMFormRules.stream();

		Stream<DDMFormRule> ddmFormRuleStream = spiDDMFormRuleStream.map(
			formRule -> convertRule(formRule, spiDDMFormRuleSerializerContext));

		return ddmFormRuleStream.collect(Collectors.toList());
	}

	protected SPIDDMFormRuleAction convertAction(
		String actionExpressionString) {

		Expression actionExpression = createExpression(actionExpressionString);

		ActionExpressionVisitor actionExpressionVisitor =
			new ActionExpressionVisitor();

		return (SPIDDMFormRuleAction)actionExpression.accept(
			actionExpressionVisitor);
	}

	protected String convertCondition(
		SPIDDMFormRuleCondition spiDDMFormRuleCondition) {

		String operator = spiDDMFormRuleCondition.getOperator();

		String functionName = _operatorFunctionNameMap.get(operator);

		List<SPIDDMFormRuleCondition.Operand> operands =
			spiDDMFormRuleCondition.getOperands();

		if (functionName == null) {
			return String.format(
				_COMPARISON_EXPRESSION_FORMAT, convertOperand(operands.get(0)),
				_operatorMap.get(operator), convertOperand(operands.get(1)));
		}

		String condition = createCondition(functionName, operands);

		if (operator.startsWith("not")) {
			return String.format(_NOT_EXPRESSION_FORMAT, condition);
		}

		return condition;
	}

	protected String convertConditions(
		String logicalOperator,
		List<SPIDDMFormRuleCondition> spiDDMFormRuleConditions) {

		if (spiDDMFormRuleConditions.size() == 1) {
			return convertCondition(spiDDMFormRuleConditions.get(0));
		}

		StringBundler sb = new StringBundler(
			spiDDMFormRuleConditions.size() * 4);

		for (SPIDDMFormRuleCondition spiDDMFormRuleCondition :
				spiDDMFormRuleConditions) {

			sb.append(convertCondition(spiDDMFormRuleCondition));
			sb.append(StringPool.SPACE);
			sb.append(logicalOperator);
			sb.append(StringPool.SPACE);
		}

		sb.setIndex(sb.index() - 3);

		return sb.toString();
	}

	protected String convertOperand(SPIDDMFormRuleCondition.Operand operand) {
		if (Objects.equals("field", operand.getType())) {
			return String.format(
				_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, "getValue",
				StringUtil.quote(operand.getValue()));
		}

		String value = operand.getValue();

		if (isNumericConstant(operand.getType())) {
			return value;
		}

		String[] values = StringUtil.split(value);

		UnaryOperator<String> quoteOperation = StringUtil::quote;
		UnaryOperator<String> trimOperation = StringUtil::trim;

		return Stream.of(
			values
		).map(
			trimOperation.andThen(quoteOperation)
		).collect(
			getCollector(operand.getType())
		);
	}

	protected String convertOperands(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		boolean hasNestedFunctionOperands = _hasNestedFunctionOperands(
			operands);

		StringBundler sb = new StringBundler(operands.size());

		for (SPIDDMFormRuleCondition.Operand operand : operands) {
			if (hasNestedFunctionOperands) {
				sb.append(operand.getValue());
			}
			else {
				sb.append(convertOperand(operand));
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected SPIDDMFormRule convertRule(DDMFormRule ddmFormRule) {
		SPIDDMFormRule spiDDMFormRule = new SPIDDMFormRule();

		spiDDMFormRule.setName(ddmFormRule.getName());

		setSPIDDMFormRuleConditions(spiDDMFormRule, ddmFormRule.getCondition());
		setSPIDDMFormRuleActions(spiDDMFormRule, ddmFormRule.getActions());

		return spiDDMFormRule;
	}

	protected DDMFormRule convertRule(
		SPIDDMFormRule spiDDMFormRule,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		String condition = convertConditions(
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

	protected String createCondition(
		String functionName, List<SPIDDMFormRuleCondition.Operand> operands) {

		if (Objects.equals(functionName, "belongsTo")) {
			operands.remove(0);
		}

		return String.format(
			_FUNCTION_CALL_UNARY_EXPRESSION_FORMAT, functionName,
			convertOperands(operands));
	}

	protected Expression createExpression(String expressionString) {
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

	protected Collector<CharSequence, ?, String> getCollector(
		String operandType) {

		if (operandType.equals("list")) {
			return Collectors.joining(
				StringPool.COMMA_AND_SPACE, StringPool.OPEN_BRACKET,
				StringPool.CLOSE_BRACKET);
		}

		return Collectors.joining(StringPool.COMMA_AND_SPACE);
	}

	protected boolean isNumericConstant(String operandType) {
		if (operandType.equals("integer") || operandType.equals("double")) {
			return true;
		}

		return false;
	}

	protected void setSPIDDMFormRuleActions(
		SPIDDMFormRule spiDDMFormRule, List<String> actions) {

		List<SPIDDMFormRuleAction> spiDDMFormRuleActions = new ArrayList<>();

		for (String action : actions) {
			spiDDMFormRuleActions.add(convertAction(action));
		}

		spiDDMFormRule.setSPIDDMFormRuleActions(spiDDMFormRuleActions);
	}

	protected void setSPIDDMFormRuleConditions(
		SPIDDMFormRule spiDDMFormRule, String conditionExpressionString) {

		Expression conditionExpression = createExpression(
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

	private boolean _hasNestedFunctionOperands(
		List<SPIDDMFormRuleCondition.Operand> operands) {

		Stream<SPIDDMFormRuleCondition.Operand> operandStream =
			operands.stream();

		return operandStream.anyMatch(
			operand -> _isNestedFunction(operand.getValue()));
	}

	private boolean _isNestedFunction(String operandValue) {
		return operandValue.matches(
			DDMExpressionConstants.NESTED_FUNCTION_REGEX);
	}

	private static final String _COMPARISON_EXPRESSION_FORMAT = "%s %s %s";

	private static final String _FUNCTION_CALL_UNARY_EXPRESSION_FORMAT =
		"%s(%s)";

	private static final String _NOT_EXPRESSION_FORMAT = "not(%s)";

	private static final Map<String, String> _operatorFunctionNameMap =
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
	private static final Map<String, String> _operatorMap = HashMapBuilder.put(
		"greater-than", ">"
	).put(
		"greater-than-equals", ">="
	).put(
		"less-than", "<"
	).put(
		"less-than-equals", "<="
	).build();

}