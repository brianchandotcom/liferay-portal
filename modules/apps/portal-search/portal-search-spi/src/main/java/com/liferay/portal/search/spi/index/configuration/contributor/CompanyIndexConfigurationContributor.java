/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.configuration.contributor;

import com.liferay.portal.search.spi.index.configuration.contributor.helper.MappingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.SettingsHelper;

/**
 * @author Adam Brandizzi
 */
public interface CompanyIndexConfigurationContributor {

	public void contributeMappings(
		long companyId, MappingsHelper mappingsHelper);

	public void contributeSettings(
		long companyId, SettingsHelper settingsHelper);

}