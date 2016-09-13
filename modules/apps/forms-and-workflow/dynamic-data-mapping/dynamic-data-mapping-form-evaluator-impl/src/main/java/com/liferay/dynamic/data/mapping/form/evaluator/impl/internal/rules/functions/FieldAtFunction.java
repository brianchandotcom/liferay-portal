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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.rules.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

/**
 * @author Leonardo Barros
 */
public class FieldAtFunction implements DDMExpressionFunction {

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length != 2) {
			throw new IllegalArgumentException(
				String.format(
					"Expected 2 parameters, received %d", parameters.length));
		}

		String fieldName = parameters[0].toString();
		int index = Double.valueOf(parameters[1].toString()).intValue();

		return String.format("%s#%d", fieldName, index);
	}

}