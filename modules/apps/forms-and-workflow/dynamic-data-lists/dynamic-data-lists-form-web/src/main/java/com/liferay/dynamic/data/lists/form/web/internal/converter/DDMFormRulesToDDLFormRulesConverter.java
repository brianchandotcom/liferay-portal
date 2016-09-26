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
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.BinaryExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.ExpressionVisitor;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, service = DDMFormRulesToDDLFormRulesConverter.class
)
public class DDMFormRulesToDDLFormRulesConverter {

	public List<DDLFormRule> convert(List<DDMFormRule> ddmFormRules) {
		List<DDLFormRule> ddlFormRules = new ArrayList<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			ddlFormRules.add(convertRule(ddmFormRule));
		}

		return ddlFormRules;
	}

	protected DDLFormRuleAction convertAction(String actionExpressionString) {
		Expression actionExpression = createExpression(actionExpressionString);

		ActionExpressionVisitor actionExpressionVisitor =
			new ActionExpressionVisitor();

		return (DDLFormRuleAction)actionExpression.accept(
			actionExpressionVisitor);
	}

	protected DDLFormRule convertRule(DDMFormRule ddmFormRule) {
		DDLFormRule ddlFormRule = new DDLFormRule();

		setDDLFormRuleConditions(ddlFormRule, ddmFormRule.getCondition());
		setDDLFormRuleActions(ddlFormRule, ddmFormRule.getActions());

		return ddlFormRule;
	}

	protected Expression createExpression(String expressionString) {
		try {
			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createBooleanDDMExpression(
					expressionString);

			return ddmExpression.getModel();
		}
		catch (DDMExpressionException ddmee) {
			throw new IllegalStateException(
				String.format(
					"Unable to parse expression \"%s\"", expressionString),
				ddmee);
		}
	}

	protected void setDDLFormRuleActions(
		DDLFormRule ddlFormRule, List<String> actions) {

		List<DDLFormRuleAction> ddlFormRuleActions = new ArrayList<>();

		for (String action : actions) {
			ddlFormRuleActions.add(convertAction(action));
		}

		ddlFormRule.setDDLFormRuleActions(ddlFormRuleActions);
	}

	protected void setDDLFormRuleConditions(
		DDLFormRule ddlFormRule, String conditionExpressionString) {

		Expression conditionExpression = createExpression(
			conditionExpressionString);

		ConditionExpressionVisitor conditionExpressionVisitor =
			new ConditionExpressionVisitor();

		conditionExpression.accept(conditionExpressionVisitor);

		ddlFormRule.setDDLFormRuleConditions(
			conditionExpressionVisitor.getConditions());
		ddlFormRule.setLogicalOperator(
			conditionExpressionVisitor.getLogicalOperator());
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	private static class ActionExpressionVisitor
		extends ExpressionVisitor<Object> {

		@Override
		public Object visit(FunctionCallExpression functionCallExpression) {
			String action = _functionToActionMap.get(
				functionCallExpression.getFunctionName());

			List<Expression> parameters =
				functionCallExpression.getParameters();

			String target = doVisit(parameters.get(0));

			return new DDLFormRuleAction(action, target);
		}

		@Override
		public Object visit(Term term) {
			return term.getValue();
		}

		protected <T> T doVisit(Expression expression) {
			return (T)expression.accept(this);
		}

		private static final Map<String, String> _functionToActionMap =
			new HashMap<>();

		static {
			_functionToActionMap.put("setVisible", "show");
			_functionToActionMap.put("setEnabled", "enable");
			_functionToActionMap.put("setRequired", "require");
			_functionToActionMap.put("setInvalid", "invalidate");
		}

	}

	private static class ConditionExpressionVisitor
		extends ExpressionVisitor<Object> {

		public List<DDLFormRuleCondition> getConditions() {
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
		public Object visit(ComparisonExpression comparisonExpression) {
			DDLFormRuleCondition.Operand leftOperand = doVisit(
				comparisonExpression.getLeftOperand());
			DDLFormRuleCondition.Operand rightOperand = doVisit(
				comparisonExpression.getRightOperand());

			DDLFormRuleCondition ddlFormRuleCondition =
				new DDLFormRuleCondition(
					_operatorMap.get(comparisonExpression.getOperator()),
					Arrays.asList(leftOperand, rightOperand));

			_conditions.add(ddlFormRuleCondition);

			return _conditions;
		}

		@Override
		public Object visit(FunctionCallExpression functionCallExpression) {
			String functionName = functionCallExpression.getFunctionName();

			List<Expression> parameters =
				functionCallExpression.getParameters();

			if (Objects.equals(functionName, "getValue")) {
				DDLFormRuleCondition.Operand operand = doVisit(
					parameters.get(0));

				return new DDLFormRuleCondition.Operand(
					"field", operand.getValue());
			}

			List<DDLFormRuleCondition.Operand> operands = new ArrayList<>();

			for (Expression parameterExpression : parameters) {
				operands.add(
					(DDLFormRuleCondition.Operand)doVisit(parameterExpression));
			}

			DDLFormRuleCondition ddlFormRuleCondition =
				new DDLFormRuleCondition(
					_functionNameOperatorMap.get(functionName), operands);

			_conditions.add(ddlFormRuleCondition);

			return _conditions;
		}

		@Override
		public Object visit(NotExpression notExpression) {
			DDLFormRuleCondition condition = doVisit(
				notExpression.getOperand());

			String operator = condition.getOperator();

			condition.setOperator("not-" + operator);

			return condition;
		}

		@Override
		public Object visit(OrExpression orExpression) {
			_andOperator = false;

			return doVisitLogicalExpression(orExpression);
		}

		@Override
		public Object visit(Term term) {
			return new DDLFormRuleCondition.Operand(
				"constant", term.getValue());
		}

		protected <T> T doVisit(Expression expression) {
			return (T)expression.accept(this);
		}

		protected List<DDLFormRuleCondition> doVisitLogicalExpression(
			BinaryExpression binaryExpression) {

			Object o1 = doVisit(binaryExpression.getLeftOperand());
			Object o2 = doVisit(binaryExpression.getRightOperand());

			if (o1 instanceof DDLFormRuleCondition) {
				_conditions.add((DDLFormRuleCondition)o1);
			}

			if (o2 instanceof DDLFormRuleCondition) {
				_conditions.add((DDLFormRuleCondition)o2);
			}

			return _conditions;
		}

		private static final Map<String, String> _functionNameOperatorMap =
			new HashMap<>();
		private static final Map<String, String> _operatorMap = new HashMap<>();

		static {
			_operatorMap.put(">", "greater-than");
			_operatorMap.put(">=", "greater-than-equals");
			_operatorMap.put("<", "less-than");
			_operatorMap.put("<=", "less-than-equals");

			_functionNameOperatorMap.put("contains", "contains");
			_functionNameOperatorMap.put("equals", "equals-to");
		}

		private boolean _andOperator = true;
		private final List<DDLFormRuleCondition> _conditions =
			new ArrayList<>();

	}

}