/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;

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