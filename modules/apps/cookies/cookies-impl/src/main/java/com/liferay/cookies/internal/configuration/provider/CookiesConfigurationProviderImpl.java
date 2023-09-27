/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.configuration.provider;

import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.cookies.configuration.banner.CookiesBannerConfiguration;
import com.liferay.cookies.configuration.consent.CookiesConsentConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = CookiesConfigurationProvider.class)
public class CookiesConfigurationProviderImpl
	implements CookiesConfigurationProvider {

	@Override
	public CookiesBannerConfiguration getCookiesBannerConfiguration(
			ThemeDisplay themeDisplay)
		throws Exception {

		return _getCookiesConfiguration(
			CookiesBannerConfiguration.class, themeDisplay);
	}

	@Override
	public CookiesConsentConfiguration getCookiesConsentConfiguration(
			ThemeDisplay themeDisplay)
		throws Exception {

		return _getCookiesConfiguration(
			CookiesConsentConfiguration.class, themeDisplay);
	}

	@Override
	public CookiesPreferenceHandlingConfiguration
			getCookiesPreferenceHandlingConfiguration(ThemeDisplay themeDisplay)
		throws Exception {

		return _getCookiesConfiguration(
			CookiesPreferenceHandlingConfiguration.class, themeDisplay);
	}

	private <T> T _getCookiesConfiguration(
			Class<T> clazz, ThemeDisplay themeDisplay)
		throws Exception {

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			themeDisplay.getServerName());

		if (layoutSet != null) {
			Group group = layoutSet.getGroup();

			return _configurationProvider.getGroupConfiguration(
				clazz, group.getGroupId());
		}

		return _configurationProvider.getCompanyConfiguration(
			clazz, themeDisplay.getCompanyId());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}