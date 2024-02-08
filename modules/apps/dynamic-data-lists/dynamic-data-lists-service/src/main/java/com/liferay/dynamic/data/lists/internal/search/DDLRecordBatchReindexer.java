/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Marcela Cunha
 */
public class DDLRecordBatchReindexer {

	public DDLRecordBatchReindexer(
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<DDLRecord> indexerWriter) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long ddlRecordSetId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property recordIdProperty = PropertyFactoryUtil.forName(
					"recordSetId");

				dynamicQuery.add(recordIdProperty.eq(ddlRecordSetId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(DDLRecord record) -> batchIndexingActionable.addDocuments(
				_indexerDocumentBuilder.getDocument(record)));

		batchIndexingActionable.performActions();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<DDLRecord> _indexerWriter;

}