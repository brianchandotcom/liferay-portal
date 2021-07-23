/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry",
	service = ModelIndexerWriterContributor.class
)
public class CPDefinitionDiagramEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CPDefinitionDiagramEntry> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(CPDefinitionDiagramEntry cpDefinitionDiagramEntry) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						cpDefinitionDiagramEntry)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_cpDefinitionDiagramEntryLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return cpDefinitionDiagramEntry.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return IndexerWriterMode.UPDATE;
	}

	@Reference
	private CPDefinitionDiagramEntryLocalService
		_cpDefinitionDiagramEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}