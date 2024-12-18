/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.configuration;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;

import java.util.Dictionary;

/**
 * @author Keven Leone
 */
public class MarketplaceConfigurationUtil {

	public static void deleteMarketplaceConfiguration(long companyId)
		throws Exception {

		ConfigurationProviderUtil.deleteCompanyConfiguration(
			MarketplaceConfiguration.class, companyId);
	}

	public static String getConfiguration(String key) {
		return _configuration.get(key);
	}

	public static MarketplaceConfiguration getMarketplaceConfiguration(
			long companyId)
		throws Exception {

		return ConfigurationProviderUtil.getCompanyConfiguration(
			MarketplaceConfiguration.class, companyId);
	}

	public static void saveMarketplaceConfiguration(
			long companyId, Dictionary<String, Object> properties)
		throws Exception {

		ConfigurationProviderUtil.saveCompanyConfiguration(
			MarketplaceConfiguration.class, companyId, properties);
	}

	private static final Configuration _configuration =
		ConfigurationFactoryUtil.getConfiguration(
			MarketplaceConfigurationUtil.class.getClassLoader(), "marketplace");

}