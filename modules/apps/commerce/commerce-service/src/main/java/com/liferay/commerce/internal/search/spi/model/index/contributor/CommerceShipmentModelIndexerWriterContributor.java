/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceShipment> {

	public CommerceShipmentModelIndexerWriterContributor(
		CommerceShipmentLocalService commerceShipmentLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_commerceShipmentLocalService = commerceShipmentLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CommerceShipment commerceShipment) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						commerceShipment)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_commerceShipmentLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(CommerceShipment commerceShipment) {
		return commerceShipment.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceShipment commerceShipment) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceShipmentLocalService _commerceShipmentLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}