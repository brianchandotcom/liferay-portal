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

package com.liferay.dynamic.data.mapping.form.evaluator.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=max",
	service = DDMExpressionFunction.class
)
public class MaxFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length < 2) {
			throw new IllegalArgumentException(
				"Two or more parameters are expected");
		}

		double max = Double.MIN_VALUE;

		for (Object parameter : parameters) {
			if (!Number.class.isInstance(parameter)) {
				throw new IllegalArgumentException(
					"The parameters should be numbers");
			}

			double parameterDouble = ((Number)parameter).doubleValue();

			if (parameterDouble > max) {
				max = parameterDouble;
			}
		}

		return max;
	}

}