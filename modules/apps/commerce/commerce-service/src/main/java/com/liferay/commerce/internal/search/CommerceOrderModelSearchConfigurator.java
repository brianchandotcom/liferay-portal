/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search;

import com.liferay.commerce.internal.search.spi.model.result.contributor.CommerceOrderModelSummaryContributor;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = ModelSearchConfigurator.class)
public class CommerceOrderModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceOrder> {

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceOrder>
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

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			IndexerWriterMode.UPDATE,
			_commerceOrderLocalService::getIndexableActionableDynamicQuery);
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	private ModelIndexerWriterContributor<CommerceOrder>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new CommerceOrderModelSummaryContributor();

}