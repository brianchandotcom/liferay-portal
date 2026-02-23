/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.search.spi.model.index.contributor;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Carolina Barbosa
 */
public class ListTypeEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ListTypeEntry> {

	public ListTypeEntryModelIndexerWriterContributor(
		ListTypeEntryLocalService listTypeEntryLocalService) {

		_listTypeEntryLocalService = listTypeEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _listTypeEntryLocalService.getIndexableActionableDynamicQuery();
	}

	private final ListTypeEntryLocalService _listTypeEntryLocalService;

}