/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search;

import com.liferay.commerce.payment.internal.search.spi.model.index.contributor.CommercePaymentEntryAuditModelIndexerWriterContributor;
import com.liferay.commerce.payment.internal.search.spi.model.result.contributor.CommercePaymentEntryAuditModelSummaryContributor;
import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.commerce.payment.service.CommercePaymentEntryAuditLocalService;
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
public class CommercePaymentEntryAuditModelSearchConfigurator
	implements ModelSearchConfigurator<CommercePaymentEntryAudit> {

	@Override
	public String getClassName() {
		return CommercePaymentEntryAudit.class.getName();
	}

	@Override
	public ModelIndexerWriterContributor<CommercePaymentEntryAudit>
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
			new CommercePaymentEntryAuditModelIndexerWriterContributor(
				_commercePaymentEntryAuditLocalService,
				_dynamicQueryBatchIndexingActionableFactory);

		_modelSummaryContributor =
			new CommercePaymentEntryAuditModelSummaryContributor();
	}

	@Reference
	private CommercePaymentEntryAuditLocalService
		_commercePaymentEntryAuditLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<CommercePaymentEntryAudit>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}