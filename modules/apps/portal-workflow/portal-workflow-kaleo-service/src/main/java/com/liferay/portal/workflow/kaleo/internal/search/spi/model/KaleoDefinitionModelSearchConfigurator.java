/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model;

import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor.KaleoDefinitionModelIndexerWriterContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ModelSearchConfigurator.class)
public class KaleoDefinitionModelSearchConfigurator
	implements ModelSearchConfigurator<KaleoDefinition> {

	@Override
	public String getClassName() {
		return KaleoDefinition.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<KaleoDefinition>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new KaleoDefinitionModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_kaleoDefinitionLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	private ModelIndexerWriterContributor<KaleoDefinition>
		_modelIndexWriterContributor;

}