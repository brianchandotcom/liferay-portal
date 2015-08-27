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

package com.liferay.dynamic.data.mapping.type.options;

import com.liferay.dynamic.data.mapping.registry.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueParameterSerializer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueRendererAccessor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, 
	property = {
		"ddm.form.field.type.js.class=Liferay.DDM.Field.Options",
		"ddm.form.field.type.js.module=liferay-ddm-form-field-options",
		"ddm.form.field.type.system=true"
	}, 
	service = DDMFormFieldType.class
)
public class OptionsDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer() {
		return _ddmFormFieldRenderer;
	}

	@Override
	public DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		Locale locale) {

		return null;
	}

	@Override
	public DDMFormFieldValueParameterSerializer
		getDDMFormFieldValueParameterSerializer() {

		return null;
	}

	@Override
	public DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(Locale locale) {

		return null;
	}

	@Override
	public String getName() {
		return "options";
	}

	@Reference(service = OptionsDDMFormFieldRenderer.class, unbind = "-")
	protected void setDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		_ddmFormFieldRenderer = ddmFormFieldRenderer;
	}

	private DDMFormFieldRenderer _ddmFormFieldRenderer;

}
