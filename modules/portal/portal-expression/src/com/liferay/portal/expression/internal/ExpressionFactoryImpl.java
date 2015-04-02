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

package com.liferay.portal.expression.internal;

import com.liferay.portal.expression.Expression;
import com.liferay.portal.expression.ExpressionFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ExpressionFactory.class)
public class ExpressionFactoryImpl implements ExpressionFactory {

	@Override
	public Expression<Boolean> createBooleanExpression(
		String expressionString) {

		return new ExpressionImpl<>(expressionString, Boolean.class);
	}

	@Override
	public Expression<Double> createDoubleExpression(String expressionString) {
		return new ExpressionImpl<>(expressionString, Double.class);
	}

	@Override
	public <T> Expression<T> createExpression(
		String expressionString, Class<T> expressionType) {

		return new ExpressionImpl<>(expressionString, expressionType);
	}

	@Override
	public Expression<Float> createFloatExpression(String expressionString) {
		return new ExpressionImpl<>(expressionString, Float.class);
	}

	@Override
	public Expression<Integer> createIntegerExpression(
		String expressionString) {

		return new ExpressionImpl<>(expressionString, Integer.class);
	}

	@Override
	public Expression<Long> createLongExpression(String expressionString) {
		return new ExpressionImpl<>(expressionString, Long.class);
	}

	@Override
	public Expression<String> createStringExpression(String expressionString) {
		return new ExpressionImpl<>(expressionString, String.class);
	}

}