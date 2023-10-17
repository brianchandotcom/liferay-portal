/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = ModelSearchConfigurator.class)
public class CPTaxCategoryModelSearchConfigurator
	implements ModelSearchConfigurator<CPTaxCategory> {

	@Override
	public String getClassName() {
		return CPTaxCategory.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CPTaxCategory>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPTaxCategory)"
	)
	private ModelIndexerWriterContributor<CPTaxCategory>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.model.CPTaxCategory)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}