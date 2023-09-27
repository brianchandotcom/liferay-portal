/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.internal.search;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
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
@Component(service = CPDefinitionGroupedEntryBatchReindexer.class)
public class CPDefinitionGroupedEntryBatchReindexerImpl
	implements CPDefinitionGroupedEntryBatchReindexer {

	@Override
	public void reindex(long companyId, long entryCProductId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property cpDefinitionIdProperty = PropertyFactoryUtil.forName(
					"entryCProductId");

				dynamicQuery.add(cpDefinitionIdProperty.eq(entryCProductId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) ->
				batchIndexingActionable.addDocuments(
					indexerDocumentBuilder.getDocument(
						cpDefinitionGroupedEntry)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CPDefinitionGroupedEntryLocalService
		cpDefinitionGroupedEntryLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)"
	)
	protected IndexerWriter<CPDefinitionGroupedEntry> indexerWriter;

}