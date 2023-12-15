/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.search;

import com.liferay.exportimport.internal.search.spi.model.index.contributor.ExportImportConfigurationModelIndexerWriterContributor;
import com.liferay.exportimport.internal.search.spi.model.result.contributor.ExportImportConfigurationModelSummaryContributor;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Akos Thurzo
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class ExportImportConfigurationModelSearchConfigurator
	implements ModelSearchConfigurator<ExportImportConfiguration> {

	@Override
	public String getClassName() {
		return ExportImportConfiguration.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.UID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK
		};
	}

	@Override
	public ModelIndexerWriterContributor<ExportImportConfiguration>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new ExportImportConfigurationModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_exportImportConfigurationLocalService);

		_modelSummaryContributor =
			new ExportImportConfigurationModelSummaryContributor();
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	private ModelIndexerWriterContributor<ExportImportConfiguration>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}