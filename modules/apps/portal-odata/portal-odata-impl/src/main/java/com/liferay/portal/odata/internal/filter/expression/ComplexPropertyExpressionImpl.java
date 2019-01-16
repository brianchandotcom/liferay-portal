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
		return _name.concat(
			"/"
		).concat(
			_propertyExpression.toString()
		);
	}

	private final String _name;
	private final PropertyExpression _propertyExpression;

}