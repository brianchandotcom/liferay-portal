/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.MemberExpression;

/**
 * @author Cristina González
 */
public class MemberExpressionImpl implements MemberExpression {

	public MemberExpressionImpl(Expression expression) {
		_expression = expression;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitMemberExpression(this);
	}

	@Override
	public Expression getExpression() {
		return _expression;
	}

	@Override
	public String toString() {
		return _expression.toString();
	}

	private final Expression _expression;

}