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

import java.math.BigDecimal;

/**
 * @author Leonardo Barros
 */
public class BetweenFunction
	implements DDMExpressionFunction.Function3
		<BigDecimal, BigDecimal, BigDecimal, Boolean> {

	public static final String NAME = "between";

	@Override
	public Boolean apply(
		BigDecimal bigDecimal1, BigDecimal bigDecimal2,
		BigDecimal bigDecimal3) {

		if ((bigDecimal1.compareTo(bigDecimal2) >= 0) &&
			(bigDecimal1.compareTo(bigDecimal3) <= 0)) {

			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

}