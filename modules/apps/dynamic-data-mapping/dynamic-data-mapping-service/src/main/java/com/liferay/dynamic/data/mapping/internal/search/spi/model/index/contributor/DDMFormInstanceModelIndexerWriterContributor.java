/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.internal.search.DDMFormInstanceRecordBatchReindexer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDMFormInstance> {

	public DDMFormInstanceModelIndexerWriterContributor(
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMFormInstanceRecordBatchReindexer ddmFormInstanceRecordBatchReindexer,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmFormInstanceRecordBatchReindexer =
			ddmFormInstanceRecordBatchReindexer;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(DDMFormInstance ddmFormInstance) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						ddmFormInstance)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ddmFormInstanceLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDMFormInstance ddmFormInstance) {
		return ddmFormInstance.getCompanyId();
	}

	@Override
	public void modelIndexed(DDMFormInstance ddmFormInstance) {
		_ddmFormInstanceRecordBatchReindexer.reindex(
			ddmFormInstance.getFormInstanceId(),
			ddmFormInstance.getCompanyId());
	}

	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private final DDMFormInstanceRecordBatchReindexer
		_ddmFormInstanceRecordBatchReindexer;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}