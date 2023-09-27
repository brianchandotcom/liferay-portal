/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.search;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = CommerceInventoryBookedQuantityBatchReindexer.class)
public class CommerceInventoryBookedQuantityBatchReindexerImpl
	implements CommerceInventoryBookedQuantityBatchReindexer {

	@Override
	public void reindex(long companyId, String sku) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property skuProperty = PropertyFactoryUtil.forName("sku");

				dynamicQuery.add(skuProperty.eq(sku));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) ->
				batchIndexingActionable.addDocuments(
					indexerDocumentBuilder.getDocument(
						commerceInventoryBookedQuantity)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CommerceInventoryBookedQuantityLocalService
		commerceInventoryBookedQuantityLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity)"
	)
	protected IndexerWriter<CommerceOrder> indexerWriter;

}