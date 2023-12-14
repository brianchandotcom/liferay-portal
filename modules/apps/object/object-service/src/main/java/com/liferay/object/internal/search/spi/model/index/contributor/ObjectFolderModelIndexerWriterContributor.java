/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Murilo Stodolni
 */
public class ObjectFolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectFolder> {

	public ObjectFolderModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		ObjectFolderLocalService objectFolderLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_objectFolderLocalService = objectFolderLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(ObjectFolder objectFolder) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(objectFolder)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_objectFolderLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(ObjectFolder objectFolder) {
		return objectFolder.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final ObjectFolderLocalService _objectFolderLocalService;

}