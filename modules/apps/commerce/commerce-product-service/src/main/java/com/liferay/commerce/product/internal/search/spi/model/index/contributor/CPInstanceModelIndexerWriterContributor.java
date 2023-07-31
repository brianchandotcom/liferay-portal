/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.model.CPInstance",
	service = ModelIndexerWriterContributor.class
)
public class CPInstanceModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPInstance> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CPInstance cpInstance) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(cpInstance)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_cpInstanceLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CPInstance cpInstance) {
		return cpInstance.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(CPInstance cpInstance) {
		return IndexerWriterMode.UPDATE;
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}