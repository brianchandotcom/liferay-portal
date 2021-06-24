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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Carolina Barbosa
 */
public class GetLocalizedValueFunction
	implements DDMExpressionFieldAccessorAware,
			   DDMExpressionFunction.Function1<String, Object> {

	public static final String NAME = "getLocalizedValue";

	@Override
	public Object apply(String field) {
		if ((_ddmExpressionFieldAccessor == null) || !_isFieldVisible(field)) {
			return StringPool.BLANK;
		}

		Object localizedValue = _getFieldPropertyResponseValue(
			field, "localizedValue");

		if (localizedValue == null) {
			return StringPool.BLANK;
		}

		return localizedValue;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionFieldAccessor(
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
	}

	private Object _getFieldPropertyResponseValue(
		String field, String property) {

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(field, property);

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		return getFieldPropertyResponse.getValue();
	}

	private boolean _isFieldVisible(String field) {
		return GetterUtil.getBoolean(
			_getFieldPropertyResponseValue(field, "visible"), true);
	}

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;

}