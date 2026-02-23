/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.search.spi.model.index.contributor;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DepotEntry> {

	public DepotEntryModelIndexerWriterContributor(
		DepotEntryLocalService depotEntryLocalService) {

		_depotEntryLocalService = depotEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _depotEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(DepotEntry depotEntry) {
		return IndexerWriterMode.UPDATE;
	}

	private final DepotEntryLocalService _depotEntryLocalService;

}