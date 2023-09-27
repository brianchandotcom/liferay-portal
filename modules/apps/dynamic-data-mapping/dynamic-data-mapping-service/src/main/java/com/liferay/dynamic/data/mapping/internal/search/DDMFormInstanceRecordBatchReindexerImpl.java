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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = DDMFormInstanceRecordBatchReindexer.class)
public class DDMFormInstanceRecordBatchReindexerImpl
	implements DDMFormInstanceRecordBatchReindexer {

	@Override
	public void reindex(long formInstanceId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			indexerWriter.getBatchIndexingActionable();

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
					indexerDocumentBuilder.getDocument(ddmFormInstanceRecord)));

		batchIndexingActionable.performActions();
	}

	@Reference
	protected DDMFormInstanceRecordLocalService formInstanceRecordLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	protected IndexerWriter<DDMFormInstanceRecord> indexerWriter;

}