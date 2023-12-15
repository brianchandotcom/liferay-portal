/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.search;

import com.liferay.commerce.inventory.internal.search.spi.model.index.contributor.CommerceInventoryBookedQuantityModelIndexerWriterContributor;
import com.liferay.commerce.inventory.internal.search.spi.model.result.contributor.CommerceInventoryBookedQuantityModelSummaryContributor;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
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
public class CommerceInventoryBookedQuantityModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceInventoryBookedQuantity> {

	@Override
	public String getClassName() {
		return CommerceInventoryBookedQuantity.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceInventoryBookedQuantity>
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
		_modelIndexWriterContributor =
			new CommerceInventoryBookedQuantityModelIndexerWriterContributor(
				_commerceInventoryBookedQuantityLocalService,
				_dynamicQueryBatchIndexingActionableFactory);

		_modelSummaryContributor =
			new CommerceInventoryBookedQuantityModelSummaryContributor();
	}

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommerceInventoryBookedQuantity>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}