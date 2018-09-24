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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	factory = DDMConstants.EXPRESSION_FUNCTION_FACTORY_NAME,
	service = {
		DDMExpressionFieldAccessorAware.class,
		DDMExpressionFunction.Function1.class
	}
)
public class GetValueFunction
	implements DDMExpressionFunction.Function1<String, Object>,
			   DDMExpressionFieldAccessorAware {

	@Override
	public Object apply(String field) {
		if (_ddmExpressionFieldAccessor == null) {
			return StringPool.BLANK;
		}

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(field, "value");

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		return getFieldPropertyResponse.getValue();
	}

	@Override
	public String getName() {
		return "getValue";
	}

	@Override
	public void setDDMExpressionFieldAccessor(
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
	}

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;

}