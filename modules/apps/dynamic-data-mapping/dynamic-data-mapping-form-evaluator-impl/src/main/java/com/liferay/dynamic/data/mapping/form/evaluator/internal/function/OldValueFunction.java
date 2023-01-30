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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;

import java.util.Map;

/**
 * @author Paulo Albuquerque
 */
public class OldValueFunction
	implements DDMExpressionFunction.Function1<Object, Object>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "oldValue";

	@Override
	public Object apply(Object fieldName) {
		Map<String, Object> objectFieldsOldValues =
			_ddmExpressionParameterAccessor.getObjectFieldsOldValues();

		return objectFieldsOldValues.get(fieldName);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}