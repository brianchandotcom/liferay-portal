/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_type, "(", _variableName, " -> ", _expression, ")");
	}

	private final Expression _expression;
	private final Type _type;
	private final String _variableName;

}