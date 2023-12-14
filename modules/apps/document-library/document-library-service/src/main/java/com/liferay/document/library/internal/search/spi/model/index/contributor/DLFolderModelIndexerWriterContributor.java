/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Michael C. Han
 */
public class DLFolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<DLFolder> {

	public DLFolderModelIndexerWriterContributor(
		DLFolderLocalService dlFolderLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_dlFolderLocalService = dlFolderLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("mountPoint");

				dynamicQuery.add(property.eq(false));
			});
		batchIndexingActionable.setPerformActionMethod(
			(DLFolder dlFolder) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(dlFolder)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_dlFolderLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(DLFolder dlFolder) {
		return dlFolder.getCompanyId();
	}

	private final DLFolderLocalService _dlFolderLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}