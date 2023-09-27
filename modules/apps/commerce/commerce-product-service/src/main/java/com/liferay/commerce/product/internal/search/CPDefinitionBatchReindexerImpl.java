/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
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
@Component(service = CPDefinitionBatchReindexer.class)
public class CPDefinitionBatchReindexerImpl
	implements CPDefinitionBatchReindexer {

	@Override
	public void reindex(long companyId, long cpDefinitionId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property cpDefinitionIdProperty = PropertyFactoryUtil.forName(
					"cpDefinitionId");

				dynamicQuery.add(cpDefinitionIdProperty.eq(cpDefinitionId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CPDefinition cpDefinition) -> batchIndexingActionable.addDocuments(
				indexerDocumentBuilder.getDocument(cpDefinition)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CPDefinitionLocalService cpDefinitionLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	protected IndexerWriter<CPDefinition> indexerWriter;

}