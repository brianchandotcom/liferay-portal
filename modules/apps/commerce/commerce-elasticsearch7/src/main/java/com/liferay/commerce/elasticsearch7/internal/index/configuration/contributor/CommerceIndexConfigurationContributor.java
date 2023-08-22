/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.elasticsearch7.internal.index.configuration.contributor;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.index.configuration.contributor.IndexConfigurationContributor;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.IndexSettingsHelper;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.TypeMappingsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = IndexConfigurationContributor.class)
public class CommerceIndexConfigurationContributor
	implements IndexConfigurationContributor {

	@Override
	public void contributeMappings(TypeMappingsHelper typeMappingsHelper) {
		String typeMappings = StringUtil.read(
			getClass(), "dependencies/additional-type-mappings.json");

		typeMappingsHelper.putTypeMappings(typeMappings);
	}

	@Override
	public void contributeSettings(IndexSettingsHelper indexSettingsHelper) {
	}

}