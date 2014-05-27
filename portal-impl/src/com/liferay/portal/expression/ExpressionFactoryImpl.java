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

package com.liferay.portal.expression;

import com.liferay.portal.kernel.expression.Expression;
import com.liferay.portal.kernel.expression.ExpressionFactory;

/**
 * @author Marcellus Tavares
 */
public class ExpressionFactoryImpl implements ExpressionFactory {

	@Override
	public Expression<Boolean> createBooleanExpression(
		String expressionString) {

		return new ExpressionImpl<Boolean>(expressionString, Boolean.class);
	}

	@Override
	public Expression<Double> createDoubleExpression(String expressionString) {
		return new ExpressionImpl<Double>(expressionString, Double.class);
	}

	@Override
	public <T> Expression<T> createExpression(
		String expressionString, Class<T> expressionType) {

		return new ExpressionImpl<T>(expressionString, expressionType);
	}

	@Override
	public Expression<Float> createFloatExpression(String expressionString) {
		return new ExpressionImpl<Float>(expressionString, Float.class);
	}

	@Override
	public Expression<Integer> createIntegerExpression(
		String expressionString) {

		return new ExpressionImpl<Integer>(expressionString, Integer.class);
	}

	@Override
	public Expression<Long> createLongExpression(String expressionString) {
		return new ExpressionImpl<Long>(expressionString, Long.class);
	}

	@Override
	public Expression<String> createStringExpression(String expressionString) {
		return new ExpressionImpl<String>(expressionString, String.class);
	}

}