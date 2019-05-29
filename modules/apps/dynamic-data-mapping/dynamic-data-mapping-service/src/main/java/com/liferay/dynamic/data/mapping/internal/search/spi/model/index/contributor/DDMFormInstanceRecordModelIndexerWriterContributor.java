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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
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
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = ModelIndexerWriterContributor.class
)
public class DDMFormInstanceRecordModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDMFormInstanceRecord> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmFormInstanceRecordIdProperty =
					PropertyFactoryUtil.forName("formInstanceRecordId");

				DynamicQuery ddmFormInstanceRecordVersionDynamicQuery =
					ddmFormInstanceRecordVersionLocalService.dynamicQuery();

				ddmFormInstanceRecordVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceRecordId"));

				dynamicQuery.add(
					ddmFormInstanceRecordIdProperty.in(
						ddmFormInstanceRecordVersionDynamicQuery));

				Property ddmFormInstanceProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				DynamicQuery ddmFormInstanceDynamicQuery =
					ddmFormInstanceLocalService.dynamicQuery();

				ddmFormInstanceDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceId"));

				dynamicQuery.add(
					ddmFormInstanceProperty.in(ddmFormInstanceDynamicQuery));
			});
		batchIndexingActionable.setPerformActionMethod(
			(DDMFormInstanceRecord ddmFormInstanceRecord) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						ddmFormInstanceRecord)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				ddmFormInstanceRecordLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDMFormInstanceRecord ddmFormInstanceRecord) {
		return ddmFormInstanceRecord.getCompanyId();
	}

	@Reference
	protected DDMFormInstanceLocalService ddmFormInstanceLocalService;

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	@Reference
	protected DDMFormInstanceRecordVersionLocalService
		ddmFormInstanceRecordVersionLocalService;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

}