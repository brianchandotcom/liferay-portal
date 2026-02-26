/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceRecordBatchReindexer {

	public DDMFormInstanceRecordBatchReindexer(
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<DDMFormInstanceRecord> indexerWriter) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long formInstanceId, long companyId) {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_indexerWriter.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property formInstanceIdProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				dynamicQuery.add(formInstanceIdProperty.eq(formInstanceId));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(DDMFormInstanceRecord ddmFormInstanceRecord) ->
				indexableActionableDynamicQuery.addDocument(
					_indexerDocumentBuilder.getDocument(
						ddmFormInstanceRecord)));

		indexableActionableDynamicQuery.performActions();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<DDMFormInstanceRecord> _indexerWriter;

}