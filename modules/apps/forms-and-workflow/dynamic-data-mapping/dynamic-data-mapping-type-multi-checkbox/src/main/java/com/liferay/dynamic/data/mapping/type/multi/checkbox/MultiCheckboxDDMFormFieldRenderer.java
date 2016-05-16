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

package com.liferay.dynamic.data.mapping.type.multi.checkbox;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=multi-checkbox",
	service = DDMFormFieldRenderer.class
)
public class MultiCheckboxDDMFormFieldRenderer
	extends BaseDDMFormFieldRenderer {

	@Override
	public String getTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public String getTemplateNamespace() {
		return "ddm.multi_checkbox";
	}

	@Override
	public TemplateResource getTemplateResource() {
		return _templateResource;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_templateResource = getTemplateResource(
			"/META-INF/resources/multi_checkbox.soy");
	}

	@Deactivate
	protected void deactivate() {
		_templateResource = null;
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		MultiCheckboxDDMFormFieldContextHelper
			multiCheckboxDDMFormFieldContextHelper =
				new MultiCheckboxDDMFormFieldContextHelper(
					jsonFactory, ddmFormField.getDDMFormFieldOptions(),
					ddmFormFieldRenderingContext.getValue(),
					ddmFormField.getPredefinedValue(),
					ddmFormFieldRenderingContext.getLocale());

		return multiCheckboxDDMFormFieldContextHelper.getOptions();
	}

	@Override
	protected void populateOptionalContext(
		Template template, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		template.put(
			"showAsSwitcher", ddmFormField.getProperty("showAsSwitcher"));

		template.put("inline", ddmFormField.getProperty("inline"));
	}

	@Override
	protected void populateRequiredContext(
		Template template, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		super.populateRequiredContext(
			template, ddmFormField, ddmFormFieldRenderingContext);

		template.put(
			"options", getOptions(ddmFormField, ddmFormFieldRenderingContext));
	}

	@Reference
	protected JSONFactory jsonFactory;

	private TemplateResource _templateResource;

}