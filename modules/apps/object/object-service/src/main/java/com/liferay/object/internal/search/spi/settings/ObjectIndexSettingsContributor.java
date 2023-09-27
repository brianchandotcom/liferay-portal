/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.settings;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.settings.IndexSettingsContributor;
import com.liferay.portal.search.spi.settings.IndexSettingsHelper;
import com.liferay.portal.search.spi.settings.TypeMappingsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Murilo Stodolni
 */
@Component(service = IndexSettingsContributor.class)
public class ObjectIndexSettingsContributor
	implements IndexSettingsContributor {

	@Override
	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper) {

		typeMappingsHelper.addTypeMappings(
			indexName,
			StringUtil.read(
				getClass(), "dependencies/additional-type-mappings.json"));
	}

	@Override
	public void populate(IndexSettingsHelper indexSettingsHelper) {
	}

}