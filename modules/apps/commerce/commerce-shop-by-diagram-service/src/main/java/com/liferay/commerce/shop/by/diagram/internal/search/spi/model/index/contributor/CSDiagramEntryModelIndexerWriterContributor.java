/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CSDiagramEntry> {

	public CSDiagramEntryModelIndexerWriterContributor(
		CSDiagramEntryLocalService csDiagramEntryLocalService) {

		_csDiagramEntryLocalService = csDiagramEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _csDiagramEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CSDiagramEntry csDiagramEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CSDiagramEntryLocalService _csDiagramEntryLocalService;

}