/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.configuration.contributor;

import com.liferay.portal.search.spi.index.configuration.contributor.helper.MappingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.SettingsHelper;

/**
 * Implement this interface to contribute mappings and settings to company
 * indexes in the search engine.
 *
 * @author Adam Brandizzi
 */
public interface CompanyIndexConfigurationContributor {

	/**
	 * Adds search engine mappings via the provided {@link MappingsHelper}.
	 *
	 * <p>
	 * Implementations of this method should use the {@link
	 * MappingsHelper#putMappings(String)} method to add the desired mappings to
	 * the search engine.
	 * </p>
	 *
	 * @param companyId the company ID of the index targeted by the new
	 *        mappings.
	 * @param mappingsHelper An instance of {@link MappingsHelper} used to apply
	 *        search engine mappings.
	 */
	public void contributeMappings(
		long companyId, MappingsHelper mappingsHelper);

	/**
	 * Adds search engine settings via the provided {@link SettingsHelper}.
	 *
	 * <p>
	 * Implementations of this method should use the {@link
	 * SettingsHelper#put(String, String)} method to add the desired settings to
	 * the search engine.
	 * </p>
	 *
	 * @param companyId the company ID of the index targeted by the new
	 *        settings.
	 * @param settingsHelper An instance of {@link SettingsHelper} used to apply
	 *        search engine settings.
	 */
	public void contributeSettings(
		long companyId, SettingsHelper settingsHelper);

}