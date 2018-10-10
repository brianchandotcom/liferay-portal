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

package com.liferay.portal.filtering.odata.internal.architect.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.filtering.odata.architect.filter.expression.BinaryExpression;
import com.liferay.portal.filtering.odata.architect.filter.expression.Expression;
import com.liferay.portal.filtering.odata.architect.filter.expression.ExpressionVisitException;
import com.liferay.portal.filtering.odata.architect.filter.expression.ExpressionVisitor;

/**
 * @author Cristina González
 */
public class BinaryExpressionImpl implements BinaryExpression {

	public BinaryExpressionImpl(
		Expression leftOperationExpression, Operation operation,
		Expression rightOperationExpression) {

		_leftOperationExpression = leftOperationExpression;
		_operation = operation;
		_rightOperationExpression = rightOperationExpression;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitBinaryExpressionOperation(
			_operation, _leftOperationExpression.accept(expressionVisitor),
			_rightOperationExpression.accept(expressionVisitor));
	}

	@Override
	public Expression getLeftOperationExpression() {
		return _leftOperationExpression;
	}

	@Override
	public Operation getOperation() {
		return _operation;
	}

	@Override
	public Expression getRightOperationExpression() {
		return _rightOperationExpression;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{", _leftOperationExpression, " ", _operation.name(), " ",
			_rightOperationExpression, '}');
	}

	private final Expression _leftOperationExpression;
	private final Operation _operation;
	private final Expression _rightOperationExpression;

}