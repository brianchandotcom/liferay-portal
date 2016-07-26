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

package com.liferay.dynamic.data.mapping.type.radio.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=radio",
	service = {
		RadioDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class RadioDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"inline",
			GetterUtil.getBoolean(ddmFormField.getProperty("inline")));
		parameters.put(
			"options", getOptions(ddmFormField, ddmFormFieldRenderingContext));
		parameters.put(
			"value", getValue(ddmFormField, ddmFormFieldRenderingContext));

		return parameters;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		if (Objects.equals(dataSourceType, "manual")) {
			List<Map<String, String>> keyValuePairs =
				(List<Map<String, String>>)
					ddmFormFieldRenderingContext.getProperty("options");

			if (keyValuePairs.size() == 0) {
				return ddmFormField.getDDMFormFieldOptions();
			}

			for (Map<String, String> keyValuePair : keyValuePairs) {
				ddmFormFieldOptions.addOptionLabel(
					keyValuePair.get("value"),
					ddmFormFieldRenderingContext.getLocale(),
					keyValuePair.get("label"));
			}
		}

		return ddmFormFieldOptions;
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		RadioDDMFormFieldContextHelper radioDDMFormFieldContextHelper =
			new RadioDDMFormFieldContextHelper(
				jsonFactory, getDDMFormFieldOptions(
					ddmFormField, ddmFormFieldRenderingContext),
				ddmFormFieldRenderingContext.getValue(),
				ddmFormField.getPredefinedValue(),
				ddmFormFieldRenderingContext.getLocale());

		return radioDDMFormFieldContextHelper.getOptions();
	}

	protected String getValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return ddmFormFieldRenderingContext.getValue();
	}

	@Reference
	protected JSONFactory jsonFactory;

}