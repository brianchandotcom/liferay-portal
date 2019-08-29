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

package com.liferay.dynamic.data.mapping.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRendererRegistry;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
		"mvc.command.name=renderStructureField"
	},
	service = MVCResourceCommand.class
)
public class RenderStructureFieldMVCRenderCommand
	extends BaseMVCResourceCommand {

	protected DDMFormFieldRenderingContext createDDMFormFieldRenderingContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String mode = ParamUtil.getString(httpServletRequest, "mode");
		String namespace = ParamUtil.getString(httpServletRequest, "namespace");
		String portletNamespace = ParamUtil.getString(
			httpServletRequest, "portletNamespace");
		boolean readOnly = ParamUtil.getBoolean(httpServletRequest, "readOnly");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		httpServletRequest.setAttribute(
			"aui:form:portletNamespace", portletNamespace);

		ddmFormFieldRenderingContext.setHttpServletRequest(
			_portal.getOriginalServletRequest(httpServletRequest));
		ddmFormFieldRenderingContext.setHttpServletResponse(
			httpServletResponse);
		ddmFormFieldRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormFieldRenderingContext.setMode(mode);
		ddmFormFieldRenderingContext.setNamespace(namespace);
		ddmFormFieldRenderingContext.setPortletNamespace(portletNamespace);
		ddmFormFieldRenderingContext.setReadOnly(readOnly);

		return ddmFormFieldRenderingContext;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		DDMFormField ddmFormField = getDDMFormField(httpServletRequest);

		DDMFormFieldRenderer ddmFormFieldRenderer =
			_ddmFormFieldRendererRegistry.getDDMFormFieldRenderer(
				ddmFormField.getType());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext(
				httpServletRequest, httpServletResponse);

		String ddmFormFieldHTML = ddmFormFieldRenderer.render(
			ddmFormField, ddmFormFieldRenderingContext);

		httpServletResponse.setContentType(ContentTypes.TEXT_HTML);

		ServletResponseUtil.write(httpServletResponse, ddmFormFieldHTML);
	}

	protected DDMFormField getDDMFormField(
		HttpServletRequest httpServletRequest) {

		String definition = ParamUtil.getString(
			httpServletRequest, "definition");
		String fieldName = ParamUtil.getString(httpServletRequest, "fieldName");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		return ddmFormFieldsMap.get(fieldName);
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldRendererRegistry(
		DDMFormFieldRendererRegistry ddmFormFieldRendererRegistry) {

		_ddmFormFieldRendererRegistry = ddmFormFieldRendererRegistry;
	}

	private DDMFormFieldRendererRegistry _ddmFormFieldRendererRegistry;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference
	private Portal _portal;

}