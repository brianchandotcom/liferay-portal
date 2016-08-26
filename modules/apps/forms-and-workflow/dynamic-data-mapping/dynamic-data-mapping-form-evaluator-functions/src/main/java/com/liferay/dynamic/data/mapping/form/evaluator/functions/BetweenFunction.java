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
	immediate = true, property = "ddm.form.evaluator.function.name=between",
	service = DDMExpressionFunction.class
)
public class BetweenFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length != 3) {
			throw new IllegalArgumentException("Three parameters are expected");
		}

		if (!Number.class.isInstance(parameters[0]) ||
			!Number.class.isInstance(parameters[1]) ||
			!Number.class.isInstance(parameters[2])) {

			throw new IllegalArgumentException(
				"The parameters should be numbers");
		}

		Number parameter = (Number)parameters[0];

		Number minParameter = (Number)parameters[1];
		Number maxParameter = (Number)parameters[2];

		if ((parameter.doubleValue() >= minParameter.doubleValue()) &&
			(parameter.doubleValue() <= maxParameter.doubleValue())) {

			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

}