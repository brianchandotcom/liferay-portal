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

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class MaxFunction
	implements DDMExpressionFunction.Function1<BigDecimal[], BigDecimal> {

	public static final String NAME = "MAX";

	@Override
	public BigDecimal apply(BigDecimal[] values) {
		return Stream.of(
			values
		).collect(
			Collectors.maxBy((num1, num2) -> num1.compareTo(num2))
		).orElse(
			BigDecimal.ZERO
		);
	}

	@Override
	public String getName() {
		return NAME;
	}

}