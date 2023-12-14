/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Gleice Lisbino
 */
public class ObjectRelationshipModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectRelationship> {

	public ObjectRelationshipModelIndexerWriterContributor(
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		ObjectRelationshipLocalService
			objectRelationshipLocalServiceLocalService) {

		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_objectRelationshipLocalServiceLocalService =
			objectRelationshipLocalServiceLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(ObjectRelationship objectRelationship) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						objectRelationship)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_objectRelationshipLocalServiceLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(ObjectRelationship objectRelationship) {
		return objectRelationship.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalServiceLocalService;

}