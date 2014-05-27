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
	public Expression<Boolean> createBooleanExpression(String expression) {
		return new ExpressionImpl<Boolean>(expression, Boolean.class);
	}

	@Override
	public Expression<Double> createDoubleExpression(String expression) {
		return new ExpressionImpl<Double>(expression, Double.class);
	}

	@Override
	public <T> Expression<T> createExpression(
		String expression, Class<T> expressionType) {

		return new ExpressionImpl<T>(expression, expressionType);
	}

	@Override
	public Expression<Float> createFloatExpression(String expression) {
		return new ExpressionImpl<Float>(expression, Float.class);
	}

	@Override
	public Expression<Integer> createIntegerExpression(String expression) {
		return new ExpressionImpl<Integer>(expression, Integer.class);
	}

	@Override
	public Expression<Long> createLongExpression(String expression) {
		return new ExpressionImpl<Long>(expression, Long.class);
	}

	@Override
	public Expression<String> createStringExpression(String expression) {
		return new ExpressionImpl<String>(expression, String.class);
	}

}