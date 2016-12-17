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

import java.util.Map;

/**
 * @author Inácio Nery
 */
public class JumpPageFunction implements DDMExpressionFunction {

	public JumpPageFunction(Map<Integer, Integer> pageFlow) {
		_pageFlow = pageFlow;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length != 2) {
			throw new IllegalArgumentException("Two parameters are expected");
		}

		Double fromPageIndex = (Double)parameters[0];
		Double toPageIndex = (Double)parameters[1];

		_pageFlow.put(fromPageIndex.intValue(), toPageIndex.intValue());

		return true;
	}

	private final Map<Integer, Integer> _pageFlow;

}