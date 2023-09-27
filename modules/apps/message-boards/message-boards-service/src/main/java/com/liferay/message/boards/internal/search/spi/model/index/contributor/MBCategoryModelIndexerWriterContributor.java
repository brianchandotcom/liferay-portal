/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.index.contributor;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBCategory",
	service = ModelIndexerWriterContributor.class
)
public class MBCategoryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<MBCategory> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_IN_TRASH
						}));
			});
		batchIndexingActionable.setPerformActionMethod(
			(MBCategory mbCategory) -> {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Reindexing message boards categories for ",
							"category ID ", mbCategory.getCategoryId(),
							" and group ID ", mbCategory.getGroupId()));
				}

				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(mbCategory));
			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_mbCategoryLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(MBCategory mbCategory) {
		return mbCategory.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(MBCategory mbCategory) {
		int status = mbCategory.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) ||
			(status == WorkflowConstants.STATUS_IN_TRASH)) {

			return IndexerWriterMode.UPDATE;
		}

		return IndexerWriterMode.DELETE;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBCategoryModelIndexerWriterContributor.class);

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

}