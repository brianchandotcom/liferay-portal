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

package com.liferay.portlet.dynamicdatamapping.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRendererRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Basto
 */
public class RenderStructureFieldAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			DDMFormField ddmFormField = getDDMFormField(request);

			DDMFormFieldRenderer ddmFormFieldRenderer =
				DDMFormFieldRendererRegistryUtil.getDDMFormFieldRenderer(
					ddmFormField.getType());

			DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
				createDDMFormFieldRenderingContext(request, response);

			String ddmFormFieldHTML = ddmFormFieldRenderer.render(
				ddmFormField, ddmFormFieldRenderingContext);

			response.setContentType(ContentTypes.TEXT_HTML);

			ServletResponseUtil.write(response, ddmFormFieldHTML);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected DDMFormFieldRenderingContext createDDMFormFieldRenderingContext(
		HttpServletRequest request, HttpServletResponse response) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String mode = ParamUtil.getString(request, "mode");
		String namespace = ParamUtil.getString(request, "namespace");
		String portletNamespace = ParamUtil.getString(
			request, "portletNamespace");
		boolean readOnly = ParamUtil.getBoolean(request, "readOnly");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		request.setAttribute("aui:form:portletNamespace", portletNamespace);

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

		String definition = ParamUtil.getString(request, "definition");
		String fieldName = ParamUtil.getString(request, "fieldName");

		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		return ddmFormFieldsMap.get(fieldName);
	}

}