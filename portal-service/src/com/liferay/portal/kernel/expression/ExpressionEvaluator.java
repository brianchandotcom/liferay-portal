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

import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface ExpressionEvaluator {

	public Boolean evaluateBooleanExpression(String expression)
		throws ExpressionEvaluationException;

	public Double evaluateDoubleExpression(String expression)
		throws ExpressionEvaluationException;

	public Object evaluateExpression(String expression, Class<?> returnedType)
		throws ExpressionEvaluationException;

	public Float evaluateFloatExpression(String expression)
		throws ExpressionEvaluationException;

	public Integer evaluateIntegerExpression(String expression)
		throws ExpressionEvaluationException;

	public Long evaluateLongExpression(String expression)
		throws ExpressionEvaluationException;

	public String evaluateStringExpression(String expression)
		throws ExpressionEvaluationException;

	public Map<String, VariableDependencies> getVariableDependenciesMap();

}