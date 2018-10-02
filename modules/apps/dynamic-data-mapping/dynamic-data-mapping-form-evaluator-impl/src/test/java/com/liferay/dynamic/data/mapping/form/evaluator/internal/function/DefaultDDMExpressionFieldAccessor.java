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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;

import java.util.function.Function;

/**
 * @author Rafael Praxedes
 */
public class DefaultDDMExpressionFieldAccessor
	implements DDMExpressionFieldAccessor {

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		return _getFieldPropertyResponseFunction.apply(getFieldPropertyRequest);
	}

	@Override
	public boolean isField(String parameter) {
		return true;
	}

	public void setGetFieldPropertyResponseFunction(
		Function<GetFieldPropertyRequest, GetFieldPropertyResponse>
			getFieldPropertyResponseFunction) {

		_getFieldPropertyResponseFunction = getFieldPropertyResponseFunction;
	}

	private Function<GetFieldPropertyRequest, GetFieldPropertyResponse>
		_getFieldPropertyResponseFunction;

}