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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.internal.search.util.DDMFormInstanceRecordBatchReindexer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance",
	service = ModelIndexerWriterContributor.class
)
public class DDMFormInstanceModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDMFormInstance> {

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
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				ddmFormInstanceLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDMFormInstance ddmFormInstance) {
		return ddmFormInstance.getCompanyId();
	}

	@Override
	public void modelIndexed(DDMFormInstance ddmFormInstance) {
		ddmFormInstanceRecordBatchReindexer.reindex(
			ddmFormInstance.getFormInstanceId(),
			ddmFormInstance.getCompanyId());
	}

	@Reference
	protected DDMFormInstanceLocalService ddmFormInstanceLocalService;

	@Reference
	protected DDMFormInstanceRecordBatchReindexer
		ddmFormInstanceRecordBatchReindexer;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

}