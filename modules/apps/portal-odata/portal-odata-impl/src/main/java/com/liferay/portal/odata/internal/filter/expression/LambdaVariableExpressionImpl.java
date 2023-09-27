/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaVariableExpression;

/**
 * @author Rubén Pulido
 */
public class LambdaVariableExpressionImpl implements LambdaVariableExpression {

	public LambdaVariableExpressionImpl(String variable) {
		_variable = variable;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitLambdaVariableExpression(this);
	}

	@Override
	public String getVariableName() {
		return _variable;
	}

	@Override
	public String toString() {
		return _variable;
	}

	private final String _variable;

}