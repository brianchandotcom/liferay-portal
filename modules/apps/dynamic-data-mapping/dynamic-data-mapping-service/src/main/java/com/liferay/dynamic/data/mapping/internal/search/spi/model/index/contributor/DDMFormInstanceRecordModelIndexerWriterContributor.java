/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceRecordModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDMFormInstanceRecord> {

	public DDMFormInstanceRecordModelIndexerWriterContributor(
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmFormInstanceRecordIdProperty =
					PropertyFactoryUtil.forName("formInstanceRecordId");

				DynamicQuery ddmFormInstanceRecordVersionDynamicQuery =
					_ddmFormInstanceRecordVersionLocalService.dynamicQuery();

				ddmFormInstanceRecordVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceRecordId"));

				dynamicQuery.add(
					ddmFormInstanceRecordIdProperty.in(
						ddmFormInstanceRecordVersionDynamicQuery));

				Property ddmFormInstanceProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				DynamicQuery ddmFormInstanceDynamicQuery =
					_ddmFormInstanceLocalService.dynamicQuery();

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
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ddmFormInstanceRecordLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDMFormInstanceRecord ddmFormInstanceRecord) {
		return ddmFormInstanceRecord.getCompanyId();
	}

	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}