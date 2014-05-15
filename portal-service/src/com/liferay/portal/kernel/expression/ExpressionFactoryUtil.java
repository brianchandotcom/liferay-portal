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

package com.liferay.portal.kernel.expression;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Marcellus Tavares
 */
public class ExpressionFactoryUtil {

	public static Expression<Boolean> createBooleanExpression(
		String expression) {

		return getExpressionFactory().createBooleanExpression(expression);
	}

	public static Expression<Double> createDoubleExpression(String expression) {
		return getExpressionFactory().createDoubleExpression(expression);
	}

	public static <T> Expression<T> createExpression(
		String expression, Class<T> expressionType) {

		return getExpressionFactory().createExpression(
			expression, expressionType);
	}

	public static Expression<Float> createFloatExpression(String expression) {
		return getExpressionFactory().createFloatExpression(expression);
	}

	public static Expression<Integer> createIntegerExpression(
		String expression) {

		return getExpressionFactory().createIntegerExpression(expression);
	}

	public static Expression<Long> createLongExpression(String expression) {
		return getExpressionFactory().createLongExpression(expression);
	}

	public static Expression<String> createStringExpression(String expression) {
		return getExpressionFactory().createStringExpression(expression);
	}

	public static ExpressionFactory getExpressionFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ExpressionFactoryUtil.class);

		return _expressionFactory;
	}

	public void setExpressionFactory(ExpressionFactory expressionFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_expressionFactory = expressionFactory;
	}

	private static ExpressionFactory _expressionFactory;

}