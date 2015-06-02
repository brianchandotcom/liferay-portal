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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.portal.expression.Expression;
import com.liferay.portal.expression.ExpressionEvaluationException;
import com.liferay.portal.expression.ExpressionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Marcellus Tavares
 */
public class ExpressionEvaluator {

	public ExpressionEvaluator(ExpressionFactory expressionFactory) {
		_expressionFactory = expressionFactory;
	}

	public boolean evaluateBooleanExpression(String booleanExpression) {
		try {
			Expression<Boolean> expression =
				_expressionFactory.createBooleanExpression(booleanExpression);

			return expression.evaluate();
		}
		catch (ExpressionEvaluationException eee) {
			_log.error(
				"Unable to evaluate expression " + booleanExpression, eee);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpressionEvaluator.class);

	private final ExpressionFactory _expressionFactory;

}