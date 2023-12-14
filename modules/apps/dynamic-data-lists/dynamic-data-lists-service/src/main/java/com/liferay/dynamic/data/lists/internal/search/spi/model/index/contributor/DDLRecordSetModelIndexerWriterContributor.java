/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.internal.search.DDLRecordBatchReindexer;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Marcela Cunha
 */
public class DDLRecordSetModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDLRecordSet> {

	public DDLRecordSetModelIndexerWriterContributor(
		DDLRecordBatchReindexer ddlRecordBatchReindexer,
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ddlRecordBatchReindexer = ddlRecordBatchReindexer;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(DDLRecordSet ddlRecordSet) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(ddlRecordSet)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ddlRecordSetLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDLRecordSet ddlRecordSet) {
		return ddlRecordSet.getCompanyId();
	}

	@Override
	public void modelIndexed(DDLRecordSet ddlRecordSet) {
		_ddlRecordBatchReindexer.reindex(
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getCompanyId());
	}

	private final DDLRecordBatchReindexer _ddlRecordBatchReindexer;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}