/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPSpecificationOption> {

	public CPSpecificationOptionModelIndexerWriterContributor(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CPSpecificationOption cpSpecificationOption) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						cpSpecificationOption)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_cpSpecificationOptionLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CPSpecificationOption cpSpecificationOption) {
		return cpSpecificationOption.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPSpecificationOption cpSpecificationOption) {

		return IndexerWriterMode.UPDATE;
	}

	private final CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}