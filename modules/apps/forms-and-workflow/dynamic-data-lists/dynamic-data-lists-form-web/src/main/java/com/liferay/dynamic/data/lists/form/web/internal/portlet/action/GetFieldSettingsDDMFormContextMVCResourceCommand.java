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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=getFieldSettingsDDMFormContext"
	},
	service = MVCResourceCommand.class
)
public class GetFieldSettingsDDMFormContextMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String type = ParamUtil.getString(resourceRequest, "type");

		Class<?> ddmFormFieldTypeSettings = getDDMFormFieldTypeSettings(type);

		DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
			ddmFormFieldTypeSettings);

		DDMFormLayout ddmFormFieldTypeSettingsDDMFormLayout =
			DDMFormLayoutFactory.create(ddmFormFieldTypeSettings);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			resourceRequest, ddmFormFieldTypeSettingsDDMForm);

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		ddmFormRenderingContext.setHttpServletRequest(
			_portal.getHttpServletRequest(resourceRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			_portal.getHttpServletResponse(resourceResponse));
		ddmFormRenderingContext.setContainerId("settings");
		ddmFormRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			resourceResponse.getNamespace());

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmFormFieldTypeSettingsDDMForm,
				ddmFormFieldTypeSettingsDDMFormLayout, ddmFormRenderingContext);

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		PortletResponseUtil.write(
			resourceResponse, jsonSerializer.serializeDeep(templateContext));

		resourceResponse.flushBuffer();
	}

	protected Class<?> getDDMFormFieldTypeSettings(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}