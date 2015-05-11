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

package com.liferay.dynamic.data.mapping.web.portlet.action;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRendererRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;

/**
 * @author Bruno Basto
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmRenderStructureField",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMRenderStructureFieldActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		
		HttpServletResponse httpServletResponse = 
			PortalUtil.getHttpServletResponse(portletResponse);
		
		HttpServletRequest httpServletRequest = 
			PortalUtil.getHttpServletRequest(portletRequest);

		DDMFormField ddmFormField = getDDMFormField(httpServletRequest);

		DDMFormFieldRenderer ddmFormFieldRenderer =
			DDMFormFieldRendererRegistryUtil.getDDMFormFieldRenderer(
				ddmFormField.getType());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext(
				httpServletRequest, httpServletResponse);

		String ddmFormFieldHTML = ddmFormFieldRenderer.render(
			ddmFormField, ddmFormFieldRenderingContext);
		
		httpServletResponse.setContentType(ContentTypes.TEXT_HTML);

		ServletResponseUtil.write(httpServletResponse, ddmFormFieldHTML);

	}
	
	protected DDMFormFieldRenderingContext createDDMFormFieldRenderingContext(
		HttpServletRequest request, HttpServletResponse response) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String mode = ParamUtil.getString(request, DDMConstants.MODE);
		
		String namespace = ParamUtil.getString(
			request, DDMConstants.NAMESPACE);
		
		String portletNamespace = ParamUtil.getString(
			request, DDMConstants.PORTLET_NAMESPACE);
		
		boolean readOnly = ParamUtil.getBoolean(
			request, DDMConstants.READ_ONLY);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		request.setAttribute(
			DDMConstants.AUI_FORM_PORTLET_NAMESPACE, portletNamespace);

		ddmFormFieldRenderingContext.setHttpServletRequest(request);
		ddmFormFieldRenderingContext.setHttpServletResponse(response);
		ddmFormFieldRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormFieldRenderingContext.setMode(mode);
		ddmFormFieldRenderingContext.setNamespace(namespace);
		ddmFormFieldRenderingContext.setPortletNamespace(portletNamespace);
		ddmFormFieldRenderingContext.setReadOnly(readOnly);

		return ddmFormFieldRenderingContext;
	}

	protected DDMFormField getDDMFormField(HttpServletRequest request)
		throws PortalException {

		String definition = ParamUtil.getString(
			request, DDMConstants.DEFINITION);
		
		String fieldName = ParamUtil.getString(
			request, DDMConstants.FIELD_NAME);

		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		return ddmFormFieldsMap.get(fieldName);
	}

}
