/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_ddmExpressionParameterAccessor.getObjectFieldsJSONArray();

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