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

package com.liferay.dynamic.data.mapping.expression.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class MultiplyFunction
	implements DDMExpressionFunction.Function1<BigDecimal[], BigDecimal> {

	@Override
	public BigDecimal apply(BigDecimal[] numbers) {
		Stream<BigDecimal> stream = Arrays.stream(numbers);

		return stream.reduce(BigDecimal.ONE, (n1, n2) -> n1.multiply(n2));
	}

	@Override
	public String getName() {
		return "multiply";
	}

}