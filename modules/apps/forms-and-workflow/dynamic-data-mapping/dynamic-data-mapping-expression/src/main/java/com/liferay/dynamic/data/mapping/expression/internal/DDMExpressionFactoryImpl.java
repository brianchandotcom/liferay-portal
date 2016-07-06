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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMExpressionFactory.class)
public class DDMExpressionFactoryImpl implements DDMExpressionFactory {

	@Override
	public DDMExpression<Boolean> createBooleanDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Boolean.class);

		setDDMExpressionFunctions(ddmExpression);

		return ddmExpression;
	}

	@Override
	public DDMExpression<Double> createDoubleDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return new DDMExpressionImpl<>(expressionString, Double.class);
	}

	@Override
	public DDMExpression<Float> createFloatDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return new DDMExpressionImpl<>(expressionString, Float.class);
	}

	@Override
	public DDMExpression<Integer> createIntegerDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return new DDMExpressionImpl<>(expressionString, Integer.class);
	}

	@Override
	public DDMExpression<Long> createLongDDMExpression(String expressionString)
		throws DDMExpressionException {

		return new DDMExpressionImpl<>(expressionString, Long.class);
	}

	@Override
	public DDMExpression<Number> createNumberDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Number.class);

		setDDMExpressionFunctions(ddmExpression);

		return ddmExpression;
	}

	@Override
	public DDMExpression<String> createStringDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		DDMExpression<String> ddmExpression = new DDMExpressionImpl<>(
			expressionString, String.class);

		setDDMExpressionFunctions(ddmExpression);

		return ddmExpression;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		unbind = "unbindDDMExpressionFunction"
	)
	protected void setDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey("ddm.form.evaluator.function.name")) {
			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctionMap.putIfAbsent(
				functionName, ddmExpressionFunction);
		}
	}

	protected void setDDMExpressionFunctions(DDMExpression<?> ddmExpression) {
		for (Map.Entry<String, DDMExpressionFunction> entry :
				_ddmExpressionFunctionMap.entrySet()) {

			ddmExpression.setDDMExpressionFunction(
				entry.getKey(), entry.getValue());
		}
	}

	protected void unbindDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey("ddm.form.evaluator.function.name")) {
			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctionMap.remove(functionName);
		}
	}

	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctionMap =
		new ConcurrentHashMap<>();

}