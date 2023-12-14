/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.search.spi.model.index.contributor;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Mahmoud Azzam
 */
public class CommerceCurrencyModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceCurrency> {

	public CommerceCurrencyModelIndexerWriterContributor(
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommerceCurrency commerceCurrency) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commerceCurrency)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commerceCurrencyLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CommerceCurrency commerceCurrency) {
		return commerceCurrency.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceCurrency commerceCurrency) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}