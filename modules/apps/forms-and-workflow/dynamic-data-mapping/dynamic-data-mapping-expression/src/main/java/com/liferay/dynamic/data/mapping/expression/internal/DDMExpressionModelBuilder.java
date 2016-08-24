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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.BinaryExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.dynamic.data.mapping.expression.model.UnaryExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Leonardo Barros
 */
public class DDMExpressionModelBuilder {

	public DDMExpressionModelBuilder(
		DDMExpressionListener ddmExpressionListener) {

		_ddmExpressionListener = ddmExpressionListener;
	}

	public Expression build() {
		Stack<Expression> expressions =
			_ddmExpressionListener.getExpressionTokens();

		Stack<Expression> operands = new Stack<>();

		while (!expressions.isEmpty()) {
			processExpressionToken(operands, expressions.pop());
		}

		return operands.pop();
	}

	protected void processArithmeticExpression(
		ArithmeticExpression expression, Stack<Expression> operands) {

		expression.setLeftOperand(operands.pop());
		expression.setRightOperand(operands.pop());

		operands.push(expression);
	}

	protected void processBinaryExpression(
		BinaryExpression expression, Stack<Expression> operands) {

		expression.setLeftOperand(operands.pop());
		expression.setRightOperand(operands.pop());

		operands.push(expression);
	}

	protected void processComparisonExpression(
		ComparisonExpression expression, Stack<Expression> operands) {

		expression.setLeftOperand(operands.pop());
		expression.setRightOperand(operands.pop());

		operands.push(expression);
	}

	protected void processExpressionToken(
		Stack<Expression> operands, Expression token) {

		if (token instanceof Term) {
			operands.push(token);
		}
		else if(token instanceof ArithmeticExpression) {
			processArithmeticExpression((ArithmeticExpression)token, operands);
		}
		else if(token instanceof ComparisonExpression) {
			processComparisonExpression((ComparisonExpression)token, operands);
		}
		else if(token instanceof FunctionCallExpression) {
			processFunctionCallExpression(
				(FunctionCallExpression)token, operands);
		}
		else if(token instanceof UnaryExpression) {
			processUnaryExpression((UnaryExpression)token, operands);
		}
		else if(token instanceof BinaryExpression) {
			processBinaryExpression((BinaryExpression)token, operands);
		}
	}

	protected void processFunctionCallExpression(
		FunctionCallExpression expression, Stack<Expression> operands) {

		if (expression.getNumberOfParameters() > 0) {
			int numberOfParameters = expression.getNumberOfParameters();

			List<Expression> parameters = new ArrayList<>(numberOfParameters);

			while (numberOfParameters > 0) {
				parameters.add(operands.pop());
				numberOfParameters--;
			}

			expression.setParameters(parameters);
		}

		operands.push(expression);
	}

	protected void processUnaryExpression(
		UnaryExpression expression, Stack<Expression> operands) {

		expression.setOperand(operands.pop());

		operands.push(expression);
	}

	private final DDMExpressionListener _ddmExpressionListener;

}