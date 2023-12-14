/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author David Truong
 */
public class CTCollectionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CTCollection> {

	public CTCollectionModelIndexerWriterContributor(
		CTCollectionLocalService ctCollectionLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CTCollection ctCollection) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(ctCollection)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ctCollectionLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CTCollection ctCollection) {
		return ctCollection.getCompanyId();
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}