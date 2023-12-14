/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Pei-Jung Lan
 */
public class CTEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTEntry> {

	public CTEntryModelIndexerWriterContributor(
		CTEntryLocalService ctEntryLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ctEntryLocalService = ctEntryLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CTEntry ctEntry) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(ctEntry)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ctEntryLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CTEntry ctEntry) {
		return ctEntry.getCompanyId();
	}

	private final CTEntryLocalService _ctEntryLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}