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
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;

/**
 * @author Rubén Pulido
 */
public class LambdaFunctionExpressionImpl implements LambdaFunctionExpression {

	public LambdaFunctionExpressionImpl(
		Type type, String variableName, Expression expression) {

		_type = type;
		_variableName = variableName;
		_expression = expression;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitLambdaFunctionExpression(
			_type, _variableName, _expression);
	}

	@Override
	public Expression getExpression() {
		return _expression;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getVariableName() {
		return _variableName;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			_type, "(", _variableName, " -> ", _expression.toString(), ")");
	}

	private final Expression _expression;
	private final Type _type;
	private final String _variableName;

}