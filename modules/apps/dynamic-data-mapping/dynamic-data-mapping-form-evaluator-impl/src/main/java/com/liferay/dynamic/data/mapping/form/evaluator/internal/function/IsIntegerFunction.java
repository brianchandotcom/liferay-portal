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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author Leonardo Barros
 */
public class IsIntegerFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "isInteger";

	@Override
	public Boolean apply(Object parameter) {
		Integer value = NumberUtils.toInt(
			parameter.toString(), Integer.MIN_VALUE);

		return value != Integer.MIN_VALUE;
	}

	@Override
	public String getName() {
		return NAME;
	}

}