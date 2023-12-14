/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectAction;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Marco Leo
 */
public class ObjectActionModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectAction> {

	public ObjectActionModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		ObjectActionLocalService objectActionLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_objectActionLocalService = objectActionLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(ObjectAction objectAction) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(objectAction)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_objectActionLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(ObjectAction objectAction) {
		return objectAction.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final ObjectActionLocalService _objectActionLocalService;

}