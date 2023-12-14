/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
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

/**
 * @author Marcela Cunha
 */
public class DDLRecordModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DDLRecord> {

	public DDLRecordModelIndexerWriterContributor(
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDLRecordVersionLocalService ddlRecordVersionLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_ddlRecordLocalService = ddlRecordLocalService;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property recordIdProperty = PropertyFactoryUtil.forName(
					"recordId");

				DynamicQuery recordVersionDynamicQuery =
					_ddlRecordVersionLocalService.dynamicQuery();

				recordVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("recordId"));

				dynamicQuery.add(
					recordIdProperty.in(recordVersionDynamicQuery));

				Property recordSetProperty = PropertyFactoryUtil.forName(
					"recordSetId");

				DynamicQuery recordSetDynamicQuery =
					_ddlRecordSetLocalService.dynamicQuery();

				recordSetDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("recordSetId"));

				Property scopeProperty = PropertyFactoryUtil.forName("scope");

				recordSetDynamicQuery.add(scopeProperty.in(_SCOPES));

				dynamicQuery.add(recordSetProperty.in(recordSetDynamicQuery));
			});
		batchIndexingActionable.setPerformActionMethod(
			(DDLRecord record) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(record)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_ddlRecordLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DDLRecord ddlRecord) {
		return ddlRecord.getCompanyId();
	}

	private static final int[] _SCOPES = {
		DDLRecordSetConstants.SCOPE_DATA_ENGINE,
		DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS,
		DDLRecordSetConstants.SCOPE_FORMS,
		DDLRecordSetConstants.SCOPE_KALEO_FORMS
	};

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDLRecordVersionLocalService _ddlRecordVersionLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}