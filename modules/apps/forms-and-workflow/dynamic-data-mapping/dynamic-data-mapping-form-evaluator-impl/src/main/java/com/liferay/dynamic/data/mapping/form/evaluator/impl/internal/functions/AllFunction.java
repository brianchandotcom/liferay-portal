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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.DDMExpressionFunctionRegister;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class AllFunction implements DDMExpressionFunction {

	public AllFunction(
		DDMExpressionFactory ddmExpressionFactory,
		DDMExpressionFunctionRegister ddmExpressionFunctionRegister) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmExpressionFunctionRegister = ddmExpressionFunctionRegister;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if ((parameters == null) || (parameters.length < 1)) {
			throw new IllegalArgumentException(
				"At least one parameter is expected");
		}

		if (parameters.length == 1) {
			return false;
		}

		String expression = String.valueOf(parameters[0]);

		if (!expression.contains("#value#")) {
			return false;
		}

		Object[] values = null;

		if (isArray(parameters[1])) {
			values = (Object[])parameters[1];

			if (values.length == 0) {
				return false;
			}
		}
		else {
			values = new Object[] {parameters[1]};
		}

		Stream<Object> stream = Stream.of(values);

		return stream.allMatch(value -> accept(expression, value));
	}

	protected boolean accept(String expression, Object value) {
		expression = expression.replace("#value#", String.valueOf(value));

		try {
			DDMExpression<Boolean> ddmExpression =
				_ddmExpressionFactory.createBooleanDDMExpression(expression);

			_ddmExpressionFunctionRegister.applyDDMExpressionFunctions(
				ddmExpression);

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee);
			}
		}

		return false;
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(AllFunction.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMExpressionFunctionRegister _ddmExpressionFunctionRegister;

}