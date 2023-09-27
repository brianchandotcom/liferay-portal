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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = CommerceOrderBatchReindexer.class)
public class CommerceOrderBatchReindexerImpl
	implements CommerceOrderBatchReindexer {

	@Override
	public void reindex(long commerceAccountId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

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
					indexerDocumentBuilder.getDocument(commerceOrder)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CommerceOrderLocalService commerceOrderLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	protected IndexerWriter<CommerceOrder> indexerWriter;

}