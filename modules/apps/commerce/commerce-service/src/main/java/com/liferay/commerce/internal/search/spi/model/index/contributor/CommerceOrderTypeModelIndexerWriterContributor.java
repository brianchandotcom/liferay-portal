/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderTypeModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceOrderType> {

	public CommerceOrderTypeModelIndexerWriterContributor(
		CommerceOrderTypeLocalService commerceOrderTypeLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commerceOrderTypeLocalService = commerceOrderTypeLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommerceOrderType commerceOrderType) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commerceOrderType)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commerceOrderTypeLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CommerceOrderType commerceOrderType) {
		return commerceOrderType.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceOrderType commerceOrderType) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceOrderTypeLocalService _commerceOrderTypeLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}