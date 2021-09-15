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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Objects;

/**
 * @author Mateus Santana
 */
public class IsRequiredObjectFieldFunction
	implements DDMExpressionFunction.Function1<String, Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "isRequiredObjectField";

	public IsRequiredObjectFieldFunction(JSONFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	@Override
	public Boolean apply(String fieldName) {
		fieldName = fieldName.replaceAll("\\[|\\]|\"", StringPool.BLANK);

		JSONArray objectFieldsJSONArray =
			_ddmExpressionParameterAccessor.getObjectFields();

		for (int i = 0; i < objectFieldsJSONArray.length(); i++) {
			JSONObject jsonObject = objectFieldsJSONArray.getJSONObject(i);

			if (Objects.equals(jsonObject.getString("name"), fieldName)) {
				return jsonObject.getBoolean("required");
			}
		}

		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	protected JSONFactory jsonFactory;

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}