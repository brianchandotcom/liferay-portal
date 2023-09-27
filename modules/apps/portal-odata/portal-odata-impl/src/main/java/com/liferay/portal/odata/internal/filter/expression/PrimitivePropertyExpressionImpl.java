/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;

/**
 * @author Rubén Pulido
 */
public class PrimitivePropertyExpressionImpl
	implements PrimitivePropertyExpression {

	public PrimitivePropertyExpressionImpl(String name) {
		_name = name;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitPrimitivePropertyExpression(this);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		return _name;
	}

	private final String _name;

}