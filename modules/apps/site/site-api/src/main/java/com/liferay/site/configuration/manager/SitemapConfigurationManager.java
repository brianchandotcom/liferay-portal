/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.configuration.manager;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Lourdes Fernández Besada
 */
public interface SitemapConfigurationManager {

	public boolean includeCategoriesCompanyEnabled(long companyId)
		throws ConfigurationException;

	public boolean includePagesCompanyEnabled(long companyId)
		throws ConfigurationException;

	public boolean includeWebContentCompanyEnabled(long companyId)
		throws ConfigurationException;

	public void saveSitemapCompanyConfiguration(
			long companyId, boolean includeCategories, boolean includePages,
			boolean includeWebContent)
		throws ConfigurationException;

}