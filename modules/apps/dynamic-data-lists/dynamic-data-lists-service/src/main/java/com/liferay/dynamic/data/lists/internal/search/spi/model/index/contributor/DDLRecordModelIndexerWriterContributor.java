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

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = ModelIndexerWriterContributor.class
)
public class DDLRecordModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDLRecord> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property recordIdProperty = PropertyFactoryUtil.forName(
					"recordId");

				DynamicQuery recordVersionDynamicQuery =
					recordVersionLocalService.dynamicQuery();

				recordVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("recordId"));

				dynamicQuery.add(
					recordIdProperty.in(recordVersionDynamicQuery));

				Property recordSetProperty = PropertyFactoryUtil.forName(
					"recordSetId");

				DynamicQuery recordSetDynamicQuery =
					recordSetLocalService.dynamicQuery();

				recordSetDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("recordSetId"));

				dynamicQuery.add(recordSetProperty.in(recordSetDynamicQuery));
			});
		batchIndexingActionable.setPerformActionMethod(
			(DDLRecord record) -> {
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(record));
			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				recordLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDLRecord record) {
		return record.getCompanyId();
	}

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

	@Reference
	protected DDLRecordLocalService recordLocalService;

	@Reference
	protected DDLRecordSetLocalService recordSetLocalService;

	@Reference
	protected DDLRecordVersionLocalService recordVersionLocalService;

}