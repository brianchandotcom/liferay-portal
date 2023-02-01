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

package com.liferay.object.internal.dynamic.data.mapping.expression;

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

	public ObjectEntryDDMExpressionFieldAccessor(
		Map<String, Object> objectEntryValues) {

		_objectEntryValues = objectEntryValues;
	}

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		Object fieldProperty = _objectEntryValues.get(
			getFieldPropertyRequest.getField());

		if ((fieldProperty == null) &&
			isField(getFieldPropertyRequest.getField())) {

			fieldProperty = StringPool.BLANK;
		}

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(fieldProperty);

		return builder.build();
	}

	@Override
	public boolean isField(String parameter) {
		return _objectEntryValues.containsKey(parameter);
	}

	private final Map<String, Object> _objectEntryValues;

}