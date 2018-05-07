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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.evaluator.function.available.on.calculation.rule=true",
		"ddm.form.evaluator.function.name=sum"
	},
	service = DDMExpressionFunction.class
)
public class SumFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		Object[] values = null;

		if ((parameters.length == 1) && isArray(parameters[0])) {
			values = (Object[])parameters[0];
		}
		else {
			values = parameters;
		}

		double sum = 0;

		boolean integerSum = true;

		for (Object value : values) {
			if (!Number.class.isInstance(value)) {
				continue;
			}

			if (!Integer.class.isInstance(value)) {
				integerSum = false;
			}

			Number number = (Number)value;

			sum += number.doubleValue();
		}

		if (integerSum) {
			return (int)sum;
		}

		return sum;
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

}