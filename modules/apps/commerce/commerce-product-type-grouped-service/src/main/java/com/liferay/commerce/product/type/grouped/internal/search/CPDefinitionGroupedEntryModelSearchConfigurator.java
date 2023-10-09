/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.internal.search;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = ModelSearchConfigurator.class)
public class CPDefinitionGroupedEntryModelSearchConfigurator
	implements ModelSearchConfigurator<CPDefinitionGroupedEntry> {

	@Override
	public String getClassName() {
		return CPDefinitionGroupedEntry.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CPDefinitionGroupedEntry>
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
		target = "(indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)"
	)
	private ModelIndexerWriterContributor<CPDefinitionGroupedEntry>
		_modelIndexWriterContributor;

	@Reference(
		target = "(indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)"
	)
	private ModelSummaryContributor _modelSummaryContributor;

}