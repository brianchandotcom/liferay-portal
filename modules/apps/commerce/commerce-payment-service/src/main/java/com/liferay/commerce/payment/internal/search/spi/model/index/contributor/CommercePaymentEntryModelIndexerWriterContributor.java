/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.payment.internal.search.spi.model.index.contributor;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry",
	service = ModelIndexerWriterContributor.class
)
public class CommercePaymentEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommercePaymentEntry> {

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

	@Reference
	private CommercePaymentEntryLocalService _commercePaymentEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}