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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererHelper {

	public DDMFormRendererHelper(
		PortletRequest portletRequest, PortletResponse portletResponse,
		ConfigurationModel configurationModel,
		DDMFormRenderer ddmFormRenderer) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_configurationModel = configurationModel;
		_ddmFormRenderer = ddmFormRenderer;
	}

	public String getDDMFormHTML() throws PortletException {
		try {
			DDMForm ddmForm = getDDMForm();

			return _ddmFormRenderer.render(
				ddmForm, createDDMFormRenderingContext(ddmForm));
		}
		catch (DDMFormRenderingException ddmfre) {
			_log.error("Unable to render DDM Form ", ddmfre);

			throw new PortletException(ddmfre);
		}
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(getDDMFormValues(ddmForm));
		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(_portletRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_portletResponse));
		ddmFormRenderingContext.setLocale(getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			_portletResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMForm getDDMForm() {
		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					_configurationModel, getLocale());

		return configurationModelToDDMFormConverter.getDDMForm();
	}

	protected DDMFormValues getDDMFormValues(DDMForm ddmForm) {
		ConfigurationModelToDDMFormValuesConverter
			configurationModelToDDMFormValuesConverter =
				new ConfigurationModelToDDMFormValuesConverter(
					_configurationModel, ddmForm, getLocale());

		return configurationModelToDDMFormValuesConverter.getDDMFormValues();
	}

	protected Locale getLocale() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getLocale();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRendererHelper.class);

	private final ConfigurationModel _configurationModel;
	private final DDMFormRenderer _ddmFormRenderer;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;

}