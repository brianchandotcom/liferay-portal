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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ArrayUtil;

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
		return Objects.equals(_getValue(object1), _getValue(object2));
	}

	@Override
	public String getName() {
		return NAME;
	}

	private Object _getValue(Object object) {
		if (object instanceof BigDecimal) {
			return object.toString();
		}
		else if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			String[] stringArray = ArrayUtil.toStringArray(jsonArray);

			Arrays.sort(stringArray);

			StringBundler sb = new StringBundler((stringArray.length * 2) - 1);

			for (int i = 0; i < stringArray.length; i++) {
				sb.append(stringArray[i]);

				if (i < (stringArray.length - 1)) {
					sb.append(CharPool.COMMA);
				}
			}

			return sb.toString();
		}

		return object;
	}

}