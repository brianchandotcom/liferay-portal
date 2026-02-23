/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.search.spi.model.index.contributor;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alessio Antonio Rendina
 */
public class COREntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<COREntry> {

	public COREntryModelIndexerWriterContributor(
		COREntryLocalService corEntryLocalService) {

		_corEntryLocalService = corEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _corEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(COREntry corEntry) {
		return IndexerWriterMode.UPDATE;
	}

	private final COREntryLocalService _corEntryLocalService;

}