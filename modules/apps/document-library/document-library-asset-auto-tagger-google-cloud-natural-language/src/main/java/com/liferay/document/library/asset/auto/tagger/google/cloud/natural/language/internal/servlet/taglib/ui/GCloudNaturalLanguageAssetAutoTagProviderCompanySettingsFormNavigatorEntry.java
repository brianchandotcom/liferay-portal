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

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.servlet.taglib.ui;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.asset.auto.tagger.constants.FormNavigatorAssetAutoTaggerConstants;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
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
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alicia García
 */
@Component(immediate = true, service = FormNavigatorEntry.class)
public class
	GCloudNaturalLanguageAssetAutoTagProviderCompanySettingsFormNavigatorEntry
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
		return "document-library-document-google-cloud-natural-language";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			_resourceBundleLoader.loadResourceBundle(locale),
			"google-cloud-natural-language-asset-auto-tag-provider-" +
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
		target = "(osgi.web.symbolicname=com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/portal_settings/google_cloud_natural_language_auto_tag_" +
			"provider.jsp";
	}

	private void _addConfigurationToRequest(HttpServletRequest request)
		throws ConfigurationException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration =
				_configurationProvider.getConfiguration(
					GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.class,
					new CompanyServiceSettingsLocator(
						themeDisplay.getCompanyId(),
						GCloudNaturalLanguageAssetAutoTagProviderConstants.
							SERVICE_NAME));

		request.setAttribute(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.class.
				getName(),
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GCloudNaturalLanguageAssetAutoTagProviderCompanySettingsFormNavigatorEntry.class);

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}