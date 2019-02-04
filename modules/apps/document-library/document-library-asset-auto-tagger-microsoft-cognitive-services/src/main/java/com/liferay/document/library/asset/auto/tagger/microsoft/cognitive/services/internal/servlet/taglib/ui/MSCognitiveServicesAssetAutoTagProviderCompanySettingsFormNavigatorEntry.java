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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.servlet.taglib.ui;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.constants.FormNavigatorAssetAutoTaggerConstants;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.MSCognitiveServicesAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = FormNavigatorEntry.class)
public class
	MSCognitiveServicesAssetAutoTagProviderCompanySettingsFormNavigatorEntry
		extends BaseJSPFormNavigatorEntry<Company>
		implements FormNavigatorEntry<Company> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorAssetAutoTaggerConstants.
			CATEGORY_KEY_COMPANY_SETTINGS_ASSET_AUTO_TAGGER;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_COMPANY_SETTINGS;
	}

	@Override
	public String getKey() {
		return "document-library-image-microsoft-cognitive-services";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.get(
			resourceBundle,
			"microsoft-cognitive-services-asset-auto-tag-provider-" +
				"configuration-name");
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		try {
			_addConfigurationToRequest(request);

			super.include(request, response);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}
	}

	@Override
	public boolean isVisible(User user, Company company) {
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			_assetAutoTaggerConfigurationFactory.
				getCompanyAssetAutoTaggerConfiguration(company);

		return assetAutoTaggerConfiguration.isEnabled();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/portal_settings/microsoft_cognitive_services_auto_tag_" +
			"provider.jsp";
	}

	private void _addConfigurationToRequest(HttpServletRequest request)
		throws ConfigurationException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration
			msCognitiveServicesAssetAutoTagProviderCompanyConfiguration =
				_configurationProvider.getConfiguration(
					MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
						class,
					new CompanyServiceSettingsLocator(
						themeDisplay.getCompanyId(),
						MSCognitiveServicesAssetAutoTagProviderConstants.
							SERVICE_NAME));

		request.setAttribute(
			MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration.class.
				getName(),
			msCognitiveServicesAssetAutoTagProviderCompanyConfiguration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSCognitiveServicesAssetAutoTagProviderCompanySettingsFormNavigatorEntry.class);

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

}