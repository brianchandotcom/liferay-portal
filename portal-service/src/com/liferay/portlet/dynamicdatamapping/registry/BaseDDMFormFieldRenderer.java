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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;

import java.io.Writer;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMFormFieldRenderer implements DDMFormFieldRenderer {

	@Override
	public String render(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, templateResource, false);

		template.put(TemplateConstants.NAMESPACE, templateNamespace);

		populateRequiredContext(
			template, ddmFormField, ddmFormFieldRenderingContext);

		populateOptionalContext(
			template, ddmFormField, ddmFormFieldRenderingContext);

		return render(template);
	}

	protected String getFieldNameSuffix(String instanceId) {
		return _INSTANCE_SEPARATOR.concat(instanceId);
	}

	protected String getFieldQualifiedName(
		String fieldName, String instanceId) {

		String fieldNameSuffix = getFieldNameSuffix(instanceId);

		return fieldName.concat(fieldNameSuffix);
	}

	protected void populateOptionalContext(
		Template template, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {
	}

	protected void populateRequiredContext(
		Template template, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Locale locale = ddmFormFieldRenderingContext.getLocale();

		String fieldName = ddmFormField.getName();

		String instanceId = StringUtil.randomString();

		template.put("dir", LanguageUtil.get(locale, "lang.dir"));
		template.put("fieldName", ddmFormField.getName());
		template.put(
			"fieldQualifiedName", getFieldQualifiedName(fieldName, instanceId));
		template.put("fieldNameSuffix", getFieldNameSuffix(instanceId));

		LocalizedValue label = ddmFormField.getLabel();

		template.put("fieldLabel", label.getString(locale));

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		template.put("fieldValue", predefinedValue.getString(locale));
	}

	protected String render(Template template) throws PortalException {
		Writer writer = new UnsyncStringWriter();

		template.processTemplate(writer);

		return writer.toString();
	}

	protected String templateNamespace;
	protected TemplateResource templateResource;

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

}