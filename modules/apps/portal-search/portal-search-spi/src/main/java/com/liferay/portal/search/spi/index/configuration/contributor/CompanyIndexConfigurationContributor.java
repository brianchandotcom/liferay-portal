/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.configuration.contributor;

import com.liferay.portal.search.spi.index.configuration.contributor.helper.MappingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.SettingsHelper;

/**
 * This interface defines methods for contributing mappings and settings to the
 * company indexes in the search engine.
 *
 * @author Adam Brandizzi
 */
public interface CompanyIndexConfigurationContributor {

	/**
	 * This method allows contributors to add search engine mappings via the provided {@link MappingsHelper}.
	 *
	 * Implementations of this method should use the {@link MappingsHelper#putMappings(String)} method to
	 * add the desired mappings to the search engine.
	 *
	 * @param companyId the company id that corresponds to the index that the mappings will be applied to.
	 * @param mappingsHelper An instance of {@link MappingsHelper} used to apply search engine mappings.
	 */
	public void contributeMappings(
		long companyId, MappingsHelper mappingsHelper);

	/**
	 * This method allows contributors to add search engine settings via the provided {@link SettingsHelper}.
	 *
	 * Implementations of this method should use the {@link SettingsHelper#put(String, String)} method to
	 * add the desired settings to the search engine.
	 *
	 * @param companyId the company id that corresponds to the index that the settings will be applied to.
	 * @param settingsHelper An instance of {@link SettingsHelper} used to apply search engine settings.
	 */
	public void contributeSettings(
		long companyId, SettingsHelper settingsHelper);

}