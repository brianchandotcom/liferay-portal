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

package com.liferay.dynamic.data.mapping.type.checkbox;

import com.liferay.portlet.dynamicdatamapping.registry.BaseDDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettings;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueAccessor;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueParameterSerializer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueRendererAccessor;
import com.liferay.portlet.dynamicdatamapping.registry.DefaultDDMFormFieldValueParameterSerializer;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(immediate = true, service = DDMFormFieldType.class)
public class CheckboxDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer() {
		return _ddmFormFieldRenderer;
	}

	@Override
	public String getDDMFormFieldTypeJavaScriptClass() {
		return "Liferay.DDM.Field.Checkbox";
	}

	@Override
	public String getDDMFormFieldTypeJavaScriptModule() {
		return "liferay-ddm-form-field-checkbox";
	}

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return CheckboxDDMFormFieldTypeSettings.class;
	}

	@Override
	public DDMFormFieldValueAccessor<Boolean> getDDMFormFieldValueAccessor(
		Locale locale) {

		return new CheckboxDDMFormFieldValueAccessor(locale);
	}

	@Override
	public DDMFormFieldValueParameterSerializer
		getDDMFormFieldValueParameterSerializer() {

		return new DefaultDDMFormFieldValueParameterSerializer();
	}

	@Override
	public DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(Locale locale) {

		return new CheckboxDDMFormFieldValueRendererAccessor(
			getDDMFormFieldValueAccessor(locale));
	}

	@Override
	public String getIcon() {
		return "icon-check";
	}

	@Override
	public String getName() {
		return "checkbox";
	}

	@Reference(service = CheckboxDDMFormFieldRenderer.class, unbind = "-")
	protected void setDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		_ddmFormFieldRenderer = ddmFormFieldRenderer;
	}

	private DDMFormFieldRenderer _ddmFormFieldRenderer;

}