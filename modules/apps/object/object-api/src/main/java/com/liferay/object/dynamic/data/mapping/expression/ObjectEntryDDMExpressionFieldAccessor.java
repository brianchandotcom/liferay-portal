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

package com.liferay.object.dynamic.data.mapping.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;

import java.util.Map;

/**
 * @author Pedro Tavares
 */
public class ObjectEntryDDMExpressionFieldAccessor
	implements DDMExpressionFieldAccessor {

	public ObjectEntryDDMExpressionFieldAccessor(Map<String, Object> values) {
		_values = values;
	}

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		Object value = _values.get(getFieldPropertyRequest.getField());

		if ((value == null) && isField(getFieldPropertyRequest.getField())) {
			value = StringPool.BLANK;
		}

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(value);

		return builder.build();
	}

	@Override
	public boolean isField(String parameter) {
		return _values.containsKey(parameter);
	}

	private final Map<String, Object> _values;

}