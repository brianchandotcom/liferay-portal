/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.elasticsearch7.internal.index.settings.contributor;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.settings.IndexSettingsContributor;
import com.liferay.portal.search.spi.settings.IndexSettingsHelper;
import com.liferay.portal.search.spi.settings.TypeMappingsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = IndexSettingsContributor.class)
public class CommerceIndexSettingsContributor
	implements IndexSettingsContributor {

	@Override
	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper) {

		String typeMappings = StringUtil.read(
			getClass(), "dependencies/additional-type-mappings.json");

		typeMappingsHelper.addTypeMappings(indexName, typeMappings);
	}

	@Override
	public void populate(IndexSettingsHelper indexSettingsHelper) {
	}

}