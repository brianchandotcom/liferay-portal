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

import com.liferay.portal.kernel.expression.ExpressionEvaluationException;
import com.liferay.portal.kernel.util.MathUtil;

import org.codehaus.janino.ExpressionEvaluator;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluatorUtil {

	public static Object evaluate(
			String expression, Class<?> expressionType, String[] parameterNames,
			Class<?>[] parameterTypes, Object[] values)
		throws ExpressionEvaluationException {

		try {
			ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

			expressionEvaluator.setExpressionType(expressionType);
			expressionEvaluator.setParameters(parameterNames, parameterTypes);
			expressionEvaluator.setExtendedClass(MathUtil.class);
			expressionEvaluator.cook(expression);

			return expressionEvaluator.evaluate(values);
		}
		catch (Exception e) {
			throw new ExpressionEvaluationException(e);
		}
	}

}