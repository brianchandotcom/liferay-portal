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