/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search;

import com.liferay.commerce.payment.internal.search.spi.model.index.contributor.CommercePaymentEntryModelIndexerWriterContributor;
import com.liferay.commerce.payment.internal.search.spi.model.result.contributor.CommercePaymentEntryModelSummaryContributor;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = ModelSearchConfigurator.class)
public class CommercePaymentEntryModelSearchConfigurator
	implements ModelSearchConfigurator<CommercePaymentEntry> {

	@Override
	public String getClassName() {
		return CommercePaymentEntry.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommercePaymentEntry>
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
			new CommercePaymentEntryModelIndexerWriterContributor(
				_commercePaymentEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);

		_modelSummaryContributor =
			new CommercePaymentEntryModelSummaryContributor();
	}

	@Reference
	private CommercePaymentEntryLocalService _commercePaymentEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommercePaymentEntry>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}