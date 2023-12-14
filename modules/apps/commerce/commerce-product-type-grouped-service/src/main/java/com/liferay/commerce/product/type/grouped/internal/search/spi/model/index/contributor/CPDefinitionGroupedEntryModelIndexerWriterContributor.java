/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Brian I. Kim
 */
public class CPDefinitionGroupedEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPDefinitionGroupedEntry> {

	public CPDefinitionGroupedEntryModelIndexerWriterContributor(
		CPDefinitionGroupedEntryLocalService
			cpDefinitionGroupedEntryLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_cpDefinitionGroupedEntryLocalService =
			cpDefinitionGroupedEntryLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						cpDefinitionGroupedEntry)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_cpDefinitionGroupedEntryLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		return cpDefinitionGroupedEntry.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}