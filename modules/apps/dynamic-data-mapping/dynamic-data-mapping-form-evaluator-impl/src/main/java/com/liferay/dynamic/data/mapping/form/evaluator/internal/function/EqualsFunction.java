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

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class EqualsFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "equals";

	@Override
	public Boolean apply(Object object1, Object object2) {
		Object value1 = object1;

		if (object1 instanceof JSONArray) {
			value1 = _getValue((JSONArray)object1);
		}

		Object value2 = object2;

		if (object2 instanceof JSONArray) {
			value2 = _getValue((JSONArray)object2);
		}

		return Objects.equals(value1, value2);
	}

	@Override
	public String getName() {
		return NAME;
	}

	private Object _getValue(JSONArray jsonArray) {
		if (jsonArray.length() == 1) {
			return jsonArray.get(0);
		}

		return jsonArray;
	}

}