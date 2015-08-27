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

package com.liferay.dynamic.data.mapping.type.select;

import com.liferay.dynamic.data.mapping.registry.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueParameterSerializer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueRendererAccessor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, 
	property = {
		"ddm.form.field.type.icon=icon-list",
		"ddm.form.field.type.js.class=Liferay.DDM.Field.Select",
		"ddm.form.field.type.js.module=liferay-ddm-form-field-select",
		"ddm.form.field.type.name=select"
	}, 
	service = DDMFormFieldType.class
)
public class SelectDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer() {
		return _ddmFormFieldRenderer;
	}

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return SelectDDMFormFieldTypeSettings.class;
	}

	@Override
	public DDMFormFieldValueAccessor<JSONArray> getDDMFormFieldValueAccessor(
		Locale locale) {

		return new SelectDDMFormFieldValueAccessor(locale);
	}

	@Override
	public DDMFormFieldValueParameterSerializer
		getDDMFormFieldValueParameterSerializer() {

		return new DDMFormFieldValueParameterSerializer() {

			@Override
			public String getParameterValue(
				HttpServletRequest httpServletRequest,
				String ddmFormFieldParameterName,
				String defaultDDMFormFieldParameterValue) {

				String[] parameterValues = ParamUtil.getParameterValues(
					httpServletRequest, ddmFormFieldParameterName,
					GetterUtil.DEFAULT_STRING_VALUES);

				return JSONFactoryUtil.serialize(parameterValues);
			}

		};
	}

	@Override
	public DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(Locale locale) {

		return new SelectDDMFormFieldValueRendererAccessor(
			getDDMFormFieldValueAccessor(locale));
	}

	@Override
	public String getName() {
		return "select";
	}

	@Reference(service = SelectDDMFormFieldRenderer.class, unbind = "-")
	protected void setDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		_ddmFormFieldRenderer = ddmFormFieldRenderer;
	}

	private DDMFormFieldRenderer _ddmFormFieldRenderer;

}