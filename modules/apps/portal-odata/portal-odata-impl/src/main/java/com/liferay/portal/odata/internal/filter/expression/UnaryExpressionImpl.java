/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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