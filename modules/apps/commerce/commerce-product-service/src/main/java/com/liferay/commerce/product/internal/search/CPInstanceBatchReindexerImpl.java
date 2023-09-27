/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
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
@Component(service = CPInstanceBatchReindexer.class)
public class CPInstanceBatchReindexerImpl implements CPInstanceBatchReindexer {

	@Override
	public void reindex(long companyId, long cpInstanceId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property cpInstanceIdProperty = PropertyFactoryUtil.forName(
					"cpInstanceId");

				dynamicQuery.add(cpInstanceIdProperty.eq(cpInstanceId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(CPInstance cpInstance) -> batchIndexingActionable.addDocuments(
				indexerDocumentBuilder.getDocument(cpInstance)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected CPInstanceLocalService cpInstanceLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPInstance)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPInstance)"
	)
	protected IndexerWriter<CPInstance> indexerWriter;

}