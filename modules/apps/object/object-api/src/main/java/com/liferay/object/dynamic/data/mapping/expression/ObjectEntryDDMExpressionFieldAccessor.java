/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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