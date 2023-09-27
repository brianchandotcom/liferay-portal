/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.ListExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristina González
 */
public class ListExpressionImpl implements ListExpression {

	public ListExpressionImpl(
		Expression leftOperationExpression, Operation operation,
		List<Expression> rightOperationExpressions) {

		_leftOperationExpression = leftOperationExpression;
		_operation = operation;
		_rightOperationExpressions = rightOperationExpressions;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		List<T> visitedRightOperationExpressions = new ArrayList<>();

		for (Expression rightOperationExpression : _rightOperationExpressions) {
			visitedRightOperationExpressions.add(
				rightOperationExpression.accept(expressionVisitor));
		}

		return expressionVisitor.visitListExpressionOperation(
			_operation, _leftOperationExpression.accept(expressionVisitor),
			visitedRightOperationExpressions);
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
	public List<Expression> getRightOperationExpressions() {
		return _rightOperationExpressions;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{", _leftOperationExpression, " ", _operation.name(), " ",
			_rightOperationExpressions, '}');
	}

	private final Expression _leftOperationExpression;
	private final Operation _operation;
	private final List<Expression> _rightOperationExpressions;

}