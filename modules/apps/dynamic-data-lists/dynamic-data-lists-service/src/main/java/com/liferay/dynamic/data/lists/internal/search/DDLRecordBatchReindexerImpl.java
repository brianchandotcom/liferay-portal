/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(service = DDLRecordBatchReindexer.class)
public class DDLRecordBatchReindexerImpl implements DDLRecordBatchReindexer {

	@Override
	public void reindex(long ddlRecordSetId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property recordIdProperty = PropertyFactoryUtil.forName(
					"recordSetId");

				dynamicQuery.add(recordIdProperty.eq(ddlRecordSetId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(DDLRecord record) -> batchIndexingActionable.addDocuments(
				indexerDocumentBuilder.getDocument(record)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected DDLRecordLocalService ddlRecordLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)"
	)
	protected IndexerWriter<DDLRecord> indexerWriter;

}