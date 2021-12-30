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

package com.liferay.configuration.admin.web.internal.helper;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationDDMFormDeclarationUtil;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelToDDMFormConverter;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelToDDMFormValuesConverter;
import com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererHelper {

	public DDMFormRendererHelper(
		PortletRequest portletRequest, PortletResponse portletResponse,
		ConfigurationModel configurationModel, DDMFormRenderer ddmFormRenderer,
		LocationVariableResolver locationVariableResolver,
		ResourceBundleLoaderProvider resourceBundleLoaderProvider) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_configurationModel = configurationModel;
		_ddmFormRenderer = ddmFormRenderer;
		_locationVariableResolver = locationVariableResolver;
		_resourceBundleLoaderProvider = resourceBundleLoaderProvider;
	}

	public String getDDMFormHTML() throws PortletException {
		try {
			DDMForm ddmForm = _getDDMForm();

			return _ddmFormRenderer.render(
				ddmForm, _getDDMFormLayout(ddmForm),
				_createDDMFormRenderingContext(ddmForm));
		}
		catch (DDMFormRenderingException ddmFormRenderingException) {
			_log.error("Unable to render DDM Form ", ddmFormRenderingException);

			throw new PortletException(ddmFormRenderingException);
		}
	}

	private DDMFormRenderingContext _createDDMFormRenderingContext(
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(_getDDMFormValues(ddmForm));
		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(_portletRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_portletResponse));
		ddmFormRenderingContext.setLocale(_getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			_portletResponse.getNamespace());
		ddmFormRenderingContext.setShowSubmitButton(false);

		return ddmFormRenderingContext;
	}

	private DDMForm _getDDMForm() {
		ResourceBundleLoader resourceBundleLoader =
			_resourceBundleLoaderProvider.getResourceBundleLoader(
				_configurationModel.getBundleSymbolicName());

		Locale locale = _getLocale();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					_configurationModel, locale, resourceBundle);

		return configurationModelToDDMFormConverter.getDDMForm();
	}

	private DDMFormLayout _getDDMFormLayout(DDMForm ddmForm) {
		Class<?> formClass =
			ConfigurationDDMFormDeclarationUtil.getConfigurationDDMFormClass(
				_configurationModel);

		if (formClass != null) {
			try {
				return DDMFormLayoutFactory.create(formClass);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						illegalArgumentException, illegalArgumentException);
				}
			}
		}

		return DDMUtil.getDefaultDDMFormLayout(ddmForm);
	}

	private DDMFormValues _getDDMFormValues(DDMForm ddmForm) {
		ConfigurationModelToDDMFormValuesConverter
			configurationModelToDDMFormValuesConverter =
				new ConfigurationModelToDDMFormValuesConverter(
					_configurationModel, ddmForm, _getLocale(),
					_locationVariableResolver);

		return configurationModelToDDMFormValuesConverter.getDDMFormValues();
	}

	private Locale _getLocale() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getLocale();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRendererHelper.class);

	private final ConfigurationModel _configurationModel;
	private final DDMFormRenderer _ddmFormRenderer;
	private final LocationVariableResolver _locationVariableResolver;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

}