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
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleCondition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * @author Rafael Praxedes
 */
public class ConditionExpressionVisitor extends ExpressionVisitor<Object> {

	public String getLogicalOperator() {
		if (_andOperator) {
			return "AND";
		}

		return "OR";
	}

	public List<SPIDDMFormRuleCondition> getSPIDDMFormRuleConditions() {
		return _spiDDMFormRuleConditions;
	}

	@Override
	public Object visit(AndExpression andExpression) {
		_andOperator = true;

		return doVisitLogicalExpression(andExpression);
	}

	@Override
	public Object visit(ArrayExpression arrayExpression) {
		String value = arrayExpression.getValue();

		return new SPIDDMFormRuleCondition.Operand(
			"list", value.replaceAll("\\[|\\]|'", StringPool.BLANK));
	}

	@Override
	public Object visit(ComparisonExpression comparisonExpression) {
		SPIDDMFormRuleCondition.Operand leftOperand = doVisit(
			comparisonExpression.getLeftOperandExpression());
		SPIDDMFormRuleCondition.Operand rightOperand = doVisit(
			comparisonExpression.getRightOperandExpression());

		SPIDDMFormRuleCondition spiDDMFormRuleCondition =
			new SPIDDMFormRuleCondition(
				_operatorMap.get(comparisonExpression.getOperator()),
				Arrays.asList(leftOperand, rightOperand));

		_spiDDMFormRuleConditions.push(spiDDMFormRuleCondition);

		return _spiDDMFormRuleConditions;
	}

	@Override
	public Object visit(FloatingPointLiteral floatingPointLiteral) {
		return new SPIDDMFormRuleCondition.Operand(
			"double", floatingPointLiteral.getValue());
	}

	@Override
	public Object visit(FunctionCallExpression functionCallExpression) {
		String functionName = functionCallExpression.getFunctionName();

		List<Expression> parameterExpressions =
			functionCallExpression.getParameterExpressions();

		if (Objects.equals(functionName, "getValue")) {
			SPIDDMFormRuleCondition.Operand operand = doVisit(
				parameterExpressions.get(0));

			return new SPIDDMFormRuleCondition.Operand(
				"field", operand.getValue());
		}

		List<SPIDDMFormRuleCondition.Operand> operands = new ArrayList<>();

		for (Expression parameterExpression : parameterExpressions) {
			if (functionCallExpression.hasNestedFunctions()) {
				operands.add(
					new SPIDDMFormRuleCondition.Operand(
						"condition", parameterExpression.toString()));
			}
			else {
				operands.add(
					(SPIDDMFormRuleCondition.Operand)doVisit(
						parameterExpression));
			}
		}

		_spiDDMFormRuleConditions.push(
			createDDMFormRuleCondition(functionName, operands));

		return _spiDDMFormRuleConditions;
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		return new SPIDDMFormRuleCondition.Operand(
			"integer", integerLiteral.getValue());
	}

	@Override
	public Object visit(NotExpression notExpression) {
		doVisit(notExpression.getOperandExpression());

		SPIDDMFormRuleCondition spiDDMFormRuleCondition =
			_spiDDMFormRuleConditions.peek();

		String operator = spiDDMFormRuleCondition.getOperator();

		spiDDMFormRuleCondition.setOperator("not-" + operator);

		return _spiDDMFormRuleConditions;
	}

	@Override
	public Object visit(OrExpression orExpression) {
		_andOperator = false;

		return doVisitLogicalExpression(orExpression);
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		return new SPIDDMFormRuleCondition.Operand(
			"string", stringLiteral.getValue());
	}

	@Override
	public Object visit(Term term) {
		return new SPIDDMFormRuleCondition.Operand("field", term.getValue());
	}

	protected SPIDDMFormRuleCondition createDDMFormRuleCondition(
		String functionName, List<SPIDDMFormRuleCondition.Operand> operands) {

		String functionNameOperator = _functionNameOperatorMap.get(
			functionName);

		return new SPIDDMFormRuleCondition(functionNameOperator, operands);
	}

	protected <T> T doVisit(Expression expression) {
		return (T)expression.accept(this);
	}

	protected List<SPIDDMFormRuleCondition> doVisitLogicalExpression(
		BinaryExpression binaryExpression) {

		Object o1 = doVisit(binaryExpression.getLeftOperandExpression());
		Object o2 = doVisit(binaryExpression.getRightOperandExpression());

		if (o1 instanceof SPIDDMFormRuleCondition) {
			_spiDDMFormRuleConditions.push((SPIDDMFormRuleCondition)o1);
		}

		if (o2 instanceof SPIDDMFormRuleCondition) {
			_spiDDMFormRuleConditions.push((SPIDDMFormRuleCondition)o2);
		}

		return _spiDDMFormRuleConditions;
	}

	private static final Map<String, String> _functionNameOperatorMap =
		HashMapBuilder.put(
			"belongsTo", "belongs-to"
		).put(
			"contains", "contains"
		).put(
			"equals", "equals-to"
		).put(
			"isEmpty", "is-empty"
		).build();
	private static final Map<String, String> _operatorMap = HashMapBuilder.put(
		"<", "less-than"
	).put(
		"<=", "less-than-equals"
	).put(
		">", "greater-than"
	).put(
		">=", "greater-than-equals"
	).build();

	private boolean _andOperator = true;
	private final Stack<SPIDDMFormRuleCondition> _spiDDMFormRuleConditions =
		new Stack<>();

}