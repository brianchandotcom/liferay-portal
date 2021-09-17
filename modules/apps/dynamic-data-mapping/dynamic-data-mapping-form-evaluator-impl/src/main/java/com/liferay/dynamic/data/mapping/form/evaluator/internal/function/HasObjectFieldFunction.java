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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carolina Barbosa
 */
public class HasObjectFieldFunction
	implements DDMExpressionFunction.Function1<String, Boolean> {

	public static final String NAME = "hasObjectField";

	@Override
	public Boolean apply(String field) {
		if (Validator.isNull(field) ||
			Validator.isNull(
				field.replaceAll("\\[|\\]|\"", StringPool.BLANK))) {

			return false;
		}

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

}