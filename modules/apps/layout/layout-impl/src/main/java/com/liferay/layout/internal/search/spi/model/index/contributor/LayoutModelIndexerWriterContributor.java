/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Vagner B.C
 */
public class LayoutModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Layout> {

	public LayoutModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		LayoutLocalService layoutLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(Layout layout) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(layout)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_layoutLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Layout layout) {
		return layout.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(Layout layout) {
		return IndexerWriterMode.UPDATE;
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final LayoutLocalService _layoutLocalService;

}