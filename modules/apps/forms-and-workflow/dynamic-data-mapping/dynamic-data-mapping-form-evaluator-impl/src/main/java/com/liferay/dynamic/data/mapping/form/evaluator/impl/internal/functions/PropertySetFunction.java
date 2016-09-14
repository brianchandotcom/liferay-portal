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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;

/**
 * @author Leonardo Barros
 */
public class PropertySetFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length < 3) {
			throw new IllegalArgumentException(
				"Three or more parameters are expected");
		}

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)parameters[0];

		String propertyName = parameters[1].toString();

		ddmFormFieldEvaluationResult.setProperty(propertyName, parameters[2]);

		if (propertyName.equals("valid") && (parameters.length > 3)) {
			ddmFormFieldEvaluationResult.setErrorMessage(
				parameters[3].toString());
		}

		return true;
	}

}