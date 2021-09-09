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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Mateus Santana
 */
public class IsRequiredObjectFieldFunction
	implements DDMExpressionFunction.Function1<Object, Boolean>,
			   DDMExpressionParameterAccessorAware {

	public static final String NAME = "IsRequiredObjectField";

	public IsRequiredObjectFieldFunction(JSONFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	@Override
	public Boolean apply(Object fieldValue) {
		try {
			JSONArray fieldJSONArray = jsonFactory.createJSONArray(
				String.valueOf(fieldValue));

			JSONArray objectFieldsJSONArray =
				_ddmExpressionParameterAccessor.getObjectFields();

			for (int i = 0; i < objectFieldsJSONArray.length(); i++) {
				JSONObject jsonObject = objectFieldsJSONArray.getJSONObject(i);

				if (jsonObject.getString(
						"name"
					).equals(
						fieldJSONArray.get(0)
					)) {

					return jsonObject.getBoolean("required");
				}
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
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

	private static final Log _log = LogFactoryUtil.getLog(
		IsRequiredObjectFieldFunction.class);

	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;

}