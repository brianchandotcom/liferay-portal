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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class EqualsFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "equals";

	@Override
	public Boolean apply(Object object1, Object object2) {
		Object value1 = _getValue(object1);

		Object value2 = _getValue(object2);

		if (!Objects.equals(value1.getClass(), value2.getClass())) {
			if (object1 instanceof BigDecimal) {
				value1 = _convertValue(value2, value1);
			}
			else if (object2 instanceof BigDecimal) {
				value2 = _convertValue(value1, value2);
			}
		}

		return Objects.equals(value1, value2);
	}

	@Override
	public String getName() {
		return NAME;
	}

	private Object _convertValue(Object value1, Object value2) {
		if (value1 instanceof Double) {
			return GetterUtil.getDouble(value2);
		}
		else if (value1 instanceof Integer) {
			return GetterUtil.getInteger(value2);
		}
		else if (value1 instanceof Long) {
			return GetterUtil.getLong(value2);
		}

		return value2;
	}

	private Object _getValue(Object object) {
		if (object instanceof BigDecimal || object instanceof Boolean ||
			object instanceof JSONObject) {

			return object.toString();
		}
		else if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			String[] strings = ArrayUtil.toStringArray(jsonArray);

			Arrays.sort(strings);

			return StringUtil.merge(strings);
		}

		return object;
	}

}