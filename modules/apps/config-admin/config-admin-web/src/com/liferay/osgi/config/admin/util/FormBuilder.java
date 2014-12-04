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

package com.liferay.osgi.config.admin.util;

import com.liferay.osgi.config.admin.model.ConfigurationModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormRendererUtil;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.InvalidSyntaxException;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class FormBuilder {

	public FormBuilder(ConfigurationModel model, ThemeDisplay themeDisplay) {
		_model = model;
		_themeDisplay = themeDisplay;
	}

	public String build(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws InvalidSyntaxException, IOException, PortalException {

		DDMForm ddmForm = _modelToDDM.convert(
			_model, _model.getConfiguration(), _themeDisplay.getLocale());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(portletRequest));
		ddmFormFieldRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(portletResponse));
		ddmFormFieldRenderingContext.setLocale(_themeDisplay.getLocale());
		ddmFormFieldRenderingContext.setPortletNamespace(
			portletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(false);

		String configFieldJSON = DDMFormJSONSerializerUtil.serialize(ddmForm);

		if (_log.isDebugEnabled()) {
			_log.debug("DDMForm: " + configFieldJSON);
		}

		portletRequest.setAttribute("configFieldJSON", configFieldJSON);
		portletRequest.setAttribute(
			"scopeGroupId", _themeDisplay.getScopeGroupId());
		portletRequest.setAttribute("plId", _themeDisplay.getPlid());

		return DDMFormRendererUtil.render(
			ddmForm, ddmFormFieldRenderingContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormBuilder.class);

	private final ThemeDisplay _themeDisplay;
	private final ConfigurationDDMHelper _modelToDDM =
		new ConfigurationDDMHelper();
	private final ConfigurationModel _model;

}