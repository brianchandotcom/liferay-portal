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

package com.liferay.dynamic.data.mapping.type.validation.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=validation",
	service = {
		ValidationDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class ValidationDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"value", getValue(ddmFormField, ddmFormFieldRenderingContext));

		return parameters;
	}

	protected Map<String, String> getValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, String> value = new HashMap<>();

		String valueString = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNotNull(valueString)) {
			try {
				JSONObject valueJSONObject = jsonFactory.createJSONObject(
					valueString);

				value.put(
					"errorMessage", valueJSONObject.getString("errorMessage"));
				value.put(
					"expression", valueJSONObject.getString("expression"));
			}
			catch (JSONException jsone) {
				jsone.printStackTrace();
			}
		}
		else {
			value.put("errorMessage", StringPool.BLANK);
			value.put("expression", StringPool.BLANK);
		}

		return value;
	}

	@Reference
	protected JSONFactory jsonFactory;

}