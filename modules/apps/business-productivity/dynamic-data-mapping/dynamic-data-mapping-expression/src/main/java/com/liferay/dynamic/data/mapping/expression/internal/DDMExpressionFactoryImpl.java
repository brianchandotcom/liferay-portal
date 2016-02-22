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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMExpressionFactory.class)
public class DDMExpressionFactoryImpl implements DDMExpressionFactory {

	@Override
	public DDMExpression<Boolean> createBooleanDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, Boolean.class);
	}

	@Override
	public DDMExpression<Double> createDoubleDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, Double.class);
	}

	@Override
	public DDMExpression<Float> createFloatDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, Float.class);
	}

	@Override
	public DDMExpression<Integer> createIntegerDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, Integer.class);
	}

	@Override
	public DDMExpression<Long> createLongDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, Long.class);
	}

	@Override
	public DDMExpression<String> createStringDDMExpression(
		String expressionString) {

		return new DDMExpressionImpl<>(expressionString, String.class);
	}

}