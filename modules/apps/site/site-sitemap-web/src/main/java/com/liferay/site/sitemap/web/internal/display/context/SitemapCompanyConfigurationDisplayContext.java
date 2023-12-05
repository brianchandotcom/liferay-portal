/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.sitemap.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;

/**
 * @author Lourdes Fernández Besada
 */
public class SitemapCompanyConfigurationDisplayContext {

	public SitemapCompanyConfigurationDisplayContext(
		SitemapConfigurationManager sitemapConfigurationManager,
		ThemeDisplay themeDisplay) {

		_sitemapConfigurationManager = sitemapConfigurationManager;
		_themeDisplay = themeDisplay;
	}

	public boolean includeCategories() throws ConfigurationException {
		return _sitemapConfigurationManager.includeCategories(
			_themeDisplay.getCompanyId());
	}

	public boolean includePages() throws ConfigurationException {
		return _sitemapConfigurationManager.includePages(
			_themeDisplay.getCompanyId());
	}

	public boolean includeWebContent() throws ConfigurationException {
		return _sitemapConfigurationManager.includeWebContent(
			_themeDisplay.getCompanyId());
	}

	private final SitemapConfigurationManager _sitemapConfigurationManager;
	private final ThemeDisplay _themeDisplay;

}