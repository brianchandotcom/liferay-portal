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

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

/**
 * @author Cristina González
 */
public class UnaryExpressionImpl implements UnaryExpression {

	public UnaryExpressionImpl(Expression expression, Operation operation) {
		_expression = expression;
		_operation = operation;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitUnaryExpressionOperation(
			_operation, _expression.accept(expressionVisitor));
	}

	@Override
	public Expression getExpression() {
		return _expression;
	}

	@Override
	public Operation getOperation() {
		return _operation;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			StringPool.OPEN_CURLY_BRACE, _operation.name(),
			StringPool.OPEN_PARENTHESIS, _expression,
			StringPool.CLOSE_PARENTHESIS, StringPool.CLOSE_CURLY_BRACE);
	}

	private final Expression _expression;
	private final Operation _operation;

}