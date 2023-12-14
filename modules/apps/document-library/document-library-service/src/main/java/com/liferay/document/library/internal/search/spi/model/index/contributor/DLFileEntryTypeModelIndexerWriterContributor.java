/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryTypeModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DLFileEntryType> {

	public DLFileEntryTypeModelIndexerWriterContributor(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(DLFileEntryType dlFileEntryType) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						dlFileEntryType)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_dlFileEntryTypeLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DLFileEntryType dlFileEntryType) {
		return dlFileEntryType.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		DLFileEntryType dlFileEntryType) {

		return IndexerWriterMode.UPDATE;
	}

	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}