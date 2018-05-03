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

package com.liferay.dynamic.data.mapping.expression;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDMExpressionFactory {

	public DDMExpression<Boolean> createBooleanDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<Double> createDoubleDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<Float> createFloatDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<Integer> createIntegerDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<Long> createLongDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<Number> createNumberDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

	public DDMExpression<String> createStringDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException;

}