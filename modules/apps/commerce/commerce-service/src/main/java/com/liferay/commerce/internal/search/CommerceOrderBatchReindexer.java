/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Danny Situ
 */
public class CommerceOrderBatchReindexer {

	public CommerceOrderBatchReindexer(
		CommerceOrderLocalService commerceOrderLocalService,
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<CommerceOrder> indexerWriter) {

		_commerceOrderLocalService = commerceOrderLocalService;
		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long commerceAccountId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property commerceAccountIdProperty =
					PropertyFactoryUtil.forName("commerceAccountId");

				dynamicQuery.add(
					commerceAccountIdProperty.eq(commerceAccountId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CommerceOrder commerceOrder) ->
				batchIndexingActionable.addDocuments(
					_indexerDocumentBuilder.getDocument(commerceOrder)));

		batchIndexingActionable.performActions();
	}

	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<CommerceOrder> _indexerWriter;

}