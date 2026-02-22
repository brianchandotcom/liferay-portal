/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.search.spi.model.index.contributor;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTermEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceTermEntry> {

	public CommerceTermEntryModelIndexerWriterContributor(
		CommerceTermEntryLocalService commerceTermEntryLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commerceTermEntryLocalService = commerceTermEntryLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commerceTermEntryLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceTermEntry commerceTermEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceTermEntryLocalService _commerceTermEntryLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}