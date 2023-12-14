/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommercePaymentEntry> {

	public CommercePaymentEntryModelIndexerWriterContributor(
		CommercePaymentEntryLocalService commercePaymentEntryLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commercePaymentEntryLocalService = commercePaymentEntryLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommercePaymentEntry commercePaymentEntry) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commercePaymentEntry)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commercePaymentEntryLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CommercePaymentEntry commercePaymentEntry) {
		return commercePaymentEntry.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommercePaymentEntry commercePaymentEntry) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommercePaymentEntryLocalService
		_commercePaymentEntryLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}