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
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class IsEmptyFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "isEmpty";

	@Override
	public Boolean apply(Object parameter) {
		if (parameter == null) {
			return true;
		}

		if (isArray(parameter)) {
			Object[] values = (Object[])parameter;

			return Stream.of(
				values
			).allMatch(
				Validator::isNull
			);
		}

		if (parameter instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)parameter;

			return jsonArray.length() == 0;
		}

		return Validator.isNull(parameter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

}