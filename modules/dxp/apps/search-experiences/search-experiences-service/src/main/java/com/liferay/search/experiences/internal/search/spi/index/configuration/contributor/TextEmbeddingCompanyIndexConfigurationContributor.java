/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.index.configuration.contributor;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.index.configuration.contributor.CompanyIndexConfigurationContributor;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.MappingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.SettingsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(
	enabled = false, service = CompanyIndexConfigurationContributor.class
)
public class TextEmbeddingCompanyIndexConfigurationContributor
	implements CompanyIndexConfigurationContributor {

	@Override
	public void contributeMappings(
		long companyId, MappingsHelper mappingsHelper) {

		mappingsHelper.putMappings(
			StringUtil.read(
				getClass(),
				"dependencies/text-embedding-dynamic-templates.json"));
	}

	@Override
	public void contributeSettings(
		long companyId, SettingsHelper settingsHelper) {
	}

}