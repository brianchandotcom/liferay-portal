/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.internal.search.spi.model.index.contributor.CPConfigurationListModelIndexerWriterContributor;
import com.liferay.commerce.product.internal.search.spi.model.result.contributor.CPDefinitionModelSummaryContributor;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = ModelSearchConfigurator.class)
public class CPConfigurationListModelSearchConfigurator
	implements ModelSearchConfigurator<CPConfigurationList> {

	@Override
	public String getClassName() {
		return CPConfigurationList.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.NAME
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<CPConfigurationList>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isPermissionAware() {
		return false;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new CPConfigurationListModelIndexerWriterContributor(
				_cpConfigurationListLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private CPConfigurationListLocalService _cpConfigurationListLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CPConfigurationList>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CPDefinitionModelSummaryContributor();

}