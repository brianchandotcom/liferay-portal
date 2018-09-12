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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-field-settings-form-context",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFieldSettingsDDMFormContextServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-field-settings-form-context/*"
	},
	service = Servlet.class
)
public class DDMFieldSettingsDDMFormContextServlet
	extends BaseDDMFormBuilderServlet {

	protected Map<String, Object> createFieldSettingsFormContext(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			String languageId = ParamUtil.getString(request, "languageId");
			String portletNamespace = ParamUtil.getString(
				request, "portletNamespace");
			String type = ParamUtil.getString(request, "type");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			LocaleThreadLocal.setThemeDisplayLocale(locale);

			Class<?> ddmFormFieldTypeSettings = getDDMFormFieldTypeSettings(
				type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormLayout ddmFormFieldTypeSettingsDDMFormLayout =
				DDMFormLayoutFactory.create(ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				request, ddmFormFieldTypeSettingsDDMForm);

			setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(request);
			ddmFormRenderingContext.setHttpServletResponse(response);
			ddmFormRenderingContext.setContainerId("settings");
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(portletNamespace);

			return _ddmFormTemplateContextFactory.create(
				ddmFormFieldTypeSettingsDDMForm,
				ddmFormFieldTypeSettingsDDMFormLayout, ddmFormRenderingContext);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		Map<String, Object> fieldSettingsFormContext =
			createFieldSettingsFormContext(request, response);

		if (fieldSettingsFormContext == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		ServletResponseUtil.write(
			response, jsonSerializer.serializeDeep(fieldSettingsFormContext));
	}

	protected Class<?> getDDMFormFieldTypeSettings(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	protected void setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldSettingsDDMFormContextServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}