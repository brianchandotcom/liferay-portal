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
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleCondition;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleSerializerContext;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ConditionExpressionVisitor;
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
@Component(immediate = true, service = DDMFormRuleConverter.class)
public class DDMFormRuleConverter {

	public List<DDMFormRule> convert(
		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules) {

		List<DDMFormRule> convertedDDMFormRules = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMFormRule ddmFormRule :
				ddmFormRules) {

			convertedDDMFormRules.add(convertRule(ddmFormRule));
		}

		return convertedDDMFormRules;
	}

	public List<com.liferay.dynamic.data.mapping.model.DDMFormRule> convert(
		List<DDMFormRule> ddmFormRules,
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		Stream<com.liferay.dynamic.data.mapping.model.DDMFormRule>
			convertedFormRulesStream = stream.map(
				formRule -> convertRule(
					formRule, ddmFormRuleSerializerContext));

		return convertedFormRulesStream.collect(Collectors.toList());
	}

	protected DDMFormRuleAction convertAction(String actionExpressionString) {
		Expression actionExpression = createExpression(actionExpressionString);

		ActionExpressionVisitor actionExpressionVisitor =
			new ActionExpressionVisitor();

		return (DDMFormRuleAction)actionExpression.accept(
			actionExpressionVisitor);
	}

	protected String convertCondition(
		DDMFormRuleCondition ddmFormRuleCondition) {

		String operator = ddmFormRuleCondition.getOperator();

		String functionName = _operatorFunctionNameMap.get(operator);

		List<DDMFormRuleCondition.Operand> operands =
			ddmFormRuleCondition.getOperands();

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
		List<DDMFormRuleCondition> ddmFormRuleConditions) {

		if (ddmFormRuleConditions.size() == 1) {
			return convertCondition(ddmFormRuleConditions.get(0));
		}

		StringBundler sb = new StringBundler(ddmFormRuleConditions.size() * 4);

		for (DDMFormRuleCondition ddmFormRuleCondition :
				ddmFormRuleConditions) {

			sb.append(convertCondition(ddmFormRuleCondition));
			sb.append(StringPool.SPACE);
			sb.append(logicalOperator);
			sb.append(StringPool.SPACE);
		}

		sb.setIndex(sb.index() - 3);

		return sb.toString();
	}

	protected String convertOperand(DDMFormRuleCondition.Operand operand) {
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
		List<DDMFormRuleCondition.Operand> operands) {

		StringBundler sb = new StringBundler(operands.size());

		for (DDMFormRuleCondition.Operand operand : operands) {
			sb.append(convertOperand(operand));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected DDMFormRule convertRule(
		com.liferay.dynamic.data.mapping.model.DDMFormRule ddmFormRule) {

		DDMFormRule convertedDDMFormRule = new DDMFormRule();

		setDDMFormRuleConditions(
			convertedDDMFormRule, ddmFormRule.getCondition());
		setDDMFormRuleActions(convertedDDMFormRule, ddmFormRule.getActions());

		return convertedDDMFormRule;
	}

	protected com.liferay.dynamic.data.mapping.model.DDMFormRule convertRule(
		DDMFormRule ddmFormRule,
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		String condition = convertConditions(
			ddmFormRule.getLogicalOperator(),
			ddmFormRule.getDDMFormRuleConditions());

		List<String> actions = new ArrayList<>();

		for (DDMFormRuleAction ddmFormRuleAction :
				ddmFormRule.getDDMFormRuleActions()) {

			actions.add(
				ddmFormRuleAction.serialize(ddmFormRuleSerializerContext));
		}

		return new com.liferay.dynamic.data.mapping.model.DDMFormRule(
			condition, actions);
	}

	protected String createCondition(
		String functionName, List<DDMFormRuleCondition.Operand> operands) {

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
		catch (DDMExpressionException ddmee) {
			throw new IllegalStateException(
				String.format(
					"Unable to parse expression \"%s\"", expressionString),
				ddmee);
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

	protected void setDDMFormRuleActions(
		DDMFormRule ddmFormRule, List<String> actions) {

		List<DDMFormRuleAction> ddmFormRuleActions = new ArrayList<>();

		for (String action : actions) {
			ddmFormRuleActions.add(convertAction(action));
		}

		ddmFormRule.setDDMFormRuleActions(ddmFormRuleActions);
	}

	protected void setDDMFormRuleConditions(
		DDMFormRule ddmFormRule, String conditionExpressionString) {

		Expression conditionExpression = createExpression(
			conditionExpressionString);

		ConditionExpressionVisitor conditionExpressionVisitor =
			new ConditionExpressionVisitor();

		conditionExpression.accept(conditionExpressionVisitor);

		ddmFormRule.setDDMFormRuleConditions(
			conditionExpressionVisitor.getConditions());
		ddmFormRule.setLogicalOperator(
			conditionExpressionVisitor.getLogicalOperator());
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

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