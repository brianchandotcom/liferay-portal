/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.PropertyExpression;

/**
 * @author Rubén Pulido
 */
public class ComplexPropertyExpressionImpl
	implements ComplexPropertyExpression {

	public ComplexPropertyExpressionImpl(
		String name, PropertyExpression propertyExpression) {

		_name = name;
		_propertyExpression = propertyExpression;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitComplexPropertyExpression(this);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public PropertyExpression getPropertyExpression() {
		return _propertyExpression;
	}

	@Override
	public String toString() {
		return StringBundler.concat(_name, "/", _propertyExpression);
	}

	private final String _name;
	private final PropertyExpression _propertyExpression;

}