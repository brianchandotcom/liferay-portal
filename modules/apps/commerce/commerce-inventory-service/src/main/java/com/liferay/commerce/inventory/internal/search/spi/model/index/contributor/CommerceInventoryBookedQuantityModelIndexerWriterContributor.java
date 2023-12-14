/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.search.spi.model.index.contributor;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Brian I. Kim
 */
public class CommerceInventoryBookedQuantityModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceInventoryBookedQuantity> {

	public CommerceInventoryBookedQuantityModelIndexerWriterContributor(
		CommerceInventoryBookedQuantityLocalService
			commerceInventoryBookedQuantityLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commerceInventoryBookedQuantityLocalService =
			commerceInventoryBookedQuantityLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commerceInventoryBookedQuantity)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commerceInventoryBookedQuantityLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		return commerceInventoryBookedQuantity.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}