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
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Leonardo Barros
 */
public class IsURLFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "isURL";

	@Override
	public Boolean apply(Object parameter) {
		if (parameter == null) {
			return false;
		}

		return Validator.isUrl(parameter.toString());
	}

	@Override
	public String getName() {
		return NAME;
	}

}