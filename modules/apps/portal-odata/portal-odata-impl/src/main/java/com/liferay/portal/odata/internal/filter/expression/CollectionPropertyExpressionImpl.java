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

import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;

/**
 * @author Ruben Pulido
 */
public class CollectionPropertyExpressionImpl
	implements CollectionPropertyExpression {

	public CollectionPropertyExpressionImpl(
		PropertyExpression propertyExpression,
		LambdaFunctionExpression lambdaFunctionExpression) {

		_propertyExpression = propertyExpression;
		_lambdaFunctionExpression = lambdaFunctionExpression;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitCollectionPropertyExpression(this);
	}

	@Override
	public LambdaFunctionExpression getLambdaFunctionExpression() {
		return _lambdaFunctionExpression;
	}

	@Override
	public String getName() {
		return _propertyExpression.getName();
	}

	@Override
	public PropertyExpression getPropertyExpression() {
		return _propertyExpression;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(2);

		sb.append(_propertyExpression.toString());
		sb.append("/");
		sb.append(_lambdaFunctionExpression.toString());

		return sb.toString();
	}

	private final LambdaFunctionExpression _lambdaFunctionExpression;
	private final PropertyExpression _propertyExpression;

}