/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.internal.search.spi.model.index.contributor.CPDefinitionModelIndexerWriterContributor;
import com.liferay.commerce.product.internal.search.spi.model.result.contributor.CPDefinitionModelSummaryContributor;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = ModelSearchConfigurator.class)
public class CPDefinitionModelSearchConfigurator
	implements ModelSearchConfigurator<CPDefinition> {

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			CPField.DEFAULT_IMAGE_FILE_URL, CPField.DEFAULT_IMAGE_FILE_URL,
			CPField.DEPTH, CPField.HEIGHT, CPField.IS_IGNORE_SKU_COMBINATIONS,
			CPField.PRODUCT_TYPE_NAME, CPField.SHORT_DESCRIPTION,
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.NAME, Field.SCOPE_GROUP_ID, Field.UID, Field.URL
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.NAME};
	}

	@Override
	public ModelIndexerWriterContributor<CPDefinition>
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
			new CPDefinitionModelIndexerWriterContributor(
				_cpDefinitionLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CPDefinition>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CPDefinitionModelSummaryContributor();

}