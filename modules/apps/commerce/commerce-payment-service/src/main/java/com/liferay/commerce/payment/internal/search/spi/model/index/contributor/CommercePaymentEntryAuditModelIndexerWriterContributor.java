/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.commerce.payment.service.CommercePaymentEntryAuditLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryAuditModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommercePaymentEntryAudit> {

	public CommercePaymentEntryAuditModelIndexerWriterContributor(
		CommercePaymentEntryAuditLocalService
			commercePaymentEntryAuditLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commercePaymentEntryAuditLocalService =
			commercePaymentEntryAuditLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommercePaymentEntryAudit commercePaymentEntryAudit) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commercePaymentEntryAudit)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commercePaymentEntryAuditLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		return commercePaymentEntryAudit.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommercePaymentEntryAudit commercePaymentEntryAudit) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommercePaymentEntryAuditLocalService
		_commercePaymentEntryAuditLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}