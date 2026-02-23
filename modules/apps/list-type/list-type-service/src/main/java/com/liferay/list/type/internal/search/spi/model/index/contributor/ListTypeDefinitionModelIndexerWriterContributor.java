/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.search.spi.model.index.contributor;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Carolina Barbosa
 */
public class ListTypeDefinitionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ListTypeDefinition> {

	public ListTypeDefinitionModelIndexerWriterContributor(
		ListTypeDefinitionLocalService listTypeDefinitionLocalService) {

		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _listTypeDefinitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;

}