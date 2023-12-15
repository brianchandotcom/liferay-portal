/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.search;

import com.liferay.commerce.currency.internal.search.spi.model.index.contributor.CommerceCurrencyModelIndexerWriterContributor;
import com.liferay.commerce.currency.internal.search.spi.model.result.contributor.CommerceCurrencyModelSummaryContributor;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = ModelSearchConfigurator.class)
public class CommerceCurrencyModelSearchConfigurator
	implements ModelSearchConfigurator<CommerceCurrency> {

	@Override
	public String getClassName() {
		return CommerceCurrency.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommerceCurrency>
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
			new CommerceCurrencyModelIndexerWriterContributor(
				_commerceCurrencyLocalService,
				_dynamicQueryBatchIndexingActionableFactory);

		_modelSummaryContributor =
			new CommerceCurrencyModelSummaryContributor();
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommerceCurrency>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}