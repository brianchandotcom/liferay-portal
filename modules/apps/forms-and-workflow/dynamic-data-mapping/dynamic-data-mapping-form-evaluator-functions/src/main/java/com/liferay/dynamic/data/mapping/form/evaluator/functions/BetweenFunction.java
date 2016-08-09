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
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=between",
	service = DDMExpressionFunction.class
)
public class BetweenFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length != 3) {
			throw new IllegalArgumentException(
				String.format(
					"Expected 3 parameters, received %d", parameters.length));
		}

		if (Validator.isNull(parameters[0]) ||
			!Validator.isNumber(parameters[0].toString()) ||
			Validator.isNull(parameters[1]) ||
			!Validator.isNumber(parameters[1].toString()) ||
			Validator.isNull(parameters[2]) ||
			!Validator.isNumber(parameters[2].toString())) {

			return false;
		}

		double value1 = ((Number)parameters[0]).doubleValue();
		double value2 = ((Number)parameters[1]).doubleValue();
		double value3 = ((Number)parameters[2]).doubleValue();

		return Double.compare(value1, value2) >= 0 &&
			Double.compare(value1, value3) <= 0;
	}

}