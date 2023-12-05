/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration.manager;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;
import com.liferay.site.internal.configuration.SitemapCompanyConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = SitemapConfigurationManager.class)
public class SitemapConfigurationManagerImpl
	implements SitemapConfigurationManager {

	@Override
	public boolean includeCategories(long companyId)
		throws ConfigurationException {

		SitemapCompanyConfiguration sitemapCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SitemapCompanyConfiguration.class, companyId);

		return sitemapCompanyConfiguration.includeCategories();
	}

	@Override
	public boolean includePages(long companyId) throws ConfigurationException {
		SitemapCompanyConfiguration sitemapCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SitemapCompanyConfiguration.class, companyId);

		return sitemapCompanyConfiguration.includePages();
	}

	@Override
	public boolean includeWebContent(long companyId)
		throws ConfigurationException {

		SitemapCompanyConfiguration sitemapCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SitemapCompanyConfiguration.class, companyId);

		return sitemapCompanyConfiguration.includeWebContent();
	}

	@Override
	public void saveSitemapCompanyConfiguration(
			long companyId, boolean includeCategories, boolean includePages,
			boolean includeWebContent)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			SitemapCompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"includeCategories", includeCategories
			).put(
				"includePages", includePages
			).put(
				"includeWebContent", includeWebContent
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}