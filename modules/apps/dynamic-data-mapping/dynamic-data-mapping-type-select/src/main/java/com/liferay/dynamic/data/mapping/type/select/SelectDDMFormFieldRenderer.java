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

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=select",
	service = DDMFormFieldRenderer.class
)
public class SelectDDMFormFieldRenderer extends BaseDDMFormFieldRenderer {

	@Override
	public String getTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public String getTemplateNamespace() {
		return "ddm.select";
	}

	@Override
	public TemplateResource getTemplateResource() {
		return _templateResource;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_templateResource = getTemplateResource(
			"/META-INF/resources/select.soy");
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormField ddmFormField) {

		String dataSourceType = (String)ddmFormField.getProperty(
			"dataSourceType");

		if (Validator.equals(dataSourceType, "manual")) {
			return ddmFormField.getDDMFormFieldOptions();
		}

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		long ddmDataProviderInstanceId = GetterUtil.getLong(
			ddmFormField.getProperty("ddmDataProviderInstanceId"));

		try {
			DDMDataProviderInstance ddmDataProviderInstance =
				_ddmDataProviderInstanceService.getDataProviderInstance(
					ddmDataProviderInstanceId);

			List<KeyValuePair> data = ddmDataProviderInstance.getData();

			for (KeyValuePair keyValuePair : data) {
				ddmFormFieldOptions.addOptionLabel(
					keyValuePair.getKey(), LocaleUtil.getDefault(),
					keyValuePair.getValue());
			}

			return ddmFormFieldOptions;
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to retrieve data from DDM data provider ID " +
					ddmDataProviderInstanceId,
				pe);
		}

		return ddmFormFieldOptions;
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		SelectDDMFormFieldContextHelper selectDDMFormFieldContextHelper =
			new SelectDDMFormFieldContextHelper(
				getDDMFormFieldOptions(ddmFormField),
				ddmFormFieldRenderingContext.getValue(),
				ddmFormField.getPredefinedValue(),
				ddmFormFieldRenderingContext.getLocale());

		return selectDDMFormFieldContextHelper.getOptions();
	}

	@Override
	protected void populateRequiredContext(
		Template template, DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		super.populateRequiredContext(
			template, ddmFormField, ddmFormFieldRenderingContext);

		template.put(
			"multiple",
			ddmFormField.isMultiple() ? "multiple" : StringPool.BLANK);
		template.put(
			"options", getOptions(ddmFormField, ddmFormFieldRenderingContext));

		Map<String, String> stringsMap = new HashMap<>();

		stringsMap.put(
			"chooseAnOption",
			LanguageUtil.get(
				ddmFormFieldRenderingContext.getLocale(), "choose-an-option"));

		template.put("strings", stringsMap);
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderInstanceService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldRenderer.class);

	private volatile DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private TemplateResource _templateResource;

}