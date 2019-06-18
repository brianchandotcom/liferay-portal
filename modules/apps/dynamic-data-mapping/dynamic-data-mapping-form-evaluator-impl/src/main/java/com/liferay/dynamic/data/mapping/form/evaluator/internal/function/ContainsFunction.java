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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Leonardo Barros
 */
public class ContainsFunction
	implements DDMExpressionFunction.Function2<Object, String, Boolean> {

	public static final String NAME = "contains";

	@Override
	public Boolean apply(Object object, String key) {
		if (object == null) {
			return false;
		}

		if (object instanceof JSONArray) {
			return apply(object.toString(), key);
		}

		if (object instanceof String) {
			return apply((String)object, key);
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

	protected Boolean apply(String string1, String string2) {
		if (Validator.isNull(string1) || Validator.isNull(string2)) {
			return false;
		}

		string1 = StringUtil.toLowerCase(string1);
		string2 = StringUtil.toLowerCase(string2);

		return string1.contains(string2);
	}

}