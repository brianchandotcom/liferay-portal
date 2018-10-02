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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor;

import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArrayExpression;
import com.liferay.dynamic.data.mapping.expression.model.BinaryExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.ExpressionVisitor;
import com.liferay.dynamic.data.mapping.expression.model.FloatingPointLiteral;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.IntegerLiteral;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.StringLiteral;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleCondition;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * @author Rafael Praxedes
 */
public class ConditionExpressionVisitor extends ExpressionVisitor<Object> {

	public List<DDMFormRuleCondition> getConditions() {
		return _conditions;
	}

	public String getLogicalOperator() {
		if (_andOperator) {
			return "AND";
		}

		return "OR";
	}

	@Override
	public Object visit(AndExpression andExpression) {
		_andOperator = true;

		return doVisitLogicalExpression(andExpression);
	}

	@Override
	public Object visit(ArrayExpression arrayExpression) {
		String value = arrayExpression.getValue();

		return new DDMFormRuleCondition.Operand(
			"list", value.replaceAll("\\[|\\]|'", StringPool.BLANK));
	}

	@Override
	public Object visit(ComparisonExpression comparisonExpression) {
		DDMFormRuleCondition.Operand leftOperand = doVisit(
			comparisonExpression.getLeftOperandExpression());
		DDMFormRuleCondition.Operand rightOperand = doVisit(
			comparisonExpression.getRightOperandExpression());

		DDMFormRuleCondition ddmFormRuleCondition = new DDMFormRuleCondition(
			_operatorMap.get(comparisonExpression.getOperator()),
			Arrays.asList(leftOperand, rightOperand));

		_conditions.push(ddmFormRuleCondition);

		return _conditions;
	}

	@Override
	public Object visit(FloatingPointLiteral floatingPointLiteral) {
		return new DDMFormRuleCondition.Operand(
			"double", floatingPointLiteral.getValue());
	}

	@Override
	public Object visit(FunctionCallExpression functionCallExpression) {
		String functionName = functionCallExpression.getFunctionName();

		List<Expression> parameterExpressions =
			functionCallExpression.getParameterExpressions();

		if (Objects.equals(functionName, "getValue")) {
			DDMFormRuleCondition.Operand operand = doVisit(
				parameterExpressions.get(0));

			return new DDMFormRuleCondition.Operand(
				"field", operand.getValue());
		}

		List<DDMFormRuleCondition.Operand> operands = new ArrayList<>();

		for (Expression parameterExpression : parameterExpressions) {
			operands.add(
				(DDMFormRuleCondition.Operand)doVisit(parameterExpression));
		}

		_conditions.push(createDDMFormRuleCondition(functionName, operands));

		return _conditions;
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		return new DDMFormRuleCondition.Operand(
			"integer", integerLiteral.getValue());
	}

	@Override
	public Object visit(NotExpression notExpression) {
		doVisit(notExpression.getOperandExpression());

		DDMFormRuleCondition condition = _conditions.peek();

		String operator = condition.getOperator();

		condition.setOperator("not-" + operator);

		return _conditions;
	}

	@Override
	public Object visit(OrExpression orExpression) {
		_andOperator = false;

		return doVisitLogicalExpression(orExpression);
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		return new DDMFormRuleCondition.Operand(
			"string", stringLiteral.getValue());
	}

	protected DDMFormRuleCondition createDDMFormRuleCondition(
		String functionName, List<DDMFormRuleCondition.Operand> operands) {

		String functionNameOperator = _functionNameOperatorMap.get(
			functionName);

		return new DDMFormRuleCondition(functionNameOperator, operands);
	}

	protected <T> T doVisit(Expression expression) {
		return (T)expression.accept(this);
	}

	protected List<DDMFormRuleCondition> doVisitLogicalExpression(
		BinaryExpression binaryExpression) {

		Object o1 = doVisit(binaryExpression.getLeftOperandExpression());
		Object o2 = doVisit(binaryExpression.getRightOperandExpression());

		if (o1 instanceof DDMFormRuleCondition) {
			_conditions.push((DDMFormRuleCondition)o1);
		}

		if (o2 instanceof DDMFormRuleCondition) {
			_conditions.push((DDMFormRuleCondition)o2);
		}

		return _conditions;
	}

	private static final Map<String, String> _functionNameOperatorMap =
		new HashMap<String, String>() {
			{
				put("belongsTo", "belongs-to");
				put("contains", "contains");
				put("equals", "equals-to");
				put("isEmpty", "is-empty");
			}
		};
	private static final Map<String, String> _operatorMap =
		new HashMap<String, String>() {
			{
				put("<", "less-than");
				put("<=", "less-than-equals");
				put(">", "greater-than");
				put(">=", "greater-than-equals");
			}
		};

	private boolean _andOperator = true;
	private final Stack<DDMFormRuleCondition> _conditions = new Stack<>();

}