/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
public class ObjectEntryBatchReindexerImpl
	implements ObjectEntryBatchReindexer {

	public ObjectEntryBatchReindexerImpl(
		IndexerDocumentBuilder indexerDocumentBuilder,
		ModelIndexerWriterContributor<ObjectEntry>
			modelIndexerWriterContributor,
		ObjectDefinition objectDefinition) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_modelIndexerWriterContributor = modelIndexerWriterContributor;
		_objectDefinition = objectDefinition;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public void reindex(long accountEntryId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_modelIndexerWriterContributor.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName(
					"objectDefinitionId");

				dynamicQuery.add(
					property.eq(_objectDefinition.getObjectDefinitionId()));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(ObjectEntry objectEntry) -> batchIndexingActionable.addDocuments(
				_indexerDocumentBuilder.getDocument(objectEntry)));

		batchIndexingActionable.performActions();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final ModelIndexerWriterContributor<ObjectEntry>
		_modelIndexerWriterContributor;
	private final ObjectDefinition _objectDefinition;

}