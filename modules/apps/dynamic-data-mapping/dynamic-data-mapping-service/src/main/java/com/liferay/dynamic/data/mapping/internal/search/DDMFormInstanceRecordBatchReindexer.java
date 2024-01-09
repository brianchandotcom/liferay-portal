/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceRecordBatchReindexer {

	public DDMFormInstanceRecordBatchReindexer(
		DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<DDMFormInstanceRecord> indexerWriter) {

		_formInstanceRecordLocalService = formInstanceRecordLocalService;
		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long formInstanceId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property formInstanceIdProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				dynamicQuery.add(formInstanceIdProperty.eq(formInstanceId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(DDMFormInstanceRecord ddmFormInstanceRecord) ->
				batchIndexingActionable.addDocuments(
					_indexerDocumentBuilder.getDocument(
						ddmFormInstanceRecord)));

		batchIndexingActionable.performActions();
	}

	private final DDMFormInstanceRecordLocalService
		_formInstanceRecordLocalService;
	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<DDMFormInstanceRecord> _indexerWriter;

}