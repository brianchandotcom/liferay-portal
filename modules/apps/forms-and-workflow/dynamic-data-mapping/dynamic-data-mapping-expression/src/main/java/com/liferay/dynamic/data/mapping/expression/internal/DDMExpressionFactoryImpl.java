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
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMExpressionFactory.class)
public class DDMExpressionFactoryImpl implements DDMExpressionFactory {

	@Override
	public DDMExpression<Boolean> createBooleanDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Boolean.class);
	}

	@Override
	public DDMExpression<Double> createDoubleDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Double.class);
	}

	@Override
	public DDMExpression<Float> createFloatDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Float.class);
	}

	@Override
	public DDMExpression<Integer> createIntegerDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Integer.class);
	}

	@Override
	public DDMExpression<Long> createLongDDMExpression(String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Long.class);
	}

	@Override
	public DDMExpression<Number> createNumberDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, Number.class);
	}

	@Override
	public DDMExpression<String> createStringDDMExpression(
			String expressionString)
		throws DDMExpressionException {

		return createDDMExpression(expressionString, String.class);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDMExpressionFunction"
	)
	protected void addDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey("ddm.form.evaluator.function.name")) {
			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctionMap.putIfAbsent(
				functionName, ddmExpressionFunction);
		}
	}

	protected <T> DDMExpression<T> createDDMExpression(
			String expressionString, Class<T> expressionClass)
		throws DDMExpressionException {

		DDMExpression<T> ddmExpression = new DDMExpressionImpl<>(
			expressionString, expressionClass);

		setDDMExpressionFunctions(ddmExpression);

		return ddmExpression;
	}

	protected void removeDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey("ddm.form.evaluator.function.name")) {
			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctionMap.remove(functionName);
		}
	}

	protected void setDDMExpressionFunctions(DDMExpression<?> ddmExpression) {
		for (Map.Entry<String, DDMExpressionFunction> entry :
				_ddmExpressionFunctionMap.entrySet()) {

			ddmExpression.setDDMExpressionFunction(
				entry.getKey(), entry.getValue());
		}
	}

	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctionMap =
		new ConcurrentHashMap<>();

}