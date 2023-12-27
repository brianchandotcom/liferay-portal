/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.search;

import com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor.CSDiagramEntryModelIndexerWriterContributor;
import com.liferay.commerce.shop.by.diagram.internal.search.spi.model.result.contributor.CSDiagramEntryModelSummaryContributor;
import com.liferay.commerce.shop.by.diagram.internal.search.spi.model.result.contributor.CSDiagramEntryModelVisibilityContributor;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelSearchConfigurator.class)
public class CSDiagramEntryModelSearchConfigurator
	implements ModelSearchConfigurator<CSDiagramEntry> {

	@Override
	public String getClassName() {
		return CSDiagramEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CSDiagramEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CSDiagramEntryModelIndexerWriterContributor(
				_csDiagramEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
		_modelVisibilityContributor =
			new CSDiagramEntryModelVisibilityContributor(
				_csDiagramEntryLocalService);
	}

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CSDiagramEntry>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CSDiagramEntryModelSummaryContributor();
	private ModelVisibilityContributor _modelVisibilityContributor;

}